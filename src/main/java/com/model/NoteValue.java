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

	public final char character;

	NoteValue(char character) {
		this.character = character;
	}
}
