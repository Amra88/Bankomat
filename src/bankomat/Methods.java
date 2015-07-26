package bankomat;

import java.io.*;
import java.util.*;

//klasa koja sadrzi razne metode
public class Methods {
	// metod za ucitavanje liste korisnika iz file-a
	public static List<User> loadUserFromFile() throws IOException {

		List<User> userList = new ArrayList<User>();
		BufferedReader br = null;

		try {
			br = new BufferedReader(new FileReader("users.csv"));
			String lineFromFile = "";

			while ((lineFromFile = br.readLine()) != null) {
				userList.add(new User(lineFromFile));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			br.close();
		}

		return userList;
	}

	// metod za spremanje korisnika u file, kao i promjena vezanih za korisnike
	public static void saveUsersToFile(List<User> userList) {
		File file = new File("users.csv");
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(file);
			for (User user : userList) {
				pw.println(user.getName() + "," + user.getPassword() + ","
						+ user.getAccountBalance() + "," + user.isAdmin());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pw.close();
		}
	}

	// Login korisnika
	public static User login(List<User> userList) {
		Scanner input = new Scanner(System.in);
		final int LIMIT = 2;
		int userIndex = -1;
		int passCount = 0;
		int userCount = 0;
		User user = null;
		String username;
		int password;
		boolean condition = true;
		boolean usernameCondition = true;
		boolean passCondition = false;

		while (condition) {
			while (usernameCondition) {
				userCount++;
				System.out.println("\nUnesite Vase ime: ");
				username = input.nextLine();
				for (User u : userList) {
					if (u.getName().equals(username)) {
						userIndex = userList.indexOf(u);
						usernameCondition = false;
						passCondition = true;
					}
				}

				if (usernameCondition && userCount <= LIMIT) {
					System.out.println("\n\t\t" + username
							+ " nije ispravno. Pokusajte ponovo!");
				}

				if (userCount > LIMIT) {
					System.out
							.println("\n\t\tUnijeli ste pogresno ime 3 puta. Pokusajte ponovo!");
					usernameCondition = false;
					condition = false;

				}
			}

			while (!usernameCondition && passCondition) {
				System.out.println("\nUnesite password "
						+ userList.get(userIndex).getName() + ": ");
				password = input.nextInt();
				passCount++;

				for (User u : userList) {
					if (u.getPassword() == password) {
						user = userList.get(userIndex);
						passCondition = false;
					}
				}

				if (passCondition && passCount <= LIMIT) {
					System.out
							.println("\n\t\tUnijeli ste pogresan password. Pokusajte ponovo!");
				}

				if (passCount > LIMIT) {
					System.out
							.println("\n\t\tUnijeli ste pogresan password 3 puta. Izlaz!");
					passCondition = false;
					condition = false;
				}
			}

			if (!usernameCondition && !passCondition) {
				condition = false;
			}
		}

		return user;
	}

	// ispis korisnickog menija
	public static void printUserMenu() {
		System.out.println("\n1. Stanje na racunu\n" + "2. Podizanje novca\n"
				+ "0. Izlaz");
	}

	// ispis menija za admina
	public static void printAdminMenu() {
		System.out.println("\n1. Prikazi sve korisnike\n"
				+ "2. Prikazi odredjenog korisnika\n"
				+ "3. Brisanje postojeceg korisnika\n"
				+ "4. Dodavanje korisnika \n" + "5. Stanje novcanica \n"
				+ "6. Dodavanje novcanica \n" + "0. Izlaz\n");
	}

	// metod koji sadrzi opcije za korisnika
	public static void userMenu(User user) throws Exception {

		Scanner input = new Scanner(System.in);
		int option = -1;
		while (option != 0) {
			printUserMenu();// printanje menija
			option = input.nextInt();
			if (option == 1) {
				System.out.println(user);// prikaz odredjenog korisnika
			} else if (option == 2) {
				System.out.println("\nUnesite iznos koji zelite podici: ");
				double amount = input.nextDouble();
				user.withdraw(amount);// podizanje novca
			} else if (option == 0) {
				System.out
						.println("\n\t\tHvala sto koristite nase usluge!\n\t\tDovidjenja "
								+ user.getName() + "!");

			} else {
				System.out
						.println("\n\t\tPogresan unos.\n\t\tPokusajte ponovo!");
			}
		}
	}

	// metod koji sadrzi opcije za admina
	public static void adminMenu(List<User> userList) throws Exception {
		Scanner input = new Scanner(System.in);
		int option = -1;
		AtmMachine atm = new AtmMachine();
		atm.printWarning();
		while (option != 0) {
			printAdminMenu();// printanje menija
			option = input.nextInt();
			if (option == 1) {
				Admin.printAllUsers(userList);// prikaz svih korisnika
			} else if (option == 2) {
				int option2;

				do {
					// prikaz odredjenog korisnika
					System.out.println("\nUnesite ime: ");
					String name = input.next();
					User user = Admin.findUser(userList, name);
					if (user != null) {
						System.out.println("\n" + user.toString());
					} else {
						System.out
								.println("\n\t\tKorisnik ne postoji.\n\t\tPokusajte ponovo.");
					}
					System.out.println("\n\t1. Pokusati ponovo?\n\t0. Izlaz");
					option2 = input.nextInt();
				} while (option2 != 0);

			} else if (option == 3) {// brisanje korisnika
				System.out.println("\nUnesite korisnicko ime: ");
				String name = input.next();
				User user = Admin.findUser(userList, name);
				if (user != null) {
					Admin.deleteUserFromList(userList, user);

				} else {
					System.out.println("\n\t\tError! Pokusajte ponovo!");
				}
			} else if (option == 4) {// dodavanje korisnika
				Admin.addUser(userList);
			} else if (option == 5) {// prikaz stanja novcanica
				atm.billsState();
			} else if (option == 6) {// dopuna novcanica
				atm.fillBills();
			} else if (option == 0) {
				System.out.println("\n\t\tIzlaz... \n\t\tDovidjenja! \n");
			} else {
				System.out.println("\n\t\tPogresan unos! Pokusajte ponovo!");
			}
		}
	}

	// metod koji provjerava da li korisnik vec postoji u file-u
	public static boolean checkUser(User u) {
		User u1 = new User();
		if (u.getName().equals(u1.getName())
				&& u.getPassword() == (u1.getPassword())) {

			
		}
		return true;
	}
}
