package fi.harjoitustyo.verkkokauppa.tietotaso;

import java.util.Collection;

import fi.harjoitustyo.verkkokauppa.tietorakenne.Entiteetti;

/**
 * Yleinen rajapinta kaikkien Data Access Object
 * -suunnittelumallin mukaisten rajapintojen perustaksi.
 * 
 * @author kuha
 * 
 */
public interface YleinenDao<E extends Entiteetti> {

  /**
   * Hakee kaikki oliot tietokannasta, jotka ovat tiettyä
   * tyyppiä.
   * 
   * @return Kokoelma kyseisiä olioita.
   */
  public Collection<E> haeKaikki();

  /**
   * Hakee olioita tietokannasta, joilla on tietty arvo
   * perusavaimella.
   * 
   * @param pa
   *          Haettavan olion perusavain tietokannassa.
   * @return Kyseinen olio ladattuna tietokannasta.
   */
  public E haePerusavaimella(Integer id);

  /**
   * Poistaa olion tiedot tietokannasta.
   * 
   * @param entiteetti
   *          Poistettava entiteetti.
   */
  public void poista(E entiteetti);

  /**
   * Tallentaa kyseisen entiteetin tiedot tietokantaan.
   * 
   * @param Tallennettava
   *          entiteetti.
   */
  public void tallenna(E entiteetti);
  public void paivita(E entiteetti);
  
  
}