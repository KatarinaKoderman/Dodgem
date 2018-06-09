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

	public static final int ZMAGA = 100000000; // vrednost zmage je najvecja = 100 milijonov 
	// ce je v definicije zmage igra.N, stevila N ne moremo spreminjati
	public static final int ZGUBA = -ZMAGA;

	public static int oceniPozicijo(Igralec jaz, Igra igra) {
		Igralec naPotezi = null;
		int utez = 10;
		final int ZMAGA_PRILAGOJENA = ZMAGA - utez * igra.getSteviloOdigranihPotez();
		final int ZGUBA_PRILAGOJENA = - ZMAGA_PRILAGOJENA;
		
		// Ko računalnik vidi, da bo izgubil, se neha truditi za zmago. 
		// Ker nas zanima samo zmaga/zguba, ne zanima pa nas število preostalih premikov naravnost, 
		// ki bi vodili do zmage, tega pri izračunu ocene ne upoštevamo.
		switch (igra.stanje()) {
		case ZMAGA_VERTICAL:
			return (jaz == Igralec.VERTICAL ? (ZMAGA_PRILAGOJENA) : (ZGUBA_PRILAGOJENA));
		case ZMAGA_HORIZONTAL:  
			return (jaz == Igralec.HORIZONTAL ? (ZMAGA_PRILAGOJENA) : (ZGUBA_PRILAGOJENA));
		case NA_POTEZI_VERTICAL:
			naPotezi = Igralec.VERTICAL;
		case NA_POTEZI_HORIZONTAL:
			naPotezi = Igralec.HORIZONTAL; }

		// Prestejemo, koliko premikov naprej je opravljenih.
		Polje[][] plosca = igra.getPlosca();

		// da igralec zmaga, mora svoje figure prestaviti naprej (Igra.N * (Igra.N - 1))-krat
		int vrednostVertical = 0;
		int vrednostHorizontal = 0;
		int preostalihAvtomobilckovVertical = 0;
		int preostalihAvtomobilckovHorizontal = 0;

		// Stejemo, kolikokrat se je ze premaknil NAPREJ.
		for (int i = 0; i < igra.N; i++) {
			for (int j = 0; j < igra.N; j++) {
				switch (plosca[i][j]) {
				case VERTICAL:
					vrednostVertical += (igra.N - 1 - j) * utez;
					preostalihAvtomobilckovVertical += 1;
					break;
				case HORIZONTAL:
					vrednostHorizontal += (i) * utez;
					preostalihAvtomobilckovHorizontal += 1;
					break;
				case PRAZNO:
					break;
				}
			}
		}

		// Upostevamo avtomobilcke, ki so ze zapustili plosco.
		vrednostVertical = vrednostVertical +  utez * igra.N * (igra.N - 1 - preostalihAvtomobilckovVertical);
		vrednostHorizontal = vrednostHorizontal + utez * igra.N * (igra.N - 1 - preostalihAvtomobilckovHorizontal);

		if (naPotezi == Igralec.HORIZONTAL) { vrednostHorizontal *= 4; }
		if (naPotezi == Igralec.VERTICAL) { vrednostVertical *= 4; }

		return (jaz == Igralec.VERTICAL ? 
				(vrednostVertical - vrednostHorizontal - igra.getSteviloOdigranihPotez()) : 
					(vrednostHorizontal - vrednostVertical - igra.getSteviloOdigranihPotez()));
	}
}