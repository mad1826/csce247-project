package com.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.UUID;

import com.model.MusicAppFacade;
import com.model.Song;
import com.musicapp.App;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class SongDetailsController extends NavigatableController {
    @FXML
    private TextField searchField;
    
    @FXML
    private VBox songsContainer;
    
    @FXML
    private Button createSongButton;
    
    @FXML
    private Button editSongButton;

    private MusicAppFacade facade;
    private HashMap<UUID, Song> allSongs;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
		facade = MusicAppFacade.getInstance();
		App.setMainLabel("Songs");
        
        setupSearchField();
        loadAllSongs();
        setupButtons();
    }

    private void setupSearchField() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.trim().isEmpty()) {
                displaySongs(allSongs);
            } else {
                HashMap<UUID, Song> filteredSongs = facade.searchSongs(newValue);
                displaySongs(filteredSongs);
            }
        });
    }

    private void loadAllSongs() {
        displaySongs(facade.getSongs());
    }

    private void displaySongs(HashMap<UUID, Song> songs) {
        songsContainer.getChildren().clear();
        if (songs.isEmpty()) {
            Label noSongs = new Label("No songs found");
            noSongs.setStyle("-fx-font-size: 16px; -fx-text-fill: #666666;");
            songsContainer.getChildren().add(noSongs);
        } else {
            for (Song song : songs.values()) {
                songsContainer.getChildren().add(createSongCard(song));
            }
        }
    }

    private VBox createSongCard(Song song) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: white; -fx-padding: 25; -fx-background-radius: 12; -fx-border-radius: 12; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.1), 10, 0, 0, 0);");
        card.setMinHeight(100);
        card.setPrefWidth(600);
        
        Label title = new Label(song.getTitle());
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;");
        title.setWrapText(true);
        
        Label artist = new Label(song.getArtist());
        artist.setStyle("-fx-text-fill: #666666; -fx-font-size: 16px;");
        artist.setWrapText(true);
        
        String instrumentList = String.join(", ", song.getInstruments());
        Label details = new Label(song.getNumberOfSheets() + " Sheets: " + instrumentList);
        details.setStyle("-fx-text-fill: #666666; -fx-font-size: 16px;");
        details.setWrapText(true);
        
        card.getChildren().addAll(title, artist, details);
        
        card.setOnMouseEntered(e -> card.setStyle("-fx-background-color: #f8f9fa; -fx-padding: 25; -fx-background-radius: 12; -fx-border-radius: 12; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.15), 10, 0, 0, 0);"));
        card.setOnMouseExited(e -> card.setStyle("-fx-background-color: white; -fx-padding: 25; -fx-background-radius: 12; -fx-border-radius: 12; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.1), 10, 0, 0, 0);"));
        
        card.setOnMouseClicked(event -> openSongDetails(song));
        
        return card;
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
            facade.setCurrentSong(song);
            App.setRoot("song-view");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
} 