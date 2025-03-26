package com.model;

import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONObject;

import com.model.managers.CourseManager;

/**
 * Represents a teacher in the music app
 *  @author Ryan Smith
 */
public class Teacher extends User {
    
    /**
     * Creates a new Teacher with an existing UUID and user details
     *
     * @param id            unique identifier for the teacher
     * @param firstName     teacher's first name
     * @param lastName      teacher's last name
     * @param emailAddress  teacher's email address
     * @param password      teacher's password
	 * @param metronomeSpeedModifier - the teacher's modifier for metronome speed
	 * @param unlinkedFriends - the teacher's friends to be linked to full Teacher instances
     */
    public Teacher(UUID id, String firstName, String lastName, String emailAddress, String password, double metronomeSpeedModifier, ArrayList<UUID> unlinkedFriends) {
        super(id, firstName, lastName, emailAddress, password, metronomeSpeedModifier, unlinkedFriends);
    }

    /**
     * Creates a new Teacher with automatically generated UUID
     *
     * @param firstName     teacher's first name
     * @param lastName      teacher's last name
     * @param emailAddress  teacher's email address
     * @param password      teacher's password
     */
    public Teacher(String firstName, String lastName, String emailAddress, String password) {
        super(firstName, lastName, emailAddress, password);
    }

    /**
     * Creates a new course with the specified code
     *
	 * @param code - the code that students will enter to join the course
	 * @param title - the course's title
     * @return the result of attempting to create the course
     */
    public OperationResult<Course> createCourse(String code, String title) {
		return CourseManager.getInstance().createCourse(code, title, this);
    }

    /**
     * Deletes a course with the specified ID
     *
     * @param id The unique identifier of the course to delete
     * @return true if the course was successfully deleted, false otherwise
     */
    public boolean deleteCourse(UUID id) {
        return CourseManager.getInstance().deleteCourse(id);
    }

	/**
	 * Gets a json representation of the teacher.
	 */
    @Override
    @SuppressWarnings({ "unchecked", "exports" })
	public JSONObject toJSON() {
        JSONObject o = super.toJSON();

        o.put("type","Teacher");

        return o;
    }
} 