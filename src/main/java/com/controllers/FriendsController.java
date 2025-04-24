package com.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.model.MusicAppFacade;
import com.model.OperationResult;
import com.model.User;
import com.musicapp.App;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FriendsController implements Initializable {
    
	@FXML
	private Button addFriend;

    @FXML
	private ListView<User> friendsList;

	private final ObservableList<User> observableList = FXCollections.observableArrayList();

	private MusicAppFacade facade;

	private User user;

	private void promptAddFriend() {
		Stage popup = new Stage();
		popup.initModality(Modality.APPLICATION_MODAL); // blocks input to other windows
		popup.setTitle("Add a Friend");
		VBox mainContainer = new VBox();
		HBox lowerOptions = new HBox();

		TextField input = new TextField();
		input.setPromptText("Email");

		Label feedback = new Label("");

		Button closeButton = new Button("Cancel");
		closeButton.setOnAction(e -> {
			System.out.println("CLOSE?");
			popup.close();
		});

		Button joinButton = new Button("Join");
		joinButton.setOnAction(e -> {
			String text = input.getText();
            
            OperationResult<?> or = facade.addFriend(text);

			if (or.success) {
				popup.close();

				loadFriends(); //redisplay courses
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
		facade = MusicAppFacade.getInstance();
		user = facade.getCurrentUser();
		App.setMainLabel("My Friends");

		addFriend.setOnAction(e -> promptAddFriend());

		loadFriends();
    }

	/**
	 * Loads all lessons for the user's courses.
	 */
	private void loadFriends() {
		for (User u : user.getFriends().values()) {
			observableList.add(u);
		}

		friendsList.setCellFactory((ListView<User> list) -> {
			ListCell<User> cell = new ListCell<User>() {
				@Override
				protected void updateItem(User lesson, boolean empty) {
					super.updateItem(lesson, empty);
					
					if (empty || lesson == null) {
						setText(null);
						setGraphic(null);
					}
					else {
						setText(user.getFirstName() + " " + user.getLastName());
						ImageView imageView = new ImageView(App.class.getResource("images/default-avatar.png").toString());
						imageView.setFitHeight(100);
						imageView.setFitWidth(100);
						setGraphic(imageView);
					}
				}
			};
			
			return cell;
		});

		friendsList.setItems(observableList);
	}

	/**
	 * Converts a Lesson object into a container that lists its children horizontally, including the progress for student accounts.
	 * @param lesson a Lesson object
	 * @return a container that lists its children horizontally
	 */
	private VBox makeFriendContainer(User friend) {
		VBox courseContent = new VBox();
        courseContent.getStyleClass().add("home-course");

		Label courseName = new Label();
        courseName.getStyleClass().add("home-coursetitle");
		courseName.setText(friend.getFirstName()+" "+friend.getLastName());

        // Label courseDesc = new Label();
        // courseDesc.getStyleClass().add("home-coursedesc");
        // courseDesc.setWrapText(true);
        // courseDesc.setText("A very long and potentially multiline description here which is ever so unfurtunatley unable to be implemented as we did not include this in course object.");

		courseContent.getChildren().add(courseName);
        // courseContent.getChildren().add(courseDesc);

		// courseContent.setOnMouseClicked(e -> {
        //     System.out.println("Select "+course.toString());
        // });

		return courseContent;
	}
}