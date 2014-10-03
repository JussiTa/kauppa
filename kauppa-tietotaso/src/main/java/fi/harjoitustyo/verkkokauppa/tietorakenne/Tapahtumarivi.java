package fi.harjoitustyo.verkkokauppa.tietorakenne;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

/**
 * Yksitt‰inen laskuun liittyv‰ tapahtuma, kuten tilaus
 * huonepalvelusta.
 * 
 * @author kuha
 * 
 */

@Entity
@Table(name = "tapahtumarivi")
public class Tapahtumarivi extends Entiteetti implements
    Comparable<Tapahtumarivi> {

  public static final int MAX_SELITE_PITUUS = 180;

  private static final long serialVersionUID = 1L;

  @NotNull
  @Column(name = "aikaleima", nullable = false)
  private Date aikaleima;

  @Enumerated(value = EnumType.STRING)
  private AlvKanta alvKanta;

  @NotNull
  @Column(name = "kpl", nullable = false)
  private int kappalemaara;

  @ManyToOne
  @JoinColumn(name = "lasku_id")
  @ForeignKey(name = "viiteavain_tapahtuma_lasku", inverseName = "viiteavain_lasku_tapahtuma")
  private Lasku lasku;

  @Length(min = 0, max = MAX_SELITE_PITUUS)
  @Column(name = "selite", length = MAX_SELITE_PITUUS, nullable = true)
  private String selite;

  @NotNull
  @Column(name = "verotonSumma", nullable = false, precision = 9, scale = 2)
  private BigDecimal verotonSumma;

  /**
   * Peruskonstruktori, joka asettaa automaattisesti
   * aikaleiman olion luontihetkelle.
   * 
   * @param string
   */
  public Tapahtumarivi() {
    aikaleima = Calendar.getInstance().getTime();
  }

  /**
   * Konstruktori, jonka avulla voidaan luoda rivi tietyll‰
   * verottomalla summalla ja joka asettaa automaattisesti
   * aikaleiman olion luontihetkelle.
   * 
   * @param string
   */
  public Tapahtumarivi(final String summa) {
    aikaleima = Calendar.getInstance().getTime();
    verotonSumma = new BigDecimal(summa);
  }

  /**
   * Vertailumetodi, jota k‰ytet‰‰n tapahtumarivien
   * j‰rjestelyyn laskun sis‰ll‰ muistissa. Huom! SortedSet
   * rajapintaan tallennettava olio ei saa palauttaa arvoksi
   * nollaa (0) tai se korvaa aiemmin sinne tallennetun rivin.
   */
  public int compareTo(final Tapahtumarivi t) {
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

    // Mik‰li vertailua ei voida tehd‰ perusavaimien mukaan
    // pyrit‰‰n se tekem‰‰n summien perusteella.
    if ((getVerotonSumma() != null)
        && (t.getVerotonSumma() != null)) {
      i = getVerotonSumma().compareTo(t.getVerotonSumma());
      if (i != 0) {
        return i;
      }
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

  /**
   * @return the aikaleima
   */
  public Date getAikaleima() {
    return aikaleima;
  }

  /**
   * @return the alvKanta
   */
  public AlvKanta getAlvKanta() {
    return alvKanta;
  }

  /**
   * @return the kappalemaara
   */
  public int getKappalemaara() {
    return kappalemaara;
  }

  /**
   * @return the lasku
   */
  public Lasku getLasku() {
    return lasku;
  }

  /**
   * @return the selite
   */
  public String getSelite() {
    return selite;
  }

  /**
   * @return the verotonSumma
   */
  public BigDecimal getVerotonSumma() {
    return verotonSumma;
  }

  /**
   * @param aikaleima
   *          the aikaleima to set
   */
  public void setAikaleima(final Date aikaleima) {
    this.aikaleima = aikaleima;
  }

  /**
   * @param alvKanta
   *          the alvKanta to set
   */
  public void setAlvKanta(final AlvKanta alvKanta) {
    this.alvKanta = alvKanta;
  }

  /**
   * @param kappalemaara
   *          the kappalemaara to set
   */
  public void setKappalemaara(final int kappalemaara) {
    this.kappalemaara = kappalemaara;
  }

  /**
   * @param lasku
   *          the lasku to set
   */
  public void setLasku(final Lasku lasku) {
    this.lasku = lasku;
  }

  /**
   * @param selite
   *          the selite to set
   */
  public void setSelite(final String selite) {
    this.selite = selite;
  }

  /**
   * @param verotonSumma
   *          the verotonSumma to set
   */
  public void setVerotonSumma(final BigDecimal verotonSumma) {
    this.verotonSumma = verotonSumma;
  }

}
