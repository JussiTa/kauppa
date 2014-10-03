package fi.harjoitustyo.verkkokauppa.palvelut.toteutus;



import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



public class Ostoskori implements Iterable<Ostos> {
	private int lkm =0;
	
	private int samaId;
	List<Ostos> ostokset = new ArrayList<Ostos>();
	
	
	 
	  public void lisaaOstos(Ostos ostos) {
		  lkm+=ostos.getKpl();
		  ostokset.add(ostos);
	  }
      //Poistetaan parametrina annetua vastaava tuote
      public void poistaOstos(Ostos ostos){
    	  ostokset.remove(ostos);
    	  lkm-= ostos.getKpl();
    	 		   
	 }
	  public Iterator<Ostos> iterator() {
		  
		  return ostokset.iterator();
	    
	  }
	  
	  public void poistaKaikki(){
		  ostokset.clear();
		  lkm=0;
		
	  }
	 
	  //Palauttaa korin sisällön
	 public List<Ostos> naytaKori(){
		  return ostokset;
		
		}
	  
	
	 public boolean samaTuote(int id){
		Iterator<Ostos> ostositer = iterator();
		Ostos ostos;
		while(ostositer.hasNext()) {
		   ostos=ostositer.next();
		   if( ostos.getId()==id){
		       samaId=ostos.getId();
			   return true;
		   }
				
		}
		return false;
	   }
     
	
	 public int haeSamanOstoksenId(){
		 return samaId;
	 	
	 }
	 
	 public void lisaaOstoksenLkm(int lkm, int id){
		 Iterator<Ostos> ostositer = iterator();
			Ostos ostos;
			this.lkm+=lkm;
			while(ostositer.hasNext()) {
			   ostos=ostositer.next();
			   if(ostos.getId()==id){
			       ostos.lisaaKpl(lkm);
			   }
		   }
	 }
	 
	 public void muutaOstoksenLkm(int lkm, int id){
		 Iterator<Ostos> ostositer = iterator();
			Ostos ostos;
			int alkup=0;
			
			while(ostositer.hasNext()) {
			   ostos=ostositer.next();
			   if(ostos.getId()==id){
				   alkup = ostos.getKpl();
				    this.lkm-=alkup;
				   
				   this.lkm+=lkm; 
			       ostos.setKpl(lkm);
			       if(lkm==0)
			    	  poistaOstos(ostos);
			   }
		   }
	 }
	 
	 public void poistaOstosId(int id){
			Iterator<Ostos> ostositer = iterator();
			Ostos ostos;
			int i=0;
			synchronized(ostositer)		
			{while(ostositer.hasNext()) {
			   ostos=ostositer.next();
			   if( ostos.getId()==id){
			       ostokset.remove(i);
			       this.lkm-=ostos.getKpl();
			       break;
			   }   
			 i++;  
		   } }
	 }
	 
	 public int haeOstoksenLkm(int id){
			Iterator<Ostos> ostositer = iterator();
			Ostos ostos;
			int lkm =0;
					
			while(ostositer.hasNext()) {
			   ostos=ostositer.next();
			   if( ostos.getId()==id)
			       lkm= ostos.getKpl();
				    
			 
		   }
			return lkm;	
	 }		
	 
	 
	 
		
  }
