package com.model;

import java.util.ArrayList;

/**
 * Defines behavior for any class manager compatible with data loader and writer.
 * @author Matt Carey
 */

public interface SavableList<T> {
    /**
     * Returns the json file path this list is stored at.
     * @return filePath
     */
    String getFilePath();

    /**
     * Defines how the object should be converted into JSON.
     * @return jsonResult
     */
    String toJSON(T object);

    /**
     * Defines how the json should be converted into objects.
     * @return ArrayList of objects contained in the JSON file
     */
    ArrayList<T> toObjects(String json);

    ArrayList<T> getObjects();
}