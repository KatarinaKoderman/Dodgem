package logika;

public enum Igralec {
	VERTICAL, HORIZONTAL;

	public Igralec nasprotnik() {
		return (this == VERTICAL ? HORIZONTAL : VERTICAL);
	}

	public Polje getPolje() {
		return (this == VERTICAL ? Polje.VERTICAL : Polje.HORIZONTAL);
	}
}
