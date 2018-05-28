package logika;

import java.util.LinkedList;
import java.util.List;

public class Igra {
	/**
	 * Velikost igralne plo��e N x N.
	 */
	public static final int N = 3;

	/**
	 * Atributi objekta iz razreda igra.
	 */

	public Polje[][] plosca;
	public Igralec naPotezi;
	public static int steviloOdigranihPotez = 0;
	
	/**
	 * Nova igra, v za�etnem stanju so figurice prvega igralca (igralec VERTICAL) 
	 * postavljene na dnu plo��e, figurice drugega igralca (igralec HORIZONTAL)
	 * pa na levi strani plo��e.
	 */
	public Igra() {
		plosca = new Polje[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (j == (N-1) && i != 0) {
					plosca[i][j] = Polje.VERTICAL;
				}
				else {
					if (i == 0 && j != (N-1)) {
						plosca[i][j] = Polje.HORIZONTAL;
					}
					else {
						plosca[i][j] = Polje.PRAZNO;
					}
				}
			}
		}
		naPotezi = Igralec.VERTICAL;
	}


	/**
	 * @param igra nova kopija dane igre
	 */
	public Igra(Igra igra) {
		plosca = new Polje[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				plosca[i][j] = igra.plosca[i][j];
			}
		}
		this.naPotezi = igra.naPotezi;
	}

	public Polje[][] getPlosca() {
		return plosca; 
	}


	/**
	 * @return seznam mo�nih potez za igralca, ki je na potezi
	 * Pazi na orientacijo osi!
	 */
	public List<Poteza> poteze() {
		LinkedList<Poteza> veljavnePoteze = new LinkedList<Poteza>();
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (naPotezi == Igralec.VERTICAL && plosca[i][j] == Polje.VERTICAL) {
					if (i > 0 && plosca[i-1][j] == Polje.PRAZNO) {
						veljavnePoteze.add(new Poteza(i, j, Smer.LEVO));
					}
					if (i<N-1 && plosca[i+1][j] == Polje.PRAZNO) {
						veljavnePoteze.add(new Poteza(i, j, Smer.DESNO));
					}
					if ((j>0 && plosca[i][j-1] == Polje.PRAZNO) || (j==0)) { 
						veljavnePoteze.add(new Poteza(i, j, Smer.NAPREJ));
					}
				}
				if (naPotezi == Igralec.HORIZONTAL && plosca[i][j] == Polje.HORIZONTAL) {
					if (j>0 && plosca[i][j-1] == Polje.PRAZNO) {
						veljavnePoteze.add(new Poteza(i, j, Smer.LEVO));
					}
					if (j<N-1 && plosca[i][j+1] == Polje.PRAZNO) {
						veljavnePoteze.add(new Poteza(i, j, Smer.DESNO));
					}
					if ((i<N-1 && plosca[i+1][j] == Polje.PRAZNO) || (i==N-1)) {
						veljavnePoteze.add(new Poteza(i, j, Smer.NAPREJ));
					}
				}
			}
		}
		return veljavnePoteze;
	}


	/**
	 * @return stanje igre
	 */
	// 	TODO ali smemo stanje gledati z dovoljenimi potezami? Raje ne.
	// TODO dodati je treba, kdo je na potezi
	public Stanje stanje() {
		int steviloPotezVERTICAL = 0;
		int steviloPotezHORIZONTAL = 0;
		int steviloAvtomobilovVERTICAL = 0;
		int steviloAvtomobilovHORIZONTAL = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) { 
				// izbolj�ava: takoj, ko se eno od �tevil steviloAvtomobilov pove�a, ga ne rabimo ve� �teti, naprej bi lahko pregledovali le za drugega igralca
				if (plosca[i][j] == Polje.VERTICAL) {
					steviloAvtomobilovVERTICAL += 1;
					// Obstaja figura, ki pripada VERTICAL. �e za to figuro obstaja poteza, jo �tejemo, sicer ne.
					if (i > 0 && plosca[i-1][j] == Polje.PRAZNO) { // nismo na levem robu in lahko se pomaknemo levo
						steviloPotezVERTICAL += 1;
						continue;
					}
					if (i < N-1 && plosca[i+1][j] == Polje.PRAZNO) { // nismo na desnem robu in lahko se pomaknemo desno
						steviloPotezVERTICAL += 1;
						continue;
					}
					if (j == 0 || plosca[i][j-1] == Polje.PRAZNO) { // lahko se pomaknemo naprej
						steviloPotezVERTICAL += 1;
						continue;
					}
				}
				if (plosca[i][j] == Polje.HORIZONTAL) {
					steviloAvtomobilovHORIZONTAL += 1;	
					// Obstaja figura, ki pripada HORIZONTAL. �e za to figuro obstaja poteza, jo �tejemo, sicer ne.
					if (j > 0 && plosca[i][j-1] == Polje.PRAZNO) { // nismo na zgornjem robu in lahko se pomaknemo levo
						steviloPotezHORIZONTAL += 1;
						continue;
					}
					if (j < N-1 && plosca[i][j+1] == Polje.PRAZNO) { // nismo na spodnjem robu in lahko se pomaknemo desno
						steviloPotezHORIZONTAL += 1;
						continue;
					}
					if ((i == N-1) || (plosca[i+1][j] == Polje.PRAZNO)) { // lahko se pomaknemo naprej
						steviloPotezHORIZONTAL += 1;
						continue;
					}
				}
			}
		}
		if (steviloAvtomobilovVERTICAL == 0 || (steviloPotezVERTICAL == 0 && naPotezi == Igralec.VERTICAL)) { 
			// VERTICAL je vse svoje avtomobil�ke spravil s plo��e in je zmagal ALI na potezi je VERTICAL in ne more izvesti nobene poteze
			return Stanje.ZMAGA_VERTICAL;
		}
		else {
			if (steviloAvtomobilovHORIZONTAL == 0 || (steviloPotezHORIZONTAL == 0 && naPotezi == Igralec.HORIZONTAL)) {
				return Stanje.ZMAGA_HORIZONTAL;
			}
			else {
				if (naPotezi == Igralec.VERTICAL) {
					return Stanje.NA_POTEZI_VERTICAL;
				}
				else {
					return Stanje.NA_POTEZI_HORIZONTAL;
				}
			}
		}
	}


	/**
	 * Odigraj potezo p.
	 * 
	 * @param p
	 * @return true, �e je bila poteza uspe�no odigrana
	 */
	public boolean odigraj(Poteza p) {
		assert (naPotezi != null); // te metode ne smemo klicati, �e ni nih�e na potezi
		if (plosca[p.getX()][p.getY()] != naPotezi.getPolje()) {
			return false;
		}
		else {
			int x = p.getX();
			int y = p.getY();
			if (naPotezi == Igralec.VERTICAL) {
				switch(p.getSmer()) {					
				// Za vsako mo�no smer preverimo, �e je poteza dovoljena:
				// 1. ali je na polju (x, y) res VERTICAL
				// 2. Ali smo na (levem/desnem/zgornjem) robu?
				// 3. �e nismo na robu, ali je na (levi/desni/naprej) prazno polje?
				// Izvedemo potezo: spremenimo obe polji (eno na prazno, eno na VERTICAL), pri smeri NAPREJ lahko v dolo�eni situaciji spremenimo le eno polje
				// return true
				case LEVO:
					if (x == 0) { // smo na levem robu, ne moremo levo
						return false;
					}
					else { // nismo na levem robu
						if (plosca[x-1][y] != Polje.PRAZNO) { // ne moremo levo, ker polje tam ni prazno
							return false;
						}
						else { // polje na levi je prazno, izvedemo potezo
							plosca[x][y] = Polje.PRAZNO;
							plosca[x-1][y] = Polje.VERTICAL;
							naPotezi = naPotezi.nasprotnik();
							steviloOdigranihPotez += 1;
							return true;
						}
					}
				case DESNO:
					if (x == N-1) { // smo na desnem robu, ne moremo desno
						return false;
					}
					else { // nismo na desnem robu
						if (plosca[x+1][y] != Polje.PRAZNO) { // ne moremo desno, ker polje tam ni prazno
							return false;
						}
						else { // polje na desni je prazno, izvedemo potezo
							plosca[x][y] = Polje.PRAZNO;
							plosca[x+1][y] = Polje.VERTICAL;
							naPotezi = naPotezi.nasprotnik();
							steviloOdigranihPotez += 1;
							return true;
						}
					}
				case NAPREJ:
					if (y == 0) { // smo na zgornjem robu, premik naprej odstrani avtomobil�ek
						plosca[x][y] = Polje.PRAZNO;
						naPotezi = naPotezi.nasprotnik();
						steviloOdigranihPotez += 1;
						return true;
					}
					else { // nismo na zgornjem robu
						if (plosca[x][y-1] != Polje.PRAZNO) { // ne moremo naprej, ker polje tam ni prazno
							return false;
						}
						else { // polje naprej je prazno, izvedemo potezo
							plosca[x][y] = Polje.PRAZNO;
							plosca[x][y-1] = Polje.VERTICAL;
							naPotezi = naPotezi.nasprotnik();
							steviloOdigranihPotez += 1;
							return true;
						}
					}
				} // switch
			}
			else { // na potezi je igralec HORIZONTAL
				// Ali se lahko zgodi, da nimamo igralca?
				if (plosca[x][y] != Polje.HORIZONTAL) { // na polju (x, y) ni igralec HORIZONTAL
					return false;
				}
				else { // na polju (x, y) je avtomobil�ek, ki pripada HORIZONTAL
					switch(p.getSmer()) { 
					case LEVO:
						if (y == 0) { // smo na zgornjem (glede na orientacijo tega avta je to na levem) robu, ne moremo levo
							return false;
						}
						else { // nismo na levem robu
							if (plosca[x][y-1] != Polje.PRAZNO) { // ne moremo levo, ker polje tam ni prazno
								return false;
							}
							else { // polje na levi je prazno, izvedemo potezo
								plosca[x][y] = Polje.PRAZNO;
								plosca[x][y-1] = Polje.HORIZONTAL;
								naPotezi = naPotezi.nasprotnik();
								steviloOdigranihPotez += 1;
								return true;
							}
						}
					case DESNO:
						if (y==N-1) { // smo na spodnjem (glede na orientacijo tega avta je to na desnem) robu, ne moremo desno
							return false;
						}
						else { // nismo na desnem robu
							if (plosca[x][y+1] != Polje.PRAZNO) { // ne moremo desno, ker polje tam ni prazno
								return false;
							}
							else { // polje na desni je prazno, izvedemo potezo
								plosca[x][y] = Polje.PRAZNO;
								plosca[x][y+1] = Polje.HORIZONTAL;
								naPotezi = naPotezi.nasprotnik();
								steviloOdigranihPotez += 1;
								return true;
							}
						}
					case NAPREJ:
						if (x==N-1) { // smo na desnem robu, premik naprej odstrani avtomobil�ek
							plosca[x][y] = Polje.PRAZNO;
							naPotezi = naPotezi.nasprotnik();
							steviloOdigranihPotez += 1;
							return true;
						}
						else { // nismo na desnem robu
							if (plosca[x+1][y] != Polje.PRAZNO) { //ne moremo naprej, ker polje tam ni prazno
								return false;
							}
							else { // polje naprej je prazno, izvedemo potezo
								plosca[x][y] = Polje.PRAZNO;
								plosca[x+1][y] = Polje.HORIZONTAL;
								naPotezi = naPotezi.nasprotnik();
								steviloOdigranihPotez += 1;
								return true;
							}
						}
					} // switch
				}
			}
		}
		assert false; // Java je neumna
		return false; // Nikoli ne pridemo do sem.
	}
}

