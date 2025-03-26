package com.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * represent a song in the music app system
 * song contains data such as title, artist, genres, and sheet music for different instruments
 * @author Makyia Irick
 */

public class Song {
	/**
	 * The song's id
	 */
    private UUID id;
	/**
	 * The song's title
	 */
    private String title;
	/**
	 * The song's artist
	 */
    private String artist;
	/**
	 * The song's genres
	 */
    private ArrayList<Genre> genres;
	/**
	 * Sheets made for the song
	 */
    private HashMap<Instrument, SheetMusic> sheets;

    /**
     * constructs a Song with a specified UUID
     * @param id the unique ID of the song
     * @param title the title of the song
     * @param artist the artist of the song
     * @param genres the list of genres associated with the song
     * @param sheets a HashMap of instruments and their corresponding sheet music
     */
    public Song(UUID id, String title, String artist, ArrayList<Genre> genres, HashMap<Instrument, SheetMusic> sheets) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.genres = genres;
        this.sheets = sheets;
    }

    /**
     * constructs a Song without a UUID (automatically generates one)
     * @param title the title of the song
     * @param artist the artist of the song
     * @param genres the list of genres associated with the song
     * @param sheets a HashMap of instruments and their corresponding sheet music
     */
    public Song(String title, String artist, ArrayList<Genre> genres, HashMap<Instrument, SheetMusic> sheets) {
        this(UUID.randomUUID(), title, artist, genres, sheets);
    }

	/**
	 * Gets the course's identifier
	 * @return a UUID
	 */
	public UUID getId() {
		return id;
	}

    /**
     * retrieves the sheet music associated with the song for each instrument
     * @return - HashMap containing instruments as keys and their corresponding sheet music as values
     */
    public HashMap<Instrument, SheetMusic> getSheets() {
        return sheets;
    }

	/**
	 * Creates a new sheet for this song
	 * @param instrument - the instrument that will play the sheet
	 * @param difficulty - the sheet's difficulty
	 * @param clef - the clef to play the sheet in
	 * @param audioPlaybackEnabled - whether the sheet will play audio back
	 * @param measures - the sheet's measures
	 * @param isPrivate - whether the sheet is private
	 * @return The result of attempting to create the sheet
	 */
	public OperationResult<SheetMusic> createSheet(Instrument instrument, Difficulty difficulty, 
		Clef clef, boolean audioPlaybackEnabled, 
		ArrayList<Measure> measures, boolean isPrivate) {
		SheetMusic newSheet = new SheetMusic(instrument, difficulty, clef, audioPlaybackEnabled, measures, isPrivate);
		sheets.put(instrument, newSheet);
		return new OperationResult<>(newSheet);
	}

    /**
     * gets the title of the song
     * @return - title of the song
     */
    public String getTitle() {
        return title;
    }

    /**
     * gets the artist of the song
     * @return - the artist of the song
     */
    public String getArtist() {
        return artist;
    }

	/**
	 * Gets the genres the song belongs to
	 * @return A list of song genres
	 */
	public ArrayList<Genre> getGenres() {
		return genres;
	}

    /**
     * gets the unique ID of the song
     * @return - the uuid of the song
     */
    public UUID getID() {
        return id;
    }

	/**
	 * Gets the sheet for a specified instrument.
	 * @param instrumentName the instrument's name
	 * @return the sheet matching the instrument, or null if not found
	 */
	public SheetMusic getSheet(String instrumentName) {
		for (Instrument instrument : sheets.keySet()) {
			if (instrument.getType().getName().equalsIgnoreCase(instrumentName)) {
				return sheets.get(instrument);
			}
		}
		return null;
	}
	
	/**
	 * Transforms this instance into a JSON object
	 * @return a JSON object
	 */
	@SuppressWarnings({ "unchecked", "exports" })
	public JSONObject toJSON() {
		JSONObject songObject = new JSONObject();

		songObject.put("id", id.toString());
		songObject.put("title", title);
		songObject.put("artist", artist);
		JSONArray genresJSON = new JSONArray();
		for (Genre genre : genres) {
			genresJSON.add(genre.toString());
		}
		songObject.put("genres", genresJSON);
		JSONObject sheetsJSON = new JSONObject();
		for (Instrument instrument : sheets.keySet()) {
			JSONObject instrumentJSON = instrument.toJSON(sheets.get(instrument).toJSON());
			sheetsJSON.put(instrument.getType().toString(), instrumentJSON);
		}
		songObject.put("sheets", sheetsJSON);

		return songObject;
	}

	/**
	 * Gets a string representation of the song
	 */
	@Override
	public String toString() {
		return "Song {" +
            "\n\tid=" + id +
            ",\n\ttitle=" + title +
            ",\n\tartist=" + artist +
            ",\n\tgenres=" + genres +
            ",\n\tsheets=" + sheets +
            "\n}";
	}

}
