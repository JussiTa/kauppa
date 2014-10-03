package fi.harjoitustyo.verkkokauppa.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;

import fi.harjoitustyo.verkkokauppa.tietorakenne.Entiteetti;

/**
 * Tietokantatestien yliluokka.
 * 
 * @author kuha
 * 
 */
public abstract class TietokantaTestCase {

  private static final Log log =
      LogFactory.getLog(TietokantaTestCase.class);

  private EntityManager entityManager;

  private EntityManagerFactory entityManagerFactory;

  /**
   * @return the entityManager
   */
  public EntityManager getEntityManager() {
    return entityManager;
  }

  /**
   * @return the entityManagerFactory
   */
  public EntityManagerFactory getEntityManagerFactory() {
    return entityManagerFactory;
  }

  /**
   * Tämä metodi luo Entity Managerin ennen jokaisen testin
   * alkua.
   * 
   */
  @Before
  public void luoEntityManager() {
    log.debug("Avataan tietokantayhteys!");
    entityManagerFactory =
        Persistence
            .createEntityManagerFactory("KAUPPA");
    entityManager =
        entityManagerFactory.createEntityManager();
  }

  /**
   * @param entityManager
   *          the entityManager to set
   */
  public void setEntityManager(
      final EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  /**
   * @param entityManagerFactory
   *          the entityManagerFactory to set
   */
  public void setEntityManagerFactory(
      final EntityManagerFactory entityManagerFactory) {
    this.entityManagerFactory = entityManagerFactory;
  }

  /**
   * Tämä metodi sulkee EntityManagerin jokaisen testin
   * jälkeen.
   * 
   */
  @After
  public void suljeEntityManager() {
    if (entityManager != null) {
      entityManager.close();
    } else {
      log.warn("EntityManager oli jostain syystä tyhjä "
          + "(null) testeissä.");
    }
  }

  /**
   * Lataa entiteetin tallennuspaikasta.
   * 
   * @param entiteetinLuokka
   *          Ladattavan entiteetin luokka
   * @param perusavain
   *          Ladattavan entiteetin tietokanta-avain
   * @return löytynyt entiteetti
   */
  protected <T extends Entiteetti> T lataaEntiteetti(
      final Class<T> entiteetinLuokka,
      final Integer perusavain) {
    EntityManager em = getEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    T entiteetti = em.find(entiteetinLuokka, perusavain);
    tx.commit();
    return entiteetti;
  }

  /**
   * Tallentaa entiteetin kantaan.
   * 
   * @param kayttaja
   * @return perusavain kyseiselle tallennetulle entiteetille
   */
  protected Integer tallennaEntiteetti(
      final Entiteetti entiteetti) {
    EntityManager em = getEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    em.persist(entiteetti);
    tx.commit();

    return entiteetti.getId();
  }
}
