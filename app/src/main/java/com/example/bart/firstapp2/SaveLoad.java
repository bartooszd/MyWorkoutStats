package com.example.bart.firstapp2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.joda.time.LocalDate;

import java.io.File;

public class SaveLoad extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_load);
    }

    public void onClickSave(View arg0) {
        WorkoutStats wStats = WorkoutStats.getInstance();
        EditText eText = findViewById(R.id.editSaveText);
        LocalDate currentDate = new LocalDate();
        String fileName = "SavedFile" + currentDate.toString();


        if(eText.toString() != "") {
            fileName = eText.getText().toString();
        }

        wStats.saveToCsv(fileName);
    }

    public void onClicLoad(View arg0) {
    }
}
