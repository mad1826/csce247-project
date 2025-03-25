package com.musicapp;

import com.model.MusicAppFacade;
import com.model.OperationResult;
import com.model.Song;
import com.model.User;

/**
 * A terminal-based interface for interacting with the application.
 * @author Michael Davis
 */
public class MusicAppDriver {
	/**
	 * The facade that the driver will interact through
	 */
	private final MusicAppFacade facade = MusicAppFacade.getInstance();

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
		signUp(true);
		// signUp(false);
		login(true);
		// login(false);
		playSong(true);
		// playSong(false);
	}

	/**
	 * Sign up for a new account.
	 */
	public void signUp(boolean valid) {
		System.out.println("Signing up for a new account...");
		OperationResult<User> or = facade.signUp("Portia", "Plante", valid ? "pplante@email.sc.edu" : "jane.smith@example.com", "secu4eP@ssw0rd");
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
	 * Logs in to an existing account.
	 */
	public void login(boolean valid) {
		System.out.println("Logging in with existing credentials...");
		facade.login("jane.smith@example.com", valid ? "secureP@ss987" : "wrong-PW0rd");
		User user = facade.getCurrentUser();
		if (user == null) {
			System.out.println("Unable to log in with these credentials.");
			return;
		}

		System.out.println("Welcome " + user.getFirstName() + " " + user.getLastName() + "!");

		boolean changed = facade.logout();
		if (changed)
			System.out.println("You have successfully logged out.");
	}

	/**
	 * List all songs, then filter all songs by a query and plays the first song found.
	 */
	public void playSong(boolean valid) {
		facade.login("jane.smith@example.com", "secureP@ss987");
		User user = facade.getCurrentUser();
		if (user == null) {
			System.out.println("Login was unsuccessful.");
			return;
		}
		
		System.out.println("Welcome " + user.getFirstName() + " " + user.getLastName() + "!");

		System.out.println("Listing all songs:");
		for (Song song : facade.getSongs().values()) {
			System.out.println("\t" + song.getTitle() + " by " + song.getArtist());
		}

		Song foundSong = null;

		String titleFilter = valid ? "ick" : "fake song";
		System.out.println("Filtering songs by \"title: " + titleFilter + "\"");
		facade.setTitleQuery(titleFilter);
		for (Song song : facade.searhSongs().values()) {
			if (foundSong == null)
				foundSong = song;
			System.out.println("\t" + song.getTitle() + " by " + song.getArtist());
		}

		if (foundSong != null) {
			System.out.println("Now playing: " + foundSong.getTitle() + " by " + foundSong.getArtist());
		}
		else {
			System.out.println("No song was found in the search.");
		}

		boolean changed = facade.logout();
		if (changed)
			System.out.println("You have successfully logged out.");
	}
}
