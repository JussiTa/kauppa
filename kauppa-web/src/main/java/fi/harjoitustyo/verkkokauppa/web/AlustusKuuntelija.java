/*
 * Kuha, Janne: Tehokas Java EE -sovellustuotanto. WSOY 2008,
 * www.docendo.fi.
 */
package fi.harjoitustyo.verkkokauppa.web;

import java.sql.Connection;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Alustaa tietokannan sovelluksen käynnistyessä.
 * 
 * @author kuha
 * 
 */
public class AlustusKuuntelija implements
    ServletContextListener {

  private static final Log log =
      LogFactory.getLog(AlustusKuuntelija.class);

  /**
   * Ei toimintoa sovelluksen sammuessa.
   */
  public void contextDestroyed(
      final ServletContextEvent tapahtuma) {
  }

  /**
   * Sovelluksen käynnistyessä, eli kontekstin luonnin
   * yhteydessä alustaa tietokannan DBUnit-kirjaston avulla.
   */
  public void contextInitialized(
      final ServletContextEvent tapahtuma) {
    WebApplicationContext springKonteksti =
        WebApplicationContextUtils
            .getWebApplicationContext(tapahtuma
                .getServletContext());
    DataSource tietolahde =
        (DataSource) springKonteksti.getBean("tietolahde");

    log.warn("Alustetaan tietokanta oletustiedoilla...");

    // Normaali tietokantayhteys
    Connection yhteys =
        DataSourceUtils.getConnection(tietolahde);

    try {

      // DBUnit tietokantayhteys
      IDatabaseConnection dbUnitYhteys =
          new DatabaseConnection(yhteys);

      // Ladataan xml-tiedosto luokkapolusta
      ClassPathResource xml =
          new ClassPathResource("/dbunit-aloitusdata.xml");

      // Ladataan tietokannan data kyseisestä xml-tiedostosta
      IDataSet dataSet =
          new FlatXmlDataSet(xml.getInputStream());

      // Tuhoaa kaiken tiedon tietokannasta ja sen jälkeen
      // luo xml-tiedoston mukaiset rivit tietokantaan.
      DatabaseOperation.CLEAN_INSERT.execute(dbUnitYhteys,
          dataSet);
    } catch (Exception e) {
      log.fatal(e.getMessage(), e);
      throw new IllegalStateException(
          "Tietokantaa ei voida alustaa!");
    } finally {
      // Suljetaan käytetty tietokantayhteys.
      DataSourceUtils.releaseConnection(yhteys, tietolahde);
    }

    log.warn("Tietokanta alustettu.");

  }
}
