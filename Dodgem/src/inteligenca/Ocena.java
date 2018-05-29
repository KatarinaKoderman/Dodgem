package inteligenca;

import logika.Igra;
import logika.Igralec;
import logika.Polje;

/**
 * Ocena trenutne pozicije
 * 
 * @author KodermanK15
 *
 */
public class Ocena {
	public static final int ZMAGA = (1 << (Igra.N * Igra.N * 10000)); // vrednost zmage je najveèja
	public static final int ZGUBA = -ZMAGA;

	public static int oceniPozicijo(Igralec jaz, Igra igra, int steviloOdigranihPotez) { // metoda sprejme še podatek o številu do sedaj odigranih potez
		Igralec naPotezi = null;
		int odigranePoteze = steviloOdigranihPotez;
		System.out.println("Sem v Oceni pred ocenjevanjem. Število odigranih potez: " + steviloOdigranihPotez);
		final int ZMAGA_PRILAGOJENA = ZMAGA - 10 * odigranePoteze;
		final int ZGUBA_PRILAGOJENA = - ZMAGA_PRILAGOJENA;
		
		switch (igra.stanje()) {
		case ZMAGA_VERTICAL:
			return (jaz == Igralec.VERTICAL ? (ZMAGA_PRILAGOJENA) : (ZGUBA_PRILAGOJENA));
		case ZMAGA_HORIZONTAL: 
			return (jaz == Igralec.HORIZONTAL ? (ZMAGA_PRILAGOJENA) : (ZGUBA_PRILAGOJENA));
		case NA_POTEZI_VERTICAL:
			naPotezi = Igralec.VERTICAL;
		case NA_POTEZI_HORIZONTAL:
			naPotezi = Igralec.HORIZONTAL; }
		
		// preštejemo, koliko premikov naprej je opravljenih
		
		Polje[][] plosca = igra.getPlosca();

		// da igralec zmaga, mora svoje figure prestaviti naprej (Igra.N * (Igra.N - 1))-krat
		
		int vrednostVertical = 0;
		int vrednostHorizontal = 0;
		int preostalihAvtomobilckovVertical = 0;
		int preostalihAvtomobilckovHorizontal = 0;
		// štejemo, kolikokrat se je že premaknil NAPREJ. 
		// Morda bi poskusili: Štejemo, kolikokrat bi se še moral.
		for (int i = 0; i < Igra.N; i++) {
			for (int j = 0; j < Igra.N; j++) {
				switch (plosca[i][j]) {
				case VERTICAL: vrednostVertical += (Igra.N - j - 1);
				preostalihAvtomobilckovVertical += 1;
				case HORIZONTAL: vrednostHorizontal += i;
				preostalihAvtomobilckovHorizontal += 1;
				case PRAZNO:;
				}
			}
		}
		// Upoštevamo še avtomobilèke, ki so že zapustili plošèo.
		vrednostVertical = vrednostVertical + 100 * Igra.N * preostalihAvtomobilckovVertical - 10 * odigranePoteze;
		vrednostHorizontal = vrednostHorizontal + 100 * Igra.N * preostalihAvtomobilckovHorizontal - 10 * odigranePoteze;
		if (naPotezi == Igralec.VERTICAL) {vrednostHorizontal /= 2;}
		if (naPotezi == Igralec.HORIZONTAL) {vrednostVertical /= 2;}
		return (jaz == Igralec.VERTICAL ? (vrednostVertical) : (vrednostHorizontal));
	}
}
