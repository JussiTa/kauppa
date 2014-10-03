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
// Maks. hakutuloksia per haku. Voisi olla myös kontekstin
  // parametrina.
  private static final int MAX_TULOKSIA = 5;

  /**
   * Konstruktori, jonka avulla voidaan määritellä käytettävä
   * kentän tunniste.
   * 
   * @param id
   * @param object
   */
  public AutomaattinenEhdotuskentta(final String id) {
    super(id);
  }

  /**
   * Palauttaa annetun merkkijonon mukaiset ehdotukset.
   * Esimerkiksi, jos käyttäjä syöttää maaehdotukseksi
   * merkkijonon"uom", palautetaan käyttäjälle mm."Suomi /
   * Finland".
   */
  
  @SuppressWarnings("rawtypes")
@Override
  protected Iterator getChoices(final String syote) {

    // Tuloksia ei palauteta tyhjälle merkkijonolle
    if ((syote == null) || (syote.length() == 0)) {
      return Collections.EMPTY_LIST.iterator();
    }

    // Mikäli lähdejoukon alkion nimi vastaa annettua syötettä
    // lisätään kyseinen alkio osaksi tuloslistaa.
    final List<String> tulokset = getVaihtoehdot(syote);

    // Ei palauteta tuloksia kuitenkaan enempää kuin maks.
    // MAX_TULOKSIA kappaletta.
    if (tulokset.size() > MAX_TULOKSIA) {
      return tulokset.subList(0, MAX_TULOKSIA).iterator();
    }

    return tulokset.iterator();
  }

  /**
   * Palauttaa kyseiselle kentälle (osittaista) syötettä
   * vastaavat vaihtoehdot.
   * 
   * @return Kokonaislista mahdollisista vaihtoehdoista.
   */
  public abstract List<String> getVaihtoehdot(String syote);
}
