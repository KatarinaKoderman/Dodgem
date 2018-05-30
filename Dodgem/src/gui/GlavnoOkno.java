package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
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

import java.io.*;
import javax.sound.sampled.*;


@SuppressWarnings("serial")
public class GlavnoOkno extends JFrame implements ActionListener {
	/**
	 * JPanel, v katerega rišemo VERTICAL in HORIZONTAL
	 */
	private IgralnoPolje polje;

	/**
	 * Statusna vrstica v spodnjem delu okna
	 */
	private JLabel status;


	/**
	 * Logika igre, null èe se igra trenutno ne igra
	 */
	protected Igra igra;

	/**
	 * Strateg, ki vleèe poteze VERTICAL.
	 */
	protected Strateg strategVERTICAL;

	/**
	 * Strateg, ki vleèe poteze HORIZONTAL
	 */
	protected Strateg strategHORIZONTAL;

	//ustvarimo clip
	 
	static File soundFile = new File("resources\\Insert-Coins-Jake-Wright.wav");
	static Clip clip;
	static {
		try {
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
			clip = AudioSystem.getClip();
			clip.open(audioIn);
		} catch (UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	// Izbire v menujih
	private JMenuItem igraClovekRacunalnik;
	private JMenuItem igraRacunalnikClovek;
	private JMenuItem igraClovekClovek;
	private JMenuItem igraRacunalnikRacunalnik;

	private JMenuItem mala;
	private JMenuItem srednja;
	private JMenuItem velika;

	public GlavnoOkno() {
		this.setTitle("Dodgem");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridBagLayout());

		// menu
		JMenuBar menu_bar = new JMenuBar();
		this.setJMenuBar(menu_bar);
		JMenu igra_menu = new JMenu("Igra");
		menu_bar.add(igra_menu);
		JMenu velikostPlosca_menu = new JMenu("Velikost plošèe");
		menu_bar.add(velikostPlosca_menu);



		//izbire v igra: 
		igraClovekClovek = new JMenuItem("Èlovek Rumeni  -  Èlovek Rdeèi");
		igra_menu .add(igraClovekClovek);
		igraClovekClovek.addActionListener(this);

		igraClovekRacunalnik = new JMenuItem("Raèunalnik Rumeni  -  Èlovek Rdeèi");
		igra_menu .add(igraClovekRacunalnik);
		igraClovekRacunalnik.addActionListener(this);

		igraRacunalnikClovek = new JMenuItem("Èlovek Rumeni  -  Raèunalnik Rdeèi");
		igra_menu .add(igraRacunalnikClovek);
		igraRacunalnikClovek.addActionListener(this);

		igraRacunalnikRacunalnik = new JMenuItem("Raèunalnik Rumeni  -  Raèunalnik Rdeèi");
		igra_menu .add(igraRacunalnikRacunalnik);
		igraRacunalnikRacunalnik.addActionListener(this);
		
		//izbire v velikostPlosce: 
		mala = new JMenuItem("Mala");
		velikostPlosca_menu .add(mala);
		mala.addActionListener(this);

		srednja = new JMenuItem("Srednja");
		velikostPlosca_menu .add(srednja);
		srednja.addActionListener(this);
		
		velika = new JMenuItem("Velika");
		velikostPlosca_menu .add(velika);
		velika.addActionListener(this);
		
		// gumb za glasbo
		JButton glasbaButton = new JButton("Glasba");
		GridBagConstraints glasbaButton_layout = new GridBagConstraints();
		glasbaButton_layout.gridx = 1;
		glasbaButton_layout.gridy = 1;
		glasbaButton_layout.anchor = GridBagConstraints.CENTER;
		getContentPane().add(glasbaButton, glasbaButton_layout);
		glasbaButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					music();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
		});
		

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
		if (strategHORIZONTAL != null) { strategHORIZONTAL.prekini(); }
		if (strategVERTICAL != null) { strategVERTICAL.prekini(); }
		this.igra = new Igra(5);
		strategHORIZONTAL = horizontal;
		strategVERTICAL = vertical;
		switch (igra.stanje()) {
		case NA_POTEZI_HORIZONTAL: strategHORIZONTAL.na_potezi(); break;
		case NA_POTEZI_VERTICAL: strategVERTICAL.na_potezi(); break;
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

		else if (e.getSource() == mala) {
			igra = new Igra(3);

//			nova_igra(strategHORIZONTAL, strategVERTICAL);
		}

		else if (e.getSource() == srednja) {
			igra = new Igra(5);


//			nova_igra(strategHORIZONTAL, strategVERTICAL);
		}

		else if (e.getSource() == velika) {
			igra = new Igra(10);


			
//			nova_igra(strategHORIZONTAL, strategVERTICAL);
		}
		osveziGUI();
		repaint();
	}

	//zaène s predvajanjem, ali ga ustavi.
	public static void music() throws Exception, IOException{
		if (clip.isRunning()) {
			clip.stop();
			System.out.println("stop music");
		} else {
			clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			System.out.println("play music");
		}
	}

	public void odigraj(Poteza p) {
		if(igra.odigraj(p)) {
			osveziGUI();
			switch (igra.stanje()) {
			case NA_POTEZI_HORIZONTAL: strategHORIZONTAL.na_potezi(); break;
			case NA_POTEZI_VERTICAL: strategVERTICAL.na_potezi(); break;
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
			case NA_POTEZI_HORIZONTAL: status.setText("Na potezi je rdeèi."); break;
			case NA_POTEZI_VERTICAL: status.setText("Na potezi je rumeni."); break;
			case ZMAGA_HORIZONTAL: status.setText("Zmagal je rdeèi."); break;
			case ZMAGA_VERTICAL: status.setText("Zmagal je rumeni."); break;
			}
		}
		polje.repaint();
	}

	public void klikniPolje(int i, int j, Smer s) {
		if (igra != null) {
			switch (igra.stanje()) {
			case NA_POTEZI_VERTICAL:
				strategVERTICAL.klik(i, j, s);
				break;
			case NA_POTEZI_HORIZONTAL:
				strategHORIZONTAL.klik(i, j, s);
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
	
	public int copySteviloPotez() {
		return igra.getSteviloOdigranihPotez();
	}
}