package com.model;

import java.util.UUID;

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

	// private SheetMusic sheet;

	/**
	 * Constructs a new Lesson instance that already has an identifier
	 * @param id - the lesson's unique identifier
	 * @param title - the lesson's title
	 */
	public Lesson(UUID id, String title) {
		this.id = id;
		this.title = title;
	}

	/**
	 * Constructs a new Lesson instance and automatically generates an identifier
	 * @param title - the lesson's title
	 */
	public Lesson(String title) {
		this(UUID.randomUUID(), title);
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
	 * Gets the lesson's identifier as a string
	 * @return a stringified UUID
	 */
	public String getId() {
		return id.toString();
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
}
