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
      // sormenjälkiä vertaamalla.
      if (kayttaja.varmennaSalasana(salasana)) {
    	 return kayttaja;
      } else {
        // Käyttäjän syöttämää salasanaa ei voitu varmentaa
        throw new VirheellinenTunnusTaiSalasanaException();
      }
    } catch (javax.persistence.NoResultException e) {
      // Käyttäjää ei löytynyt tietokannasta.
      throw new VirheellinenTunnusTaiSalasanaException();
    }
  }

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
      // Kyseinen käyttäjätunnus on jo käytössä.
      throw new KayttajatunnusVarattuException(kayttaja
          .getKayttajatunnus());
    }

    // Tallennetaan käyttäjän tiedot.
    tallenna(kayttaja);
  }

}
