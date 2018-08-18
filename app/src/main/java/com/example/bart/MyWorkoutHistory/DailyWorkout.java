package com.example.bart.MyWorkoutHistory;

import android.util.Log;

import org.joda.time.LocalDate;
import java.io.Serializable;

import static android.content.ContentValues.TAG;

/**
 * Created by bart on 25.03.2018.
 */

public class DailyWorkout implements Serializable {
    LocalDate currentDate;
    double[] exercises = new double[5];
    long workoutTimeInMiliseconds;
    int workoutTimeInMinutes;


    public DailyWorkout() {
        currentDate = LocalDate.now();
//        exercises = new double[5];
        workoutTimeInMiliseconds = 0;
        workoutTimeInMinutes = 0;
    }

    //one more constructor to load object from csv line
    public DailyWorkout(String csvLine, String loadedAppVersion) {
        Log.i(TAG, "DailyWorkout constructor — Loading from version: " + loadedAppVersion);
        if(loadedAppVersion.equals("v1")) {
            currentDate = LocalDate.now();
 //           exercises = new double[5];
            String[] split = csvLine.split(", ");
            currentDate = LocalDate.parse(split[0]);
            workoutTimeInMinutes = Integer.parseInt(split[1]);
            for (int i = 0; i < 5; i++) {
                exercises[i] = Double.valueOf(split[i + 2]);
            }
        }

    }


    public boolean addReps(int exerciseId, double reps) {
        exercises[exerciseId] = reps;
        return true;
    }

    public Double readReps(int exersiseID) {
        return exercises[exersiseID];
    }

    public long readWorkoutTime() { return workoutTimeInMiliseconds;}

    public void addWourkoutTime(long timeToSetInMiliseconds) {
        workoutTimeInMiliseconds = workoutTimeInMiliseconds + timeToSetInMiliseconds;
        workoutTimeInMinutes = (int) (workoutTimeInMiliseconds / 60000);
    }

    public String toString() {
        String result;
        result = currentDate.toString() + "\t\t\t";
        for (int i=0; i< exercises.length; i++ ) result = result + Double.toString(exercises[i]) + "\t\t\t";
        result = result + "\n";
        return result;
    }

    public String toCsv() {
        String result;
        result = currentDate.toString() + ", ";
        result = result + workoutTimeInMinutes + ", ";
        for (int i=0; i< exercises.length; i++ ) result = result + Double.toString(exercises[i]) + ", ";
        result = result + "\n";
        return result;
    }
}
