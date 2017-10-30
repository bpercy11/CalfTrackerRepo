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
