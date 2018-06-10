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
		
		// Da racunalnik igra na zmago, tudi ce vidi, da bo izgubil, 
		// upostevamo vrednost premikov naprej in preostalih avtomobilckov 
		// ne glede na to, kaksno bo stanje igre.
		
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
		
		// Upostevamo stanje igre in določimo oceno.
		switch (igra.stanje()) {
		case ZMAGA_VERTICAL:
			return (jaz == Igralec.VERTICAL ? (ZMAGA - vrednostHorizontal- igra.getSteviloOdigranihPotez()) : 
				(ZGUBA + vrednostHorizontal + igra.getSteviloOdigranihPotez()));
		case ZMAGA_HORIZONTAL:  
			return (jaz == Igralec.HORIZONTAL ? (ZMAGA - vrednostVertical - igra.getSteviloOdigranihPotez()) : 
				(ZGUBA + vrednostVertical + igra.getSteviloOdigranihPotez()));
		case NA_POTEZI_VERTICAL:
			naPotezi = Igralec.VERTICAL;
		case NA_POTEZI_HORIZONTAL:
			naPotezi = Igralec.HORIZONTAL; }
		
		if (naPotezi == Igralec.HORIZONTAL) { vrednostHorizontal *= 4; }
		if (naPotezi == Igralec.VERTICAL) { vrednostVertical *= 4; }

		return (jaz == Igralec.VERTICAL ? 
				(vrednostVertical - vrednostHorizontal - igra.getSteviloOdigranihPotez()) : 
					(vrednostHorizontal - vrednostVertical - igra.getSteviloOdigranihPotez()));
	}
}