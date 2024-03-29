/*
 * Kuha, Janne: Tehokas Java EE -sovellustuotanto. WSOY 2008,
 * www.docendo.fi.
 */
package fi.harjoitustyo.verkkokauppa.web.turvallisuus;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.security.hive.authentication.LoginContext;
import org.apache.wicket.security.hive.authentication.Subject;
import org.apache.wicket.security.strategies.LoginException;

/**
 * Sovelluksen sisäänkirjautumiseen liittyvä konteksti.
 * 
 * @author kuha
 * 
 */
public class Loginkonteksti extends LoginContext {

  @SuppressWarnings("unused")
  private static final Log log =
      LogFactory.getLog(Loginkonteksti.class);
  private final Subject subjekti;

  /**
   * Konstruktori, jonka avulla kontekstin sisältämät
   * autentikaatiedot voidaan asettaa.
   * 
   */
  public Loginkonteksti(final Subject subjekti) {
    this.subjekti = subjekti;
  }

  /**
   * Login-metodi, jota kutsutaan kirjautumisen yhteydessä.
   * Tulee palauttaa käyttäjän autentikaatiotietoa esittävä
   * Subject-olio.
   * 
   */
  @Override
  public Subject login() throws LoginException {
    return subjekti;
  }
}