package fi.harjoitustyo.verkkokauppa.web;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.spring.injection.annot.SpringBean;


import fi.harjoitustyo.verkkokauppa.palvelut.Verkkokauppapalvelu;
import fi.harjoitustyo.verkkokauppa.tietorakenne.Tuote;



public class Tuotteet extends WebPage {
	
	
	@SuppressWarnings("unused")
	  private static final Log log =
	      LogFactory.getLog(Tuotteet.class);
	

	  @SpringBean(name = "verkkokauppaPalvelu")
	  private Verkkokauppapalvelu palvelu;
	  
	
	
	
	
	

	public Tuotteet(PageParameters sivunParametrit) {
		super(sivunParametrit);
		// TODO Auto-generated constructor stub
          
	
		  /**
		   * Konstruktori, jokaa hakee vapaat huoneet palvelun avulla
		   * ja muuntaa tulokset Wicketin web-malliksi.
		   * 
		   * @param sivunParametrit
		   */
		  

		    // Haetaan palvelulta kaikki vapaat huoneet
		    Collection<Tuote> tuotteet1 =
		        palvelu.haeKaikkiTuotteet();
		    

		    // Luodaan komponentti, jonka sis‰ltˆ toistuu useita
		    // kertaa web-sivulla.
		    RepeatingView huoneetView = new RepeatingView("tuotteet");
		    // Lis‰t‰‰n komponentti osaksi koko web-sivun rakennetta
		    add(huoneetView);

		    // Iteroidaan tulosten l‰pi ja muunnetaan tiedot sopivaan
		    // muotoon.
		    Iterator<Tuote> tuotteet = tuotteet1.iterator();
		    while (tuotteet.hasNext()) {

		      // Lis‰t‰‰n kutakin rivi‰ varten html-merkkausta
		      // sis‰lt‰v‰ komponentti.
		      WebMarkupContainer item =
		          new WebMarkupContainer(huoneetView.newChildId());
		      huoneetView.add(item);

		      // Haetaan seuraavan huoneen tiedot iteraattorilta.
		      Tuote tuote = tuotteet.next();

		      // Lis‰t‰‰n yksitt‰isen huoneen tiedot osaksi sivun
		      // mallia Label-komponenttien avulla.
		      item
		          .add(new Label("id", String.valueOf(tuote.getId())));
		      item.add(new Label("nimi", tuote
		          .getNimi()));
		      
		    }
		  }
		}


