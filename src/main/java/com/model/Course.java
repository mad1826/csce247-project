package com.model;

import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
		this.lessons = lessons;
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

	public ArrayList<Student> getMembers() {
		return members;
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
	 * @param sheet - the song this lesson will play a sheet from
	 * @param instrumentType - the instrument this lesson will play a sheet using
	 * @return whether the lesson was successfully created
	 */
	public boolean createLesson(String title, Song song, InstrumentType instrumentType) {
		lessons.add(new Lesson(title, song, instrumentType));
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

	@SuppressWarnings({ "unchecked", "exports" })
	public JSONObject toJSON() {
		JSONObject courseDetails = new JSONObject();

		courseDetails.put("id", id.toString());
		courseDetails.put("code", id.toString());
		courseDetails.put("title", title);
		courseDetails.put("owner", owner.getId().toString());

		JSONArray membersArray = new JSONArray();
		for (Student member : members) {
			membersArray.add(member.getId().toString());
		}
		courseDetails.put("members", membersArray);

		JSONArray lessonsArray = new JSONArray();
		for (Lesson lesson : lessons) {
			lessonsArray.add(lesson.toJSON());
		}
		courseDetails.put("lessons", lessonsArray);

		return courseDetails;
	}
}
