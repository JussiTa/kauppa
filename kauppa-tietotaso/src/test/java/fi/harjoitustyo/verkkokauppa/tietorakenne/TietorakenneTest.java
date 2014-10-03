package fi.harjoitustyo.verkkokauppa.tietorakenne;


//import java.util.ArrayList;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.SortedSet;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.validator.InvalidStateException;
import org.junit.Test;






import fi.harjoitustyo.verkkokauppa.test.AvustajaTesteille;
import fi.harjoitustyo.verkkokauppa.test.TietokantaTestCase;






/**
 * T‰m‰n luokan avulla testataan tietomalliluokkien
 * perusteita, eli tallentamista ja lataamista.
 * 
 * @author Jussi Isokangas
 * 
 */
public class TietorakenneTest extends TietokantaTestCase {
  private static final Log log =
      LogFactory.getLog(TietorakenneTest.class);

@Test
  public void liukulukutesti() {
    System.out.println(10.0 * 0.09);
  }

/**
 * T‰ss‰ testiss‰ testataan alv-kannan m‰‰rittely‰
 * tapahtumariville.
 */
@Test
public void testAlvKanta() {
  // Luodaan kaksi eri alv-kantaa k‰ytt‰v‰‰ tapahtumaa.
  Tapahtumarivi ruokaostos = new Tapahtumarivi();
  ruokaostos.setVerotonSumma(new BigDecimal("2.50"));
  ruokaostos.setAlvKanta(AlvKanta.ALV17);

  Tapahtumarivi paasylippu = new Tapahtumarivi();
  paasylippu.setVerotonSumma(new BigDecimal("10.20"));
  paasylippu.setAlvKanta(AlvKanta.ALV8);

  // Tallennetaan tapahtumarivit tietokantaan.
  Integer rId = tallennaEntiteetti(ruokaostos);
  Integer pId = tallennaEntiteetti(paasylippu);

  // Ladataan tapahtumarivit tietokannasta.
  ruokaostos = lataaEntiteetti(Tapahtumarivi.class, rId);
  paasylippu = lataaEntiteetti(Tapahtumarivi.class, pId);

  // Varmistetaan ALV-kantojen oikeellisuus.
  Assert.assertEquals("ALV-kanta v‰‰rin ruokaostokselle",
      AlvKanta.ALV17, ruokaostos.getAlvKanta());

  Assert.assertEquals("ALV-kanta v‰‰rin p‰‰sylipulle",
      AlvKanta.ALV8, paasylippu.getAlvKanta());
}



  /**
   * T‰ss‰ testiss‰ testataan alv-kannan m‰‰rittely‰
   * tapahtumariville.
   */
  /**
   * T‰m‰ metodi testaa k‰ytt‰j‰n tallentamista tietokantaan.
   */
@Test
public void testKayttajaTallennus() {
  // Luodaan k‰ytt‰j‰
  log.debug("Luodaan k‰ytt‰j‰");
  Kayttaja kayttaja =
      new Kayttaja("mmottonen", "mikko", "mottonen");
  kayttaja.setPaikkakunta("jyvaskyl‰");
  kayttaja.setPostinumero("41140");

  // Tallennetaan k‰ytt‰j‰
  log.debug("K‰ytt‰j‰ tallennetaan tietokantaan");
  EntityManager em = getEntityManager();
  EntityTransaction tx = em.getTransaction();
  tx.begin();
  em.persist(kayttaja);
  Integer perusavain = kayttaja.getId();
  tx.commit();

  // Luetaan kayttaja tietokannasta
  log.debug("K‰ytt‰j‰ luetaan tietokannasta");
  tx = em.getTransaction();
  tx.begin();
  kayttaja = em.find(Kayttaja.class, perusavain);
  tx.commit();

  // Varmistetaan, ett‰ k‰ytt‰j‰tunnus oli oikein
  // tietokannassa.
  Assert.assertEquals(
      "K‰ytt‰j‰tunnus oli v‰‰rin tietokannassa",
      "mmottonen", kayttaja.getKayttajatunnus());

}


  /**
   * T‰m‰ testi testaa liian pitk‰n k‰ytt‰j‰tunnuksen
   * asettamista k‰ytt‰j‰lle. Tallennuksen yhteydess‰ tulisi
   * tulla poikkus, mik‰li validointi toimii oikein.
   */
  @Test(expected = InvalidStateException.class)
  public void testLiianPitkaKayttajatunnus() {
    // Luodaan k‰ytt‰j‰
    Kayttaja kayttaja = new Kayttaja();
    String tunnus =
        AvustajaTesteille.satunnainenMerkkijono(1024);
    kayttaja.setKayttajatunnus(tunnus);
    kayttaja.setEtunimi("Etunimi");
    kayttaja.setSukunimi("Sukunimi");
    kayttaja.setPaikkakunta("jy");
    kayttaja.setPostinumero("41140");
    tallennaEntiteetti(kayttaja);
  }

  /**
   * T‰m‰ testi testaa k‰ytt‰j‰n osoitteen toimintaa.
   */
  
  @Test
  public void testOsoite() {
    //Luodaan k‰ytt‰j‰
    Kayttaja kayttaja =
        new Kayttaja("Esko", "Esko", "Esimerkki");
    
    Kayttaja kayttaja2 =
        new Kayttaja("Esko1", "Esko2", "Esimerkki");
    
    Kayttaja kayttaja3 =
        new Kayttaja("Esko2", "Esko3", "Esimerkki");

    // Luodaan osoite
    
    kayttaja.setKatu("Keskuskatu 25a");
    kayttaja.setPaikkakunta("Jyvaskyla");
    kayttaja.setPostinumero("40100");
    kayttaja.setPostilokero(null);
    
    kayttaja2.setKatu("Keskuskatu 25a");
    kayttaja2.setPaikkakunta("Jyvaskyla");
    kayttaja2.setPostinumero("40100");
    kayttaja2.setPostilokero(null);
    
    kayttaja3.setKatu("Keskuskatu 25a");
    kayttaja3.setPaikkakunta("Jyvaskyla");
    kayttaja3.setPostinumero("40100");
    kayttaja3.setPostilokero(null);
    
   
    
    

    // Tallennetaan k‰ytt‰j‰ ja osoite
    
    Integer kayttajaid = tallennaEntiteetti(kayttaja);
    Integer kayttajaid2 = tallennaEntiteetti(kayttaja2);
    Integer kayttajaid3 = tallennaEntiteetti(kayttaja3);
    
    // Luetaan kayttaja tietokannasta
    kayttaja = lataaEntiteetti(Kayttaja.class, kayttajaid);
    kayttaja2 = lataaEntiteetti(Kayttaja.class, kayttajaid2);
    kayttaja3 = lataaEntiteetti(Kayttaja.class, kayttajaid3);

 
    
    
    // Varmistetaan, ett‰ kaikki tiedot tulivat oikein
    // kannasta.
    
    Assert.assertNotNull("K‰ytt‰j‰lt‰ puuttuu katu", kayttaja.getKatu());
    Assert.assertNotNull("K‰ytt‰j‰lt‰ puuttuu katu", kayttaja2.getKatu());
    Assert.assertNotNull("K‰ytt‰j‰lt‰ puuttuu katu", kayttaja3.getKatu());
       
    
    
    // Varmistetaan, ett‰ osoitteella on 3 k‰ytt‰j‰‰
    //Assert.assertEquals(
      //      "Tapahtumalla on liian v‰h‰n tapahtumarivej‰", 3,
        //    kayttajat.size());
   
    // ...

  }
  
  @Test
  public void testTilauksenLasku() {

    // Luodaan tilaus.
    Tilaus tilaus = new Tilaus();
    

    // Luodaan lasku ja asetetaan sen pakolliset tiedot.
    Lasku lasku = new Lasku();
    lasku.setErapaiva(new Date());

    // Yhdistet‰‰n lasku tilaukseen.
    tilaus.setLasku(lasku);

    // Tallennetaan huonevaraus ja siihen liittyv‰ lasku.
    tallennaEntiteetti(lasku);
    Integer tilausId = tallennaEntiteetti(tilaus);

    // Ladataan varaus tietokannasta ja varmistetaan ettei
    // varaukseen liittyv‰ lasku ole tyhj‰.
    tilaus = lataaEntiteetti(Tilaus.class, tilausId);
    Assert.assertNotNull(
        "Huonevaraukseen liittyv‰ lasku oli tyhj‰", tilaus
            .getLasku());
    

  }

  @Test
  public void testTilausriviTilaukselle() {
    // Luodaan tilaus ja asetetaan sen pakolliset tiedot.
    Tilaus tilaus = new Tilaus();
    

    // Luodaan tilausrivi ja asetetaan sen pakolliset
    // tiedot.
    Tilausrivi tilausrivi1 = new Tilausrivi();
    Tilausrivi tilausrivi2 = new Tilausrivi();
    Tilausrivi tilausrivi3 = new Tilausrivi();
      
    	// Lis‰t‰‰n tilausrivit tilaukselle
    tilaus.lisaaTilausrivi(tilausrivi1);
    tilaus.lisaaTilausrivi(tilausrivi2);
    tilaus.lisaaTilausrivi(tilausrivi3);
    

    // Tallennetaan entiteetit
    
    Integer tilausId = tallennaEntiteetti(tilaus);

    // Ladataan tilaus
    tilaus = lataaEntiteetti(Tilaus.class, tilausId);
    SortedSet<Tilausrivi> tilausrivit =
        tilaus.getTilausrivit();
    Iterator<Tilausrivi> tilausrivitIterator =
        tilausrivit.iterator();

    // Varmistetaan ett‰ tilauksella on kolme tilausrivi‰.
    Assert.assertEquals(
        "Tilauksella on liian v‰h‰n tilausrivej‰", 3,
        tilausrivit.size());

    // Varmistetaan, ett‰ tapahtumat palautetaan oikeassa
    // j‰rjestyksess‰.
    Assert
        .assertEquals(
            "Tilausrivi 3 oli v‰‰r‰ss‰ kohdassa tapahtumalistassa",
            tilausrivi3, tilausrivitIterator.next());
    
    Assert
    .assertEquals(
        "Tilausrivi 2 oli v‰‰r‰ss‰ kohdassa tapahtumalistassa",
        tilausrivi2, tilausrivitIterator.next());
    
    
    Assert
    .assertEquals(
        "Tilausrivi 1 oli v‰‰r‰ss‰ kohdassa tapahtumalistassa",
        tilausrivi1, tilausrivitIterator.next());

    
  }
  
    
  
  
  /**
   * T‰m‰ testi testaa tuotteen lis‰‰mist‰.
   */


   @Test
    public void lisaaTuote(){
	   Tuote tuote = new Tuote();
	   tuote.setAlv(22);
	   tuote.setHinta(222.5);
	   tuote.setKpl(2);
	   tuote.setNimi("housut");
	   
	   Assert.assertNotNull("Tuotteelta puuttuu m‰‰r‰", tuote.getKpl()); 
	   
   }
   
   
   
   @Test
   public void testAsiakkalleTilaus() {
	 // Luodaan tilaus ja asetetaan sen pakolliset tiedot.
	   Kayttaja asiakas5 = new Kayttaja("meik‰l‰inen", "maija","maijala"); 
	   Tilaus tilaus4 = new Tilaus();
	   Tilaus tilaus5 = new Tilaus();
	   
	   
	  
	   
	   //asetetaan asiakkaalle tilaus
	   
	   
	   //Tallennetaan entiteetit
	   
	   Integer asiakasId = tallennaEntiteetti(asiakas5);
	   //Integer tilausId1 = tallennaEntiteetti(tilaus4);
	   //Integer tilausId2 = tallennaEntiteetti(tilaus5);
	   asiakas5.lisaaTilaus(tilaus5);
	   asiakas5.lisaaTilaus(tilaus4);
	   tallennaEntiteetti(tilaus4);
	   tallennaEntiteetti(tilaus5);
	   //Ladataan asiakas
	   asiakas5 = lataaEntiteetti(Kayttaja.class,asiakasId);
	   //asiakas5.lisaaTilaus(lataaEntiteetti(Tilaus.class,tilausId1));
	   //asiakas5.lisaaTilaus(lataaEntiteetti(Tilaus.class,tilausId2));
	   
	   
	   SortedSet<Tilaus> tilaukset =
	        asiakas5.getTilaukset();
	   
	   
	   Assert.assertEquals("ei riitt‰v‰‰ m‰‰r‰‰ tilauksia", 2, tilaukset.size());
	   
   }
   
  
   /**
    * Testi varmentaa validin viitenumeron sis‰lt‰m‰n laskun
    * tallentamista.
    * 
    */
   
   @Test
   public void testLaskunViitteenValidointi() {
     Lasku lasku = new Lasku();
     lasku.setErapaiva(new Date());
     lasku.setViitenumero("12345678912345678917");
     tallennaEntiteetti(lasku);
   }
   
	    
	    
   /**
    * Testi varmentaa, ettei virheellinen viitenumero p‰‰see
    * l‰pi viitenumeron oikeellisuustarkistuksesta.
    * 
    */
   
   @Test(expected = InvalidStateException.class)
   public void testLaskunViitteenVirheenValidointi() {
     Lasku lasku = new Lasku();
     lasku.setErapaiva(new Date());
     // Viimeisen numeron pit‰isi olla seitsem‰n (7).
     lasku.setViitenumero("1234567891234567891");
     tallennaEntiteetti(lasku);
   }
	    
   /**
    * T‰m‰ testi testaa optimistisen luokituksen yhteydess‰
    * k‰ytett‰v‰‰ versionumeroa.
    */
   @Test
   public void testVersionumero() {
     // K‰ytt‰j‰ tallennetaan ensimm‰ist‰ kertaa = SQL-insert.
     Kayttaja kayttaja =
         new Kayttaja("Esko", "Esko", "Esimerkki");
     kayttaja.setPaikkakunta("jy");
     kayttaja.setPostinumero("41140");
     tallennaEntiteetti(kayttaja);

     // K‰ytt‰j‰ tallennetaan toista kertaa = SQL-update.
     kayttaja.setKayttajatunnus("esko.mottonen");
     Integer id = tallennaEntiteetti(kayttaja);

     // Ladataan k‰ytt‰j‰ tietokannasta.
     kayttaja = lataaEntiteetti(Kayttaja.class, id);
     Assert.assertEquals(
         "Versionumero v‰‰rin k‰ytt‰j‰entiteetiss‰", 1,
         kayttaja.getVersio());

   }
   


}