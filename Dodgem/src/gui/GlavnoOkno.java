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
import logika.Igralec;
import logika.Polje;
import logika.Poteza;
import logika.Smer;
import vmesnik.Clovek;
import vmesnik.Racunalnik;
import vmesnik.Strateg;

@SuppressWarnings("serial")
public class GlavnoOkno extends JFrame implements ActionListener {
	/**
	 * JPanel, v katerega rišemo X in Y
	 */
	private IgralnoPolje polje;

	/**
	 * Statusna vrstica v spodnjem delu okna
	 */
	private JLabel status;


	/**
	 * Logika igre, null èe se igra trenutno ne igra
	 */
	private Igra igra;

	/**
	 * Strateg, ki vleèe poteze X.
	 */
	private Strateg strategX;

	/**
	 * Strateg, ki vleèe poteze Y
	 */
	private Strateg strategY;

	// Izbire v menujih
	private JMenuItem nova_igra;

	// Izbire v menujih
	private JMenuItem igraClovekRacunalnik;
	private JMenuItem igraRacunalnikClovek;
	private JMenuItem igraClovekClovek;
	private JMenuItem igraRacunalnikRacunalnik;
	
	
	
	public GlavnoOkno() {
		this.setTitle("Dodgem");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridBagLayout());

		// menu
		JMenuBar menu_bar = new JMenuBar();
		this.setJMenuBar(menu_bar);
		JMenu igra_menu = new JMenu("Igra");
		menu_bar.add(igra_menu);

		
		//izbire v igra: 
		igraClovekClovek = new JMenuItem("Èlovek Rumeni  ><  Èlovek Rdeèi");
		igra_menu .add(igraClovekClovek);
		igraClovekClovek.addActionListener(this);
		
		igraClovekRacunalnik = new JMenuItem("Raèunalnik Rumeni  ><  Èlovek Rdeèi");
		igra_menu .add(igraClovekRacunalnik);
		igraClovekRacunalnik.addActionListener(this);
		
		igraRacunalnikClovek = new JMenuItem("Èlovek Rumeni  ><  Raèunalnik Rdeèi");
		igra_menu .add(igraRacunalnikClovek);
		igraRacunalnikClovek.addActionListener(this);
		
		igraRacunalnikRacunalnik = new JMenuItem("Raèunalnik Rumeni  ><  Raèunalnik Rdeèi");
		igra_menu .add(igraRacunalnikRacunalnik);
		igraRacunalnikRacunalnik.addActionListener(this);
		
		
  
		// igralno polje
		polje = new IgralnoPolje(this);
		GridBagConstraints polje_layout = new GridBagConstraints();
		polje_layout.gridx = 0;
		polje_layout.gridy = 0;
		polje_layout.fill = GridBagConstraints.BOTH;
		polje_layout.weightx = 1.0;
		polje_layout.weighty = 1.0;
		getContentPane().add(polje, polje_layout);

		// statusna vrstica za sporoèila
		status = new JLabel();
		status.setFont(new Font(status.getFont().getName(),
				status.getFont().getStyle(),
				20));
		GridBagConstraints status_layout = new GridBagConstraints();
		status_layout.gridx = 0;
		status_layout.gridy = 1;
		status_layout.anchor = GridBagConstraints.CENTER;
		getContentPane().add(status, status_layout);

		// za zacetek clovek proti cloveku
		nova_igra(new Clovek(this, Igralec.HORIZONTAL), new Clovek(this, Igralec.VERTICAL));
		}


	/**
	 * @return trenutna igralna plosèa, ali null, èe igra ni aktivna
	 */
	public Polje[][] getPlosca() {
		return (igra == null ? null : igra.getPlosca());
	}

	public void nova_igra(Strateg horizontal, Strateg vertical) {
		if (strategY != null) { strategY.prekini(); }
		if (strategX != null) { strategX.prekini(); }
		this.igra = new Igra();
		strategY = horizontal;
		strategX = vertical;
		switch (igra.stanje()) {
		case NA_POTEZI_HORIZONTAL: strategY.na_potezi(); break;
		case NA_POTEZI_VERTICAL: strategX.na_potezi(); break;
		default: break;
		}
		osveziGUI();
		repaint();
	}
 
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == igraClovekRacunalnik) {
			nova_igra(new Clovek(this, Igralec.HORIZONTAL),
					  new Racunalnik(this, Igralec.VERTICAL));
		}
		else if (e.getSource() == igraRacunalnikClovek) {
			nova_igra(new Racunalnik(this, Igralec.HORIZONTAL),
					  new Clovek(this, Igralec.VERTICAL));
		}
		else if (e.getSource() == igraRacunalnikRacunalnik) {
			nova_igra(new Racunalnik(this, Igralec.HORIZONTAL),
					  new Racunalnik(this, Igralec.VERTICAL));
		}
		else if (e.getSource() == igraClovekClovek) {
			nova_igra(new Clovek(this, Igralec.HORIZONTAL),
			          new Clovek(this, Igralec.VERTICAL));
		}
		
	}

	public void odigraj(Poteza p) {
		if(igra.odigraj(p)) {
			osveziGUI();
			switch (igra.stanje()) {
			case NA_POTEZI_HORIZONTAL: strategY.na_potezi(); break;
			case NA_POTEZI_VERTICAL: strategX.na_potezi(); break;
			case ZMAGA_HORIZONTAL: break;
			case ZMAGA_VERTICAL: break;
			}
		}
	}

	public void osveziGUI() {
		if (igra == null) {
			status.setText("Igra ni v teku.");
		}
		else {
			switch(igra.stanje()) {
			case NA_POTEZI_HORIZONTAL: status.setText("Na potezi je Y."); break;
			case NA_POTEZI_VERTICAL: status.setText("Na potezi je X."); break;
			case ZMAGA_HORIZONTAL: status.setText("Zmagal je Y."); break;
			case ZMAGA_VERTICAL: status.setText("Zmagal je X."); break;
			}
		}
		polje.repaint();
	}

	public void klikniPolje(int i, int j) {
		if (igra != null) {
			switch (igra.stanje()) {
			case NA_POTEZI_VERTICAL:
				strategX.klik(i, j);
				break;
			case NA_POTEZI_HORIZONTAL:
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