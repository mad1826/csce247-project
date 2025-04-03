package com.model;

import org.junit.Test;

import com.model.OperationResult;
import com.model.DataHandlers.DataLoader;
import com.model.Chord;

public class DataLoaderTest {
    @Test
    public void testLoadAll() {
        OperationResult<Void> or = DataLoader.loadAllData();

        System.out.println(or);
    }
}