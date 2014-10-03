package fi.harjoitustyo.verkkokauppa.tietotaso;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fi.harjoitustyo.verkkokauppa.test.TietokantaTestCase;
import fi.harjoitustyo.verkkokauppa.tietotaso.toteutus.TuotettaEiOleRiittavastiException;
import fi.harjoitustyo.verkkokauppa.tietotaso.toteutus.TuoteDaoToteutus;


import fi.harjoitustyo.verkkokauppa.tietorakenne.Tuote;

public class TuoteDaoTest extends TietokantaTestCase {
	
	
	private TuoteDao tuoteDao;
	  private EntityTransaction tx;

	  /**
	   * Luo transaktion testien taustalle.
	   */
	  @Before
	  public void aloitaTransaktio() {
	    EntityManager em = getEntityManager();
	    tx = em.getTransaction();
	    tx.begin();
	  }

	  /**
	   * Luo transaktion testien taustalle.
	   */
	  @After
	  public void lopetaTransaktio() {
	    tx.rollback();
	  }

	  /**
	   * Luo konkreetin huoneita k‰sittelev‰n DAO-olion.
	   */
	  @Before
	  public void luoTuoteDao() {
	    TuoteDaoToteutus dao = new TuoteDaoToteutus();
	    dao.setEntityManager(getEntityManager());
	    tuoteDao = dao;
	  }

	  /**
	   * Testaa tuotteen hakemista tietokannasta 
	   * tuotetunnuksella
	   * 
	   * @throws VirheellinenTunnusTaiSalasanaException
	   */
	  @Test
	  public void testHaetuote(){
	      
	    Tuote tuote = new Tuote();
	    tuote.setNimi("farkut");
	    tuote.setTuoteryhma("vaatteet");
	    tuote.setHinta(34);
	    tuote.setKpl(2);
	    getEntityManager().persist(tuote);

	    Query q =
	        getEntityManager()
	            .createQuery(
	                "FROM Tuote WHERE nimi LIKE 'farkut'");
	    Assert.assertNotNull(
	        "Tuotetta ei ole tietokannassa", q
	            .getSingleResult());
	    
	    
	   Assert.assertEquals(
	            "Tuotetta virheellinen m‰‰r‰ kannassa", 2,
	           tuoteDao.haePerusavaimella(tuote.getId()).getKpl());
	    
	  
	  
	 Assert.assertEquals(			 
	            "Tuotetta virheellinen m‰‰r‰ kannassa", 2,
	           tuoteDao.haeTuote("farkut").getKpl());

	  }
	@Test(expected = TuotettaEiOleRiittavastiException.class)
	  public void testTuotettaEiOle()
	      throws TuotettaEiOleRiittavastiException {
		  Tuote tuote1 = new Tuote();
		    tuote1.setNimi("farkut");
		    tuote1.setTuoteryhma("tyokalut");
		    tuote1.setHinta(34);
		    tuote1.setKpl(5);
		   	
		    getEntityManager().persist(tuote1);
	    tuoteDao.lisaaTuoteTilaukseen(tuote1.getId(),11);

	  }
	
	@Test
	  public void testpaivitalkm(){
	     
		  Tuote tuote2 = new Tuote();
		    tuote2.setNimi("farkut2");
		    tuote2.setTuoteryhma("vaatteet");
		    tuote2.setHinta(3);
		    tuote2.setKpl(8);
		   	
		    getEntityManager().persist(tuote2);
		    
	       tuote2.setKpl(tuote2.getKpl()-2);
		   tuoteDao.paivita(tuote2);
		    
	    Assert.assertEquals(
	            "Tuotetta virheellinen m‰‰r‰ kannassa", 6,
	           tuoteDao.haePerusavaimella(tuote2.getId()).getKpl());

	  }


}
