package bankomat;

import java.io.*;
import java.util.*;

public class Admin {
	// metod za dodavanje novog korisnika
	public static void addUser(List<User> userList) throws Exception {
		Scanner userInput = new Scanner(System.in);
		User u = new User();
		// unos podataka
		System.out.println("\nUnesite ime korisnika: ");
		String userName = userInput.next();
		System.out.println("\nUnesite password(4 cifre): ");
		int userPassword = userInput.nextInt();
		do {
			// provjera passworda - password mora da se sastoji od 4 cifre
			if (String.valueOf(userPassword).length() == 4) {
				u.setPassword(userPassword);
			} else {
				System.out.println("\nNeispravan unos! Pokusajte ponovo: ");
				userPassword = userInput.nextInt();
			}
		} while (String.valueOf(userPassword).length() != 4);

		System.out.println("\nStanje na racunu: ");
		double userBalance = userInput.nextDouble();

		User newUser = new User(userName, userPassword, userBalance, false);
		// provjera da li korisnik sa istim imenom i passwordom vec postoji u
		// file-u
		if (Methods.checkUser(newUser)) {
			System.out
					.println("\n\t\tKorisnik "
							+ newUser.getName()
							+ " vec postoji!\n\t\tUpisite 4 za dodavanje nekog drugog korisnika!");
		} else {
			// ukoliko korisnik ne postoji, dodaje se novi korisnik i upisuje u
			// file
			userList.add(newUser);
			Methods.saveUsersToFile(userList);
			System.out.println("\n\t\tKorisnik uspjesno dodan!");
		}
	}

	// metod za brisanje postojeceg korisnika
	public static void deleteUserFromList(List<User> userList, User user) {
		int index = -1;
		// provjeravanje da li je korisnik admin
		for (User u : userList) {
			if (u.getName().equals(user.getName())) {
				if (!u.isAdmin()) {
					index = userList.indexOf(u);
				} else {
					System.out.println("\n\t\t" + u.getName()
							+ " se ne smije brisati!");
				}
			}
		}
		// ukoliko korisnik nije admin, slijedi brisanje korisnika
		if (index != -1) {
			userList.remove(index);
			Methods.saveUsersToFile(userList);
			System.out.println("\n\t\tKorisnik izbrisan!");
		}
	}

	// metod za prikaz svih korisnika
	public static void printAllUsers(List<User> userList) {
		for (User u : userList) {
			System.out.println("\n" + u.toString());
		}
	}

	// metod za trazenje i prikaz odredjenog korisnika
	public static User findUser(List<User> userList, String username) {
		for (User u : userList) {
			if (u.getName().equals(username)) {
				return u;
			}
		}
		return null;
	}
}
