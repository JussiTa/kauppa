/*
 *
 */
package fi.harjoitustyo.verkkokauppa.web;

import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.WicketRuntimeException;

import org.apache.wicket.security.hive.HiveMind;
import org.apache.wicket.security.hive.config.PolicyFileHiveFactory;
import org.apache.wicket.security.swarm.SwarmWebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;


import fi.harjoitustyo.verkkokauppa.web.turvallisuus.Loginsivu;

/**
 * Verkkokaupan Wicket web-projekti. Peritty
 * SwarmWebApplication turvallisuusmääritysten käyttämiseksi.
 * 
 * @Author Jussi Isokangas
 */
public class VerkkokauppaSovellus extends SwarmWebApplication {

  private static final Log log =
      LogFactory.getLog(VerkkokauppaSovellus.class);
  Sessio session;

  @Override
  protected Object getHiveKey() {
    return "verkkokauppasovellus";

  }

  /**
   * Aloitussivuna käytetään alussa vapaiden huoneiden
   * listausta.
   */
  
  @SuppressWarnings("rawtypes")
@Override
  public Class getHomePage() {
    return Aloitussivu.class;
  }

  /**
   * Palauttaa sisäänkirjautumissivun.
   */
 
  @SuppressWarnings("rawtypes")
public Class getLoginPage() {
    return Loginsivu.class;
  }

  /**
   * Lisää tarvittavan kuuntelijan Spring Framework
   * -kontekstin komponenttien käsittelyä varten.
   */
  @Override
  public void init() {
    super.init();
    addComponentInstantiationListener(new SpringComponentInjector(
        this));
  }
     
  
 
  

  /**
   * Asettaa perustiedot käyttäjäoikeuksien lukemista varten.
   * Katso WEB-INF/turvallisuus.hive
   */
 
  
  @Override
  protected void setUpHive() {

    // Luodaan uusi tehdas oikeuksien määrittelyä varten.
    PolicyFileHiveFactory factory =
        new PolicyFileHiveFactory();

    // Luodaan lyhenteet sovelluksen web-paketteja varten
    // jotta policy-tiedoston kirjoittaminen helpottuu.
    factory.setAlias("paketti","fi.harjoitustyo.verkkokauppa.web");
    factory.setAlias("tp",
        "fi.harjoitustyo.verkkokauppa.web.turvallisuus");
    try {
      // Lisätään yksi kappale
      // turvallisuusmäärittelytiedostoja.
      URL tiedosto =
          getServletContext().getResource(
              "/WEB-INF/turvallisuus.hive");
      factory.addPolicyFile(tiedosto);
    } catch (Exception e) {
      log.fatal(e, e);
      throw new WicketRuntimeException(e);
    }

    // Rekisteröidään turvallisuusmääritykset.
    HiveMind.registerHive(getHiveKey(), factory);
  }
  

  @Override
  public Session newSession(Request request, Response response) {
    return session =  new Sessio(request,VerkkokauppaSovellus.this);
    
  }

  
  
  
  

}


