package gui;

import logika.Igralec;
import logika.Poteza;
import logika.Smer;

public class Clovek extends Strateg {
	private GlavnoOkno master;
	private Igralec ego;
	
	public Clovek(GlavnoOkno master, Igralec ego) {
		this.master = master;
		this.ego = ego;
	}
	
	@Override
	public void na_potezi() {
	}
	
	@Override
	public void prekini() {
	}
	
	@Override
	public void klik(int i, int j, Smer s) {
		// smer dobimo z ukazom s tipkovnice. TODO
		master.odigraj(new Poteza(i, j, s));
	}

}
