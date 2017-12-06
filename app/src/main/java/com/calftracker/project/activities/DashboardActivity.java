package com.calftracker.project.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.calftracker.project.adapters.dashboard.DashboardGridAdapter;
import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Farm;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class DashboardActivity extends BaseActivity {
    Farm farm;

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

        retrieveData();

        TextView mFarmName = (TextView) findViewById(R.id.textViewFarmNameDashboard);
        mFarmName.setText(farm.getName());

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
                    case 4: intent = new Intent(DashboardActivity.this,InsightsActivity.class);
                            startActivity(intent);
                            break;
                    case 5: intent = new Intent(DashboardActivity.this,SettingsActivity.class);
                            startActivity(intent);
                            break;
                }
            }
        });
    }

    // TODO
    public void saveData() {
        // EMPTY METHOD TO KEEP CONSISTENCY
        // NO DATA IS SAVED IN THIS ACTIVITY
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            moveTaskToBack(true);
        }
    }
}