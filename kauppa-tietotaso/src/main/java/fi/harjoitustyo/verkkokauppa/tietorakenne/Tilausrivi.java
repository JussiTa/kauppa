package fi.harjoitustyo.verkkokauppa.tietorakenne;

import java.util.Calendar;
import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;



import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

import org.hibernate.validator.NotNull;

/**
 * 
 * 
 * @author Jussi Isokangas
 * 
 */

@Entity
@Table(name = "tilausrivi")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

public class Tilausrivi extends Entiteetti
	implements Comparable<Tilausrivi> {


 /**
	 * 
	 */


	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Column(name= "pvm", nullable = true)
	private Date aikaleima;
	
	
	@Column(name= "kpl", nullable = true)
	private int kpl;	
	
	
	@Column(name= "tuotenimi", nullable = true)
	private String tuotenimi;

	
	
	
	@ManyToOne
	  @JoinColumn(name = "tilaus_id")
	  @ForeignKey(name = "viiteavain_tilausrivi_tilaus", inverseName = "viiteavain_tilaus_tlausrivi")
	  private Tilaus tilaus;

	public Tilausrivi(){
		 aikaleima = Calendar.getInstance().getTime();
		 
	}
	public Tilausrivi(int kpl, String tuotenimi){
		 aikaleima = Calendar.getInstance().getTime();
		 //this.tuoteid =id;
		 this.kpl = kpl;
	  }
	
	public Date getAikaleima(){
		return aikaleima;
	}
   
  

   
   
   public void setKappalemaara(int kpl){
	   this.kpl=kpl;
   }

   public int getKappalemaara(){
	   return kpl;
   }
  
  public void setTilaus(Tilaus tilaus){
	  this.tilaus = tilaus;	  
  }
  
  public Tilaus getTilaus(){
	  return tilaus;
	  
  }
  
  public void setTuoteNimi(String nimi){
	  this.tuotenimi = nimi;;
  }
  
  public String getTuoteNimi(){
	  return tuotenimi;
  }
  
  public int compareTo(final Tilausrivi t) {
	    // Mik‰li aikaleima on tyhj‰ tulee t‰m‰ rivi loppuun.
	    if (aikaleima == null) {
	      return 1;
	    }
	    // Mik‰li toisen tapahtumarivin aikaleima on tyhj‰ tulee
	    // kyseinen rivi loppuun.
	    if (t.getAikaleima() == null) {
	      return -1;
	    }

	    // K‰ytet‰‰n vertailuun Daten sis‰ist‰ vertailua.
	    int i = (aikaleima.compareTo(t.getAikaleima()));
	    if (i != 0) {
	      return i;
	    }

	    // Mik‰li aikaleimojen vertailu tuottaa arovn nolla,
	    // pyrit‰‰n vertailu tekem‰‰n peruasavaimien vertailulla.
	    // Samalla perusavaimella olevat rivit saavat korvata
	    // toisensa.
	    if ((getId() != null) && (t.getId() != null)) {
	      return getId().compareTo(t.getId());
	    }

	    

	    // Yritet‰‰n tehd‰ vertailu viel‰ kappalem‰‰r‰n mukaan,
	    // mik‰li summatkin ovat samat
	    if (getKappalemaara() != t.getKappalemaara()) {
	      return new Integer(getKappalemaara()).compareTo(t
	          .getKappalemaara());
	    }

	    // Luovutetaan ;) ja palautetaan jokin vertailuarvo, t‰m‰
	    // kuitenkin saattaa aiheuttaa rivien siirtymisen
	    // laskun sis‰ll‰.
	    return -1;

	  }

  
  
  
  
   
}   