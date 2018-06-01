package gui;

import logika.Smer;

/**
 * Strateg je objekt, ki zna odigrati potezo. Lahko je clovek ali racunalnik.
 */

public abstract class Strateg {
	/**
	 *  Glavno okno klice to metodo, ko je strateg na potezi.
	 */
	public abstract void na_potezi();
	
	/**
	 * Strateg naj neha igrati potezo;
	 */
	public abstract void prekini();
	
	/**
	 * Glavno okno klice to metodo, ko uporabnik klikne na polje (i, j).
	 */
	public abstract void klik(int i, int j, Smer s);
	
	public abstract boolean semClovek();
}