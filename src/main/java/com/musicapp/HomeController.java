package com.musicapp;

import java.net.URL;
import java.util.ResourceBundle;

import com.model.MusicAppFacade;
import com.model.User;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class HomeController implements Initializable {

    @FXML
    private Label welcome;

	private MusicAppFacade facade;

	private User user;

    @FXML
    public void initialize(URL url, ResourceBundle rb)  {
		facade = MusicAppFacade.getInstance();
		user = facade.getCurrentUser();

		welcome.setText("Welcome " + user.getFirstName() + " " + user.getLastName() + "!");
    }
}