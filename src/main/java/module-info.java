module com.musicapp {
    requires javafx.controls;
    requires javafx.fxml;
	requires json.simple;

    opens com.musicapp to javafx.fxml;
    exports com.musicapp;
	
    opens com.model to javafx.fxml;
    exports com.model;
}
