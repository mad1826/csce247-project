package com.model.datahandlers;

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
    static <T> OperationResult<Void> setData(SavableList<T> list, HashMap<UUID,T> data) {
        for (UUID key : data.keySet()){
            list.toJSON(data.get(key));
        }
        
        return new OperationResult<>("Not implemented");
    }
}