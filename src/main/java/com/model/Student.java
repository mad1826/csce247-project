package com.model;

import java.util.HashMap;
import java.util.UUID;

/**
 * Represents a student in the music app
 * @author Ryan Smith
 */
public class Student extends User {
    private HashMap<UUID, Integer> lessonProgress;
    
    /**
     * Creates a new Student with an existing UUID and user details
     *
     * @param id            unique identifier for the student
     * @param firstName     student's first name
     * @param lastName      student's last name
     * @param emailAddress  student's email address
     * @param password      student's password
     */
    public Student(UUID id, String firstName, String lastName, String emailAddress, String password) {
        super(id, firstName, lastName, emailAddress, password);
        this.lessonProgress = new HashMap<>();
    }

    /**
     * Creates a new Student with automatically generated UUID
     *
     * @param firstName     student's first name
     * @param lastName      student's last name
     * @param emailAddress  student's email address
     * @param password      student's password
     */
    public Student(String firstName, String lastName, String emailAddress, String password) {
        super(firstName, lastName, emailAddress, password);
        this.lessonProgress = new HashMap<>();
    }

    /**
     * Joins a course with the specified ID
     *
     * @param id The unique identifier for the course
     * @return The joined Course object
     */
    public Course joinCourse(UUID id) {
        return new Course(id, "Placeholder Course", "Placeholder Description");
    }

    /**
     * Leaves a course with the specified ID
     *
     * @param id The unique identifier of the course to leave
     * @return true if the course was successfully left, false otherwise
     */
    public boolean leaveCourse(UUID id) {
        return true;
    }

    /**
     * Gets the progress for a specific lesson
     *
     * @param lesson The lesson to check progress for
     * @return The progress value (0-100)
     */
    public int getLessonProgress(Lesson lesson) {
        return 0;
    }
} 