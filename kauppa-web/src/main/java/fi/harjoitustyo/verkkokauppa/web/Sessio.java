package fi.harjoitustyo.verkkokauppa.web;




import org.apache.wicket.Request;
import org.apache.wicket.security.WaspSession;
import fi.harjoitustyo.verkkokauppa.tietorakenne.Kayttaja;
import fi.harjoitustyo.verkkokauppa.palvelut.toteutus.Ostoskori;



public class Sessio extends WaspSession{
	
	private static final long serialVersionUID = 1L;
	private Ostoskori ostoskori ; 
	private Kayttaja kayttaja;
	
	 public Sessio(Request request, VerkkokauppaSovellus sovellus){
	    super(sovellus,request); 
	    ostoskori  = new Ostoskori();
		
	 }
	
	
	       
	 public Ostoskori haeOstoskori(){
	    return ostoskori;
	 }
	 
	 
	 public Kayttaja getKayttaja(){
		    return kayttaja;
		 }
	 
	 public void setKayttaja(Kayttaja kayttaja){
		 this.kayttaja = kayttaja;
		 }
	   
}    

	
	
	
	
	
	

