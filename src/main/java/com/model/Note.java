package com.model;

import org.jfugue.player.Player;
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
    private String jfugueString;

    /**
     * Creates a new musical note with all properties
     *
     * @param pitch          the note's pitch (A, B, C, etc.)
     * @param pitchModifier  the note's modifier (sharp, flat, etc.)
     * @param value         the note's duration value
     * @param dot           whether the note is dotted
     * @param line          whether the note has a line
     * @param octave        the note's octave number
     * @param jfugueString  a String to play the chord(s)
     */
    public Note(Pitch pitch, PitchModifier pitchModifier, NoteValue value, boolean dot, boolean line, int octave, String jfugueString) {
        this.pitch = pitch;
        this.pitchModifier = pitchModifier;
        this.value = value;
        this.dot = dot;
        this.line = line;
        this.octave = octave > 0 ? octave : 1;
        this.jfugueString = jfugueString;
    }

    /**
     * Sets the pitch of the note
     *
     * @param pitch the new pitch to set
     */
    public void setPitch(Pitch pitch) {
        this.pitch = pitch;
    }

	/**
	 * Sets the pitch modifier of the note
	 * @param pitchModifier - the new pitch modifier
	 */
	public void setPitchModifier(PitchModifier pitchModifier) {
		this.pitchModifier = pitchModifier;
	}

    /**
     * Sets the duration value of the note
     *
     * @param value the new value to set
     */
    public void setValue(NoteValue value) {
		this.value = value;
    }

    /**
     * Toggles the dot property of the note
     */
    public void toggleDot() {
		dot = !dot;
    }

	/**
	 * Toggles whether the note should have a line
	 */
	public void toggleLine() {
		line = !line;
	}

	/**
	 * Sets the octave of the note
	 * @param octave - the new octave
	 */
	public void setOctave(int octave) {
		if (octave > 0)
			this.octave = octave;
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

  @Override
  public String toString() {
    return this.pitch.name()+this.pitchModifier.abbreviatedName;
  }

  /*
   * plays the note using jfugue
   */
  public void play() {
    Player player = new Player();
    player.play(jfugueString);
  }

public String getJfugueString() {
    return jfugueString;
  }

  
} 