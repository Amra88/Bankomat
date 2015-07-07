package bankomat;

import java.util.ArrayList;
import java.util.List;

//klasa User sadrzi data fields i metode za kreiranje novog korisnika
public class User {

	private String name;
	private int password;
	private double accountBalance;
	public boolean isAdmin;

	public User() {

	}

	public User(String name, int password) {
		this.name = name;
		this.password = password;
		isAdmin = true;

	}

	public User(String name, int password, double accountBalance,
			boolean isAdmin) {
		this.name = name;
		this.password = password;
		this.accountBalance = accountBalance;
		this.isAdmin = isAdmin;

	}

	public User(String fromFile) {
		// rasclanjivanje unesenih podataka
		// razdvajanje pomocu zareza
		String[] arr = fromFile.split(",");

		name = arr[0];
		password = Integer.parseInt(arr[1]);
		accountBalance = Double.parseDouble(arr[2]);
		isAdmin = Boolean.parseBoolean(arr[3]);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPassword() {
		return password;
	}

	public void setPassword(int password) {
		this.password = password;
	}

	public double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	// metod za isplatu novca korisniku
	public void withdraw(double amount) throws Exception {
		
		AtmMachine atm = new AtmMachine();
		if (amount > accountBalance) {
			System.out
					.println("\n\t\tNemate dovoljno novca na racunu!\n\t\tTrenutni iznos na Vasem racunu je: "
							+ accountBalance + " KM");
		} else if (amount % 10 != 0) {
			System.out
					.println("\n\t\tBankomat moze isplatiti samo novcanice od 10, 20, 50 i 100KM!");
		} else if (amount > atm.getAtmBalance()) {
			System.out
					.println("\n\t\tNije moguce isplatiti trazeni iznos.\n\t\tMolimo posjetite drugi bankomat ili nasu poslovnicu.\n\t\tHvala!");
		} else {
			atm.payout(amount);// metod iz AtmMachine klase, prikazuje isplatu
								// pojedinih novcanica
			accountBalance -= amount;
			System.out.println("\n\t\tVas racun je umanjen za " + amount
					+ " KM.\n\t\tPreostalo na racunu: " + accountBalance + ".");

		}
	}

	// prikaz odredjenog korisnika
	@Override
	public String toString() {

		return "Korisnik: " + name + "\nPassword:\t" + password
				+ "\nStanje na Vasem racunu je: " + accountBalance;
	}
}
