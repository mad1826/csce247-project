package com.musicapp;

import java.io.IOException;

import com.model.MusicAppFacade;
import com.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class PrimaryController {

    @FXML
    private Button btnlogin;

    @FXML
    private Label output;

    @FXML
    private Button primaryButton;

    @FXML
    private TextField textPassword;

    @FXML
    private TextField textUsername;

    @FXML
    void login(ActionEvent event) {
		MusicAppFacade facade = MusicAppFacade.getInstance();
		facade.login(textUsername.getText(), textPassword.getText());
		User user = facade.getCurrentUser();
		if (user == null) {
			output.setText("User not found");
		}
		else {
			output.setText("Signed in as " + user.getFirstName() + " " + user.getLastName());
		}
    }

    @FXML
    void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}