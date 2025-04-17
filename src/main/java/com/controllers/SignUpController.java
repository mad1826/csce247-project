package com.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class SignUpController extends BaseAuthController implements Initializable {

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
		boolean teacher = radioTeacher.isSelected();
		signUp(textFirst.getText(), textLast.getText(), textEmail.getText(), textPassword.getText(), teacher);
	}
}