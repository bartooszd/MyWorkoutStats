package com.example.bart.MyWorkoutHistory;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.example.bart.firstapp2.R;

import static android.content.ContentValues.TAG;

public class CreditSettingsActivity extends AppCompatActivity {

    WorkoutStats myWorkoutStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_settings);
        EditText eText;
        myWorkoutStats = WorkoutStats.getInstance();

        // Set the values of stored coins ratio for each of exercises
        eText = findViewById(R.id.exercise1CreditsEditText);
        eText.setText(Double.toString(myWorkoutStats.walletInstance.getCredit(0)));
        eText = findViewById(R.id.exercise2CreditsEditText);
        eText.setText(Double.toString(myWorkoutStats.walletInstance.getCredit(1)));
        eText = findViewById(R.id.exercise3CreditsEditText);
        eText.setText(Double.toString(myWorkoutStats.walletInstance.getCredit(2)));
        eText = findViewById(R.id.exercise4CreditsEditText);
        eText.setText(Double.toString(myWorkoutStats.walletInstance.getCredit(3)));
        eText = findViewById(R.id.exercise5CreditsEditText);
        eText.setText(Double.toString(myWorkoutStats.walletInstance.getCredit(4)));
    }


    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first

        myWorkoutStats.walletInstance.setExerciseRatio(0, Double.valueOf(((EditText)findViewById(R.id.exercise1CreditsEditText)).getText().toString()));
        myWorkoutStats.walletInstance.setExerciseRatio(1, Double.valueOf(((EditText)findViewById(R.id.exercise2CreditsEditText)).getText().toString()));
        myWorkoutStats.walletInstance.setExerciseRatio(2, Double.valueOf(((EditText)findViewById(R.id.exercise3CreditsEditText)).getText().toString()));
        myWorkoutStats.walletInstance.setExerciseRatio(3, Double.valueOf(((EditText)findViewById(R.id.exercise4CreditsEditText)).getText().toString()));
        myWorkoutStats.walletInstance.setExerciseRatio(4, Double.valueOf(((EditText)findViewById(R.id.exercise5CreditsEditText)).getText().toString()));
    }

}