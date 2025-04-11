package com.musicapp;

import java.io.IOException;
import javafx.fxml.FXML;

public class SignUpController {

    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("login");
    }
}