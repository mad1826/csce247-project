package com.model;

import java.util.ArrayList;

import org.jfugue.player.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.model.managers.SongManager;

/**
 * sheet with measures and playback settings
 * @author Ryan Smith
 */
public class SheetMusic {
	/**
	 * The instrument associated with this sheet
	 */
    private final Instrument instrument;
	/**
	 * The sheet's difficulty
	 */
    private Difficulty difficulty;
	/**
	 * The clef to play the sheet in
	 */
    private Clef clef;
	/**
	 * Whether the sheet should play back audio
	 */
    private boolean audioPlaybackEnabled;
	/**
	 * The sheet's measures
	 */
    private final ArrayList<Measure> measures;
	/**
	 * Whether the sheet is private
	 */
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
        audioPlaybackEnabled = !audioPlaybackEnabled;
		SongManager.getInstance().save();
    }

    /**
     * Adds a measure to the sheet
     * @param measure the measure to add
     */
    public void addMeasure(Measure measure) {
        measures.add(measure);
		SongManager.getInstance().save();
    }

    /**
     * Removes a measure from the sheet
     * @param measure the measure to remove
     */
    public void removeMeasure(Measure measure) {
        measures.remove(measure);
		SongManager.getInstance().save();
    }

	/**
	 * Gets all the sheet's measures.
	 * @return an array list of measures
	 */
    public ArrayList<Measure> getMeasures() {
        return this.measures;
    }

	/**
	 * Updates the sheet's difficulty.
	 * @param difficulty the sheet's difficulty
	 */
	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
		SongManager.getInstance().save();
	}

	/**
	 * Updates the sheet's clef.
	 * @param clef the new clef
	 */
	public void setClef(Clef clef) {
		this.clef = clef;
		SongManager.getInstance().save();
	}

    /**
     * Toggles the private status
     */
    public void togglePrivate() {
        isPrivate = !isPrivate;
		SongManager.getInstance().save();
    }

	/**
	 * Plays this sheet using jfugue.
	 */
	public void play() {
		new Player().play(toJfugue());
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

    @Override
    public String toString() {
        return "SheetMusic {" +
           "\n\tinstrument=" + instrument +
           ",\n\tdifficulty=" + difficulty +
           ",\n\tclef=" + clef +
           ",\n\taudioPlaybackEnabled=" + audioPlaybackEnabled +
           ",\n\tmeasures=" + measures +
           ",\n\tisPrivate=" + isPrivate +
           "\n}";
    }

	/**
	 * Get a jfugue pattern representation of this sheet.
	 * @return the jfugue pattern as a string
	 */
	public String toJfugue() {
        StringBuilder songPattern = new StringBuilder();

        for ( Measure measure : measures) {
            songPattern.append(measure.toJfugue()).append(" ");
        }

        return songPattern.toString().trim();
	}
} 