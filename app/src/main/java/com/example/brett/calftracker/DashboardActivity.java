package com.example.brett.calftracker;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class DashboardActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_dashboard);

        CustomGrid adapter = new CustomGrid(DashboardActivity.this, dashboard_items, imageId);
        grid=(GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(DashboardActivity.this, "You Clicked at " +dashboard_items[+ position], Toast.LENGTH_SHORT).show();
            }
        });
    }
}