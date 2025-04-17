package com.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.model.MusicAppFacade;
import com.model.User;
import com.musicapp.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LoginController implements Initializable {

	@FXML
	private VBox vboxMain;

	@FXML
	private VBox content;

	@FXML
	private HBox footer;

    @FXML
    private Label labelMain;

    @FXML
    private Button btnlogin;

    @FXML
    private Label output;

    @FXML
    private Button swapAuthMethod;

    @FXML
    private TextField textPassword;

    @FXML
    private TextField textUsername;

    @FXML
	@Override
    public void initialize(URL url, ResourceBundle rb)  {
		labelMain.setText("Log in\nto continue your journey"); // Line break not supported directly in fxml
	}

    @FXML
    void login(ActionEvent event) throws IOException {
		loginWithCredentials(textUsername.getText(), textPassword.getText());
    }

	@FXML
	void continueWithGoogle(ActionEvent event) throws IOException {
		// Simulate getting credentials from device's locally saved Google account
		loginWithCredentials("jane.smith@example.com", "secureP@ss987");
	}

    @FXML
    void switchToSignUp() throws IOException {
		labelMain.setText("Sign up\nto start your journey");
        Parent signUp = App.loadFXML("signup");
		content.getChildren().setAll(signUp);
		Parent signUpFooter = App.loadFXML("signupfooter");
		footer.getChildren().setAll(signUpFooter);
    }

	/**
	 * Logs in with the specified email and password.
	 * @param email the user's email
	 * @param password the user's password
	 * @throws IOException thrown if the root file is invalid
	 */
	private void loginWithCredentials(String email, String password) throws IOException {
		MusicAppFacade facade = MusicAppFacade.getInstance();
		facade.login(email, password);
		User user = facade.getCurrentUser();
		if (user == null) {
			output.setText("No account found with that username and password.");
		}
		else {
			App.setRoot("home");
		}
	}
}