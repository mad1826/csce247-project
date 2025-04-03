package com.model;

import java.util.UUID;

import org.json.simple.JSONObject;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Tests for the Course class.
 * @author Michael Davis
 */
public class CourseTest {
	@Test
	public void testValidGetCourseById1() {
		MusicAppFacade facade = MusicAppFacade.getInstance();
		assertNotNull(facade.getCourse(UUID.fromString("dd957d62-de3a-4dc5-b3fd-530d300cb80d")));
	}
	@Test
	public void testValidGetCourseById2() {
		MusicAppFacade facade = MusicAppFacade.getInstance();
		assertNotNull(facade.getCourse(UUID.fromString("712a41c0-2cd2-45d0-ade5-727d5261c0c5")));
	}

	@Test
	public void testGetCourseByInvalidId() {
		MusicAppFacade facade = MusicAppFacade.getInstance();
		assertNull(facade.getCourse(UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479")));
	}

	@Test
	public void testGetCourseByNullId() {
		MusicAppFacade facade = MusicAppFacade.getInstance();
		assertNull(facade.getCourse(null));
	}

	@Test
	public void testSetValidCourseTitle1() {
		MusicAppFacade facade = MusicAppFacade.getInstance();
		Course course = facade.getCourse(UUID.fromString("dd957d62-de3a-4dc5-b3fd-530d300cb80d"));
		String newTitle = "Course 2: The Sequel";
		boolean success = course.setTitle(newTitle);
		assertEquals(course.getTitle(), newTitle);
		assertTrue(success);
	}

	@Test
	public void testSetValidCourseTitle2() {
		MusicAppFacade facade = MusicAppFacade.getInstance();
		Course course = facade.getCourse(UUID.fromString("dd957d62-de3a-4dc5-b3fd-530d300cb80d"));
		String newTitle = "123 Course 4 U";
		boolean success = course.setTitle(newTitle);
		assertEquals(course.getTitle(), newTitle);
		assertTrue(success);
	}

	@Test
	public void testSetDuplicateCourseTitle() {
		MusicAppFacade facade = MusicAppFacade.getInstance();
		Course course = facade.getCourse(UUID.fromString("dd957d62-de3a-4dc5-b3fd-530d300cb80d"));
		boolean success = course.setTitle(course.getTitle());
		assertFalse(success);
	}

	@Test
	public void testSetEmptyCourseTitle() {
		MusicAppFacade facade = MusicAppFacade.getInstance();
		Course course = facade.getCourse(UUID.fromString("dd957d62-de3a-4dc5-b3fd-530d300cb80d"));
		boolean success = course.setTitle("");
		assertFalse(success);
	}

	@Test
	public void testSetNullCourseTitle() {
		MusicAppFacade facade = MusicAppFacade.getInstance();
		Course course = facade.getCourse(UUID.fromString("dd957d62-de3a-4dc5-b3fd-530d300cb80d"));
		boolean success = course.setTitle(null);
		assertFalse(success);
	}

	@Test
	public void testAddValidStudent() {
		MusicAppFacade facade = MusicAppFacade.getInstance();
		Course course = facade.getCourse(UUID.fromString("dd957d62-de3a-4dc5-b3fd-530d300cb80d"));
		facade.signUp("Jack", "Smith", "abc@gmail.com", "abc12345E!", false);
		Student student = facade.getCurrentStudent();
		boolean result = course.addMember(student);
		facade.logout();
		assertTrue(result);
		assertTrue(course.getMembers().contains(student));
		assertTrue(student.getCourses().contains(course));
	}

	@Test
	public void testAddDuplicateStudent() {
		MusicAppFacade facade = MusicAppFacade.getInstance();
		Course course = facade.getCourse(UUID.fromString("dd957d62-de3a-4dc5-b3fd-530d300cb80d"));
		facade.signUp("Dan", "Smith", "def@gmail.com", "abc12345E!", false);
		Student student = facade.getCurrentStudent();
		boolean success1 = course.addMember(student);
		boolean success2 = course.addMember(student);
		facade.logout();
		assertTrue(success1);
		assertFalse(success2);
	}

	@Test
	public void testAddNullStudent() {
		MusicAppFacade facade = MusicAppFacade.getInstance();
		Course course = facade.getCourse(UUID.fromString("dd957d62-de3a-4dc5-b3fd-530d300cb80d"));
		boolean result = course.addMember(null);
		assertFalse(result);
	}

	@Test
	public void testRemoveValidMember() {
		MusicAppFacade facade = MusicAppFacade.getInstance();
		Course course = facade.getCourse(UUID.fromString("712a41c0-2cd2-45d0-ade5-727d5261c0c5"));
		facade.login("ffredrickson@gmail.com", "secureP@ss987");
		Student student = facade.getCurrentStudent();
		boolean success = course.removeMember(student);
		assertTrue(success);
	}

	@Test
	public void testRemoveMemberTwice() {
		MusicAppFacade facade = MusicAppFacade.getInstance();
		Course course = facade.getCourse(UUID.fromString("712a41c0-2cd2-45d0-ade5-727d5261c0c5"));
		facade.login("ffredrickson@gmail.com", "secureP@ss987");
		Student student = facade.getCurrentStudent();
		boolean success1 = course.removeMember(student);
		boolean success2 = course.removeMember(student);
		assertTrue(success1);
		assertFalse(success2);
	}

	@Test
	public void testRemoveNullMember() {
		MusicAppFacade facade = MusicAppFacade.getInstance();
		Course course = facade.getCourse(UUID.fromString("712a41c0-2cd2-45d0-ade5-727d5261c0c5"));
		boolean success = course.removeMember(null);
		assertFalse(success);
	}

	@Test
	public void testCreateValidLesson1() {
		MusicAppFacade facade = MusicAppFacade.getInstance();
		Course course = facade.getCourse(UUID.fromString("dd957d62-de3a-4dc5-b3fd-530d300cb80d"));

		int oldLessonNumber = course.getLessons().size();

		String title = "New Lesson 1";
		Song song = facade.getSong(UUID.fromString("564dcc08-e886-4ed1-95eb-6cd1e689e06e")).result;
		InstrumentType instrumentType = InstrumentType.GUITAR;
		int numberOfTimes = 5;
		OperationResult<Lesson> lessonResult = course.createLesson(title, song, instrumentType, numberOfTimes);
		Lesson lesson = lessonResult.result;

		assertTrue(lessonResult.success);
		assertEquals(title, lesson.getTitle());
		assertEquals(instrumentType, lesson.getSheet().getInstrument().getType());
		assertEquals(numberOfTimes, lesson.getNumberOfTimes());
		assertEquals(oldLessonNumber + 1, course.getLessons().size());
	}

	@Test
	public void testCreateValidLesson2() {
		MusicAppFacade facade = MusicAppFacade.getInstance();
		Course course = facade.getCourse(UUID.fromString("dd957d62-de3a-4dc5-b3fd-530d300cb80d"));

		int oldLessonNumber = course.getLessons().size();

		String title = "My Second Lesson";
		Song song = facade.getSong(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6")).result;
		InstrumentType instrumentType = InstrumentType.PIANO;
		int numberOfTimes = 4;
		OperationResult<Lesson> lessonResult = course.createLesson(title, song, instrumentType, numberOfTimes);
		Lesson lesson = lessonResult.result;

		assertTrue(lessonResult.success);
		assertEquals(title, lesson.getTitle());
		assertEquals(instrumentType, lesson.getSheet().getInstrument().getType());
		assertEquals(numberOfTimes, lesson.getNumberOfTimes());
		assertEquals(oldLessonNumber + 1, course.getLessons().size());
	}

	@Test
	public void testToJSON() {
		MusicAppFacade facade = MusicAppFacade.getInstance();
		Course course = facade.getCourse(UUID.fromString("dd957d62-de3a-4dc5-b3fd-530d300cb80d"));
		JSONObject courseJSON = course.toJSON();
		assertEquals(course.getId().toString(), courseJSON.get("id"));
		assertEquals(course.getTitle(), courseJSON.get("title"));
		assertEquals(course.getCode(), courseJSON.get("code"));
	}

	@Test
	public void testDeleteFirstValidLesson() {
		MusicAppFacade facade = MusicAppFacade.getInstance();
		Course course = facade.getCourse(UUID.fromString("712a41c0-2cd2-45d0-ade5-727d5261c0c5"));
		boolean success = course.deleteLesson(UUID.fromString("24d85a1d-e68c-4961-a43c-f9d02a7483c0"));
		assertTrue(success);
	}

	@Test
	public void testDeleteLastValidLesson() {
		MusicAppFacade facade = MusicAppFacade.getInstance();
		Course course = facade.getCourse(UUID.fromString("712a41c0-2cd2-45d0-ade5-727d5261c0c5"));
		boolean success = course.deleteLesson(UUID.fromString("42011ee3-1ad6-465a-b14c-44cd72f6491e"));
		assertTrue(success);
	}

	@Test
	public void testDeleteLessonTwice() {
		MusicAppFacade facade = MusicAppFacade.getInstance();
		Course course = facade.getCourse(UUID.fromString("dd957d62-de3a-4dc5-b3fd-530d300cb80d"));
		UUID lessonId = UUID.fromString("564dcc08-e886-4ed1-95eb-6cd1e689e06e");
		boolean success1 = course.deleteLesson(lessonId);
		boolean success2 = course.deleteLesson(lessonId);
		assertTrue(success1);
		assertFalse(success2);
	}

	@Test
	public void testDeleteInvalidLessonId() {
		MusicAppFacade facade = MusicAppFacade.getInstance();
		Course course = facade.getCourse(UUID.fromString("dd957d62-de3a-4dc5-b3fd-530d300cb80d"));
		boolean success = course.deleteLesson(UUID.fromString("dd957d62-de3a-4dc5-b3fd-530d300cb80d"));
		assertFalse(success);
	}

	@Test
	public void testDeleteNullLessonId() {
		MusicAppFacade facade = MusicAppFacade.getInstance();
		Course course = facade.getCourse(UUID.fromString("dd957d62-de3a-4dc5-b3fd-530d300cb80d"));
		boolean success = course.deleteLesson(null);
		assertFalse(success);
	}

	@Test
	public void testDeleteValidCourse() {
		MusicAppFacade facade = MusicAppFacade.getInstance();
		UUID id = UUID.fromString("dd957d62-de3a-4dc5-b3fd-530d300cb80d");
		Course course = facade.getCourse(id);
		boolean success = course.delete();
		assertNull(facade.getCourse(id));
		assertTrue(success);
	}

	@Test
	public void testDeleteCourseTwice() {
		MusicAppFacade facade = MusicAppFacade.getInstance();
		UUID id = UUID.fromString("712a41c0-2cd2-45d0-ade5-727d5261c0c5");
		Course course = facade.getCourse(id);
		boolean success1 = course.delete();
		boolean success2 = course.delete();
		assertNull(facade.getCourse(id));
		assertTrue(success1);
		assertFalse(success2);
	}
}
