package com.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.model.datahandlers.DataLoader;

/**
 * manages all songs within the music app system
 * implements the Singleton design pattern to ensure only one instance exists
 * @author Makyia Irick
 */
public class SongManager implements  SavableList<Song> {
    private static SongManager songManager; //singleton instance
    private HashMap<UUID, Song> songs; //list of all songs
    final static String filePath = "src/main/java/com/data/songs.json";

    /**
     * private constructor for SongManager
     */
    private SongManager() {
        this.songs = new HashMap<>();
    }

    /**
     * eeturns singleton instance of SongManager
     * if the instance does not exist, a new one is created
     * @return - the singleton instance of SongManager
     */
    public static SongManager getInstance() {
        if (songManager == null) {
            songManager = new SongManager();
        }
        return songManager;
    }

    /**
     * retrieves a song by its unique ID
     * @param - the unique ID of the song
     * @return - the Song object if found, otherwise null
     */
    public OperationResult<Song> getSong(UUID id) {
        for (Song song : songs.values()) {
            if (song.getID().equals(id)) {
                return new OperationResult<>(song);
            }
        }
        return new OperationResult<>("Song not found");
    }

	/**
	 * Get all songs loaded
	 * @return All songs
	 */
	public HashMap<UUID, Song> getSongs() {
		return songs;
	}

    /**
     * finds songs based on search filters
     * @param - a HashMap of search filters (e.g., title, artist, genre)
     * @return All matching songs
     */
    public HashMap<UUID, Song> findSongs(HashMap<SongFilter, String> query) {
		HashMap<UUID, Song> matches = new HashMap<>();

		for (UUID songId : songs.keySet()) {
			Song song = songs.get(songId);

			String titleQuery = query.get(SongFilter.TITLE);
			String artistQuery = query.get(SongFilter.ARTIST);
			String genreQuery = query.get(SongFilter.GENRE);

			if (genreQuery != null) {
				boolean correct = false;
				for (Genre genre : song.getGenres()) {
					if (genre.toString().toLowerCase().contains(genreQuery.toLowerCase())) {
						correct = true;
						break;
					}
				}
				
				if (!correct)
					continue;
			}

			if ((titleQuery == null || song.getTitle().toLowerCase().contains(titleQuery.toLowerCase()))
			&& (artistQuery == null || song.getArtist().toLowerCase().equalsIgnoreCase(artistQuery.toLowerCase())))
				matches.put(songId, song);
		}

        return matches;
    }

    /**
     * adds a new song to the list
     * @param - the song to add
     * @return - true if the song was added successfully, false otherwise
     */
    public boolean createSong(Song song) {
        songs.put(song.getID(), song);
        return true;
    }

    /**
     * deletes a song from the list by its ID
     * @param - the unique ID of the song
     * @return - operationResult with the song if found, or null if not found
     */
    public OperationResult<Song> deleteSong(UUID songId) {
        for (Song song : songs.values()) {
            if (song.getID().equals(songId)) {
                songs.remove(song.getID());
                return new OperationResult<>(song);
            }
        }
        return new OperationResult<>("Song not found");
    }

    /**
     * Returns the json file path this list is stored at.
     * @return filePath
     */
	@Override
	public String getFilePath() {
		return filePath;
	}

    /**
     * converts all songs into JSON format
     * this is a placeholder method
     * @return - JSON representation of the song
     */
	@SuppressWarnings({ "exports", "unchecked" })
	@Override
    public JSONArray toJSON() {
        JSONArray songsJSON = new JSONArray();

		for (Song song : songs.values()) {
			songsJSON.add(song.toJSON());
		}

		return songsJSON;
    }

	/**
	 * Below methods ending at toObject have to do with JSON to java object conversions
	 * @param object JSONObject
	 * @return java object
	 */

    private Chord toChord(JSONObject chord) {
        String name = (String)chord.get("name");
        Boolean tie = (Boolean)chord.get("tie");

        ArrayList<Note> notes = new ArrayList<>();
        JSONArray notesJSON = (JSONArray)chord.get("notes");
        for (Object o : notesJSON) {
            JSONObject noteObj = (JSONObject)o;
            Pitch p = Pitch.valueOf((String)noteObj.get("pitch"));
            PitchModifier pm = PitchModifier.valueOf((String)noteObj.get("pitchModifier"));
            NoteValue v = NoteValue.valueOf((String)noteObj.get("value"));
            Boolean dot = (Boolean) noteObj.get("dot");
            Boolean line = (Boolean) noteObj.get("line");
            int oct = (int) noteObj.get("octave");

            Note n = new Note(p, pm, v, dot, line, oct);
            notes.add(n);
        }

        return new Chord(name, notes, tie);
    }

    private Measure toMeasure(JSONObject measure) {
        int tempo = (int)measure.get("tempo");
        int tsn = (int)measure.get("timeSignatureNum");
        int tsd = (int) measure.get("timeSignatureDenom");
        boolean repeatOpen = (Boolean) measure.get("repeatOpen");
        boolean repeatClosed = (Boolean) measure.get("repeatClosed");

        ArrayList<Chord> chords = new ArrayList<>();
        JSONArray chordsJSON = (JSONArray)measure.get("chords");
        for (Object chord : chordsJSON) {
            chords.add(toChord((JSONObject)chord));
        }

        return new Measure(chords, tempo, tsn, tsd, repeatOpen, repeatClosed);
    }

    private SheetMusic toSheet(String instrument, JSONObject sheet) {
        Difficulty d = Difficulty.valueOf((String)sheet.get("difficulty"));
        Clef c = Clef.valueOf((String)sheet.get("clef"));
        InstrumentType i = InstrumentType.valueOf(instrument);
        Boolean isPrivate = (Boolean)sheet.get("isPrivate");

        ArrayList<Measure> measures = new ArrayList<>();
        JSONArray measuresJSON = (JSONArray)sheet.get("measures");
        for (Object measure : measuresJSON) {
            measures.add(toMeasure((JSONObject)measure));
        }

        return new SheetMusic(new Instrument(i),d,c,true,measures,isPrivate);
    }

	@Override
    public Song toObject(@SuppressWarnings("exports") JSONObject object) {
        UUID id = UUID.fromString((String) object.get("id"));
        String title = (String) object.get("title");
        String artist = (String) object.get("artist");

        ArrayList<Genre> genres = new ArrayList<>();
        JSONArray genresJSON = (JSONArray)object.get("genres");
        for (Object genre : genresJSON) { //iterate array
            genres.add(Genre.valueOf((String) genre));
        }

        HashMap<Instrument,SheetMusic> sheets = new HashMap<>();
        JSONObject sheetsJSON = (JSONObject)object.get("sheets");

        for (Object key : sheetsJSON.keySet()) {
            String index = (String)key;
            JSONObject value = (JSONObject)sheetsJSON.get(index);

            SheetMusic m = toSheet(index,value);

            sheets.put(m.getInstrument(),m);
        }

        return new Song(id,title,artist,genres,sheets);
    }

    @Override
    public OperationResult<Void> loadData() {
        OperationResult<ArrayList<Song>> or = DataLoader.getData(this);

        if (or.success) {
            for (Song s : or.result) {
                this.songs.put(s.getId(),s);
            }
        }

        return new OperationResult<>(true);
    }

    @Override
    public OperationResult<Void> linkData() {
        return new OperationResult<>(true); //Nothing to link
    }

    public static void main(String[] args) {
        SongManager.getInstance().loadData();
        System.out.println(SongManager.getInstance().songs);
    }
}
