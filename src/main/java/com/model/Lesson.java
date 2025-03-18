package com.model;

import java.util.HashMap;
import java.util.UUID;

import org.json.simple.JSONObject;

/**
 * A lesson for playing a sheet
 * 
 * @author Michael Davis
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
	 */
	public Lesson(UUID id, String title, Song song, InstrumentType instrumentType) {
		this.id = id;
		this.title = title;
		this.song = song;
		this.instrumentType = instrumentType;
	}

	/**
	 * Constructs a new Lesson instance and automatically generates an identifier
	 * @param title - the lesson's title
	 * @param song - the song this lesson will play a sheet from
	 * @param instrumentType - the instrument this lesson will play a sheet from
	 */
	public Lesson(String title, Song song, InstrumentType instrumentType) {
		this(UUID.randomUUID(), title, song, instrumentType);
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

		return lessonObject;
	}
}