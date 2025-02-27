package com.model;

import java.util.UUID;

/**
 * A lesson for playing a sheet
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

	public Lesson(String title) {
		this.id = UUID.randomUUID();
		this.title = title;
	}

	public boolean hasId(UUID otherId) {
		return this.id.equals(otherId);
	}

	public String getId() {
		return id.toString();
	}

	public String getTitle() {
		return title;
	}
}
