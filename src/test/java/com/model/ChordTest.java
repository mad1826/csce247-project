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
import com.model.PitchModifier;
import com.model.Pitch;
import com.model.NoteValue;

public class ChordTest {

    @Test
    public void testTeseting() {
        assertTrue(true);
    }

    @Test
    public void testToJfugueSingleNote() {
        ArrayList<Note> notes = new ArrayList<>();
        notes.add(new Note(Pitch.C, PitchModifier.NONE, NoteValue.QUARTER, false, false, 4, ""));
        Chord chord = new Chord("C Major", notes, false);
        
        assertEquals("C4Q", chord.toJfugue());
    }

    @Test
    public void testToJfugueMultipleNotes() {
        ArrayList<Note> notes = new ArrayList<>();
        notes.add(new Note(Pitch.C, PitchModifier.NONE, NoteValue.QUARTER, false, false, 4, ""));
        notes.add(new Note(Pitch.E, PitchModifier.NONE, NoteValue.QUARTER, false, false, 4, ""));
        notes.add(new Note(Pitch.G, PitchModifier.NONE, NoteValue.QUARTER, false, false, 4, ""));
        Chord chord = new Chord("C Major", notes, false);
        
        assertEquals("(C4Q E4Q G4Q)", chord.toJfugue());
    }

    @Test
    public void testAddNoteValid() {
        ArrayList<Note> notes = new ArrayList<>();
        notes.add(new Note(Pitch.C, PitchModifier.NONE, NoteValue.QUARTER, false, false, 4, ""));
        Chord chord = new Chord("C Major", notes, false);
        Note newNote = new Note(Pitch.E, PitchModifier.NONE, NoteValue.QUARTER, false, false, 4, "");
        
        chord.addNote(newNote);
        assertTrue(chord.toJfugue().contains("E4Q"));
    }

    @Test
    public void testAddNoteDuplicate() {
        ArrayList<Note> notes = new ArrayList<>();
        Note note = new Note(Pitch.C, PitchModifier.NONE, NoteValue.QUARTER, false, false, 4, "");
        notes.add(note);
        Chord chord = new Chord("C Major", notes, false);
        
        int initialSize = chord.toJfugue().length();
        chord.addNote(note);
        assertEquals(initialSize, chord.toJfugue().length());
    }

    @Test
    public void testRemoveNoteValid() {
        ArrayList<Note> notes = new ArrayList<>();
        Note note1 = new Note(Pitch.C, PitchModifier.NONE, NoteValue.QUARTER, false, false, 4, "");
        Note note2 = new Note(Pitch.E, PitchModifier.NONE, NoteValue.QUARTER, false, false, 4, "");
        notes.add(note1);
        notes.add(note2);
        Chord chord = new Chord("C Major", notes, false);
        
        chord.removeNote(note2);
        assertFalse(chord.toJfugue().contains("E4Q"));
    }

    @Test
    public void testRemoveNoteInvalid() {
        ArrayList<Note> notes = new ArrayList<>();
        Note note1 = new Note(Pitch.C, PitchModifier.NONE, NoteValue.QUARTER, false, false, 4, "");
        notes.add(note1);
        Chord chord = new Chord("C Major", notes, false);
        Note nonExistingNote = new Note(Pitch.G, PitchModifier.NONE, NoteValue.QUARTER, false, false, 4, "");
        
        int initialSize = chord.toJfugue().length();
        chord.removeNote(nonExistingNote);
        assertEquals(initialSize, chord.toJfugue().length());
    }

    @Test
    public void testPlaySingleNote() {
        ArrayList<Note> notes = new ArrayList<>();
        notes.add(new Note(Pitch.C, PitchModifier.NONE, NoteValue.QUARTER, false, false, 4, ""));
        Chord chord = new Chord("C Major", notes, false);
        
        // This test just verifies the method runs without exceptions
        chord.play();
        assertTrue(true);
    }

    @Test
    public void testPlayChord() {
        ArrayList<Note> notes = new ArrayList<>();
        notes.add(new Note(Pitch.C, PitchModifier.NONE, NoteValue.QUARTER, false, false, 4, ""));
        notes.add(new Note(Pitch.E, PitchModifier.NONE, NoteValue.QUARTER, false, false, 4, ""));
        notes.add(new Note(Pitch.G, PitchModifier.NONE, NoteValue.QUARTER, false, false, 4, ""));
        Chord chord = new Chord("C Major", notes, false);
        
        // This test just verifies the method runs without exceptions
        chord.play();
        assertTrue(true);
    }

    @Test
    public void testToJSONBasic() {
        ArrayList<Note> notes = new ArrayList<>();
        notes.add(new Note(Pitch.C, PitchModifier.NONE, NoteValue.QUARTER, false, false, 4, ""));
        Chord chord = new Chord("C Major", notes, false);
        
        JSONObject json = chord.toJSON();
        assertEquals("C Major", json.get("name"));
        assertTrue(json.get("notes") instanceof JSONArray);
        assertFalse((boolean) json.get("tie"));
    }

    @Test
    public void testToJSONWithMultipleNotes() {
        ArrayList<Note> notes = new ArrayList<>();
        notes.add(new Note(Pitch.C, PitchModifier.NONE, NoteValue.QUARTER, false, false, 4, ""));
        notes.add(new Note(Pitch.E, PitchModifier.NONE, NoteValue.QUARTER, false, false, 4, ""));
        Chord chord = new Chord("C Major", notes, true);
        
        JSONObject json = chord.toJSON();
        JSONArray notesArray = (JSONArray) json.get("notes");
        assertEquals(2, notesArray.size());
        assertTrue((boolean) json.get("tie"));
    }

    @Test
    public void testToJSONNoteContents() {
        ArrayList<Note> notes = new ArrayList<>();
        notes.add(new Note(Pitch.C, PitchModifier.NONE, NoteValue.QUARTER, false, false, 4, ""));
        Chord chord = new Chord("C Major", notes, false);
        
        JSONObject json = chord.toJSON();
        JSONArray notesArray = (JSONArray) json.get("notes");
        JSONObject firstNote = (JSONObject) notesArray.get(0);
        assertEquals("C", firstNote.get("pitch"));
    }
}