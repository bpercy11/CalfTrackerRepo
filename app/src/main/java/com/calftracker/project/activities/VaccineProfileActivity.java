package com.calftracker.project.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.calftracker.project.adapters.VaccineAdapter;
import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Vaccine;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VaccineProfileActivity extends BaseActivity {

    private Vaccine vaccine;
    private List<Vaccine> vaccineList;
    private int vaccinePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_vaccine_profile, frameLayout);
        mNavigationView.getMenu().findItem(R.id.nav_protocols).setChecked(true);

        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        if(mPreferences.contains("VaccineProfile")) {
            SharedPreferences.Editor editor = mPreferences.edit();

            Gson gson = new Gson();
            String json = mPreferences.getString("VaccineProfile", "");
            vaccine = gson.fromJson(json, new TypeToken<Vaccine>() {
            }.getType());
        } else { }

        if(mPreferences.contains("VaccineList")) {
            SharedPreferences.Editor editor = mPreferences.edit();

            Gson gson = new Gson();
            String json = mPreferences.getString("VaccineList", "");
            vaccineList = gson.fromJson(json, new TypeToken<ArrayList<Vaccine>>() {
            }.getType());
        } else { }

        for (int i = 0; i < vaccineList.size(); i++){
            if(vaccineList.get(i).getName().equals(vaccine.getName())){
                vaccinePosition = i;
            }
        }

        TextView vaccineName = (TextView) findViewById(R.id.vaccine_profile_vaccine_nameData);
        TextView vaccineAdminStart = (TextView) findViewById(R.id.vaccine_profile_adminStartData);
        TextView vaccineAdminEnd = (TextView) findViewById(R.id.vaccine_profile_adminEndData);
        TextView vaccineDosage = (TextView) findViewById(R.id.vaccine_profile_dosageData);
        TextView vaccineDosageUnits = (TextView) findViewById(R.id.vaccine_profile_dosageUnitsData);
        TextView vaccineAdminMethod = (TextView) findViewById(R.id.vaccine_profile_adminMethodData);

        int range[] = new int[2];
        range =  vaccine.getToBeAdministered().get(0).getSpan();

        vaccineName.setText(vaccine.getName());

        if (range[0] % 7 == 0 && range[1] % 7 == 0) {
            range[0] = range[0]/7;
            range[1] = range[1]/7;
            vaccineAdminStart.setText(Integer.toString(range[0]) + " Weeks");
            vaccineAdminEnd.setText(Integer.toString(range[1]) + " Weeks");
        }
        else if(range[0] % 30 == 0 && range[1] % 30 == 0){
            range[0] = range[0]/30;
            range[1] = range[1]/30;
            vaccineAdminStart.setText(Integer.toString(range[0]) + " Months");
            vaccineAdminEnd.setText(Integer.toString(range[1]) + " Months");
        }
        else{
            vaccineAdminStart.setText(Integer.toString(range[0]) + " Days");
            vaccineAdminEnd.setText(Integer.toString(range[1]) + " Days");
        }

        vaccineDosage.setText(Double.toString(vaccine.getDosage()));
        vaccineDosageUnits.setText(vaccine.getDosageUnits());
        vaccineAdminMethod.setText(vaccine.getMethodOfAdministration());

    }
    public void onVProfile_editButton(View view){
        Intent intent = new Intent(VaccineProfileActivity.this,EditVaccineProfileActivity.class);
        startActivity(intent);
    }

    public void onVProfile_removeButton(View view){

        vaccineList.remove(vaccinePosition);

        SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(vaccineList);
        prefsEditor.putString("VaccineList",json);
        prefsEditor.apply();

        Intent intent = new Intent(VaccineProfileActivity.this,ProtocolActivity.class);
        startActivity(intent);
    }
}
