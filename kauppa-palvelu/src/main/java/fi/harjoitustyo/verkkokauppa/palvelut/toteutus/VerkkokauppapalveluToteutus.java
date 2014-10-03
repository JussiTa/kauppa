package fi.harjoitustyo.verkkokauppa.palvelut.toteutus;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;

import org.springframework.transaction.annotation.Transactional;


//import fi.harjoitustyo.verkkokauppa.palvelut.TuotettaEiOleRiittavastiException;
import fi.harjoitustyo.verkkokauppa.palvelut.TuotettaEiOleRiittavastiException;
import fi.harjoitustyo.verkkokauppa.palvelut.Verkkokauppapalvelu;



import fi.harjoitustyo.verkkokauppa.tietorakenne.Tilaus;
import fi.harjoitustyo.verkkokauppa.tietorakenne.Tilausrivi;
import fi.harjoitustyo.verkkokauppa.tietorakenne.Kayttaja;
import fi.harjoitustyo.verkkokauppa.tietotaso.KayttajaDao;
import fi.harjoitustyo.verkkokauppa.tietotaso.TilausDao;
import fi.harjoitustyo.verkkokauppa.tietotaso.TuoteDao;
import fi.harjoitustyo.verkkokauppa.tietorakenne.Tuote;
import fi.harjoitustyo.verkkokauppa.tietotaso.toteutus.KayttajatunnusVarattuException;
import fi.harjoitustyo.verkkokauppa.tietotaso.toteutus.VirheellinenTunnusTaiSalasanaException;



@ Transactional
public class VerkkokauppapalveluToteutus implements Verkkokauppapalvelu {
	
	TilausDao tilausDao;
	KayttajaDao kayttajaDao;
	TuoteDao tuoteDao;
	
	
	/**
	 * Verkkokaupan palvelut, jotka näkyvät esim. sovelluksen
	 * web-komponenteille.
	 * 
	 * @author Jussi Isokangas
	 */
	

	  /**
	   * Hakee käyttäjän tietokannasta samalla varmistaen
	   * tunnuksen ja salasanan oikeellisuuden.
	   * 
	   * @param tunnus
	   *          hakuun käytettävä tunnus.
	   * @param salasana
	   *          hakuun käytettävä salasana.
	   * @return Tietokannasta haettu käyttäjä.
	   * @throws VirheellinenTunnusTaiSalasanaException
	   *           heitetään, jos tietokannasta ei löydy
	   *           käyttäjätunnusta ja salasanaa vastaavia
	   *           tietoja.
	   */
	  public Kayttaja haeJaVarmennaKayttaja(String tunnus,
	      String salasana)
	      throws VirheellinenTunnusTaiSalasanaException{
		  
		  return  kayttajaDao.haeJaVarmennaKayttaja(tunnus, salasana);
	  }
	  
	  /**
	   * Riippuvuus oikeaan tuoteDao-olioon voidaan asettaa
	   * ulkopuolelta.
	   * 
	   * @param tuoteDao
	   *          Palvelun yhteydessä käytettävä tuoteDao.
	   * 
	   */
	  public void setTuoteDao(final TuoteDao tuoteDao) {
	    this.tuoteDao = tuoteDao;
	  }
	  
	 

	  /**
	   * @param kayttajaDao
	   *          the kayttajaDao to set
	   */
	  public void setKayttajaDao(final KayttajaDao kayttajaDao) {
	    this.kayttajaDao = kayttajaDao;
	  }

	  /**
	   * Riippuvuus oikeaan tilausDao-olioon voidaan asettaa
	   * ulkopuolelta.
	   * 
	   * @param tilausDao
	   *          Palvelun yhteydessä käytettävä VarausDao.
	   */
	  public void setTilausDao(final TilausDao tilausDao) {
	    this.tilausDao = tilausDao;
	  }
	  

	  /**
	   * Lisää uuden asiakkaan tietokantaan ja samalla varmentaa
	   * ettei asiakkkaan valitsema käyttäjätunnus ole jo varattu.
	   * 
	   * @param asiakas
	   *          Uuden asiakkaan tiedot.
	   * 
	   * @throws KayttajatunnusVarattuException
	   *           heitetään, mikäli käyttäjätunnus on jo
	   *           käytössä.
	   */
	  
	  public void lisaaKayttaja(Kayttaja kayttaja)
	      throws KayttajatunnusVarattuException{
		  kayttajaDao.lisaaKayttaja(kayttaja);
	  
	  }
	  
  
	  /**
	   * Tilauksen tekeminen varmistaa, että jokaista tuotetta löytyy ostoskorissa oleva määrä. Muutoin heittää
	   * tuotetta ei ole riitävästi poikkeuksen
	   * 
	   */
	  public void teeTilaus(final Ostoskori ostoskori)
	  	throws TuotettaEiOleRiittavastiException{
		  	  
		    int lkm =0;
		    Iterator<Ostos> kori = ostoskori.iterator();	  
		    while (kori.hasNext()){
		    	Ostos ostos = kori.next();
		    	lkm=ostos.getKpl();
			   
			    try {
				   tuoteDao.lisaaTuoteTilaukseen(ostos.getId(),lkm);
				
			   }catch (fi.harjoitustyo.verkkokauppa.tietotaso.toteutus.TuotettaEiOleRiittavastiException e) {
				    throw new TuotettaEiOleRiittavastiException();
				
			   }
		  }	  
			    
	  }
	  
	  /**
	   * Tilaus vahvistetaan ja tallennetaan. 
	   * Samalla kutsutaan tuoteDao-metodia tuotteen lukumäärän päivitttämiseksi.
	   * Lisäksi päivitys käyttäjälle
	   */
	  public void vahvistaTilaus(Ostoskori ostoskori, Tilaus tilaus, Kayttaja kayttaja){
		  Ostos ostos;
		  Tuote tuote;
		  int lkm =0;
		 
		  Iterator<Ostos> kori = ostoskori.iterator();	
		  
		    while (kori.hasNext()){
		    	ostos = kori.next();
		        tuote=tuoteDao.haePerusavaimella(ostos.getId());
		    	Tilausrivi tilausrivi = new Tilausrivi(lkm, tuote.getNimi());
		    	
			    tilaus.lisaaTilausrivi(tilausrivi);
			    tilausrivi.setTilaus(tilaus);
			    tuote.setKpl(tuote.getKpl()- lkm);
			    tuoteDao.paivita(tuote);
				
		   }
		    
		    
		    kayttaja.lisaaTilaus(tilaus);
			tilaus.setAsiakas(kayttaja);
		    tilausDao.tallenna(tilaus);
		    if(kayttaja.getVersio()>0){
		     kayttajaDao.paivita(kayttaja);
		    }
		   	  
	  }
	  
	  /**
	   * Metodeita tuotteiden hakuun ja käyttäjän sekä tilausrivien jakuun.
	   */

	public Tuote haeTuoteNimella(String hakusana) {
		return tuoteDao.haeTuote(hakusana);
	}

	public List<Tuote> haetuoteRyhmanMukaan(String ryhma) {
		return tuoteDao.haeTuoteTuoteryhmanMukaan(ryhma);
	}

	public Collection<Tuote> haeKaikkiTuotteet() {
		return tuoteDao.haeKaikki();
	}

	public Collection<Kayttaja> haeKaikkiKayttajat() {
		return kayttajaDao.haeKaikki();
	}

	public SortedSet<Tilausrivi> haeTilausrivit() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Tuote haePerusavaimella(final int id){
		return tuoteDao.haePerusavaimella(id);
	}
	
	public Tilaus haePerusavaimella2(final int id){
		return tilausDao.haePerusavaimella(id);
	}
	
	public Kayttaja haePerusavaimella3(final int id){
		return kayttajaDao.haePerusavaimella(id);
	}
  
}


