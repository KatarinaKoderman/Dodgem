package inteligenca;

import java.util.Collections;
import java.util.List;

import javax.swing.SwingWorker;
import gui.GlavnoOkno;
import logika.Igra;
import logika.Igralec;
import logika.Poteza;

public class AlphaBeta extends SwingWorker<Poteza, Object>  {

	private GlavnoOkno master;
	private int globina;
	private Igralec jaz;

	public AlphaBeta(GlavnoOkno master, int globina, Igralec jaz) {
		this.master = master;
		this.globina = globina;
		this.jaz = jaz;
	}

	@Override
	protected Poteza doInBackground() throws Exception {
		Igra igra = master.copyIgra();
		OcenjenaPoteza p = alphaBeta(igra, globina, -100000000, 100000000); // največ je 100 milijonov
		assert (p.poteza != null);
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
		} 
		catch (Exception e) {
		}
	}

	private OcenjenaPoteza alphaBeta(Igra igra, int globina, int alpha, int beta) {
		Igralec naPotezi = null;
		switch (igra.stanje()) {
		case NA_POTEZI_HORIZONTAL:
			naPotezi = Igralec.HORIZONTAL; 
			break;
		case NA_POTEZI_VERTICAL:
			naPotezi = Igralec.VERTICAL; 
			break;
		case ZMAGA_HORIZONTAL:
			return new OcenjenaPoteza(null, (jaz == Igralec.HORIZONTAL ? Ocena.ZMAGA + globina : Ocena.ZGUBA - globina));
		case ZMAGA_VERTICAL:
			return new OcenjenaPoteza(null, (jaz == Igralec.VERTICAL ? Ocena.ZMAGA + globina : Ocena.ZGUBA - globina));
		default:
			break;
		}

		if (globina == 0) {
			return new OcenjenaPoteza(null, Ocena.oceniPozicijo(jaz, igra));
		}

		Poteza najboljsaPoteza = null;
		List<Poteza> moznePoteze = igra.poteze();
		Collections.shuffle(moznePoteze);

		if (naPotezi == jaz) { // maximiziramo
			int najboljsaOcena = -1000000000;

			for (Poteza poteza : moznePoteze) {
				Igra kopijaIgre = new Igra(igra);
				kopijaIgre.odigraj(poteza);
				int ocenaPoteze = alphaBeta(kopijaIgre, globina - 1, alpha, beta).vrednost;
				if (najboljsaOcena < ocenaPoteze) {
					najboljsaOcena = ocenaPoteze;
					najboljsaPoteza = poteza;
				}
				alpha = Math.max(alpha, najboljsaOcena);
				if (beta <= alpha) {
					break;
				}
			}
			return new OcenjenaPoteza(najboljsaPoteza, najboljsaOcena);
		} else { // minimiziramo
			int najboljsaOcena = 100000000;
			for (Poteza poteza : moznePoteze) {
				Igra kopijaIgre = new Igra(igra);
				kopijaIgre.odigraj(poteza);

				int ocenaPoteze = alphaBeta(kopijaIgre, globina - 1, alpha, beta).vrednost;

				if (najboljsaOcena > ocenaPoteze) {
					najboljsaOcena = ocenaPoteze;
					najboljsaPoteza = poteza;
				}
				beta = Math.min(beta, najboljsaOcena);
				if (beta <= alpha) {
					break;
				}
			}
			return new OcenjenaPoteza(najboljsaPoteza, najboljsaOcena);
		}
	}
}