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
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * A base controller class for any pages with a usable nav bar, i.e., all pages after authentication.
 */
public class NavigatableController implements Initializable {
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

	private String currentTab;

	@FXML
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		setCurrentTab("home");
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
		currentTab = tab;
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
		if (tab.equals(currentTab))  // Prevent unnecessary logic if navigating to the current tab
			return;
	
		setCurrentTab(tab);
		// Remove old tab
		VBox vboxMain = (VBox)App.getNodeById("vboxMain");
		VBox content = (VBox)App.getNodeById("content");
		vboxMain.getChildren().removeAll(content, footer);
		// Add new tab
		try {
			Parent newTab = App.loadFXML(tab);
			vboxMain.getChildren().add(newTab);
		}
		catch (Exception e) {
			System.err.println("Error loading file for tab \"" + tab + "\":");
			e.printStackTrace();
		}
		finally {
			vboxMain.getChildren().add(footer);
		}
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
			}
		});
	}
}