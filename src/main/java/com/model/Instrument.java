package com.model;

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
} 