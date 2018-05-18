package logika;

import java.util.LinkedList;
import java.util.List;

public class Igra {
	/**
	 * Velikost igralne plo��e N x N.
	 */
	public static final int N = 5;

	/**
	 * Atributi objekta iz razreda igra.
	 */

	private Polje[][] plosca;
	private Igralec naPotezi;

	/**
	 * Nova igra, v za�etnem stanju so figurice prvega igralca (igralec X) 
	 * postavljene na dnu plo��e, figurice drugega igralca (igralec Y)
	 * pa na levi strani plo��e.
	 */
	public Igra() {
		plosca = new Polje[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (j == (N-1) && i != 0) {
					plosca[i][j] = Polje.X;
				}
				else {
					if (i == 0 && j != (N-1)) {
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
	 * @return seznam mo�nih potez za X in Y glede na igralca, ki je na potezi
	 * Pazi na orientacijo osi!
	 */
	public List<Poteza> poteze() {
		LinkedList<Poteza> psX = new LinkedList<Poteza>(); // poteze od tistega, ki gre gor
		LinkedList<Poteza> psY = new LinkedList<Poteza>(); // poteze od tistega, ki gre desno
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (plosca[i][j] == Polje.X) {
					if (i > 0 && plosca[i-1][j] == Polje.PRAZNO) {
						psX.add(new Poteza(i-1, j, Smer.LEVO));
					}
					if (i<N-1 && plosca[i+1][j] == Polje.PRAZNO) {
						psX.add(new Poteza(i+1, j, Smer.DESNO));
					}
					if (j>0 && plosca[i][j-1] == Polje.PRAZNO) { 
						psX.add(new Poteza(i, j-1, Smer.GOR));
					}
					if (j==0) {
						psX.add(new Poteza(i, j-1, Smer.ODSTRANI));
					}
				}
				if (plosca[i][j] == Polje.Y) {
					if (j>0 && plosca[i][j-1] == Polje.PRAZNO) {
						psY.add(new Poteza(i, j-1, Smer.GOR));
					}
					if (j<N-1 && plosca[i][j+1] == Polje.PRAZNO) {
						psY.add(new Poteza(i, j+1, Smer.DOL));
					}
					if (i<N-1 && plosca[i+1][j] == Polje.PRAZNO) {
						psY.add(new Poteza(i+1, j, Smer.DESNO));
					}
					if (i==N-1) {
						psY.add(new Poteza(i+1, j, Smer.ODSTRANI));
					}
				}
			}
		}
		if (naPotezi == Igralec.X) {
			return psX;
		}
		else {    // na potezi je Y
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
				// takoj, ko se eno od �tevil steviloAvtomobilov pove�a, ga ne rabimo ve� �teti, naprej bi lahko pregledovali le za drugega igralca
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


	/**
	 * Odigraj potezo p.
	 * 
	 * @param p
	 * @return true, �e je bila poteza uspe�no odigrana
	 */
	public boolean odigraj(Poteza p) {
		if (plosca[p.getX()][p.getY()] != Polje.PRAZNO) {
			int x = p.getX();
			int y = p.getY();
			
			if (naPotezi == Igralec.X) {
//				switch(p.getSmer()) {
//				case Smer.LEVO: 
//					// Najprej preverimo, ali je ta poteza dovoljena:
//					// 1. ali je na polju x, y res X
//					// 2. ali je levi rob?
//					// 3. če ni, ali je na levi prazno polje?
//					// v CELOTI naredimo VSE, da izvedemo to potezo:
//					// spremenimo dve polji (eno na prazno, eno na X)
//					// return true
//				case Smer.DESNO: break;
//				case Smer.NAPREJ: break;
//				}
				
				if(p.getSmer() == Smer.LEVO) x -= 1;
				if(p.getSmer() == Smer.GOR) y -= 1;
				if(p.getSmer() == Smer.DESNO) x += 1;
				else; // Smer.ODSTRANI -> koordinat ne spremenimo, polje samo spraznimo
			}
			
			else {  
				if(p.getSmer() == Smer.GOR) y -= 1;
				if(p.getSmer() == Smer.DESNO) x += 1;
				if(p.getSmer() == Smer.DOL) y += 1;
				else;	// Smer.ODSTRANI
			}
			
			if (x == p.getX() && y == p.getY()) {
				plosca[x][y] = Polje.PRAZNO;
			}
			else {
			plosca[x][y] = plosca[p.getX()][p.getY()];
			plosca[p.getX()][p.getY()] = Polje.PRAZNO;
			}
			
			naPotezi = naPotezi.nasprotnik();
			return true;
		}
		else {
			return false;
		}
	}
} 
      
 