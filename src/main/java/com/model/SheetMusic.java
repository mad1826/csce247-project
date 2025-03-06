package com.model;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * sheet with measures and playback settings
 * @author Ryan Smith
 */
public class SheetMusic {
    private Instrument instrument;
    private Difficulty difficulty;
    private Clef clef;
    private boolean audioPlaybackEnabled;
    private ArrayList<Measure> measures;
    private boolean isPrivate;

    /**
     * Creates a new sheet music instance
     *
     * @param instrument         the instrument for this sheet
     * @param difficulty         the difficulty level
     * @param clef              the musical clef
     * @param audioPlaybackEnabled whether audio playback is enabled
     * @param measures         the list of measures
     * @param isPrivate        whether the sheet is private
     */
    
    public SheetMusic(Instrument instrument, Difficulty difficulty, 
                     Clef clef, boolean audioPlaybackEnabled, ArrayList<Measure> measures, 
                     boolean isPrivate) {
        this.instrument = instrument;
        this.difficulty = difficulty;
        this.clef = clef;
        this.audioPlaybackEnabled = audioPlaybackEnabled;
        this.measures = measures;
        this.isPrivate = isPrivate;
    }

	/**
	 * Gets the instrument playable with this sheet
	 * @return the Instrument instance
	 */
	public Instrument getInstrument() {
		return instrument;
	}

    /**
     * Toggles audio playback on/off
     */
    public void toggleAudioPlayback() {
        
    }

    /**
     * Adds a measure to the sheet
     * @param measure the measure to add
     */
    public void addMeasure(Measure measure) {
        measures.add(measure);
    }

    /**
     * Removes a measure from the sheet
     * @param measure the measure to remove
     */
    public void removeMeasure(Measure measure) {
        
    }

    /**
     * Toggles the private status
     */
    public void togglePrivate() {
        
    }

	/**
	 * Transforms this instance into a JSON object
	 * @return a JSON object
	 */
	@SuppressWarnings({ "unchecked", "exports" })
	public JSONObject toJSON() {
		JSONObject sheetObject = new JSONObject();

		sheetObject.put("difficulty", difficulty.toString());
		sheetObject.put("clef", clef.toString());
		sheetObject.put("audioPlaybackEnabled", audioPlaybackEnabled);
		JSONArray measureArray = new JSONArray();
		for (Measure measure : measures) {
			measureArray.add(measure.toJSON());
		}
		sheetObject.put("measures", measureArray);
		sheetObject.put("private", isPrivate);

		return sheetObject;
	}
} 