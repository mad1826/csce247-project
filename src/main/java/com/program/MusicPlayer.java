package com.program;

import com.musicapp.*;
import java.lang.Thread;

public class MusicPlayer {

    public void playSong() {
        try {
            playLine1();
            playLine2();
            playLine3();
            playLine4();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void playLine1() {
        System.out.println("Head, shoulders, knees, and toes");
        Music.playNote("C");  // "Head"
        Music.playNote("D");  // "shoulders"
        Music.playNote("E");  // "knees"
        Music.playNote("D");  // "and"
        Music.playNote("C");  // "toes"
    }

    private void playLine2() {
        System.out.println("Head, shoulders, knees, and toes");
        Music.playNote("C");  // "Head"
        Music.playNote("D");  // "shoulders"
        Music.playNote("E");  // "knees"
        Music.playNote("D");  // "and"
        Music.playNote("C");  // "toes"
    }

    private void playLine3() {
        System.out.println("Eyes and ears and mouth and nose");
        Music.playNote("E");  // "Eyes"
        Music.playNote("F");  // "and"
        Music.playNote("G");  // "ears"
        Music.playNote("F");  // "and"
        Music.playNote("E");  // "mouth"
        Music.playNote("D");  // "and"
        Music.playNote("C");  // "nose"
    }

    private void playLine4() {
        System.out.println("Head, shoulders, knees, and toes");
        Music.playNote("C");  // "Head"
        Music.playNote("D");  // "shoulders"
        Music.playNote("E");  // "knees"
        Music.playNote("D");  // "and"
        Music.playNote("C");  // "toes"
    }

    public static void main(String[] args) {
        MusicPlayer player = new MusicPlayer();
        player.playSong();
    }
}