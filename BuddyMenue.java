package Buddy;

import java.util.Scanner;

public class BuddyMenue {
	private static Buddy buddy = new Buddy();

	static Scanner s = new Scanner(System.in);

	public static void main(String[] args) {

		menue();

	}

	public static void menue() {
		boolean again = true, pfadAusgabeSwitch = false; // um Speicherpfad zB "QLLLRLRLRL" mit ausgeben. Default =
															// False

		start();
		while (again) {

			System.out.print("\nWas möchten Sie tun?\n" + "\"1\" Neue Speicheranforderung\t | "
					+ "\"2\" Prozess beenden | " + "\"3\" Speicherbelegung ausgeben \n"
					+ "\"4\" Speicherpfad Ausgabe Ein/Aus | " + "\"5\" Programm beenden\n");

			char entscheidung = s.next().charAt(0);
			switch (entscheidung) {

			case '1':
				menueAuswahl1();

				break;

			case '2':
				menueAuswahl2();
				break;
			case '3':
				buddy.ausgabe();
				break;
			case '4':
				pfadAusgabeSwitch = !pfadAusgabeSwitch;
				buddy.pfadAusgabe = pfadAusgabeSwitch; // Die Ausgabe des Pfades wird hier umgeschaltet(geswitcht)...
				buddy.ausgabe(); // ...jenachdem, ob es vorher true oder false war.
				break;
			case '5':
				again = false;
				System.out.println("Ende des Programms!");
				break;
			}
		}
	}

	public static void start() {

		System.out.print("Bitte geben Sie den Gesamtspeicher an: ");
		int blockgroeße = s.nextInt();
		if ((blockgroeße < 0) ^ (blockgroeße == 0)) {
			System.out.println("Blockgröße darf nicht negativ oder 0 sein\nStarten sie das Programm neu !");

		} else {
			int prozessAnforderung = (int) buddy.naechsteZweierPotenz(blockgroeße);

			System.out.println("Speicher Initialwert: " + prozessAnforderung);
			buddy.blockeingabe(prozessAnforderung);

		}

	}

	public static void menueAuswahl1() {

		System.out.println("Geben sie eine Speicheranforderung ein: ");
		int prozess = s.nextInt();
		
		double nextHigherPow = 0;
		// die Potenz k ist unbekannt. Erst wenn nextHigherPow groesser gleich
		// anforderung ist hat man eine 2er-Potenz
		for (double k = 0; nextHigherPow < prozess; k++) {
			nextHigherPow = Math.pow(2.0, k);
		}
		if (prozess <= 0) {
			nextHigherPow = 1;
		}
		System.out.println("Speicheranforderung wurde auf 2er potenz aufgerundet!");
		prozess = (int) nextHigherPow;
		boolean prozessZugewiesen = false;

		System.out.println("SpeicherAnforderung: " + prozess + "MB");

		System.out.println("");
		prozessZugewiesen = buddy.findBlock(prozess, Prozess.zaehler);

		if (prozessZugewiesen) {

			System.out.println();
			System.out.println("Speicher wurde zugewiesen mit der Prozess-ID P:" + (Prozess.zaehler));
			Prozess.zaehler++;

		} else
			System.out.println("Speicher wurde nicht zugewiesen!");

		buddy.ausgabe();
		System.out.println(
				"__________________________________________________________________________________________________________________________________________________");

	}

	public static void menueAuswahl2() {

		System.out.println("Welcher Prozess soll Beendet werden ? (id eingeben)");
		
		int loeschen = s.nextInt();
		System.out.println();

		buddy.ausgabe();
		System.out.println("\t\tLösch pID: " + loeschen);
		buddy.freigabe(loeschen);

		System.out.println(
				"__________________________________________________________________________________________________________________________________________________");

		buddy.ausgabe();
		System.out.println("");

	}

}




