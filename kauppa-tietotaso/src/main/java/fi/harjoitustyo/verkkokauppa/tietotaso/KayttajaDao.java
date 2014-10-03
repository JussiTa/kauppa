package fi.harjoitustyo.verkkokauppa.tietotaso;

import fi.harjoitustyo.verkkokauppa.tietorakenne.Kayttaja;
import fi.harjoitustyo.verkkokauppa.tietotaso.toteutus.KayttajatunnusVarattuException;
import fi.harjoitustyo.verkkokauppa.tietotaso.toteutus.VirheellinenTunnusTaiSalasanaException;

/**
 * Rajapinta huoneiden tietokantak�sittely� varten.
 * 
 * @author kuha
 * 
 */
public interface KayttajaDao extends YleinenDao<Kayttaja> {

  /**
   * Hakee k�ytt�j�n tietokannasta samalla varmistaen
   * tunnuksen ja salasanan oikeellisuuden.
   * 
   * @param tunnus
   *          hakuun k�ytett�v� tunnus.
   * @param salasana
   *          varmennettava salasana.
   * @return Tietokannasta haettu k�ytt�j�.
   * @throws VirheellinenTunnusTaiSalasanaException
   *           heitet��n, jos tietokannasta ei l�ydy
   *           k�ytt�j�tunnusta ja salasanaa vastaavia
   *           tietoja.
   */
  public Kayttaja haeJaVarmennaKayttaja(final String tunnus,
      final String salasana)
      throws VirheellinenTunnusTaiSalasanaException;

  /**
   * Lis�� uuden k�ytt�j�n tietokantaan ja samalla varmentaa
   * ettei k�ytt�j�n valitsema k�ytt�j�tunnus ole jo varattu.
   * 
   * @param kayttaja
   *          Uuden k�ytt�j�n tiedot.
   * 
   * @throws KayttajatunnusVarattuException
   *           heitet��n, mik�li k�ytt�j�tunnus on jo
   *           k�yt�ss�.
   */
  public void lisaaKayttaja(Kayttaja kayttaja)
      throws KayttajatunnusVarattuException;

}
