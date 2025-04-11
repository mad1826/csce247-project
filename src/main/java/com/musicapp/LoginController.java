package com.musicapp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.model.MusicAppFacade;
import com.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class LoginController implements Initializable {

	@FXML
	private VBox vboxMain;

    @FXML
    private Label labelLogin;

    @FXML
    private Button btnlogin;

    @FXML
    private Label output;

    @FXML
    private Button toSignUp;

    @FXML
    private TextField textPassword;

    @FXML
    private TextField textUsername;

    @FXML
    public void initialize(URL url, ResourceBundle rb)  {
		labelLogin.setText("Log in\nto continue your journey"); // Line break not supported directly in fxml
	}

    @FXML
    void login(ActionEvent event) throws IOException {
		MusicAppFacade facade = MusicAppFacade.getInstance();
		facade.login(textUsername.getText(), textPassword.getText());
		User user = facade.getCurrentUser();
		if (user == null) {
			output.setText("No account found with that username and password.");
		}
		else {
			App.setRoot("home");
		}
    }

    @FXML
    void switchToSignUp() throws IOException {
        App.setRoot("signup");
    }
}