package fi.harjoitustyo.verkkokauppa.tietotaso;



	import java.math.BigDecimal;
	import java.util.Date;
	import java.util.Iterator;
	import java.util.List;

	import javax.persistence.EntityManager;
	import javax.persistence.EntityTransaction;
	import javax.persistence.Query;

	import org.apache.commons.logging.Log;
	import org.apache.commons.logging.LogFactory;
	import org.junit.After;
	import org.junit.Assert;
	import org.junit.Before;
	import org.junit.Test;

	import fi.harjoitustyo.verkkokauppa.test.TietokantaTestCase;
	import fi.harjoitustyo.verkkokauppa.tietorakenne.Kayttaja;
	import fi.harjoitustyo.verkkokauppa.tietorakenne.Lasku;
	import fi.harjoitustyo.verkkokauppa.tietorakenne.Tapahtumarivi;

	/**
	 * * Testi, jolla testataan erilaisia hakuja ja niiden
	 * toimivuuksia suhteessa sovellusalamalliluokkiin.
	 * 
	 * @author kuha
	 * 
	 */
	public class HakuTest extends TietokantaTestCase {

	  @SuppressWarnings("unused")
	  private static final Log log =
	      LogFactory.getLog(TietokantaTestCase.class);

	  private EntityTransaction tx;

	  @Before
	  public void aloitaTx() {
	    tx = getEntityManager().getTransaction();
	    tx.begin();
	  }

	  @After
	  public void lopetaTx() {
	    if (tx.getRollbackOnly()) {
	      tx.rollback();
	    } else {
	      tx.commit();
	    }
	  }

	  /**
	   * Haku, jolla haetaan k‰ytt‰j‰t k‰ytt‰j‰nimen mukaan
	   * j‰rjesteltyn‰.
	   */
	  @SuppressWarnings("unchecked")
	  @Test
	  public void testJarjestely() {

	    Kayttaja k1 =
	        new Kayttaja("Valtteri", "Valtteri", "Virtanen");
	    Kayttaja k2 = new Kayttaja("Esko", "Esko", "Esimerkki");
	    Kayttaja k3 =
	        new Kayttaja("Mikko", "Mikki", "Meikalainen");
	    EntityManager em = getEntityManager();
	    em.persist(k1);
	    em.persist(k2);
	    em.persist(k3);
	    Query q =
	        em.createQuery("SELECT k FROM Kayttaja AS k "
	            + "ORDER BY k.kayttajatunnus");
	    List<Kayttaja> tulokset = q.getResultList();
	    Assert.assertEquals(
	        "Hakutuloksia palautettiin virheellinen m‰‰r‰", 3,
	        tulokset.size());

	    Iterator<Kayttaja> kayttajat = tulokset.iterator();
	    Assert.assertEquals(
	        "K‰ytt‰j‰ 2 (Esko) oli v‰‰r‰ss‰ paikassa", k2,
	        kayttajat.next());
	    Assert.assertEquals(
	        "K‰ytt‰j‰ 3 (Mikko) oli v‰‰r‰ss‰ paikassa", k3,
	        kayttajat.next());
	    Assert.assertEquals(
	        "K‰ytt‰j‰ 1 (Valtteri) oli v‰‰r‰ss‰ paikassa", k1,
	        kayttajat.next());
	  }

	  /**
	   * Hakee laskut, joissa tapahtumiin liittyv‰ verotonSumma on
	   * tietyn v‰lin sis‰ll‰.
	   * 
	   */
	  @Test
	  @SuppressWarnings("unchecked")
	  public void testLaskunHakuVerottomanSummanPerusteella() {

	    Lasku l1 = new Lasku();
	    l1.setErapaiva(new Date());
	    l1.lisaaTapahtuma(new Tapahtumarivi("1.23"));
	    l1.lisaaTapahtuma(new Tapahtumarivi("2.15"));

	    Lasku l2 = new Lasku();
	    l2.setErapaiva(new Date());
	    l2.lisaaTapahtuma(new Tapahtumarivi("1.23"));
	    l2.lisaaTapahtuma(new Tapahtumarivi("3.15"));

	    Lasku l3 = new Lasku();
	    l3.setErapaiva(new Date());
	    l3.lisaaTapahtuma(new Tapahtumarivi("3.23"));
	    l3.lisaaTapahtuma(new Tapahtumarivi("3.15"));

	    EntityManager em = getEntityManager();
	    em.persist(l1);
	    em.persist(l2);
	    em.persist(l3);

	    Query q =
	        em.createQuery("SELECT l FROM Lasku AS l "
	            + "LEFT JOIN l.tapahtumat AS t "
	            + "WHERE t.verotonSumma >= :alaraja AND "
	            + "t.verotonSumma <= :ylaraja");

	    q.setParameter("alaraja", new BigDecimal("2.00"));
	    q.setParameter("ylaraja", new BigDecimal("3.00"));

	    List<Lasku> tulokset = q.getResultList();

	    Assert.assertEquals(
	        "Hakutuloksia palautettiin virheellinen m‰‰r‰", 1,
	        tulokset.size());

	    Assert.assertEquals("Haku palautti v‰‰r‰n tulokset", l1,
	        tulokset.iterator().next());

	  }

	  /**
	   * Haku, jolla haetaan tapahtumaan liittyv‰t laskut.
	   */
	  @SuppressWarnings("unchecked")
	  @Test
	  public void testLaskunTapahtumatGroupByKappalemaara() {

	    Lasku l = new Lasku();
	    l.setErapaiva(new Date());
	    Tapahtumarivi t1 = new Tapahtumarivi("1.00");
	    t1.setKappalemaara(1);
	    Tapahtumarivi t2 = new Tapahtumarivi("2.50");
	    t2.setKappalemaara(2);
	    Tapahtumarivi t3 = new Tapahtumarivi("1.25");
	    t3.setKappalemaara(2);
	    Tapahtumarivi t4 = new Tapahtumarivi("1.00");
	    t4.setKappalemaara(3);
	    Tapahtumarivi t5 = new Tapahtumarivi("2.00");
	    t5.setKappalemaara(3);
	    Tapahtumarivi t6 = new Tapahtumarivi("3.00");
	    t6.setKappalemaara(3);

	    l.lisaaTapahtuma(t1);
	    l.lisaaTapahtuma(t2);
	    l.lisaaTapahtuma(t3);
	    l.lisaaTapahtuma(t4);
	    l.lisaaTapahtuma(t5);
	    l.lisaaTapahtuma(t6);

	    EntityManager em = getEntityManager();
	    em.persist(l);
	    Query q =
	        em.createQuery("SELECT t.kappalemaara, "
	            + " sum(t.verotonSumma) FROM Tapahtumarivi AS t "
	            + " GROUP BY t.kappalemaara "
	            + " HAVING t.kappalemaara > 1"
	            + " ORDER BY t.kappalemaara ");
	    List<Object[]> tulokset = q.getResultList();
	    Assert.assertEquals("Tuloksia oli virheellinen m‰‰r‰", 2,
	        tulokset.size());

	    Iterator<Object[]> i = tulokset.iterator();

	    // Tarkistetaan tuloksien arvot
	    Object[] tulos1 = i.next();
	    Assert.assertEquals(
	        "Ensimm‰isen tuloksen kappalem‰‰r‰ oli virheellinen",
	        2, tulos1[0]);
	    Assert.assertEquals(
	        "Ensimm‰isen tuloksen summa oli virheellinen",
	        new BigDecimal("3.75"), tulos1[1]);

	    Object[] tulos2 = i.next();
	    Assert.assertEquals(
	        "Ensimm‰isen tuloksen kappalem‰‰r‰ oli virheellinen",
	        3, tulos2[0]);
	    Assert.assertEquals(
	        "Ensimm‰isen tuloksen summa oli virheellinen",
	        new BigDecimal("6.00"), tulos2[1]);

	  }

	  /**
	   * Haku, jolla haetaan tapahtumaan liittyv‰t laskut.
	   */
	  @SuppressWarnings("unchecked")
	  @Test
	  public void testLaskunTapahtumatHaku() {

	    Lasku l = new Lasku();
	    l.setErapaiva(new Date());
	    l.lisaaTapahtuma(new Tapahtumarivi("1.23"));
	    l.lisaaTapahtuma(new Tapahtumarivi("2.15"));
	    l.lisaaTapahtuma(new Tapahtumarivi("3.75"));
	    Assert.assertEquals("Tapahtumia on virheellinen m‰‰r‰",
	        3, l.getTapahtumat().size());

	    EntityManager em = getEntityManager();
	    em.persist(l);
	    Query q =
	        em.createQuery("SELECT l.tapahtumat FROM Lasku AS l");
	    List<Tapahtumarivi> tulokset = q.getResultList();
	    Assert.assertNotNull("Tuloksena tuli tyhj‰ olio",
	        tulokset);
	    Assert.assertEquals(
	        "Hakutuloksia palautettiin virheellinen m‰‰r‰", 3,
	        tulokset.size());
	  }

	  
	  
	}


