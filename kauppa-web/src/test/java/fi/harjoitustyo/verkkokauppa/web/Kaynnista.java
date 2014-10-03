package fi.harjoitustyo.verkkokauppa.web;
/*
 * 
 */


import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.webapp.WebAppContext;

/**
 * K‰ynnist‰‰ Jetty-sovelluspalvelimen m‰‰ritelty‰ porttia
 * k‰ytt‰en ja rekisterˆi verkkokauppa-web -sovelluksen.
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
    System.out.println("JETTY - k‰ynnistyy...");

    // Kommunikaatiov‰yl‰n m‰‰rittelyt.
    Connector kommunikaatio = new SocketConnector();
    kommunikaatio.setPort(8080);

    // Web-sovelluskonteksti.
    WebAppContext webkonteksti = new WebAppContext();
    webkonteksti.setContextPath("/kauppa-web");
    webkonteksti.setWar("src/main/webapp");

    // Palvelimen k‰ynnist‰minen.
    Server palvelin = new Server();
    palvelin.setConnectors(new Connector[] { kommunikaatio });
    palvelin.addHandler(webkonteksti);

    // Odotetaan kunnes palvelin sammutetaan.
    palvelin.start();
    palvelin.join();
  }
}
