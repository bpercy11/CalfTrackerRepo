package com.example.brett.calftracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class EditVaccineActivity extends AppCompatActivity {

    private AlertDialog alertDialog;
    private TextView vaccineName;
    private TextView dosage;
    private TextView dosageUnits;
    private TextView adminDays;
    private TextView adminMethod;
    private List<Vaccine> vaccineList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_vaccine);
/*
        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        if(mPreferences.contains("VaccineList")) {
            SharedPreferences.Editor editor = mPreferences.edit();

            Gson gson = new Gson();
            String json = mPreferences.getString("VaccineList", "");
            vaccineList = gson.fromJson(json, new TypeToken<ArrayList<Vaccine>>() {
            }.getType());
        } else { vaccineList = new ArrayList<Vaccine>(); }
*/
        android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder(EditVaccineActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.activity_edit_vaccine, null);

        final EditText mVaccine = (EditText) mView.findViewById(R.id.editTextVaccineName);
        final EditText mDosage = (EditText) mView.findViewById(R.id.editTextDosage);
        final EditText mDosageUnits = (EditText) mView.findViewById(R.id.editTextDosage);
        final EditText mAdminDays = (EditText) mView.findViewById(R.id.editTextAdminDate);
        final EditText mAdminMethod = (EditText) mView.findViewById(R.id.editTextVaccineAdminMethod);
        final Button mAddVaccine = (Button) mView.findViewById(R.id.buttonAddVaccine);
        final Button mCancel = (Button) mView.findViewById(R.id.buttonVaccineCancel);

        mAddVaccine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mVaccine.getText().toString().isEmpty() && !mDosage.getText().toString().isEmpty() &&
                        !mDosageUnits.getText().toString().isEmpty() && !mAdminDays.getText().toString().isEmpty()
                        && !mAdminMethod.getText().toString().isEmpty()){
                    Toast.makeText(EditVaccineActivity.this, R.string.vaccine_successful,
                            Toast.LENGTH_SHORT).show();
                    clickAddVaccineButton(view);
                }
                else {
                    Toast.makeText(EditVaccineActivity.this, R.string.empty_fields_message,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        mBuilder.setView(mView);
        android.app.AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    public void clickAddVaccineButton(View view){
        EditText vaccine = (EditText) findViewById(R.id.editTextVaccineName);
        EditText dosage = (EditText) findViewById(R.id.editTextVaccineDosage);
        EditText dosageUnits = (EditText) findViewById(R.id.editTextDosageUnits);
        EditText activeDays = (EditText) findViewById(R.id.editTextTimeActive);
        EditText adminMethod = (EditText) findViewById(R.id.editTextVaccineAdminMethod);

        String vaccineStr = vaccine.getText().toString();
        Double dosageDbl = Double.parseDouble(dosage.getText().toString());
        String dosageUnitsStr = dosageUnits.getText().toString();
        int activeDaysInt = Integer.parseInt(activeDays.getText().toString());
        String adminMethodString = adminMethod.getText().toString();

        /*
        // MAKE A NEW VACCINE OBJECT
        Vaccine newVaccine = new Vaccine(vaccineStr,activeDaysInt,dosageDbl,dosageUnitsStr,
               adminMethodString);
               */
        /*
        // SAVE NEW VACCINE
        SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(vaccineList);
        prefsEditor.putString("VaccineList",json);
        prefsEditor.apply();

        Intent intent = new Intent(this,ProtocolActivity.class);
        startActivity(intent);
        */
    }
    public void clickCancelVaccineButton(View view){
        Intent intent = new Intent(EditVaccineActivity.this,ProtocolActivity.class);
        startActivity(intent);
    }
}
