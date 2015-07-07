package bankomat;

import java.io.*;
import java.util.Scanner;

//klasa AtmMachine sadrzi sve vezano za bankomat i novcanice u bankomatu
public class AtmMachine extends User {
	// novcanice
	private static int bill10 = 10;
	private static int bill20 = 20;
	private static int bill50 = 50;
	private static int bill100 = 100;
	// brojaci novcanica
	private static int count10 = 60;
	private static int count20 = 30;
	private static int count50 = 20;
	private static int count100 = 10;
	// ukupno stanje novca u bankomatu
	private int atmBalance = (bill10 * count10) + (bill20 * count20)
			+ (bill50 * count50) + (bill100 * count100);

	public AtmMachine() {
	}

	public int getBill10() {
		return bill10;
	}

	public void setBill10(int bill10) {
		this.bill10 = bill10;
	}

	public int getBill20() {
		return bill20;
	}

	public void setBill20(int bill20) {
		this.bill20 = bill20;
	}

	public int getBill50() {
		return bill50;
	}

	public void setBill50(int bill50) {
		this.bill50 = bill50;
	}

	public int getBill100() {
		return bill100;
	}

	public void setBill100(int bill100) {
		this.bill100 = bill100;
	}

	public int getCount10() {
		return count10;
	}

	public void setCount10(int count10) {
		this.count10 = count10;
	}

	public int getCount20() {
		return count20;
	}

	public void setCount20(int count20) {
		this.count20 = count20;
	}

	public int getCount50() {
		return count50;
	}

	public void setCount50(int count50) {
		this.count50 = count50;
	}

	public int getCount100() {
		return count100;
	}

	public void setCount100(int count100) {
		this.count100 = count100;
	}

	public int getAtmBalance() {
		return atmBalance;
	}

	public void setAtmBalance(int atmBalance) {
		this.atmBalance = atmBalance;
	}

	// metod za upis stanja novcanica u file
	public void saveBillsToFile(AtmMachine atm) throws Exception {
		File file = new File("bills.txt");
		PrintWriter pw = new PrintWriter(file);
		try {
			pw.print(atm.getCount100() + " ");
			pw.print(atm.getCount50() + " ");
			pw.print(atm.getCount20() + " ");
			pw.print(atm.getCount10());
		} finally {
			pw.close();
		}
	}

	// metod za ucitavanje stanja novcanica u bankomatu iz file-a
	public static AtmMachine readBillsFromFile() throws Exception {
		AtmMachine a = new AtmMachine();
		File file = new File("bills.txt");
		Scanner fromFile = new Scanner(file);

		while (fromFile.hasNextInt()) {

			a.setCount100(fromFile.nextInt());
			a.setCount50(fromFile.nextInt());
			a.setCount20(fromFile.nextInt());
			a.setCount10(fromFile.nextInt());

		}
		return a;
	}

	// metod za ispis stanja novcanica u bankomatu
	public void billsState() throws Exception {
		System.out.println("\nBankomat sadrzi:\n" + count100 + " novcanica od "
				+ bill100 + "KM;\n" + count50 + " novcanica od " + bill50
				+ "KM;\n" + count20 + " novcanica od " + bill20 + "KM;\n"
				+ count10 + " novcanica od " + bill10 + "KM.");
	}

	// metod za dodavanje novcanica u bankomat
	public void fillBills() throws Exception {
		AtmMachine atm = new AtmMachine();
		Scanner input = new Scanner(System.in);
		System.out
				.println("\n1. Dodati novcanice od 10KM\n2. Dodati novcanice od 20KM\n3. Dodati novcanice od 50KM\n4. Dodati novcanice od 100KM");
		int option = input.nextInt();
		System.out.println("\nKoliko novcanica zelite dodati: ");
		int numOfBills = input.nextInt();

		if (option == 1) {
			int max = 60;

			if (getCount10() + numOfBills > max) {
				System.out.println("\n\t\tBankomat moze sadrzati maksimalno "
						+ max + " novcanica od 10KM! Pokusajte ponovo!");
			} else {
				setCount10(count10 + numOfBills);
				System.out.println("\n\t\tNovcanice dodane!");
			}

			billsState();// ispis ukupnog stanja novcanica
		} else if (option == 2) {
			int max = 30;

			if (getCount20() + numOfBills > max) {
				System.out.println("\n\t\tBankomat moze sadrzati maksimalno "
						+ max + " novcanica od 20KM! Pokusajte ponovo!");
			} else {
				setCount20(count20 + numOfBills);
				System.out.println("\n\t\tNovcanice dodane!");
			}

			billsState();
		} else if (option == 3) {
			int max = 20;

			if (getCount50() + numOfBills > max) {
				System.out.println("\n\t\tBankomat moze sadrzati maksimalno "
						+ max + " novcanica od 50KM! Pokusajte ponovo!");
			} else {
				setCount50(count50 + numOfBills);
				System.out.println("\n\t\tNovcanice dodane!");
			}

			billsState();
		} else if (option == 4) {
			int max = 10;

			if (getCount100() + numOfBills > max) {
				System.out.println("\n\t\tBankomat moze sadrzati maksimalno "
						+ max + " novcanica od 100KM! Pokusajte ponovo!");
			} else {
				setCount100(count100 + numOfBills);
				System.out.println("\n\t\tNovcanice dodane!");
			}

			billsState();
		} else {
			System.out.println("\n\t\tNeispravan unos!");
		}

		saveBillsToFile(atm);// spremanje stanja novcanica u file
	}

	// metod za odabir novcanica za isplatu-isplacuje najvecu mogucu novcanicu
	public void payout(double amount) throws Exception {
		AtmMachine atm = new AtmMachine();
		// varijable koje broje isplacene novcanice
		int c100 = 0;
		int c50 = 0;
		int c20 = 0;
		int c10 = 0;

		while (amount > 0) {
			if (amount / 100 >= 1 && count100 >= c100) {
				count100--;// brojac novcanica u bankomatu se umanjuje
				amount -= bill100;// iznos se umanjuje za vrijednost novcanica
									// koje su isplacene
				c100++;// brojac isplacenih novcanica
			} else if (amount / 50 >= 1 && count50 >= c50) {
				count50--;
				amount -= bill50;
				c50++;

			} else if (amount / 20 >= 1 && count20 >= c20) {
				count20--;
				amount -= bill20;
				c20++;
			} else if (amount / 10 >= 1 && count10 >= c10) {
				count10--;
				amount -= bill10;
				c10++;
			} else {
				System.out
						.println("\n\t\tNije moguce isplatiti trazeni iznos.\n\t\tMolimo posjetite drugi bankomat ili nasu poslovnicu.\n\t\tHvala!");
			}
		}

		saveBillsToFile(atm);// spremanje novog stanja u file
		System.out.println("\nIsplata: ");// prikaz novcanica koje su isplacene
		if (c100 > 0) {
			System.out.println("\n---> " + c100 + " novcanica od " + bill100
					+ "KM;");
		}
		if (c50 > 0) {
			System.out.println("\n---> " + c50 + " novcanica od " + bill50
					+ "KM;");
		}
		if (c20 > 0) {
			System.out.println("\n---> " + c20 + " novcanica od " + bill20
					+ "KM;");
		}
		if (c10 > 0) {
			System.out.println("\n---> " + c10 + " novcanica od " + bill10
					+ "KM;");
		}

	}

	// upozorenje adminu u slucaju da je u bankomatu ostalo 5 ili manje
	// novcanica (od 10, 20, 50, ili 100KM)
	// pojavljuje se nakon sto se admin loguje
	public static void printWarning() {

		if (count100 <= 5 || count50 <= 5 || count20 <= 5 || count10 <= 5) {
			System.out
					.println("\n\t\tUPOZORENJE!\n\t\tManjak pojedinih novcanica u bankomatu! \n\t\tProvjerite stanje ili dopunite novcanice!");
		}
	}

}
