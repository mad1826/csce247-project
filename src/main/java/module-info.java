module com.musicapp {
    requires javafx.controls;
    requires javafx.fxml;
	requires jfugue;
	requires json.simple;

    opens com.musicapp to javafx.fxml;
    exports com.musicapp;

	opens com.controllers to javafx.fxml;
    exports com.controllers;
	
    opens com.model to javafx.fxml;
    exports com.model;
}
