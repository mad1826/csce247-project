package com.model;

import java.util.UUID;

import org.json.simple.JSONArray;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.model.datahandlers.DataLoader;
import com.model.managers.CourseManager;

/**
 * Tests for the CourseManager class.
 * @author Michael Davis
 */
public class CourseManagerTest {
	@Test
	public void testCreateValidCourse1() {
		MusicAppFacade facade = MusicAppFacade.getInstance();
		facade.login("john.doe@example.com", "p@ssw0rd123");
		Teacher teacher = facade.getCurrentTeacher();
		String courseCode = "n3wcourse";
		String courseTitle = "Music for Seniors";
		OperationResult<Course> courseResult = facade.createCourse(courseCode, courseTitle);
		assertTrue(courseResult.success);
		Course course = courseResult.result;
		assertEquals(course.getCode(), courseCode);
		assertEquals(course.getTitle(), courseTitle);
		assertTrue(teacher.getCourses().contains(course));
	}

	@Test
	public void testCreateValidCourse2() {
		MusicAppFacade facade = MusicAppFacade.getInstance();
		facade.login("john.doe@example.com", "p@ssw0rd123");
		String courseCode = "12345course";
		String courseTitle = "International Songs";
		OperationResult<Course> courseResult = facade.createCourse(courseCode, courseTitle);
		assertTrue(courseResult.success);
		Course course = courseResult.result;
		assertEquals(course.getCode(), courseCode);
		assertEquals(course.getTitle(), courseTitle);
	}

	@Test
	public void testCreateCourseWithNullArguments() {
		MusicAppFacade facade = MusicAppFacade.getInstance();
		facade.login("john.doe@example.com", "p@ssw0rd123");
		OperationResult<Course> courseResult = facade.createCourse(null, null);
		assertFalse(courseResult.success);
	}

	@Test
	public void testCreateCourseWithDuplicateCode() {
		MusicAppFacade facade = MusicAppFacade.getInstance();
		facade.login("john.doe@example.com", "p@ssw0rd123");
		String courseCode = "FA2024MUSC101";
		String courseTitle = "Fall 24 Music 101";
		OperationResult<Course> courseResult = facade.createCourse(courseCode, courseTitle);
		assertFalse(courseResult.success);
	}

	@Test
	public void testCreateCourseWithDuplicateTitle() {
		MusicAppFacade facade = MusicAppFacade.getInstance();
		facade.login("john.doe@example.com", "p@ssw0rd123");
		String courseCode = "abc123";
		String courseTitle = "Fall 2024 MUSC 101";
		OperationResult<Course> courseResult = facade.createCourse(courseCode, courseTitle);
		assertTrue(courseResult.success);
	}

	@Test
	public void testToJSON() {
		DataLoader.loadAllData();
		JSONArray coursesJSON = CourseManager.getInstance().toJSON();
		assertEquals(coursesJSON.size(), CourseManager.getInstance().getCourses().size());
	}

	@Test
	public void testDeleteValidCourseById() {
		DataLoader.loadAllData();
		UUID courseId = UUID.fromString("712a41c0-2cd2-45d0-ade5-727d5261c0c5");
		boolean success = CourseManager.getInstance().deleteCourse(courseId);
		assertTrue(success);
		assertFalse(CourseManager.getInstance().getCourses().containsKey(courseId));
	}

	@Test
	public void testDeleteCourseByInvalidId() {
		DataLoader.loadAllData();
		boolean success = CourseManager.getInstance().deleteCourse(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));
		assertFalse(success);
	}

	@Test
	public void testDeleteCourseByIdTwice() {
		DataLoader.loadAllData();
		UUID courseId = UUID.fromString("712a41c0-2cd2-45d0-ade5-727d5261c0c5");
		boolean success1 = CourseManager.getInstance().deleteCourse(courseId);
		boolean success2 = CourseManager.getInstance().deleteCourse(courseId);
		assertTrue(success1);
		assertFalse(success2);
		assertFalse(CourseManager.getInstance().getCourses().containsKey(courseId));
	}

	@Test
	public void testDeleteCourseByNullId() {
		DataLoader.loadAllData();
		boolean success = CourseManager.getInstance().deleteCourse(null);
		assertFalse(success);
	}
}
