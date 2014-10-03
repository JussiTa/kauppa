/*
 * Kuha, Janne: Tehokas Java EE -sovellustuotanto. WSOY 2008,
 * www.docendo.fi.
 */
package fi.harjoitustyo.verkkokauppa.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.security.components.SecureWebPage;

import fi.harjoitustyo.verkkokauppa.palvelut.toteutus.Ostoskori;
import fi.harjoitustyo.verkkokauppa.web.turvallisuus.Logoutsivu;

/**
 * 
 * Yleinen layout kaikille sovelluksen turvallisiksi
 * merkityille sivulle.
 * 
 * @author kuha
 * 
 */
public abstract class TurvattuSivupohja extends SecureWebPage {

  @SuppressWarnings("unused")
  private static final Log log =
      LogFactory.getLog(TurvattuSivupohja.class);
  private static final long serialVersionUID = 1L;
  protected Ostoskori ostoskori = ((Sessio)
          getSession()).haeOstoskori();

  /**
   * Konstruktori, joka lis‰‰ sovelluksen linkit vasempaan
   * reunaan.
   * 
   * @param sivunParametrit
   */
  public TurvattuSivupohja(
      final PageParameters sivunParametrit) {

    // vasemman reunan linkit
   
    add(new BookmarkablePageLink("alku", Aloitussivu.class));
    add(new BookmarkablePageLink("logoutLinkki",
        Logoutsivu.class));
    add(new BookmarkablePageLink("webmaster",
            WebMaster.class));
    
  }
  
  public void setOstoskori(Ostoskori ostoskori) {
		this.ostoskori = ostoskori;
	  }
	 
	  public Ostoskori getOstoskori() {
		return ostoskori;
	  }


}