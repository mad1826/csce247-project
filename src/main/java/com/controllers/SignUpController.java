package com.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.model.MusicAppFacade;
import com.model.OperationResult;
import com.model.User;
import com.musicapp.App;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class SignUpController implements Initializable {

    @FXML
    private RadioButton radioStudent;

    @FXML
    private RadioButton radioTeacher;

    @FXML
    private TextField textFirst;

    @FXML
    private TextField textLast;

    @FXML
    private TextField textPassword;

    @FXML
    private TextField textEmail;

	@FXML
	private Label output;

	private final ToggleGroup group = new ToggleGroup();

    @FXML
	@Override
    public void initialize(URL url, ResourceBundle rb)  {
		radioStudent.setToggleGroup(group);
		radioStudent.setSelected(true);
		radioTeacher.setToggleGroup(group);
	}

	@FXML
	private void signUp() throws IOException {
		MusicAppFacade facade = MusicAppFacade.getInstance();
		boolean teacher = radioTeacher.isSelected();
		OperationResult<User> userResult = facade.signUp(textFirst.getText(), textLast.getText(), textEmail.getText(), textPassword.getText(), teacher);
		if (userResult.success) {
			App.setRoot("home");
		}
		else {
			output.setText(userResult.message);
		}
	}

	@FXML
	private void continueWithGoogle() {
		// TODO finish
		System.out.println("Continuing with Google for sign up!");
	}

    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("login");
    }
}