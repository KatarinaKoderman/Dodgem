package gui;

import javax.swing.SwingWorker;

import logika.Igralec;
import inteligenca.Minimax;
import inteligenca.NakljucnaInteligenca;
import logika.Igra;
import logika.Poteza;
import logika.Smer;

public class Racunalnik extends Strateg {
	private GlavnoOkno master;
	private Igralec id;
	private SwingWorker<Poteza,Object> mislec;
	private boolean prekini;
	private int globina = 1;

	public Racunalnik(GlavnoOkno master, Igralec igralec) {
		this.master = master;
		id = igralec;
	}
	
	@Override
	public void na_potezi() {
		// Zaènemo razmišljati
		mislec = new Minimax(master, globina, id);
		mislec.execute();
	}

	@Override
	public void prekini() {
		if (mislec != null) {
			mislec.cancel(true);
		}
	}

	@Override
	public void klik(int i, int j, Smer s) {
	}

} 