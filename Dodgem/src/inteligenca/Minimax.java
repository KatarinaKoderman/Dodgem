package inteligenca;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.SwingWorker;

import gui.GlavnoOkno;
import logika.Igra;
import logika.Igralec;
import logika.Poteza;

public class Minimax extends SwingWorker<Poteza, Object> {

	private GlavnoOkno master;
	private int globina;
	private Igralec jaz;

	public Minimax(GlavnoOkno master, int globina, Igralec jaz) {
		this.master = master;
		this.globina = globina;
		this.jaz = jaz;
	}

	@Override
	protected Poteza doInBackground() throws Exception {
		Igra igra = master.copyIgra();
		OcenjenaPoteza p = minimax(0, igra);
		System.out.println("Minimax: " + p);
		Thread.sleep(100);
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
	 * poisce najboljso potezo v dani igri
	 */
	public OcenjenaPoteza minimax(int k, Igra igra) {
		Igralec naPotezi = null;
		// Ugotovimo, ali je konec ali je kdo na potezi.
		switch (igra.stanje()) {
		case NA_POTEZI_VERTICAL: naPotezi = Igralec.VERTICAL; break;
		case NA_POTEZI_HORIZONTAL: naPotezi = Igralec.HORIZONTAL; break;
		// Igre je konec, ne moremo vrniti poteze, vrnemo le vrednost pozicije
		case ZMAGA_VERTICAL:
			return new OcenjenaPoteza(null, Ocena.oceniPozicijo(jaz, igra));
		case ZMAGA_HORIZONTAL:
			return new OcenjenaPoteza(null, Ocena.oceniPozicijo(jaz, igra));
		}
		assert (naPotezi != null);

		if (k >= globina) {
			return new OcenjenaPoteza(null, Ocena.oceniPozicijo(jaz, igra));
		}

		LinkedList<Poteza> najboljsePoteze = new LinkedList<Poteza>();
		int ocenaNajboljse = 0;
		List<Poteza> poteze = igra.poteze();
		for (Poteza p : poteze) {
			Igra kopijaIgre = new Igra(igra);
			kopijaIgre.odigraj(p);
			int ocenaP = minimax(k+1, kopijaIgre).vrednost;
			if (najboljsePoteze.isEmpty() 
					|| (naPotezi == jaz && ocenaP > ocenaNajboljse)
					|| (naPotezi != jaz && ocenaP < ocenaNajboljse)
					) 
			{
				najboljsePoteze.clear();
				najboljsePoteze.add(p);
				ocenaNajboljse = ocenaP;
			}
			else if (ocenaP == ocenaNajboljse) {
				najboljsePoteze.add(p);
			}
		}
		assert(! najboljsePoteze.isEmpty());
		Random r = new Random();
		Poteza najboljsa = najboljsePoteze.get(r.nextInt(najboljsePoteze.size()));
		return new OcenjenaPoteza(najboljsa, ocenaNajboljse);	
	}
}
