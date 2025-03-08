package com.program;

import com.musicapp.*;
import org.jfugue.player.Player;

/**
 * the MusicPlayer class plays a simple melody using JFugue
 * in this case, it plays "Head, Shoulders, Knees, and Toes"
 * @author Makyia Irick
 */
public class MusicPlayer {

   /**
     * the main method that starts the program and plays the melody.
     */  
    public static void main(String[] args) {
        Player player = new Player();
        player.play("C C G G A A G R F F E E D D C");
      }
}