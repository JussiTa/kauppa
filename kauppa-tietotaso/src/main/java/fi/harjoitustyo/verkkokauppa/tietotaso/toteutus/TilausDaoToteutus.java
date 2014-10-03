package fi.harjoitustyo.verkkokauppa.tietotaso.toteutus;



import fi.harjoitustyo.verkkokauppa.tietorakenne.Tilaus;
import fi.harjoitustyo.verkkokauppa.tietotaso.TilausDao;
	

	/**
	 * Varauksien tietokantakäsittelijän (DAO) toteutus.
	 * 
	 * Toistaiseksi tässä luokassa ei tarvitse toteuttaa mitään
	 * erityisiä metodeita varauksia varten. Myöhemmässä vaiheessa
	 * tänne tulisivat esimerkiksi varauksia hakeva
	 * tietokantatoteutus.
	 * 
	 * @author kuha
	 * 
	 */
	public class TilausDaoToteutus extends
	    YleinenDaoPohja<Tilaus> implements TilausDao {

	  /**
	   * @param entiteetinLuokka
	   */
	  public TilausDaoToteutus() {
	    super(Tilaus.class);
	  
	  }
	  
	  
	

	}


