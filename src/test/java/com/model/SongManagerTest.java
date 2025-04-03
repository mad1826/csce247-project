package com.model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import com.model.managers.SongManager;

public class SongManagerTest {
    private SongManager songManager;
    private Song testSong;
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
        // Initialize SongManager without data persistence
        songManager = SongManager.getInstance();
        
        // Create test data without saving
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
        
        testSong = new Song(testId, testTitle, testArtist, testGenres, testSheets);
    }

    @Test
    public void testGetInstanceReturnsSameInstance() {
        SongManager instance1 = SongManager.getInstance();
        SongManager instance2 = SongManager.getInstance();
        assertSame(instance1, instance2);
    }

    @Test
    public void testCreateSongWithValidDataReturnsTrue() {
        boolean result = songManager.createSong(testSong);
        assertTrue(result);
        assertTrue(songManager.getSongs().containsValue(testSong));
    }

    @Test
    public void testGetSongWithValidIdReturnsSong() {
        songManager.createSong(testSong);
        OperationResult<Song> result = songManager.getSong(testId);
        assertTrue(result.success);
        assertEquals(testSong, result.result);
    }

    @Test
    public void testGetSongWithInvalidIdReturnsError() {
        OperationResult<Song> result = songManager.getSong(UUID.randomUUID());
        assertFalse(result.success);
        assertEquals("Song not found", result.message);
    }

    @Test
    public void testDeleteSongWithValidIdReturnsSuccess() {
        songManager.createSong(testSong);
        OperationResult<Song> result = songManager.deleteSong(testId);
        assertTrue(result.success);
        assertEquals(testSong, result.result);
        assertFalse(songManager.getSongs().containsValue(testSong));
    }

    @Test
    public void testDeleteSongWithInvalidIdReturnsError() {
        OperationResult<Song> result = songManager.deleteSong(UUID.randomUUID());
        assertFalse(result.success);
        assertEquals("Song not found", result.message);
    }

    @Test
    public void testFindSongsWithTitleFilterReturnsMatchingSongs() {
        songManager.createSong(testSong);
        HashMap<SongFilter, String> query = new HashMap<>();
        query.put(SongFilter.TITLE, "Test");
        HashMap<UUID, Song> results = songManager.findSongs(query);
        assertTrue(results.containsValue(testSong));
    }

    @Test
    public void testFindSongsWithArtistFilterReturnsMatchingSongs() {
        songManager.createSong(testSong);
        HashMap<SongFilter, String> query = new HashMap<>();
        query.put(SongFilter.ARTIST, "Test Artist");
        HashMap<UUID, Song> results = songManager.findSongs(query);
        assertTrue(results.containsValue(testSong));
    }

    @Test
    public void testFindSongsWithGenreFilterReturnsMatchingSongs() {
        songManager.createSong(testSong);
        HashMap<SongFilter, String> query = new HashMap<>();
        query.put(SongFilter.GENRE, "ROCK");
        HashMap<UUID, Song> results = songManager.findSongs(query);
        assertTrue(results.containsValue(testSong));
    }

    @Test
    public void testFindSongsWithMultipleFiltersReturnsMatchingSongs() {
        songManager.createSong(testSong);
        HashMap<SongFilter, String> query = new HashMap<>();
        query.put(SongFilter.TITLE, "Test");
        query.put(SongFilter.ARTIST, "Test Artist");
        query.put(SongFilter.GENRE, "ROCK");
        HashMap<UUID, Song> results = songManager.findSongs(query);
        assertTrue(results.containsValue(testSong));
    }

    @Test
    public void testFindSongsWithNoMatchesReturnsEmptyMap() {
        HashMap<SongFilter, String> query = new HashMap<>();
        query.put(SongFilter.TITLE, "Nonexistent");
        HashMap<UUID, Song> results = songManager.findSongs(query);
        assertTrue(results.isEmpty());
    }

    @Test
    public void testFindSongsWithNullQueryReturnsAllSongs() {
        songManager.createSong(testSong);
        HashMap<UUID, Song> results = songManager.findSongs(null);
        assertTrue(results.containsValue(testSong));
    }

    @Test
    public void testGetSongsReturnsAllSongs() {
        songManager.createSong(testSong);
        HashMap<UUID, Song> songs = songManager.getSongs();
        assertTrue(songs.containsValue(testSong));
    }

    @Test
    public void testCreateSongWithNullSongReturnsFalse() {
        boolean result = songManager.createSong(null);
        assertFalse(result);
    }
} 