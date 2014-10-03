package fi.harjoitustyo.verkkokauppa.web;

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
import fi.harjoitustyo.verkkokauppa.palvelut.toteutus.Ostos;
import fi.harjoitustyo.verkkokauppa.tietorakenne.Kayttaja;
import fi.harjoitustyo.verkkokauppa.tietorakenne.Tilaus;
import fi.harjoitustyo.verkkokauppa.tietorakenne.Tuote;



public class VahvistaTilaus extends Sivupohja {
    
	private Kayttaja kayttaja;
	
	
	
	@SuppressWarnings("unused")
	  private static final Log log =
	      LogFactory.getLog(Jaakiekko.class);
	  private static final long serialVersionUID = 1L;

	  @SpringBean(name = "verkkokauppaPalvelu")
	
	 
	  private Verkkokauppapalvelu palvelu;
	  
	
	
	public VahvistaTilaus(PageParameters sivunParametrit) {
		super(sivunParametrit);
		// TODO Auto-generated constructor stub
		
		
		   kayttaja = ((Sessio)
			          getSession()).getKayttaja();
		
		Tilaus tilaus = new Tilaus();
		
		
		
		
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
		 
		  
		}
		FeedbackPanel feedbackPanel =
	        new FeedbackPanel("palaute");
	    add(feedbackPanel);
	    info("Tilaus vahvistettu");
		palvelu.vahvistaTilaus(getOstoskori(), tilaus, kayttaja);
		
		getOstoskori().poistaKaikki();
		kayttaja = palvelu.haePerusavaimella3(kayttaja.getId());
		((Sessio) getSession()).setKayttaja(kayttaja);
		
		
	}

}
