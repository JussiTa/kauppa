package fi.harjoitustyo.verkkokauppa.tietotaso.toteutus;

public class TuotettaEiOleRiittavastiException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	   * Peruskonstruktori.
	   */
	  public TuotettaEiOleRiittavastiException() {
	    super();
	  }

	  /**
	   * Konstruktori, jossa välitetään mukana tuotteennimi.
	   * 
	   * @param arg0
	   */
	  public TuotettaEiOleRiittavastiException(final String tuotteennimi) {
	    super(tuotteennimi);
	  }

	
	
	

}
