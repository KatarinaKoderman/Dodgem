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
	static int vrednostVertical;
	static int vrednostHorizontal;
	static int vr;

	public static final int ZMAGA = (1 << ((Igra.N * Igra.N * 1000) - (10 * Igra.steviloOdigranihPotez))); // vrednost zmage je najveèja
	public static final int ZGUBA = -ZMAGA;

	public static int oceniPozicijo(Igralec jaz, Igra igra) {
		switch (igra.stanje()) {
		case ZMAGA_VERTICAL:
			return (jaz == Igralec.VERTICAL ? ZMAGA : ZGUBA);
		case ZMAGA_HORIZONTAL: 
			return (jaz == Igralec.HORIZONTAL ? ZMAGA : ZGUBA);
		case NA_POTEZI_VERTICAL:
		case NA_POTEZI_HORIZONTAL: // preštejemo, koliko premikov  naprej je opravljenih
			Polje[][] plosca = igra.getPlosca();

			// da igralec zmaga, mora svoje figure prestaviti naprej (Igra.N * (Igra.N - 1))-krat
			int vrednostVertical = 0;
			int vrednostHorizontal = 0;
			// štejemo, kolikokrat se je že premaknil NAPREJ. 
			// Morda bi poskusili: Štejemo, kolikokrat bi se še moral.
			for (int i = 0; i < Igra.N; i++) {
				for (int j = 0; j < Igra.N; j++) {
					switch (plosca[i][j]) {
					case VERTICAL: vrednostVertical += (Igra.N - j - 1);
					case HORIZONTAL: vrednostHorizontal += i;
					case PRAZNO:;
					}
				}
			}
			//			vr = vrednostVertical - vrednostHorizontal;
			return (jaz == Igralec.VERTICAL ? vrednostVertical : -vrednostHorizontal);
		}
		
		// Do spodnjega returna ne moremo priti, saj obravnavamo vse mogoèe primere stanja. Java return vseeno zahteva.
		return 0;
	}
}
