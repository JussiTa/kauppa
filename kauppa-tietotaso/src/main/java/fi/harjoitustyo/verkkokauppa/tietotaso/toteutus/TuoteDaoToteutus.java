package fi.harjoitustyo.verkkokauppa.tietotaso.toteutus;



import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import fi.harjoitustyo.verkkokauppa.tietotaso.TuoteDao;
import fi.harjoitustyo.verkkokauppa.tietorakenne.Tuote;
import fi.harjoitustyo.verkkokauppa.tietotaso.toteutus.TuotettaEiOleRiittavastiException;


public class TuoteDaoToteutus extends YleinenDaoPohja<Tuote> implements TuoteDao {
	
	public TuoteDaoToteutus(){
		super(Tuote.class);
	}
	
		
	 public Tuote haeTuote(String hakusana){
		 EntityManager em = getEntityManager();
		    Query q =
		    	em
		            .createQuery ("SELECT t FROM Tuote t WHERE t.nimi = :sana");
		     q.setParameter("sana", hakusana);         
		 
		      Tuote tuote = (Tuote)q.getSingleResult();
		      
		      return tuote;
		   
		 
		 
	 }
	 @SuppressWarnings("unchecked")
	  public List<Tuote> haeTuoteTuoteryhmanMukaan(final String tuoteryhma){
		  EntityManager em = getEntityManager();
		    Query q =
		    	em
		        .createQuery ("SELECT t FROM Tuote t WHERE t.tuoteryhma = :tuoteryhma");
			     q.setParameter("tuoteryhma", tuoteryhma);    
		      
		  
		  		  	  
		     List<Tuote> tulokset = q.getResultList();
		     return tulokset;
	  }
	  
	  public void lisaaTuoteTilaukseen(int tuoteId, int kappalemaara)
	  	  throws TuotettaEiOleRiittavastiException{
		 
		  EntityManager em = getEntityManager();
		    Query q = em.createQuery("FROM Tuote AS t WHERE t.id = :Id");
		     q.setParameter("Id", tuoteId);  
		     try{
		      Tuote tuote  =
			        (Tuote) q.getSingleResult();
			    if (tuote.getKpl() < kappalemaara){ 
			    	throw new TuotettaEiOleRiittavastiException ();
			    }
			     
			}catch (javax.persistence.NoResultException e){
				 
			    }
			    
					    
	  }



	/*public void paivitaLkmTuotteelle(final Integer id, int lkm) {
		EntityManager em = getEntityManager();
		 	Query q = em.createQuery("SELECT t FROM Tuote t WHERE id = :indeksi");
		  		q.setParameter("indeksi", id);
		  		Tuote tuote;
				tuote = (Tuote)q.getSingleResult();
				int lkm2 = tuote.getKpl();
				
				lkm2 -= lkm;
		  		Query q2 = em.createQuery("UPDATE Tuote SET kpl = " +lkm2+ " WHERE id = :indeksi");
		  		q2.setParameter("indeksi", id);
		  		q2.executeUpdate();
	}
*/
}


