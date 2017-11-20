package com.calftracker.project.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.calftracker.project.adapters.VaccineAdapter;
import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Vaccine;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class VaccineProfileActivity extends BaseActivity {

    private Vaccine vaccine;

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
        } else { /*vaccineList = new ArrayList<Vaccine>();*/ }

        TextView vaccineName = (TextView) findViewById(R.id.vaccine_profile_vaccine_nameData);
        TextView vaccineAdminStart = (TextView) findViewById(R.id.vaccine_profile_adminStartData);
        TextView vaccineAdminEnd = (TextView) findViewById(R.id.vaccine_profile_adminEndData);
        TextView vaccineDosage = (TextView) findViewById(R.id.vaccine_profile_dosageData);
        TextView vaccineDosageUnits = (TextView) findViewById(R.id.vaccine_profile_dosageUnitsData);
        TextView vaccineAdminMethod = (TextView) findViewById(R.id.vaccine_profile_adminMethodData);

        vaccineName.setText(vaccine.getName());
       // vaccineAdminStart.setText(Integer.toString(vaccine.getToBeAdministered().get(0)));
       // vaccineAdminEnd.setText(Integer.toString(vaccine.getToBeAdministered().get(1)));
        vaccineDosage.setText(Double.toString(vaccine.getDosage()));
        vaccineDosageUnits.setText(vaccine.getDosageUnits());
        vaccineAdminMethod.setText(vaccine.getMethodOfAdministration());

    }
}
