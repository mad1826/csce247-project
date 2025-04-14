package com.model;

import java.util.UUID;

import org.json.simple.JSONObject;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Tests for the Lesson class.
 * @author Michael Davis
 */
public class LessonTest {
	@Test
	public void testHasOwnId() {
		MusicAppFacade facade = MusicAppFacade.getInstance();
		Lesson lesson = getLesson(facade);

		assertNotNull(lesson);
		assertTrue(lesson.hasId(lesson.getId()));
	}

	@Test
	public void testHasInvalidId() {
		MusicAppFacade facade = MusicAppFacade.getInstance();
		Lesson lesson = getLesson(facade);

		assertNotNull(lesson);
		assertFalse(lesson.hasId(UUID.randomUUID()));
	}

	@Test
	public void testHasNullId() {
		MusicAppFacade facade = MusicAppFacade.getInstance();
		Lesson lesson = getLesson(facade);

		assertNotNull(lesson);
		assertFalse(lesson.hasId(null));
	}

	@Test
	public void testGetSheet() {
		MusicAppFacade facade = MusicAppFacade.getInstance();
		Lesson lesson = getLesson(facade);
		assertNotNull(lesson);

		SheetMusic lessonSheet = lesson.getSheet();

		SheetMusic songSheet = facade.getSong(UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479")).result.getSheet("Piano");

		assertEquals(lessonSheet, songSheet);
		assertEquals(lessonSheet.getInstrument().getType(), InstrumentType.PIANO);
	}

	@Test
	public void testCorrectToFeedback() {
		MusicAppFacade facade = MusicAppFacade.getInstance();
		Lesson lesson = getLesson(facade);
		assertNotNull(lesson);

		int studentProgress = facade.getCurrentStudent().getLessonProgress(lesson);
		String progressString = lesson.toFeedback(studentProgress);
		String expectedProgressString =  "Lesson: Making Masterpieces Pt. 1\nSong: Happy Birthday by Traditional\nInstrument Played: Piano\nTimes Completed: " + studentProgress + "/3\nComplete? No";

		assertEquals(progressString, expectedProgressString);
	}

	@Test
	public void testIncorrectToFeedback() {
		MusicAppFacade facade = MusicAppFacade.getInstance();
		Lesson lesson = getLesson(facade);
		assertNotNull(lesson);

		int studentProgress = facade.getCurrentStudent().getLessonProgress(lesson);
		String progressString = lesson.toFeedback(studentProgress + 1);
		String expectedProgressString =  "Lesson: Making Masterpieces Pt. 1\nSong: Happy Birthday by Traditional\nInstrument Played: Piano\nTimes Completed: " + studentProgress + "/3\nComplete? No";

		assertNotEquals(progressString, expectedProgressString);
	}

	@Test
	public void testToJSON() {
		MusicAppFacade facade = MusicAppFacade.getInstance();
		Lesson lesson = getLesson(facade);
		assertNotNull(lesson);
		JSONObject lessonJSON = lesson.toJSON();
		assertEquals(lessonJSON.get("id"), lesson.getId().toString());
		assertEquals(lessonJSON.get("title"), lesson.getTitle());
		assertEquals(lessonJSON.get("instrument"), lesson.getSheet().getInstrument().getType().getName());
		assertEquals(lessonJSON.get("numberOfTimes"), lesson.getNumberOfTimes());
	}

	private Lesson getLesson(MusicAppFacade facade) {
		facade.login("ffredrickson@gmail.com", "secureP@ss987");

		Course course = facade.getCourse(UUID.fromString("712a41c0-2cd2-45d0-ade5-727d5261c0c5"));
		Lesson lesson = null;
		for (Lesson courseLesson : course.getLessons().values()) {
			if (courseLesson.hasId(UUID.fromString("24d85a1d-e68c-4961-a43c-f9d02a7483c0")))
				lesson = courseLesson;
		}

		return lesson;
	}
}