package com.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jfugue.player.Player;

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
    private String jfuguePattern;

    /**
     * constructs a Song with a specified UUID
     * @param - the unique ID of the song
     * @param - the title of the song
     * @param - the artist of the song
     * @param - the list of genres associated with the song
     * @param - a HashMap of instruments and their corresponding sheet music
     * @param - a string for the pattern of the notes
     */
    public Song(UUID id, String title, String artist, ArrayList<Genre> genres, HashMap<Instrument, SheetMusic> sheets, String jfuguePattern) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.genres = genres;
        this.sheets = sheets;
        this.jfuguePattern = jfuguePattern;
    }

    /**
     * constructs a Song without a UUID (automatically generates one)
     * @param - the title of the song
     * @param - the artist of the song
     * @param - the list of genres associated with the song
     * @param - a HashMap of instruments and their corresponding sheet music
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

    /**
     * plays the entire song using JFugue
     */
    public void play() {
        Player player = new Player();
        player.play(jfuguePattern);
    }

    public String getJfuguePattern() {
        return jfuguePattern;
    }
}
