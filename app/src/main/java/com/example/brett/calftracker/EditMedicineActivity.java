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
    private TextView illnessName;
    private TextView treatment;
    private TextView dosage;
    private TextView dosageUnits;
    private TextView timeActive;
    private TextView frequency;
    private List<Illness> illnessList;
  //  private List<MedicineFrequency> medicineFrequency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_medicine);

        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        if(mPreferences.contains("IllnessList")) {
            SharedPreferences.Editor editor = mPreferences.edit();

            Gson gson = new Gson();
            String json = mPreferences.getString("IllnessList", "");
            illnessList = gson.fromJson(json, new TypeToken<ArrayList<Illness>>() {
            }.getType());
        } else { illnessList = new ArrayList<Illness>(); }
    }

    public void clickAddMedicineButton(View view){
        EditText name = (EditText) findViewById(R.id.editTextIllness);
        EditText treatment = (EditText) findViewById(R.id.editTextTreatment);
        EditText dosage = (EditText) findViewById(R.id.editTextDosage);
        EditText dosageUnits = (EditText) findViewById(R.id.editTextMedicineDosageUnits);
        EditText timeActive = (EditText) findViewById(R.id.editTextTimeActive);
        EditText frequency = (EditText) findViewById(R.id.editTextMedicineFrequency);

        if (name.getText().toString().matches("") || treatment.getText().toString().matches("")
                || dosage.getText().toString().matches("") || dosageUnits.getText().toString().matches("")
                || timeActive.getText().toString().matches("")
                || frequency.getText().toString().matches("")){
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
        String frequencyStr = frequency.getText().toString();

        // MAKE A NEW ILLNESS OBJECT
        Medicine medicine = new Medicine(treatmentStr,dosageDbl,dosageUnitsStr,timeActiveInt,frequencyStr);
        Treatment_Protocol tp = new Treatment_Protocol(medicine, "");
        Illness newIllness = new Illness(nameStr,tp);

        illnessList.add(newIllness);

        // SAVE NEW ILLNESS
        SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(illnessList);
        prefsEditor.putString("IllnessList",json);
        prefsEditor.apply();

        Intent intent = new Intent(this,MedicineActivity.class);
        startActivity(intent);
    }

    public void clickCancelButton(View view){
        Intent intent = new Intent(EditMedicineActivity.this, MedicineActivity.class);
        startActivity(intent);
    }

}
