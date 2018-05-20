package logika;

public enum Polje {
	PRAZNO,
	VERTICAL, // potuje od spodaj navzgor
	HORIZONTAL; // potuje z leve proti desni
	
	public String toString() {
		switch (this) {
		case PRAZNO: return " ";
		case VERTICAL: return "vertikalno";
		case HORIZONTAL: return "horizontalno";
		default: return "?";
		}
	}
}
