package Buddy;

import java.util.ArrayList;

public class Buddy {
	private ArrayList<Prozess> prozessTbl = new ArrayList<Prozess>();
	public boolean pfadAusgabe = false;

	public void blockeingabe(int memSize) {
		//der Hauptnlock wird mit Q gekennzeichnet für jede teilung des Hauptblocks wird
		//den buddys ein R oder L (rechts oder links) beigefügt.Somit kann bei der pfadangabe
		//nachvollzogen werden wie oft der Hauptblock schon geteilt wurde.
		prozessTbl.add(new Prozess("Q", memSize, 0));
		ausgabe();
	}

	public double naechsteZweierPotenz(int anforderung) {
		double nextHigherPow = 0;
		// die Potenz k ist unbekannt. Erst wenn nextHigherPow groesser gleich
		// anforderung ist hat man eine 2er-Potenz
		for (double k = 0; nextHigherPow < anforderung; k++) {
			nextHigherPow = Math.pow(2.0, k);
		}

		return nextHigherPow;

	}

	public boolean findBlock(int pSize, int pID) {
		//die blöcke werden mit einer prozess id gekennzeichnet id=0 steht für einen freien block
		Prozess aktuellerBlock, linkerBlock, rechterBlock;
		for (int index = 0; index < prozessTbl.size(); index++) {

			aktuellerBlock = prozessTbl.get(index);
			// Falls der Block frei ist
			if (aktuellerBlock.getProzessId() == 0) {
				// und  der hauptblock größer als die speicheraufforderung ist
				if (aktuellerBlock.getProzessSize() > pSize) {
					// dann teile ihn in zwei blöcke(buddys)
					linkerBlock = new Prozess(aktuellerBlock.getProzessPfad() + "L",
							aktuellerBlock.getProzessSize() / 2, 0);
					//die größe wird druch 2 geteilt und die id 0 wird vergeben
					rechterBlock = new Prozess(aktuellerBlock.getProzessPfad() + "R",
							aktuellerBlock.getProzessSize() / 2, 0);
					// setze den linken block an der stelle des index
					prozessTbl.set(index, linkerBlock);
					if (prozessTbl.size() - (index + 1) == 0) // Falls es das ende der liste ist dann
						// erweitere die liste um den rechten block
						prozessTbl.add(rechterBlock);

					else
						prozessTbl.add(index + 1, rechterBlock); // sonst setze ihn neben

					// damit beim nächsten druchlauf wieder der linke blcok betrachtet wird
					index--;

					ausgabe();
				}
				// Falls die Speicheraufforderung gleich blockgröße
				else if (aktuellerBlock.getProzessSize() == pSize) {
					aktuellerBlock.setProzessId(pID);

					return true;

				}
			}

		}
		return false;
	}

	public void freigabe(int pID) {

		Prozess aktuellerBlock;
		for (int index = 0; index < prozessTbl.size(); index++) {
			aktuellerBlock = prozessTbl.get(index);
			// falls die input id == mit der aktuellen id
			if (aktuellerBlock.getProzessId() == pID) {
				// dann setze die block id auf 0
				aktuellerBlock.setProzessId(0);

				// speicher den leeren block in der liste
				prozessTbl.set(index, aktuellerBlock);

				checkBuddy(index);
				break;
			}

		}
		if ((pID == 0) ^ (pID < 0) ^ (pID > prozessTbl.size())) {
			System.out.println("Prozess mit der ID: " + pID + " existiert nicht/mehr!");
		}

	}

	public void checkBuddy(int index) {
		Prozess prozessQuelle = prozessTbl.get(index);
		// letzter char des string ProzessLvl gibt die Richtung an
		String whichWay = prozessQuelle.getProzessPfad().substring(prozessQuelle.getProzessPfad().length() - 1);
		// stelle 0 bis length()-1 wird dem zusammengefassten buddy uebergeben
		String newProzessLvl = prozessQuelle.getProzessPfad().substring(0, prozessQuelle.getProzessPfad().length() - 1);
		switch (whichWay) {
		case "L":
			// buddy ist auf der rechten seite
			Prozess buddyIsRight = prozessTbl.get(index + 1);
			// falls der buddy rechts frei ist und das lvl des linken buddy gleicht
			if ((buddyIsRight.getProzessId() == 0) && (buddyIsRight.getProzessPfad().equals(newProzessLvl + "R"))) {
				System.out.print(" Zusammengefasst zu: ");
				// ersetze in der ArrayList das alte objekt mit dem neuen
				prozessTbl.set(index, new Prozess(newProzessLvl, buddyIsRight.getProzessSize() * 2, 0));
				prozessTbl.remove(index + 1);
				ausgabe();
				// wird so lange wiederhold bis die if anweisung(id==0) nicht erfüllt wird
				checkBuddy(index);
			}
			break;
		case "R":
			Prozess buddyIsLeft = prozessTbl.get(index - 1);//der buddy ist auf der linken seite

			if ((buddyIsLeft.getProzessId() == 0) && (buddyIsLeft.getProzessPfad().equals(newProzessLvl + "L"))) {

				System.out.print(" Zusammengefasst zu: ");
				prozessTbl.set(index - 1, new Prozess(newProzessLvl, buddyIsLeft.getProzessSize() * 2, 0));
				prozessTbl.remove(index);
				ausgabe();

				checkBuddy(index - 1);
			}

			break;

		}
	}

	public void ausgabe() {
		// ArrayList mit den Prozessen wird ausgegeben
		String endAusgabe = "";

		for (int index = 0; index < prozessTbl.size(); index++) {
			// falls im menue pfafAusgabe nicht gwählt wurde dann ohne pfad
			if (pfadAusgabe == false) {
				endAusgabe = endAusgabe + prozessTbl.get(index).getProzessSize() + "MB,P:"
						+ prozessTbl.get(index).getProzessId() + "| ";
			}
			// Falls doch dann mit pfad
			else {
				endAusgabe = endAusgabe + prozessTbl.get(index).getProzessPfad() + ":"
						+ prozessTbl.get(index).getProzessSize() + "MB,P:" + prozessTbl.get(index).getProzessId()
						+ "| ";
			}
		}
		System.out.println(endAusgabe);

	}

}

