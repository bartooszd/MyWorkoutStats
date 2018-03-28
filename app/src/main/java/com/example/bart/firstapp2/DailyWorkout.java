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
        exercises = new double[5];
    }

    public boolean addReps(int exerciseId, double reps) {
        exercises[exerciseId] = reps;
        return true;
    }

    public Double readReps(int exersiseID) {
        return exercises[exersiseID];
    }
}
