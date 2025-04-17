package com.controllers;

import java.io.IOException;

import com.model.MusicAppFacade;
import com.model.OperationResult;
import com.model.User;
import com.musicapp.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public abstract class BaseAuthController {

	@FXML
	protected void continueWithGoogle(ActionEvent event) throws IOException {
		// Simulate getting credentials from device's locally saved Google account
		loginWithCredentials("jane.smith@example.com", "secureP@ss987");
	}

	private void loadHome() throws IOException {
		VBox vboxMain = ((VBox)App.getNodeById("vboxMain"));
		vboxMain.getChildren().remove((HBox)App.getNodeById("footer"));
		Parent nav = App.loadFXML("nav");
		vboxMain.getChildren().add(nav);
		Parent home = App.loadFXML("home");
		((VBox)App.getNodeById("content")).getChildren().setAll(home);
	}
	
	/**
	 * Logs in with the specified email and password.
	 * @param email the user's email
	 * @param password the user's password
	 * @throws IOException thrown if the root file is invalid
	 */
	protected void loginWithCredentials(String email, String password) throws IOException {
		MusicAppFacade facade = MusicAppFacade.getInstance();
		facade.login(email, password);
		User user = facade.getCurrentUser();
		if (user == null) {
			((Label)App.getNodeById("output")).setText("No account found with that username and password.");
		}
		else {
			loadHome();
		}
	}

	protected void signUp(String firstName, String lastName, String email, String password, boolean teacher) throws IOException {
		OperationResult<User> userResult = MusicAppFacade.getInstance().signUp(firstName, lastName, email, password, teacher);
		if (userResult.success) {
			loadHome();
		}
		else {
			((Label)App.getNodeById("output")).setText(userResult.message);
		}
	}
}
