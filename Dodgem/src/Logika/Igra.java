package Logika;

import java.util.LinkedList;
import java.util.List;

public class Igra {
	/**
	 * Velikost igralne plošèe N x N.
	 */
	public static final int N = 5;

	/**
	 * Atributi objekta iz razreda igra.
	 */

	private Polje[][] plosca;
	private Igralec naPotezi;

	/**
	 * Nova igra, v zaèetnem stanju so figurice v hiši.
	 */
	public Igra() {
		plosca = new Polje[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (i == 0 && j != 0) {
					plosca[i][j] = Polje.X;
				}
				else {
					if (j == 0 && i != 0) {
						plosca[i][j] = Polje.Y;
					}
					else {
						plosca[i][j] = Polje.PRAZNO;
					}
				}
			}
		}
		naPotezi = Igralec.X;
	}

	/**
	 * @return seznam možnih potez za X in Y glede na igralca, ki je na potezi
	 * Pazi na orientacijo osi!
	 */

	public List<Poteza> poteze() {
		LinkedList<Poteza> psX = new LinkedList<Poteza>();
		LinkedList<Poteza> psY = new LinkedList<Poteza>();
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (plosca[i][j] == Polje.X) {
					if (plosca[i-1][j] == Polje.PRAZNO && i>0) {
						psX.add(new Poteza(i-1, j, Smer.DESNO));
					}
					if (plosca[i+1][j] == Polje.PRAZNO && i<N-1) {
						psX.add(new Poteza(i+1, j, Smer.LEVO));
					}
					if (plosca[i][j+1] == Polje.PRAZNO) { 
						// morda plosca[i][j+1] sploh ne obstaja! TODO
						psX.add(new Poteza(i, j+1, Smer.NAPREJ));
					}
				}
				if (plosca[i][j] == Polje.Y) {
					if (plosca[i][j-1] == Polje.PRAZNO && j>0) {
						psY.add(new Poteza(i, j-1, Smer.LEVO));
					}
					if (plosca[i][j+1] == Polje.PRAZNO && j<N-1) {
						psY.add(new Poteza(i, j+1, Smer.DESNO));
					}
					if (plosca[i+1][j] == Polje.PRAZNO) {
						// morda plosca[i+1][j] sploh ne obstaja! TODO
						psY.add(new Poteza(i+1, j, Smer.NAPREJ));
					}
				}
			}
		}
		if (naPotezi == Igralec.X) {
			return psX;
		}
		else {	// na potezi je Y
			return psY;
		}
	}
	
	/**
	 * @return stanje igre
	 */
	public Stanje stanje() {
		int steviloAvtomobilovX = 0;
		int steviloAvtomobilovY = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) { 
				//takoj, ko se eno od števil steviloAvtomobilov poveèa, ga ne rabimo veè šteti, naprej bi lahko pregledovali le za drugega igralca
				if (plosca[i][j] == Polje.X) {
					steviloAvtomobilovX += 1;
				}
				if (plosca[i][j] == Polje.Y) {
					steviloAvtomobilovY +=1; 
				}
			}
		}
		if (steviloAvtomobilovX == 0) {
			return Stanje.ZMAGA_X;
		}
		else {
			if (steviloAvtomobilovY == 0) {
				return Stanje.ZMAGA_Y;
			}
			else {
				if (naPotezi == Igralec.X) {
					return Stanje.NA_POTEZI_X;
				}
				else {
					return Stanje.NA_POTEZI_Y;
				}
			}
		}
	}
	
/*
	/**
	 * Odigraj potezo p.
	 * @param p
	 * @return true, èe je bila poteza uspešno odigrana
	 *//*
	public boolean odigraj(Poteza p) {
		
	}

*/
}
