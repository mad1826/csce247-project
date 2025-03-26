package com.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.model.managers.UserManager;

/**
 * represent a user in the music app system
 * stores user info, password, friends, and courses
 * @author Makyia Irick
 * @author Matt Carey (Data stuff)
 */
public abstract class User {
	/**
	 * The user's id
	 */
    private UUID id;
	/**
	 * The user's first name
	 */
    private String firstName;
	/**
	 * The user's last name
	 */
    private String lastName;
	/**
	 * The user's email address
	 */
    private String emailAddress;
	/**
	 * The user's password
	 */
    private String password;
	/**
	 * The user's friends
	 */
    private HashMap<UUID, User> friends;
	/**
	 * Courses the user is a member or owner of
	 */
    private ArrayList<Course> courses;
	/**
	 * The user's modifier for metronome speed
	 */
    private double metronomeSpeedModifier = 1.0;

	/**
	 * Ids of friends who must be linked with User instances after initial data loading
	 */
    private ArrayList<UUID> unlinkedFriends;

	/**
	 * Gets a list of ids of friends that should be linked.
	 * @return an array list of ids
	 */
    public ArrayList<UUID> getUnlinkedFriends() {
        return unlinkedFriends;
    }

    /**
     * constructs a User with a specific UUID
     * @param id the unique ID of the user
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param emailAddress the user's email address
     * @param password the user's password (validated)
	 * @param metronomeSpeedModifier - the user's modifier for metronome speed
	 * @param unlinkedFriends - the teacher's friends to be linked to full User instances
     * @throws - IllegalArgumentException, if password is invalid
     */
     public User(UUID id, String firstName, String lastName, String emailAddress, String password, double metronomeSpeedModifier, ArrayList<UUID> unlinkedFriends) {
           if(!isValidPassword(password)) {
            throw new IllegalArgumentException("Invalid password: Should be at least 7 characters, containing 1 number and 1 symbol.");
           }
        
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.password = password;
		this.metronomeSpeedModifier = metronomeSpeedModifier;
		this.unlinkedFriends = unlinkedFriends;
        this.friends = new HashMap<>();
        this.courses = new ArrayList<>();
     }

    /**
     * constructs a User without a UUID (generates one automatically)
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param emailAddress the user's email address
     * @param password the user's password (validated)
     */
    public User(String firstName, String lastName, String emailAddress, String password) {
        this(UUID.randomUUID(), firstName, lastName, emailAddress, password, 1, new ArrayList<>());
    }

    /**
     * validates if the password meets criteria
     * @param password the password to validate
     * @return - true if password is valid, false otherwise
     */
    private boolean isValidPassword(String password){
        if (password.length() < 7) {
            return false;
        }
        boolean hasNumber = false;
        boolean hasSymbol = false;

        for(char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                hasNumber = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSymbol = true;
            }
        }
        return hasNumber && hasSymbol;
    }
    

    /**
     * check if provided password matches the user's password
     * @param password the password to verify
     * @return - true if password is correct, false otherwise
     */
    public boolean isAuthorized(String password) {
        return this.password.equals(password);
    }

    /**
     * resets the user's password if new password is valid and different
     * @param newPassword the new password to set
     * @return - true if the password was successfully reset, false otherwise
     */
    public boolean resetPassword(String newPassword) {
        if(isValidPassword(newPassword) && !this.password.equals(newPassword)) {
            this.password = newPassword;
			UserManager.getInstance().save();
            return true;
        }
        return false;
    }

    /**
     * sets the metronome speed modifier if speed is greater than 0
     * @param speed the speed to set
     * @return - true if speed was set, false otherwise
     */
    public boolean setMetronomeSpeed(double speed) {
        if (speed > 0 ) {
            this.metronomeSpeedModifier = speed;
			UserManager.getInstance().save();
            return true;
        }
        return false;
    }

    /**
     * gets the user's unique ID
     * @return - the user's ID
     */
    public UUID getId() {
        return id;
    }

    /**
    * gets the user's first name
    * @return - the user's first name
    */
    public String getFirstName() {
        return firstName;
    }
    
    /**
    * gets the user's last name
    * @return - the user's last name
    */
    public String getLastName() {
        return lastName;
    }

    /**
     * gets the user's email address
     * @return - the user's email address
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * gets the list of the user's friends
     * @return - hash map of friends
     */
    public HashMap<UUID, User> getFriends() {
        return friends;
    }

	/**
	 * Adds a user as a friend.
	 * @param friend the user to add as a friend
	 * @return the result of attempting to add the user as a friend
	 */
	public OperationResult<Void> addFriend(User friend) {
		for (UUID friendId : friends.keySet()) {
			if (friendId.equals(friend.getId())) {
				return new OperationResult<>("This user is already your friend.");
			}
		}
		friends.put(friend.getId(), friend);
		UserManager.getInstance().save();
		return new OperationResult<>(true);
	}

	/**
	 * Removes a friend.
	 * @param friend the user to remove as a friend
	 * @return the result of attempting to unfriend
	 */
	public OperationResult<Void> removeFriend(User friend) {
		for (UUID friendId : friends.keySet()) {
			if (friendId.equals(friend.getId())) {
				friends.remove(friendId);
				UserManager.getInstance().save();
				return new OperationResult<>(true);
			}
		}
		return new OperationResult<>("User was not already a friend.");
	}

    /**
     * gets the list of the user's courses
     * @return - ArrayList of courses
     */
    public ArrayList<Course> getCourses() {
        return courses;
    }

    /**
     * gets the metronome speed modifier
     * @return - the user's metronome speed
     */
    public double getMetronomeSpeedModifier() {
        return metronomeSpeedModifier;
    }

	/**
	 * Gets a json representation of the user.
	 * @return
	 */
    @SuppressWarnings({ "unchecked", "exports" })
	public JSONObject toJSON() {
        JSONObject userDetails = new JSONObject();
        userDetails.put("id", id.toString());
        userDetails.put("firstName", firstName);
        userDetails.put("lastName", lastName);
        userDetails.put("emailAddress", emailAddress);
        userDetails.put("password", password);
        userDetails.put("metronomeSpeedModifier", metronomeSpeedModifier);
		JSONArray friendsJSON = new JSONArray();
		for (UUID friendId : friends.keySet()) {
			friendsJSON.add(friendId.toString());
		}
		userDetails.put("friends", friendsJSON);
        return userDetails;
    }

	/**
	 * Gets a string representation of the user.
	 */
    @Override
    public String toString() {
        return "User: "
            + "id=" + id
            + ", firstName='" + this.firstName + '\''
            + ", lastName='" + this.lastName + '\''
            + ", emailAddress='" + this.emailAddress + '\''
            + ", password=" + this.password + '\''
            + ", friends=" + this.friends.size() + '\''
            + ", courses=" + this.courses.size() + '\''
            + ", metronomeSpeedModifier=" + this.metronomeSpeedModifier + '\''
            + '}';
    }
}
