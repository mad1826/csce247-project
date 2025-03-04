package com.model;

import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * manages user creation, deletion, and retrieval
 * implements singleton pattern to ensure only one instance exists
 * @author Makyia Irick
 */
public class UserManager implements  SavableList<User> {
    private static UserManager userManager; //singleton instance
    private ArrayList<User> users; //list of users
    final static String filePath = "src/main/java/com/data/users.json"; //file path for saving user data

    /**
     * private constructor to create the UserManager instance
     * only called internally by getInstance method
     * @param - the list of users to manage
     * @param - the file path for saving user data
     */
    private UserManager() {
        this.users = new ArrayList<>();
    }

    /**
     * returns the singleton instance of UserManager
     * if the instance doesn't exist, it creates one
     * @param - the list of users to manage
     * @param - the file path for saving user data
     * @return - the singleton instance of UserManager
     */
    public static UserManager getInstance() {
        if (userManager == null) {
            userManager = new UserManager();
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
     * converts all users into a JSON array
     * @return - the users represented as a JSON array
     */
	@Override
    public JSONArray toJSON() {
		JSONArray jsonUsers = new JSONArray();
		for (User user : users) {
			jsonUsers.add(user.toJSON());
		}
		return jsonUsers;
    }

    /**
     * converts a user JSON Object into a user
     * @param - the JSON object containing user data
     * @return a User instance
     */

    @Override
    public User toObject(JSONObject object) {
        UUID id = UUID.fromString((String) object.get("id"));
        String fn = (String) object.get("firstName");
        String ln = (String) object.get("lastName");
        String em = (String) object.get("emailAddress");
        String pw = (String) object.get("password");
        double spd = ((Double) object.get("metronomeSpeedModifier"));

        User u = new User(id,fn,ln,em,pw,spd);

        // userDetails.put("id", id.toString());
        // userDetails.put("firstName", firstName);
        // userDetails.put("lastName", lastName);
        // userDetails.put("emailAddress", emailAddress);
        // userDetails.put("password", password);
        // userDetails.put("metronomeSpeedModifier", metronomeSpeedModifier);

        return u;
    }
    
}