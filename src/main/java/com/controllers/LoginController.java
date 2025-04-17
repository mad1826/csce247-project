package com.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.musicapp.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LoginController extends BaseAuthController implements Initializable {

	@FXML
	private VBox content;

	@FXML
	private HBox footer;

    @FXML
    private Label labelMain;

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
    void switchToSignUp() throws IOException {
		labelMain.setText("Sign up\nto start your journey");
        Parent signUp = App.loadFXML("signup");
		content.getChildren().setAll(signUp);
		Parent signUpFooter = App.loadFXML("signupfooter");
		footer.getChildren().setAll(signUpFooter);
    }

}