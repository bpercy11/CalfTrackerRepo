package com.calftracker.project.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Vaccine;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class VaccineProfileActivity extends AppCompatActivity {

    private Vaccine vaccine;
    private List<Vaccine> vaccineList;
    private int vaccinePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine_profile);

        // Stylize action bar to use back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);

        //Load clicked Vaccine
        if(mPreferences.contains("VaccineProfile")) {
            SharedPreferences.Editor editor = mPreferences.edit();

            Gson gson = new Gson();
            String json = mPreferences.getString("VaccineProfile", "");
            vaccine = gson.fromJson(json, new TypeToken<Vaccine>() {
            }.getType());
        }

        // Custom title
        getSupportActionBar().setTitle(vaccine.getName());

        //Load VaccineList
        if(mPreferences.contains("VaccineList")) {
            SharedPreferences.Editor editor = mPreferences.edit();

            Gson gson = new Gson();
            String json = mPreferences.getString("VaccineList", "");
            vaccineList = gson.fromJson(json, new TypeToken<ArrayList<Vaccine>>() {
            }.getType());
        }

        //Find Vaccine position in VaccineList
        for (int i = 0; i < vaccineList.size(); i++){
            if(vaccineList.get(i).getName().equals(vaccine.getName())){
                vaccinePosition = i;
            }
        }

        //Finding where Data needs to be displayed
        TextView vaccineName = (TextView) findViewById(R.id.vaccine_profile_vaccine_nameData);
        TextView vaccineAdminStart = (TextView) findViewById(R.id.vaccine_profile_adminStartData);
        TextView vaccineAdminEnd = (TextView) findViewById(R.id.vaccine_profile_adminEndData);
        TextView vaccineDosage = (TextView) findViewById(R.id.vaccine_profile_dosageData);
        TextView vaccineDosageUnits = (TextView) findViewById(R.id.vaccine_profile_dosageUnitsData);
        TextView vaccineAdminMethod = (TextView) findViewById(R.id.vaccine_profile_adminMethodData);

        int range[] = new int[2];
        range =  vaccine.getToBeAdministered().get(0).getSpan();

        vaccineName.setText(vaccine.getName());

        //Setting the Vacc_Range Data
        if (range[0] % 7 == 0 && range[1] % 7 == 0) {
            range[0] = range[0]/7;
            range[1] = range[1]/7;
            vaccineAdminStart.setText(Integer.toString(range[0]) + " weeks");
            vaccineAdminEnd.setText(Integer.toString(range[1]) + " weeks");
        }
        else if(range[0] % 30 == 0 && range[1] % 30 == 0){
            range[0] = range[0]/30;
            range[1] = range[1]/30;
            vaccineAdminStart.setText(Integer.toString(range[0]) + " months");
            vaccineAdminEnd.setText(Integer.toString(range[1]) + " months");
        }
        else{
            vaccineAdminStart.setText(Integer.toString(range[0]) + " days");
            vaccineAdminEnd.setText(Integer.toString(range[1]) + " days");
        }

        //Setting Data with correct vaccine information
        vaccineDosage.setText(Double.toString(vaccine.getDosage()));
        vaccineDosageUnits.setText(vaccine.getDosageUnits());
        vaccineAdminMethod.setText(vaccine.getMethodOfAdministration());

    }
    public void onVProfile_editButton(View view){
        Intent intent = new Intent(VaccineProfileActivity.this,EditVaccineProfileActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, VaccineActivity.class);
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(getApplicationContext(), VaccineActivity.class);
        startActivity(intent);
        return true;
    }
}
