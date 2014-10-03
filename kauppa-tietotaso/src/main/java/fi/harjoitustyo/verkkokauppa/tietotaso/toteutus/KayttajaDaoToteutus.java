package fi.harjoitustyo.verkkokauppa.tietotaso.toteutus;



import javax.persistence.EntityManager;
import javax.persistence.Query;

import fi.harjoitustyo.verkkokauppa.tietorakenne.Kayttaja;

import fi.harjoitustyo.verkkokauppa.tietotaso.KayttajaDao;


/**
 * Huoneisiin kohdistuvien tietokantaoperaatioiden
 * toteutusluokka.
 * 
 * @author kuha
 * 
 */
public class KayttajaDaoToteutus extends
    YleinenDaoPohja<Kayttaja> implements KayttajaDao {

  /**
   * Konstruktori, joka kutsuu yliluokan konstruktoria.
   * 
   * @param entiteetinLuokka
   */
  public KayttajaDaoToteutus() {
    super(Kayttaja.class);
  }

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
      throws VirheellinenTunnusTaiSalasanaException {
    EntityManager em = getEntityManager();
    Query q =
        em
            .createQuery("SELECT k FROM Kayttaja k "
                + " WHERE lower(k.kayttajatunnus) like lower(:tunnus)");
    q.setParameter("tunnus", tunnus);
    try {
      Kayttaja kayttaja = (Kayttaja) q.getSingleResult();
      // Varmentaa salasana oikeellisuuden salasanojen
      // sormenj�lki� vertaamalla.
      if (kayttaja.varmennaSalasana(salasana)) {
    	 return kayttaja;
      } else {
        // K�ytt�j�n sy�tt�m�� salasanaa ei voitu varmentaa
        throw new VirheellinenTunnusTaiSalasanaException();
      }
    } catch (javax.persistence.NoResultException e) {
      // K�ytt�j�� ei l�ytynyt tietokannasta.
      throw new VirheellinenTunnusTaiSalasanaException();
    }
  }

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
  public void lisaaKayttaja(final Kayttaja kayttaja)
      throws KayttajatunnusVarattuException {
    EntityManager em = getEntityManager();
    Query q =
        em
            .createQuery("SELECT count(k) FROM Kayttaja k "
                + " WHERE lower(k.kayttajatunnus) like lower(:tunnus)");
    q.setParameter("tunnus", kayttaja.getKayttajatunnus());
    Number tunnuksellaKayttajia =
        (Number) q.getSingleResult();
    if (tunnuksellaKayttajia.intValue() != 0) {
      // Kyseinen k�ytt�j�tunnus on jo k�yt�ss�.
      throw new KayttajatunnusVarattuException(kayttaja
          .getKayttajatunnus());
    }

    // Tallennetaan k�ytt�j�n tiedot.
    tallenna(kayttaja);
  }

}
