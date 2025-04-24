package com.musicapp;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(@SuppressWarnings("exports") Stage stage) throws IOException {
        scene = new Scene(loadFXML("login"));
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    @SuppressWarnings("exports")
	public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

	@SuppressWarnings("exports")
	public static Node getNodeById(String fxId) {
		return scene.lookup("#" + fxId);
	}

	/**
	 * Updates the text of the app's main label.
	 * @param text the new label text
	 */
	public static void setMainLabel(String text) {
		((Label)getNodeById("labelMain")).setText(text);
	}

    public static void main(String[] args) {
        launch();
    }

}