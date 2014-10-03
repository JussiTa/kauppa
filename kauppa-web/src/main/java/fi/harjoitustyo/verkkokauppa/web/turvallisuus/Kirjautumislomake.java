/*
 * Kuha, Janne: Tehokas Java EE -sovellustuotanto. WSOY 2008,
 * www.docendo.fi.
 */
package fi.harjoitustyo.verkkokauppa.web.turvallisuus;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.model.CompoundPropertyModel;

import org.apache.wicket.security.hive.authentication.DefaultSubject;
import org.apache.wicket.security.strategies.LoginException;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.value.ValueMap;

import fi.harjoitustyo.verkkokauppa.palvelut.Verkkokauppapalvelu;
import fi.harjoitustyo.verkkokauppa.tietorakenne.Kayttaja;
import fi.harjoitustyo.verkkokauppa.tietotaso.toteutus.VirheellinenTunnusTaiSalasanaException;
import fi.harjoitustyo.verkkokauppa.web.Sessio;
import fi.harjoitustyo.verkkokauppa.web.VerkkokauppaSovellus;

/**
 * Lomake, jota k‰ytet‰‰n sis‰‰nkirjautumisen yhteydess‰.
 * 
 * @author Jussi Isokangas
 * 
 */
public class Kirjautumislomake extends StatelessForm {

  @SuppressWarnings("unused")
  private static final Log log =
      LogFactory.getLog(Kirjautumislomake.class);

  WebKayttajaRooli webKayttajaRooli;
  
  private static final long serialVersionUID = 1L;

  @SpringBean(name = "verkkokauppaPalvelu")
  private Verkkokauppapalvelu palvelu;

  /**
   * Konstruktori, jolla v‰litet‰‰n komponentin tunniste.
   * 
   * @param id
   */
  public Kirjautumislomake(final String id) {
    super(id);
    // Perustiedot
    setModel(new CompoundPropertyModel(new ValueMap()));
    add(new RequiredTextField("kayttajatunnus"));
    add(new PasswordTextField("salasana"));
  }

  /**
   * Hoitaa k‰ytt‰j‰n kirjaamisen sis‰‰n j‰rjestelm‰‰n.
   */
  @Override
  protected void onSubmit() {
    try {

      // Luetaan lomakkeelta k‰ytt‰j‰n syˆtt‰m‰ k‰ytt‰j‰tunnus
      // ja salasana.
      ValueMap arvot = (ValueMap) getModelObject();
      String tunnus = arvot.getString("kayttajatunnus");
      String salasana = arvot.getString("salasana");

      // Haetaan palvelulta k‰ytt‰j‰tiedot.
      Kayttaja kayttaja =
          palvelu.haeJaVarmennaKayttaja(tunnus, salasana);
      Sessio sessio = (Sessio) getSession();
      sessio.setKayttaja(kayttaja);

      // Muunnetaan k‰ytt‰j‰tiedot Wicketille sopivaksi
      // Principal-rooliksi. WebKayttajaRooli toteuttaa
      // Principal-rajapinnan.
      WebKayttajaRooli webKayttajaRooli =
          new WebKayttajaRooli(kayttaja);
      
      DefaultSubject wicketKayttaja = new DefaultSubject();
      wicketKayttaja.addPrincipal(webKayttajaRooli);
     
      // Kirjataan k‰ytt‰j‰ sis‰‰n j‰rjestelm‰‰n.
      
      sessio.login(new Loginkonteksti(wicketKayttaja));
      
      
      // Ohjataan alunperin pyydetylle sivulle tai
      // sovelluksen aloitussivulle
      if (!continueToOriginalDestination()) {
        setResponsePage(VerkkokauppaSovellus.get().getHomePage());
      }

    } catch (LoginException e) {
      // Mahdollinen kirjautumisvirhe.
      warn("Kirjautuminen ei onnistu.");
    } catch (VirheellinenTunnusTaiSalasanaException e) {
      // Ilmoitus virheellisest‰ tunnuksesta tai salasanasta.
      warn("Virheellinen k‰ytt‰j‰tunnus ja salasana -yhdistelm‰.");
    }
  }
     
  public WebKayttajaRooli haekayttaja(){
	   return webKayttajaRooli;
   }
}
