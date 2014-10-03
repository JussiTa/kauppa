package fi.harjoitustyo.verkkokauppa.tietotaso.toteutus;

/**
 * Poikkeus, jolla ilmaistaan ett� k�ytt�j�n valitsema
 * k�ytt�j�tunnus on jo k�yt�ss�.
 * 
 * @author kuha
 * 
 */
public class KayttajatunnusVarattuException extends Exception {

  private static final long serialVersionUID = 1L;

  /**
   * Peruskonstruktori.
   */
  public KayttajatunnusVarattuException() {
    super();
  }

  /**
   * Konstruktori, jossa v�litet��n mukana k�ytt�j�tunnus.
   * 
   * @param arg0
   */
  public KayttajatunnusVarattuException(final String tunnus) {
    super(tunnus);
  }

}
