package com.example.bart.MyWorkoutHistory;

import android.app.Activity;
import android.os.SystemClock;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Chronometer;
import android.widget.TextView;

import com.example.bart.firstapp2.R;

import static android.content.ContentValues.TAG;

public class DailyWorkoutScreen extends Activity {

    WorkoutStats myWorkoutStats;
    Chronometer myChronometer;
    long startOfWorkoutCounter= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_workout_screen);
        myChronometer = (Chronometer)findViewById(R.id.chronometer);

        EditText eText;

        myWorkoutStats = WorkoutStats.getInstance();

        //start timer adding time captured earlier
        Log.i(TAG, "MyLog.MainActivity() — Starting timer with" + SystemClock.elapsedRealtime() +" + " + myWorkoutStats.getTodayWorkoutTime());
        myChronometer.setBase(SystemClock.elapsedRealtime()- myWorkoutStats.getTodayWorkoutTime());
        //myChronometer.setBase(SystemClock.elapsedRealtime() - 2000);
        myChronometer.start();
        startOfWorkoutCounter = SystemClock.elapsedRealtime();

        // Set the names of exercises
        TextView vText;
        vText = findViewById(R.id.textViewEx1);
        vText.setText(myWorkoutStats.exercisesName[0]);
        vText = findViewById(R.id.textViewEx2);
        vText.setText(myWorkoutStats.exercisesName[1]);
        vText = findViewById(R.id.textViewEx3);
        vText.setText(myWorkoutStats.exercisesName[2]);
        vText = findViewById(R.id.textViewEx4);
        vText.setText(myWorkoutStats.exercisesName[3]);
        vText = findViewById(R.id.textViewEx5);
        vText.setText(myWorkoutStats.exercisesName[4]);

        // Set the values of already captured reps for today
        eText = (EditText) findViewById(R.id.ex1Value);
        eText.setText(myWorkoutStats.getTodaysWorkout(0).toString());
        eText = (EditText) findViewById(R.id.ex2Value);
        eText.setText(myWorkoutStats.getTodaysWorkout(1).toString());
        eText = (EditText) findViewById(R.id.ex3Value);
        eText.setText(myWorkoutStats.getTodaysWorkout(2).toString());
        eText = (EditText) findViewById(R.id.ex4Value);
        eText.setText(myWorkoutStats.getTodaysWorkout(3).toString());
        eText = (EditText) findViewById(R.id.ex5Value);
        eText.setText(myWorkoutStats.getTodaysWorkout(4).toString());

    }

    // save the reps if app is switched to some other appr
    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first

        // stop counting time
        myChronometer.stop();

        double sumOfWorkout = Double.valueOf(((EditText)findViewById(R.id.ex1Value)).getText().toString())
                + Double.valueOf(((EditText)findViewById(R.id.ex2Value)).getText().toString())
                + Double.valueOf(((EditText)findViewById(R.id.ex3Value)).getText().toString())
                + Double.valueOf(((EditText)findViewById(R.id.ex4Value)).getText().toString())
                + Double.valueOf(((EditText)findViewById(R.id.ex5Value)).getText().toString());

        if (sumOfWorkout >0 ) {
            myWorkoutStats.addWorkout(0, Double.valueOf(((EditText) findViewById(R.id.ex1Value)).getText().toString()));
            myWorkoutStats.addWorkout(1, Double.valueOf(((EditText) findViewById(R.id.ex2Value)).getText().toString()));
            myWorkoutStats.addWorkout(2, Double.valueOf(((EditText) findViewById(R.id.ex3Value)).getText().toString()));
            myWorkoutStats.addWorkout(3, Double.valueOf(((EditText) findViewById(R.id.ex4Value)).getText().toString()));
            myWorkoutStats.addWorkout(4, Double.valueOf(((EditText) findViewById(R.id.ex5Value)).getText().toString()));

            Log.i(TAG, "MyLog.DailyWorkoutScreen() — saving time: " + SystemClock.elapsedRealtime() + " - " + startOfWorkoutCounter);
            myWorkoutStats.setTodayWorkoutTime(SystemClock.elapsedRealtime() - startOfWorkoutCounter);
        }

        Log.i(TAG, "MyLog.DailyWorkoutScreen() — serializing object to file");
        myWorkoutStats.saveObject(); // serialize the object to file
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
        } else if (buttonId == R.id.button1Ex5 || buttonId == R.id.button2Ex5 || buttonId == R.id.button3Ex5) {
            eText = findViewById(R.id.ex5Value);
            exerciseId = 4;
        } else {
            eText = findViewById(R.id.ex5Value);
            exerciseId = 4;
        }

        incrementValue = Double.valueOf(increment.substring(1));
        Log.i(TAG, "MyLog.MainActivity() — Increment will be: " + incrementValue);

        ex1rep = Double.valueOf(eText.getText().toString());
        ex1rep = ex1rep + incrementValue;
        eText.setText(ex1rep.toString());
        myWorkoutStats.addWorkout(exerciseId , ex1rep);
    }
}
