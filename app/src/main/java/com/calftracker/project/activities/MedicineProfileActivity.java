package com.calftracker.project.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Medicine;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MedicineProfileActivity extends BaseActivity {

    private Medicine medicine;
    private List<Medicine> medicineList;
    private int medicinePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLayoutInflater().inflate(R.layout.activity_medicine_profile, frameLayout);
        mNavigationView.getMenu().findItem(R.id.nav_protocols).setChecked(true);

        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);

        //Load clicked medicine
        if(mPreferences.contains("MedicineProfile")) {
            SharedPreferences.Editor editor = mPreferences.edit();

            Gson gson = new Gson();
            String json = mPreferences.getString("MedicineProfile", "");
            medicine = gson.fromJson(json, new TypeToken<Medicine>() {
            }.getType());
        } else { }

        //Load MedicineList
        if(mPreferences.contains("MedicineList")) {
            SharedPreferences.Editor editor = mPreferences.edit();

            Gson gson = new Gson();
            String json = mPreferences.getString("MedicineList", "");
            medicineList = gson.fromJson(json, new TypeToken<ArrayList<Medicine>>() {
            }.getType());
        } else { }

        //Find medicine position in medicineList
        for (int i = 0; i < medicineList.size(); i++){
            if(medicineList.get(i).getName().equals(medicine.getName())){
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

    public void onMProfile_removeButton(View view){

        medicineList.remove(medicinePosition);

        //Save updated medicineList
        SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(medicineList);
        prefsEditor.putString("MedicineList",json);
        prefsEditor.apply();

        Intent intent = new Intent(MedicineProfileActivity.this,MedicineActivity.class);
        startActivity(intent);
    }

    public void onMProfile_editButton(View view){
        Intent intent = new Intent(MedicineProfileActivity.this,EditMedicineProfileActivity.class);
        startActivity(intent);
    }

}
