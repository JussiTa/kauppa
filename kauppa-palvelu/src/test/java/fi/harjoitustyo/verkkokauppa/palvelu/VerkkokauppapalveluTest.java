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
 * Verkkokauppapalvelun yksikk�testit.
 * 
 * @author Jussi Isokangas
 * 
 */
public class VerkkokauppapalveluTest {

  private TuoteDao tuoteDao;
 
  private TilausDao tilausDao;
  private KayttajaDao kayttajaDao;

  /**
   * Ennen jokaista testi� asettaa tuotteita ja tilauksia
   * k�sittelev�t DAO-sijaistoteutukset k�ytt��n testej�
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
   * T�ll� testill� pyrit��n varmistamaan huoneita varaavan
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
    // tietokantak�sittelyluokille.
    TuoteDao tuoteDao = createMock(TuoteDao.class);
    TilausDao tilausDao = createMock(TilausDao.class);
    KayttajaDao kayttajaDao = createMock(KayttajaDao.class);
    // Asetetaan sijaisoliot k�ytt��n testattavaan palveluun
    palvelu.setTilausDao(tilausDao);
    palvelu.setTuoteDao(tuoteDao);
    palvelu.setKayttajaDao(kayttajaDao);

    // Kerrotaan mit� sijaisolion metodia palvelukomponentin
    // pit�isi kutsua ja mit� kutsun yhteydess� tulee
    // palauttaa.
    
    //kayttajaDao.paivita(kayttaja);
    
    expect(tuoteDao.haeTuote("auto1")).andReturn(
            tuote1);

    // M��ritell��n Tilaus-rajapinnan mukaiselle
    // sijaisoliolle palvelukomponentilta tulevat kutsut.
    // Palautusarvoa ei tarvitse m��ritell�, koska
    // tallenna-metodin palautusarvo on tyyppi� void.
    
    
    
    
	//kayttaja.lisaaTilaus(tilaus);		  

    //tilausDao.tallenna(tilaus);
    //kayttajaDao.paivita(kayttaja);
    	  
		 
    
	 
    // Siirret��n  sijaisoliot toisto-tilaan.
    replay(tuoteDao);
    //replay(tilausDao);
    //replay(kayttajaDao);

    // Kutsutaan testattavaa komponenttia.
   // palvelu.teeTilaus(ostoskori);
   //palvelu.vahvistaTilaus(ostoskori, tilaus, kayttaja);
    
    //palvelu.teeTilaus(ostoskori);
        palvelu.haeTuoteNimella("auto1");      
    
   //  Varmennetaan, ett� tilaukselle lis�ttiin tilausrivit
    //Assert.assertEquals("Tilauksessa ei tilausrivej�",
      //  4, tilaus.getTilausrivit().size());
    

    // Varmennetaan, ett� kaikkia m��riteltyj� metodeita
    // kutsuttiin molemmille sijaisolioille. T�m� siis
    // varmentaa ett� huoneDao sijaisolion metodia
    // haeVapaatHuoneet ja varausDaon metodia tallenna todella
    // kutsuttiin testattavasta palvelusta.
    verify(tuoteDao);
    //verify(tilausDao);
    //verify(kayttajaDao);
  
  }



	

  }

  