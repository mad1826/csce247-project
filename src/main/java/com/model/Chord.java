package com.model;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * chord composed of multiple notes
 * @author Ryan Smith
 */
public class Chord {
    private String name;
    private ArrayList<Note> notes;
    private boolean tie;

    /**
     * Creates a new chord with properties
     *
     * @param name  the name of the chord
     * @param notes the list of notes in the chord
     * @param tie   whether the chord is tied
     */
    public Chord(String name, ArrayList<Note> notes, boolean tie) {
        this.name = name;
        this.notes = notes;
        this.tie = tie;
    }

    /**
     * Adds a note to the chord
     * @param note note to add
     */
    public void addNote(Note note) {
        
    }

    /**
     * Removes a note from the chord
     * @param note the note to remove
     */
    public void removeNote(Note note) {
        
    }

	/**
	 * Transforms this instance into a JSON object
	 * @return a JSON object
	 */
	public JSONObject toJSON() {
		JSONObject chordJSON = new JSONObject();

		chordJSON.put("name", name);
		JSONArray notesJSON = new JSONArray();
		for (Note note : notes) {
			notesJSON.add(note.toJSON());
		}
		chordJSON.put("notes", notesJSON);
		chordJSON.put("tie", tie);

		return chordJSON;
	}
} 