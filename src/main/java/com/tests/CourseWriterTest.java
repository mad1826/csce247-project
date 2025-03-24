package com.tests;

import java.util.ArrayList;
import java.util.HashMap;

import com.model.Clef;
import com.model.Course;
import com.model.Difficulty;
import com.model.Instrument;
import com.model.InstrumentType;
import com.model.OperationResult;
import com.model.SheetMusic;
import com.model.Song;
import com.model.Teacher;
import com.model.DataHandlers.DataWriter;
import com.model.managers.CourseManager;

public class CourseWriterTest {
	public static void main(String[] args) {
		CourseManager courseManager = CourseManager.getInstance();
		Teacher teacher = new Teacher("Mary", "Sue", "abc@gmail.com", "$1982347Ddj");
		OperationResult<Course> courseResult = teacher.createCourse("MUSC 101", "Intro to Music");

		if (!courseResult.success) {
			System.out.println(courseResult.message);
			return;
		}

		Course course = courseResult.result;
		
		Instrument instrument = new Instrument(InstrumentType.PIANO);
		SheetMusic sheet = new SheetMusic(instrument, Difficulty.BEGINNER, Clef.BASS, true, new ArrayList<>(), true);
		HashMap<Instrument, SheetMusic> sheets = new HashMap<>();
		sheets.put(instrument, sheet);
		Song song = new Song("Sandstorm", "Darude", new ArrayList<>(), sheets);
		course.createLesson("My Lesson", song, instrument.getType(),1);

		DataWriter.setData(courseManager);
	}
}
