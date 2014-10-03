package fi.harjoitustyo.verkkokauppa.web;



	
	

	import org.apache.commons.logging.Log;
	import org.apache.commons.logging.LogFactory;
	import org.apache.wicket.PageParameters;

	import org.apache.wicket.markup.html.WebPage;
	
	import org.apache.wicket.markup.html.link.BookmarkablePageLink;



    import fi.harjoitustyo.verkkokauppa.palvelut.toteutus.Ostoskori;

    import fi.harjoitustyo.verkkokauppa.web.turvallisuus.Logoutsivu;
	

	/**
	 * 
	 * Yleinen layout kaikille sovelluksen sivuille.
	 * 
	 * @author kuha
	 * 
	 */
	
	public class OmatSivut extends WebPage { 
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
	  public OmatSivut(final PageParameters sivunParametrit) {
		   

	    // vasemman reunan linkit
	    
		  add(new BookmarkablePageLink("etusivu",
			        Aloitussivu.class));
	       
	    
		add(new BookmarkablePageLink("logoutLinkki",
	        Logoutsivu.class));
	    add(new BookmarkablePageLink("tilaukset",
		        Tilaukset.class));
	    
	    	          
	    
	        
	    
	    
	  }
	  
	  public void setOstoskori(Ostoskori ostoskori) {
		this.ostoskori = ostoskori;
	  }
	 
	  public Ostoskori getOstoskori() {
		return ostoskori;
	  }


	}
	

