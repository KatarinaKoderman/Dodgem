package inteligenca;

import javax.swing.SwingWorker;

import gui.GlavnoOkno;
import logika.Igra;
import logika.Igralec;
import logika.Poteza;

public class Minimax extends SwingWorker<Poteza, Object> {
	
	private GlavnoOkno master;
	private int globina;
	private Igralec jaz;
	private int steviloPotezOdPrej;
	
	public Minimax(GlavnoOkno master, int globina, Igralec jaz) {
		this.master = master;
		this.globina = globina;
		this.jaz = jaz;
		this.steviloPotezOdPrej = 0;
	}
	
	@Override
	protected Poteza doInBackground() throws Exception {
		Igra igra = master.copyIgra();
		steviloPotezOdPrej = master.copySteviloPotez();
		OcenjenaPoteza p = minimax(0, igra);
		for(int i = 0; i < 1; i++){
			Thread.sleep(500);
		}
		return p.poteza;
	}
	
	@Override
	public void done() {
		try {
			Poteza p = this.get();
			if (p != null) {
				master.odigraj(p); 
			}
		} catch (Exception e) {
		}
	}
	
	/**
	 * poišèe najboljšo potezo v dani igri
	 */
	public OcenjenaPoteza minimax(int k, Igra igra) {
		// System.out.println("minimax, k: " + k);
		Igralec naPotezi = null;
		// Ugotovimo, ali je konec ali je kdo na potezi.
		switch (igra.stanje()) {
			case NA_POTEZI_VERTICAL: naPotezi = Igralec.VERTICAL; break;
			case NA_POTEZI_HORIZONTAL: naPotezi = Igralec.HORIZONTAL; break;
			// Igre je konec, ne moremo vrniti poteze, vrnemo le vrednost pozicije
			case ZMAGA_VERTICAL:
				return new OcenjenaPoteza(
						null,
						(jaz == Igralec.VERTICAL ? Ocena.ZMAGA : Ocena.ZGUBA));
			case ZMAGA_HORIZONTAL:
				return new OcenjenaPoteza(
						null,
						(jaz == Igralec.HORIZONTAL ? Ocena.ZMAGA : Ocena.ZGUBA));
		}
		assert (naPotezi != null);
		
		if (k >= globina) {
			System.out.println("Sem v Minimaxu. Odigranih potez je: " + steviloPotezOdPrej);
			return new OcenjenaPoteza(null, Ocena.oceniPozicijo(jaz, igra, steviloPotezOdPrej));
		}
		
		Poteza najboljsa = null;
		int ocenaNajboljse = 0;
		for (Poteza p : igra.poteze()) {
			Igra kopijaIgre = new Igra(igra);
			kopijaIgre.odigraj(p);
			int ocenaP = minimax(k+1, kopijaIgre).vrednost;
			System.out.println(p.toString() + " ocenaP: " + ocenaP);
			if (najboljsa == null 
					|| (naPotezi == jaz && ocenaP > ocenaNajboljse)
					|| (naPotezi != jaz && ocenaP < ocenaNajboljse)
					) 
				{
					najboljsa = p;
					ocenaNajboljse = ocenaP;
				}
		}
		assert (najboljsa != null);
		return new OcenjenaPoteza(najboljsa, ocenaNajboljse);
		
		
	}


	
}
