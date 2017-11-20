package com.calftracker.project.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.calftracker.project.models.Medicine;
import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Treatment_Protocol;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class EditMedicineActivity extends AppCompatActivity {

    private AlertDialog alertDialog;
    private EditText medicineName;
    private EditText dosage;
    private EditText dosageUnits;
    private EditText timeActive;
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

       // dosage.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
    }

    public void clickAddMedicineButton(View view){
        medicineName = (EditText) findViewById(R.id.edit_medicine_editTextMedicine);
        dosage = (EditText) findViewById(R.id.edit_medicine_editTextDosage);
        dosageUnits = (EditText) findViewById(R.id.edit_medicine_editTextDosageUnits);
        timeActive = (EditText) findViewById(R.id.edit_medicine_editTextTimeActive);

        if (medicineName.getText().toString().matches("")
                || dosage.getText().toString().matches("")
                || dosageUnits.getText().toString().matches("")
                || timeActive.getText().toString().matches("")){
            Toast.makeText(EditMedicineActivity.this, R.string.empty_fields_message,
                    Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            Toast.makeText(EditMedicineActivity.this, R.string.add_medicine_successful_message,
                    Toast.LENGTH_SHORT).show();
        }

        String nameStr = medicineName.getText().toString();
        Double dosageDbl = Double.parseDouble(dosage.getText().toString());
        String dosageUnitsStr = dosageUnits.getText().toString();
        int timeActiveInt = Integer.parseInt(timeActive.getText().toString());

        // MAKE A NEW Medicine OBJECT
        Medicine medicine = new Medicine(nameStr,dosageDbl,dosageUnitsStr,timeActiveInt);

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
