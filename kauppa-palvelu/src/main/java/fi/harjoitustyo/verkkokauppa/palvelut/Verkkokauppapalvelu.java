package fi.harjoitustyo.verkkokauppa.palvelut;




import java.util.Collection;
import java.util.SortedSet;

import fi.harjoitustyo.verkkokauppa.tietorakenne.Kayttaja;
import fi.harjoitustyo.verkkokauppa.palvelut.toteutus.Ostoskori;
import fi.harjoitustyo.verkkokauppa.tietorakenne.Tilaus;
import fi.harjoitustyo.verkkokauppa.tietorakenne.Tilausrivi;
import fi.harjoitustyo.verkkokauppa.tietorakenne.Tuote;
import fi.harjoitustyo.verkkokauppa.tietotaso.toteutus.KayttajatunnusVarattuException;
import fi.harjoitustyo.verkkokauppa.tietotaso.toteutus.VirheellinenTunnusTaiSalasanaException;

public interface Verkkokauppapalvelu {

	

	/**
	 * Verkkokaupan palvelut, jotka n�kyv�t esim. sovelluksen
	 * web-komponenteille.
	 * 
	 * @author kuha
	 */
	

	  /**
	   * Hakee k�ytt�j�n tietokannasta samalla varmistaen
	   * tunnuksen ja salasanan oikeellisuuden.
	   * 
	   * @param tunnus
	   *          hakuun k�ytett�v� tunnus.
	   * @param salasana
	   *          hakuun k�ytett�v� salasana.
	   * @return Tietokannasta haettu k�ytt�j�.
	   * @throws VirheellinenTunnusTaiSalasanaException
	   *           heitet��n, jos tietokannasta ei l�ydy
	   *           k�ytt�j�tunnusta ja salasanaa vastaavia
	   *           tietoja.
	   */
	  public Kayttaja haeJaVarmennaKayttaja(String tunnus,
	      String salasana)
	      throws VirheellinenTunnusTaiSalasanaException;

	  /**
	   * Lis�� uuden asiakkaan tietokantaan ja samalla varmentaa
	   * ettei asiakkkaan valitsema k�ytt�j�tunnus ole jo varattu.
	   * 
	   * @param asiakas
	   *          Uuden asiakkaan tiedot.
	   * 
	   * @throws KayttajatunnusVarattuException
	   *           heitet��n, mik�li k�ytt�j�tunnus on jo
	   *           k�yt�ss�.
	   */
	  
	  public void lisaaKayttaja(Kayttaja kayttaja)
	      throws KayttajatunnusVarattuException;

	 
	  
	  public void teeTilaus(final Ostoskori ostoskori)
	  throws  TuotettaEiOleRiittavastiException;
	  
	  public SortedSet <Tilausrivi>haeTilausrivit();
		 
	
	  
	  
	  public void vahvistaTilaus(Ostoskori ostoskori, Tilaus tilaus, Kayttaja kayttaja);
	  
	  public Tuote haeTuoteNimella(String nimi);
	  
	  public Collection<Tuote> haetuoteRyhmanMukaan(String ryhma);
	  	
	  public Collection<Tuote>haeKaikkiTuotteet();

	  public Collection<Kayttaja>haeKaikkiKayttajat();
	  public Tuote haePerusavaimella(final int id);
	  
	  public Tilaus haePerusavaimella2(final int id);
	  public Kayttaja haePerusavaimella3(final int id);
			
		

	 
 }
