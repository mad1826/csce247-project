package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class Course {
	private UUID id;

	private String title;

	// private Teacher owner;

	// private ArrayList<Student> members;

	private ArrayList<Lesson> lessons;

	public Course(UUID id, String title) {
		this.id = id;
		this.title = title;
		this.lessons = new ArrayList<>();
	}

	public Course(String title) {
		this.id = UUID.randomUUID();
		this.title = title;
		this.lessons = new ArrayList<>();
	}

	public String getTitle() {
		return title;
	}

	public boolean createLesson(String title) {
		lessons.add(new Lesson(title));
		return true;
	}

	public boolean deleteLesson(UUID lessonId) {
		for (Lesson lesson : lessons) {
			if (lesson.hasId(lessonId)) {
				lessons.remove(lesson);
				return true;
			}
		}
		return false;
	}
}
