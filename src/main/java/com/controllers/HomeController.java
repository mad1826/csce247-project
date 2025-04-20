package com.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.model.Course;
import com.model.Lesson;
import com.model.MusicAppFacade;
import com.model.Student;
import com.model.User;
import com.musicapp.App;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;

public class HomeController implements Initializable {

	@FXML
	private ListView<Lesson> listLessons;

	private MusicAppFacade facade;

	private User user;

	private final ObservableList<Lesson> lessons = FXCollections.observableArrayList();

    @FXML
	@Override
    public void initialize(URL url, ResourceBundle rb) {
		facade = MusicAppFacade.getInstance();
		user = facade.getCurrentUser();

		Label labelMain = (Label)App.getNodeById("labelMain");
		labelMain.setText("Welcome back, " + user.getFirstName() + "!");
		loadLessons();
    }

	/**
	 * Loads all lessons for the user's courses.
	 */
	private void loadLessons() {
		for (Course course : user.getCourses()) {
			for (Lesson lesson : course.getLessons().values()) {
				lessons.add(lesson);
			}
		}
		listLessons.setCellFactory((ListView<Lesson> list) -> {
			ListCell<Lesson> cell = new ListCell<Lesson>() {
				@Override
				public void updateItem(Lesson lesson, boolean empty) {
					super.updateItem(lesson, empty);
					
					if (lesson == null)
						return;
					
					getStyleClass().add("home-lesson");
					
					setText(lesson.getTitle());
					
					Student student = facade.getCurrentStudent();
					if (student != null) {
						ProgressIndicator pi = new ProgressIndicator(1.0 * student.getLessonProgress(lesson) / lesson.getNumberOfTimes());
						pi.getStyleClass().add("lesson-progress");
						setGraphic(pi);
					}
				}
			};
			
			return cell;
		});

		listLessons.setItems(lessons);

		listLessons.setOnMouseClicked((MouseEvent event) -> {
			Lesson lesson = listLessons.getSelectionModel().getSelectedItem();
			System.out.println("Selected lesson " + lesson.getTitle());
			facade.setCurrentLesson(lesson);
		});
	}
}