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
   * Luo konkreetin kayttajia k�sittelev�n DAO-olion ja
   * asettaa sen k�ytt��n testeiss� k�ytett�v�n
   * EntityManagerin.
   * 
   */
  @Before
  public void luoKayttajaDao() {
    // nimet�n sis�luokka (anonymous inner class)
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
    // Luodaan uusi k�ytt�j� ja tallennetaan se tietokantaan
    Kayttaja kayttaja =
        new Kayttaja("MattiMottonen", "Matti", "Mottonen");
    kayttajaDao.tallenna(kayttaja);
    Assert.assertNotNull(
        "Asiakkaan perusavain on tyhj� tallennuksen "
            + "j�lkeen, onko transaktio k�ynniss�?", kayttaja
            .getId());
    // Ladataan tallennettu k�ytt�j� tietokannasta
    log.fatal("Kayttaja id: " + kayttaja.getId());
    Assert.assertNotNull(
        "Asiakasta ei l�ytynyt tietokannasta", kayttajaDao
            .haePerusavaimella(kayttaja.getId()));
    // K�ytt�j�listauksen tulisi palauttaa yksi tulos
    Assert.assertEquals(
        "V��r� m��r� k�ytt�ji� l�ytyi tietokannasta", 1,
        kayttajaDao.haeKaikki().size());
    // Poistetaan asiakas tietokannasta
    kayttajaDao.poista(kayttaja);
    // Poiston j�lkeen asiakasta ei pit�isi l�yty�
    // tietokannasta.
    Assert.assertNull("Asiakasta ei poistettu tietokannasta",
        kayttajaDao.haePerusavaimella(kayttaja.getId()));

    // Vied��n transaktio loppuun
    tx.commit();
  }
}
