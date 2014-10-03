package fi.harjoitustyo.verkkokauppa.web;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.wicket.markup.html.basic.Label;

import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.spring.injection.annot.test.AnnotApplicationContextMock;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;

import fi.harjoitustyo.verkkokauppa.palvelut.Verkkokauppapalvelu;
import fi.harjoitustyo.verkkokauppa.tietorakenne.Tuote;


public class TestHaeKaikkiTuotteet {
	
	

	/**
	 * Testi, joka testaa tuotteiden hakua.
	 * 
	 * @author Jussi Isokangas
	 * 
	 */
	

	  /**
	   * Metodi, joka testaa  tuotteiden listauksen
	   * web-sivukomponentin toiminnallisuutta.
	   * 
	   */
	  @Test
	  public void testTuotteetListaus() throws Exception {

	    // Luodaan verkkokauppapalvelua esittävä sijaisolio
	    Verkkokauppapalvelu palvelu = createMock(Verkkokauppapalvelu.class);

	    // Hotellipalveluun pitäisi kohdistua yksi kutsu
	    // vapaiden huoneiden hausta ja se palauttaa
	    // listan huoneista.
	    Collection<Tuote> tulokset = new ArrayList<Tuote>();
	    tulokset.add(new Tuote("maila", 3));
	    tulokset.add(new Tuote("pallo", 3));
	    expect(palvelu.haeKaikkiTuotteet()).andReturn(tulokset);

	    // Luodaan mock-sovelluskonteksti, josta palvelun voi
	    // hakea. Toinen mahdollisuus
	    // olisi asettaa palvelu ulkopuolelta.
	    AnnotApplicationContextMock sijaiskonteksti =
	        new AnnotApplicationContextMock();
	    sijaiskonteksti.putBean("verkkokauppaPalvelu", palvelu);

	    // Asetetaan sovelluksen käyttöön
	    // kuuntelija Spring-komponenttien käsittelyä varten
	    WicketTester wt = new WicketTester();
	    wt.getApplication().addComponentInstantiationListener(
	        new SpringComponentInjector(wt.getApplication(),
	            sijaiskonteksti));

	    // Siirretään palvelun sijaisolio tilaan toisto
	    replay(palvelu);

	    // Varmennetaan, että sivu tuotti oikeat tulokset.
	    wt.startPage(new Tuotteet(null));
	   wt.assertRenderedPage(Tuotteet.class);
	    wt.assertComponent("tuotteet", RepeatingView.class);
	    wt.assertComponent("tuotteet:1:id", Label.class);
	    wt.assertComponent("tuotteet:1:nimi", Label.class);
	  
	    
	    wt.assertNoErrorMessage();

	    wt.debugComponentTrees();

	    // Varmennetaan, että oikeita palveluita kutsuttiin
	    verify(palvelu);

	  }
	}

	
	


