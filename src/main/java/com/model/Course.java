package com.model;

import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.model.managers.CourseManager;

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
	 * The course's unique code that students will enter to join
	 */
	private String code;

	/**
	 * The course's title
	 */
	private String title;

	/**
	 * The course's owner
	 */
	private Teacher owner;
	private UUID unlinkedOwner;

	/**
	 * The course's members
	 */
	private ArrayList<Student> members = new ArrayList<>();
	/**
	 * The ids of course members before converted to instances
	 */
	private ArrayList<UUID> unlinkedMembers;

	/**
	 * The course's lessons
	 */
	private ArrayList<Lesson> lessons;
	
	/**
	 * Constructs a new Course instance for data loading
	 * @param id - the course's unique identifier
	 * @param code - the course's unique code
	 * @param title - the course's title
	 * @param lessons - the course's lessons
	 * @param owner - the course's owner's id
	 * @param members - the course's student members' ids
	 */
	public Course(UUID id, String code, String title, ArrayList<Lesson> lessons, UUID owner, ArrayList<UUID> members) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.lessons = lessons;
		this.unlinkedOwner = owner;
		this.unlinkedMembers = members;
    }

	/**
	 * Constructs a new Course instance and automatically generates an identifier
	 * @param code - the course's unique code
	 * @param title - the course's title
	 * @param lessons - the course's lessons
	 * @param owner - the course's owner
	 * @param members - the course's student members
	 */
	public Course(String code, String title, ArrayList<Lesson> lessons, Teacher owner, ArrayList<Student> members) {
        this.id = UUID.randomUUID();
        this.code = code;
        this.title = title;
        this.lessons = lessons;
		this.owner = owner;
		this.members = members;
	}

	/**
	 * Gets the course's identifier
	 * @return a UUID
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * Gets the code's unique code that students may join through.
	 * @return the course's code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Gets the course's title
	 * @return the course's title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Gets the ids of the course's members before linking.
	 * @return an array list of ids
	 */
	public ArrayList<UUID> getUnlinkedMembers() {
		return this.unlinkedMembers;
	}

	/**
	 * Gets the course's owner's id before linking.
	 * @return an id
	 */
	public UUID getUnlinkedOwner() {
		return this.unlinkedOwner;
	}

	/**
	 * Updates the course's title
	 * @param title - the new course title
	 * @return whether the title was changed
	 */
	public boolean setTitle(String title) {
		if (this.title.equals(title)) return false;
		this.title = title;
		CourseManager.getInstance().save();
		return true;
	}

	/**
	 * Deletes the course
	 * @return whether the deletion was successful
	 */
	public boolean delete() {
		return CourseManager.getInstance().deleteCourse(id);
	}

	/**
	 * Gets the course's owner.
	 * @return the teacher who created the course
	 */
	public Teacher getOwner() {
		return owner;
	}

	/**
	 * Gets the course's members.
	 * @return an array list of students
	 */
	public ArrayList<Student> getMembers() {
		return members;
	}

	/**
	 * Adds a member to this course
	 * @param member - the student joining the course
	 * @return whether the operation was successful
	 */
	public boolean addMember(Student member) {
		if (member == null || members.contains(member))
			return false;

		members.add(member);
		member.getCourses().add(this);
		CourseManager.getInstance().save();
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
	 * @param numberOfTimes how many times the lesson should be played
	 * @return whether the lesson was successfully created
	 */
	public OperationResult<Lesson> createLesson(String title, Song song, InstrumentType instrumentType, int numberOfTimes) {
		Lesson lesson = new Lesson(title, song, instrumentType, numberOfTimes);
		lessons.add(lesson);
		CourseManager.getInstance().save();
		return new OperationResult<>(lesson);
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
				CourseManager.getInstance().save();
				return true;
			}
		}
		return false;
	}

	/**
	 * Removes a member from this course
	 * @param member - the student leaving the course
	 * @return whether the operation was successful
	 */
	public boolean removeMember(Student member) {
		boolean ret = this.members.remove(member);
		CourseManager.getInstance().save();
		return ret;
	}

	/**
	 * Links the owner to the Teacher object once it has been loaded.
	 * @param owner the teacher who owns the course
	 * @return Whether the link was successful
	 */
	public boolean linkOwner(Teacher owner) {
		if (unlinkedOwner != null && !owner.getId().equals(unlinkedOwner))
			return false;

		this.owner = owner;
		this.owner.getCourses().add(this);
		return true;
	}

	/**
	 * Gets a json object of the course.
	 * @return a json object
	 */
	@SuppressWarnings({ "unchecked", "exports" })
	public JSONObject toJSON() {
		JSONObject courseDetails = new JSONObject();

		courseDetails.put("id", id.toString());
		courseDetails.put("code", code);
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
