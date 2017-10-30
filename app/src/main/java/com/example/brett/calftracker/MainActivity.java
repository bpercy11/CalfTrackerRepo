package com.example.brett.calftracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;


// TESTING JT'S COMMENT

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // on app start to main activity make a new farm object

        // turn that into json through gson and save to shared preferences
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
