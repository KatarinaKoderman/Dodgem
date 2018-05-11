package logika;

public class Poteza {
	private int x;
	private int y;
	private Smer smer;
	
	public Poteza(int x, int y, Smer smer) {
		this.x = x;
		this.y = y;
		this.smer = smer;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	public Smer getSmer() {
		return smer;
	}
	
}
