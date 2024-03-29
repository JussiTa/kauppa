package fi.harjoitustyo.verkkokauppa.web;
/*
 * 
 */


import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.webapp.WebAppContext;

/**
 * Käynnistää Jetty-sovelluspalvelimen määriteltyä porttia
 * käyttäen ja rekisteröi verkkokauppa-web -sovelluksen.
 * 
 * Esimerkki URL
 * 
 * http://localhost:8080/kauppa-web/
 * 
 * @author Jussi Isokangas
 * 
 */
public class Kaynnista {
  public static void main(final String argumentit[])
      throws Exception {
    System.out.println("JETTY - käynnistyy...");

    // Kommunikaatioväylän määrittelyt.
    Connector kommunikaatio = new SocketConnector();
    kommunikaatio.setPort(8080);

    // Web-sovelluskonteksti.
    WebAppContext webkonteksti = new WebAppContext();
    webkonteksti.setContextPath("/kauppa-web");
    webkonteksti.setWar("src/main/webapp");

    // Palvelimen käynnistäminen.
    Server palvelin = new Server();
    palvelin.setConnectors(new Connector[] { kommunikaatio });
    palvelin.addHandler(webkonteksti);

    // Odotetaan kunnes palvelin sammutetaan.
    palvelin.start();
    palvelin.join();
  }
}
