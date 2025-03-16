package com.tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import com.model.Clef;
import com.model.Course;
import com.model.CourseManager;
import com.model.Difficulty;
import com.model.Instrument;
import com.model.InstrumentType;
import com.model.SheetMusic;
import com.model.Song;
import com.model.Teacher;
import com.model.datahandlers.DataWriter;

public class CourseWriterTest {
	public static void main(String[] args) {
		CourseManager courseManager = CourseManager.getInstance();
		Course course = courseManager.createCourse(UUID.randomUUID(), "Hi", new Teacher("Mary", "Sue", "abc@gmail.com", "$1982347Ddj"));
		
		Instrument instrument = new Instrument(InstrumentType.PIANO, 1, 1);
		SheetMusic sheet = new SheetMusic(instrument, Difficulty.BEGINNER, Clef.BASS, true, new ArrayList<>(), true);
		HashMap<Instrument, SheetMusic> sheets = new HashMap<>();
		sheets.put(instrument, sheet);
		Song song = new Song("Sandstorm", "Darude", new ArrayList<>(), sheets);
		course.createLesson("My Lesson", song, instrument.getType());

		DataWriter.setData(courseManager);
	}
}
