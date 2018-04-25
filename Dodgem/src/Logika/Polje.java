package Logika;

public enum Polje {
	PRAZNO,
	X,
	Y;
	
	public String toString() {
		switch (this) {
		case PRAZNO: return " ";
		case X: return "X";
		case Y: return "Y";
		default: return "?";
		}
	}
}
