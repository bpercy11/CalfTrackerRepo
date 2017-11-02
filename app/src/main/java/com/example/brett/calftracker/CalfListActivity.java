package com.example.brett.calftracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalfListActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    static ArrayList<Calf> calfList;
    static boolean arrayExists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_calf_list, frameLayout);
        mNavigationView.getMenu().findItem(R.id.nav_list).setChecked(true);

        // try and get calf list
        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        if(mPreferences.contains("CalfList")) {
            arrayExists = true;
            SharedPreferences.Editor editor = mPreferences.edit();

            Gson gson = new Gson();
            String json = mPreferences.getString("CalfList", "");
            calfList = gson.fromJson(json, new TypeToken<ArrayList<Calf>>() {
            }.getType());
        } else {
            arrayExists = false;
            calfList = new ArrayList<Calf>();
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewCalfList);
        // use this setting to
        // improve performance if you know that changes
        // in content do not change the layout size
        // of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        List<String> input = new ArrayList<>();
        if(arrayExists) {
            for (int i = 0; i < calfList.size(); i++) {
                input.add(calfList.get(i).getFarmId());
            }
        } else { input.add("No calfs exist. Click here to get started!"); }
        mAdapter = new CalfListRecyclerAdapter(this,input);
        recyclerView.setAdapter(mAdapter);
    }
}
