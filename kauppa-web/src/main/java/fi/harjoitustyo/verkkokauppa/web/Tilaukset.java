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




import fi.harjoitustyo.verkkokauppa.tietorakenne.Kayttaja;
import fi.harjoitustyo.verkkokauppa.tietorakenne.Tilaus;



public class Tilaukset extends OmatSivut {

	 
	private Kayttaja kayttaja;
	private Collection<Tilaus> tilaukset;
	
	
	
	
	
	@SuppressWarnings("deprecation")
	public Tilaukset(PageParameters sivunParametrit) {
		super(sivunParametrit);
		// TODO Auto-generated constructor stub
	
		@SuppressWarnings("unused")
		  final Log log =
		      LogFactory.getLog(Jaakiekko.class);
		  
		  		        
		kayttaja = ((Sessio)
		     getSession()).getKayttaja();
		   
			
		   
		    
		// Haetaan k‰yt‰j‰n tilaukset
		try{
		  tilaukset =
		  kayttaja.getTilaukset();
		}catch(Exception e){
		   warn("Sinulla ei ole yht‰‰n tilausta");
		  }

					
  		RepeatingView tilaukseni = new RepeatingView("tilaukset1");
  		   add(tilaukseni); 
  				
  			Iterator<Tilaus> tilaukset2 = tilaukset.iterator();
  			while(tilaukset2.hasNext()){
		        // Haetaan seuraavan tilauksen tiedot iteraattorilta.
		        final Tilaus tilaus = tilaukset2.next();
  				
  		        WebMarkupContainer item =
  		             new WebMarkupContainer(tilaukseni.newChildId());
  		             tilaukseni.add(item);
  		      
  		      
  		           item
                      .add(new Label("pvm", String.valueOf(tilaus.getAikaleima().toLocaleString())));
                     
  				 					  
  		    }
  				
  			FeedbackPanel feedbackPanel =
  			   new FeedbackPanel("palaute");
  			add(feedbackPanel);
  			   	
  			 					          
	      }
	
}
  					      	                 
  					      	        
  					      	          
  					      	               
  					      	              
  					      	      
  					              
  					                	    
  					             
  					      	             
  					      

  					  
	

