package com.example.bart.firstapp2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static android.content.ContentValues.TAG;

public class DailyWorkoutScreen extends AppCompatActivity {

    WorkoutStats myWorkoutStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_workout_screen);

        EditText eText;

        Log.i(TAG, "MyLog.MainActivity() — Will get Workout Stats");

        myWorkoutStats = WorkoutStats.getInstance();

        eText = (EditText) findViewById(R.id.ex1Value);
        Log.i(TAG, "MyLog.MainActivity() — Will load workout 1: " + myWorkoutStats.getWorkout(0).toString());
        eText.setText(myWorkoutStats.getWorkout(0).toString());
        eText = (EditText) findViewById(R.id.ex2Value);
        Log.i(TAG, "MyLog.MainActivity() — Will load workout 2");
        eText.setText(myWorkoutStats.getWorkout(1).toString());
        eText = (EditText) findViewById(R.id.ex3Value);
        eText.setText(myWorkoutStats.getWorkout(2).toString());
        eText = (EditText) findViewById(R.id.ex4Value);
        eText.setText(myWorkoutStats.getWorkout(3).toString());
    }

    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first

        myWorkoutStats.addWorkout(0, Double.valueOf(((EditText)findViewById(R.id.ex1Value)).getText().toString()));
        myWorkoutStats.addWorkout(1, Double.valueOf(((EditText)findViewById(R.id.ex2Value)).getText().toString()));
        myWorkoutStats.addWorkout(2, Double.valueOf(((EditText)findViewById(R.id.ex3Value)).getText().toString()));
        myWorkoutStats.addWorkout(3, Double.valueOf(((EditText)findViewById(R.id.ex4Value)).getText().toString()));

        Log.i(TAG, "MyLog.MainActivity() — serializing object to file");
        myWorkoutStats.saveObject(); // serialize the object to file
        Log.i(TAG, "MyLog.MainActivity() — Size of list after serializing: " + myWorkoutStats.myList.size());
    }

    public void onClickButton(View arg0) {
        EditText eText;
        Double ex1rep;
        Button btn = (Button)arg0;
        String increment = btn.getText().toString();
        Double incrementValue;
        int buttonId = btn.getId();
        int exerciseId = 100;

        if(buttonId == R.id.button1Ex1 || buttonId == R.id.button2Ex1 || buttonId == R.id.button3Ex1) {
            eText = findViewById(R.id.ex1Value);
            exerciseId = 0;
        } else if (buttonId == R.id.button1Ex2 || buttonId == R.id.button2Ex2 || buttonId == R.id.button3Ex2) {
            eText = findViewById(R.id.ex2Value);
            exerciseId = 1;
        } else if (buttonId == R.id.button1Ex3 || buttonId == R.id.button2Ex3 || buttonId == R.id.button3Ex3) {
            eText = findViewById(R.id.ex3Value);
            exerciseId = 2;
        } else if (buttonId == R.id.button1Ex4 || buttonId == R.id.button2Ex4 || buttonId == R.id.button3Ex4) {
            eText = findViewById(R.id.ex4Value);
            exerciseId = 3;
        } else {
            eText = findViewById(R.id.ex4Value);
            exerciseId = 3;
        }

        incrementValue = Double.valueOf(increment.substring(1));
        Log.i(TAG, "MyLog.MainActivity() — Increment will be: " + incrementValue);

        ex1rep = Double.valueOf(eText.getText().toString());
        ex1rep = ex1rep + incrementValue;
        eText.setText(ex1rep.toString());
        myWorkoutStats.addWorkout(exerciseId , ex1rep);
    }
}
