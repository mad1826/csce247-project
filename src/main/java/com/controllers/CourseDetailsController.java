package com.controllers;

import com.model.Course;
import com.model.Lesson;
import com.model.MusicAppFacade;
import com.model.Student;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
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
                // Navigate back to courses list
                try {
                    com.musicapp.App.setRoot("courses");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        backButton.setOnAction(e -> {
            System.out.println("Back button clicked!");
            try {
                // Direct navigation back to courses with the entire nav layout
                com.musicapp.App.setRoot("login");
                
                // Then follow the pattern from BaseAuthController.loadHome()
                MusicAppFacade facade = MusicAppFacade.getInstance();
                // Make sure user is logged in
                if (facade.getCurrentUser() == null) {
                    facade.login("jane.smith@example.com", "secureP@ss987");
                }
                
                // rebuild the main app structure
                VBox vboxMain = (VBox)com.musicapp.App.getNodeById("vboxMain");
                if (vboxMain != null) {
                    // Clear any footer from the login screen if needed
                    HBox loginFooter = (HBox)com.musicapp.App.getNodeById("footer");
                    if (loginFooter != null) {
                        vboxMain.getChildren().remove(loginFooter);
                    }
                    
                    // Add navigation
                    Parent nav = com.musicapp.App.loadFXML("nav");
                    vboxMain.getChildren().add(nav);
                    
                    // Replace content with courses
                    VBox content = (VBox)com.musicapp.App.getNodeById("content");
                    if (content != null) {
                        content.getChildren().setAll(com.musicapp.App.loadFXML("courses"));
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                
                try {
                    com.musicapp.App.setRoot("login");
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
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
            Label dueDate = new Label("Due 1/2/3"); 
            dueDate.setStyle("-fx-font-size: 12px; -fx-text-fill: #888;");
            lessonInfo.getChildren().addAll(lessonTitle, dueDate);

            lessonCard.getChildren().addAll(pi, lessonInfo);
            lessonsContainer.getChildren().add(lessonCard);
        }
    }

    private void loadCoursesTab() throws IOException {
        VBox vboxMain = ((VBox)com.musicapp.App.getNodeById("vboxMain"));
        vboxMain.getChildren().remove((HBox)com.musicapp.App.getNodeById("footer"));
        Parent nav = com.musicapp.App.loadFXML("nav");
        vboxMain.getChildren().add(nav);
        Parent courses = com.musicapp.App.loadFXML("courses");
        ((VBox)com.musicapp.App.getNodeById("content")).getChildren().setAll(courses);
    }
} 