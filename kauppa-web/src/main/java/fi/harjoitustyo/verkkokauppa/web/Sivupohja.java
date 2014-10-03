/*
 * Kuha, Janne: Tehokas Java EE -sovellustuotanto. WSOY 2008,
 * www.docendo.fi.
 */
package fi.harjoitustyo.verkkokauppa.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.PageParameters;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;




import fi.harjoitustyo.verkkokauppa.palvelut.toteutus.Ostoskori;
import fi.harjoitustyo.verkkokauppa.web.turvallisuus.Loginsivu;
import fi.harjoitustyo.verkkokauppa.web.turvallisuus.Logoutsivu;

/**
 * 
 * Yleinen layout kaikille sovelluksen sivuille.
 * 
 * @author kuha
 * 
 */
public abstract class Sivupohja extends WebPage {
   
  @SuppressWarnings("unused")
  private static final Log log =
      LogFactory.getLog(Sivupohja.class);
  private static final long serialVersionUID = 1L;
   VerkkokauppaSovellus sovellus;
  protected Ostoskori ostoskori = ((Sessio)
          getSession()).haeOstoskori();
  
 
  /**
   * Konstruktori, joka lis‰‰ sovelluksen linkit vasempaan
   * reunaan.
   * 
   * @param sivunParametrit
   */
  public Sivupohja(final PageParameters sivunParametrit) {
	

    // vasemman reunan linkit
    add(new BookmarkablePageLink("jaakiekko",
        Jaakiekko.class));
    add(new BookmarkablePageLink("kalastus",
            Kalastus.class));
    
    add(new BookmarkablePageLink("sukellus",
            Sukellus.class));
    add(new BookmarkablePageLink("alku", Aloitussivu.class));
    add(new BookmarkablePageLink("logoutLinkki",
        Logoutsivu.class));
    add(new BookmarkablePageLink("loginLinkki",
        Loginsivu.class));
    
    add(new BookmarkablePageLink("webmaster",
            WebMaster.class));
    add(new BookmarkablePageLink("ostoskori",
            NaytaOstoskori.class));
    
    add(new BookmarkablePageLink("omatSivut",
            OmatSivut.class));
    
        
    
    add(new Label("kokonaismaara", new PropertyModel(getOstoskori(), "lkm")));
  }
  
  public void setOstoskori(Ostoskori ostoskori) {
	this.ostoskori = ostoskori;
  }
 
  public Ostoskori getOstoskori() {
	return ostoskori;
  }


}