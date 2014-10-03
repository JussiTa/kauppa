package fi.harjoitustyo.verkkokauppa.tietotaso;

import javax.persistence.EntityTransaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fi.harjoitustyo.verkkokauppa.test.TietokantaTestCase;
import fi.harjoitustyo.verkkokauppa.tietorakenne.Kayttaja;
import fi.harjoitustyo.verkkokauppa.tietotaso.toteutus.YleinenDaoPohja;

/**
 * Yleinen DAO testiluokkien yliluokka.
 * 
 * @author kuha
 * 
 */
public class YleinenDaoTest extends TietokantaTestCase {

  private static final Log log =
      LogFactory.getLog(YleinenDaoTest.class);

  private YleinenDaoPohja<Kayttaja> kayttajaDao;

  /**
   * Luo konkreetin kayttajia käsittelevän DAO-olion ja
   * asettaa sen käyttöön testeissä käytettävän
   * EntityManagerin.
   * 
   */
  @Before
  public void luoKayttajaDao() {
    // nimetön sisäluokka (anonymous inner class)
    YleinenDaoPohja<Kayttaja> dao =
        new YleinenDaoPohja<Kayttaja>(Kayttaja.class) {
        };

    dao.setEntityManager(getEntityManager());
    kayttajaDao = dao;
  }

  @Test
  public void testPerusoperaatiot() throws Exception {

    // // Aloitetaan uusi transaktio
    EntityTransaction tx =
        getEntityManager().getTransaction();
    tx.begin();
    // Luodaan uusi käyttäjä ja tallennetaan se tietokantaan
    Kayttaja kayttaja =
        new Kayttaja("MattiMottonen", "Matti", "Mottonen");
    kayttajaDao.tallenna(kayttaja);
    Assert.assertNotNull(
        "Asiakkaan perusavain on tyhjä tallennuksen "
            + "jälkeen, onko transaktio käynnissä?", kayttaja
            .getId());
    // Ladataan tallennettu käyttäjä tietokannasta
    log.fatal("Kayttaja id: " + kayttaja.getId());
    Assert.assertNotNull(
        "Asiakasta ei löytynyt tietokannasta", kayttajaDao
            .haePerusavaimella(kayttaja.getId()));
    // Kåyttäjälistauksen tulisi palauttaa yksi tulos
    Assert.assertEquals(
        "Väärä määrä käyttäjiä löytyi tietokannasta", 1,
        kayttajaDao.haeKaikki().size());
    // Poistetaan asiakas tietokannasta
    kayttajaDao.poista(kayttaja);
    // Poiston jälkeen asiakasta ei pitäisi löytyä
    // tietokannasta.
    Assert.assertNull("Asiakasta ei poistettu tietokannasta",
        kayttajaDao.haePerusavaimella(kayttaja.getId()));

    // Viedään transaktio loppuun
    tx.commit();
  }
}
