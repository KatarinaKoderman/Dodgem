package test;


import java.util.List;

import junit.framework.TestCase;
import logika.Igra;
import logika.Poteza;
import logika.Stanje;

public class TestLogikaIgre extends TestCase {
	public void testIgra() {
		Igra igra = new Igra();
		// Na za�etku je na potezi X.
		assertEquals(Stanje.NA_POTEZI_X, igra.stanje());
		
		// Na za�etku je za prvega igralca na voljo N potez.
		assertEquals(Igra.N, igra.poteze().size());
		
		// Naredimo eno potezo. Preverimo, �e je bila poteza odigrana.
		List<Poteza> p = igra.poteze();
		assertEquals(true, igra.odigraj(p.get(0)));
		
		// Po prvi odigrani potezi je na vrsti Y.
		// assertEquals(Stanje.NA_POTEZI_Y, igra.stanje());
		
	}
}
