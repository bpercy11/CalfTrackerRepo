package com.example.brett.calftracker;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class EditMedicineActivity extends AppCompatActivity {

    private AlertDialog alertDialog;
    private TextView medicineName;
    private TextView dosage;
    private TextView dosageUnits;
    private TextView timeActive;
    private List<Medicine> medicineList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_medicine);

        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        if(mPreferences.contains("MedicineList")) {
            SharedPreferences.Editor editor = mPreferences.edit();

            Gson gson = new Gson();
            String json = mPreferences.getString("MedicineList", "");
            medicineList = gson.fromJson(json, new TypeToken<ArrayList<Medicine>>() {
            }.getType());
        } else { medicineList = new ArrayList<Medicine>(); }
    }

    public void clickAddMedicineButton(View view){
        EditText name = (EditText) findViewById(R.id.edit_medicine_editTextMedicine);
        EditText treatment = (EditText) findViewById(R.id.edit_medicine_editTextTreatment);
        EditText dosage = (EditText) findViewById(R.id.edit_medicine_editTextDosage);
        EditText dosageUnits = (EditText) findViewById(R.id.edit_medicine_editTextDosageUnits);
        EditText timeActive = (EditText) findViewById(R.id.edit_medicine_editTextTimeActive);

        if (name.getText().toString().matches("") || treatment.getText().toString().matches("")
                || dosage.getText().toString().matches("") || dosageUnits.getText().toString().matches("")
                || timeActive.getText().toString().matches("")){
            Toast.makeText(EditMedicineActivity.this, R.string.empty_fields_message,
                    Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(EditMedicineActivity.this, R.string.add_medicine_successful_message,
                    Toast.LENGTH_SHORT).show();
        }

        String nameStr = name.getText().toString();
        String treatmentStr = treatment.getText().toString();
        Double dosageDbl = Double.parseDouble(dosage.getText().toString());
        String dosageUnitsStr = dosageUnits.getText().toString();
        int timeActiveInt = Integer.parseInt(timeActive.getText().toString());

        // MAKE A NEW Medicine OBJECT
        Medicine medicine = new Medicine(treatmentStr,dosageDbl,dosageUnitsStr,timeActiveInt);
        Treatment_Protocol tp = new Treatment_Protocol(medicine, "");

        medicineList.add(medicine);

        // SAVE NEW Medicine
        SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(medicineList);
        prefsEditor.putString("MedicineList",json);
        prefsEditor.apply();

        Intent intent = new Intent(this,MedicineActivity.class);
        startActivity(intent);
    }

    public void clickCancelButton(View view){
        Intent intent = new Intent(EditMedicineActivity.this, MedicineActivity.class);
        startActivity(intent);
    }

}
