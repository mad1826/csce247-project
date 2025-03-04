package com.model;

import java.util.ArrayList;

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

    /**
     * new measure with musical properties
     *
     * @param chords             list of chords in the measure
     * @param tempo              tempo of the measure
     * @param timeSignatureNum   numerator of time signature
     * @param timeSignatureDenom denominator of time signature
     * @param repeatOpen         whether measure opens a repeat section
     * @param repeatClose        whether measure closes a repeat section
     */
    public Measure(ArrayList<Chord> chords, int tempo, int timeSignatureNum, 
                  int timeSignatureDenom, boolean repeatOpen, boolean repeatClose) {
        this.chords = chords;
        this.tempo = tempo;
        this.timeSignatureNum = timeSignatureNum;
        this.timeSignatureDenom = timeSignatureDenom;
        this.repeatOpen = repeatOpen;
        this.repeatClose = repeatClose;
    }

    /**
     * Toggles the metronome
     */
    public void toggleMetronome() {
        
    }

    /**
     * Sets the metronome speed
     * @param speed the new speed to set
     */
    public void setMetronomeSpeed(double speed) {
        
    }

    /**
     * Toggles audio playback 
     */
    public void toggleAudioPlayback() {
        
    }

    /**
     * Adds a chord to the measure
     * @param chord the chord to add
     */
    public void addChord(Chord chord) {
        
    }

    /**
     * Removes a chord from the measure
     * @param chord the chord to remove
     */
    public void removeChord(Chord chord) {
        
    }
} 