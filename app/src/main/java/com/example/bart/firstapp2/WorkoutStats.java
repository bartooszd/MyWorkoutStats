package com.example.bart.firstapp2;

import android.util.Log;

import org.joda.time.LocalDate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.io.Serializable;

import static android.content.ContentValues.TAG;

/**
 * Created by bart on 25.03.2018.
 */

public class WorkoutStats implements Serializable{

    LocalDate lastEditDate = new LocalDate();
    String[] exerciseName = {"pompki", "brzuski", "przysiady", "rower", "wiosla"};
    LinkedList<DailyWorkout> myList = new LinkedList<DailyWorkout>();


    public WorkoutStats() {
        lastEditDate = LocalDate.now();
        myList.add(new DailyWorkout());
    }

    public void addWorkout(int exerciseID, double reps) {
        LocalDate currentDate = LocalDate.now();
        Log.i(TAG, "MyLog.addWorkout() — adding exercise " + exerciseID + " reps: " + reps);
        Log.i(TAG, "MyLog.addWorkout() — currentDate is " + currentDate);

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
    public Double getWorkout(int exerciseID) {
        LocalDate currentDate = LocalDate.now();
        Double result = 0.0;

        if(currentDate.compareTo(lastEditDate)==0) {
            Log.i(TAG, "MyLog.getWorkout() — dates are equal");
            result = myList.getLast().readReps(exerciseID);
        }

        return result;
    }

    public void saveObject(){
        try
        {
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
        try
        {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("/sdcard/my_workouts_saved.bin")));
            WorkoutStats ws = (WorkoutStats) ois.readObject();
            this.lastEditDate = ws.lastEditDate;
            this.myList = ws.myList;
            Log.i(TAG, "MyLog.loadSerializedObject() — loaded reps for exercize 0: " + ws.myList.getLast().exercises[0]);
            Log.i(TAG, "MyLog.loadSerializedObject() — loaded reps for exercize 0: " + myList.getLast().exercises[0]);
        }
        catch(Exception ex)
        {
            Log.v("Ser Read Error : ",ex.getMessage());
            ex.printStackTrace();
        }
    }
}

