package com.model.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.model.DataHandlers.DataLoader;
import com.model.DataHandlers.DataWriter;
import com.model.OperationResult;
import com.model.SavableList;
import com.model.Student;
import com.model.Teacher;
import com.model.User;

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
    public OperationResult<User> createUser(String firstName, String lastName, String emailAddress, String password, boolean isTeacher) {
        if (accountExists(emailAddress))
			return new OperationResult<>("An account with this email already exists.");

            /*
             * validate input parameters
             */
            if (firstName == null || firstName.trim().isEmpty()) {
                return new OperationResult<>("First name cannot be empty.");
            }
            if (lastName == null || lastName.trim().isEmpty()) {
                return new OperationResult<>("Last name cannot be empty.");
            }
            if (emailAddress == null || !emailAddress.contains("@")) {
                return new OperationResult<>("Invalid email address.");
            }
            if (password == null || password.length() < 6) {
                return new OperationResult<>("Password must be at least 6 characters long.");
            }

        User user;
        if (isTeacher)
            user = new Teacher(firstName, lastName, emailAddress, password);
        else
            user = new Student(firstName, lastName, emailAddress, password);
        
        users.put(user.getId(), user);
        
		OperationResult<User> or = new OperationResult<>(user);
		save();
		return or;
    }

    /**
     * deletes a user from the list by matching the user ID
     * @param - the unique ID of the user to delete
     * @return - the result of attempting to delete the user
     */
    public OperationResult<Void> deleteUser(UUID userID) {
        //check if user exists
        User userToDelete = users.get(userID);
        
        if (userToDelete == null) {
            return new OperationResult<>("User not found with the given ID.");
        }
 
        //remove user from the collection
        users.remove(userID);
         
        //return successful operation
        OperationResult<Void> or = new OperationResult<>(true);
		save();
        return or;
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
	@SuppressWarnings({ "unchecked" })
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
    public User toObject(JSONObject object) {
        UUID id = UUID.fromString((String) object.get("id"));
        String fn = (String) object.get("firstName");
        String ln = (String) object.get("lastName");
        String em = (String) object.get("emailAddress");
        String pw = (String) object.get("password");
        double spd = ((Double) object.get("metronomeSpeedModifier"));
        String type = (String) object.get("type");

        ArrayList<UUID> friends = new ArrayList<>();
        JSONArray friendsJSON = (JSONArray)object.get("friends");
        for (Object friendID : friendsJSON) { //iterate array
            friends.add(UUID.fromString((String) friendID));
        }

        User u = null;
        if (type.equals("Student")) {
            HashMap<UUID, Integer> lessonProgress = new HashMap<>(); //load lesson progress
            JSONObject o = (JSONObject) object.get("lessonProgress");
            for (Object key : o.keySet()) {
                String index = (String) key;
                int value = ((Long) o.get(index)).intValue();

                lessonProgress.put(UUID.fromString(index), value);
            }

            u = new Student(id,fn,ln,em,pw,spd, friends, lessonProgress);
        } else if (type.equals("Teacher")) {
            u = new Teacher(id,fn,ln,em,pw, spd, friends);
        }

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

        return new OperationResult<>(true);
    }

    @Override
    public OperationResult<Void> linkData() { //links references stored by UUID to other classes.  Must be called AFTER all other data is loaded
        for (User u : this.users.values()) {
            for (UUID id : u.getUnlinkedFriends()) {
                u.getFriends().put(id, this.users.get(id));
            }
        }

        return new OperationResult<>(true);
    }

	/**
	 * Save all users to the data writer destination.
	 */
	@Override
	public OperationResult<JSONArray> save() {
		return DataWriter.setData(this);
	}

    public static void main(String[] args) { //tester.  verifies users are deserialized.  currently works.
        DataLoader.loadAllData();

        for (User u : UserManager.getInstance().users.values()) {
            System.out.println(u);
        }
    }
}