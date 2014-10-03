/*
 * Kuha, Janne: Tehokas Java EE -sovellustuotanto. WSOY 2008,
 * www.docendo.fi.
 */
package fi.harjoitustyo.verkkokauppa.web.ehdotus;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;

/**
 * Abstrakti yliluokka yksinkertaisille automaattisia
 * ehdotuksia tarjoaville kentille.
 * 
 * @author kuha
 * 
 */
public abstract class AutomaattinenEhdotuskentta extends
    AutoCompleteTextField {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
// Maks. hakutuloksia per haku. Voisi olla my�s kontekstin
  // parametrina.
  private static final int MAX_TULOKSIA = 5;

  /**
   * Konstruktori, jonka avulla voidaan m��ritell� k�ytett�v�
   * kent�n tunniste.
   * 
   * @param id
   * @param object
   */
  public AutomaattinenEhdotuskentta(final String id) {
    super(id);
  }

  /**
   * Palauttaa annetun merkkijonon mukaiset ehdotukset.
   * Esimerkiksi, jos k�ytt�j� sy�tt�� maaehdotukseksi
   * merkkijonon"uom", palautetaan k�ytt�j�lle mm."Suomi /
   * Finland".
   */
  
  @SuppressWarnings("rawtypes")
@Override
  protected Iterator getChoices(final String syote) {

    // Tuloksia ei palauteta tyhj�lle merkkijonolle
    if ((syote == null) || (syote.length() == 0)) {
      return Collections.EMPTY_LIST.iterator();
    }

    // Mik�li l�hdejoukon alkion nimi vastaa annettua sy�tett�
    // lis�t��n kyseinen alkio osaksi tuloslistaa.
    final List<String> tulokset = getVaihtoehdot(syote);

    // Ei palauteta tuloksia kuitenkaan enemp�� kuin maks.
    // MAX_TULOKSIA kappaletta.
    if (tulokset.size() > MAX_TULOKSIA) {
      return tulokset.subList(0, MAX_TULOKSIA).iterator();
    }

    return tulokset.iterator();
  }

  /**
   * Palauttaa kyseiselle kent�lle (osittaista) sy�tett�
   * vastaavat vaihtoehdot.
   * 
   * @return Kokonaislista mahdollisista vaihtoehdoista.
   */
  public abstract List<String> getVaihtoehdot(String syote);
}
