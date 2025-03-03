package com.model;

import java.util.HashMap;
import java.util.UUID;

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
    String toJSON();

    /**
     * Defines how the json should be converted into objects.
     * @return HashMap of objects contained in the JSON file
     */
    HashMap<UUID, T> toObjects(String json);
}