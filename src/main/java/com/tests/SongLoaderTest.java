package com.tests;

import java.util.UUID;

import com.model.MusicAppFacade;
import com.model.OperationResult;
import com.model.Song;

public class SongLoaderTest {
    public static void main(String[] args) {
        UUID testID = UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479");

        MusicAppFacade f = MusicAppFacade.getInstance();
        OperationResult<Song> s = f.getSong(testID);

        if (s.success)
            System.out.println(s.result);
        else
            System.out.println(s.message);
    }
}