package fi.harjoitustyo.verkkokauppa.tietorakenne;



import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * T‰m‰ luokka toimii pohjana kaikille tietotason
 * sovellusalamalliluokille (domain model).
 * 
 
 */
@MappedSuperclass
public abstract class Entiteetti implements Serializable {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

@Id()
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Integer id = null;

  @Version
  private int versio = 0;

  /**
   * Palauttaa olion perusavaimen arvon.
   * 
   * @return null, mik‰li oliota ei ole viel‰ tallennettu
   *         tietokantaan
   * 
   */
  public Integer getId() {
    return id;
  }

  /**
   * Entiteetin versionumero optimistista lukitusta varten.
   * 
   * @return the version
   */
  public int getVersio() {
    return versio;
  }

  /**
   * Asettaa IDn arvon, mutta oletuksena t‰t‰ metodia ei tule
   * k‰ytt‰‰ luokan, paketin tai siit‰ perittyjen luokkien
   * ulkopuolella.
   * 
   * @param id
   *          the id to set
   */
  protected void setId(final Integer id) {
    this.id = id;
  }
  /**
   * @param version
   *          the version to set
   */
  public void setVersio(final int version) {
    versio = version;
  }

}
