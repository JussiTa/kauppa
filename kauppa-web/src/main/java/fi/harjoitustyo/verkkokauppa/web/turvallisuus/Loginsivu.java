/*
 * Kuha, Janne: Tehokas Java EE -sovellustuotanto. WSOY 2008,
 * www.docendo.fi.
 */
package fi.harjoitustyo.verkkokauppa.web.turvallisuus;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

import fi.harjoitustyo.verkkokauppa.web.Sivupohja;


public class Loginsivu extends Sivupohja {

  @SuppressWarnings("unused")
  private static final Log log =
      LogFactory.getLog(Loginsivu.class);

  private static final long serialVersionUID = 1L;

  public Loginsivu(final PageParameters sivunParametrit) {
    super(sivunParametrit);

    // Tilaton sivu
    setStatelessHint(true);

    // Mahdollinen palaute kirjautumisen liittyen
    FeedbackPanel feedbackPanel =
        new FeedbackPanel("palaute");
    add(feedbackPanel);

    // Kirjautumisessa käytettävä lomake
    Kirjautumislomake lomake =
        new Kirjautumislomake("lomake");
    add(lomake);

    // Linkki rekisteröitymistä varten.
    add(new BookmarkablePageLink("rekisteroidy",
        Rekisteroitymissivu.class));
  }

}
