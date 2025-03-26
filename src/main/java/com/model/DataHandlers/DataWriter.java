package com.model.datahandlers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;

import com.model.Measure;
import com.model.OperationResult;
import com.model.SavableList;
import com.model.SheetMusic;

/**
 * Handles writing data to JSON files.
 * 
 * @author Matt Carey
 */

public class DataWriter {
    /**
     * Handles writing data to JSON files.
     *
     * @param list Defines the list that is writing the data
     * @return An operation result saying if the operation was successful.
     */
    public static <T> OperationResult<JSONArray> setData(SavableList<T> list) {
        try (FileWriter file = new FileWriter(list.getFilePath())) {
			JSONArray jsonArray = list.toJSON();
			file.write(jsonArray.toJSONString());
			file.flush();
			
			return new OperationResult<>(jsonArray);
		}
		catch (IOException e) {
			e.printStackTrace();

			return new OperationResult<>("FileWriter Exception");
		}
    }

    /**
     * Writes song to a new file
     * 
     * @param path Where to save the file
     * @param song Song Object to write
     * @author Matt Carey
     * @author Ryan Smith
     */
    public static void ExportSheet(String path, SheetMusic s) {
        File f = new File(path);

        String music = "";
        
        for (Measure m : s.getMeasures()) {
            String myStr = m.toString();
            music = music+" "+myStr;

            System.out.println("Adding: "+myStr);
        }

        try (FileWriter writer = new FileWriter(f)) {
            writer.write(music);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }
}