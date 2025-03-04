package com.tests;

import java.util.HashMap;
import java.util.UUID;

import com.model.Course;
import com.model.CourseManager;
import com.model.Teacher;
import com.model.datahandlers.DataWriter;

public class CourseWriterTest {
	public static void main(String[] args) {
		CourseManager courseManager = CourseManager.getInstance(new HashMap<>(), "course-output.json");
		Course course = courseManager.createCourse(UUID.randomUUID(), "Hi", new Teacher("Mary", "Sue", "abc@gmail.com", "$1982347Ddj"));
		course.createLesson("My Lesson");

		DataWriter.setData(courseManager);
	}
}
