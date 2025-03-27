// package com.tests;

// import java.util.UUID;

// import com.model.InstrumentType;
// import com.model.datahandlers.DataLoader;
// import com.model.datahandlers.DataWriter;
// import com.model.managers.SongManager;

// public class SheetExporterTest {
//     public static void main(String[] args) {
        
//         DataLoader.loadAllData();

//         // Get a test song ID 
//         UUID testID = UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479");
        
//         // Get the song from the manager
//         SongManager songManager = SongManager.getInstance();
//         var songResult = songManager.getSong(testID);

//         if (!songResult.success) {
//             System.out.println("Failed to load song: " + songResult.message);
//             return;
//         }

//         // Get the piano sheet from the song
//         var sheets = songResult.result.getSheets();
//         var pianoSheet = ; 

//         // Test exporting the sheet
//         String exportPath = "src/main/java/com/data/exported_sheet.txt";
//         DataWriter.ExportSheet(exportPath, pianoSheet);
//         System.out.println("Sheet exported to: " + exportPath);
//     }
// }