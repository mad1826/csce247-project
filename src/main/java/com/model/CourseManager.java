package com.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * A manager for all courses
 * @author Michael Davis
 */
public class CourseManager implements SavableList<Course> {
	/** The single course manager instance */
	private static CourseManager courseManager;
	
	/** All courses */
	private HashMap<UUID, Course> courses;

	/** The location of the course data */
	private String filePath;

	/**
	 * Constructs a new CourseMangaer instance
	 * @param courses - all courses
	 * @param filePath - the location of the course data
	 */
	private CourseManager(HashMap<UUID, Course> courses, String filePath) {
		this.courses = courses;
		this.filePath = filePath;
	}

	/**
	 * Gets the single CourseManager instance
	 * @param courses - all courses if a new instance must be constructed
	 * @param filePath - the location of the course data if a new instance must be constructed
	 * @return the CourseManager singleton instance
	 */
	public static CourseManager getInstance(HashMap<UUID, Course> courses, String filePath) {
		if (courseManager == null)
			courseManager = new CourseManager(courses, filePath);

		return courseManager;
	}

	/**
	 * Finds a course by its identifier
	 * @param id - the course's unique identifier
	 * @return - the found course, or null if not found
	 */
	public Course getCourse(UUID id) {
		return courses.get(id);
	}

	/**
	 * Creates a new course
	 * @param title - the course's title
	 * @param courseId - the course's unique identifier
	 * @return - the newly created course
	 */
	public Course createCourse(String title, UUID courseId) {
		Course course = new Course(courseId, title, new ArrayList<>());
		courses.put(courseId, course);
		return course;
	}

	/**
	 * Saves all courses
	 * @return whether the save was successful
	 */
	public boolean saveCourses() {
		return false;
	}

	/**
	 * Deletes a course by its id
	 * @param courseId - the course's unique identifier
	 * @return whether the deletion was successful
	 */
	public boolean deleteCourse(UUID courseId) {
		return false;
	}

	/**
	 * Gets the location of the course data
	 */
	@Override
	public String getFilePath() {
		return filePath;
	}

	/**
	 * Transforms a Course instance into a JSON string
	 */
	@Override
	public String toJSON(Course course) {
		return "";
	}

	/**
	 * Transforms a JSON string into an array list of Course instances
	 */
	@Override
	public ArrayList<Course> toObjects(String json) {
		return new ArrayList<>();
	}
}
