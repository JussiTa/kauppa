/*
 * Pohjautuu Janne Kuhan kirjaan Tehokas Sovellustuotanto
 */
package fi.harjoitustyo.verkkokauppa.tietorakenne;

import java.io.Serializable;

/**
 * Käyttäjän roolin ilmaiseva enum.
 * 
 * @author Jussi Isokangas
 * 
 */
public enum Rooli implements Serializable {

  ASIAKAS(), ADMIN(), VIERAILIJA();

  Rooli() {
  }

}
