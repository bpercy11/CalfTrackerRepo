package com.calftracker.project.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.calftracker.project.adapters.protocols.VaccineAdapter;
import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Vaccine;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class VaccineActivity extends BaseActivity {

    private ListView lvVaccine;
    private VaccineAdapter vAdapter;
    private List<Vaccine> vaccineList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_protocol_vaccine, frameLayout);
        mNavigationView.getMenu().findItem(R.id.nav_protocols).setChecked(true);

        // Custom title
        getSupportActionBar().setTitle(R.string.protocols_title);

        retrieveData();

        lvVaccine = (ListView)findViewById(R.id.listview_vaccine);
        vAdapter = new VaccineAdapter(getApplicationContext(), vaccineList);
        lvVaccine.setAdapter(vAdapter);

        lvVaccine.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                Gson gson = new Gson();
                String json = gson.toJson(vaccineList.get(position));
                prefsEditor.putString("VaccineProfile",json);
                prefsEditor.apply();

                Intent intent = new Intent(VaccineActivity.this, VaccineProfileActivity.class);
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
        if(mPreferences.contains("VaccineList")) {
            SharedPreferences.Editor editor = mPreferences.edit();

            Gson gson = new Gson();
            String json = mPreferences.getString("VaccineList", "");
            vaccineList = gson.fromJson(json, new TypeToken<ArrayList<Vaccine>>() {
            }.getType());
        } else { vaccineList = new ArrayList<Vaccine>(); }
    }

    public void onVaccine_MedicineButtonClick(View view) {
        Intent intent = new Intent(VaccineActivity.this, MedicineActivity.class);
        startActivity(intent);
    }
    public void onVaccine_VaccineButtonClick(View view){
        Intent intent = new Intent(VaccineActivity.this, VaccineActivity.class);
        startActivity(intent);
    }
    public void onVaccine_IllnessButtonClick(View view){
        Intent intent = new Intent(VaccineActivity.this, IllnessActivity.class);
        startActivity(intent);
    }
    public void onVaccine_AddVaccineButtonClick(View view){
        Intent intent = new Intent(VaccineActivity.this, EditVaccineActivity.class);
        startActivity(intent);
    }
    public void onBackPressed() {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }
}

