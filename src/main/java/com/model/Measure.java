package com.model;

import java.util.ArrayList;

import org.jfugue.player.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.model.managers.SongManager;

/**
 * musical measure containing chords
 * @author Ryan Smith
 */
public class Measure {
    private ArrayList<Chord> chords;
    private int tempo;
    private int timeSignatureNum;
    private int timeSignatureDenom;
    private boolean repeatOpen;
    private boolean repeatClose;
    private String jfugueString;
    private boolean metronomeEnabled = false;
    private boolean audioPlaybackEnabled = false;

    /**
     * new measure with musical properties
     *
     * @param chords             list of chords in the measure
     * @param tempo              tempo of the measure
     * @param timeSignatureNum   numerator of time signature
     * @param timeSignatureDenom denominator of time signature
     * @param repeatOpen         whether measure opens a repeat section
     * @param repeatClose        whether measure closes a repeat section
     * @param jfugueString       a String to play the measure
     */
    public Measure(ArrayList<Chord> chords, int tempo, int timeSignatureNum, 
                  int timeSignatureDenom, boolean repeatOpen, boolean repeatClose, String jfugueString) {
        this.chords = chords;
        this.tempo = tempo;
        this.timeSignatureNum = timeSignatureNum;
        this.timeSignatureDenom = timeSignatureDenom;
        this.repeatOpen = repeatOpen;
        this.repeatClose = repeatClose;
        this.jfugueString = jfugueString;
    }

    /**
     * Toggles the metronome on/off
     */
    public void toggleMetronome() {
        this.metronomeEnabled = !this.metronomeEnabled;
		SongManager.getInstance().save();
    }

    /**
     * Sets the metronome speed
     * @param speed the new speed to set
     */
    public void setMetronomeSpeed(double speed) {
        if (speed > 0) {
            this.tempo = (int) speed;
			SongManager.getInstance().save();
        }
    }

    /**
     * Toggles audio playback on/off
     */
    public void toggleAudioPlayback() {
        this.audioPlaybackEnabled = !this.audioPlaybackEnabled;
		SongManager.getInstance().save();
    }

    /**
     * Adds a chord to the measure
     * @param chord the chord to add
     */
    public void addChord(Chord chord) {
        if (chord != null && !chords.contains(chord)) {
            chords.add(chord);
			SongManager.getInstance().save();
        }
    }

    /**
     * Removes a chord from the measure
     * @param chord the chord to remove
     */
    public void removeChord(Chord chord) {
        if (chord != null) {
            chords.remove(chord);
			SongManager.getInstance().save();
        }
    }

	/**
	 * Transforms this instance into a JSON object
	 * @return a JSON object
	 */
	@SuppressWarnings({ "unchecked", "exports" })
	public JSONObject toJSON() {
		JSONObject measureJSON = new JSONObject();

		JSONArray chordsJSON = new JSONArray();
		for (Chord chord : chords) {
			chordsJSON.add(chord.toJSON());
		}
		measureJSON.put("chords", chordsJSON);
		measureJSON.put("tempo", tempo);
		measureJSON.put("timeSignatureNum", timeSignatureNum);
		measureJSON.put("timeSignatureDenom", timeSignatureDenom);
		measureJSON.put("repeatOpen", repeatOpen);
		measureJSON.put("repeatClose", repeatClose);

		return measureJSON;
	}

    @Override
    public String toString() {
        return "Measure {" +
           "\n\tchords=" + chords +
           ",\n\ttempo=" + tempo +
           ",\n\ttimeSignatureNum=" + timeSignatureNum +
           ",\n\ttimeSignatureDenom=" + timeSignatureDenom +
           ",\n\trepeatOpen=" + repeatOpen +
           ",\n\trepeatClose=" + repeatClose +
           "\n}";
    }

    /**
     * plays the measure using JFugue
     */
    public void play() {
        Player player = new Player();
        player.play(jfugueString);
    }

    public String getJfugueString() {
        return jfugueString;
    }
}
