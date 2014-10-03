/*
 * Kuha, Janne: Tehokas Java EE -sovellustuotanto. WSOY 2008,
 * www.docendo.fi.
 */
package fi.harjoitustyo.verkkokauppa.palvelu;

import java.sql.Connection;


import javax.sql.DataSource;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;


import fi.harjoitustyo.verkkokauppa.tietorakenne.Kayttaja;
import fi.harjoitustyo.verkkokauppa.tietorakenne.Tilaus;
import fi.harjoitustyo.verkkokauppa.tietorakenne.Tuote;
//import fi.harjoitustyo.verkkokauppa.tietorakenne.Tilaus;
import fi.harjoitustyo.verkkokauppa.palvelut.toteutus.Ostos;
import fi.harjoitustyo.verkkokauppa.palvelut.toteutus.Ostoskori;

import fi.harjoitustyo.verkkokauppa.palvelut.Verkkokauppapalvelu;
import fi.harjoitustyo.verkkokauppa.palvelut.TuotettaEiOleRiittavastiException;

/**
 * Integraatiotesti, joka k‰ytt‰‰ DBUnit-kirjastoa tiedon
 * alustamiseen tietokantaan testej‰ varten.
 * 
 * @author Jussi Isokangas
 * 
 */
@ContextConfiguration(locations = { "classpath:spring-integraatiotestit.xml" })
public class DBUnitIntegraatioTest extends
    AbstractTransactionalJUnit4SpringContextTests {

  @Autowired
  @Qualifier("tietolahde")
  private DataSource dataSource;

  @Autowired
  @Qualifier("verkkokauppaPalvelu")
  private Verkkokauppapalvelu verkkokauppapalvelu;

  /**
   * Alustaa tietokannan tilan tiedoston
   * dbunit-tietokantamalli.xml mukaiseksi.
   * 
   * @throws Exception
   */
  @Before
  public void alustaTietokannanTiedot() throws Exception {

    // Normaali tietokantayhteys
    Connection yhteys =
        DataSourceUtils.getConnection(dataSource);

    try {

      // DBUnit tietokantayhteys
      IDatabaseConnection dbUnitYhteys =
          new DatabaseConnection(yhteys);

      // Ladataan xml-tiedosto luokkapolusta
      ClassPathResource xml =
          new ClassPathResource("/dbunit-tietokantamalli.xml");

      // Ladataan tietokannan data kyseisest‰ xml-tiedostosta
      IDataSet dataSet =
          new FlatXmlDataSet(xml.getInputStream());

      // Tuhoaa kaiken tiedon tietokannasta ja sen j‰lkeen
      // luo xml-tiedoston mukaiset rivit tietokantaan.
      DatabaseOperation.CLEAN_INSERT.execute(dbUnitYhteys,
          dataSet);
    } finally {
      // Suljetaan k‰ytetty tietokantayhteys.
      DataSourceUtils.releaseConnection(yhteys, dataSource);
    }
  }

  // /**
  // * Vie tietokannan tiedon erilliseen xml-tiedostoon.
  // *
  // * @throws ClassNotFoundException
  // */
  // @Test
  // public void testTietokantaExport() throws Exception {
  //
  // // Normaali tietokantayhteys
  // Connection yhteys =
  // DataSourceUtils.getConnection(dataSource);
  //
  // // DBUnit tietokantayhteys
  // IDatabaseConnection dbUnitYhteys =
  // new DatabaseConnection(yhteys);
  //
  // try {
  // // Vied‰‰n tietokannan sis‰ltˆ erilliseen
  // // xml-tiedostoon.
  // IDataSet fullDataSet = dbUnitYhteys.createDataSet();
  // FlatXmlDataSet.write(fullDataSet, new FileOutputStream(
  // "hotelli-tietokanta.xml"));
  // } finally {
  // DataSourceUtils.releaseConnection(yhteys, dataSource);
  // }
  // }

  /**
   * Varmistaa, ett‰ tilauksen tekeminen vaikuttaa tuotteen lukum‰‰r‰‰n
   * 
   * 
   */
  @Test
  public void testTietokantahaku()
      throws TuotettaEiOleRiittavastiException {
	  

    // Varmistetaan, ett‰ tietokannassa on tuotteita
    // oletettu m‰‰r‰
    Assert.assertEquals(
        "Tuotteita alustettuna tietokannassa v‰‰r‰ m‰‰r‰", 7,
        ((Verkkokauppapalvelu) verkkokauppapalvelu).haeKaikkiTuotteet().size());

    // Tehd‰‰n uusi osotoskori ja tilaus siit‰
    
    Tuote tuote = verkkokauppapalvelu.haeTuoteNimella("siima");
    Ostoskori ostoskori = new Ostoskori();
    ostoskori.lisaaOstos(new Ostos(tuote.getId(),2));
    Kayttaja kayttaja = new Kayttaja("esko","esimerki","eero");
    
   Tilaus tilaus = new Tilaus();
    
    
    
    verkkokauppapalvelu.teeTilaus(ostoskori);
    verkkokauppapalvelu.vahvistaTilaus(ostoskori,tilaus, kayttaja);
    

    // Varmistetaan ett‰ tuotteita on kannassa oikea m‰‰r‰
    // tilauksen j‰lkeen.
    Assert.assertEquals(
        "Tuotetta virheellinen m‰‰r‰ kannassa", 2,
        verkkokauppapalvelu.haeTuoteNimella("siima").getKpl());

  }

}