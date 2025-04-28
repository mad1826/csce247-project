package com.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateEditSongController implements Initializable {

    @FXML
    private VBox mainContainer;
    
    @FXML
    private TextField txtSongName;
    
    @FXML
    private HBox starContainer;
    
    @FXML
    private ImageView star1;
    
    @FXML
    private ImageView star2;
    
    @FXML
    private ImageView star3;
    
    @FXML
    private ComboBox<String> pitchDropdown;
    
    @FXML
    private ComboBox<String> noteValueDropdown;
    
    @FXML
    private Button btnAddChord;
    
    @FXML
    private Button btnAddNote;
    
    @FXML
    private Button btnAddMeasure;
    
    @FXML
    private Button btnSave;
    
    // Track the current difficulty level (1-3)
    private int difficultyLevel = 0;
    
    private Image grayStar;
    private Image goldStar;
    
    private boolean editMode = false;
    private String currentSongId = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        grayStar = new Image(getClass().getResourceAsStream("/com/musicapp/images/star_gray.png"));
        goldStar = new Image(getClass().getResourceAsStream("/com/musicapp/images/star_gold.png"));
        
        ObservableList<String> pitchOptions = FXCollections.observableArrayList(
            "A", "B", "C", "D", "E", "F", "G", "R"
        );
        pitchDropdown.setItems(pitchOptions);
        pitchDropdown.setValue("A");
        
        ObservableList<String> noteValueOptions = FXCollections.observableArrayList(
            "Whole", "Half", "Quarter", "Eighth", "Sixteenth"
        );
        noteValueDropdown.setItems(noteValueOptions);
        noteValueDropdown.setValue("Sixteenth");
    }

    @FXML
    private void handleStarClick(MouseEvent event) {
        ImageView clickedStar = (ImageView) event.getSource();
        
        // Determine which star was clicked
        int starNumber = 0;
        if (clickedStar == star1) {
            starNumber = 1;
        } else if (clickedStar == star2) {
            starNumber = 2;
        } else if (clickedStar == star3) {
            starNumber = 3;
        }
        
        updateStars(starNumber);
    }
    
    private void updateStars(int selectedLevel) {
        difficultyLevel = selectedLevel;
        
        // Update star images
        star1.setImage(selectedLevel >= 1 ? goldStar : grayStar);
        star2.setImage(selectedLevel >= 2 ? goldStar : grayStar);
        star3.setImage(selectedLevel >= 3 ? goldStar : grayStar);
    }

    @FXML
    private void handleAddChord(ActionEvent event) {
        String selectedPitch = pitchDropdown.getValue();
        System.out.println("Adding chord with pitch: " + selectedPitch);
    }

    @FXML
    private void handleAddNote(ActionEvent event) {
        String selectedPitch = pitchDropdown.getValue();
        String selectedNoteValue = noteValueDropdown.getValue();
        System.out.println("Adding note with pitch: " + selectedPitch + " and value: " + selectedNoteValue);
    }

    @FXML
    private void handleAddMeasure(ActionEvent event) {
        System.out.println("Adding new measure");
    }

    @FXML
    private void handleSave(ActionEvent event) {
        String songName = txtSongName.getText().trim();
        
        if (songName.isEmpty()) {
            System.out.println("Song name cannot be empty");
            return;
        }
        
        System.out.println("Saving song: " + songName);
        System.out.println("Difficulty level: " + difficultyLevel);
        
        if (editMode && currentSongId != null) {
        } else {
        }
        
        // Navigate back to songs list or clear form
        clearForm();

    }
    
    public void setupForEdit(String songId, String songName, int difficulty) {
        // Set controller to edit mode
        this.editMode = true;
        this.currentSongId = songId;
        
        // Populate form with existing song data
        txtSongName.setText(songName);
        updateStars(difficulty);
        
        // Load other song details as needed
    }
    
    public void setupForCreate() {
        // Set controller to create mode
        this.editMode = false;
        this.currentSongId = null;
        
        // Clear form
        clearForm();
    }
    
    private void clearForm() {
        txtSongName.clear();
        updateStars(0);
        pitchDropdown.setValue("A");
        noteValueDropdown.setValue("Sixteenth");
    }
}