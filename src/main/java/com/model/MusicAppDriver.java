package com.model;

/**
 * A terminal-based interface for interacting with the application.
 * @author Michael Davis
 */
public class MusicAppDriver {
	/**
	 * The facade that the driver will interact through
	 */
	public MusicAppFacade facade = MusicAppFacade.getInstance();

	/**
	 * Create a new driver and run its designated scenarios.
	 * @param args - options passed in through the command line
	 */
	public static void main(String[] args) {
		MusicAppDriver driver = new MusicAppDriver();
		driver.run();
	}

	/**
	 * Runs one or more individual scenarios.
	 */
	public void run() {
		logInAndOut();
		searchSongs();
		signUp();
		signUpInvalid();
	}

	/**
	 * Logs in with a user and then immediately logs out.
	 */
	public void logInAndOut() {
		facade.login("jane.smith@example.com", "secureP@ss987");
		User user = facade.getCurrentUser();
		if (user == null) {
			System.out.println("Login was unsuccessful.");
			return;
		}

		System.out.println("Welcome " + user.getFirstName() + " " + user.getLastName() + "!");

		boolean changed = facade.logout();
		if (changed)
			System.out.println("You have successfully logged out.");
	}

	/**
	 * Sign up for a new account.
	 */
	public void signUp() {
		System.out.println("Signing up for a new account...");
		OperationResult<User> or = facade.signUp("Portia", "Plante", "pplante@email.sc.edu", "secu4eP@ssw0rd");
		if (or.success) {
			User user = facade.getCurrentUser();
			System.out.println("Welcome " + user.getFirstName() + " " + user.getLastName() + "!");

			boolean changed = facade.logout();
			if (changed)
				System.out.println("You have successfully logged out.");
		}
		else {
			System.out.println("Your account could not be created: " + or.message);
		}
	}

	/**
	 * Attempt to sign up for a new account with an email that an account is already regitered to.
	 */
	public void signUpInvalid() {
		System.out.println("Signing up for a new account...");
		OperationResult<User> or = facade.signUp("Portia", "Plante","jane.smith@example.com", "secu4eP@ssw0rd");
		if (or.success) {
			User user = facade.getCurrentUser();
			System.out.println("Welcome " + user.getFirstName() + " " + user.getLastName() + "!");

			boolean changed = facade.logout();
			if (changed)
				System.out.println("You have successfully logged out.");
		}
		else {
			System.out.println("Your account could not be created for the following reason: " + or.message);
		}
	}

	/**
	 * List all songs, then filter all songs by a query.
	 */
	public void searchSongs() {
		facade.login("jane.smith@example.com", "secureP@ss987");
		User user = facade.getCurrentUser();
		if (user == null) {
			System.out.println("Login was unsuccessful.");
			return;
		}
		
		System.out.println("Welcome " + user.getFirstName() + " " + user.getLastName() + "!");

		System.out.println("Listing all songs:");
		for (Song song : facade.getSongs().values()) {
			System.out.println("\t" + song);
		}

		String titleFilter = "birth";
		System.out.println("Filtering songs by \"title: " + titleFilter + "\"");
		facade.setTitleQuery(titleFilter);
		for (Song song : facade.searhSongs().values()) {
			System.out.println("\t" + song);
		}

		boolean changed = facade.logout();
		if (changed)
			System.out.println("You have successfully logged out.");
	}
}
