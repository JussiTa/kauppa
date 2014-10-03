/*
 * 
 * 
 */
package fi.harjoitustyo.verkkokauppa.web.turvallisuus;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.security.hive.authentication.Subject;
import org.apache.wicket.security.hive.authorization.Principal;

import fi.harjoitustyo.verkkokauppa.tietorakenne.Kayttaja;
import fi.harjoitustyo.verkkokauppa.tietorakenne.Rooli;

/**
 * Käyttäjän roolin esitystapa web-kerroksella, pitää
 * sisällään varsinaisen sovellusalamallin mukaisen käyttäjän.
 * 
 * @author kuha
 * 
 */
public class WebKayttajaRooli implements Principal,
    Serializable {
  private static final Log log =
      LogFactory.getLog(WebKayttajaRooli.class);

  private static final long serialVersionUID = 1L;
  private final Kayttaja kayttaja;

  private Rooli rooli = Rooli.VIERAILIJA;

  /**
   * 
   * Hotellisovelluksen web-käyttäjä, joka ympyröi varsinaisen
   * sovellusalamallin mukaisen käyttäjäluokan toteutuksen.
   * 
   * @param kayttaja
   *          ympyröitävä käyttäjä.
   */
  public WebKayttajaRooli(final Kayttaja kayttaja) {
    this.kayttaja = kayttaja;
     //Ainoastaan ADMINILLE palautetaan tunniste
    // "ADMIN" oikeuksia varten.'
    if (kayttaja != null) {
      rooli = kayttaja.getRooli();
    }

    if (log.isDebugEnabled() && (kayttaja != null)) {
     log.debug("Käyttäjä [" + kayttaja.getKayttajatunnus()
          + "] kirjautui. Taso [" + rooli + "].");
    }

  }

  /**
   * Konstruktori, jota käytetään Wicketistä.
   */
  public WebKayttajaRooli(final String roolinimi) {
    if (roolinimi != null) {
      rooli = Rooli.valueOf(roolinimi.toUpperCase());
    }
    kayttaja = null;
  }

  /**
   * Equals-toteutus perustuen rooleihin.
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(final Object obj) {

    if (this == obj) {
      return true;
    }

    if (obj instanceof WebKayttajaRooli) {
      final WebKayttajaRooli other = (WebKayttajaRooli) obj;
      if ((getRooli() != null)
          && getRooli().equals(other.getRooli())) {
        return true;
      }
    }

    return false;
  }

  /**
   * Palauttaa sovellusalamallin mukaisen käyttäjän.
   * 
   * @return the kayttaja
   */
  public Kayttaja getKayttaja() {
    return kayttaja;
  }

  /**
   * Palauttaa käyttäjän käyttäjätunnuksen.
   * 
   * @return
   * @see fi.harjoitustyo.verkkokauppa.tietorakenne.Kayttaja#getKayttajatunnus()
   */
  public String getKayttajatunnus() {
    return kayttaja.getKayttajatunnus();
  }

  /**
   * Palauttaa käyttäjän käyttäjänimen..
   */
  public String getName() {
    if (kayttaja != null) {
      return kayttaja.getKayttajatunnus();
    }
    return null;
  }

  /**
   * @return the rooli
   */
  public Rooli getRooli() {
    return rooli;
  }

  /**
   * generated hash based on class and name.
   * 
   * @see java.lang.Object#hashCode()
   */
  public boolean implies(final Subject subject) {
    return false;
  }

  @Override
  public String toString() {
    return  "rooli: " + rooli + ", käyttäjä: " + kayttaja;
  }

}
