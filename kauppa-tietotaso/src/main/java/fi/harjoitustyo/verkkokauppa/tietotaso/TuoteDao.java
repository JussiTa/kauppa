package fi.harjoitustyo.verkkokauppa.tietotaso;





import java.util.List;

import fi.harjoitustyo.verkkokauppa.tietorakenne.Tuote;
import fi.harjoitustyo.verkkokauppa.tietotaso.toteutus.TuotettaEiOleRiittavastiException;

/**
 * Rajapinta tuotteiden tietokantakäsittelyä varten
 * 
 * @author Jussi Isokangas
 * 
 */
public interface TuoteDao extends YleinenDao<Tuote> {

  
  public Tuote haeTuote(String hakusana);
       
  public List<Tuote> haeTuoteTuoteryhmanMukaan(final String tuoteryhma);
  
 
  
  public void lisaaTuoteTilaukseen(int id, int kpl)
  throws TuotettaEiOleRiittavastiException;
  /*
  public void paivitaLkmTuotteelle(Integer id,int lkm);
 */
}
