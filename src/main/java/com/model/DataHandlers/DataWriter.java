package com.model.datahandlers;

import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;

import com.model.OperationResult;
import com.model.SavableList;

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
}