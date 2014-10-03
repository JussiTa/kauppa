package fi.harjoitustyo.verkkokauppa.palvelu;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.easymock.EasyMock.expect;






//import java.util.ArrayList;




import org.junit.Before;
import org.junit.Test;

import fi.harjoitustyo.verkkokauppa.palvelut.TuotettaEiOleRiittavastiException;
import fi.harjoitustyo.verkkokauppa.palvelut.toteutus.Ostos;
import fi.harjoitustyo.verkkokauppa.palvelut.toteutus.VerkkokauppapalveluToteutus;

import fi.harjoitustyo.verkkokauppa.tietorakenne.Tuote;

import fi.harjoitustyo.verkkokauppa.tietotaso.TuoteDao;
import fi.harjoitustyo.verkkokauppa.tietotaso.TilausDao;
import fi.harjoitustyo.verkkokauppa.tietotaso.KayttajaDao;

import fi.harjoitustyo.verkkokauppa.palvelut.toteutus.Ostoskori;

/**
 * Verkkokauppapalvelun yksikkötestit.
 * 
 * @author Jussi Isokangas
 * 
 */
public class VerkkokauppapalveluTest {

  private TuoteDao tuoteDao;
 
  private TilausDao tilausDao;
  private KayttajaDao kayttajaDao;

  /**
   * Ennen jokaista testiä asettaa tuotteita ja tilauksia
   * käsittelevät DAO-sijaistoteutukset käyttöön testejä
   * varten.
   */
  @Before
  public void asetaPalvelu() {
    VerkkokauppapalveluToteutus palveluToteutus =
        new VerkkokauppapalveluToteutus();

    tuoteDao = createMock(TuoteDao.class);
    palveluToteutus.setTuoteDao(tuoteDao);
    tilausDao = createMock(TilausDao.class);
    palveluToteutus.setTilausDao(tilausDao);
    kayttajaDao = createMock(KayttajaDao.class);
    palveluToteutus.setKayttajaDao(kayttajaDao);
  }

  /**
   * Tällä testillä pyritään varmistamaan huoneita varaavan
   * palvelutoteutuksen toiminta.
 * @throws TuotettaEiOleRiittavastiException 
 * @throws fi.harjoitustyo.verkkokauppa.tietotaso.toteutus.TuotettaEiOleRiittavastiException 
   * 
   * 
   */
  @Test
  public void testLisaaTuotteita() throws TuotettaEiOleRiittavastiException, fi.harjoitustyo.verkkokauppa.tietotaso.toteutus.TuotettaEiOleRiittavastiException  {
	  
	   Tuote tuote1 = new Tuote("auto1",3);
	   //Tilaus tilaus = new Tilaus();
	   //Kayttaja kayttaja = new Kayttaja("koe","koe","koe");
	   
	   
	   
      
             
        

    // Luodaan testattava palvelu
    VerkkokauppapalveluToteutus palvelu =
        new VerkkokauppapalveluToteutus();
     
    
     
     Ostoskori ostoskori = new Ostoskori();
     ostoskori.lisaaOstos(new Ostos(1,1));
     
     
    // luodaan sijaistoteutukset palvelun tarvitsemille
    // tietokantakäsittelyluokille.
    TuoteDao tuoteDao = createMock(TuoteDao.class);
    TilausDao tilausDao = createMock(TilausDao.class);
    KayttajaDao kayttajaDao = createMock(KayttajaDao.class);
    // Asetetaan sijaisoliot käyttöön testattavaan palveluun
    palvelu.setTilausDao(tilausDao);
    palvelu.setTuoteDao(tuoteDao);
    palvelu.setKayttajaDao(kayttajaDao);

    // Kerrotaan mitä sijaisolion metodia palvelukomponentin
    // pitäisi kutsua ja mitä kutsun yhteydessä tulee
    // palauttaa.
    
    //kayttajaDao.paivita(kayttaja);
    
    expect(tuoteDao.haeTuote("auto1")).andReturn(
            tuote1);

    // Määritellään Tilaus-rajapinnan mukaiselle
    // sijaisoliolle palvelukomponentilta tulevat kutsut.
    // Palautusarvoa ei tarvitse määritellä, koska
    // tallenna-metodin palautusarvo on tyyppiä void.
    
    
    
    
	//kayttaja.lisaaTilaus(tilaus);		  

    //tilausDao.tallenna(tilaus);
    //kayttajaDao.paivita(kayttaja);
    	  
		 
    
	 
    // Siirretään  sijaisoliot toisto-tilaan.
    replay(tuoteDao);
    //replay(tilausDao);
    //replay(kayttajaDao);

    // Kutsutaan testattavaa komponenttia.
   // palvelu.teeTilaus(ostoskori);
   //palvelu.vahvistaTilaus(ostoskori, tilaus, kayttaja);
    
    //palvelu.teeTilaus(ostoskori);
        palvelu.haeTuoteNimella("auto1");      
    
   //  Varmennetaan, että tilaukselle lisättiin tilausrivit
    //Assert.assertEquals("Tilauksessa ei tilausrivejä",
      //  4, tilaus.getTilausrivit().size());
    

    // Varmennetaan, että kaikkia määriteltyjä metodeita
    // kutsuttiin molemmille sijaisolioille. Tämä siis
    // varmentaa että huoneDao sijaisolion metodia
    // haeVapaatHuoneet ja varausDaon metodia tallenna todella
    // kutsuttiin testattavasta palvelusta.
    verify(tuoteDao);
    //verify(tilausDao);
    //verify(kayttajaDao);
  
  }



	

  }

  