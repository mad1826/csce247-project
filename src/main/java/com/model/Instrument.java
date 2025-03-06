package com.model;

import org.json.simple.JSONObject;

/**
 * musical instrument with playback 
 * @author Ryan Smith
 */
public class Instrument {
    private InstrumentType type;
    private int rangeMin;
    private int rangeMax;
    private String tuning = "Standard";
    private boolean isMuted = false;

    /**
     * Creates a new instrument 
     *
     * @param type     the instrument type
     * @param rangeMin the min range value
     * @param rangeMax the max range value
     */
    public Instrument(InstrumentType type, int rangeMin, int rangeMax) {
        this.type = type;
        this.rangeMin = rangeMin;
        this.rangeMax = rangeMax;
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
        
    }

    /**
     * Stops playing a note
     * @param note note to stop
     */
    public void stopNote(Note note) {
        
    }

    /**
     * Adjusts the instrument's tuning
     * @param newTuning the new tuning to set
     */
    public void adjustTuning(String newTuning) {
        
    }

    /**
     * Mutes instrument
     */
    public void mute() {
        
    }

    /**
     * Unmutes instrument
     */
    public void unmute() {
        
    }

    /**
     * Sets the volume
     * @param volume the volume level 
     */
    public void setVolume(int volume) {
        
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
		instrumentJSON.put("rangeMin", rangeMin);
		instrumentJSON.put("rangeMax", rangeMax);
		instrumentJSON.put("tuning", tuning);
		instrumentJSON.put("isMuted", isMuted);
		instrumentJSON.put("sheet", sheetJSON);

		return instrumentJSON;
	}
} 