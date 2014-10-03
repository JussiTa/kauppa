package fi.harjoitustyo.verkkokauppa.test;

import java.util.Random;

/**
 * Testien apuna k�ytett�v� luokka, jossa yleisi� testauksessa
 * k�ytett�vi� metodeita.
 * 
 * @author kuha
 * 
 */
public class AvustajaTesteille {
  private static Random rn = new Random();

  /**
   * T�m� metodi luo satunnaisia kirjaimia sis�lt�v�n
   * merkkijonon halutulla pituudella.
   * 
   * @param pituus
   * @return
   */
  public static String satunnainenMerkkijono(final int pituus) {
    StringBuffer jono = new StringBuffer();
    int vali = 'z' - 'a' + 1;
    for (int i = 0; i < pituus; i++) {
      int kirjain = rn.nextInt() % vali;
      jono.append((char) ('a' + kirjain));
    }
    return jono.toString();
  }

}
