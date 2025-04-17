package com.controllers;

import java.net.URL;
import java.time.Duration;
import java.util.ResourceBundle;
import java.util.UUID;

import com.model.Course;
import com.model.MusicAppFacade;
import com.model.OperationResult;
import com.model.User;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CourseController extends NavigatableController {
    @FXML
    private Label welcome;
    
	@FXML
	private Button joinCourse;

    @FXML
	private VBox listCourses;

	private MusicAppFacade facade;

	private User user;

	private void promptJoinCourse() {
		Stage popup = new Stage();
		popup.initModality(Modality.APPLICATION_MODAL); // blocks input to other windows
		popup.setTitle("Join a Course");

		VBox mainContainer = new VBox();
		HBox lowerOptions = new HBox();

		TextField input = new TextField();
		input.setPromptText("Course ID");

		Label feedback = new Label("");

		Button closeButton = new Button("Cancel");
		closeButton.setOnAction(e -> {
			System.out.println("CLOSE?");
			popup.close();
		});

		Button joinButton = new Button("Join");
		joinButton.setOnAction(e -> {
			String text = input.getText();
			OperationResult<Course> or = facade.joinCourse(text);
			System.out.println(or);
			if (or.success) {
				popup.close();

				loadCourses(); //redisplay courses
			} else { //display issue
				feedback.setText(or.message);

				PauseTransition delay = new PauseTransition(javafx.util.Duration.seconds(3));
				delay.setOnFinished(event -> feedback.setText(""));
				delay.play();
			}
		});

		lowerOptions.getChildren().addAll(closeButton,joinButton);

		mainContainer.getChildren().addAll(input,lowerOptions,feedback);

		Scene popupScene = new Scene(mainContainer, 200, 100);

		popup.setScene(popupScene);
		popup.showAndWait(); // blocks until popup is closed
	}

    @FXML
	@Override
    public void initialize(URL url, ResourceBundle rb)  {
		super.initialize(url, rb);
		setCurrentTab("courses");
		facade = MusicAppFacade.getInstance();
		user = facade.getCurrentUser();
        welcome.setText("My Courses");

		joinCourse.setOnAction(e -> promptJoinCourse());

		loadCourses();
    }

	/**
	 * Loads all lessons for the user's courses.
	 */
	private void loadCourses() {
		listCourses.getChildren().clear();
		for (Course course : user.getCourses()) {
			listCourses.getChildren().add(getLessonVBox(course));
		}
	}

	/**
	 * Converts a Lesson object into a container that lists its children horizontally, including the progress for student accounts.
	 * @param lesson a Lesson object
	 * @return a container that lists its children horizontally
	 */
	private VBox getLessonVBox(Course course) {
		VBox courseContent = new VBox();
        courseContent.getStyleClass().add("home-course");

		Label courseName = new Label();
        courseName.getStyleClass().add("home-coursetitle");
		courseName.setText(course.getTitle());

        Label courseDesc = new Label();
        courseDesc.getStyleClass().add("home-coursedesc");
        courseDesc.setWrapText(true);
        courseDesc.setText("A very long and potentially multiline description here which is ever so unfurtunatley unable to be implemented as we did not include this in course object.");

		courseContent.getChildren().add(courseName);
        courseContent.getChildren().add(courseDesc);

		courseContent.setOnMouseClicked(e -> {
            System.out.println("Select "+course.toString());
        });

		return courseContent;
	}
}