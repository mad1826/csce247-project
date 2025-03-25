package com.model;

import org.json.simple.JSONObject;

/**
 * musical instrument with playback 
 * @author Ryan Smith
 */
public class Instrument {
    private InstrumentType type;
    private String tuning = "Standard";
    private boolean isMuted = false;

    /**
     * Creates a new instrument 
     *
     * @param type     the instrument type
     */

    public Instrument(InstrumentType t) {
        this.type = t;
    }

	/**
	 * Gets the type of instrument
	 * @return the instrument type
	 */
	public InstrumentType getType() {
		return type;
	}

    /**
     * Plays a musical note
     * @param note note to play
     */
    public void playNote(Note note) {
        if (!isMuted) {
            note.play();
        }
    }

    /**
     * Stops playing a note
     * @param note note to stop
     */
    public void stopNote(Note note) {
        //Doesn't Jfuge automatically stop notes when they finish playing?
    }

    /**
     * Adjusts the instrument's tuning
     * @param newTuning the new tuning to set
     */
    public void adjustTuning(String newTuning) {
        this.tuning = newTuning;
    }

    /**
     * Mutes instrument
     */
    public void mute() {
        this.isMuted = true;
    }

    /**
     * Unmutes instrument
     */
    public void unmute() {
        this.isMuted = false;
    }

    /**
     * Sets the volume
     * @param volume the volume level 
     */
    public void setVolume(int volume) {
        //Does Jfuge set the volume?
    }

	
	/**
	 * Transforms this instance into a JSON object
	 * @param sheetJSON - the associated sheet
	 * @return a JSON object
	 */
	@SuppressWarnings({ "exports", "unchecked" })
	public JSONObject toJSON(JSONObject sheetJSON) {
		JSONObject instrumentJSON = new JSONObject();

		instrumentJSON.put("name", type.toString());
		instrumentJSON.put("tuning", tuning);
		instrumentJSON.put("isMuted", isMuted);
		instrumentJSON.put("sheet", sheetJSON);

		return instrumentJSON;
	}

    @Override
    public String toString() {
        return this.type.getName();
    }
} 