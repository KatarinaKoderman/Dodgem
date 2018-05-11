package gui;

/**
 * Strateg je objekt, ki zna odigrati potezo. Lahko je �lovek ali ra�unalnik.
 */

public abstract class Strateg {
	/**
	 *  Glavno okno kli�e to metodo, ko je strateg na potezi.
	 */
	public abstract void na_potezi();
	
	/**
	 * Strateg naj neha igrati potezo;
	 */
	public abstract void prekini();
	
	/**
	 * Glavno okno kli�e to metodo, ko uporabnik klikne na polje (i, j).
	 */
	public abstract void klik(int i, int j);
}
