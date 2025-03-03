package com.model;

import java.util.ArrayList;
import java.util.UUID;

/**
 * A teacher's course that can contain lessons for members to progress through
 * 
 * @author Michael Davis
 */
public class Course {
	/**
	 * The course's unique identifier
	 */
	private UUID id;

	/**
	 * The course's title
	 */
	private String title;

	/**
	 * The course's owner
	 */
	private Teacher owner;

	/**
	 * The course's members
	 */
	private ArrayList<Student> members;

	/**
	 * The course's lessons
	 */
	private ArrayList<Lesson> lessons;

	/**
	 * Constructs a new Course instance that already has an identifier
	 * @param id - the course's unique identifier
	 * @param title - the course's title
	 * @param lessons - the course's lessons
	 * @param owner - the course's owner
	 * @param members - the course's student members
	 */
	public Course(UUID id, String title, ArrayList<Lesson> lessons, Teacher owner, ArrayList<Student> members) {
		this.id = id;
		this.title = title;
		this.owner = owner;
		this.members = members;
	}

	/**
	 * Constructs a new Course instance and automatically generates an identifier
	 * @param title - the course's title
	 * @param lessons - the course's lessons
	 * @param owner - the course's owner
	 * @param members - the course's student members
	 */
	public Course(String title, ArrayList<Lesson> lessons, Teacher owner, ArrayList<Student> members) {
		this(UUID.randomUUID(), title, lessons, owner, members);
	}

	/**
	 * Gets the course's identifier
	 * @return a UUID
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * Gets the course's title
	 * @return the course's title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Updates the course's title
	 * @param title - the new course title
	 * @return whether the title was changed
	 */
	public boolean setTitle(String title) {
		if (this.title.equals(title)) return false;
		this.title = title;
		return true;
	}

	/**
	 * Deletes the course
	 * @return whether the deletion was successful
	 */
	public boolean delete() {
		return true;
	}

	/**
	 * Adds a member to this course
	 * @param member - the student joining the course
	 * @return whether the operation was successful
	 */
	public boolean addMember(Student member) {
		this.members.add(member);
		return true;
	}

	/**
	 * Gets the course's lessons
	 * @return an array list of the course's lessons
	 */
	public ArrayList<Lesson> getLessons() {
		return lessons;
	}

	/**
	 * Creates a new lesson in this course
	 * @param title - the lesson's title
	 * @return whether the lesson was successfully created
	 */
	public boolean createLesson(String title) {
		lessons.add(new Lesson(title));
		return true;
	}

	/**
	 * Deletes an existing lesson in this course
	 * @param lessonId - the lesson's identifier
	 * @return whether the lesson was successfully deleted
	 */
	public boolean deleteLesson(UUID lessonId) {
		for (Lesson lesson : lessons) {
			if (lesson.hasId(lessonId)) {
				lessons.remove(lesson);
				return true;
			}
		}
		return false;
	}
}
