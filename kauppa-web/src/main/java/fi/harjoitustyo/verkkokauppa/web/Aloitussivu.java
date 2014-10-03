/*
 * Kuha, Janne: Tehokas Java EE -sovellustuotanto. WSOY 2008,
 * www.docendo.fi.
 */
package fi.harjoitustyo.verkkokauppa.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.PageParameters;

/**
 * Hotellisovelluksen sivu, joka n‰ytt‰‰ t‰ll‰ hetkell‰
 * vapaana olevat huoneet. Muutettu k‰ytt‰m‰‰n yleist‰
 * sivulayouttia.
 */
public class Aloitussivu extends Sivupohja {

  @SuppressWarnings("unused")
  private static final Log log =
      LogFactory.getLog(Aloitussivu.class);

  private static final long serialVersionUID = 1L;

  /**
   * Konstruktori, jokaa hakee vapaat huoneet palvelun avulla
   * ja muuntaa tulokset Wicketin web-malliksi.
   * 
   * @param sivunParametrit
   */
  public Aloitussivu(final PageParameters sivunParametrit) {
    super(sivunParametrit);
    
  
  }
  
  public void whateverMethod()
  {
   
  }



}
