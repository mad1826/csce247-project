package com.model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;


public class SongTest {
    private Song song;
    private UUID testId;
    private String testTitle;
    private String testArtist;
    private ArrayList<Genre> testGenres;
    private HashMap<Instrument, SheetMusic> testSheets;
    private Instrument testInstrument;
    private SheetMusic testSheet;
    private Measure testMeasure;

    @Before
    public void setUp() {
        testId = UUID.randomUUID();
        testTitle = "Test Song";
        testArtist = "Test Artist";
        testGenres = new ArrayList<>();
        testGenres.add(Genre.ROCK);
        testGenres.add(Genre.JAZZ);
        
        testSheets = new HashMap<>();
        testInstrument = new Instrument(InstrumentType.PIANO);
        
        ArrayList<Note> notes = new ArrayList<>();
        notes.add(new Note(Pitch.C, PitchModifier.NONE, NoteValue.QUARTER, false, false, 0, ""));
        ArrayList<Chord> chords = new ArrayList<>();
        chords.add(new Chord("C", notes, false));
        testMeasure = new Measure(chords, 1, 100, 4, false, false);
        
        ArrayList<Measure> measures = new ArrayList<>();
        measures.add(testMeasure);
        testSheet = new SheetMusic(testInstrument, Difficulty.BEGINNER, Clef.TREBLE, true, measures, false);
        testSheets.put(testInstrument, testSheet);
        
        song = new Song(testId, testTitle, testArtist, testGenres, testSheets);
    }

    @Test
    public void testConstructorWithUUIDInitializesCorrectly() {
        assertEquals(testId, song.getId());
        assertEquals(testTitle, song.getTitle());
        assertEquals(testArtist, song.getArtist());
        assertEquals(testGenres, song.getGenres());
        assertEquals(testSheets, song.getSheets());
    }

    @Test
    public void testConstructorWithoutUUIDGeneratesUUID() {
        Song newSong = new Song(testTitle, testArtist, testGenres, testSheets);
        assertNotNull(newSong.getId());
        assertNotEquals(testId, newSong.getId());
        assertEquals(testTitle, newSong.getTitle());
        assertEquals(testArtist, newSong.getArtist());
        assertEquals(testGenres, newSong.getGenres());
        assertEquals(testSheets, newSong.getSheets());
    }

    @Test
    public void testCreateSheetAddsNewSheet() {
        Instrument newInstrument = new Instrument(InstrumentType.GUITAR);
        ArrayList<Measure> newMeasures = new ArrayList<>();
        SheetMusic newSheet = new SheetMusic(newInstrument, Difficulty.INTERMEDIATE, Clef.TREBLE, true, newMeasures, false);
        
        OperationResult<SheetMusic> result = song.createSheet(newInstrument, Difficulty.INTERMEDIATE, 
            Clef.TREBLE, true, newMeasures, false);
        
        assertTrue(result.success);
        assertEquals(newSheet.getInstrument(), result.result.getInstrument());
        assertEquals(newSheet.getDifficulty(), result.result.getDifficulty());
        assertEquals(newSheet.getClef(), result.result.getClef());
        assertEquals(newSheet.isAudioPlaybackEnabled(), result.result.isAudioPlaybackEnabled());
        assertEquals(newSheet.isPrivate(), result.result.isPrivate());
        assertEquals(2, song.getSheets().size());
    }

    @Test
    public void testGetSheetWithValidInstrumentReturnsCorrectSheet() {
        SheetMusic result = song.getSheet("PIANO");
        assertNotNull(result);
        assertEquals(testSheet, result);
    }

    @Test
    public void testGetSheetWithInvalidInstrumentReturnsNull() {
        SheetMusic result = song.getSheet("INVALID");
        assertNull(result);
    }

    @Test
    public void testGetSheetWithNullInstrumentReturnsNull() {
        SheetMusic result = song.getSheet(null);
        assertNull(result);
    }

    @Test
    public void testToJSONContainsAllFields() {
        org.json.simple.JSONObject json = song.toJSON();
        assertEquals(testId.toString(), json.get("id"));
        assertEquals(testTitle, json.get("title"));
        assertEquals(testArtist, json.get("artist"));
        
        org.json.simple.JSONArray genresJson = (org.json.simple.JSONArray) json.get("genres");
        assertEquals(2, genresJson.size());
        assertTrue(genresJson.contains(Genre.ROCK.toString()));
        assertTrue(genresJson.contains(Genre.JAZZ.toString()));
        
        org.json.simple.JSONObject sheetsJson = (org.json.simple.JSONObject) json.get("sheets");
        assertEquals(1, sheetsJson.size());
        assertTrue(sheetsJson.containsKey(InstrumentType.PIANO.toString()));
    }

    @Test
    public void testToStringContainsAllFields() {
        String stringRep = song.toString();
        assertTrue(stringRep.contains(testId.toString()));
        assertTrue(stringRep.contains(testTitle));
        assertTrue(stringRep.contains(testArtist));
        assertTrue(stringRep.contains(testGenres.toString()));
        assertTrue(stringRep.contains(testSheets.toString()));
    }
} 