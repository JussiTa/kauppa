package fi.harjoitustyo.verkkokauppa.tietotaso.toteutus;



import fi.harjoitustyo.verkkokauppa.tietorakenne.Tilaus;
import fi.harjoitustyo.verkkokauppa.tietotaso.TilausDao;
	

	/**
	 * Varauksien tietokantak�sittelij�n (DAO) toteutus.
	 * 
	 * Toistaiseksi t�ss� luokassa ei tarvitse toteuttaa mit��n
	 * erityisi� metodeita varauksia varten. My�hemm�ss� vaiheessa
	 * t�nne tulisivat esimerkiksi varauksia hakeva
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


