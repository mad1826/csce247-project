package com.model;

import java.util.ArrayList;

/**
 * sheet with measures and playback settings
 * @author Ryan Smith
 */
public class SheetMusic {
    private Song song;
    private Instrument instrument;
    private Difficulty difficulty;
    private Clef clef;
    private boolean audioPlaybackEnabled;
    private ArrayList<Measure> measures;
    private boolean isPrivate;

    /**
     * Creates a new sheet music instance
     *
     * @param song               the associated song
     * @param instrument         the instrument for this sheet
     * @param difficulty         the difficulty level
     * @param clef              the musical clef
     * @param audioPlaybackEnabled whether audio playback is enabled
     * @param measures         the list of measures
     * @param isPrivate        whether the sheet is private
     */
    
    public SheetMusic(Song song, Instrument instrument, Difficulty difficulty, 
                     Clef clef, boolean audioPlaybackEnabled, ArrayList<Measure> measures, 
                     boolean isPrivate) {
        this.song = song;
        this.instrument = instrument;
        this.difficulty = difficulty;
        this.clef = clef;
        this.audioPlaybackEnabled = audioPlaybackEnabled;
        this.measures = measures;
        this.isPrivate = isPrivate;
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
} 