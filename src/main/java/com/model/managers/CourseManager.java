package com.model.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.model.Course;
import com.model.InstrumentType;
import com.model.Lesson;
import com.model.OperationResult;
import com.model.SavableList;
import com.model.Student;
import com.model.Teacher;
import com.model.DataHandlers.DataLoader;
import com.model.DataHandlers.DataWriter;

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
	 * Get all loaded courses.
	 * @return a hash map of courses, mapped by their ids
	 */
	public HashMap<UUID, Course> getCourses() {
		return courses;
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
	 * @param code - the course's unique code
	 * @param title - the course's title
	 * @param owner - the course's owner
	 * @return the newly created course
	 */
	public OperationResult<Course> createCourse(String code, String title, Teacher owner) {
		for (Course course : courses.values()) {
			if (course.getCode().equals(code)) {
				return new OperationResult<>("A course with the specified code already exists.");
			}
		} 

		Course course = new Course(code, title, new ArrayList<>(), owner, new ArrayList<>());
		courses.put(course.getId(), course);
		save();
		return new OperationResult<>(course);
	}

	/**
	 * Deletes a course by its id
	 * @param courseId - the course's unique identifier
	 * @return whether the deletion was successful
	 */
	public boolean deleteCourse(UUID courseId) {
		Course course = courses.get(courseId);
		if (course == null) {
			return false;
		}

		// Remove course from all enrolled students
		for (Student student : course.getMembers()) {
			student.getCourses().remove(course);
		}

		// Remove course from the manager
		courses.remove(courseId);
		save();
		return true;
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
	@SuppressWarnings({ "unchecked" })
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
	public Course toObject(JSONObject object) {
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

			Object numTimesObj = lessonJSON.get("numberOfTimes");
			if (numTimesObj==null) { //safe against nullptr;
				numTimesObj = 1;
			}
			int numberOfTimes = (Integer)numTimesObj;

			Lesson l = new Lesson(lessonID, lessonTitle, songID,InstrumentType.valueOf(instrument.toUpperCase()),numberOfTimes);
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

        return new OperationResult<>(true);
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
				l.linkSong(SongManager.getInstance().getSong(l.getUnlinkedSong()).result); //will error until we have songmanager.loadData
			}
        }

        return new OperationResult<>(true);
	}

	/**
	 * Save all courses to the data writer destination.
	 */
	@Override
	public OperationResult<JSONArray> save() {
		return DataWriter.setData(this);
	}
}
