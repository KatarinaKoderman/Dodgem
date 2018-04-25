package Logika;

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
	 * @return seznam možnih potez. LinkedList bo sestavljen iz 4-ih komponent: izvorno stanje (x, y) ter možna poteza, zopet z dvema koordinatama.
	 */
	
	

}
