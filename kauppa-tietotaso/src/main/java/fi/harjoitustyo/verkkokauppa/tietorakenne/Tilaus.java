package fi.harjoitustyo.verkkokauppa.tietorakenne;



import java.util.Calendar;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;





import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import javax.persistence.JoinColumn;

import javax.persistence.OneToMany;



import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

import org.hibernate.validator.NotNull;



/**
 * 
 * 
 * @author Jussi Isokangas
 * 
 */
@Entity
@Table(name = "tilaus")


public class Tilaus extends Entiteetti implements Comparable<Tilaus> {

  /**
	 * 
	 */
 private static final long serialVersionUID = 1L;

 @NotNull
 @Column(name = "pvm", nullable = true)
 private Date aikaleima;

 

 //Tilauksiin liittyv‰ k‰ytt‰j‰
 
  
  
   // kaksisuuntainen yhdest‰ yhteen -suhde
  @OneToOne(optional = true)
  @JoinColumn(name = "lasku_id", nullable = true, unique = true)
  @ForeignKey(name = "viiteavain_tilaus_lasku", inverseName = "viiteavain_lasku_tilaus")
  private Lasku lasku;
  
//Kaksisuuntainen yhdest‰ moneen -suhde.
  @OneToMany(mappedBy = "tilaus", cascade = CascadeType.ALL)
  @Sort(type = SortType.NATURAL)
  private SortedSet<Tilausrivi> tilausrivit =
      new TreeSet<Tilausrivi>();
  
  @ManyToOne
  @JoinColumn(name = "kayttaja_id", nullable = true)
  @ForeignKey(name = "viiteavain_kayttaja_tilaus", inverseName = "viiteavain_tilaus_kayttaja")
  private Kayttaja kayttaja;
  
  
  public Tilaus(){
	  aikaleima = Calendar.getInstance().getTime();
  }
  
  

  public Kayttaja getAsiakas(){
	  return kayttaja;
  }
 
  /**
   * Asettaa sek‰ laskun ett‰ varauksen kaksisuuntaisen
   * suhteen viitteet kuntoon molempiin p‰ihin.
   * 
   * @param lasku
   */
  
  
  
  

  public void asetaLasku(final Lasku lasku) {
	  this.lasku = lasku;
	  lasku.setTilaus(this);
  }

  
  /**
   * @return the lasku
   */
  public Lasku getLasku() {
    return lasku;
  }

  public void setAikaleima(final Date aikaleima) {
	    this.aikaleima = aikaleima;
	  }
  
 
  /**
   * @param lasku
   *          the lasku to set
   */
  public void setLasku(final Lasku lasku) {
    this.lasku = lasku;
  }

  public Date getAikaleima(){
	  return aikaleima;
  }
  
  public void lisaaTilausrivi(final Tilausrivi tilausrivi) {
	    getTilausrivit().add(tilausrivi);
	    tilausrivi.setTilaus(this);
	  }
  
  public SortedSet<Tilausrivi> getTilausrivit() {
	    return tilausrivit;
	  }


  public void setAsiakas(Kayttaja kayttaja) {
	this.kayttaja=kayttaja;
  }

  public int compareTo(final Tilaus t) {
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
	    if ((getId() != null) && (t.getId() != null)) {
		      return getId().compareTo(t.getId());
		    }

	    return -1;

  }	    

}



  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  

