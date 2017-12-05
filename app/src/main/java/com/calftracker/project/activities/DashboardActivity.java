package com.calftracker.project.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.calftracker.project.adapters.dashboard.DashboardGridAdapter;
import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Employee;
import com.calftracker.project.models.Farm;
import com.calftracker.project.models.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Map;

public class DashboardActivity extends BaseActivity {
    Farm farm;
    String temp;
    TextView mFarmName;
    Firebase fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_dashboard, frameLayout);

        mNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
        GridView grid;
        String[] dashboard_items = {
                getString(R.string.calf_list_title),
                getString(R.string.add_calf_title),
                getString(R.string.tasks_title),
                getString(R.string.protocols_title),
                getString(R.string.insights_title),
                getString(R.string.settings_title)
        } ;
        int[] imageId = {
                R.drawable.ic_view_list_black_24dp,
                R.drawable.ic_add_circle_black_24dp,
                R.drawable.ic_check_box_black_24dp,
                R.drawable.ic_local_hospital_black_24dp,
                R.drawable.ic_insert_chart_black_24dp,
                R.drawable.ic_settings_black_24dp
        };
        DashboardGridAdapter adapter = new DashboardGridAdapter(DashboardActivity.this, dashboard_items, imageId);
        grid=(GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent;
                switch(position) {
                    case 0: intent = new Intent(DashboardActivity.this,CalfListActivity.class);
                        startActivity(intent);
                        break;
                    case 1: intent = new Intent(DashboardActivity.this,AddCalfActivity.class);
                        startActivity(intent);
                        break;
                    case 2: intent = new Intent(DashboardActivity.this,TasksActivity.class);
                        startActivity(intent);
                        break;
                    case 3: intent = new Intent(DashboardActivity.this,VaccineActivity.class);
                        startActivity(intent);
                        break;
                    case 4: break;
                    case 5: intent = new Intent(DashboardActivity.this,SettingsActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });


        retrieveData();

        mFarmName = (TextView) findViewById(R.id.textViewFarmNameDashboard);

        mFarmName.setText(farm.getName());

    }


    public void retrieveData() {
        // set up shared preference variables
        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        Gson gson = new Gson();
        String json;

        // Load the Farm object from storage
        json = mPreferences.getString("Farm", "");
        farm = gson.fromJson(json, new TypeToken<Farm>() {}.getType());
    }


    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}