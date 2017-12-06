package com.calftracker.project.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Illness;
import com.calftracker.project.models.Medicine;
import com.calftracker.project.models.Treatment_Protocol;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MedicineProfileActivity extends AppCompatActivity {

    private Medicine medicine;
    private List<Medicine> medicineList;
    private int medicinePosition;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_profile);

        // Stylize action bar to use back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);

        //Load clicked medicine
        if (mPreferences.contains("MedicineProfile")) {
            SharedPreferences.Editor editor = mPreferences.edit();

            Gson gson = new Gson();
            String json = mPreferences.getString("MedicineProfile", "");
            medicine = gson.fromJson(json, new TypeToken<Medicine>() {
            }.getType());
        }

        // Custom title
        getSupportActionBar().setTitle(medicine.getName());

        //Load MedicineList
        if (mPreferences.contains("MedicineList")) {
            SharedPreferences.Editor editor = mPreferences.edit();

            Gson gson = new Gson();
            String json = mPreferences.getString("MedicineList", "");
            medicineList = gson.fromJson(json, new TypeToken<ArrayList<Medicine>>() {
            }.getType());
        }

        //Find medicine position in medicineList
        for (int i = 0; i < medicineList.size(); i++) {
            if (medicineList.get(i).getName().equals(medicine.getName())) {
                medicinePosition = i;
            }
        }

        //Finding where Data needs to be displayed
        TextView medicineName = (TextView) findViewById(R.id.medicine_profile_medicine_nameData);
        TextView medicineDosage = (TextView) findViewById(R.id.medicine_profile_dosageData);
        TextView medicineDosageUnits = (TextView) findViewById(R.id.medicine_profile_dosageUnitsData);
        TextView medicineTimeActive = (TextView) findViewById(R.id.medicine_profile_timeActiveData);
        TextView medicineNotes = (TextView) findViewById(R.id.medicine_profile_notesData);

        //Setting Data with correct medicine information
        medicineName.setText(medicine.getName());
        medicineDosage.setText(Double.toString(medicine.getDosage()));
        medicineDosageUnits.setText(medicine.getDosage_units());
        medicineTimeActive.setText(Integer.toString(medicine.getTimeActive()) + " days");
        medicineNotes.setText(medicine.getNotes());
    }

    public void onMProfile_removeButton(View view) {

        medicineList.remove(medicinePosition);

        //Save updated medicineList
        SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(medicineList);
        prefsEditor.putString("MedicineList", json);
        prefsEditor.apply();

        // if this medicine is removed, make sure that no treatment protocol calls for this
        ArrayList<Illness> illnessList;
        List<Medicine> tempMedicineList;

        json = mPrefs.getString("IllnessList", "");
        illnessList = gson.fromJson(json, new TypeToken<ArrayList<Illness>>() {
        }.getType());

        for (int i = 0; i < illnessList.size(); i++) {
            if (illnessList.get(i).getTreatmentProtocol().getMedicines().contains(medicine)) {
                // illnessList.get(i).getTreatmentProtocol().getMedicines().remove(medicine);
                tempMedicineList = illnessList.get(i).getTreatmentProtocol().getMedicines();
                tempMedicineList.remove(medicine);
                Treatment_Protocol tempTP = new Treatment_Protocol
                        (tempMedicineList, illnessList.get(i).getTreatmentProtocol().getNotes());
                Illness tempIllness = new Illness(illnessList.get(i).getName(), tempTP);
                illnessList.remove(i);
                illnessList.add(tempIllness);
            }
        }


        json = gson.toJson(illnessList);
        prefsEditor.putString("IllnessList", json);
        prefsEditor.apply();

        Intent intent = new Intent(MedicineProfileActivity.this, MedicineActivity.class);
        startActivity(intent);
    }

    // TODO
    public void saveData() {
        // EMPTY METHOD TO KEEP CONSISTENCY
        // NO DATA IS SAVED IN THIS ACTIVITY
    }

    public void retrieveData() {
        // EMPTY METHOD TO KEEP CONSISTENCY
        // NO DATA IS RETRIEVED IN THIS ACTIVITY
    }

    public void onMProfile_editButton(View view){
        Intent intent = new Intent(MedicineProfileActivity.this,EditMedicineProfileActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MedicineActivity.class);
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(getApplicationContext(), MedicineActivity.class);
        startActivity(intent);
        return true;
    }
}
