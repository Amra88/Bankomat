package bankomat;

import java.util.*;

public class TestBankomat {

	public static void main(String[] args) throws Exception {
		Scanner input = new Scanner(System.in);
		List<User> userList = Methods.loadUserFromFile();// ucitavanje korisnika
															// iz file-a
		User user;
		AtmMachine atm = new AtmMachine();
		atm.readBillsFromFile();// ucitavanje stanja novcanica iz file-a

		int option = -1;
		// pokretanje programa i pocetni meni
		while (option != 0) {
			System.out.println("\n\t\tDobro dosli na bankomat!!!");
			System.out.println("\n1. Login\n0. Izlaz!");
			option = input.nextInt();
			if (option == 1) {
				user = Methods.login(userList);// logovanje korisnika
				if (user != null) {
					if (user.isAdmin()) {
						Methods.adminMenu(userList);// prikaz menija za admina
					} else {
						Methods.userMenu(user);// ili za korisnika
					}
				} else {
					System.out.println("\n\t\tNije moguce.Pokusajte ponovo!");
				}
			} else if (option == 0) {
				System.out.println("\n\t\tBankomat ugasen!");// izlaz iz
																// programa
			} else {
				System.out.println("\n\t\tNije moguce!");
			}

		}

		Methods.saveUsersToFile(userList);

	}
}
