package com.model;

import java.util.ArrayList;

import org.jfugue.player.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.model.managers.SongManager;

/**
 * chord composed of multiple notes
 * @author Ryan Smith
 */
public class Chord {
	/**
	 * The chord's name
	 */
    private String name;
	/**
	 * The notes in the chord
	 */
    private ArrayList<Note> notes;
	/**
	 * Whether the chord's notes are tied
	 */
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
     * converts the chord to a JFugue string representation
     * @return - JFugue string for the entire chord
     */
    public String toJfugue() {
        StringBuilder chordString = new StringBuilder();

        // if there's only one note, return its JFugue representation
        if (notes.size() == 1) {
            return notes.get(0).toJfugue();
        }

        // for multiple notes, create a chord representation
        chordString.append("(");
        for (Note note : notes) {
            chordString.append(note.toJfugue()).append(" ");
        }
        chordString.append(")");

        return chordString.toString().trim();
    }

    /**
     * Adds a note to the chord
     * @param note the note to add
     */
    public void addNote(Note note) {
        if (note != null && !notes.contains(note)) {
            notes.add(note);
			SongManager.getInstance().save();
        }
    }

    /**
     * Removes a note from the chord
     * @param note the note to remove
     */
    public void removeNote(Note note) {
        if (note != null) {
            notes.remove(note);
			SongManager.getInstance().save();
        }
    }

     /**
     * Plays the chord using JFugue
     */
    public void play() {
        Player player = new Player();
        player.play(toJfugue());
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

	/**
	 * Converts the note to a readable format
	 */
    @Override
    public String toString() {
        return this.name+" - "+this.notes;
    }

} 