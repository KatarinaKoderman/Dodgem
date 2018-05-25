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
	static int N = Igra.N;
	static int vr;
	
	public static final int ZMAGA = (1 << Igra.N * Igra.N); // vrednost zmage je najveèja
	public static final int ZGUBA = -ZMAGA;

	public static int oceniPozicijo(Igralec jaz, Igra igra) {
		switch (igra.stanje()) {
		case ZMAGA_VERTICAL:
			return (jaz == Igralec.VERTICAL ? ZMAGA : ZGUBA);
		case ZMAGA_HORIZONTAL: 
			return (jaz == Igralec.HORIZONTAL ? ZMAGA : ZGUBA);
		case NA_POTEZI_VERTICAL:
		case NA_POTEZI_HORIZONTAL: // preštejemo, koliko premikov naprej je do zmage
			Polje[][] plosca = igra.getPlosca();
			
			
			// da igralec zmaga, mora svoje figure prestaviti naprej (Igra.N * (Igra.N - 1))-krat
			int vrednostVertical = Igra.N * (Igra.N - 1);
			int vrednostHorizontal = Igra.N * (Igra.N - 1);
			
			for (int i = 0; i < Igra.N; i++) {
				for (int j = 0; j < Igra.N; j++) {
					switch (plosca[i][j]) {
					case VERTICAL: vrednostVertical -= (Igra.N - j);
					case HORIZONTAL: vrednostHorizontal -= (i);
					case PRAZNO:;
					}
				}
			}
//			vr = vrednostVertical - vrednostHorizontal;
			return (jaz == Igralec.VERTICAL ? vrednostVertical : -vrednostHorizontal);
	}
	// java je še vedno vredi 
		// Matej! Kot je Bauer povedal, je programiranje v paru kot zakon. In moje zaupanje si izgubil! 
		// Ne samo, da si dal nekomu za popravljat kodo brez moje vednosti in si kodo tudi commital, 
		// še moji komentarji so bili popravljeni! Komentar je z razlogom na svojem mestu! 
		// (java je še vedno vredi)! Tale projekt, potem me pa niti ne pozdravi veè!
	return 0; // TODO 0 verjetno ni primerna vrednost, premisli še enkrat zgornje vrednosti
}
}
