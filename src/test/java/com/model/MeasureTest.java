package com.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;

import com.model.Chord;
import com.model.Note;
import com.model.Measure;
import com.model.Pitch;
import com.model.PitchModifier;
import com.model.NoteValue;

public class MeasureTest {

    @Test
    public void testToJfugueBasicMeasure() {
        ArrayList<Chord> chords = new ArrayList<>();
        chords.add(createTestChord("C Major"));
        Measure measure = new Measure(chords, 120, 4, 4, false, false);
        
        assertEquals("T120 (C4Q E4Q G4Q)", measure.toJfugue());
    }

    @Test
    public void testToJfugueWithRepeatOpen() {
        ArrayList<Chord> chords = new ArrayList<>();
        chords.add(createTestChord("C Major"));
        Measure measure = new Measure(chords, 120, 4, 4, true, false);
        
        assertTrue(measure.toJfugue().contains("|:"));
    }

    @Test
    public void testToJfugueWithRepeatClose() {
        ArrayList<Chord> chords = new ArrayList<>();
        chords.add(createTestChord("C Major"));
        Measure measure = new Measure(chords, 120, 4, 4, false, true);
        
        assertTrue(measure.toJfugue().contains(":|"));
    }

    @Test
    public void testToJfugueMultipleChords() {
        ArrayList<Chord> chords = new ArrayList<>();
        chords.add(createTestChord("C Major"));
        chords.add(createTestChord("F Major"));
        Measure measure = new Measure(chords, 120, 4, 4, false, false);
        
        String result = measure.toJfugue();
        assertTrue(result.contains("(C4Q E4Q G4Q)"));
        assertTrue(result.contains("(F4Q A4Q C5Q)"));
    }

    @Test
    public void testAddChordValid() {
        ArrayList<Chord> chords = new ArrayList<>();
        Measure measure = new Measure(chords, 120, 4, 4, false, false);
        Chord newChord = createTestChord("G Major");
        
        measure.addChord(newChord);
        assertTrue(measure.toJfugue().contains("(G4Q B4Q D5Q)"));
    }

    @Test
    public void testAddChordDuplicate() {
        ArrayList<Chord> chords = new ArrayList<>();
        Chord chord = createTestChord("C Major");
        chords.add(chord);
        Measure measure = new Measure(chords, 120, 4, 4, false, false);
        
        int initialSize = measure.toJfugue().length();
        measure.addChord(chord);
        assertEquals(initialSize, measure.toJfugue().length());
    }

    @Test
    public void testRemoveChordValid() {
        ArrayList<Chord> chords = new ArrayList<>();
        Chord chord1 = createTestChord("C Major");
        Chord chord2 = createTestChord("F Major");
        chords.add(chord1);
        chords.add(chord2);
        Measure measure = new Measure(chords, 120, 4, 4, false, false);
        
        measure.removeChord(chord2);
        assertFalse(measure.toJfugue().contains("(F4Q A4Q C5Q)"));
    }

    @Test
    public void testRemoveChordInvalid() {
        ArrayList<Chord> chords = new ArrayList<>();
        Chord chord1 = createTestChord("C Major");
        chords.add(chord1);
        Measure measure = new Measure(chords, 120, 4, 4, false, false);
        Chord nonExistingChord = createTestChord("G Major");
        
        int initialSize = measure.toJfugue().length();
        measure.removeChord(nonExistingChord);
        assertEquals(initialSize, measure.toJfugue().length());
    }

    @Test
    public void testPlaySingleChord() {
        ArrayList<Chord> chords = new ArrayList<>();
        chords.add(createTestChord("C Major"));
        Measure measure = new Measure(chords, 120, 4, 4, false, false);
        
        // Just verify the method runs without exceptions
        measure.play();
        assertTrue(true);
    }

    @Test
    public void testPlayMultipleChords() {
        ArrayList<Chord> chords = new ArrayList<>();
        chords.add(createTestChord("C Major"));
        chords.add(createTestChord("G Major"));
        Measure measure = new Measure(chords, 120, 4, 4, false, false);
        
        // Just verify the method runs without exceptions
        measure.play();
        assertTrue(true);
    }

    @Test
    public void testToJSONBasic() {
        ArrayList<Chord> chords = new ArrayList<>();
        chords.add(createTestChord("C Major"));
        Measure measure = new Measure(chords, 120, 4, 4, false, false);
        
        JSONObject json = measure.toJSON();
        assertTrue(json.get("chords") instanceof JSONArray);
        assertEquals(120L, json.get("tempo")); // Note: JSON simple uses Long for numbers
    }

    @Test
    public void testToJSONWithRepeat() {
        ArrayList<Chord> chords = new ArrayList<>();
        chords.add(createTestChord("F Major"));
        Measure measure = new Measure(chords, 100, 3, 4, true, true);
        
        JSONObject json = measure.toJSON();
        assertTrue((boolean) json.get("repeatOpen"));
        assertTrue((boolean) json.get("repeatClose"));
    }

    @Test
    public void testToJSONChordContents() {
        ArrayList<Chord> chords = new ArrayList<>();
        chords.add(createTestChord("G Major"));
        Measure measure = new Measure(chords, 150, 2, 4, false, false);
        
        JSONObject json = measure.toJSON();
        JSONArray chordsArray = (JSONArray) json.get("chords");
        JSONObject firstChord = (JSONObject) chordsArray.get(0);
        assertEquals("G Major", firstChord.get("name"));
    }

    // Helper method to create test chords
    private Chord createTestChord(String name) {
        ArrayList<Note> notes = new ArrayList<>();
        switch(name) {
            case "C Major":
                notes.add(new Note(Pitch.C, PitchModifier.NONE, NoteValue.QUARTER, false, false, 4, ""));
                notes.add(new Note(Pitch.E, PitchModifier.NONE, NoteValue.QUARTER, false, false, 4, ""));
                notes.add(new Note(Pitch.G, PitchModifier.NONE, NoteValue.QUARTER, false, false, 4, ""));
                break;
            case "F Major":
                notes.add(new Note(Pitch.F, PitchModifier.NONE, NoteValue.QUARTER, false, false, 4, ""));
                notes.add(new Note(Pitch.A, PitchModifier.NONE, NoteValue.QUARTER, false, false, 4, ""));
                notes.add(new Note(Pitch.C, PitchModifier.NONE, NoteValue.QUARTER, false, false, 5, ""));
                break;
            case "G Major":
                notes.add(new Note(Pitch.G, PitchModifier.NONE, NoteValue.QUARTER, false, false, 4, ""));
                notes.add(new Note(Pitch.B, PitchModifier.NONE, NoteValue.QUARTER, false, false, 4, ""));
                notes.add(new Note(Pitch.D, PitchModifier.NONE, NoteValue.QUARTER, false, false, 5, ""));
                break;
        }
        return new Chord(name, notes, false);
    }
}