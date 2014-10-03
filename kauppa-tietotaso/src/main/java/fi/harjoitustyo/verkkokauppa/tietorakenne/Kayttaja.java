package fi.harjoitustyo.verkkokauppa.tietorakenne;

import java.security.MessageDigest;
import java.util.SortedSet;
import java.util.TreeSet;


import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.FetchType;

import javax.persistence.OneToMany;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import javax.persistence.Table;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import sun.misc.BASE64Encoder;

/**
 * Luokka, joka mallintaa j‰rjestelm‰n k‰ytt‰j‰‰.
 * 
 * @author Jussi Isokangas
 * 
 */
@Entity
@Table(name = "kayttaja")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tyyppi", length = 15)
@DiscriminatorValue(value = "kayttaja")
public class Kayttaja extends Entiteetti {

  private static final Log log =
      LogFactory.getLog(Kayttaja.class);

  public static final int MAX_KAYTTAJATUNNUS_PITUUS = 35;
  public static final int MAX_NIMIOSA_PITUUS = 35;
  public static final int MAX_SALASANA_HASH_PITUUS = 28;
  public static final int MAX_KATU_PITUUS = 50;
  
  public static final int MAX_PAIKKAKUNTA_PITUUS = 50;
  public static final int MAX_POSTILOKERO_PITUUS = 10;
  public static final int MAX_POSTINUMERO_PITUUS = 5;
  public static final int MIN_POSTINUMERO_PITUUS = 5;
  private static final long serialVersionUID = 1L;

  @NotNull
  @Length(min = 1, max = MAX_NIMIOSA_PITUUS)
  @Column(name = "etunimi", length = MAX_NIMIOSA_PITUUS, nullable = false)
  private String etunimi;

  @NotNull
  @Length(min = 1, max = MAX_KAYTTAJATUNNUS_PITUUS)
  @Column(name = "tunnus", length = MAX_KAYTTAJATUNNUS_PITUUS, nullable = false)
  private String kayttajatunnus;

  
  @Length(min = 1, max = MAX_SALASANA_HASH_PITUUS)
  @Column(name = "salasana", length = MAX_SALASANA_HASH_PITUUS, nullable = true)
  private String salasanaHash;

  @NotNull
  @Length(min = 1, max = MAX_NIMIOSA_PITUUS)
  @Column(name = "sukunimi", length = MAX_NIMIOSA_PITUUS, nullable = false)
  private String sukunimi;
  
  
  @Length(min = 1, max = MAX_KATU_PITUUS)
  @Column(name = "katu", length = MAX_KATU_PITUUS, nullable = true)
  private String katu;
 
  
  
  @Length(min = 1, max = MAX_PAIKKAKUNTA_PITUUS)
  @Column(name = "paikkakunta", length = MAX_PAIKKAKUNTA_PITUUS, nullable = true)
  private String paikkakunta;

  @Length(min = 1, max = MAX_POSTILOKERO_PITUUS)
  @Column(name = "postilokero", length = MAX_POSTILOKERO_PITUUS, nullable = true)
  private String postilokero;

  @Length(min = MIN_POSTINUMERO_PITUUS, max = MAX_POSTINUMERO_PITUUS)
  @Column(name = "postinumero", length = MAX_POSTINUMERO_PITUUS, nullable = true)
  private String postinumero;
  
  
  
    
    @OneToMany(mappedBy = "kayttaja",fetch=FetchType.EAGER)
    
    @Sort(type = SortType.NATURAL)
    
    private SortedSet<Tilaus> tilaukset =
      new TreeSet<Tilaus>();

   

  /**
   * Peruskonstruktori, jota mm. Hibernate k‰ytt‰‰.
   */
  public Kayttaja() {
  }

  /**
   * Konstruktori, jonka kautta voi asettaa useita oleellisia
   * k‰ytt‰j‰n tietoja.
   * 
   * @param tunnus
   * @param etunimi
   * @param sukunimi
   */
  public Kayttaja(final String kayttajatunnus,
      final String etunimi, final String sukunimi) {
    this.kayttajatunnus = kayttajatunnus;
    this.etunimi = etunimi;
    this.sukunimi = sukunimi;
  }

  /**
   * @return the etunimi
   */
  public String getEtunimi() {
    return etunimi;
  }

  /**
   * @return the kayttajatunnus
   */
  public String getKayttajatunnus() {
    return kayttajatunnus;
  }

  /**
   * @return the osoite
   */

 
  
  

  /**
   * Palauttaa k‰ytt‰j‰n roolin.
   * 
   * @return
   */
  public Rooli getRooli() {
    return Rooli.ASIAKAS;
  }

  /**
   * Salasanaa ei voi tuoda takaisin sormenj‰lkimuodosta.
   */
  public String getSalasana() {
    return null;
  }

  /**
   * @return the salasana
   */
  public String getSalasanaHash() {
    return salasanaHash;
  }

  /**
   * @return the sukunimi
   */
  public String getSukunimi() {
    return sukunimi;
  }

  

  /**
   * Metodi, joka luo annetusta merkkijonosta SHA-algoritmia
   * k‰ytt‰en yksisuuntaisen sormenj‰ljen (hash).
   * 
   * @param salasanaSelvatekstina
   *          selv‰kielisen‰ tekstin‰.
   * @return Salasanan hash.
   */
  private String luoSormenjalki(
      final String salasanaSelvatekstina) {

    try {
      MessageDigest digest = MessageDigest.getInstance("SHA");
      digest.update(salasanaSelvatekstina.getBytes("UTF-8"));
      byte tavut[] = digest.digest();
      String sormenjalki =
          (new BASE64Encoder()).encode(tavut);
      return sormenjalki;
    } catch (Exception e) {
      log.fatal(e, e);
      throw new IllegalStateException(
          "Sormenj‰ljen luominen salasanasta ei onnistu, syy:"
              + e.getMessage());
    }
  }

  /**
   * @param etunimi
   *          the etunimi to set
   */
  public void setEtunimi(final String etunimi) {
    this.etunimi = etunimi;
  }

  /**
   * @param kayttajatunnus
   *          the kayttajatunnus to set
   */
  public void setKayttajatunnus(final String kayttajatunnus) {
    this.kayttajatunnus = kayttajatunnus;
  }

  
  /**
   * Asettaa salasana muuttamalla selv‰kielisess‰ muodossa
   * annetun tekstin SHA-sormenj‰ljeksi.
   * 
   * @param salasanaHash
   *          the salasana to set
   */
  public void setSalasana(final String selvakielinenSalasana) {
    String sormenjalki =
        luoSormenjalki(selvakielinenSalasana);
    setSalasanaHash(sormenjalki);
  }

  /**
   * Normaali salasanan setteri, joka ei luo sormenj‰lke‰ vaan
   * olettaa, ett‰ salasana on jo SHA-sormenj‰lkimuodossa.
   * 
   * @param salasana
   */
  public void setSalasanaHash(
      final String salasananSormenjalki) {
    salasanaHash = salasananSormenjalki;
  }

  /**
   * @param sukunimi
   *          the sukunimi to set
   */
  public void setSukunimi(final String sukunimi) {
    this.sukunimi = sukunimi;
  }

  
  

  /**
   * Varmentaa, ett‰ k‰ytt‰j‰n salasanan sormenj‰lki ja
   * parametrina annetun salasanan sormenj‰ljet vastaavat
   * toisiaan.
   * 
   * @return
   */
  public boolean varmennaSalasana(
      final String verrattavaSelvakielinenSalasana) {
    if ((verrattavaSelvakielinenSalasana == null)
        || (verrattavaSelvakielinenSalasana.length() == 0)) {
      return false;
    }

    // Tarkistetaan, ett‰ salasanojen sormenj‰ljet t‰sm‰‰v‰t.
    if (luoSormenjalki(verrattavaSelvakielinenSalasana)
        .equals(getSalasanaHash())) {
      return true;
    }

    return false;
  }
 
  public String getPaikkakunta() {
	    return paikkakunta;
	  }

	  /**
	   * @return the postilokero
	   */
	  public String getPostilokero() {
	    return postilokero;
	  }

	  /**
	   * @return the postinumero
	   */
	  public String getPostinumero() {
	    return postinumero;
	  }

	  /**
	   * @param katu
	   *          the katu to set
	   */
	  public void setKatu(final String katu) {
	    this.katu = katu;
	  }

	  public String getKatu() {
		    return katu;
		  }
	  /**
	   * @param paikkakunta
	   *          the paikkakunta to set
	   */
	  public void setPaikkakunta(final String paikkakunta) {
	    this.paikkakunta = paikkakunta;
	  }

	  /**
	   * @param postilokero
	   *          the postilokero to set
	   */
	  public void setPostilokero(final String postilokero) {
	    this.postilokero = postilokero;
	  }

	  /**
	   * @param postinumero
	   *          the postinumero to set
	   */
	  public void setPostinumero(final String postinumero) {
	    this.postinumero = postinumero;
	  }

	  public void lisaaTilaus(final Tilaus tilaus) {
		    getTilaukset().add(tilaus);
		    
		  }
    
    public SortedSet<Tilaus> getTilaukset(){
    	return tilaukset;
    	
    }

}
