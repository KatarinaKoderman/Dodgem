package Logika;

public enum Smer {
	LEVO,
	DESNO,
	NAPREJ;

	public String toString() {
		switch (this) {
		case LEVO: return "levo";
		case DESNO: return "desno";
		case NAPREJ: return "naprej";
		default: return "?";
		}
	}
}
