package com.example.bart.MyWorkoutHistory;

import android.app.Activity;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FilenameFilter;
import com.example.bart.firstapp2.R;

import org.joda.time.LocalDate;

import java.io.File;

import static android.content.ContentValues.TAG;

public class SaveLoadScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_load);

        // find the list of files in directory
        File directory = new File(Environment.getExternalStorageDirectory(), "MyWorkoutStats");
        if(!directory.exists()) {
            directory.mkdirs();
        }
        Log.i(TAG, "MyLog.onCreate() — Files location: " + directory.toString());
        File[] files = directory.listFiles(new FilenameFilter() {
            public boolean accept(File directory, String name) {
                return name.toLowerCase().endsWith(".csv");
            }});
        int numberOfFies;

        // check if not null in case it is run on emulator
        if (files != null ) {
            numberOfFies = files.length;
        } else {
            numberOfFies = 0;
        }

        String[] fileNames = new String[numberOfFies];

        for (int i = 0; i <numberOfFies; i++)
        {
                fileNames[i] = files[i].getName().toString();
                Log.i(TAG, "MyLog.onClickLoad() — Found file :" + fileNames[i]);

        }

        //get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.Load_spinner);


//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, fileNames);
//set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
    }

    public void onClickSave(View arg0) {
        WorkoutStats wStats = WorkoutStats.getInstance();
        EditText eText = findViewById(R.id.editSaveText);
        LocalDate currentDate = new LocalDate();
        String fileName = "SavedFile" + currentDate.toString();


        if(eText.getText().toString().length() != 0) {
            Log.i(TAG, "MyLog.onClickSave() — File name to save is not empty. Its size is" + eText.getText().toString().length());
            fileName = eText.getText().toString();
        }

        wStats.saveToCsv(fileName);
    }

    public void onClickLoad(View arg0) {
        WorkoutStats wStats = WorkoutStats.getInstance();
        Spinner fileSpinner = (Spinner) findViewById(R.id.Load_spinner);
        String fileNameToLoad = String.valueOf(fileSpinner.getSelectedItem());
        Log.i(TAG, "MyLog.onClickLoad() — File selected is " + fileNameToLoad);

        File directory = new File(Environment.getExternalStorageDirectory(), "MyWorkoutStats");
        File fileReference = new File(directory.toString() + "/" + fileNameToLoad);

        wStats.loadFromCsv(fileReference);

        Toast toast = Toast.makeText(getApplicationContext(),
                "History loaded from file: " + fileNameToLoad,
                Toast.LENGTH_SHORT);

        toast.show();
    }
}
