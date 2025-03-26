package com.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.jfugue.player.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * represent a song in the music app system
 * song contains data such as title, artist, genres, and sheet music for different instruments
 * @author Makyia Irick
 */

public class Song {
    private UUID id;
    private String title;
    private String artist;
    private ArrayList<Genre> genres;
    private HashMap<Instrument, SheetMusic> sheets;

    /**
     * constructs a Song with a specified UUID
     * @param - the unique ID of the song
     * @param - the title of the song
     * @param - the artist of the song
     * @param - the list of genres associated with the song
     * @param - a HashMap of instruments and their corresponding sheet music
     * @param - a string for the pattern of the notes
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
     * @param - the title of the song
     * @param - the artist of the song
     * @param - the list of genres associated with the song
     * @param - a HashMap of instruments and their corresponding sheet music
     */
    public Song(String title, String artist, ArrayList<Genre> genres, HashMap<Instrument, SheetMusic> sheets) {
        this(UUID.randomUUID(), title, artist, genres, sheets,"");
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
     * converts song into a full JFugue pattern string
     * @param - the instrument to generate the JFugue
     * @return - the JFugue pattern for the entire song
     */
    public String toJfugue(Instrument instrument) {
        //get sheet music for the specified instrument
        SheetMusic sheetMusic = sheets.get(instrument);
        if (sheetMusic == null) {
            return ""; //return empty string if no sheet music for instrument
        }

        //get measures from sheet music
        ArrayList<Measure> measures = sheetMusic.getMeasures();

        StringBuilder songPattern = new StringBuilder();

        for ( Measure measure : measures) {
            songPattern.append(measure.toJfugue()).append(" ");
        }

        return songPattern.toString().trim();
    }

    /**
     * plays the song for a specific instrument
     * @param - the instrument to play
     */
    public void play(Instrument instrument) {
        Player player = new Player();
        player.play(toJfugue(instrument));
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
