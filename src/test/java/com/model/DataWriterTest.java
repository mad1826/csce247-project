package com.model;

import org.junit.Test;

import com.model.datahandlers.DataWriter;
import com.model.managers.CourseManager;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.json.simple.JSONArray;

/**
 * @author Matt Carey
 */

public class DataWriterTest {
    //Test case where a list is passed in
    @Test
    public void saveListValidList() {
        CourseManager c = CourseManager.getInstance();
        OperationResult<JSONArray> or = DataWriter.setData(c);

        assertTrue(or.success);
    }

    //Test null case
    @Test
    public void saveListNull() {
        OperationResult<JSONArray> or = DataWriter.setData(null);

        assertFalse(or.success);
    }
}