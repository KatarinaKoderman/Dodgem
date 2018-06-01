package test;

import java.util.List;

import junit.framework.TestCase;
import logika.Igra;
import logika.Poteza;
import logika.Stanje;

public class TestLogikaIgre extends TestCase {
	public void testIgra() {
		Igra igra = new Igra(4);
		// Na zacetku je na potezi X.
		assertEquals(Stanje.NA_POTEZI_VERTICAL, igra.stanje());
		
		// Na zacetku je za prvega igralca na voljo N potez.
		assertEquals(igra.N, igra.poteze().size());
		
		// Naredimo eno potezo. Preverimo, ce je bila poteza odigrana.
		@SuppressWarnings("unused")
		List<Poteza> p = igra.poteze();
		assertEquals(true, igra.odigraj(igra.poteze().get(0)));
		
		// Po prvi odigrani potezi je na vrsti Y.
		assertEquals(Stanje.NA_POTEZI_HORIZONTAL, igra.stanje()); 
		// Po prvi odigrani potezi, je stevilo odigranih potez 1.
		assertEquals(igra.steviloOdigranihPotez, 1);
		
		// Naredimo drugo potezo. Preverimo, da je bila poteza odigrana in da je stevilo odigranih potez enako 2.
		assertEquals(true, igra.odigraj(igra.poteze().get(0)));
		assertEquals(Stanje.NA_POTEZI_VERTICAL, igra.stanje());
		assertEquals(igra.steviloOdigranihPotez, 2);
		
		// Naredimo se eno potezo.
		assertEquals(true, igra.odigraj(igra.poteze().get(0)));
		assertEquals(Stanje.NA_POTEZI_HORIZONTAL, igra.stanje());
		assertEquals(igra.steviloOdigranihPotez, 3);
	}
}
