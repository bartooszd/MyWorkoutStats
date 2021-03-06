package com.example.bart.MyWorkoutHistory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.bart.firstapp2.R;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity {

    WorkoutStats myWorkoutStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myWorkoutStats = WorkoutStats.getInstance();
        Log.i(TAG, "MyLog.MainActivity() — Loading serializing object from file");
       myWorkoutStats.loadSerializedObject(); // serialize the object from file

        //print history
        Log.i(TAG, "MyLog.MainActivity() — Loaded history: " + myWorkoutStats.getHistory());

        // calculate and display coins account balance
        myWorkoutStats.walletInstance.calculateWalletBalance(myWorkoutStats.myList);
        TextView vText;
        vText = findViewById(R.id.creditsValueTextView);
        vText.setText( String.valueOf( myWorkoutStats.walletInstance.getAccountBalanceInteger()));

        // calculate and display coins from current day
        TextView dailyCoinsText;
        dailyCoinsText = findViewById(R.id.creditsFromTodayTextView);
        double coinsFromToday = myWorkoutStats.walletInstance.calculateCoinsFromToday(myWorkoutStats.myList);
        double dailyBonusFromToday = myWorkoutStats.walletInstance.calculateDailyBonusForToday();
        Log.i(TAG, "MyLog.MainActivity() — Daily bonus for today is: " + dailyBonusFromToday);
        if (dailyBonusFromToday > 0)
            dailyCoinsText.setText( "Today: " + String.valueOf( coinsFromToday) + " + " + String.valueOf(dailyBonusFromToday));
        else if (dailyBonusFromToday < 0)
            dailyCoinsText.setText( "Today: " + String.valueOf( coinsFromToday) + " - " + String.valueOf(dailyBonusFromToday *(-1)));
        else
            dailyCoinsText.setText( "Today: " + String.valueOf( coinsFromToday));

        Log.i(TAG, "MyLog.MainActivity() — Will get Workout Stats");
    }

    @Override
    public void onResume(){
        super.onResume();
        myWorkoutStats = WorkoutStats.getInstance();

        // calculate and display coins account balance
        myWorkoutStats.walletInstance.calculateWalletBalance(myWorkoutStats.myList);
        TextView vText;
        vText = findViewById(R.id.creditsValueTextView);
        vText.setText( String.valueOf( myWorkoutStats.walletInstance.getAccountBalanceInteger()));

        // calculate and display coins from current day
        TextView dailyCoinsText;
        dailyCoinsText = findViewById(R.id.creditsFromTodayTextView);
        double coinsFromToday = myWorkoutStats.walletInstance.calculateCoinsFromToday(myWorkoutStats.myList);
        double dailyBonusFromToday = myWorkoutStats.walletInstance.calculateDailyBonusForToday();
        Log.i(TAG, "MyLog.MainActivity() — Daily bonus for today is: " + dailyBonusFromToday);
        if (dailyBonusFromToday > 0)
            dailyCoinsText.setText( "Today: " + String.valueOf( coinsFromToday) + " + " + String.valueOf(dailyBonusFromToday));
        else if (dailyBonusFromToday < 0)
            dailyCoinsText.setText( "Today: " + String.valueOf( coinsFromToday) + " - " + String.valueOf(dailyBonusFromToday *(-1)));
        else
            dailyCoinsText.setText( "Today: " + String.valueOf( coinsFromToday));

            if (coinsFromToday > 0)
            dailyCoinsText.setVisibility(View.VISIBLE);
        else
            dailyCoinsText.setVisibility(View.INVISIBLE);

        Log.i(TAG, "MyLog.MainActivity() — Will get Workout Stats");
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
        Intent intent = new Intent(this, SaveLoadScreen.class);
        startActivity(intent);
    }

    public void onClickSettings(View arg0) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void onClickWallet(View arg0) {
        Intent intent = new Intent(this, CheckoutScreen.class);
        startActivity(intent);
    }


    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first
        Log.i(TAG, "MyLog.MainActivity() — serializing object to file");
        myWorkoutStats.saveObject(); // serialize the object to file
    }

}

