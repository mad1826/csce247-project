package com.model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.UUID;
import java.util.ArrayList;
import java.util.HashMap;
import com.model.managers.CourseManager;
import com.model.datahandlers.DataLoader;

public class StudentTest {
    private Student student;
    private UUID studentId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private double metronomeSpeedModifier;
    private ArrayList<UUID> unlinkedFriends;
    private HashMap<UUID, Integer> lessonProgress;

    @Before
    public void setUp() {
        DataLoader.loadAllData();
        CourseManager.getInstance().getCourses().clear();
        
        studentId = UUID.randomUUID();
        firstName = "John";
        lastName = "Doe";
        email = "john.doe@example.com";
        password = "Secure123!@#";
        metronomeSpeedModifier = 1.0;
        unlinkedFriends = new ArrayList<>();
        lessonProgress = new HashMap<>();
        
        student = new Student(studentId, firstName, lastName, email, password, 
                            metronomeSpeedModifier, unlinkedFriends, lessonProgress);
    }

    @Test
    public void testJoinCourseWithValidCode_returnsSuccess() {
        Teacher teacher = new Teacher("Mary", "Sue", "teacher@example.com", "Secure123!@#");
        String courseCode = "MUS101";
        teacher.createCourse(courseCode, "Introduction to Music");

        OperationResult<Course> result = student.joinCourse(courseCode);

        assertTrue(result.success);
        assertNotNull(result.result);
        assertEquals(courseCode, result.result.getCode());
        assertTrue(student.getCourses().contains(result.result));
    }

    @Test
    public void testJoinCourseWithInvalidCode_returnsFailure() {
        String invalidCode = "INVALID123";

        OperationResult<Course> result = student.joinCourse(invalidCode);

        assertFalse(result.success);
        assertNull(result.result);
        assertEquals("Course with code " + invalidCode + " not found.", result.message);
    }

    @Test
    public void testJoinCourseAlreadyEnrolled_returnsFailure() {
        Teacher teacher = new Teacher("Mary", "Sue", "teacher@example.com", "Secure123!@#");
        String courseCode = "MUS101";
        teacher.createCourse(courseCode, "Introduction to Music");
        student.joinCourse(courseCode);

        OperationResult<Course> result = student.joinCourse(courseCode);

        assertFalse(result.success);
        assertNull(result.result);
        assertEquals("Already enrolled in this course.", result.message);
    }

    @Test
    public void testLeaveCourseWithValidCode_returnsSuccess() {
        Teacher teacher = new Teacher("Mary", "Sue", "teacher@example.com", "Secure123!@#");
        String courseCode = "MUS101";
        teacher.createCourse(courseCode, "Introduction to Music");
        student.joinCourse(courseCode);

        OperationResult<Void> result = student.leaveCourse(courseCode);

        assertTrue(result.success);
        assertFalse(student.getCourses().stream().anyMatch(c -> c.getCode().equals(courseCode)));
    }

    @Test
    public void testLeaveCourseNotEnrolled_returnsFailure() {
        String courseCode = "MUS101";

        OperationResult<Void> result = student.leaveCourse(courseCode);

        assertFalse(result.success);
        assertEquals("Not enrolled in course with code " + courseCode, result.message);
    }

    @Test
    public void testGetLessonProgressWithNoProgress_returnsZero() {
        Lesson lesson = new Lesson("Test Lesson", new Song("Test Song", "Test Artist", new ArrayList<>(), new HashMap<>()), 
                                 InstrumentType.PIANO, 5);

        int progress = student.getLessonProgress(lesson);

        assertEquals(0, progress);
    }

    @Test
    public void testGetLessonProgressWithProgress_returnsCorrectValue() {
        Lesson lesson = new Lesson("Test Lesson", new Song("Test Song", "Test Artist", new ArrayList<>(), new HashMap<>()), 
                                 InstrumentType.PIANO, 5);
        student.assignLesson(lesson);
        student.progressLesson(lesson);

        int progress = student.getLessonProgress(lesson);

        assertEquals(1, progress);
    }

    @Test
    public void testAssignLesson_createsNewProgressEntry() {
        Lesson lesson = new Lesson("Test Lesson", new Song("Test Song", "Test Artist", new ArrayList<>(), new HashMap<>()), 
                                 InstrumentType.PIANO, 5);

        student.assignLesson(lesson);

        assertTrue(lessonProgress.containsKey(lesson.getId()));
        assertEquals(0, (int)lessonProgress.get(lesson.getId()));
    }

    @Test
    public void testProgressLessonIncrementsProgress() {
        Lesson lesson = new Lesson("Test Lesson", new Song("Test Song", "Test Artist", new ArrayList<>(), new HashMap<>()), 
                                 InstrumentType.PIANO, 5);
        student.assignLesson(lesson);

        student.progressLesson(lesson);

        assertEquals(1, (int)lessonProgress.get(lesson.getId()));
    }

    @Test
    public void testProgressLessonDoesNotExceedMaxProgress() {
        Lesson lesson = new Lesson("Test Lesson", new Song("Test Song", "Test Artist", new ArrayList<>(), new HashMap<>()), 
                                 InstrumentType.PIANO, 2);
        student.assignLesson(lesson);
        student.progressLesson(lesson);
        student.progressLesson(lesson);

        student.progressLesson(lesson);

        assertEquals(2, (int)lessonProgress.get(lesson.getId()));
    }
} 