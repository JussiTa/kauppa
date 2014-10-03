package fi.harjoitustyo.verkkokauppa.palvelut.toteutus;

public class Ostos {
	
	private int id;
	private int kpl;
	
	public Ostos(int id, int kpl){
		
		this. kpl =kpl;
		this.id = id;
	}
	
	public int getKpl(){
		return kpl;
	}
    
	public int getId(){
		return id;
	}
	public void setKpl(int kpl){
		this.kpl=kpl;
	}
	public void lisaaKpl(int kpl){
		this.kpl+=kpl;
	}
	
	public void setId(int id){
		this.id=id;
	}
}
