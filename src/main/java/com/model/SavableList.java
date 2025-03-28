package com.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
    @SuppressWarnings("exports")
	JSONArray toJSON();

    /**
     * Defines how the json should be converted into an object instance.
     * @return the object instance of the generic type
     */
    T toObject(@SuppressWarnings("exports") JSONObject object);


    /**
     * Loads data into manager classes, recursive references stored as UUIDs
     * @return OperationResult depicting outcome
     */
    OperationResult<Void> loadData();


    /**
     * Links data to other loaded data
     * @return
     */
    OperationResult<Void> linkData();

	/**
	 * Write this list's data to the file path.
	 */
	@SuppressWarnings("exports")
	OperationResult<JSONArray> save();
}