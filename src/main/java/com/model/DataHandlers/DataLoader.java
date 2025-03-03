package com.model.datahandlers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.UUID;

import com.model.OperationResult;
import com.model.SavableList;

public class DataLoader {

    static <T> OperationResult<HashMap<UUID, T>> getData(SavableList<T> list) {
        Path p = Paths.get(list.getFilePath());

        String fileData = "";
        try {
            fileData = new String(Files.readAllBytes(p), StandardCharsets.UTF_8);

        } catch (IOException e) {
            return new OperationResult<>("Error reading file " + p.getFileName(), e);
        }

        return new OperationResult<>(list.toObjects(fileData));
    }

    // public static void main(String[] args) { //tester
    //     testHandler t = new testHandler();
    //     for (testObject o : t.getObjects()) {
    //         System.out.println(o.test);
    //     }
    // }
}

//below is example of using data loader.
// class testHandler implements SavableList<testObject> {
//     private final String JSON_PATH = "json/courses.json";
//     private ArrayList<testObject> objects;
//     testHandler() {
//         initData();
//     }
//     private void initData() {
//         OperationResult<ArrayList<testObject>> o = DataLoader.getData(this);
//         if (o.success)
//             this.objects = o.result;
//         else
//             System.out.println("bad");
//     }
//     @Override
//     public ArrayList<testObject> toObjects(String s) {
//         ArrayList<testObject> arr = new ArrayList<>();
//         for (String s2 : s.split(",")) {
//             arr.add(new testObject(s2));
//         }
//         return arr;
//     }
//     @Override
//     public String toJSON(testObject o) {
//         return "";
//     }
//     @Override
//     public String getFilePath() {
//         return JSON_PATH;
//     }
//     public ArrayList<testObject> getObjects() {
//         return this.objects;
//     }
// }
// class testObject {
//     String test = "";
//     public testObject(String t) {
//         this.test = t;
//     }
// }
