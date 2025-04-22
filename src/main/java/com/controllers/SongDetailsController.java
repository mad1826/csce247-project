package com.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.model.Song;
import com.model.OperationResult;
import com.model.managers.SongManager;
import com.musicapp.App;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SongDetailsController extends NavigatableController {
    @FXML
    private TextField searchField;
    
    @FXML
    private VBox recentlyPlayedContainer;
    
    @FXML
    private VBox popularSongsContainer;
    
    @FXML
    private Button createSongButton;
    
    @FXML
    private Button editSongButton;

    private SongManager songManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        songManager = SongManager.getInstance();
        OperationResult<Void> result = songManager.loadData();
        if (!result.success) {
            System.err.println("Failed to load songs: " + result.message);
        }
        setupSearchField();
        loadRecentlyPlayed();
        loadPopularSongs();
        setupButtons();
    }

    private void setupSearchField() {
        searchField.setPromptText("Song title, artist, genre, instrument, difficulty");
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Filter songs based on search text
            filterSongs(newValue);
        });
    }

    private void loadRecentlyPlayed() {
        // Clear existing content
        recentlyPlayedContainer.getChildren().clear();
        
        // Get recently played songs from SongManager
        ArrayList<Song> recentSongs = songManager.getRecentlyPlayed();
        if (recentSongs.isEmpty()) {
            recentlyPlayedContainer.getChildren().add(new Text("No recently played songs"));
        } else {
            for (Song song : recentSongs) {
                recentlyPlayedContainer.getChildren().add(createSongCard(song));
            }
        }
    }

    private void loadPopularSongs() {
        // Clear existing content
        popularSongsContainer.getChildren().clear();
        
        // Get popular songs from SongManager
        ArrayList<Song> popularSongs = songManager.getPopularSongs();
        if (popularSongs.isEmpty()) {
            popularSongsContainer.getChildren().add(new Text("No songs available"));
        } else {
            for (Song song : popularSongs) {
                popularSongsContainer.getChildren().add(createSongCard(song));
            }
        }
    }

    private VBox createSongCard(Song song) {
        VBox card = new VBox(5); // 5 is spacing between elements
        
        // Create song card elements
        ImageView albumArt = new ImageView();
        try {
            albumArt.setImage(new javafx.scene.image.Image(getClass().getResourceAsStream(song.getAlbumArtUrl())));
        } catch (Exception e) {
            // Fallback if image loading fails
            System.err.println("Failed to load album art: " + e.getMessage());
        }
        albumArt.setFitWidth(60);
        albumArt.setFitHeight(60);
        
        Text title = new Text(song.getTitle());
        Text artist = new Text(song.getArtist());
        Text details = new Text(song.getNumberOfSheets() + " Sheets: " + String.join(", ", song.getInstruments()));
        
        // Add elements to card
        card.getChildren().addAll(albumArt, title, artist, details);
        
        // Add click handler
        card.setOnMouseClicked(event -> openSongDetails(song));
        
        return card;
    }

    private void filterSongs(String searchText) {
        if (searchText == null || searchText.trim().isEmpty()) {
            loadRecentlyPlayed();
            loadPopularSongs();
            return;
        }

        // Filter songs based on search criteria
        popularSongsContainer.getChildren().clear();
        for (Song song : songManager.searchSongs(searchText)) {
            popularSongsContainer.getChildren().add(createSongCard(song));
        }
    }

    private void setupButtons() {
        createSongButton.setOnAction(event -> {
            try {
                App.setRoot("create-song");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        editSongButton.setOnAction(event -> {
            try {
                App.setRoot("edit-song");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void openSongDetails(Song song) {
        try {
            // Store selected song in manager for access in details view
            songManager.setSelectedSong(song);
            App.setRoot("song-view");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
} 