package com.calftracker.project.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.calftracker.project.adapters.calfprofile.GrowthHistoryHeightAdapter;
import com.calftracker.project.adapters.calfprofile.GrowthHistoryWeightAdapter;
import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Calf;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class CalfProfileGrowthHistoryActivity extends AppCompatActivity {

    String calfID;
    ArrayList<Calf> calfList;
    private Calf calf;

    GrowthHistoryWeightAdapter weightAdapter;
    GrowthHistoryHeightAdapter heightAdapter;
    private boolean noWeights = true;
    private boolean noHeights = true;

    ListView listview;

    Button heightButton;
    Button weightButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calf_profile_growth_history);

        // Stylize action bar to use back button and custom title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Growth History");

        retrieveData();

        // try and get calf object made by main activity
        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        Gson gson = new Gson();
        String json = mPreferences.getString("CalfList","");
        json = mPreferences.getString("calfToViewInProfile","");
        calfID = gson.fromJson(json, String.class);

        // Search through the calfList to find the correct calf by ID
        for (int i = 0; i < calfList.size(); i++) {
            if (calfList.get(i).getFarmId().equals(calfID)) {
                calf = calfList.get(i);
                break;
            }
        }
        noRecordingsCheck();

        listview = (ListView) findViewById(R.id.listViewGrowthHistory);
        if (calf.getPhysicalHistory().isEmpty() || noWeights) {
            findViewById(R.id.textViewNoWeightRecorded).setVisibility(View.VISIBLE);
            findViewById(R.id.textViewNoHeightRecorded).setVisibility(View.GONE);
        } else {
            findViewById(R.id.textViewNoWeightRecorded).setVisibility(View.GONE);
            findViewById(R.id.textViewNoHeightRecorded).setVisibility(View.GONE);
        }

        weightAdapter = new GrowthHistoryWeightAdapter(getApplicationContext(),calf.getPhysicalHistory());
        listview.setAdapter(weightAdapter);

        heightButton = (Button) findViewById(R.id.buttonHeight);
        weightButton = (Button) findViewById(R.id.buttonWeight);

        // Background tint only works on 15 & up.
        // This should probably be changed to Background Color but that overrides styles and I'm lazy
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            weightButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorMedGrey));
            weightButton.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        }
    }

    public void retrieveData() {
        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPreferences.getString("CalfList","");
        calfList = gson.fromJson(json, new TypeToken<ArrayList<Calf>>(){}.getType());
    }

    public void onClickWeightButton(View view) {
        noRecordingsCheck();
        if (calf.getPhysicalHistory().isEmpty() || noWeights) {
            findViewById(R.id.textViewNoWeightRecorded).setVisibility(View.VISIBLE);
            findViewById(R.id.textViewNoHeightRecorded).setVisibility(View.GONE);
        } else {
            findViewById(R.id.textViewNoWeightRecorded).setVisibility(View.GONE);
            findViewById(R.id.textViewNoHeightRecorded).setVisibility(View.GONE);
        }
        weightAdapter = new GrowthHistoryWeightAdapter(getApplicationContext(),calf.getPhysicalHistory());
        listview.setAdapter(weightAdapter);

        // Background tint only works on 15 & up.
        // This should probably be changed to Background Color but that overrides styles and I'm lazy
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            weightButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorMedGrey));
            weightButton.setTextColor(ContextCompat.getColor(this, android.R.color.white));
            heightButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorLightGrey));
            heightButton.setTextColor(ContextCompat.getColor(this, android.R.color.black));
        }
    }

    public void onClickHeightButton(View view) {
        noRecordingsCheck();
        if (calf.getPhysicalHistory().isEmpty() || noHeights) {
            findViewById(R.id.textViewNoHeightRecorded).setVisibility(View.VISIBLE);
            findViewById(R.id.textViewNoWeightRecorded).setVisibility(View.GONE);
        } else {
            findViewById(R.id.textViewNoWeightRecorded).setVisibility(View.GONE);
            findViewById(R.id.textViewNoHeightRecorded).setVisibility(View.GONE);
        }
        heightAdapter = new GrowthHistoryHeightAdapter(getApplicationContext(),calf.getPhysicalHistory());
        listview.setAdapter(heightAdapter);

        // Background tint only works on 15 & up.
        // This should probably be changed to Background Color but that overrides styles and I'm lazy
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heightButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorMedGrey));
            heightButton.setTextColor(ContextCompat.getColor(this, android.R.color.white));
            weightButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorLightGrey));
            weightButton.setTextColor(ContextCompat.getColor(this, android.R.color.black));
        }
    }

    public void noRecordingsCheck() {
        for (int i = 0; i < calf.getPhysicalHistory().size(); i++) {
            if (calf.getPhysicalHistory().get(i).getWeight() != -1) {
                noWeights = false;
            }
            if (calf.getPhysicalHistory().get(i).getHeight() != -1) {
                noHeights = false;
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, CalfProfileActivity.class);
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(getApplicationContext(), CalfProfileActivity.class);
        startActivity(intent);
        return true;
    }
}
