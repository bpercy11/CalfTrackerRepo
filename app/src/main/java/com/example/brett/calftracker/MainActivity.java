package com.example.brett.calftracker;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import java.util.Calendar;
import android.widget.Button;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.GregorianCalendar;


// TESTING JT'S COMMENT

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // on app start to main activity make a new farm object
        Calendar calfDOB = new GregorianCalendar(2017,10,30);
        Calf calf = new Calf(1001,"male",calfDOB);

        // turn that into json through gson and save to shared preferences
        SharedPreferences mPrefs = getSharedPreferences("test", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(calf);
        prefsEditor.putString("newCalf",json);
        prefsEditor.apply();
    }

    public void clickButton(View view) {
        Intent intent = new Intent(this,AddCalfActivity.class);
        startActivity(intent);
    }

    public void clickButton2(View view) {
        Intent intent = new Intent(this,CalfProfileActivity.class);
        startActivity(intent);
    }
}
