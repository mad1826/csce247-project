package com.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

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
     * gets the unique ID of the song
     * @return - the uuid of the song
     */
    public UUID getID() {
        return id;
    }
}
