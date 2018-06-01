package logika;

public enum Igralec {
	VERTICAL, // igralec, katerega figure imajo cilj v vertikalni smeri
	HORIZONTAL; // igralec, katerega figure imajo cilj v horizontalni smeri

	public Igralec nasprotnik() {
		return (this == VERTICAL ? HORIZONTAL : VERTICAL);
	}

	public Polje getPolje() {
		return (this == VERTICAL ? Polje.VERTICAL : Polje.HORIZONTAL);
	}
}
