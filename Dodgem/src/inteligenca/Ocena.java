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

	public static final int ZMAGA = (1 << ((Igra.N * Igra.N * 10000 - (5 * Igra.steviloOdigranihPotez)))); // vrednost zmage je najve�ja
	public static final int ZGUBA = -ZMAGA;

	public static int oceniPozicijo(Igralec jaz, Igra igra) {

		System.out.println(Igra.steviloOdigranihPotez);
		switch (igra.stanje()) {
		case ZMAGA_VERTICAL:
			return (jaz == Igralec.VERTICAL ? ZMAGA : ZGUBA);
		case ZMAGA_HORIZONTAL: 
			return (jaz == Igralec.HORIZONTAL ? ZMAGA : ZGUBA);
		case NA_POTEZI_VERTICAL:
		case NA_POTEZI_HORIZONTAL: // pre�tejemo, koliko premikov  naprej je opravljenih
			Polje[][] plosca = igra.getPlosca();

			// da igralec zmaga, mora svoje figure prestaviti naprej (Igra.N * (Igra.N - 1))-krat
			int vrednostVertical = 0;
			int vrednostHorizontal = 0;
			int preostalihAvtomobilckovVertical = 0;
			int preostalihAvtomobilckovHorizontal = 0;
			// �tejemo, kolikokrat se je �e premaknil NAPREJ. 
			// Morda bi poskusili: �tejemo, kolikokrat bi se �e moral.
			for (int i = 0; i < Igra.N; i++) {
				for (int j = 0; j < Igra.N; j++) {
					switch (plosca[i][j]) {
					case VERTICAL: vrednostVertical += 10 * (Igra.N - j - 1);
							preostalihAvtomobilckovVertical += 1;
					case HORIZONTAL: vrednostHorizontal += 10 * i;
						preostalihAvtomobilckovHorizontal += 1;
					case PRAZNO:;
					}
				}
			}
			// Upo�tevamo �e avtomobil�ke, ki so �e zapustili plo��o.
			vrednostVertical += 100 * (Igra.N - preostalihAvtomobilckovVertical) * Igra.N;
			vrednostHorizontal += 100 * (Igra.N - preostalihAvtomobilckovHorizontal) * Igra.N;
			return (jaz == Igralec.VERTICAL ? vrednostVertical : -vrednostHorizontal);
		}
		
		// Do spodnjega returna ne moremo priti, saj obravnavamo vse mogo�e primere stanja. Java return vseeno zahteva.
		return 0;
	}
}
