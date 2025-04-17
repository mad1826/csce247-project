package com.controllers;

import java.io.IOException;
import com.musicapp.App;

import javafx.fxml.FXML;

public class SignUpFooterController {
    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("login");
    }
}