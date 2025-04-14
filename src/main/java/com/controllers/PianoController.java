
package com.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.musicapp.*;

public class PianoController {

    @FXML
    void playC(ActionEvent event) {
        Music.playNote("C");
    }
        
    @FXML
    void playD(ActionEvent event) {
        Music.playNote("D");
    }

    @FXML
    void playE(ActionEvent event) {
        Music.playNote("E");
    }
}
