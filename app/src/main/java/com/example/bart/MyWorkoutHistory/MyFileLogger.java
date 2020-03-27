package com.example.bart.MyWorkoutHistory;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import static android.content.ContentValues.TAG;

public class MyFileLogger {
    static File logFile;

    public MyFileLogger()  {


        File directory = new File(Environment.getExternalStorageDirectory(), "MyWorkoutStats");
        if (!directory.exists()) {
            directory.mkdirs();
            Log.i(TAG, "MyLog.MyFileLogger() - Created directory: " + directory.toString());
        }
        logFile = new File(directory.toString() + "/my_workouts.log");
        Log.i(TAG, "MyLog.MyFileLogger() — creation or initiation of logger file: " + logFile.toString());
    }

    public static void AddLog(String stringToLog) {
        if(logFile != null) {
            try {
                OutputStream os = new FileOutputStream(logFile, true);
                os.write(stringToLog.getBytes(), 0, stringToLog.length());
                os.close();
            } catch (Exception ex) {
                Log.v("Ser Save Error : ", ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
}
