package com.tests;

import java.util.ArrayList;
import java.util.HashMap;

import com.model.Chord;
import com.model.Clef;
import com.model.Difficulty;
import com.model.Genre;
import com.model.Instrument;
import com.model.InstrumentType;
import com.model.Measure;
import com.model.Note;
import com.model.NoteValue;
import com.model.Pitch;
import com.model.PitchModifier;
import com.model.SheetMusic;
import com.model.Song;
import com.model.SongManager;
import com.model.datahandlers.DataWriter;

public class SongWriterTest {
	public static void main(String[] args) {
		SongManager songManager = SongManager.getInstance();

		ArrayList<Genre> genres = new ArrayList<>();
		genres.add(Genre.ROCK);
		genres.add(Genre.JAZZ);

		Instrument piano = new Instrument(InstrumentType.PIANO);
		Note note = new Note(Pitch.A, PitchModifier.NONE, NoteValue.SIXTEENTH, false, false, 2);
		ArrayList<Note> notes = new ArrayList<>();
		notes.add(note);
		Chord chord = new Chord("My First Chord", notes, false);
		ArrayList<Chord> chords = new ArrayList<>();
		chords.add(chord);
		Measure measure = new Measure(chords, 1, 50, 1, false, false);

		HashMap<Instrument, SheetMusic> sheets = new HashMap<>();
		SheetMusic sheet = new SheetMusic(piano, Difficulty.BEGINNER, Clef.BASS, false, new ArrayList<>(), false);
		sheet.addMeasure(measure);
		sheets.put(piano, sheet);
	
		Song song = new Song("Echoes of Time", "The Soundwaves", genres, sheets);
		songManager.createSong(song);
		
		DataWriter.setData(songManager);
	}
}
