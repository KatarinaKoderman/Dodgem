package gui;

import javax.swing.SwingWorker;

import logika.Igralec;
import inteligenca.NakljucnaInteligenca;
import logika.Igra;
import logika.Poteza;

public class Racunalnik extends Strateg {
	private GlavnoOkno master;
	private Igralec id;
	private SwingWorker<Poteza,Object> mislec;
	private boolean prekini;

	public Racunalnik(GlavnoOkno master, Igralec igralec) {
		this.master = master;
		id = igralec;
	}
	
	@Override
	public void na_potezi() {
		// Zaènemo razmišljati
		mislec = new NakljucnaInteligenca(master);
		mislec.execute();
	}

	@Override
	public void prekini() {
		if (mislec != null) {
			mislec.cancel(true);
		}
	}

	@Override
	public void klik(int i, int j) {
	}

} 