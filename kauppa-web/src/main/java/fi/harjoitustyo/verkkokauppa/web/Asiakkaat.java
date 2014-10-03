package fi.harjoitustyo.verkkokauppa.web;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.spring.injection.annot.SpringBean;

import fi.harjoitustyo.verkkokauppa.palvelut.Verkkokauppapalvelu;
import fi.harjoitustyo.verkkokauppa.tietorakenne.Kayttaja;


public class Asiakkaat extends WebMaster {

	  final Log log =
	      LogFactory.getLog(Jaakiekko.class);
	
	@SuppressWarnings("unused")
	private Collection<Kayttaja> kayttajat;
	@SpringBean(name = "verkkokauppaPalvelu")
	private Verkkokauppapalvelu palvelu;
	  
	

	public Asiakkaat(PageParameters sivunParametrit) {
		super(sivunParametrit);
		// TODO Auto-generated constructor stub
		
			   
			    
			// Haetaan käyttäjät
			   try{
			    kayttajat =
			        palvelu.haeKaikkiKayttajat();
			   }catch(Exception e){
				   warn("Jostain syystä ei löytynyt käyttäjiä");
			      }

			    
	  						
	  						
	  		   RepeatingView kayttajat = new RepeatingView("kayttajat");
	  		   add(kayttajat); 
	  				
	  			 @SuppressWarnings("unchecked")
				Iterator<Kayttaja> kayttajat2 = kayttajat.iterator();
	  				while(kayttajat2.hasNext()){
			          // Haetaan seuraavan kayttäjän tiedot iteraattorilta.
			          final Kayttaja kayttaja = kayttajat2.next();
	  				
	  			      WebMarkupContainer item =
	  		             new WebMarkupContainer(kayttajat.newChildId());
	  		             kayttajat.add(item);
	  		      
	  		      
	  		          item.add(new Label("nimi", kayttaja.getEtunimi()));
	  		          item.add(new Label("sukunimi", kayttaja.getSukunimi()));
	  		          item.add(new Label("katu", kayttaja.getKatu()));
	  		          item.add(new Label("paikkakunta", kayttaja.getPaikkakunta()));
	  		          item.add(new Label("pl", String.valueOf(kayttaja.getPostilokero())));
	  		          item.add(new Label("postinumero", String.valueOf(kayttaja.getPostinumero())));
	  				  					  
	  				 }
	  			FeedbackPanel feedbackPanel =
	  			     new FeedbackPanel("palaute");
	  			add(feedbackPanel);
	  		       	
	  			 					          
		      }
		
	
		
	}


