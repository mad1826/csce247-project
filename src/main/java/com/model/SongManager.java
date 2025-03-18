package com.model;

import java.util.HashMap;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
    public Song getSong(UUID id) {
        for (Song song : songs.values()) {
            if (song.getID().equals(id)) {
                return song;
            }
        }
        return null;
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
	 * TODO
	 * @param object
	 * @return
	 */
	@Override
    public Song toObject(@SuppressWarnings("exports") JSONObject object) {
        return null;
    }

    @Override
    public OperationResult<Void> loadData() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadData'");
    }

    @Override
    public OperationResult<Void> linkData() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'linkData'");
    }
}
