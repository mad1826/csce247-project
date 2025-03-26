package com.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import com.model.DataHandlers.DataLoader;
import com.model.managers.SongManager;
import com.model.managers.UserManager;

/**
 * facade for the music application
 * central access to app functionality
 * @author Ryan Smith
 */
public class MusicAppFacade {
	/**
	 * The singleton facade instance
	 */
	private static MusicAppFacade instance;
	/**
	 * The currently authenticated user
	 */
    private User currentUser;
	/**
	 * The currently selected song
	 */
    private Song currentSong;
	/**
	 * The currently selected music sheet
	 */
    private SheetMusic currentSheet;
	/**
	 * The current query for filtering songs
	 */
	private final HashMap<SongFilter, String> songQuery = new HashMap<>();
	/**
	 * The currently selected course
	 */
	private Course currentCourse;
	/**
	 * The currently selected lesson
	 */
	private Lesson currentLesson;

    /**
     * Constructs a new MusicAppFacade instance
     */
    private MusicAppFacade() {
        DataLoader.loadAllData();
    }

	/**
	 * Gets the facade singleton instance.
	 * @return the sole facade instance
	 */
	public static MusicAppFacade getInstance() {
		if (instance == null)
			instance = new MusicAppFacade();

		return instance;
	}

	/**
	 * Gets the current authenticated user
	 * @return The user that has logged in
	 */
	public User getCurrentUser() {
		return currentUser;
	}

	/**
	 * Gets the current authenticated teacher.
	 * @return the teacher that has logged in
	 */
	public Teacher getCurrentTeacher() {
        if (currentUser == null || !(currentUser instanceof Teacher)) {
			return null;
		}
		return (Teacher)currentUser;
	}

	/**
	 * Gets the current authenticated student.
	 * @return the student that has logged in
	 */
	public Student getCurrentStudent() {
        if (currentUser == null || !(currentUser instanceof Student)) {
			return null;
		}
		return (Student)currentUser;
	}

	/**
	 * Gets the currently selected song
	 * @return The currently selected song
	 */
	public Song getCurrentSong() {
		return currentSong;
	}
	
	/**
	 * Gets the currently selected course.
	 * @return the currently selected course
	 */
	public Course getCurrentCourse() {
		return currentCourse;
	}

	/**
	 * Updates the selected course.
	 * @param course the course to select
	 */
	public void setCurrentCourse(Course course) {
		currentCourse = course;
	}

	/**
	 * Gets the currently selected lesson.
	 * @return the currently selected lesson
	 */
	public Lesson getCurrentLesson() {
		return currentLesson;
	}

	/**
	 * Updates the selected lesson.
	 * @param lesson the lesson to select
	 */
	public void setCurrentLesson(Lesson lesson) {
		currentLesson = lesson;
	} 

    /**
     * sign up
     * @param firstName first name
     * @param lastName last name
     * @param email email
     * @param password password
     * @return the result of creating a new user
     */
    public OperationResult<User> signUp(String firstName, String lastName, String email, String password) {
        OperationResult<User> or = UserManager.getInstance().createUser(firstName, lastName, email, password,false);
		if (or.success)
			this.currentUser = or.result;

		return or;
    }

    /**
     * login
     * @param email email
     * @param password password
     * @return The newly authenticated user
     */
    public User login(String email, String password) {
        return this.currentUser = UserManager.getInstance().getUser(email, password);
    }

    /**
     * Log the currently authenticated user out
     * @return Whether the user's authentication state changed
     */
    public boolean logout() {
		if (this.currentUser == null)
			return false;

		this.currentUser = null;
		return true;
    }

    /**
     * Reset the authenticated user's password
     * @param newPassword the new password
     * @return the result of attempting to reset the password
     */
    public OperationResult<Void> resetPassword(String newPassword) {
		if (currentUser == null) 
			return new OperationResult<>("Must be logged in to get friends.");

		return new OperationResult<>(currentUser.resetPassword(newPassword));
    }

    /**
     * create course
     * @param title title
     * @param id id
     * @return OperationResult<Course>
     */
    public OperationResult<Course> createCourse(String code, String title) {
        Teacher teacher = getCurrentTeacher();
		if (teacher == null)
			return new OperationResult<>("Must be logged in as a teacher to create a course.");
		return teacher.createCourse(code, title);
    }

    /**
     * delete course
     * @param id id
     * @return OperationResult<Void>
     */
    public OperationResult<Void> deleteCourse(UUID id) {
        Teacher teacher = getCurrentTeacher();
		if (teacher == null)
			return new OperationResult<>("Must be logged in as a teacher to delete a course.");
		
		boolean success = teacher.deleteCourse(id);
		if (success)
			return new OperationResult<>(true);
		else 
			return new OperationResult<>("Unable to delete the course.");
    }

    /**
     * create lesson
     * @param course - the course to create the lesson for
     * @param title - the lesson's title
     * @param song - the song the lesson focuses on
	 * @param instrumentType - the instrument the lesson is played with
     * @return The result of creating the lesson
     */
    public OperationResult<Lesson> createLesson(Course course, String title, Song song, InstrumentType instrumentType, int numberOfTimes) {
        Teacher teacher = getCurrentTeacher();
		if (teacher == null)
			return new OperationResult<>("Must be logged in as a teacher to create a lesson.");

		return course.createLesson(title, currentSong, instrumentType, numberOfTimes);
    }

    /**
     * delete lesson
     * @param course - the course to delete the lesson from
	 * @param lessonId - the id of the lesson to delete
     * @return The result of attempting to delete the lesson
     */
    public OperationResult<Void> deleteLesson(Course course, UUID lessonId) {
        Teacher teacher = getCurrentTeacher();
		if (teacher == null)
			return new OperationResult<>("Must be logged in as a teacher to delete a lesson.");

		return new OperationResult<>(course.deleteLesson(lessonId));
    }

    /**
     * join course
     * @param code - the course code
     * @return The result of attemping to join a course
     */
    public OperationResult<Course> joinCourse(String code) {
        Student student = getCurrentStudent();
		if (student == null)
			return new OperationResult<>("Must be logged in as a student to join a course.");

		return student.joinCourse(code);
    }

    /**
     * leave course
     * @param code - the course 
     * @return OperationResult<Void>
     */
    public OperationResult<Void> leaveCourse(String code) {
        Student student = getCurrentStudent();
		if (student == null)
			return new OperationResult<>("Must be logged in as a student to leave a course.");

		return student.leaveCourse(code);
    }

    /**
     * get songs
     * @return All songs
     */
    public HashMap<UUID, Song> getSongs() {
        return SongManager.getInstance().getSongs();
    }

	/**
	 * Set the artist of songs to search for
	 * @param artist - the name of the artist
	 */
	public void setArtistQuery(String artist) {
		songQuery.put(SongFilter.ARTIST, artist);
	}

	/**
	 * Set the title of songs to search for
	 * @param title - the song's title
	 */
	public void setTitleQuery(String title) {
		songQuery.put(SongFilter.TITLE, title);
	}

	/**
	 * Set the genre of songs to search for
	 * @param genre - the song's genre
	 */
	public void setGenreQuery(String genre) {
		songQuery.put(SongFilter.GENRE, genre);
	}

	/**
	 * Clears the filters for which songs to search for
	 */
	public void clearQuery() {
		songQuery.clear();
	}

    /**
     * Search songs for those that match the query filters
     * @return All songs that match the query
     */
    public HashMap<UUID, Song> searhSongs() {
        return SongManager.getInstance().findSongs(songQuery);
    }

    public OperationResult<Song> getSong(UUID id) {
        return SongManager.getInstance().getSong(id);
    }

    /**
     * get sheets
     * @param song song
     * @return HashMap<Instrument, SheetMusic>
     */
    public HashMap<Instrument, SheetMusic> getSheets(Song song) {
        return song.getSheets();
    }

    /**
     * create sheet
     * @param song song
     * @param instrument instrument
     * @param difficulty difficulty
     * @param clef clef
     * @param audioPlaybackEnabled audio playback enabled
     * @param measures measures
     * @param isPrivate is private
     * @return boolean
     */
    public OperationResult<SheetMusic> createSheet(Song song, Instrument instrument, Difficulty difficulty, 
                             Clef clef, boolean audioPlaybackEnabled, 
                             ArrayList<Measure> measures, boolean isPrivate) {
        return song.createSheet(instrument, difficulty, clef, audioPlaybackEnabled, measures, isPrivate);
    }

    /**
     * edit sheet
     * @param sheet sheet
     * @return boolean
     */
    public boolean editSheet(SheetMusic sheet) {
        return true;
    }

    /**
     * Plays a sheet using jfugue.
     * @param sheet the sheet to play
     */
    public void playSheet(SheetMusic sheet) {
		sheet.play();
    }

	/**
	 * Plays a sheet of a song by the instrument to play.
	 * @param song the song to play
	 * @param instrumentName the instrument to play the song with
	 * @return the sheet found, or null if not found
	 */
	public SheetMusic playSheet(Song song, String instrumentName) {
		SheetMusic sheet = song.getSheet(instrumentName);
		if (sheet != null)
			playSheet(sheet);
		
		return sheet;
	}

    /**
     * get current sheet
     * @return SheetMusic
     */
    public SheetMusic getCurrentSheet() {
        return currentSheet;
    }

    /**
     * get friends
     * @return The result of getting the authenticated user's friends
     */
    public OperationResult<HashMap<UUID, User>> getFriends() {
		if (currentUser == null) 
			return new OperationResult<>("Must be logged in to get friends.");
        return new OperationResult<>(currentUser.getFriends());
    }

    /**
     * add friend
     * @param user user
     * @return The result of attempting to add the friend
     */
    public OperationResult<Void> addFriend(User user) {
		if (currentUser == null) 
			return new OperationResult<>("Must be logged in to add a friend.");
		return currentUser.addFriend(user);
    }

    /**
     * remove friend
     * @param user user
     * @return The result of attemping to remove the friend
     */
    public OperationResult<Void> removeFriend(User user) {
		if (currentUser == null) 
			return new OperationResult<>("Must be logged in to add a friend.");
		return currentUser.removeFriend(user);
    }

    /**
     * Get progress for the authenticated student's lesson
	 * @param lesson the lesson to get the progress of
     * @return times the student has progressed the lesson
     */
    public OperationResult<Integer> getProgress(Lesson lesson) {
        Student student = getCurrentStudent();
		if (student == null)
			return new OperationResult<>("Must be logged in as a student to join a course.");

		return new OperationResult<>(student.getLessonProgress(lesson));
    }
} 