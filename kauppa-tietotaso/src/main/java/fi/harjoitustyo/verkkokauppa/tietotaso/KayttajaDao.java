package fi.harjoitustyo.verkkokauppa.tietotaso;

import fi.harjoitustyo.verkkokauppa.tietorakenne.Kayttaja;
import fi.harjoitustyo.verkkokauppa.tietotaso.toteutus.KayttajatunnusVarattuException;
import fi.harjoitustyo.verkkokauppa.tietotaso.toteutus.VirheellinenTunnusTaiSalasanaException;

/**
 * Rajapinta huoneiden tietokantakäsittelyä varten.
 * 
 * @author kuha
 * 
 */
public interface KayttajaDao extends YleinenDao<Kayttaja> {

  /**
   * Hakee käyttäjän tietokannasta samalla varmistaen
   * tunnuksen ja salasanan oikeellisuuden.
   * 
   * @param tunnus
   *          hakuun käytettävä tunnus.
   * @param salasana
   *          varmennettava salasana.
   * @return Tietokannasta haettu käyttäjä.
   * @throws VirheellinenTunnusTaiSalasanaException
   *           heitetään, jos tietokannasta ei löydy
   *           käyttäjätunnusta ja salasanaa vastaavia
   *           tietoja.
   */
  public Kayttaja haeJaVarmennaKayttaja(final String tunnus,
      final String salasana)
      throws VirheellinenTunnusTaiSalasanaException;

  /**
   * Lisää uuden käyttäjän tietokantaan ja samalla varmentaa
   * ettei käyttäjän valitsema käyttäjätunnus ole jo varattu.
   * 
   * @param kayttaja
   *          Uuden käyttäjän tiedot.
   * 
   * @throws KayttajatunnusVarattuException
   *           heitetään, mikäli käyttäjätunnus on jo
   *           käytössä.
   */
  public void lisaaKayttaja(Kayttaja kayttaja)
      throws KayttajatunnusVarattuException;

}
