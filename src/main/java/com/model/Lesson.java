package com.model;

import java.util.HashMap;
import java.util.UUID;

import org.json.simple.JSONObject;

import com.model.managers.CourseManager;

/**
 * A lesson for playing a sheet
 * 
 * A lesson will prompt the student to play the song a set amount of times.  The lesson is complete when they play the song this amount of times.
 * 
 * @author Michael Davis
 * @author Matt Carey
 */
public class Lesson {
	/**
	 * The lesson's unique identifier
	 */
	private UUID id;

	/**
	 * The lesson's title
	 */
	private String title;

	/**
	 * The song this lesson will play a sheet from
	 */
	private Song song;
	private UUID unlinkedSong;
	private int numberOfTimes; //How many times the song needs to be played for the lesson to be complete

	/**
	 * The instrument this lesson will play a sheet from
	 */
	private InstrumentType instrumentType;

	/**
	 * Constructs a new Lesson instance that already has an identifier
	 * @param id - the lesson's unique identifier
	 * @param title - the lesson's title
	 * @param song - the song this lesson will play a sheet from
	 * @param instrumentType - the instrument this lesson will play a sheet from
	 * @param numberOfTimes - how many times the song should be played for the lesson to be complete.
	 */
	public Lesson(UUID id, String title, Song song, InstrumentType instrumentType, int numberOfTimes) {
		this.id = id;
		this.title = title;
		this.song = song;
		this.instrumentType = instrumentType;
		this.numberOfTimes = numberOfTimes;
	}

	public Lesson(UUID id, String title, UUID song, InstrumentType instrumentType, int numberOfTimes) {
        this.id = id;
        this.title = title;
        this.unlinkedSong = song;
        this.instrumentType = instrumentType;
		this.numberOfTimes = numberOfTimes;
    }

	/**
	 * Constructs a new Lesson instance and automatically generates an identifier
	 * @param title - the lesson's title
	 * @param song - the song this lesson will play a sheet from
	 * @param instrumentType - the instrument this lesson will play a sheet from
	 */
	public Lesson(String title, Song song, InstrumentType instrumentType, int numberOfTimes) {
		this(UUID.randomUUID(), title, song, instrumentType, numberOfTimes);
	}

	/**
	 * Returns whether this lesson has the same id as another id
	 * @param otherId - the other identifier
	 * @return whether the ids are the same
	 */
	public boolean hasId(UUID otherId) {
		return this.id.equals(otherId);
	}

	/**
	 * Gets the lesson's identifier
	 * @return a UUID
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * Updates the song the lesson is based around.
	 * @param s the new song
	 */
	public void setSong(Song s) {
		this.song = s;
		CourseManager.getInstance().save();
	}

	public UUID getUnlinkedSong() {
		return this.unlinkedSong;
	}

	/**
	 * Gets the lesson's title
	 * @return the lesson's title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Updates the lesson's title
	 * @param title - the new lesson title
	 * @return whether the title was changed
	 */
	public boolean setTitle(String title) {
		if (this.title.equals(title)) return false;
		this.title = title;
		return true;
	}

	/**
	 * Gets the sheet this lesson focuses on
	 * @return a piece of sheet music
	 */
	public SheetMusic getSheet() {
		HashMap<Instrument, SheetMusic> sheets = song.getSheets();
		for (Instrument instrument : sheets.keySet()) {
			if (instrument.getType().getName().equals(instrumentType.getName()))
				return sheets.get(instrument);
		}

		return null;
	}

	@SuppressWarnings({ "unchecked", "exports" })
	public JSONObject toJSON() {
		JSONObject lessonObject = new JSONObject();

		lessonObject.put("id", id.toString());
		lessonObject.put("title", title);
		lessonObject.put("song", song.getId().toString());
		lessonObject.put("instrument", instrumentType.getName());
		lessonObject.put("numberOfTimes",numberOfTimes);
		
		return lessonObject;
	}

	/**
	 * Initially links the song to the lesson.
	 * @param song the song to link
	 */
	public void linkSong(Song song) {
		this.song = song;
	}

    public int getNumberOfTimes() {
        return numberOfTimes;
    }

    public void setNumberOfTimes(int numberOfTimes) {
        this.numberOfTimes = numberOfTimes;
    }
}