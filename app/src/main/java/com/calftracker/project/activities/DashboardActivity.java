package com.calftracker.project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.calftracker.project.adapters.DashboardGridAdapter;
import com.calftracker.project.calftracker.R;

public class DashboardActivity extends BaseActivity {
    GridView grid;
    String[] dashboard_items = {
            "Calf List",
            "Add Calf",
            "Tasks",
            "Protocols",
            "Insights",
            "Settings"
    } ;
    int[] imageId = {
            R.drawable.ic_view_list_black_24dp,
            R.drawable.ic_add_circle_black_24dp,
            R.drawable.ic_check_box_black_24dp,
            R.drawable.ic_local_hospital_black_24dp,
            R.drawable.ic_insert_chart_black_24dp,
            R.drawable.ic_settings_black_24dp
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_dashboard, frameLayout);
        mNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);

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
                    case 2: break;
                    case 3: intent = new Intent(DashboardActivity.this,ProtocolsVaccineActivity.class);
                            startActivity(intent);
                            break;
                    case 4: break;
                    case 5: break;
                }
            }
        });
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