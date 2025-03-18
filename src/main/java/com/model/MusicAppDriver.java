package com.model;

import java.util.HashMap;

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
	}

	/**
	 * Logs in with a user and then immediately logs out.
	 */
	public void logInAndOut() {
		facade.login("jane.smith@example.com", "secureP@ss987");
		User user = facade.getCurrentUser();
		System.out.println("Welcome " + user.getFirstName() + " " + user.getLastName() + "!");

		boolean changed = facade.logout();
		if (changed)
			System.out.println("You have successfully logged out.");
	}

	public void searchSongs() {
		facade.login("jane.smith@example.com", "secureP@ss987");
		User user = facade.getCurrentUser();
		System.out.println("Welcome " + user.getFirstName() + " " + user.getLastName() + "!");

		System.out.println("Listing all songs:");
		for (Song song : facade.getSongs().values()) {
			System.out.println("\t" + song);
		}

		String titleFilter = "sound";
		System.out.println("Filtering songs by \"title: " + titleFilter + "\"");
		HashMap<SongFilter, String> query = new HashMap<>();
		query.put(SongFilter.TITLE, titleFilter);
		for (Song song : facade.findSongs(query).values()) {
			System.out.println("\t" + song);
		}

		boolean changed = facade.logout();
		if (changed)
			System.out.println("You have successfully logged out.");
	}
}
