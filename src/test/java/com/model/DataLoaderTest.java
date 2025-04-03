package com.model;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.model.DataHandlers.DataLoader;

/**
 * @author Matt Carey
 */

public class DataLoaderTest {
    @Test
    public void testLoadAll() {
        OperationResult<Void> or = DataLoader.loadAllData();

        assertTrue(or.success);
    }

    @Test
    public void testLoadMultipleTimes() {
        DataLoader.loadAllData(); //load once

        OperationResult<Void> or = DataLoader.loadAllData(); //second load should not work
        assertFalse(or.success);
    }
}