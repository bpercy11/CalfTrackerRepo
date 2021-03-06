package com.calftracker.project.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class AddVaccineActivity extends AppCompatActivity {

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
    private int adminStartInteger;
    private String[] dropDownItems;
    private Spinner dropDown;
    private Spinner dropDown1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vaccine);

        // Custom title
        getSupportActionBar().setTitle(R.string.protocols_vaccine_add);

        EditText dosage = (EditText) findViewById(R.id.edit_vaccine_editTextDosage);

        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);

        if(mPreferences.contains("VaccineList")) {
            SharedPreferences.Editor editor = mPreferences.edit();

            Gson gson = new Gson();
            String json = mPreferences.getString("VaccineList", "");
            vaccineList = gson.fromJson(json, new TypeToken<ArrayList<Vaccine>>() {
            }.getType());
        } else { vaccineList = new ArrayList<Vaccine>(); }


        //get the spinner from the xml.
        dropDown = (Spinner)findViewById(R.id.edit_vaccine_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.vacc_range_spinner, android.R.layout.simple_spinner_dropdown_item);
        // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dropDown.setAdapter(adapter);

        dropDown1 = (Spinner)findViewById(R.id.edit_vaccine_spinner1);
        dropDown1.setAdapter(adapter);

        ((Button) findViewById(R.id.edit_vaccine_buttonAddVaccine)).setText("Add");
    }

    public void clickAddVaccineButton(View view){
        EditText vaccine = (EditText) findViewById(R.id.edit_vaccine_editTextName);
        EditText adminStart = (EditText) findViewById(R.id.edit_vaccine_editTextAdminStart);
        EditText adminEnd = (EditText) findViewById(R.id.edit_vaccine_editTextAdminEnd);
        EditText dosage = (EditText) findViewById(R.id.edit_vaccine_editTextDosage);
        EditText dosageUnits = (EditText) findViewById(R.id.edit_vaccine_editTextDosageUnits);
        EditText adminMethod = (EditText) findViewById(R.id.edit_vaccine_editTextAdminMethod);

        if (vaccine.getText().toString().matches("") || dosage.getText().toString().matches("")
                || adminStart.getText().toString().matches("") || adminEnd.getText().toString().matches("")
                || adminMethod.getText().toString().matches("")){
            Toast.makeText(AddVaccineActivity.this, R.string.empty_fields_message,
                    Toast.LENGTH_SHORT).show();
            return;
        }
        String vaccineStr = vaccine.getText().toString();
        int adminStartInt = Integer.parseInt(adminStart.getText().toString());
        int adminEndInt = Integer.parseInt(adminEnd.getText().toString());
        Double dosageDbl = Double.parseDouble(dosage.getText().toString());
        String dosageUnitsStr = dosageUnits.getText().toString();
        String adminMethodString = adminMethod.getText().toString();

        for (int i = 0; i < vaccineList.size(); i++) {
            if (vaccineList.get(i).getName().equals(vaccineStr)) {
                Toast.makeText(AddVaccineActivity.this, "A vaccine with this name already exists, please choose a new name or delete the other vaccine",
                        Toast.LENGTH_LONG).show();
                vaccine.setText("");
                return;
            }
        }

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

        switch(dropDown1.getSelectedItem().toString()){
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

        if(adminEndInt <= adminStartInt){
            Toast.makeText(AddVaccineActivity.this, R.string.edit_vaccine_vacc_range_error,
                    Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            Toast.makeText(AddVaccineActivity.this, R.string.vaccine_successful,
                    Toast.LENGTH_SHORT).show();
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

        Intent intent = new Intent(this,VaccineActivity.class);
        startActivity(intent);
    }

    public void clickCancelVaccineButton(View view){
        Intent intent = new Intent(AddVaccineActivity.this,VaccineActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, VaccineActivity.class);
        startActivity(intent);
    }
}
