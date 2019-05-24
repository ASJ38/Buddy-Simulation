package Buddy;

public class Prozess {
	private String prozessPfad;
	private int prozessId, prozessSize;
	static int zaehler = 1;

	public Prozess(String prozessPfad, int prozessSize, int prozessId) {
		this.prozessSize = prozessSize;
		this.prozessPfad = prozessPfad;
		this.prozessId = prozessId;
	}

	public String getProzessPfad() {
		return prozessPfad;
	}

	public void setProzessPfad(String prozessPfad) {
		this.prozessPfad = prozessPfad;
	}

	public int getProzessId() {
		return prozessId;
	}

	public void setProzessId(int prozessId) {
		this.prozessId = prozessId;
	}

	public int getProzessSize() {
		return prozessSize;
	}

	public void setProzessSize(int prozessSize) {
		this.prozessSize = prozessSize;
	}

}
