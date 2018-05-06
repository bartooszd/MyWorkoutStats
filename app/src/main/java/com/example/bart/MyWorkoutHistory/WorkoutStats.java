package com.example.bart.MyWorkoutHistory;

import android.os.Environment;
import android.util.Log;

import org.joda.time.LocalDate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.io.Serializable;
import java.util.ListIterator;

import static android.content.ContentValues.TAG;

/**
 * Created by bart on 25.03.2018.
 */

public class WorkoutStats implements Serializable{
    private static final WorkoutStats instance = new WorkoutStats();

    LocalDate lastEditDate = new LocalDate();
    String[] exerciseName = {"pompki", "brzuski", "przysiady", "rower"};
    LinkedList<DailyWorkout> myList = new LinkedList<DailyWorkout>();


    // Private constructor prevents instantiation from other classes
    private WorkoutStats()  {
//        loadSampleData();
        // Load from saved file
        Log.i(TAG, "MyLog.WorkoutStats() — Loading serializing object from file");
        loadSerializedObject(); // serialize the object from file
        if (myList.size() == 0) {
            DailyWorkout newTraining = new DailyWorkout();
            myList.add(newTraining);
        }
    }

    public static WorkoutStats getInstance() {
        return instance;
    }

//    public WorkoutStats() {
//        lastEditDate = LocalDate.now();
//        myList.add(new DailyWorkout());
//    }

    public void addWorkout(int exerciseID, double reps) {
        LocalDate currentDate = LocalDate.now();
        Log.i(TAG, "MyLog.addWorkout() — adding exercise " + exerciseID + " reps: " + reps);
        Log.i(TAG, "MyLog.addWorkout() — currentDate is " + currentDate + " and last edit date is "+ lastEditDate);

        if(currentDate.compareTo(lastEditDate)==0) {
            Log.i(TAG, "MyLog.addWorkout() — dates are equal");

            myList.getLast().addReps(exerciseID, reps);

            lastEditDate = LocalDate.now();
        }
        else {
            Log.i(TAG, "MyLog.addWorkout() — dates are not equal");
            DailyWorkout newTraining = new DailyWorkout();
            newTraining.addReps(exerciseID, reps);
            myList.add(newTraining);
            lastEditDate = LocalDate.now();
        }
    }

    public void loadSampleData() {
        LinkedList<DailyWorkout> myList2 = new LinkedList<DailyWorkout>();

        DailyWorkout newTraining = new DailyWorkout();
        newTraining.addReps(0, 20.0);
        newTraining.addReps(2, 30.0);
        newTraining.currentDate = LocalDate.now().minusDays(2);
        myList2.add(newTraining);
        DailyWorkout newTraining2 = new DailyWorkout();
        newTraining2.addReps(1, 50.0);
        newTraining2.addReps(3, 1.5);
        newTraining2.currentDate = LocalDate.now().minusDays(1);
        myList2.add(newTraining2);

        myList = myList2;
    }

    public Double getWorkout(int exerciseID) {
        LocalDate currentDate = LocalDate.now();
        Double result = 0.0;

        if(currentDate.compareTo(lastEditDate)==0 && !myList.isEmpty()) {
            Log.i(TAG, "MyLog.getWorkout() — dates are equal");
            result = myList.getLast().readReps(exerciseID);
        }

        return result;
    }

    public void saveObject(){
        try
        {

            Log.i(TAG, "MyLog.saveObject — saving history: " + getHistory());
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("/sdcard/my_workouts_saved.bin")));
            oos.writeObject(this); // write the class as an 'object'
            oos.flush(); // flush the stream to insure all of the information was written to 'save_object.bin'
            oos.close();// close the stream
        }
        catch(Exception ex)
        {
            Log.v("Ser Save Error : ",ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void loadSerializedObject()
    {
        Log.i(TAG, "MyLog.loadSerializedObject() — loading history: " + getHistory());

        File savedFile = new File("/sdcard/my_workouts_saved.bin");
        if(savedFile.exists() && !savedFile.isDirectory()) {
            try
            {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(savedFile));
                WorkoutStats ws = (WorkoutStats) ois.readObject();
                this.lastEditDate = ws.lastEditDate;
                this.myList = ws.myList;
//                Log.i(TAG, "MyLog.loadSerializedObject() — loaded reps for exercize 0: " + ws.myList.getLast().exercises[0]);
                Log.i(TAG, "MyLog.loadSerializedObject() — loaded reps for exercize 0: " + myList.getLast().exercises[0]);
            }
            catch(Exception ex)
            {
                Log.v("Ser Read Error : ",ex.getMessage());
                ex.printStackTrace();
            }
        } else {
            Log.i(TAG, "MyLog.loadSerializedObject() - file not found so object will not be loaded");
        }

    }

    public String getHistory() {
        String result = "";

        ListIterator<DailyWorkout> litr = myList.listIterator();
        Log.i(TAG, "MyLog.getHistory() - size of list: " + myList.size());

        while (litr.hasNext()) {
            result = result + litr.next().toString();
        }
        Log.i(TAG, "MyLog.getHistory() - finished getting history");
        return result;
    }

    public void saveToCsv(String fileName) {
        FileOutputStream outputStream;
        String csvExport = "";
        String test = "test test";

        Log.i(TAG, "MyLog.saveToCsv() - FileName passed: " + fileName);

        // create a directory if it doesn't exist
        File directory = new File(Environment.getExternalStorageDirectory(), "MyWorkoutStats");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        Log.i(TAG, "MyLog.saveToCsv() - Created directory: " + directory.toString());

        //Create a file
        File fileReference = new File(directory.toString() + "/" + fileName + ".csv");

        //Create a string to save
        ListIterator<DailyWorkout> litr = myList.listIterator();
        Log.i(TAG, "MyLog.saveToCsv() - size of list: " + myList.size());

        // preparing string with all exercises in csv format
        while (litr.hasNext()) {
            csvExport = csvExport + litr.next().toCsv();
        }

        //Save to file
        try {
            Log.i(TAG, "MyLog.saveToCsv() - Will create new file: " + fileReference.toString());
            fileReference.createNewFile();
            Log.i(TAG, "MyLog.saveToCsv() - File created");
            //second argument of FileOutputStream constructor indicates whether to append or create new file if one exists
            outputStream = new FileOutputStream(fileReference, false);

            outputStream.write(csvExport.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadFromCsv(File fileReference) {
        String s;
        myList = new LinkedList<DailyWorkout>();

        try {
            FileInputStream fIn = new FileInputStream(fileReference);
            BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn));
            while ((s = myReader.readLine()) != null) {
                Log.i(TAG, "MyLog.onClicLoad() — Adding line to file: " + s);
                myList.add(new DailyWorkout(s));
            }
            myReader.close();

//            this.textView.setText(fileContent);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

