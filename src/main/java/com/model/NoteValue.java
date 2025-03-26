package com.model;

/**
 * Represents a note's value
 * @author Michael Davis
 */
public enum NoteValue {
	SIXTEENTH('S'),
	EIGHTH('I'),
	QUARTER('Q'),
	HALF('H'),
	WHOLE('W');

	/**
	 * The jfugue character representing the value
	 */
	public final char character;

	/**
	 * Creates a NoteValue instance
	 * @param character the jfugue character representation
	 */
	private NoteValue(char character) {
		this.character = character;
	}
}
