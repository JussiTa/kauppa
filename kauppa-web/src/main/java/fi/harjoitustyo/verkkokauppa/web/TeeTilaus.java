package fi.harjoitustyo.verkkokauppa.web;


import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.RepeatingView;

import org.apache.wicket.spring.injection.annot.SpringBean;

import fi.harjoitustyo.verkkokauppa.palvelut.TuotettaEiOleRiittavastiException;
import fi.harjoitustyo.verkkokauppa.palvelut.Verkkokauppapalvelu;
import fi.harjoitustyo.verkkokauppa.palvelut.toteutus.Ostos;
import fi.harjoitustyo.verkkokauppa.tietorakenne.Tuote;


public class TeeTilaus extends Sivupohja {

	  @SuppressWarnings("unused")
	  private static final Log log =
	      LogFactory.getLog(Jaakiekko.class);
	  	
	  @SpringBean(name = "verkkokauppaPalvelu")
	  private Verkkokauppapalvelu palvelu;
	  
	
	public TeeTilaus(PageParameters sivunParametrit) {
		super(sivunParametrit);
		
		
		/**
		 * Kutsutaan palvelu-rajapinnan metodia ja takistetaan, ett‰ tuotetta on riitt‰v‰ m‰‰r‰
		 * Otetaan poikkeuskiinni ja annetaan varoitus kyseisest‰ tuotteesta. poistetaan tuote korista, jos sit‰ on
		 * 0 kappaletta
		 * 
		 * 
		*/
		
	    try {
			palvelu.teeTilaus(getOstoskori());
		} catch (TuotettaEiOleRiittavastiException e) {
			
			for(Ostos ostos :getOstoskori()){
			   Tuote tuote = palvelu.haePerusavaimella(ostos.getId());
			   if (ostos.getKpl()> tuote.getKpl()){
					warn("Tuotetta "+tuote.getNimi()+" varastossa vain " +tuote.getKpl()+"  kappaletta!");
			        if(ostos.getKpl()== 0)
				       getOstoskori().poistaOstos(ostos);	
			   }
			}
				
		  }
		
	   
		// Iteroidaan tulosten l‰pi ja muunnetaan tiedot sopivaan
		// muotoon.
		RepeatingView tuotteetView = new RepeatingView("ostoskori3");
		add(tuotteetView);
		
		Iterator<Ostos> tuoteiter = getOstoskori().iterator();

		while (tuoteiter.hasNext()) {

		  // Lis‰t‰‰n kutakin rivi‰ varten html-merkkausta
		  // sis‰lt‰v‰ komponentti.
		  WebMarkupContainer item =
		     new WebMarkupContainer(tuotteetView.newChildId());
		        
		 tuotteetView.add(item);
			
			
		  // Haetaan seuraavan tuotteen tiedot iteraattorilta.
		  final Ostos ostos = tuoteiter.next();
		  Tuote tuote = palvelu.haePerusavaimella(ostos.getId());
		  // Lis‰t‰‰n yksitt‰isen tuotteen tiedot osaksi sivun
		  // mallia Label-komponenttien avulla.
		  item.add(new Label("id", String.valueOf(ostos.getId())));
		  	    
		  item.add(new Label("nimi", tuote
		          .getNimi()));
		  item.add(new Label("hinta", String.valueOf(tuote
		      .getHinta())));
		  
		  item.add(new Label("kpl", String.valueOf(ostos
			      .getKpl())));
		 
		  
		  item.add(new Link("poista") {
              private static final long serialVersionUID = 1L;

				public void onClick() {
					
                   getOstoskori().poistaOstos(ostos);
                   setResponsePage(TeeTilaus.class);
                   
               }
          });
		  
		  
        
		  
		}
		// Luodaan paneeli palautteen antamista varten
	    // asiakkaalle, jos tuotetta ei riitt‰v‰sti.
	     FeedbackPanel feedbackPanel =
	        new FeedbackPanel("palaute");
	     add(feedbackPanel);
       
	     add(new Link("vahvista") {
            private static final long serialVersionUID = 1L;

				public void onClick() {
			        setResponsePage(VahvistaTilaus.class);
				
                 
                 
                
				} 
		 });
	
	  }
	
   }
 

      