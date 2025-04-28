package com.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.UUID;

import com.model.MusicAppFacade;
import com.model.SheetMusic;
import com.model.Song;
import com.musicapp.App;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class SongDetailsController extends NavigatableController {

    private MusicAppFacade facade;

    private Song currentSong;

    @FXML
    private Label songNameLabel;

    @FXML
    private Label artistLabel;

    @FXML
    private Label sheetsLabel;

	@FXML
	private Button piano;

    // @FXML
    // private Label timeLabel;

    // @FXML
    // private Label pianoDifficultyLabel;

    // @FXML
    // private Label guitarDifficultyLabel;

    // @FXML
    // private Label vocalsDifficultyLabel;

    // @FXML
    // private Label bassDifficultyLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        facade = MusicAppFacade.getInstance();
        currentSong = facade.getCurrentSong();

        App.setMainLabel("Song Details");

        songNameLabel.setText(currentSong.getTitle());
        artistLabel.setText(currentSong.getArtist());
        sheetsLabel.setText(Integer.toString(currentSong.getNumberOfSheets())+" Sheets");
		
		piano.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				playPiano();
			}
		});
    }

	@FXML
	private void playPiano() {
		SheetMusic pianoSheet = currentSong.getSheet("piano");
		pianoSheet.play();
	}
}