package fi.harjoitustyo.verkkokauppa.tietotaso.toteutus;

/**
 * Poikkeus, jolla ilmaistaan että käyttäjän valitsema
 * käyttäjätunnus on jo käytössä.
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
   * Konstruktori, jossa välitetään mukana käyttäjätunnus.
   * 
   * @param arg0
   */
  public KayttajatunnusVarattuException(final String tunnus) {
    super(tunnus);
  }

}
