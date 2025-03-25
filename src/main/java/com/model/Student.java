package com.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.model.managers.CourseManager;
import com.model.managers.UserManager;

/**
 * Represents a student in the music app
 * @author Ryan Smith
 */
public class Student extends User {
    /**
     * Maps lesson to amount of times they have played the lesson.
     */
    private final HashMap<UUID, Integer> lessonProgress;

    /**
     * Creates a new Student with an existing UUID and user details
     *
     * @param id            unique identifier for the student
     * @param firstName     student's first name
     * @param lastName      student's last name
     * @param emailAddress  student's email address
     * @param password      student's password
	 * @param metronomeSpeedModifier - the student's modifier for metronome speed
	 * @param unlinkedFriends - the student's friends to be linked to full Student instances
	 * @param lessonProgress - the student's progress per lesson, mapped by each lesson's id
     */
    public Student(UUID id, String firstName, String lastName, String emailAddress, String password, double metronomeSpeedModifier, ArrayList<UUID> unlinkedFriends, HashMap<UUID,Integer> lessonProgress) {
        super(id, firstName, lastName, emailAddress, password, metronomeSpeedModifier, unlinkedFriends);
        this.lessonProgress = lessonProgress;
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
     * Joins a course with the specified code
     *
     * @param code The unique code for the course
     * @return Operation result containing the joined Course or error message
     */
    public OperationResult<Course> joinCourse(String code) {
        CourseManager courseManager = CourseManager.getInstance();
        Course courseToJoin = null;
        
        // Find course with matching code
        JSONArray courses = courseManager.toJSON();
        for (Object obj : courses) {
            JSONObject courseJson = (JSONObject)obj;
            if (courseJson.get("code").equals(code)) {
                courseToJoin = courseManager.toObject(courseJson);
                break;
            }
        }

        if (courseToJoin == null) {
            return new OperationResult<>("Course with code " + code + " not found.");
        }
        
        // Check if already enrolled
        for (Course enrolledCourse : getCourses()) {
            if (enrolledCourse.getCode().equals(code)) {
                return new OperationResult<>("Already enrolled in this course.");
            }
        }

        getCourses().add(courseToJoin);
        courseToJoin.addMember(this);
		UserManager.getInstance().save();
        return new OperationResult<>(courseToJoin);
    }

    /**
     * Leaves a course with the specified code
     *
     * @param code The unique code of the course to leave
     * @return Operation result indicating success or failure
     */
    public OperationResult<Void> leaveCourse(String code) {
        Course courseToLeave = null;
        for (Course course : getCourses()) {
            if (course.getCode().equals(code)) {
                courseToLeave = course;
                break;
            }
        }
        
        if (courseToLeave == null) {
            return new OperationResult<>("Not enrolled in course with code " + code);
        }

        getCourses().remove(courseToLeave);
		UserManager.getInstance().save();
        if (!courseToLeave.removeMember(this)) {
            return new OperationResult<>("Failed to remove student from course.");
        }
        return new OperationResult<>(true);
    }

    /**
     * Gets the progress for a specific lesson
     *
     * @param lesson The lesson to check progress for
     * @return The progress value (0-100)
     */
    public int getLessonProgress(Lesson lesson) {
        return lessonProgress.get(lesson.getId());
    }
    
    /**
     * Assigns a lesson to this user
     * 
     * @param l lesson being assigned
     */

    public void assignLesson(Lesson l) {
        this.lessonProgress.put(l.getId(),0);
		UserManager.getInstance().save();
    }

    @Override
    @SuppressWarnings({ "unchecked", "exports" })
	public JSONObject toJSON() {
        JSONObject o = super.toJSON();

        JSONObject progress = new JSONObject();
		for (UUID lessonId : lessonProgress.keySet()) {
			progress.put(lessonId, lessonProgress.get(lessonId));
		}
        o.put("type","Student");
		o.put("lessonProgress", progress);

        return o;
    }

    /**
     * Increments amount of times this lesson has been played by one
     * 
     * @param lesson lesson that is being progressed in
     */
    public void progressLesson(Lesson lesson) {
        int maxProgress = lesson.getNumberOfTimes();
        int thisProgress = this.lessonProgress.get(lesson.getId());
        if (thisProgress<maxProgress) { //If lesson is complete, do not increment progress
            thisProgress++;
			UserManager.getInstance().save();
        }
    }
} 