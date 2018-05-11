package logika;

public enum Igralec {
	X, Y;

	public Igralec nasprotnik() {
		return (this == X ? Y : X);
	}

	public Polje getPolje() {
		return (this == X ? Polje.X : Polje.Y);
	}
}
