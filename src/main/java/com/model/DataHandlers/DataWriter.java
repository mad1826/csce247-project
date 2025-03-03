package com.model.datahandlers;

import com.model.OperationResult;
import com.model.SavableList;

public class DataWriter {
    static <T> OperationResult<Void> setData(SavableList<T> list) {
        for (T obj : list.getObjects()) {
            String data = list.toJSON(obj);

            //TODO: write or append data somehow
        }
        
        return new OperationResult<>("Not implemented");
    }
}