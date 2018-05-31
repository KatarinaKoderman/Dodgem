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

	public static final int ZMAGA = 100000000; // vrednost zmage je najveèja  // ce je v definicije zmage igra.N, stevila N ne moremo spreminjati
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

		int steviloOdstranjenihVertical = 0;
		int steviloOdstranjenihHorizontal = 0;
		int steviloPraznih = 0;
		
		// štejemo, kolikokrat se je že premaknil NAPREJ. 
		// Morda bi poskusili: Štejemo, kolikokrat bi se še moral.
		for (int i = 0; i < igra.N; i++) {
			for (int j = 0; j < igra.N; j++) {
				switch (plosca[i][j]) {
				case VERTICAL: vrednostVertical += (igra.N - j)*10;
				preostalihAvtomobilckovVertical += 1;
				case HORIZONTAL: vrednostHorizontal += (i)*10;
				preostalihAvtomobilckovHorizontal += 1;
				case PRAZNO: steviloPraznih += 1;
				}
			}
		}
		steviloOdstranjenihVertical = igra.N - preostalihAvtomobilckovVertical;
		steviloOdstranjenihVertical = igra.N - preostalihAvtomobilckovVertical;				
		// Upoštevamo še avtomobilèke, ki so že zapustili plošèo.
		vrednostVertical = vrednostVertical - 10000 * preostalihAvtomobilckovVertical + steviloPraznih;
		vrednostHorizontal = vrednostHorizontal - 10000 * preostalihAvtomobilckovHorizontal + steviloPraznih;

		return (jaz == Igralec.VERTICAL ? (vrednostVertical) : (vrednostHorizontal));
	}
}
