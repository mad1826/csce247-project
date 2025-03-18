package com.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

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
     * Constructs a new MusicAppFacade instance
     */
    private MusicAppFacade() {
        UserManager.getInstance().loadData();
		// CourseManager.getInstance().loadData();
		// SongManager.getInstance().loadData();
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
	 * Gets the currently selected song
	 * @return The currently selected song
	 */
	public Song getCurrentSong() {
		return currentSong;
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
        return null;
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
     * reset password
     * @param newPassword new password
     * @return OperationResult<User>
     */
    public OperationResult<User> resetPassword(String newPassword) {
        return null;
    }

    /**
     * create course
     * @param title title
     * @param id id
     * @return OperationResult<Course>
     */
    public OperationResult<Course> createCourse(String title, UUID id) {
        return null;
    }

    /**
     * delete course
     * @param id id
     * @return OperationResult<Void>
     */
    public OperationResult<Void> deleteCourse(UUID id) {
        return null;
    }

    /**
     * create lesson
     * @param course course
     * @param title title
     * @param sheet sheet
     * @return OperationResult<Void>
     */
    public OperationResult<Void> createLesson(Course course, String title, SheetMusic sheet) {
        return null;
    }

    /**
     * delete lesson
     * @param lesson lesson
     * @return OperationResult<Void>
     */
    public OperationResult<Void> deleteLesson(Lesson lesson) {
        return null;
    }

    /**
     * join course
     * @param id id
     * @return OperationResult<Course>
     */
    public OperationResult<Course> joinCourse(UUID id) {
        return null;
    }

    /**
     * leave course
     * @param id id
     * @return OperationResult<Void>
     */
    public OperationResult<Void> leaveCourse(UUID id) {
        return null;
    }

    /**
     * start lesson
     * @param lesson lesson
     * @return boolean
     */
    public boolean startLesson(Lesson lesson) {
        return true;
    }

    /**
     * get songs
     * @return ArrayList<Song>
     */
    public ArrayList<Song> getSongs() {
        return new ArrayList<>();
    }

    /**
     * find songs
     * @param query query
     * @return ArrayList<Song>
     */
    public ArrayList<Song> findSongs(HashMap<SongFilter, String> query) {
        return new ArrayList<>();
    }

    /**
     * get sheets
     * @param song song
     * @return HashMap<Instrument, SheetMusic>
     */
    public HashMap<Instrument, SheetMusic> getSheets(Song song) {
        return new HashMap<>();
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
    public boolean createSheet(Song song, Instrument instrument, Difficulty difficulty, 
                             Clef clef, boolean audioPlaybackEnabled, 
                             ArrayList<Measure> measures, boolean isPrivate) {
        return true;
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
     * play sheet
     * @param sheet sheet
     */
    public void playSheet(SheetMusic sheet) {
        
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
     * @return ArrayList<User>
     */
    public ArrayList<User> getFriends() {
        return new ArrayList<>();
    }

    /**
     * add friend
     * @param user user
     * @return boolean
     */
    public boolean addFriend(User user) {
        return true;
    }

    /**
     * remove friend
     * @param user user
     * @return boolean
     */
    public boolean removeFriend(User user) {
        return true;
    }

    /**
     * get progress
     * @return HashMap<UUID, Double>
     */
    public HashMap<UUID, Double> getProgress() {
        return new HashMap<>();
    }
} 