package com.model;

import org.json.simple.JSONObject;

import com.model.managers.SongManager;

/**
 * Represents a musical note with musical properties
 * @author Ryan Smith
 */
public class Note {
	/**
	 * The note's pitch
	 */
    private Pitch pitch;
	/**
	 * The note's pitch modifier
	 */
    private PitchModifier pitchModifier;
	/**
	 * The note's value
	 */
    private NoteValue value;
	/**
	 * Whether the note has a dot
	 */
    private boolean dot;
	/**
	 * Whether the note has a line
	 */
    private boolean line;
	/**
	 * The note's octave
	 */
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
    public Note(Pitch pitch, PitchModifier pitchModifier, NoteValue value, boolean dot, boolean line, int octave, String jfugueString) {
        this.pitch = pitch;
        this.pitchModifier = pitchModifier;
        this.value = value;
        this.dot = dot;
        this.line = line;
        this.octave = octave > 0 ? octave : 1;
    }

    /**
     * Sets the pitch of the note
     *
     * @param pitch the new pitch to set
     */
    public void setPitch(Pitch pitch) {
        this.pitch = pitch;
		SongManager.getInstance().save();
    }

	/**
	 * Sets the pitch modifier of the note
	 * @param pitchModifier - the new pitch modifier
	 */
	public void setPitchModifier(PitchModifier pitchModifier) {
		this.pitchModifier = pitchModifier;
		SongManager.getInstance().save();
	}

    /**
     * Sets the duration value of the note
     *
     * @param value the new value to set
     */
    public void setValue(NoteValue value) {
		this.value = value;
		SongManager.getInstance().save();
    }

    /**
     * Toggles the dot property of the note
     */
    public void toggleDot() {
		dot = !dot;
		SongManager.getInstance().save();
    }

	/**
	 * Toggles whether the note should have a line
	 */
	public void toggleLine() {
		line = !line;
		SongManager.getInstance().save();
	}

	/**
	 * Sets the octave of the note
	 * @param octave - the new octave
	 */
	public void setOctave(int octave) {
		if (octave > 0) {
			this.octave = octave;
			SongManager.getInstance().save();
		}
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

	/**
	 * Gets a string representation of the note.
	 */
  @Override
  public String toString() {
    return this.pitch.name()+this.pitchModifier.abbreviatedName;
  }

  /**
   * converts note into a JFugue string format 
   * @return - the JFugue representation of the note 
   */
  public String toJfugue() {
    return pitch.name() + octave + value.character;
  }

} 