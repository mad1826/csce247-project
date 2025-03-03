package com.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.json.simple.JSONArray;

/**
 * manages user creation, deletion, and retrieval
 * implements singleton pattern to ensure only one instance exists
 * @author Makyia Irick
 */
public class UserManager implements  SavableList<User> {
    private static UserManager userManager; //singleton instance
    private ArrayList<User> users; //list of users
    private String filePath; //file path for saving user data

    /**
     * private constructor to create the UserManager instance
     * only called internally by getInstance method
     * @param - the list of users to manage
     * @param - the file path for saving user data
     */
    private UserManager(ArrayList<User> users, String filePath) {
        this.users = users;
        this.filePath = filePath;
    }

    /**
     * returns the singleton instance of UserManager
     * if the instance doesn't exist, it creates one
     * @param - the list of users to manage
     * @param - the file path for saving user data
     * @return - the singleton instance of UserManager
     */
    public static UserManager getInstance(ArrayList<User> users, String filePath) {
        if (userManager == null) {
            userManager = new UserManager(users, filePath);
        }
        return userManager;
    }

    /**
     * creates a new user and adds it to the list of users
     * @param - the first name of the user
     * @param - the last name of the user
     * @param - the email address of the user
     * @param - the password of the user
     * @return - the newly created user object
     */
    public User createUser(String firstName, String lastName, String emailAddress, String password) {
        User newUser = new User(firstName, lastName, emailAddress, password);
        users.add(newUser);
        return newUser;
    }

    /**
     * deletes a user from the list by matching the user ID
     * @param - the unique ID of the user to delete
     * @return - true if the user was successfully deleted, false otherwise
     */
    public boolean deleteUser(UUID userID) {
        for (User user : users) {
            if (user.getId().equals(userID)) { //direct attribute access
                users.remove(user);
                return true;
            }
        }
        return false;
    }

    /**
     * retrieve a user by matching their email address and password
     * @param - the email address of the user
     * @param - the password of the user
     * @return - the matching user object, or null if no user is found 
     */
    public User getUser(String emailAddress, String password) {
        for (User user : users) {
            if(user.getEmailAddress().equals(emailAddress) && user.isAuthorized(password)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Returns the json file path this list is stored at.
     * @return filePath
     */
	@Override
	public String getFilePath() {
		return filePath;
	}

    /**
     * converts all users into a JSON string
     * @return - the users represented as a JSON string
     */
    public String toJSON() {
		JSONArray jsonUsers = new JSONArray();
		for (User user : users) {
			jsonUsers.add(user.toJSON());
		}
		return jsonUsers.toJSONString();
    }

    /**
     * converts a JSON string into a HashMap of users
     * this method is a placeholder for future JSON parsing functionality
     * @param - the JSON string containing user data
     * @return - a HashMap of user IDs mapped to User objects
     */
    public HashMap<UUID, User> toObjects(String json) {
        return new HashMap<>();
    }
    
}