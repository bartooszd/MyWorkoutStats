package com.example.bart.firstapp2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

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

