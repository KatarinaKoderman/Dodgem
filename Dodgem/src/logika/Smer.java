package logika;

public enum Smer {
	LEVO,
	DESNO,
	GOR,
	DOL,
	ODSTRANI;

	public String toString() {
		switch (this) {
		case LEVO: return "levo";
		case DESNO: return "desno";
		case GOR: return "gor";
		case DOL: return "dol";
		case ODSTRANI: return "odstrani"; 
		
		default: return "?";
		}
	}
}
