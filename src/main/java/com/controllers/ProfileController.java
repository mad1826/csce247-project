package com.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.model.MusicAppFacade;
import com.model.User;
import com.musicapp.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ProfileController implements Initializable {

    @FXML
    private Label usernameLabel;

    @FXML
    private ImageView profileImage;

    @FXML
    private Slider sliderMetronomeSpeed;

    @FXML
    private Button btnChangePassword;

    @FXML
    private Button btnDeleteAccount;

    @FXML
    private Label statusMessage;

    private MusicAppFacade facade;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        facade = MusicAppFacade.getInstance();
        User currentUser = facade.getCurrentUser();

		App.setMainLabel(currentUser.getFirstName() + " " + currentUser.getLastName());

        // Default metronome speed value
        sliderMetronomeSpeed.setValue(1.0);

        // Load profile image from resources
        Image profile = new Image(getClass().getResourceAsStream("/com/musicapp/images/profile-picture.png"));

        profileImage.setImage(profile);

		double arcLength = profileImage.getFitWidth() * 2 / 3;
		Rectangle clip = new Rectangle(profileImage.getFitWidth() * 0.6, profileImage.getFitHeight());
		clip.setArcWidth(arcLength);
		clip.setArcHeight(arcLength);
		profileImage.setClip(clip);

		// snapshot the rounded image.
		SnapshotParameters parameters = new SnapshotParameters();
		parameters.setFill(Color.TRANSPARENT);
		WritableImage image = profileImage.snapshot(parameters, null);

		profileImage.setClip(null);

		// store the rounded image in the imageView.
		profileImage.setImage(image);
    }


    @FXML
    void handleResetPassword(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Reset Password");
        dialog.setHeaderText("Enter a new password:");
        dialog.setContentText("New Password:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newPassword -> {
            if (newPassword.length() >= 7 && newPassword.matches(".*\\d.*") && newPassword.matches(".*\\W.*")) {
                facade.resetPassword(newPassword);
                statusMessage.setText("Password changed successfully.");
            } else {
                statusMessage.setText("Password must be at least 7 characters with 1 number and 1 symbol.");
            }
        });
    }

    @FXML
    void handleDeleteAccount(ActionEvent event) throws IOException {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete Account");
        confirm.setHeaderText("Are you sure?");
        confirm.setContentText("This will permanently delete your account.");

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // facade.deleteCurrentUser(); // Uncomment if method is implemented
            App.setRoot("login");
        }
    }

    @FXML
    void handleSetMetronomeSpeed(ActionEvent event) {
        double speed = sliderMetronomeSpeed.getValue();
        User currentUser = facade.getCurrentUser();

        if (currentUser != null && currentUser.setMetronomeSpeed(speed)) {
            statusMessage.setText(String.format("Metronome speed set to %.2fx", speed));
        } else {
            statusMessage.setText("Failed to update metronome speed.");
        }
    }
}

