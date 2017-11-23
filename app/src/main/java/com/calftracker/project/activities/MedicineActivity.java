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

import com.calftracker.project.adapters.MedicineAdapter;
import com.calftracker.project.models.Medicine;
import com.calftracker.project.calftracker.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MedicineActivity extends BaseActivity {

    private List<Medicine> medicineList;
    private ListView lvMedicine;
    private MedicineAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_protocol_medicine, frameLayout);
        mNavigationView.getMenu().findItem(R.id.nav_protocols).setChecked(true);

        // Custom title
        getSupportActionBar().setTitle(R.string.protocols_title);

        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        if(mPreferences.contains("MedicineList")) {
            SharedPreferences.Editor editor = mPreferences.edit();

            Gson gson = new Gson();
            String json = mPreferences.getString("MedicineList", "");
            medicineList = gson.fromJson(json, new TypeToken<ArrayList<Medicine>>() {
            }.getType());
        } else { medicineList = new ArrayList<Medicine>(); }

        lvMedicine = (ListView)findViewById(R.id.listview_medicine);
        mAdapter = new MedicineAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,medicineList);
        lvMedicine.setAdapter(mAdapter);

        lvMedicine.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                Gson gson = new Gson();
                String json = gson.toJson(medicineList.get(position));
                prefsEditor.putString("MedicineProfile",json);
                prefsEditor.apply();

                Intent intent = new Intent(MedicineActivity.this, MedicineProfileActivity.class);
                startActivity(intent);
            }
        });
    }
    public void onMedicine_VaccineButtonClick(View view) {
        Intent intent = new Intent(MedicineActivity.this,VaccineActivity.class);
        startActivity(intent);

    }
    public void onMedicine_MedicineButtonClick(View view){
        Intent intent = new Intent(MedicineActivity.this, MedicineActivity.class);
        startActivity(intent);
    }
    public void onMedicine_IllnessButtonClick(View view){
        Intent intent = new Intent(MedicineActivity.this, IllnessActivity.class);
        startActivity(intent);
    }
    public void onMedicine_AddMedicineButtonClick(View view){
        Intent intent = new Intent(MedicineActivity.this, EditMedicineActivity.class);
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
