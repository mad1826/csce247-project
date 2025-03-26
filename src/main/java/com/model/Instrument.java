package com.model;

import org.json.simple.JSONObject;

/**
 * musical instrument with playback 
 * @author Ryan Smith
 */
public class Instrument {
	/**
	 * The instrument's type
	 */
    private InstrumentType type;
	/**
	 * The instrument's tuning
	 */
    private String tuning = "Standard";
	/**
	 * Whether the instrument is muted
	 */
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

	/**
	 * Gets a string representation of the instrument
	 */
    @Override
    public String toString() {
        return this.type.getName();
    }
} 