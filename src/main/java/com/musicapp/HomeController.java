package com.musicapp;

import java.net.URL;
import java.util.ResourceBundle;

import com.model.Course;
import com.model.Lesson;
import com.model.MusicAppFacade;
import com.model.Student;
import com.model.User;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class HomeController extends NavigatableController {

    @FXML
    private Label welcome;

	@FXML
	private VBox listLessons;

	private MusicAppFacade facade;

	private User user;

    @FXML
    public void initialize(URL url, ResourceBundle rb)  {
		super.initialize(url, rb);
		setCurrentTab("home");
		facade = MusicAppFacade.getInstance();
		user = facade.getCurrentUser();

		welcome.setText("Welcome back, " + user.getFirstName() + "!");
		loadLessons();
    }

	/**
	 * Loads all lessons for the user's courses.
	 */
	private void loadLessons() {
		for (Course course : user.getCourses()) {
			for (Lesson lesson : course.getLessons().values()) {
				listLessons.getChildren().add(getLessonHBox(lesson));
			}
		}
	}

	/**
	 * Converts a Lesson object into a container that lists its children horizontally, including the progress for student accounts.
	 * @param lesson a Lesson object
	 * @return a container that lists its children horizontally
	 */
	private HBox getLessonHBox(Lesson lesson) {
		HBox hbox = new HBox();
		hbox.getStyleClass().add("home-lesson");
		Student student = facade.getCurrentStudent();

		if (student != null) {
			ProgressIndicator pi = new ProgressIndicator(1.0 * student.getLessonProgress(lesson) / lesson.getNumberOfTimes());
			pi.getStyleClass().add("lesson-progress");
			hbox.getChildren().add(pi);
		}

		VBox labelContainer = new VBox();
		Label lessonLabel = new Label();
		lessonLabel.setText(lesson.getTitle());
		labelContainer.setAlignment(Pos.CENTER_LEFT);
		labelContainer.getChildren().add(lessonLabel);

		hbox.getChildren().add(labelContainer);
		return hbox;
	}
}