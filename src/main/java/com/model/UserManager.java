package com.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.model.datahandlers.DataLoader;

/**
 * manages user creation, deletion, and retrieval
 * implements singleton pattern to ensure only one instance exists
 * @author Makyia Irick
 */
public class UserManager implements  SavableList<User> {
    private static UserManager userManager; //singleton instance
    private final HashMap<UUID,User> users; //list of users
    final static String FILE_PATH = "src/main/java/com/data/users.json"; //file path for saving user data

    /**
     * private constructor to create the UserManager instance
     * only called internally by getInstance method
     * @param - the list of users to manage
     * @param - the file path for saving user data
     */
    private UserManager() {
        this.users = new HashMap<>();
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
     * @return - the result of attempting to create the user
     */
    public OperationResult<User> createUser(String firstName, String lastName, String emailAddress, String password) {
        if (accountExists(emailAddress))
			return new OperationResult<>("An account with this email already exists.");

        User user = new User(firstName, lastName, emailAddress, password);
        users.put(user.getId(), user);
		// TODO save data
        
		OperationResult<User> or = new OperationResult<>(user);
		return or;
    }

    /**
     * deletes a user from the list by matching the user ID
     * @param - the unique ID of the user to delete
     * @return - the result of attempting to delete the user
     */
    public OperationResult<Void> deleteUser(UUID userID) {
        // TODO implement using users.get
        return new OperationResult<>("Not implemented");
    }

	/**
	 * Checks whether a user is already registered with a designated email address
	 * @param emailAddress - the email address to check all users for
	 * @return whether a user has already registered with the address
	 */
	public boolean accountExists(String emailAddress) {
		for (User user : users.values()) {
			if (user.getEmailAddress().equalsIgnoreCase(emailAddress))
				return true;
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
        for (User user : users.values()) {
            if (user.getEmailAddress().equals(emailAddress) && user.isAuthorized(password)) {
        		return user;
            }
        }
        return null;
    }

    public User getUser(UUID id) {
        return this.users.get(id);
    }

    /**
     * Returns the json file path this list is stored at.
     * @return filePath
     */
	@Override
	public String getFilePath() {
		return FILE_PATH;
	}

    /**
     * converts all users into a JSON array
     * @return - the users represented as a JSON array
     */
	@SuppressWarnings({ "unchecked", "exports" })
	@Override
    public JSONArray toJSON() {
		JSONArray jsonUsers = new JSONArray();
		for (User user : users.values()) {
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
    public User toObject(@SuppressWarnings("exports") JSONObject object) {
        UUID id = UUID.fromString((String) object.get("id"));
        String fn = (String) object.get("firstName");
        String ln = (String) object.get("lastName");
        String em = (String) object.get("emailAddress");
        String pw = (String) object.get("password");
        double spd = ((Double) object.get("metronomeSpeedModifier"));

        ArrayList<UUID> friends = new ArrayList<>();
        JSONArray friendsJSON = (JSONArray)object.get("friends");
        for (Object friendID : friendsJSON) { //iterate array
            friends.add(UUID.fromString((String) friendID));
        }

        User u = new User(id,fn,ln,em,pw,spd,friends);

        // userDetails.put("id", id.toString());
        // userDetails.put("firstName", firstName);
        // userDetails.put("lastName", lastName);
        // userDetails.put("emailAddress", emailAddress);
        // userDetails.put("password", password);
        // userDetails.put("metronomeSpeedModifier", metronomeSpeedModifier);

        return u;
    }
    
    @Override
    public OperationResult<Void> loadData() { //loads all data to user file
        OperationResult<ArrayList<User>> or = DataLoader.getData(this);

        if (or.success) {
            for (User u : or.result) {
                this.users.put(u.getId(),u);
            }
        }

        return new OperationResult<>("Needs result message done");
    }

    @Override
    public OperationResult<Void> linkData() { //links references stored by UUID to other classes.  Must be called AFTER all other data is loaded
        for (User u : this.users.values()) {
            for (UUID id : u.getUnlinkedFriends()) {
                u.getFriends().add(this.users.get(id));
            }
        }

        return new OperationResult<>(true);
    }

    public static void main(String[] args) { //tester.  verifies users are deserialized.  currently works.
        DataLoader.loadAllData();

        for (User u : UserManager.getInstance().users.values()) {
            System.out.println(u);
        }
    }
}