package com.model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.UUID;
import java.util.ArrayList;
import org.json.simple.JSONObject;
import com.model.Teacher;
import com.model.Course;
import com.model.OperationResult;
import com.model.managers.CourseManager;

public class TeacherTest {
    private Teacher teacher;
    private UUID teacherId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private double metronomeSpeedModifier;
    private ArrayList<UUID> unlinkedFriends;

    @Before
    public void setUp() {
        teacherId = UUID.randomUUID();
        firstName = "John";
        lastName = "Doe";
        email = "john.doe@example.com";
        password = "securePassword@123";
        metronomeSpeedModifier = 1.0;
        unlinkedFriends = new ArrayList<>();
        
        teacher = new Teacher(teacherId, firstName, lastName, email, password, 
                            metronomeSpeedModifier, unlinkedFriends);
        
        // Reset CourseManager state
        CourseManager.getInstance().getCourses().clear();
    }

    @Test
    public void testCreateCourseWithValidInput_returnsSuccess() {
        
        String code = "MUS101";
        String title = "Introduction to Music";

        
        OperationResult<Course> result = teacher.createCourse(code, title);

        
        assertTrue(result.success);
        assertNotNull(result.result);
        assertEquals(code, result.result.getCode());
        assertEquals(title, result.result.getTitle());
    }

    @Test
    public void testCreateCourseWithDuplicateCode_returnsFailure() {
     
        String code = "MUS101";
        String title = "Introduction to Music";
        
        
        OperationResult<Course> firstResult = teacher.createCourse(code, title);
        assertTrue("First course creation should succeed", firstResult.success);

        // Try to create second course with same code
        OperationResult<Course> secondResult = teacher.createCourse(code, "Another Course");

        
        assertFalse("Creating course with duplicate code should fail", secondResult.success);
        assertNull("Result should be null for duplicate code", secondResult.result);
    }

    @Test
    public void testDeleteCourseWithExistingCourse_returnsTrue() {
       
        String code = "MUS101";
        String title = "Introduction to Music";
        OperationResult<Course> createResult = teacher.createCourse(code, title);
        UUID courseId = createResult.result.getId();

     
        boolean result = teacher.deleteCourse(courseId);

        
        assertTrue(result);
    }

    @Test
    public void testDeleteCourseWithNonExistingCourse_returnsFalse() {
       
        UUID nonExistingId = UUID.randomUUID();

     
        boolean result = teacher.deleteCourse(nonExistingId);

       
        assertFalse(result);
    }

    @Test
    public void testDeleteCourseWithNullId_returnsFalse() {
        
        boolean result = teacher.deleteCourse(null);

    
        assertFalse(result);
    }

    @Test
    public void testToJSON_returnsCorrectType() {
       
        JSONObject json = teacher.toJSON();

       
        assertNotNull(json);
        assertEquals("Teacher", json.get("type"));
    }

    @Test
    public void testToJSON_returnsIncorrectType() {
        
        JSONObject json = teacher.toJSON();

        
        assertNotNull(json);
        assertNotEquals("Student", json.get("type"));
        assertNotEquals("", json.get("type"));
        assertNotEquals(null, json.get("type"));
    }
} 