package gui;

import javax.swing.SwingWorker;

import logika.Igralec;
import inteligenca.Minimax;
import logika.Poteza;
import logika.Smer;

public class Racunalnik extends Strateg {
	private GlavnoOkno master;
	private Igralec id;
	private SwingWorker<Poteza,Object> mislec;
	private int globina = 6;

	public Racunalnik(GlavnoOkno master, Igralec igralec) {
		this.master = master;
		id = igralec;
	}
	
	@Override
	public void na_potezi() {
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

	@Override
	public boolean semClovek() {
		return false;
	}

} 