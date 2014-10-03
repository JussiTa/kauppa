/*
 * Kuha, Janne: Tehokas Java EE -sovellustuotanto. WSOY 2008,
 * www.docendo.fi.
 */
package fi.harjoitustyo.verkkokauppa.web.turvallisuus;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;

import org.apache.wicket.spring.injection.annot.SpringBean;
import org.hibernate.validator.InvalidStateException;
import org.hibernate.validator.InvalidValue;

import fi.harjoitustyo.verkkokauppa.palvelut.Verkkokauppapalvelu;
import fi.harjoitustyo.verkkokauppa.tietorakenne.Kayttaja;

import fi.harjoitustyo.verkkokauppa.tietotaso.toteutus.KayttajatunnusVarattuException;
import fi.harjoitustyo.verkkokauppa.web.Sivupohja;
import fi.harjoitustyo.verkkokauppa.web.ehdotus.PaikkakuntaEhdotus;


/**
 * Hotellisovelluksen sivu, joka näyttää tällä hetkellä
 * vapaana olevat huoneet. Muutettu käyttämään yleistä
 * sivulayouttia.
 */
public class Rekisteroitymissivu extends Sivupohja {

  @SuppressWarnings("unused")
  private static final Log log =
      LogFactory.getLog(Rekisteroitymissivu.class);
  private static final long serialVersionUID = 1L;

  @SpringBean(name = "verkkokauppaPalvelu")
  private Verkkokauppapalvelu palvelu;

  /**
   * Konstruktori, joka luo rekisteröitymissivun sisällön.
   */
  public Rekisteroitymissivu(
      final PageParameters sivunParametrit) {
    super(sivunParametrit);

    // Alustetaan uusi käyttäjä
    final Kayttaja kayttaja = new Kayttaja();
    
   

    // Luodaan lomake rekisteröintitietojen keräämistä varten
    Form lomake = new Form("lomake") {
      private static final long serialVersionUID = 1L;

      @Override
      protected void onSubmit() {
    	  try {

              // Rekisteröi uuden asiakkaan tiedot tietokantaan.
              palvelu.lisaaKayttaja(kayttaja);
              

              // Ohjataan toiselle sivulle rekisteröinnin jälkeen.
              setRedirect(true);
              setResponsePage(Loginsivu.class);

            } catch (InvalidStateException e) {
              // Kerätään kaikki tietojen validoinnissa löydetyt
              // virheet ja näytetään ne käyttäjälle.
              for (InvalidValue value : e.getInvalidValues()) {
                warn("Syöttämäsi arvo kenttään ["
                    + value.getPropertyName()
                    + "] on väärää muotoa!");
              }
            } catch (KayttajatunnusVarattuException e) {
              warn("Valitsemasi käyttäjätunnus on jo käytössä.");
            }
          }
        };
    
    
    // Perustiedot
    lomake.add(new RequiredTextField("kayttajatunnus"));
    lomake.add(new PasswordTextField("salasana"));
    lomake.add(new RequiredTextField("etunimi"));
    lomake.add(new RequiredTextField("sukunimi"));

    
    lomake.add(new TextField("katu"));
    lomake.add(new PaikkakuntaEhdotus(
        "paikkakunta"));
    lomake.add(new TextField("postinumero"));
    lomake.add(new TextField("postilokero"));
    


    // Asetetaan lomakkeen sisällöksi käyttäjätiedot ja
    // lisätään lomake sivulle.
    lomake.setModel(new CompoundPropertyModel(kayttaja));
    add(lomake);
    
    

    // Luodaan paneeli palautteen antamista varten
    // asiakkaalle.
    FeedbackPanel feedbackPanel =
        new FeedbackPanel("palaute");
    add(feedbackPanel);

  }
}
