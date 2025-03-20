package com.model;

import java.util.ArrayList;

import org.jfugue.player.Player;
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
     * @param note the note to add
     */
    public void addNote(Note note) {
        if (note != null && !notes.contains(note)) {
            notes.add(note);
        }
    }

    /**
     * Removes a note from the chord
     * @param note the note to remove
     */
    public void removeNote(Note note) {
        if (note != null) {
            notes.remove(note);
        }
    }

	/**
	 * Transforms this instance into a JSON object
	 * @return a JSON object
	 */
	@SuppressWarnings({ "exports", "unchecked" })
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

    @Override
    public String toString() {
        return this.name+" - "+this.notes;
    }

    /**
     * plays the chord using JFugue
     */
    public void play() {
        Player player = new Player();
        StringBuilder chordString = new StringBuilder();

        for (Note note : notes) {
            chordString.append(note.getJfugueString()).append(" ");
        }

        player.play(chordString.toString().trim());
    }
} 