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
 * Hotellisovelluksen sivu, joka n�ytt�� t�ll� hetkell�
 * vapaana olevat huoneet. Muutettu k�ytt�m��n yleist�
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
   * Konstruktori, joka luo rekister�itymissivun sis�ll�n.
   */
  public Rekisteroitymissivu(
      final PageParameters sivunParametrit) {
    super(sivunParametrit);

    // Alustetaan uusi k�ytt�j�
    final Kayttaja kayttaja = new Kayttaja();
    
   

    // Luodaan lomake rekister�intitietojen ker��mist� varten
    Form lomake = new Form("lomake") {
      private static final long serialVersionUID = 1L;

      @Override
      protected void onSubmit() {
    	  try {

              // Rekister�i uuden asiakkaan tiedot tietokantaan.
              palvelu.lisaaKayttaja(kayttaja);
              

              // Ohjataan toiselle sivulle rekister�innin j�lkeen.
              setRedirect(true);
              setResponsePage(Loginsivu.class);

            } catch (InvalidStateException e) {
              // Ker�t��n kaikki tietojen validoinnissa l�ydetyt
              // virheet ja n�ytet��n ne k�ytt�j�lle.
              for (InvalidValue value : e.getInvalidValues()) {
                warn("Sy�tt�m�si arvo kentt��n ["
                    + value.getPropertyName()
                    + "] on v��r�� muotoa!");
              }
            } catch (KayttajatunnusVarattuException e) {
              warn("Valitsemasi k�ytt�j�tunnus on jo k�yt�ss�.");
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
    


    // Asetetaan lomakkeen sis�ll�ksi k�ytt�j�tiedot ja
    // lis�t��n lomake sivulle.
    lomake.setModel(new CompoundPropertyModel(kayttaja));
    add(lomake);
    
    

    // Luodaan paneeli palautteen antamista varten
    // asiakkaalle.
    FeedbackPanel feedbackPanel =
        new FeedbackPanel("palaute");
    add(feedbackPanel);

  }
}
