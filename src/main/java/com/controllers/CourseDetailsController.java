package com.controllers;

import com.model.Course;
import com.model.Lesson;
import com.model.MusicAppFacade;
import com.model.Student;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class CourseDetailsController implements Initializable {
    @FXML private Label courseTitleLabel;
    @FXML private VBox lessonsContainer;
    @FXML private Button leaveCourseButton;
    @FXML private Button backButton;

    private final MusicAppFacade facade = MusicAppFacade.getInstance();
    private Course course;
    private Student student;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        course = facade.getCurrentCourse();
        student = facade.getCurrentStudent();
        if (course != null) {
            courseTitleLabel.setText(course.getTitle());
            populateLessons();
        }
        leaveCourseButton.setOnAction(e -> {
            if (course != null) {
                facade.leaveCourse(course.getCode());
                // Optionally navigate back to courses list
            }
        });
        backButton.setOnAction(e -> {
            System.out.println("Back button clicked!");
            try {
                javafx.scene.layout.VBox vboxMain = (javafx.scene.layout.VBox) com.musicapp.App.getNodeById("vboxMain");
                javafx.scene.layout.VBox content = (javafx.scene.layout.VBox) com.musicapp.App.getNodeById("content");
                vboxMain.getChildren().remove(content);
                javafx.scene.Parent courses = com.musicapp.App.loadFXML("courses");
                vboxMain.getChildren().add(1, courses); // Add at index 1 to keep nav bar at the bottom
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private void populateLessons() {
        lessonsContainer.getChildren().clear();
        for (Lesson lesson : course.getLessons().values()) {
            HBox lessonCard = new HBox(16);
            lessonCard.setStyle("-fx-background-color: #fafbfc; -fx-border-color: #e0e0e0; -fx-border-radius: 8; -fx-background-radius: 8; -fx-padding: 12 16 12 16; -fx-alignment: CENTER_LEFT;");

            double progress = 0.0;
            if (student != null && lesson.getNumberOfTimes() > 0) {
                progress = 1.0 * student.getLessonProgress(lesson) / lesson.getNumberOfTimes();
            }
            ProgressIndicator pi = new ProgressIndicator(progress);
            pi.setPrefSize(40, 40);

            VBox lessonInfo = new VBox(2);
            Label lessonTitle = new Label(lesson.getTitle());
            lessonTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
            Label dueDate = new Label("Due 1/2/3"); // Placeholder, replace with real due date if available
            dueDate.setStyle("-fx-font-size: 12px; -fx-text-fill: #888;");
            lessonInfo.getChildren().addAll(lessonTitle, dueDate);

            lessonCard.getChildren().addAll(pi, lessonInfo);
            lessonsContainer.getChildren().add(lessonCard);
        }
    }
} 