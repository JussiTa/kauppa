package fi.harjoitustyo.verkkokauppa.tietorakenne.validaatio;

import org.hibernate.validator.Validator;



/**
 * Viitenumeron tarkistuslaskuri. Voit generoida viitenumeroja
 * eri pankkien webbipalveluissa. Viitenumerosta ja muista
 * tarkistuksista löydät paljon lisätietoa verkosta,
 * esimerkiksi osoitteesta
 * http://koti.mbnet.fi/~thales/tarkmerk.htm#viitenumero.
 * 
 * @author kuha
 * 
 */
public class ViitenumeroValidaattori implements
    Validator<Viitenumero> {

  /**
   * Mahdolliset aloitustoimenpiteet.
   * 
   * @see org.hibernate.validator.Validator#initialize(java.lang.annotation.Annotation)
   */
  public void initialize(final Viitenumero numero) {
  }

  /**
   * Varsinainen viitenumeron oikeellisuuden tarkistus.
   * 
   * @return true, jos validointi meni läpi, muuten false.
   * @see org.hibernate.validator.Validator#isValid(java.lang.Object)
   */
  public boolean isValid(final Object olio) {
    if (olio == null) {
      return true;
    }

    if (olio instanceof String) {
      String merkkijono = (String) olio;
      return validoiViitenumero(merkkijono);
    }

    return false;
  }

  /**
   * Laskee viitenumeron tarkistenumeron.
   * 
   * @param merkkijono
   * @return true, jos viitenumeron tarkistenumero täsmää.
   */
  private boolean validoiViitenumero(final String merkkijono) {
    // Tarkistetaan viitenumeron pituus
    if ((merkkijono.length() < 4)
        || (merkkijono.length() > 20)) {
      return false;
    }

    int alkupTarkiste =
        Integer.parseInt(""
            + merkkijono.charAt(merkkijono.length() - 1));

    String ilmanTarkistetta =
        merkkijono.substring(0, merkkijono.length() - 1);

    // Kasvatetaan viitenumeron pituus täyteen mittaansa
    // etunollia käyttämällä.
    // Tämä voitaisiin myös tehdä silmukkaa käyttämällä.
    String etunollat = "0000000000000000000";                                                                                                      
    String yhdiste = etunollat + ilmanTarkistetta;
    String tasattu = yhdiste.substring(yhdiste.length() - 19);

    // Lasketaan varsinainen tarkistenumero.
    int summa = 0;
    int kertoimet[] = { 1, 3, 7 };
    for (int i = 0; i < tasattu.length(); i++) {
      int kertoimenIndeksi = (i + 2) % 3;
      int luku = Integer.parseInt("" + tasattu.charAt(i));
      int osaTarkiste = luku * kertoimet[kertoimenIndeksi];
      summa += osaTarkiste;
    }

    // Verrataan tarkistetta oikeaan lukuun
    int tarkiste = (1000 - summa) % 10;
    if (tarkiste == alkupTarkiste) {
      return true;
    }

    return false;
  }
}
  
