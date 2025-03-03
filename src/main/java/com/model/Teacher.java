package com.model;

import java.util.UUID;
import java.util.ArrayList;

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
     */
    public Teacher(UUID id, String firstName, String lastName, String emailAddress, String password) {
        super(id, firstName, lastName, emailAddress, password);
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
     * Creates a new course with the specified ID
     *
     * @param id The unique identifier for the course
     * @return A new Course object
     */
    public Course createCourse(UUID id) {
        return new Course(id, "Placeholder Course", new ArrayList<Lesson>());
    }

    /**
     * Deletes a course with the specified ID
     *
     * @param id The unique identifier of the course to delete
     * @return true if the course was successfully deleted, false otherwise
     */
    public boolean deleteCourse(UUID id) {
        
        return true;
    }
} 