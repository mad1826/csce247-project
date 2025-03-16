package com.model;

import java.util.Scanner;

/**
 * A terminal-based interface for interacting with the application.
 * @author Michael Davis
 */
public class MusicAppDriver {
	/**
	 * A scanner that collects responses from the default system input device
	 */
	public static Scanner scanner = new Scanner(System.in);

	/**
	 * The facade that the driver will interact through
	 */
	public static MusicAppFacade facade = MusicAppFacade.getInstance();

	public static void main(String[] args) {
		User user = authenticate();

		if (user == null)
			return;

		System.out.println("Welcome " + user.getFirstName() + " " + user.getLastName() + "!");
	}

	/**
	 * Authenticates the user through their preferred authentication method
	 * @return the User instance that should be logged in with
	 */
	public static User authenticate() {
		System.out.println("Welcome to the music app! Please select one of the following authentication methods:\n(1) Log in\n(2) Sign Up\n(9) Quit");

		User ret = null;

		while (ret == null) {
			int method = scanner.nextInt();
			scanner.nextLine();

			switch (method) {
				case 1: {
					System.out.println("Enter your email address.");
					String address = scanner.nextLine();
					System.out.println("Enter your password.");
					String password = scanner.nextLine();
					
					User result = facade.login(address, password);
					if (result != null) {
						ret = result;
					}
					else {
						System.out.println("Invalid credentials. Please select a method and try again.");
					}
					break;
				}
				case 2: {
					// TODO Create new user account
					break;
				}
				case 9: {
					return null;
				}
				default: {
					System.out.println("Unexpected option, please select an authentication method again.");
				}
			}
		}

		return ret;
	}
}
