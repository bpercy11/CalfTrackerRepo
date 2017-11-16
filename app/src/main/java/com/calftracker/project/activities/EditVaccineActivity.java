package com.calftracker.project.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Vacc_Range;
import com.calftracker.project.models.Vaccine;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class EditVaccineActivity extends AppCompatActivity {

    private AlertDialog alertDialog;
    private TextView vaccineName;
    private TextView dosage;
    private TextView dosageUnits;
    private TextView adminStart;
    private TextView adminEnd;
    private TextView adminMethod;
    private ArrayList<Vaccine> vaccineList;
    private ArrayList<Vacc_Range> vaccRange;
    private int[] range;
    private String[] dropDownItems;
    private Spinner dropDown;
    private Spinner dropDown1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_vaccine);

        EditText dosage = (EditText) findViewById(R.id.edit_vaccine_editTextDosage);
        dosage.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);

        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);

        if(mPreferences.contains("VaccineList")) {
            SharedPreferences.Editor editor = mPreferences.edit();

            Gson gson = new Gson();
            String json = mPreferences.getString("VaccineList", "");
            vaccineList = gson.fromJson(json, new TypeToken<ArrayList<Vaccine>>() {
            }.getType());
        } else { vaccineList = new ArrayList<Vaccine>(); }
    }

    public void clickAddVaccineButton(View view){
        EditText vaccine = (EditText) findViewById(R.id.edit_vaccine_editTextName);
        EditText dosage = (EditText) findViewById(R.id.edit_vaccine_editTextDosage);
        EditText dosageUnits = (EditText) findViewById(R.id.edit_vaccine_editTextDosageUnits);
        EditText adminStart = (EditText) findViewById(R.id.edit_vaccine_editTextAdminDateStart);
        EditText adminEnd = (EditText) findViewById(R.id.edit_vaccine_editTextAdminDateEnd);
        EditText adminMethod = (EditText) findViewById(R.id.edit_vaccine_editTextAdminMethod);


        if (vaccine.getText().toString().matches("") || dosage.getText().toString().matches("")
                || adminStart.getText().toString().matches("") || adminEnd.getText().toString().matches("")
                || adminMethod.getText().toString().matches("")){
            Toast.makeText(EditVaccineActivity.this, R.string.empty_fields_message,
                    Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(EditVaccineActivity.this, R.string.vaccine_successful,
                Toast.LENGTH_SHORT).show();
        }

        String vaccineStr = vaccine.getText().toString();
        int adminStartInt = Integer.parseInt(adminStart.getText().toString());
        int adminEndInt = Integer.parseInt(adminEnd.getText().toString());
        Double dosageDbl = Double.parseDouble(dosage.getText().toString());
        String dosageUnitsStr = dosageUnits.getText().toString();
        String adminMethodString = adminMethod.getText().toString();

        //get the spinner from the xml.
        dropDown = (Spinner)findViewById(R.id.edit_vaccine_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.vacc_range_spinner, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dropDown.setAdapter(adapter);

        switch(dropDown.getSelectedItem().toString()){
            case "Day":
                adminStartInt = adminStartInt*1;
                break;
            case "Week":
                adminStartInt = adminStartInt*7;
                break;
            case "Month":
                adminStartInt = adminStartInt*30;
                break;
        }

        dropDown1 = (Spinner)findViewById(R.id.edit_vaccine_spinner1);
        dropDown1.setAdapter(adapter);

        switch(dropDown.getSelectedItem().toString()){
            case "Day":
                adminEndInt = adminEndInt*1;
                break;
            case "Week":
                adminEndInt = adminEndInt*7;
                break;
            case "Month":
                adminEndInt = adminEndInt*30;
                break;
        }

        range = new int[] {adminStartInt,adminEndInt};

        vaccRange = new ArrayList<Vacc_Range>();
        vaccRange.add(new Vacc_Range(range));

        // MAKE A NEW VACCINE OBJECT
        Vaccine newVaccine = new Vaccine(vaccineStr, vaccRange, dosageDbl, dosageUnitsStr,
                adminMethodString);

        vaccineList.add(newVaccine);

        // SAVE NEW VACCINE
        SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(vaccineList);
        prefsEditor.putString("VaccineList",json);
        prefsEditor.apply();

        Intent intent = new Intent(this,ProtocolActivity.class);
        startActivity(intent);
    }

    public void clickCancelVaccineButton(View view){
        Intent intent = new Intent(EditVaccineActivity.this,ProtocolActivity.class);
        startActivity(intent);
    }
}
