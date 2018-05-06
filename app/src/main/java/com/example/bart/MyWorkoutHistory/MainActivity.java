package com.example.bart.MyWorkoutHistory;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.bart.firstapp2.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickHistory(View arg0) {
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }

    public void onClickStartDailyWorkout(View arg0) {
        Intent intent = new Intent(this, DailyWorkoutScreen.class);
        startActivity(intent);
    }

    public void onClickSaveLoad(View arg0) {
        Intent intent = new Intent(this, SaveLoad.class);
        startActivity(intent);
    }

}

