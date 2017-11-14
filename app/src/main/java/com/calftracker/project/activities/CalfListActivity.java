package com.calftracker.project.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.calftracker.project.adapters.CalfListRecyclerAdapter;
import com.calftracker.project.models.Calf;
import com.calftracker.project.calftracker.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class CalfListActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public static ArrayList<Calf> calfList;
    public static boolean arrayExists;

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
        } else { input.add("No calves exist. Click here to get started!"); }
        mAdapter = new CalfListRecyclerAdapter(this,input);
        recyclerView.setAdapter(mAdapter);
    }

    public void clickAdd(View view) {
        Intent intent = new Intent(this,AddCalfActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,DashboardActivity.class);
        startActivity(intent);
    }
}