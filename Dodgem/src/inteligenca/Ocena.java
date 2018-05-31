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

	public static final int ZMAGA = 100000000; // vrednost zmage je najve�ja  // ce je v definicije zmage igra.N, stevila N ne moremo spreminjati
	public static final int ZGUBA = -ZMAGA;
	
	public static int oceniPozicijo(Igralec jaz, Igra igra) { // metoda sprejme �e podatek o �tevilu do sedaj odigranih potez
		Igralec naPotezi = null;
		System.out.println("Sem v Oceni pred ocenjevanjem. �tevilo odigranih potez: " + igra.getSteviloOdigranihPotez());
		final int ZMAGA_PRILAGOJENA = ZMAGA - 10 * igra.getSteviloOdigranihPotez();
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
		
		// pre�tejemo, koliko premikov naprej je opravljenih
		
		Polje[][] plosca = igra.getPlosca();

		// da igralec zmaga, mora svoje figure prestaviti naprej (Igra.N * (Igra.N - 1))-krat
		
		int vrednostVertical = 0;
		int vrednostHorizontal = 0;
		int preostalihAvtomobilckovVertical = 0;
		int preostalihAvtomobilckovHorizontal = 0;
		int steviloPraznih = 0;
		
		// �tejemo, kolikokrat se je �e premaknil NAPREJ. 
		// Morda bi poskusili: �tejemo, kolikokrat bi se �e moral.
		for (int i = 0; i < igra.N; i++) {
			for (int j = 0; j < igra.N; j++) {
				switch (plosca[i][j]) {
				case VERTICAL:
					vrednostVertical += (igra.N - 1 - j)*10;
					preostalihAvtomobilckovVertical += 1;
					break;
				case HORIZONTAL:
					vrednostHorizontal += (i)*10;
					preostalihAvtomobilckovHorizontal += 1;
					break;
				case PRAZNO:
					steviloPraznih += 1;
					break;
				}
			}
		}
			
		// Upo�tevamo �e avtomobil�ke, ki so �e zapustili plo��o.
		vrednostVertical = vrednostVertical - 100000 * preostalihAvtomobilckovVertical + steviloPraznih;
		vrednostHorizontal = vrednostHorizontal - 100000 * preostalihAvtomobilckovHorizontal + steviloPraznih;

		return (jaz == Igralec.VERTICAL ? (vrednostVertical) : (vrednostHorizontal));
	}
}
