package logika;

public enum Smer {
	LEVO,
	DESNO,
	NAPREJ,
	ODSTRANI;

	public String toString() {
		switch (this) {
		case LEVO: return "levo";
		case DESNO: return "desno";
		case NAPREJ: return "naprej";
		case ODSTRANI: return "odstrani";
		
		default: return "?";
		}
	}
}
