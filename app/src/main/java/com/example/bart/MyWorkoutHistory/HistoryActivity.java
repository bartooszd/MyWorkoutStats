package com.example.bart.MyWorkoutHistory;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.bart.firstapp2.R;

import static android.content.ContentValues.TAG;

public class HistoryActivity extends Activity {

    TextView eText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("History of workouts");

        WorkoutStats wStats = WorkoutStats.getInstance();
        String tmpText = wStats.getHistory();
        eText = findViewById(R.id.HistoryList);
        Log.i(TAG, "MyLog.OnCreateHistoryActivity() - history is " + tmpText);
        eText.setText(tmpText);
//        eText.setText("test test");
    }
}
