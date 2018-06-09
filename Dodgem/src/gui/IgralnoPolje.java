package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JPanel;

import logika.Igralec;
import logika.Polje;
import logika.Smer;

@SuppressWarnings("serial")
public class IgralnoPolje extends JPanel implements MouseListener, MouseMotionListener {
	private GlavnoOkno master;

	/**
	 * Relativna sirina crte.
	 */
	private final static double LINE_WIDTH = 0.05;

	/**
	 * Relativni prostor okoli avtomobilckov
	 */
	private final static double PADDING = 0.1;

	private Polygon pobarvaj;

	public IgralnoPolje(GlavnoOkno master) {
		super();
		setBackground(Color.white);
		this.master = master;
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.pobarvaj = null;
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(400, 400);
	}

	private double squareWidth() {
		return Math.min(getWidth(), getHeight()) / master.igra.N;
	}

	//manjsi trikotniki VERTICAL
	ArrayList<Polygon> poligonckiVerticalN = new ArrayList<Polygon>();
	ArrayList<Polygon> poligonckiVerticalL = new ArrayList<Polygon>();
	ArrayList<Polygon> poligonckiVerticalD = new ArrayList<Polygon>();

	//nevidni trikotniki, za premikanje VERTICAL
	ArrayList<Polygon> poligonckiVerticalNAPREJ = new ArrayList<Polygon>();
	ArrayList<Polygon> poligonckiVerticalLEVO = new ArrayList<Polygon>();
	ArrayList<Polygon> poligonckiVerticalDESNO = new ArrayList<Polygon>();

	//manjsi trikotniki HORIZONTAL
	ArrayList<Polygon> poligonckiHorizontalN = new ArrayList<Polygon>();
	ArrayList<Polygon> poligonckiHorizontalL = new ArrayList<Polygon>();
	ArrayList<Polygon> poligonckiHorizontalD = new ArrayList<Polygon>();

	//nevidni trikotniki, za premikanje HORIZONTAL
	ArrayList<Polygon> poligonckiHorizontalNAPREJ = new ArrayList<Polygon>();
	ArrayList<Polygon> poligonckiHorizontalLEVO = new ArrayList<Polygon>();
	ArrayList<Polygon> poligonckiHorizontalDESNO = new ArrayList<Polygon>();


	private void paintVERTICAL(Graphics2D g2, int i, int j) {
		double w = squareWidth();
		double r = w * (1.0 - LINE_WIDTH - 2.0 * PADDING); // sirina VERTICAL
		double x = w * (i + 0.5 * LINE_WIDTH + PADDING);
		double y = w * (j + 0.5 * LINE_WIDTH + PADDING);
		g2.setColor(Color.orange);
		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
		int xpoints[] = {(int) (x), (int) (x+r), (int) (x+r*0.5)};
		int ypoints[] = {(int) (y+r*0.9), (int) (y+r*0.9), (int) (y)};
		int npoints = 3;
		g2.fillPolygon(xpoints, ypoints, npoints);

		// mali trikotniki
		g2.setColor(Color.white);
		int xpointsN[] = {(int) (x+r*0.35), (int) (x+r-r*0.35), (int) (x+r*0.5)};
		int ypointsN[] = {(int) (y+r*0.4), (int) (y+r*0.4), (int) (y+r*0.1)};
		g2.fillPolygon(xpointsN, ypointsN, npoints);

		Polygon poligoncekN = new Polygon(xpointsN, ypointsN, npoints);
		poligonckiVerticalN .add(poligoncekN);

		g2.setColor(Color.white);
		int xpointsL[] = {(int) (x+r*0.1), (int) (x+r-r*0.58), (int) (x+r*0.25)};
		int ypointsL[] = {(int) (y+r*0.9-r*0.05), (int) (y+r*0.9-r*0.05), (int) (y+r*0.6)};
		g2.fillPolygon(xpointsL, ypointsL, npoints);

		Polygon poligoncekL = new Polygon(xpointsL, ypointsL, npoints);
		poligonckiVerticalL.add(poligoncekL);

		g2.setColor(Color.white);
		int xpointsD[] = {(int) (x+r*0.58), (int) (x+r-r*0.1), (int) (x+r*0.75)};
		int ypointsD[] = {(int) (y+r*0.9-r*0.05), (int) (y+r*0.9-r*0.05), (int) (y+r*0.6)};
		g2.fillPolygon(xpointsD, ypointsD, npoints);

		Polygon poligoncekD = new Polygon(xpointsD, ypointsD, npoints);
		poligonckiVerticalD.add(poligoncekD);

		// Nekoliko vecji majhni nevidni trikotniki, namenjeni temu, da lahko uporabik klikne tudi malo izven majhnega trikotnika.
		int xpointsNAPREJ[] = {(int) (x+r*0.2), (int) (x+r-r*0.2), (int) (x+r*0.5)};
		int ypointsNAPREJ[] = {(int) (y+r*0.5), (int) (y+r*0.5), (int) (y-r*0.1)};

		Polygon poligoncekNAPREJ = new Polygon(xpointsNAPREJ, ypointsNAPREJ, npoints);
		poligonckiVerticalNAPREJ .add(poligoncekNAPREJ);


		int xpointsLEVO[] = {(int) (x-r*0.05), (int) (x+r-r*0.55), (int) (x+r*0.2)};
		int ypointsLEVO[] = {(int) (y+r*0.95), (int) (y+r*0.95), (int) (y+r*0.5)};

		Polygon poligoncekLEVO = new Polygon(xpointsLEVO, ypointsLEVO, npoints);
		poligonckiVerticalLEVO.add(poligoncekLEVO);

		int xpointsDESNO[] = {(int) (x+r*0.55), (int) (x+r+r*0.05), (int) (x+r-r*0.2)};
		int ypointsDESNO[] = {(int) (y+r*0.95), (int) (y+r*0.95), (int) (y+r*0.5)};

		Polygon poligoncekDESNO = new Polygon(xpointsDESNO, ypointsDESNO, npoints);
		poligonckiVerticalDESNO.add(poligoncekDESNO);
	}

	private void paintHORIZONTAL(Graphics2D g2, int i, int j) {
		double w = squareWidth();
		double r = w * (1.0 - LINE_WIDTH - 2.0 * PADDING); // premer HORIZONTAL
		double x = w * (i + 0.5 * LINE_WIDTH + PADDING);
		double y = w * (j + 0.5 * LINE_WIDTH + PADDING); 
		g2.setColor(Color.red);
		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
		int xpoints[] = {(int) (x), (int) (x), (int) (x+r*0.9)};
		int ypoints[] = {(int) (y), (int) (y+r), (int) (y+r*0.5)};
		int npoints = 3;
		g2.fillPolygon(xpoints, ypoints, npoints);

		// Mali trikotniki.
		g2.setColor(Color.white);
		int xpointsN[] = {(int) (x+r*0.5), (int) (x+r*0.5), (int) (x+r-r*0.2)};
		int ypointsN[] = {(int) (y+r*0.35), (int) (y+r-r*0.35), (int) (y+r*0.5)};
		g2.fillPolygon(xpointsN, ypointsN, npoints);

		Polygon poligoncekN = new Polygon(xpointsN, ypointsN, npoints);
		poligonckiHorizontalN.add(poligoncekN);

		g2.setColor(Color.white);
		int xpointsL[] = {(int) (x+r*0.05), (int) (x+r*0.05), (int) (x+r*0.3)};
		int ypointsL[] = {(int) (y+r*0.1), (int) (y+r-r*0.58), (int) (y+r*0.25)};
		g2.fillPolygon(xpointsL, ypointsL, npoints);

		Polygon poligoncekL = new Polygon(xpointsL, ypointsL, npoints);
		poligonckiHorizontalL.add(poligoncekL);

		g2.setColor(Color.white);
		int xpointsD[] = {(int) (x+r*0.05), (int) (x+r*0.05), (int) (x+r*0.3)};
		int ypointsD[] = {(int) (y+r-r*0.1), (int) (y+r-r*0.42), (int) (y+r-r*0.25)};
		g2.fillPolygon(xpointsD, ypointsD, npoints);

		Polygon poligoncekD = new Polygon(xpointsD, ypointsD, npoints);
		poligonckiHorizontalD.add(poligoncekD);

		// Nekoliko vecji majhni nevidni trikotniki, namenjeni temu, da lahko uporabik klikne tudi malo izven majhnega trikotnika.
		int xpointsNAPREJ[] = {(int) (x+r*0.5), (int) (x+r*0.5), (int) (x+r+r*0.1)};
		int ypointsNAPREJ[] = {(int) (y+r*0.2), (int) (y+r-r*0.2), (int) (y+r*0.5)};

		Polygon poligoncekNAPREJ = new Polygon(xpointsNAPREJ, ypointsNAPREJ, npoints);
		poligonckiHorizontalNAPREJ.add(poligoncekNAPREJ);

		int xpointsLEVO[] = {(int) (x-r*0.05), (int) (x-r*0.05), (int) (x+r*0.5)};
		int ypointsLEVO[] = {(int) (y-r*0.05), (int) (y+r-r*0.55), (int) (y+r*0.2)};

		Polygon poligoncekLEVO = new Polygon(xpointsLEVO, ypointsLEVO, npoints);
		poligonckiHorizontalLEVO.add(poligoncekLEVO);

		int xpointsDESNO[] = {(int) (x-r*0.05), (int) (x-r*0.05), (int) (x+r*0.5)};
		int ypointsDESNO[] = {(int) (y+r+r*0.05), (int) (y+r-r*0.45), (int) (y+r-r*0.2)};

		Polygon poligoncekDESNO = new Polygon(xpointsDESNO, ypointsDESNO, npoints);
		poligonckiHorizontalDESNO.add(poligoncekDESNO);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		// sirina kvadratka
		double w = squareWidth();
		// crte
		g2.setColor(Color.gray);
		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
		for (int i = 1; i < master.igra.N; i++) {
			g2.drawLine((int)(i * w),
					(int)(LINE_WIDTH * w),
					(int)(i * w),
					(int)((master.igra.N) * w));
			g2.drawLine((int)(LINE_WIDTH * w),
					(int)(i * w),
					(int)((master.igra.N) * w),
					(int)(i * w));
		}
		// ciljna crta za horizontal
		g2.setColor(Color.red);
		g2.drawLine((int)(master.igra.N * w),
				(int)(0),
				(int)(master.igra.N * w),
				(int)(master.igra.N * w));
		// ciljna crta za vertical
		g2.setColor(Color.orange);
		g2.drawLine((int)(0),
				(int)(0),
				(int)(master.igra.N * w),
				(int)(0));
		// skrajno leva crta
		g2.setColor(Color.gray);
		g2.drawLine((int)(0),
				(int)(0),
				(int)(0),
				(int)(master.igra.N * w));
		// skrajno spodnja crta
		g2.setColor(Color.gray);
		g2.drawLine((int)(0),
				(int)(master.igra.N * w),
				(int)(master.igra.N * w),
				(int)(master.igra.N * w));

		// avtomobilcki (za igralca VERTICAL in HORIZONTAL)
		Polje[][] plosca = master.getPlosca();
		if (plosca != null) {
			for (int i = 0; i < master.igra.N; i++) {
				for (int j = 0; j < master.igra.N; j++) {
					switch(plosca[i][j]) {
					case VERTICAL: paintVERTICAL(g2, i, j); break;
					case HORIZONTAL: paintHORIZONTAL(g2, i, j); break;
					default: break;
					}
				}
			}
		}	
		if (pobarvaj != null){
			g2.setColor(Color.green);
			g2.fillPolygon(pobarvaj);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		int w = (int)(squareWidth());
		int i = x / w ;
		double di = (x % w) / squareWidth() ;
		int j = y / w ;
		double dj = (y % w) / squareWidth() ;
		if (0 <= i && i < master.igra.N &&
				0.5 * LINE_WIDTH < di && di < 1.0 - 0.5 * LINE_WIDTH &&
				0 <= j && j < master.igra.N && 
				0.5 * LINE_WIDTH < dj && dj < 1.0 - 0.5 * LINE_WIDTH) {

			gumbekPremakni(poligonckiVerticalNAPREJ, Smer.NAPREJ, e);
			gumbekPremakni(poligonckiVerticalLEVO, Smer.LEVO, e);
			gumbekPremakni(poligonckiVerticalDESNO, Smer.DESNO, e);
			gumbekPremakni(poligonckiHorizontalNAPREJ, Smer.NAPREJ, e);
			gumbekPremakni(poligonckiHorizontalLEVO, Smer.LEVO, e);
			gumbekPremakni(poligonckiHorizontalDESNO, Smer.DESNO, e);
			pobarvaj = null;
			repaint();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {		
	}

	@Override
	public void mouseReleased(MouseEvent e) {		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {

		boolean nasel = false;

		int x = e.getX();
		int y = e.getY();
		int w = (int)(squareWidth());
		int i = x / w ;
		int j = y / w ;
		if (i >= master.igra.N || j >= master.igra.N || i < 0 || j < 0) return;

		if (master.igra.plosca[i][j] == Polje.PRAZNO){
			return;
		}

		if(master.strategVERTICAL.semClovek() && master.igra.naPotezi == Igralec.VERTICAL && master.igra.plosca[i][j] == Polje.VERTICAL){
			gumbekZelen(poligonckiVerticalNAPREJ, poligonckiVerticalN, poligonckiVerticalLEVO, poligonckiVerticalL, poligonckiVerticalDESNO, poligonckiVerticalD, e, nasel);
			repaint();
		}
		else if(master.strategHORIZONTAL.semClovek() && master.igra.naPotezi == Igralec.HORIZONTAL && master.igra.plosca[i][j] == Polje.HORIZONTAL){
			gumbekZelen(poligonckiHorizontalNAPREJ, poligonckiHorizontalN, poligonckiHorizontalLEVO, poligonckiHorizontalL, poligonckiHorizontalDESNO, poligonckiHorizontalD, e, nasel);
			repaint();
		}

		//manjsi trikotniki VERTICAL
		poligonckiVerticalN = new ArrayList<Polygon>();
		poligonckiVerticalL = new ArrayList<Polygon>();
		poligonckiVerticalD = new ArrayList<Polygon>();

		//nevidni trikotniki, za premikanje VERTICAL
		poligonckiVerticalNAPREJ = new ArrayList<Polygon>();
		poligonckiVerticalLEVO = new ArrayList<Polygon>();
		poligonckiVerticalDESNO = new ArrayList<Polygon>();

		//manjsi trikotniki HORIZONTAL
		poligonckiHorizontalN = new ArrayList<Polygon>();
		poligonckiHorizontalL = new ArrayList<Polygon>();
		poligonckiHorizontalD = new ArrayList<Polygon>();

		//nevidni trikotniki, za premikanje HORIZONTAL
		poligonckiHorizontalNAPREJ = new ArrayList<Polygon>();
		poligonckiHorizontalLEVO = new ArrayList<Polygon>();
		poligonckiHorizontalDESNO = new ArrayList<Polygon>();
		repaint();

		//manjsi trikotniki VERTICAL
		poligonckiVerticalN = new ArrayList<Polygon>();
		poligonckiVerticalL = new ArrayList<Polygon>();
		poligonckiVerticalD = new ArrayList<Polygon>();

		//nevidni trikotniki, za premikanje VERTICAL
		poligonckiVerticalNAPREJ = new ArrayList<Polygon>();
		poligonckiVerticalLEVO = new ArrayList<Polygon>();
		poligonckiVerticalDESNO = new ArrayList<Polygon>();

		//manjsi trikotniki HORIZONTAL
		poligonckiHorizontalN = new ArrayList<Polygon>();
		poligonckiHorizontalL = new ArrayList<Polygon>();
		poligonckiHorizontalD = new ArrayList<Polygon>();

		//nevidni trikotniki, za premikanje HORIZONTAL
		poligonckiHorizontalNAPREJ = new ArrayList<Polygon>();
		poligonckiHorizontalLEVO = new ArrayList<Polygon>();
		poligonckiHorizontalDESNO = new ArrayList<Polygon>();	
		repaint();
	}

	// s klikom na nevidni trikotnik dolocimo smer avtomobilcku
	private void gumbekPremakni(ArrayList<Polygon> trikotniki, Smer smer, MouseEvent e){
		int x = e.getX();
		int y = e.getY();
		int w = (int)(squareWidth());
		int i = x / w ;
		int j = y / w ;

		for(Polygon poligoncek : trikotniki) {
			if (poligoncek.contains(e.getPoint())) {
				master.klikniPolje(i, j, smer);
				break;
			}
		}
	}

	// s prehodom miske cez nevidni trikotnik, se beli trikotnik pobarva v zeleno
	private void gumbekZelen(ArrayList<Polygon> naprej, ArrayList<Polygon> n,
			ArrayList<Polygon> levo, ArrayList<Polygon> l,
			ArrayList<Polygon> desno, ArrayList<Polygon> d,
			MouseEvent e, boolean nasel){
		for(Polygon poligoncek : naprej) {
			if (poligoncek.contains(e.getPoint())) {
				for(Polygon poligoncek1 : n) {
					if(poligoncek.getBounds2D().contains(poligoncek1.getBounds2D())){
						pobarvaj = poligoncek1;
						repaint();
						nasel = true;
						break;
					}
				}
				break;
			}
		}
		for(Polygon poligoncek : levo) {
			if (poligoncek.contains(e.getPoint())) {
				for(Polygon poligoncek1 : l) {
					if(poligoncek.getBounds2D().contains(poligoncek1.getBounds2D())){
						pobarvaj = poligoncek1;
						repaint();
						nasel = true;
						break;
					}
				}
				break;
			}
		}
		for(Polygon poligoncek : desno) {
			if (poligoncek.contains(e.getPoint())) {
				for(Polygon poligoncek1 : d) {
					if(poligoncek.getBounds2D().contains(poligoncek1.getBounds2D())){
						pobarvaj = poligoncek1;
						repaint();
						nasel = true;
						break;
					}
				}
				break;
			}
		}
		if(!nasel && pobarvaj != null){
			pobarvaj = null;
			repaint();
		}
	}
}