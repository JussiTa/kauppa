package fi.harjoitustyo.verkkokauppa.web;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;

import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.spring.injection.annot.SpringBean;



import fi.harjoitustyo.verkkokauppa.palvelut.Verkkokauppapalvelu;
import fi.harjoitustyo.verkkokauppa.tietorakenne.Kayttaja;






public class WebMaster extends TurvattuSivupohja {

	@SuppressWarnings("unused")
	  private static final Log log =
	      LogFactory.getLog(WebMaster.class);
	
	
	@SpringBean(name = "verkkokauppaPalvelu")
	  private Verkkokauppapalvelu palvelu;
	

	public WebMaster(PageParameters sivunParametrit) {
		super(sivunParametrit);
		// TODO Auto-generated constructor stub
		
		
	

	    // Haetaan palvelulta kaikki tuotteet
	    Collection<Kayttaja> kayttajat =
	        palvelu.haeKaikkiKayttajat();
	    //add(new Label("kokonaismaara", String
	      //  .valueOf(jaakiekko.size())));

	    // Luodaan komponentti, jonka sis‰ltˆ toistuu useita
	    // kertaa web-sivulla.
	    RepeatingView tuotteetView = new RepeatingView("kayttajat");
	    // Lis‰t‰‰n komponentti osaksi koko web-sivun rakennetta
	    add(tuotteetView);

	    // Iteroidaan tulosten l‰pi ja muunnetaan tiedot sopivaan
	    // muotoon.
	    Iterator<Kayttaja> tuoteiter = kayttajat.iterator();
	    while (tuoteiter.hasNext()) {

	      // Lis‰t‰‰n kutakin rivi‰ varten html-merkkausta
	      // sis‰lt‰v‰ komponentti.
	      WebMarkupContainer item =
	          new WebMarkupContainer(tuotteetView.newChildId());
	      tuotteetView.add(item);

	      // Haetaan seuraavan tuotteen tiedot iteraattorilta.
	      Kayttaja kayttaja = tuoteiter.next();

	      // Lis‰t‰‰n yksitt‰isen tuotteen tiedot osaksi sivun
	      // mallia Label-komponenttien avulla.
	      item.add(new Label("id", String.valueOf(kayttaja.getId())));
	      
	      item.add(new Label("etunimi", kayttaja.getEtunimi()));
	      
	      item.add(new Label("sukunimi", kayttaja.getSukunimi()));
	      
	      
	    }
	  
	    
	}
	  
	    
	  }

