/*
 * Kuha, Janne: Tehokas Java EE -sovellustuotanto. WSOY 2008,
 * www.docendo.fi.
 */
package fi.harjoitustyo.verkkokauppa.web.turvallisuus;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;

import fi.harjoitustyo.verkkokauppa.web.Sivupohja;

/**
 * Sivu, joka kirjaa k�ytt�j�n ulos j�rjestelm�st� ja
 * tyhjent�� samalla mahdollisesti k�ytt�j�n sessiossa olevat
 * asiat.
 * 
 */
public class Logoutsivu extends Sivupohja {

  @SuppressWarnings("unused")
  private static final Log log =
      LogFactory.getLog(Logoutsivu.class);

  private static final long serialVersionUID = 1L;

  public Logoutsivu(final PageParameters sivunParametrit) {
    super(sivunParametrit);

    // Tyhjennet��n k�ytt�j�n sessio kokonaisuudessaan.
    getSession().invalidate();

    // Linkki rekister�itymist� varten.
    add(new BookmarkablePageLink("loginUudestaanLinkki",
        Loginsivu.class));
  }

}
