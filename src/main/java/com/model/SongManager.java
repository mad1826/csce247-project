package com.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * manages all songs within the music app system
 * implements the Singleton design pattern to ensure only one instance exists
 * @author Makyia Irick
 */
public class SongManager {
    private static SongManager songManager; //singleton instance
    private ArrayList<Song> songs; //list of all songs
    private String filePath; //file path for saving songs

    /**
     * private constructor for SongManager
     * @param - the list of songs to manage
     * @param - the file path where songs are stored
     */
    private SongManager(ArrayList<Song> songs, String filePath) {
        this.songs = songs;
        this.filePath = filePath;
    }

    /**
     * eeturns singleton instance of SongManager
     * if the instance does not exist, a new one is created
     * @param - the list of songs
     * @param - the file path where songs are stored
     * @return - the singleton instance of SongManager
     */
    public static SongManager getInstance(ArrayList<Song> songs, String filePath) {
        if (songManager == null) {
            songManager = new SongManager(songs, filePath);
        }
        return songManager;
    }

    /**
     * retrieves a song by its unique ID
     * @param - the unique ID of the song
     * @return - the Song object if found, otherwise null
     */
    public Song getSong(UUID id) {
        for (Song song : songs) {
            if (song.getID().equals(id)) {
                return song;
            }
        }
        return null;
    }

    /**
     * finds songs based on search filters
     * @param - a HashMap of search filters (e.g., title, artist, genre)
     * @return - a list of matching songs
     */
    public ArrayList<Song> findSongs(HashMap<SongFilter, String> query) {
        return new ArrayList<>(); //placeholder stub
    }

    /**
     * adds a new song to the list
     * @param - the song to add
     * @return - true if the song was added successfully, false otherwise
     */
    public boolean createSong(Song song) {
        songs.add(song);
        return true;
    }

    /**
     * deletes a song from the list by its ID
     * @param - the unique ID of the song
     * @return - operationResult with the song if found, or null if not found
     */
    public OperationResult<Song> deleteSong(UUID songId) {
        for (Song song : songs) {
            if (song.getID().equals(songId)) {
                songs.remove(song);
                return new OperationResult<>(song);
            }
        }
        return new OperationResult<>("Song not found");
    }

    /**
     * converts a Song object into JSON format
     * this is a placeholder method
     * @param - the song to convert
     * @return - JSON representation of the song
     */
    public String toJSON(Song song) {
        return "{ \"title\": \"" + song.getTitle() + "\", \"artist\": \"" + song.getArtist() + "\" }";
    }

    /**
     * converts JSON data into a HashMap of songs
     * this is a placeholder method
     * @param - the JSON data
     * @return - a HashMap of songs
     */
    public HashMap<UUID, Song> toObjects(String json) {
        return new HashMap<>();
    }
}
