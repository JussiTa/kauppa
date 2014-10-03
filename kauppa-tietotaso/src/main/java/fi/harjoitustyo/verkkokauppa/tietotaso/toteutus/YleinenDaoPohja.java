package fi.harjoitustyo.verkkokauppa.tietotaso.toteutus;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fi.harjoitustyo.verkkokauppa.tietorakenne.Entiteetti;
import fi.harjoitustyo.verkkokauppa.tietotaso.YleinenDao;

/**
 * Yhteinen DAO-suunnittelumallin mukaisten luokkien
 * yliluokka.
 * 
 * @author kuha
 * 
 */
public abstract class YleinenDaoPohja<E extends Entiteetti>
    implements YleinenDao<E> {

  private static final Log log =
      LogFactory.getLog(YleinenDaoPohja.class);

  private Class<E> entiteetinLuokka;

  @PersistenceContext
  private EntityManager entityManager;

  /**
   * Konstruktori, joka asettaan k‰sitelt‰v‰n luokan tyypin.
   * 
   * @param entiteetinLuokka
   */
  public YleinenDaoPohja(final Class<E> entiteetinLuokka) {
    this.entiteetinLuokka = entiteetinLuokka;
  }

  /**
   * @return the entiteetinLuokka
   */
  public Class<E> getEntiteetinLuokka() {
    return entiteetinLuokka;
  }

  /**
   * @return the entityManager
   */
  public EntityManager getEntityManager() {
    return entityManager;
  }

  /**
   * Hakee kaikki oliot tietokannasta, jotka ovat tietty‰
   * tyyppi‰.
   * 
   * @return Kokoelma kyseisi‰ olioita.
   */
  @SuppressWarnings("unchecked")
  public Collection<E> haeKaikki() {
    Query q =
        entityManager.createQuery("SELECT o FROM "
            + entiteetinLuokka.getName() + " AS o");
    List<E> tulokset = q.getResultList();
    return tulokset;
  }

  /**
   * Hakee olioita tietokannasta, joilla on tietty arvo
   * perusavaimella.
   * 
   * @param pa
   *          Haettavan olion perusavain tietokannassa.
   * @return Kyseinen olio ladattuna tietokannasta.
   */
  public E haePerusavaimella(final Integer id) {
    if (log.isDebugEnabled()) {
      log.debug("Haetaan entiteetti‰, jonka luokka on ["
          + entiteetinLuokka + "] ja id [" + id + "].");
    }
    return entityManager.find(entiteetinLuokka, id);
  }

  /**
   * Poistaa olion tiedot tietokannasta.
   * 
   * @param entiteetti
   *          Poistettava entiteetti.
   */
  public void poista(final E entiteetti) {
    entityManager.remove(entiteetti);
  }

  /**
   * @param entiteetinLuokka
   *          the entiteetinLuokka to set
   */
  public void setEntiteetinLuokka(
      final Class<E> entiteetinLuokka) {
    this.entiteetinLuokka = entiteetinLuokka;
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
   * Tallentaa kyseisen entiteetin tiedot tietokantaan.
   * 
   * K‰ytt‰‰ EntityManagerin merge-metodia, jotta voidaan
   * tallentaa sek‰ uusi olio (ei perusavainta) sek‰ p‰ivitt‰‰
   * aiemmin tietokannassa ollut olio (perusavaimella on
   * arvo).
   * 
   * @param Tallennettava
   *          entiteetti.
   */
  public void tallenna(final E entiteetti) {
    entityManager.persist(entiteetti);
   
  }
  
  public void paivita(final E entiteetti){
	  entityManager.merge(entiteetti);
  }
  
}
