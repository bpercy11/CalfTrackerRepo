package com.calftracker.project.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.calftracker.project.adapters.GrowthHistoryHeightAdapter;
import com.calftracker.project.adapters.GrowthHistoryWeightAdapter;
import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Calf;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class CalfProfileGrowthHistoryActivity extends AppCompatActivity {

    String calfID;
    ArrayList<Calf> calfList;
    Calf calf;

    GrowthHistoryWeightAdapter weightAdapter;
    GrowthHistoryHeightAdapter heightAdapter;

    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calf_profile_growth_history);

        // try and get calf object made by main activity
        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();

        Gson gson = new Gson();
        String json = mPreferences.getString("CalfList","");
        calfList = gson.fromJson(json, new TypeToken<ArrayList<Calf>>(){}.getType());

        json = mPreferences.getString("calfToViewInProfile","");
        calfID = gson.fromJson(json, String.class);

        // Search through the calfList to find the correct calf by ID
        for (int i = 0; i < calfList.size(); i++) {
            if (calfList.get(i).getFarmId().equals(calfID)) {
                calf = calfList.get(i);
                break;
            }
        }

        listview = (ListView) findViewById(R.id.listViewGrowthHistory);

        weightAdapter = new GrowthHistoryWeightAdapter(getApplicationContext(),calf.getPhysicalHistory());
        listview.setAdapter(weightAdapter);

    }

    public void onClickWeightButton(View view) {
        weightAdapter = new GrowthHistoryWeightAdapter(getApplicationContext(),calf.getPhysicalHistory());
        listview.setAdapter(weightAdapter);
    }

    public void onClickHeightButton(View view) {
        heightAdapter = new GrowthHistoryHeightAdapter(getApplicationContext(),calf.getPhysicalHistory());
        listview.setAdapter(heightAdapter);
    }
}
