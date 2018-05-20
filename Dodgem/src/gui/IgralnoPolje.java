package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


import javax.swing.JPanel;

import logika.Igra;
import logika.Polje;
import logika.Smer;

@SuppressWarnings("serial")
public class IgralnoPolje extends JPanel implements MouseListener {
	private GlavnoOkno master;

	/**
	 * Relativna širina èrte.
	 */
	private final static double LINE_WIDTH = 0.05;

	/**
	 * Relativni prostor okoli avtomobilckov
	 */
	private final static double PADDING = 0.1;

	public IgralnoPolje(GlavnoOkno master) {
		super();
		setBackground(Color.white);
		this.master = master;
		this.addMouseListener(this);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(400, 400);
	}

	private double squareWidth() {
		return Math.min(getWidth(), getHeight()) / Igra.N;
	}

	private void paintX(Graphics2D g2, int i, int j) {
		double w = squareWidth();
		double r = w * (1.0 - LINE_WIDTH - 2.0 * PADDING); // sirina X
		double x = w * (i + 0.5 * LINE_WIDTH + PADDING);
		double y = w * (j + 0.5 * LINE_WIDTH + PADDING);
		g2.setColor(Color.orange);
		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
	    int xpoints[] = {(int) (x), (int) (x+r), (int) (x+r*0.5)};
	    int ypoints[] = {(int) (y+r*0.9), (int) (y+r*0.9), (int) (y)};
	    int npoints = 3;
	    g2.fillPolygon(xpoints, ypoints, npoints);
	}
	
	private void paintY(Graphics2D g2, int i, int j) {
		double w = squareWidth();
		double r = w * (1.0 - LINE_WIDTH - 2.0 * PADDING); // premer Y
		double x = w * (i + 0.5 * LINE_WIDTH + PADDING);
		double y = w * (j + 0.5 * LINE_WIDTH + PADDING);
		g2.setColor(Color.red);
		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
	    int xpoints[] = {(int) (x), (int) (x), (int) (x+r*0.9)};
	    int ypoints[] = {(int) (y), (int) (y+r), (int) (y+r*0.5)};
	    int npoints = 3;
	    g2.fillPolygon(xpoints, ypoints, npoints);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		// širina kvadratka
		double w = squareWidth();
		// èrte
		g2.setColor(Color.gray);
		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
		for (int i = 1; i < Igra.N; i++) {
			g2.drawLine((int)(i * w),
					    (int)(LINE_WIDTH * w),
					    (int)(i * w),
					    (int)((Igra.N - LINE_WIDTH) * w));
			g2.drawLine((int)(LINE_WIDTH * w),
					    (int)(i * w),
					    (int)((Igra.N - LINE_WIDTH) * w),
					    (int)(i * w));
		}
		
		// avtomobilèki (za igralca X in Y)
		Polje[][] plosca = master.getPlosca();
		if (plosca != null) {
			for (int i = 0; i < Igra.N; i++) {
				for (int j = 0; j < Igra.N; j++) {
					switch(plosca[i][j]) {
					case VERTICAL: paintX(g2, i, j); break;
					case HORIZONTAL: paintY(g2, i, j); break;
					default: break;
					}
				}
			}
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
		if (0 <= i && i < Igra.N &&
		    0.5 * LINE_WIDTH < di && di < 1.0 - 0.5 * LINE_WIDTH &&
		    0 <= j && j < Igra.N && 
		    0.5 * LINE_WIDTH < dj && dj < 1.0 - 0.5 * LINE_WIDTH) {
			master.klikniPolje(i, j);
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

	public void keyPressed(KeyEvent e) {

	    int key = e.getKeyCode();

	    if (key == KeyEvent.VK_LEFT) {
	        Smer levo = Smer.LEVO;
	    }

	    if (key == KeyEvent.VK_UP) {
	        Smer gor = Smer.NAPREJ;
	    }

	    if (key == KeyEvent.VK_RIGHT) {
	        Smer desno = Smer.DESNO;
	    }

	}
		
}