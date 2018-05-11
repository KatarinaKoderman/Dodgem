package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import logika.Igra;
import logika.Polje;
import logika.Poteza;
import logika.Smer;

@SuppressWarnings("serial")
public class GlavnoOkno extends JFrame implements ActionListener {
	/**
	 * JPanel, v katerega ri�emo X in Y
	 */
	private IgralnoPolje polje;

	/**
	 * Statusna vrstica v spodnjem delu okna
	 */
	private JLabel status;


	/**
	 * Logika igre, null �e se igra trenutno ne igra
	 */
	private Igra igra;

	/**
	 * Strateg, ki vle�e poteze X.
	 */
	private Strateg strategX;

	/**
	 * Strateg, ki vle�e poteze Y
	 */
	private Strateg strategY;

	// Izbire v menujih
	private JMenuItem nova_igra;

	public GlavnoOkno() {
		this.setTitle("Dodgem");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridBagLayout());

		// menu
		JMenuBar menu_bar = new JMenuBar();
		this.setJMenuBar(menu_bar);
		JMenu igra_menu = new JMenu("Igra");
		menu_bar.add(igra_menu);
		nova_igra = new JMenuItem("Nova igra");
		igra_menu.add(nova_igra);
		nova_igra.addActionListener(this);

		// igralno polje
		polje = new IgralnoPolje(this);
		GridBagConstraints polje_layout = new GridBagConstraints();
		polje_layout.gridx = 0;
		polje_layout.gridy = 0;
		polje_layout.fill = GridBagConstraints.BOTH;
		polje_layout.weightx = 1.0;
		polje_layout.weighty = 1.0;
		getContentPane().add(polje, polje_layout);

		// statusna vrstica za sporo�ila
		status = new JLabel();
		status.setFont(new Font(status.getFont().getName(),
				status.getFont().getStyle(),
				20));
		GridBagConstraints status_layout = new GridBagConstraints();
		status_layout.gridx = 0;
		status_layout.gridy = 1;
		status_layout.anchor = GridBagConstraints.CENTER;
		getContentPane().add(status, status_layout);

		// za�nemo novo igro
		nova_igra();
	}

	/**
	 * @return trenutna igralna plos�a, ali null, �e igra ni aktivna
	 */
	public Polje[][] getPlosca() {
		return (igra == null ? null : igra.getPlosca());
	}

	public void nova_igra() {
		if (strategY != null) { strategY.prekini(); }
		if (strategX != null) { strategX.prekini(); }
		this.igra = new Igra();
		strategY = new Clovek(this);
		strategX = new Racunalnik(this);
		// Tistemu, ki je na potezi, to povemo
		switch (igra.stanje()) {
		case NA_POTEZI_Y: strategY.na_potezi(); break;
		case NA_POTEZI_X: strategX.na_potezi(); break;
		default: break;
		}
		osveziGUI();
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == nova_igra) {
			nova_igra();
		}
	}

	public void odigraj(Poteza p) {
		igra.odigraj(p);
		osveziGUI();
		switch (igra.stanje()) {
		case NA_POTEZI_Y: strategY.na_potezi(); break;
		case NA_POTEZI_X: strategX.na_potezi(); break;
		case ZMAGA_Y: break;
		case ZMAGA_X: break;
		}
	}

	public void osveziGUI() {
		if (igra == null) {
			status.setText("Igra ni v teku.");
		}
		else {
			switch(igra.stanje()) {
			case NA_POTEZI_Y: status.setText("Na potezi je Y"); break;
			case NA_POTEZI_X: status.setText("Na potezi je X"); break;
			case ZMAGA_Y: status.setText("Zmagal je Y"); break;
			case ZMAGA_X: status.setText("Zmagal je X"); break;
			}
		}
		polje.repaint();
	}

	public void klikniPolje(int i, int j) {
		if (igra != null) {
			switch (igra.stanje()) {
			case NA_POTEZI_X:
				strategX.klik(i, j);
				break;
			case NA_POTEZI_Y:
				strategY.klik(i, j);
				break;
			default:
				break;
			}
		}		
	} 

	/**
	 * @return kopija trenutne igre
	 */
	public Igra copyIgra() {
		return new Igra(igra);
	}
}