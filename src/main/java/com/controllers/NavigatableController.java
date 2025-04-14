package com.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.musicapp.App;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * A base controller class for any pages with a usable nav bar, i.e., all pages after authentication.
 */
public abstract class NavigatableController implements Initializable {
	@FXML
	private HBox footer;

	@FXML
	private VBox tabHome;

	@FXML
	private VBox tabCourses;

	@FXML
	private VBox tabSongs;

	@FXML
	private VBox tabProfile;

	@FXML
	private VBox tabFriends;

	@FXML
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		addOnClickEvent(tabHome, "home");
		addOnClickEvent(tabCourses, "courses");
		addOnClickEvent(tabSongs, "songs");
		addOnClickEvent(tabProfile, "profile");
		addOnClickEvent(tabFriends, "friends");
	}

	/**
	 * Sets the highlighted tab indicating the current tab the user is on.
	 * @param tab the new tab
	 */
	protected void setCurrentTab(String tab) {
		ObservableList<Node> navTabs = footer.getChildren();
		for (Node navTab : navTabs) {
			if (navTab.getId().toLowerCase().contains(tab)) {
				navTab.getStyleClass().add("current-tab");
			}
			else {
				navTab.getStyleClass().remove("current-tab");
			}
		}
	}

	/**
	 * Sets the current tab and goes to that tab's page.
	 * @param tab the new tab
	 * @throws IOException thrown if the root file is invalid
	 */
	private void setTab(String tab) throws IOException {
		setCurrentTab(tab);
		App.setRoot(tab);
	}

	/**
	 * Add an event listener that switches to a tab once the corresponding node is clicked.
	 * @param node the node to listen for the onMouseClicked event
	 * @param tab the tab to set the app to
	 */
	private void addOnClickEvent(Node node, String tab) {
		node.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				try {
					setTab(tab);
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}