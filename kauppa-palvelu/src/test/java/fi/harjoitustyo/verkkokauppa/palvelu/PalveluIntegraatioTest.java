
package fi.harjoitustyo.verkkokauppa.palvelu;



import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import fi.harjoitustyo.verkkokauppa.palvelut.toteutus.Ostos;
import fi.harjoitustyo.verkkokauppa.palvelut.toteutus.VerkkokauppapalveluToteutus;
import fi.harjoitustyo.verkkokauppa.palvelut.Verkkokauppapalvelu;
import fi.harjoitustyo.verkkokauppa.palvelut.TuotettaEiOleRiittavastiException;
import fi.harjoitustyo.verkkokauppa.tietorakenne.Kayttaja;
import fi.harjoitustyo.verkkokauppa.tietorakenne.Tuote;
import fi.harjoitustyo.verkkokauppa.palvelut.toteutus.Ostoskori;

import fi.harjoitustyo.verkkokauppa.tietorakenne.Tilaus;

import fi.harjoitustyo.verkkokauppa.tietotaso.TuoteDao;
import fi.harjoitustyo.verkkokauppa.tietotaso.TilausDao;

import fi.harjoitustyo.verkkokauppa.tietotaso.toteutus.TuoteDaoToteutus;
import fi.harjoitustyo.verkkokauppa.tietotaso.toteutus.TilausDaoToteutus;

/**
 * Testi, joka pyrkii varmentamaan sovelluskomponenttien
 * saumattoman yhteisty�n.
 * 
 * @author Jussi Isokangas
 * 
 */
@ContextConfiguration(locations = { "classpath:spring-integraatiotestit.xml" })
public class PalveluIntegraatioTest extends
    AbstractTransactionalJUnit4SpringContextTests {

  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  @Qualifier("verkkokauppaPalvelu")
  private Verkkokauppapalvelu verkkokauppaPalvelu;

  /**
   * Testi, joka varmentaa ett� palvelukomponentin l�pi
   * voidaan hakea tuote.
   * 
   */
 
 
  @Test
  public void testhaeTuoteNimella() {
	 Tuote tuote = new Tuote ("py�r�", 3);
	 tuote.setTuoteryhma("py�raily");
	 entityManager.persist(tuote);
     tuote = verkkokauppaPalvelu.haeTuoteNimella("py�r�");
    
    Assert.assertNotNull("Ei ollut totetta", tuote);
    
    
  }


  /**
   * Testi, joka varmentaa ett� palvelukomponentin l�pi
   * lis�t� tuotteita.
 * @throws TuotettaEiOleRiittavastiException 
   * 
   * 
   * 
   */

 
  @Test(expected = TuotettaEiOleRiittavastiException.class)
  public void testTuotettaeioleriittavasti()
      throws TuotettaEiOleRiittavastiException {
    // Tehd��n uusi tilaus
	  Tuote tuote1 = new Tuote("mopo",1);
	    tuote1.setHinta(5);
	    tuote1.setTuoteryhma("ajoneuvot");
	    tuote1.setAlv(23);
	    entityManager.persist(tuote1);  
	
	  Ostoskori ostoskori= new Ostoskori();
    
    
    ostoskori.lisaaOstos(new Ostos(tuote1.getId(),3));
    
    
    
    verkkokauppaPalvelu.teeTilaus(ostoskori);
    
  }

  /**
   * Esimerkki manuaalisesti komponenttien linkitt�misest�.
   * 
   */
  @Test
  public void testManuaalinenKomponenttilinkitys() {
    VerkkokauppapalveluToteutus palvelu =
        new VerkkokauppapalveluToteutus();
    TuoteDao tuoteDao = new TuoteDaoToteutus();
    TilausDao tilausDao = new TilausDaoToteutus();
    palvelu.setTuoteDao(tuoteDao);
    palvelu.setTilausDao(tilausDao);
  }

  /**
   * Testi, joka tekee huonevarauksen l�pi kaikkien
   * sovelluskerrosten, k�ytt�liittym�� lukuun ottamatta.
   * 
   */
  @Test
  public void testlisaaTuotetilaukseen() throws Exception {
    
    // Luodaan uusi tuote ja k�ytt�j�
	Kayttaja kayttaja = new Kayttaja("ss","simo","seppo"); 
	
    Tuote tuote2 = new Tuote("maila",5);
    tuote2.setHinta(5);
    tuote2.setTuoteryhma("j��kiekko");
    tuote2.setAlv(23);
    entityManager.persist(tuote2);
    entityManager.persist(kayttaja);
    String tunnus ="ss";
    Query q =
        entityManager
            .createQuery("SELECT k FROM Kayttaja k "
                + " WHERE lower(k.kayttajatunnus) like lower(:tunnus)");
    q.setParameter("tunnus", tunnus);
    kayttaja = (Kayttaja) q.getSingleResult();

    // Tehd��n uusi tilaus
    Ostoskori ostoskori= new Ostoskori();
    ostoskori.lisaaOstos(new Ostos(tuote2.getId(),5));
    Tilaus tilaus = new Tilaus();
    verkkokauppaPalvelu.teeTilaus(ostoskori);
    verkkokauppaPalvelu.vahvistaTilaus(ostoskori,tilaus, kayttaja);
    
    Assert.assertEquals(
        "Tilaukseen liittyy v��r� m��r� tilausrivej�.", 1,
        tilaus.getTilausrivit().size());
  
  

  
  }

}
