package com.model.datahandlers;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

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
     * @param data the data to be written
     * @return An operation result saying if the operation was successful.
     */
    public static <T> OperationResult<HashMap<UUID, T>> setData(SavableList<T> list) {
        try (FileWriter file = new FileWriter(list.getFilePath())) {
			String jsonString = list.toJSON();
			file.write(jsonString);
			file.flush();
			
			return new OperationResult<HashMap<UUID, T>>(list.toObjects(jsonString));
		}
		catch (IOException e) {
			e.printStackTrace();

			return new OperationResult<>("FileWriter Exception");
		}
    }
}