package com.model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;


public class SheetMusicTest {
    private SheetMusic sheetMusic;
    private Instrument instrument;
    private ArrayList<Measure> measures;
    private Measure testMeasure;

    @Before
    public void setUp() {
        instrument = new Instrument(InstrumentType.PIANO);
        measures = new ArrayList<>();
        sheetMusic = new SheetMusic(instrument, Difficulty.BEGINNER, Clef.TREBLE, 
                                  true, measures, false);
        
        ArrayList<Note> notes = new ArrayList<>();
        notes.add(new Note(Pitch.C, PitchModifier.NONE, NoteValue.QUARTER, false, false, 0, ""));
        ArrayList<Chord> chords = new ArrayList<>();
        chords.add(new Chord("C", notes, false));
        testMeasure = new Measure(chords, 1, 100, 4, false, false);
    }

    @Test
    public void testConstructorInitializesCorrectly() {
        assertEquals(instrument, sheetMusic.getInstrument());
        assertEquals(Difficulty.BEGINNER, sheetMusic.getDifficulty());
        assertEquals(Clef.TREBLE, sheetMusic.getClef());
        assertTrue(sheetMusic.isAudioPlaybackEnabled());
        assertFalse(sheetMusic.isPrivate());
        assertTrue(sheetMusic.getMeasures().isEmpty());
    }

    @Test
    public void testToggleAudioPlaybackChangesState() {
        assertTrue(sheetMusic.isAudioPlaybackEnabled());
        sheetMusic.toggleAudioPlayback();
        assertFalse(sheetMusic.isAudioPlaybackEnabled());
        sheetMusic.toggleAudioPlayback();
        assertTrue(sheetMusic.isAudioPlaybackEnabled());
    }

    @Test
    public void testAddMeasureIncreasesMeasureCount() {
        assertEquals(0, sheetMusic.getMeasures().size());
        sheetMusic.addMeasure(testMeasure);
        assertEquals(1, sheetMusic.getMeasures().size());
        assertEquals(testMeasure, sheetMusic.getMeasures().get(0));
    }

    @Test
    public void testRemoveMeasureDecreasesMeasureCount() {
        sheetMusic.addMeasure(testMeasure);
        assertEquals(1, sheetMusic.getMeasures().size());
        sheetMusic.removeMeasure(testMeasure);
        assertEquals(0, sheetMusic.getMeasures().size());
    }

    @Test
    public void testSetDifficultyUpdatesValue() {
        assertEquals(Difficulty.BEGINNER, sheetMusic.getDifficulty());
        sheetMusic.setDifficulty(Difficulty.INTERMEDIATE);
        assertEquals(Difficulty.INTERMEDIATE, sheetMusic.getDifficulty());
    }

    @Test
    public void testSetClefUpdatesValue() {
        assertEquals(Clef.TREBLE, sheetMusic.getClef());
        sheetMusic.setClef(Clef.BASS);
        assertEquals(Clef.BASS, sheetMusic.getClef());
    }

    @Test
    public void testTogglePrivateChangesState() {
        assertFalse(sheetMusic.isPrivate());
        sheetMusic.togglePrivate();
        assertTrue(sheetMusic.isPrivate());
        sheetMusic.togglePrivate();
        assertFalse(sheetMusic.isPrivate());
    }

    @Test
    public void testToJfugueGeneratesCorrectPattern() {
        sheetMusic.addMeasure(testMeasure);
        String expectedPattern = testMeasure.toJfugue();
        assertEquals(expectedPattern, sheetMusic.toJfugue());
    }

    @Test
    public void testToJfugueWithMultipleMeasures() {
        Measure secondMeasure = new Measure(new ArrayList<>(), 2, 100, 4, false, false);
        sheetMusic.addMeasure(testMeasure);
        sheetMusic.addMeasure(secondMeasure);
        String expectedPattern = testMeasure.toJfugue() + " " + secondMeasure.toJfugue();
        assertEquals(expectedPattern, sheetMusic.toJfugue());
    }

    @Test
    public void testToJfugueWithEmptyMeasures() {
        assertEquals("", sheetMusic.toJfugue());
    }
} 