package fi.harjoitustyo.verkkokauppa.tietorakenne;


import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

/**
 * Web-kaupan hallinoija, mikä laajentaa käyttäjää. Tarvitsee henkilötunnuksen.
 * 
 * 
 * @author Jussi Isokangas
 * 
 */
@Entity
@Table(name = "admin")
@DiscriminatorValue(value = "admin")
public class Admin extends Kayttaja {

  private static final long serialVersionUID = 1L;

  @NotNull
  @Column(nullable = false)
  private String henkilotunnus;

  

  /**
   * Peruskonstruktori, jota mm. Hibernate käyttää.
   */
  public Admin() {
    this(null, null, null);
  }

  /**
   * Konstruktori, jonka kautta voi asettaa useita oleellisia
   * henkilökunnan tietoja.
   * 
   * @param tunnus
   * @param etunimi
   * @param sukunimi
   */
  public Admin(final String tunnus,
      final String etunimi, final String sukunimi) {
    super(tunnus, etunimi, sukunimi);
  }

  /**
   * @return the henkilotunnus
   */
  public String getHenkilotunnus() {
    return henkilotunnus;
  }

  /**
   * Palauttaa käyttäjän roolin.
   * 
   * @return
   */
  @Override
  public Rooli getRooli() {
    return Rooli.ADMIN;
  }

  

  /**
   * @param henkilotunnus
   *          the henkilotunnus to set
   */
  public void setHenkilotunnus(final String henkilotunnus) {
    this.henkilotunnus = henkilotunnus;
  }

  
}