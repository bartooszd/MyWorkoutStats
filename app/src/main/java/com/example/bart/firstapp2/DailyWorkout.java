package com.example.bart.firstapp2;

import org.joda.time.LocalDate;

import java.io.Serializable;

/**
 * Created by bart on 25.03.2018.
 */

public class DailyWorkout implements Serializable {
    LocalDate currentDate;
    double[] exercises;

    public DailyWorkout() {
        currentDate = LocalDate.now();
        exercises = new double[4];
    }

    //one more constructor to load object from csv line
    public DailyWorkout(String csvLine) {
        currentDate = LocalDate.now();
        exercises = new double[4];
        String[] splitted = csvLine.split(", ");
        currentDate = LocalDate.parse(splitted[0]);
        for (int i=0; i<4; i++) {
            exercises[i] = Double.valueOf(splitted[i+1]);
        }
    }


    public boolean addReps(int exerciseId, double reps) {
        exercises[exerciseId] = reps;
        return true;
    }

    public Double readReps(int exersiseID) {
        return exercises[exersiseID];
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
        for (int i=0; i< exercises.length; i++ ) result = result + Double.toString(exercises[i]) + ", ";
        result = result + "\n";
        return result;
    }
}
