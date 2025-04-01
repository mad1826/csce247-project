package com.musicapp;

import com.model.Course;
import com.model.Lesson;
import com.model.MusicAppFacade;
import com.model.OperationResult;
import com.model.SheetMusic;
import com.model.Song;
import com.model.Student;
import com.model.User;
import com.model.DataHandlers.DataWriter;

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
		signUp(false);
		signUp(true);
		login(true);
		playSong(true);
		progressLesson(true);
	}

	/**
	 * Sign up for a new account.
	 * @param valid whether to show a successful use case
	 */
	public void signUp(boolean valid) {
		System.out.println("Signing up for a new account...");
		OperationResult<User> or = facade.signUp("Fred", "Fredrickson", valid ? "ffred@gmail.com" : "ffredrickson@gmail.com", "secu4eP@ssw0rd", false);
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
	 * @param valid whether to show a successful use case
	 */
	public void login(boolean valid) {
		System.out.println("Logging in with existing credentials...");
		facade.login("ffredrickson@gmail.com", valid ? "secureP@ss987" : "wrong-PW0rd");
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
	 * @param valid whether to show a successful use case
	 */
	public void playSong(boolean valid) {
		facade.login("ffredrickson@gmail.com", "secureP@ss987");
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

		String titleFilter = valid ? "seven" : "fake song";
		System.out.println("Filtering songs by \"title: " + titleFilter + "\"");
		facade.setTitleQuery(titleFilter);
		for (Song song : facade.searhSongs().values()) {
			if (foundSong == null)
				foundSong = song;
			System.out.println("\t" + song.getTitle() + " by " + song.getArtist());
		}

		if (foundSong != null) {
			SheetMusic sheet = facade.playSheet(foundSong, "Piano");
			System.out.println("Printing sheet to text file...");
			
        	String exportPath = "src/main/java/com/data/exported_sheet.txt";
			DataWriter.ExportSheet(exportPath, sheet);
		}
		else {
			System.out.println("No song was found in the search.");
		}

		boolean changed = facade.logout();
		if (changed)
			System.out.println("You have successfully logged out.");
	}

	/**
	 * Progresses a student's lesson.
	 * @param valid whether to show a successful use case
	 */
	public void progressLesson(boolean valid) {
		if (valid) {
			System.out.println("Logging in as a student...");
			facade.login("ffredrickson@gmail.com", "secureP@ss987");
		}
		else {
			System.out.println("Logging in as a teacher...");
			facade.login("john.doe@example.com", "p@ssw0rd123");
		}

		Student student = facade.getCurrentStudent();
		if (student == null) {
			System.out.println("Student login was unsuccessful.");
			return;
		}
		
		System.out.println("Welcome " + student.getFirstName() + " " + student.getLastName() + "!");

		System.out.println("Listing all courses:");
		for (Course course : facade.getCurrentStudent().getCourses()) {
			System.out.println("\t" + course.getTitle());
			facade.setCurrentCourse(course);
		}

		Course course = facade.getCurrentCourse();
		if (course == null) {
			System.out.println("No course was found to enter.");
		}
		else {
			System.out.println("Listing lessons in course " + course.getTitle() + ":");
			for (Lesson lesson : course.getLessons()) {
				System.out.println("\t" + lesson.getTitle());
				OperationResult<Integer> progressResult = facade.getProgress(lesson);
				if (progressResult.success && progressResult.result != 0)
					facade.setCurrentLesson(lesson);
			}

			Lesson lesson = facade.getCurrentLesson();
			if (lesson == null) {
				System.out.println("No lesson was found to begin progressing.");
			}
			else {
				System.out.println("Opened lesson " + lesson.getTitle() + ", playing on " + lesson.getSheet().getInstrument().getType().getName());
				System.out.println("Current student progress: " + facade.getProgress(lesson).result);
				System.out.println("Progressing the lesson...");
				facade.playSheet(lesson.getSheet());
				student.progressLesson(lesson);
				System.out.println("Progressed lesson! New lesson progress: " + facade.getProgress(lesson).result);
				System.out.println("Progressing again...");
				facade.playSheet(lesson.getSheet());
				student.progressLesson(lesson);
				System.out.println("New lesson progress: " + facade.getProgress(lesson).result);
			}

			for (Lesson newLesson : course.getLessons()) {
				if (newLesson.getTitle().equals(lesson.getTitle())) {
					continue;
				}
				OperationResult<Integer> progressResult = facade.getProgress(newLesson);
				if (progressResult.success) {
					facade.setCurrentLesson(newLesson);
				}
			}
			lesson = facade.getCurrentLesson();
			System.out.println("Changed lesson to " + lesson.getTitle());
			System.out.println("Current lesson progress: " + facade.getProgress(lesson).result);
			System.out.println("Progressing lesson...");
			facade.playSheet(lesson.getSheet());
			student.progressLesson(lesson);
			System.out.println("New lesson progress: " + facade.getProgress(lesson).result);

			String feedbackPath = "feedback.txt";
			System.out.println("Printing lesson feedback to " + feedbackPath);
			student.printLessonFeedback(feedbackPath);
		}

		boolean changed = facade.logout();
		if (changed)
			System.out.println("You have successfully logged out.");
	}
}
