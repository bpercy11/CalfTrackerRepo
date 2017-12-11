package com.calftracker.project.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Vacc_Range;
import com.calftracker.project.models.Vaccine;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class EditVaccineProfileActivity extends AppCompatActivity {

    private ConstraintLayout mConstraintLayout;
    private Vaccine vaccine;
    private int vaccinePosition;
    private ArrayList<Vaccine> vaccineList;
    private ArrayList<Vacc_Range> vaccRange;
    private int[] range;
    private int[] preRange;
    private int adminStartInteger;
    private String[] dropDownItems;
    private Spinner dropDown;
    private Spinner dropDown1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vaccine);

        // Custom title
        getSupportActionBar().setTitle(R.string.edit_vaccine_edit_vaccine);

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

        //get the spinner from the xml.
        dropDown = (Spinner)findViewById(R.id.edit_vaccine_spinner);
        dropDown1 = (Spinner)findViewById(R.id.edit_vaccine_spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.vacc_range_spinner, android.R.layout.simple_spinner_dropdown_item);
        dropDown.setAdapter(adapter);
        dropDown1.setAdapter(adapter);

        EditText vaccineName = (EditText) findViewById(R.id.edit_vaccine_editTextName);
        EditText adminStart = (EditText) findViewById(R.id.edit_vaccine_editTextAdminStart);
        EditText adminEnd = (EditText) findViewById(R.id.edit_vaccine_editTextAdminEnd);
        EditText dosage = (EditText) findViewById(R.id.edit_vaccine_editTextDosage);
        EditText dosageUnits = (EditText) findViewById(R.id.edit_vaccine_editTextDosageUnits);
        EditText adminMethod = (EditText) findViewById(R.id.edit_vaccine_editTextAdminMethod);


        vaccineName.setText(vaccine.getName());
        preRange = vaccine.getToBeAdministered().get(0).getSpan();
        adminStart.setText(Integer.toString(preRange[0]));
        adminEnd.setText(Integer.toString(preRange[1]));
        dosage.setText(Double.toString(vaccine.getDosage()));
        dosageUnits.setText(vaccine.getDosageUnits());
        adminMethod.setText(vaccine.getMethodOfAdministration());

        ((Button) findViewById(R.id.edit_vaccine_buttonAddVaccine)).setText("Apply");

        // Show "Delete" button
        ((Button) findViewById(R.id.vaccine_profile_removeButton)).setVisibility(View.VISIBLE);

        // Rearrange buttons
        mConstraintLayout = (ConstraintLayout) findViewById(R.id.add_vaccine_layout);
        ConstraintSet set = new ConstraintSet();
        set.clone(mConstraintLayout);
        set.clear(R.id.edit_vaccine_buttonCancel, ConstraintSet.BOTTOM);
        set.applyTo(mConstraintLayout);
    }

    public void clickAddVaccineButton(View view){

        vaccineList.remove(vaccinePosition);

        EditText vaccine = (EditText) findViewById(R.id.edit_vaccine_editTextName);
        EditText adminStart = (EditText) findViewById(R.id.edit_vaccine_editTextAdminStart);
        EditText adminEnd = (EditText) findViewById(R.id.edit_vaccine_editTextAdminEnd);
        EditText dosage = (EditText) findViewById(R.id.edit_vaccine_editTextDosage);
        EditText dosageUnits = (EditText) findViewById(R.id.edit_vaccine_editTextDosageUnits);
        EditText adminMethod = (EditText) findViewById(R.id.edit_vaccine_editTextAdminMethod);

        String vaccineStr = vaccine.getText().toString();
        int adminStartInt = Integer.parseInt(adminStart.getText().toString());
        int adminEndInt = Integer.parseInt(adminEnd.getText().toString());
        Double dosageDbl = Double.parseDouble(dosage.getText().toString());
        String dosageUnitsStr = dosageUnits.getText().toString();
        String adminMethodString = adminMethod.getText().toString();

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

        if (vaccine.getText().toString().matches("") || dosage.getText().toString().matches("")
                || adminStart.getText().toString().matches("") || adminEnd.getText().toString().matches("")
                || adminMethod.getText().toString().matches("")){
            Toast.makeText(EditVaccineProfileActivity.this, R.string.empty_fields_message,
                    Toast.LENGTH_SHORT).show();
            return;
        }
        else if(adminEndInt <= adminStartInt){
            Toast.makeText(EditVaccineProfileActivity.this, R.string.edit_vaccine_vacc_range_error,
                    Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            Toast.makeText(EditVaccineProfileActivity.this, R.string.vaccine_successful,
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

    public void onVProfile_removeButton(View view){
        AlertDialog.Builder builderDelete = new AlertDialog.Builder(this);
        builderDelete.setMessage("Are you sure you want to delete this vaccine? This action cannot be undone.")
                .setTitle("Delete Vaccine");
        builderDelete.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                vaccineList.remove(vaccinePosition);

                //Save Updated VaccineList
                SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                Gson gson = new Gson();
                String json = gson.toJson(vaccineList);
                prefsEditor.putString("VaccineList",json);
                prefsEditor.apply();

                // Show a toast saying that the vaccine was removed
                Context context = getApplicationContext();
                CharSequence text = "Vaccine successfully deleted";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                Intent intent = new Intent(EditVaccineProfileActivity.this,VaccineActivity.class);
                startActivity(intent);
            }
        });
        builderDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Do nothing
            }
        });

        AlertDialog alertDelete = builderDelete.create();
        alertDelete.show();
    }

    public void clickCancelVaccineButton(View view){
        Intent intent = new Intent(EditVaccineProfileActivity.this,VaccineActivity.class);
        startActivity(intent);
    }
}
