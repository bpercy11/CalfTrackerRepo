package com.calftracker.project.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.*;
import android.util.Log;
import com.calftracker.project.models.Calf;
import java.util.Locale;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.widget.Toast;


import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Calf;
import com.calftracker.project.models.Farm;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;

public class SettingsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        Button editFarmButton = (Button) findViewById(R.id.settingsFarmButton);
        Button editEmployeesButton = (Button) findViewById(R.id.settingsEmployeeButton);
        Button englishButton = (Button) findViewById(R.id.settingsEnglishButton);
        Button spanishButton = (Button) findViewById(R.id.settingsSpanishButton);


        editFarmButton.setOnClickListener(new View.OnClickListener(){
            Intent intent;
            public void onClick(View v){
                intent = new Intent(SettingsActivity.this, SettingsEditFarmActivity.class);
                startActivity(intent);

            }
        });

        editEmployeesButton.setOnClickListener(new View.OnClickListener(){
            Intent intent;
            public void onClick(View v){
                intent = new Intent(SettingsActivity.this, SettingsEditEmployeesActivity.class);
                startActivity(intent);

            }
        });

        englishButton.setOnClickListener(new View.OnClickListener(){
            Intent intent;
            public void onClick(View v){

                setLocale("en");
            }
        });

        spanishButton.setOnClickListener(new View.OnClickListener(){
            Intent intent;
            public void onClick(View v){
                setLocale("es");
            }
        });




        //SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        //Map<String, ?> allEntries = mPrefs.getAll();
        //for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            //Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
         //   mDatabase.child("child").setValue(entry.getKey() + ": " + entry.getValue().toString());
        //}
        //ArrayList<Calf> calfList;
        //String json;
        //Gson gson = new Gson();
        //json = mPrefs.getString("CalfList", "");
        //calfList = gson.fromJson(json, new TypeToken<ArrayList<Calf>>() {}.getType());
        //mDatabase.child("Root").child("key").setValue("test");


        //mDatabaseRead = FirebaseDatabase.getInstance().getReference().child("Farm Name");


        //mInfoView = (TextView) findViewById(R.id.info_view);

        //mFirebaseButton = (Button) findViewById(R.id.firebase_btn);
        //mFirebaseReadButton = (Button) findViewById(R.id.firebase_read_btn);


        //mFarmName = (EditText) findViewById(R.id.farmNameText);
        //mFarmOwner = (EditText) findViewById(R.id.farmOwnerText);
        //mFarmLocation = (EditText) findViewById(R.id.farmLocationText);

    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, SettingsActivity.class);
        startActivity(refresh);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,DashboardActivity.class);
        startActivity(intent);
    }
}
