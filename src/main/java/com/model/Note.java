package com.model;

import org.json.simple.JSONObject;

/**
 * Represents a musical note with musical properties
 * @author Ryan Smith
 */
public class Note {
    private Pitch pitch;
    private PitchModifier pitchModifier;
    private NoteValue value;
    private boolean dot;
    private boolean line;
    private int octave;

    /**
     * Creates a new musical note with all properties
     *
     * @param pitch          the note's pitch (A, B, C, etc.)
     * @param pitchModifier  the note's modifier (sharp, flat, etc.)
     * @param value         the note's duration value
     * @param dot           whether the note is dotted
     * @param line          whether the note has a line
     * @param octave        the note's octave number
     */
    public Note(Pitch pitch, PitchModifier pitchModifier, NoteValue value, boolean dot, boolean line, int octave) {
        this.pitch = pitch;
        this.pitchModifier = pitchModifier;
        this.value = value;
        this.dot = dot;
        this.line = line;
        this.octave = octave;
    }

    /**
     * Sets the pitch of the note
     *
     * @param pitch the new pitch to set
     */
    public void setPitch(Pitch pitch) {
        
    }

    /**
     * Sets the duration value of the note
     *
     * @param value the new value to set
     */
    public void setValue(NoteValue value) {
        
    }

    /**
     * Toggles the dot property of the note
     */
    public void toggleDot() {
        
    }

    /**
     * Adds a tie to the next note
     *
     * @param nextNote the note to tie to
     */
    public void addTie(Note nextNote) {
        
    }

    /**
     * Removes a tie to the next note
     *
     * @param nextNote the note to remove tie from
     */
    public void removeTie(Note nextNote) {
       
    }

	/**
	 * Transforms this instance into a JSON object
	 * @return a JSON object
	 */
	@SuppressWarnings({ "exports", "unchecked" })
	public JSONObject toJSON() {
		JSONObject noteJSON = new JSONObject();

		noteJSON.put("pitch", pitch.toString());
		noteJSON.put("pitchModifier", pitchModifier.toString());
		noteJSON.put("value", value.toString());
		noteJSON.put("dot", dot);
		noteJSON.put("line", line);
		noteJSON.put("octave", octave);

		return noteJSON;
	}
} 