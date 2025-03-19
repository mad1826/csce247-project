package com.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.model.datahandlers.DataLoader;

/**
 * A manager for all courses
 * @author Michael Davis
 */
public class CourseManager implements SavableList<Course> {
	/** The single course manager instance */
	private static CourseManager courseManager;
	
	/** All courses */
	private final HashMap<UUID, Course> courses;

	/** The location of the course data */
    final static String FILE_PATH = "src/main/java/com/data/courses.json";

	/**
	 * Constructs a new CourseMangaer instance
	 * @param courses - all courses
	 * @param filePath - the location of the course data
	 */
	private CourseManager() {
		this.courses = new HashMap<>();
	}

	/**
	 * Gets the single CourseManager instance
	 * @return the CourseManager singleton instance
	 */
	public static CourseManager getInstance() {
		if (courseManager == null)
			courseManager = new CourseManager();

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
	 * @param owner - the course's owner
	 * @return the newly created course
	 */
	public Course createCourse(UUID courseId, String title, Teacher owner) {
		Course course = new Course(courseId, title, new ArrayList<>(), owner, new ArrayList<>());
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
		return FILE_PATH;
	}

	/**
	 * Transforms all Course instances into a JSON array
	 */
	@SuppressWarnings({ "exports", "unchecked" })
	@Override
	public JSONArray toJSON() {
		JSONArray jsonCourses = new JSONArray();

		for (Course course : courses.values()) {
			jsonCourses.add(course.toJSON());
		}

		return jsonCourses;
	}

	/**
	 * Transforms a JSON object into a Course instances
	 */
	@Override
	public Course toObject(@SuppressWarnings("exports") JSONObject object) {
		UUID id = UUID.fromString((String) object.get("id"));
		String code = (String) object.get("code");
		String title = (String) object.get("title");
		UUID owner = UUID.fromString((String) object.get("owner"));

		ArrayList<UUID> members = new ArrayList<>();
		JSONArray membersJSON = (JSONArray) object.get("members");
		for (Object memberID : membersJSON) { //iterate array
			members.add(UUID.fromString((String) memberID));
		}

		ArrayList<Lesson> lessons = new ArrayList<>();
		JSONArray lessonsJSON = (JSONArray) object.get("lessons");
		for (Object lessonObject : lessonsJSON) {
			JSONObject lessonJSON = (JSONObject) lessonObject;

			UUID lessonID = UUID.fromString((String) lessonJSON.get("id"));
			String lessonTitle = (String) lessonJSON.get("title");
			UUID songID = UUID.fromString((String) lessonJSON.get("song"));
			String instrument = (String) lessonJSON.get("instrument");

			Lesson l = new Lesson(lessonID, lessonTitle, songID,InstrumentType.valueOf(instrument));
			lessons.add(l);
		}

		return new Course(id,code,title,lessons,owner,members);
	}

	@Override
	public OperationResult<Void> loadData() {
		 OperationResult<ArrayList<Course>> or = DataLoader.getData(this);

        if (or.success) {
            for (Course c : or.result) {
                this.courses.put(c.getId(),c);
            }
        }

        return new OperationResult<>("Needs result message done");
	}

	@Override
	public OperationResult<Void> linkData() {
		for (Course c : this.courses.values()) {
            for (UUID id : c.getUnlinkedMembers()) {
				Student s = (Student) UserManager.getInstance().getUser(id); //will throw error if non student user is in course

				s.getCourses().add(c); //Add course to user, ideally would be done in user.linkData but the loop is already here so it was easier.

            	c.getMembers().add(s); //Add user to course
            }

			for (Lesson l : c.getLessons()) {
				l.setSong(SongManager.getInstance().getSong(l.getUnlinkedSong()).result); //will error until we have songmanager.loadData
			}
        }

        return new OperationResult<>(true);
	}
}
