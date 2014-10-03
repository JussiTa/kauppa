package fi.harjoitustyo.verkkokauppa.tietorakenne;

import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import fi.harjoitustyo.verkkokauppa.tietorakenne.validaatio.Viitenumero;



/**
 * Varaukseen liittyv‰ lasku.
 * 
 * @author Jussi Isokangas
 * 
 */
@Entity
@Table(name = "lasku")
public class Lasku extends Entiteetti {

  public static final int MAX_VIITENUMERO_PITUUS = 20;
  public static final int MIN_VIITENUMERO_PITUUS = 4;
  private static final long serialVersionUID = 1L;

  @NotNull
  @Column(name = "erapaiva", nullable = false)
  private Date erapaiva;

  @Column(name = "suorituspaiva", nullable = true)
  private Date suorituspaiva;

  // Kaksisuuntainen yhdest‰ moneen -suhde.
  @OneToMany(mappedBy = "lasku", cascade = CascadeType.ALL)
  @Sort(type = SortType.NATURAL)
  private SortedSet<Tapahtumarivi> tapahtumat =
      new TreeSet<Tapahtumarivi>();

  // Kaksisuuntainen yhdest‰ yhteen -suhde.
  @OneToOne(mappedBy = "lasku")
  private Tilaus tilaus;

  @Viitenumero
  @Length(min = MIN_VIITENUMERO_PITUUS, max = MAX_VIITENUMERO_PITUUS)
  @Column(name = "viitenumero", length = MAX_VIITENUMERO_PITUUS, nullable = true)
  private String viitenumero;

  /**
   * Asettaa sek‰ laskun ett‰ varauksen kaksisuuntaisen
   * suhteen viitteet kuntoon molempiin p‰ihin.
   * 
   * @param lasku
   */
  public void asetaTilaus(final Tilaus tilaus) {
    this.tilaus = tilaus;
    tilaus.setLasku(this);
  }

  /**
   * @return the erapaiva
   */
  public Date getErapaiva() {
    return erapaiva;
  }

  /**
   * @return the suorituspaiva
   */
  public Date getSuorituspaiva() {
    return suorituspaiva;
  }

  /**
   * @return the tapahtumat
   */
  public SortedSet<Tapahtumarivi> getTapahtumat() {
    return tapahtumat;
  }

  /**
   * @return the varaus
   */
  public Tilaus getTilaus() {
    return tilaus;
  }

  /**
   * @return the viitenumero
   */
  public String getViitenumero() {
    return viitenumero;
  }

  /**
   * Lis‰‰ uuden tapahtuman tapahtumakokoelmaan.
   * 
   * @param tapahtuma
   */
  public void lisaaTapahtuma(final Tapahtumarivi tapahtuma) {
    getTapahtumat().add(tapahtuma);
    tapahtuma.setLasku(this);
  }

  /**
   * @param erapaiva
   *          the erapaiva to set
   */
  public void setErapaiva(final Date erapaiva) {
    this.erapaiva = erapaiva;
  }

  /**
   * @param suorituspaiva
   *          the suorituspaiva to set
   */
  public void setSuorituspaiva(final Date suorituspaiva) {
    this.suorituspaiva = suorituspaiva;
  }

  /**
   * @param tapahtumat
   *          the tapahtumat to set
   */
  public void setTapahtumat(
      final SortedSet<Tapahtumarivi> tapahtumat) {
    this.tapahtumat = tapahtumat;
  }

  /**
   * @param varaus
   *          the varaus to set
   */
  public void setTilaus(final Tilaus tilaus) {
    this.tilaus = tilaus;
  }

  /**
   * @param viitenumero
   *          the viitenumero to set
   */
  public void setViitenumero(final String viitenumero) {
    this.viitenumero = viitenumero;
  }

}