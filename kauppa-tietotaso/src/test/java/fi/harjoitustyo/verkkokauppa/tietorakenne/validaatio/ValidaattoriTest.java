package fi.harjoitustyo.verkkokauppa.tietorakenne.validaatio;

import org.junit.Assert;
import org.junit.Test;

/**
 * Testi, joka testaa erilaisten validaattorien toimintaa.
 * 
 * @author kuha
 * 
 */
public class ValidaattoriTest {

  @Test
  public void testViitenumeroValidaatioOikeat() {
    ViitenumeroValidaattori v = new ViitenumeroValidaattori();

    Assert.assertTrue("Viitenumeron 12344 tarkistus "
        + "antoi v‰‰r‰n tuloksen ", v.isValid("12344"));
    Assert.assertTrue("Viitenumeron 123453 tarkistus "
        + "antoi v‰‰r‰n tuloksen ", v.isValid("123453"));
    Assert.assertTrue("Viitenumeron 12344 tarkistus "
        + "antoi v‰‰r‰n tuloksen ", v.isValid("12344"));
    Assert.assertTrue("Viitenumeron 1234561 tarkistus "
        + "antoi v‰‰r‰n tuloksen ", v.isValid("1234561"));
    Assert.assertTrue("Viitenumeron 12345672 tarkistus "
        + "antoi v‰‰r‰n tuloksen ", v.isValid("12345672"));
    Assert.assertTrue("Viitenumeron 123456780 tarkistus "
        + "antoi v‰‰r‰n tuloksen ", v.isValid("123456780"));

    Assert.assertTrue(
        "Viitenumeron 12345678912345678917 tarkistus "
            + "antoi v‰‰r‰n tuloksen ", v
            .isValid("12345678912345678917"));
  }

  /**
   * Testi, joka testaa viitenumerovalidaattorin toimintaa.
   */
  @Test
  public void testViitenumeroValidaatioVaarat() {
    ViitenumeroValidaattori v = new ViitenumeroValidaattori();

    Assert
        .assertFalse(
            "Virheellisen viitenumeron 9876 tarkistus antoi v‰‰r‰n tuloksen ",
            v.isValid("98761"));
    Assert
        .assertFalse(
            "Virheellisen viitenumeron 987651 tarkistus antoi v‰‰r‰n tuloksen ",
            v.isValid("987651"));
    Assert
        .assertFalse(
            "Virheellisen viitenumeron 22 tarkistus antoi v‰‰r‰n tuloksen ",
            v.isValid("22"));
  }
}
