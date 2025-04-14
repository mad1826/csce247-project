package com.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.json.simple.JSONObject;
import org.junit.Test;

public class NoteTest {

    @Test
    public void testToJfugueBasicNote() {
        Note note = new Note(Pitch.C, PitchModifier.NONE, NoteValue.QUARTER, false, false, 4, "");
        assertEquals("C4Q", note.toJfugue());
    }

    @Test
    public void testSetPitch() {
        Note note = new Note(Pitch.A, PitchModifier.NONE, NoteValue.QUARTER, false, false, 4, "");
        note.setPitch(Pitch.D);
        assertEquals("D4Q", note.toJfugue());
    }

    @Test
    public void testSetPitchModifier() {
        Note note = new Note(Pitch.E, PitchModifier.NONE, NoteValue.QUARTER, false, false, 4, "");
        note.setPitchModifier(PitchModifier.SHARP);
        // Verify through JSON since we can't directly access pitchModifier
        JSONObject json = note.toJSON();
        assertEquals("SHARP", json.get("pitchModifier"));
    }

    @Test
    public void testSetValue() {
        Note note = new Note(Pitch.F, PitchModifier.NONE, NoteValue.QUARTER, false, false, 4, "");
        note.setValue(NoteValue.SIXTEENTH);
        assertEquals("F4S", note.toJfugue());
    }

    @Test
    public void testToggleDot() {
        Note note = new Note(Pitch.G, PitchModifier.NONE, NoteValue.QUARTER, false, false, 4, "");
        note.toggleDot(); // dot should now be true
        JSONObject json = note.toJSON();
        assertTrue((boolean) json.get("dot"));
        note.toggleDot(); // dot should now be false again
        json = note.toJSON();
        assertFalse((boolean) json.get("dot"));
    }

    @Test
    public void testToggleLine() {
        Note note = new Note(Pitch.A, PitchModifier.NONE, NoteValue.QUARTER, false, false, 4, "");
        note.toggleLine(); // line should now be true
        JSONObject json = note.toJSON();
        assertTrue((boolean) json.get("line"));
        note.toggleLine(); // line should now be false again
        json = note.toJSON();
        assertFalse((boolean) json.get("line"));
    }

    @Test
    public void testSetOctaveValid() {
        Note note = new Note(Pitch.B, PitchModifier.NONE, NoteValue.QUARTER, false, false, 4, "");
        note.setOctave(5);
        assertEquals("B5Q", note.toJfugue());
    }

    @Test
    public void testSetOctaveInvalid() {
        Note note = new Note(Pitch.C, PitchModifier.NONE, NoteValue.QUARTER, false, false, 4, "");
        note.setOctave(-1); // Should be ignored
        assertEquals("C4Q", note.toJfugue());
    }

    @Test
    public void testToJSONBasic() {
        Note note = new Note(Pitch.D, PitchModifier.NONE, NoteValue.HALF, false, true, 4, "");
        JSONObject json = note.toJSON();
        
        assertEquals("D", json.get("pitch"));
        assertEquals("NONE", json.get("pitchModifier"));
        assertEquals("HALF", json.get("value"));
        assertFalse((boolean) json.get("dot"));
        assertTrue((boolean) json.get("line"));
        assertEquals(4L, json.get("octave"));
    }

    @Test
    public void testToString() {
        Note note = new Note(Pitch.F, PitchModifier.FLAT, NoteValue.QUARTER, false, false, 4, "");
        assertEquals("Fb", note.toString());
    }
}