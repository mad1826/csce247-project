package com.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.model.Course;
import com.model.MusicAppFacade;
import com.model.OperationResult;
import com.model.User;
import com.musicapp.App;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class CourseController implements Initializable {
    
	@FXML
	private TextField courseCode;
    
	@FXML
	private Button joinCourse;

	@FXML
	private Label joinCourseOutput;

    @FXML
	private VBox listCourses;

	private MusicAppFacade facade;

	private User user;

    @FXML
	@Override
    public void initialize(URL url, ResourceBundle rb)  {
		facade = MusicAppFacade.getInstance();
		user = facade.getCurrentUser();
        App.setMainLabel("My Courses");

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

	@FXML
	private void joinCourse() {
		String code = courseCode.getText();
		OperationResult<Course> result = facade.joinCourse(code);
		if (result.success) {
			joinCourseOutput.setText("");
			loadCourses();
		}
		else {
			joinCourseOutput.setText(result.message);
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