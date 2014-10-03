package fi.harjoitustyo.verkkokauppa.tietorakenne;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Enumeraatio eri ALV-verokantojen m‰‰rittelyyn
 * (23%,17%,8%,0%)
 * 
 * @author kuha
 * 
 */
public enum AlvKanta implements Serializable {

  ALV0("0%",
      "Lehtien tilausmaksut ja taitelijoiden palkkiot",
      new BigDecimal("0.00")),
  ALV17("17%", "Elintarvikkeet ja rehut", new BigDecimal(
      "0.17")),
  ALV23("23%", "Yleinen arvonlis‰verokanta", new BigDecimal(
      "0.23")),
  ALV8(
      "8%",
      "Majoituspalvelut, kirjat, viihdetilaisuuksien- ja liikuntatilojen p‰‰symaksut.",
      new BigDecimal("0.08"));

  private final String otsikko;
  private final String selite;
  private final BigDecimal verokanta;

  /**
   * Konstruktori, jonka avulla halutun kaltaisen ALV-kannan
   * voi luoda.
   * 
   * @param otsikko
   * @param selite
   * @param verokanta
   */
  AlvKanta(final String otsikko, final String selite,
      final BigDecimal verokanta) {
    this.otsikko = otsikko;
    this.selite = selite;
    this.verokanta = verokanta;
  }

  /**
   * @return the otsikko
   */
  public String getOtsikko() {
    return otsikko;
  }

  /**
   * @return the otsikko
   */
  public String getSelite() {
    return selite;
  }

  /**
   * @return the verokanta
   */
  public BigDecimal getVerokanta() {
    return verokanta;
  }

}
