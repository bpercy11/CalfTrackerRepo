package com.calftracker.project.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.calftracker.project.adapters.protocols.IllnessAdapter;
import com.calftracker.project.models.Firebase;
import com.calftracker.project.models.Illness;
import com.calftracker.project.calftracker.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class IllnessActivity extends BaseActivity {
    private List<Illness> illnessList;
    private ListView lvIllness;
    private IllnessAdapter iAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_protocol_illness, frameLayout);
        mNavigationView.getMenu().findItem(R.id.nav_protocols).setChecked(true);

        // Custom title
        getSupportActionBar().setTitle(R.string.protocols_title);

        retrieveData();

        lvIllness = (ListView) findViewById(R.id.listview_illness);
        iAdapter = new IllnessAdapter(getApplicationContext(), illnessList);
        lvIllness.setAdapter(iAdapter);

        lvIllness.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                Gson gson = new Gson();
                String json = gson.toJson(illnessList.get(position));
                prefsEditor.putString("IllnessProfile",json);
                prefsEditor.apply();

                Firebase fb = (Firebase) getApplicationContext();
                fb.saveData("IllnessProfile", illnessList);

                Intent intent = new Intent(IllnessActivity.this, IllnessProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    // TODO
    public void saveData() {
        // EMPTY METHOD TO KEEP CONSISTENCY
        // NO DATA IS SAVED IN THIS ACTIVITY
    }

    public void retrieveData() {
        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        if (mPreferences.contains("IllnessList")) {
            SharedPreferences.Editor editor = mPreferences.edit();

            Gson gson = new Gson();
            String json = mPreferences.getString("IllnessList", "");
            illnessList = gson.fromJson(json, new TypeToken<ArrayList<Illness>>() {
            }.getType());
        } else { illnessList = new ArrayList<Illness>(); }
    }

    public void onIllness_MedicineButtonClick(View view) {
        Intent intent = new Intent(IllnessActivity.this, MedicineActivity.class);
        startActivity(intent);
    }
    public void onIllness_VaccineButtonClick(View view) {
        Intent intent = new Intent(IllnessActivity.this, VaccineActivity.class);
        startActivity(intent);
    }
    public void onIllness_IllnessButtonClick(View view){
        Intent intent = new Intent(IllnessActivity.this, IllnessActivity.class);
        startActivity(intent);
    }
    public void onIllness_AddIllnessButtonClick(View view) {
        Intent intent = new Intent(IllnessActivity.this, EditIllnessActivity.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(this, DashboardActivity.class);
            startActivity(intent);
        }
    }
}




