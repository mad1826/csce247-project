package com.model.DataHandlers;

import java.io.FileReader;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.model.OperationResult;
import com.model.SavableList;
import com.model.managers.CourseManager;
import com.model.managers.SongManager;
import com.model.managers.UserManager;

/**
 * Handles loading data from a JSON file to any savable list
 * 
 * @author Matt Carey
 */

public class DataLoader {
	/**
	 * Whether the data has been loaded
	 */
    private static boolean isLoaded = false;

	/**
	 * Gets all data from a list
	 * @param <T> the object the list contains
	 * @param list the list of objects
	 * @return the result of getting a list of every object
	 */
    @SuppressWarnings("UseSpecificCatch")
    public static <T> OperationResult<ArrayList<T>> getData(SavableList<T> list) {
        try {
            ArrayList<T> result = new ArrayList<>();

            FileReader reader = new FileReader(list.getFilePath()); //const reader

            JSONArray jsonData = (JSONArray) new JSONParser().parse(reader); //parse file into JSONArray

            for (int i = 0; i < jsonData.size(); i++) { //iterate array\
                JSONObject o = (JSONObject) jsonData.get(i); //get object in array

                T object = list.toObject(o); //call class specific behavior on how to construct object from json data

                result.add(object); //append result
            }

            return new OperationResult<>(result);
        } catch (Exception e) {
            System.out.println(e.toString());
            return new OperationResult<>("Unable to load data from loader.",e);
        }
    }
    
    /**
     * To be called on initialization to load data from files into all classes.
     * @return the result of loading all data
     */
    public static OperationResult<Void> loadAllData() {
        if (isLoaded) {
            return new OperationResult<>("Failed to load, data has already been loaded");
        }

        // Index all savable lists we want to load
        ArrayList<SavableList<?>> handlers = new ArrayList<>();
        handlers.add(UserManager.getInstance());
        handlers.add(CourseManager.getInstance());
        handlers.add(SongManager.getInstance());

        // Load all data from files
        for (SavableList<?> list : handlers) {
            OperationResult<Void> or = list.loadData();

            if (!or.success) {
                return or;
            }
        }

        // Link all loaded data
        for (SavableList<?> list : handlers) {
            OperationResult<Void> or = list.linkData();

            if (!or.success) {
                return or;
            }
        }

        isLoaded = true;

        return new OperationResult<>(true);
    }
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
