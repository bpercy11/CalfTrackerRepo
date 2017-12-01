package com.calftracker.project.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Medicine;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class EditMedicineProfileActivity extends AppCompatActivity {

    private Medicine medicine;
    private AlertDialog alertDialog;
    private EditText medicineName;
    private EditText dosage;
    private EditText dosageUnits;
    private EditText timeActive;
    private EditText editMedicineNotes;
    private List<Medicine> medicineList;
    private Button notesButton;
    private EditText medicineNotes;
    final Context context = this;
    private int medicinePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_medicine);

        // Custom title
        getSupportActionBar().setTitle(R.string.edit_medicine);

        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        if(mPreferences.contains("MedicineProfile")) {
            SharedPreferences.Editor editor = mPreferences.edit();

            Gson gson = new Gson();
            String json = mPreferences.getString("MedicineProfile", "");
            medicine = gson.fromJson(json, new TypeToken<Medicine>() {
            }.getType());
        } else { }

        if(mPreferences.contains("MedicineList")) {
            SharedPreferences.Editor editor = mPreferences.edit();

            Gson gson = new Gson();
            String json = mPreferences.getString("MedicineList", "");
            medicineList = gson.fromJson(json, new TypeToken<ArrayList<Medicine>>() {
            }.getType());
        } else { }

        for (int i = 0; i < medicineList.size(); i++){
            if(medicineList.get(i).getName().equals(medicine.getName())){
                medicinePosition = i;
            }
        }

        medicineName = (EditText) findViewById(R.id.edit_medicine_editTextMedicine);
        dosage = (EditText) findViewById(R.id.edit_medicine_editTextDosage);
        dosageUnits = (EditText) findViewById(R.id.edit_medicine_editTextDosageUnits);
        timeActive = (EditText) findViewById(R.id.edit_medicine_editTextTimeActive);
        medicineNotes = (EditText) findViewById(R.id.edit_medicine_editTextNotes);

        medicineName.setText(medicine.getName());
        dosage.setText(Double.toString(medicine.getDosage()));
        dosageUnits.setText(medicine.getDosage_units());
        timeActive.setText(Integer.toString(medicine.getTimeActive()));
        medicineNotes.setText(medicine.getNotes());

        ((Button) findViewById(R.id.edit_medicine_buttonAdd)).setText("Apply");

    }
    public void clickAddMedicineButton(View view){

        medicineList.remove(medicinePosition);

        medicineName = (EditText) findViewById(R.id.edit_medicine_editTextMedicine);
        dosage = (EditText) findViewById(R.id.edit_medicine_editTextDosage);
        dosageUnits = (EditText) findViewById(R.id.edit_medicine_editTextDosageUnits);
        timeActive = (EditText) findViewById(R.id.edit_medicine_editTextTimeActive);
        medicineNotes = (EditText) findViewById(R.id.edit_medicine_editTextNotes);

        if (medicineName.getText().toString().matches("")
                || dosage.getText().toString().matches("")
                || dosageUnits.getText().toString().matches("")
                || timeActive.getText().toString().matches("")){
            Toast.makeText(EditMedicineProfileActivity.this, R.string.empty_fields_message,
                    Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            Toast.makeText(EditMedicineProfileActivity.this, R.string.add_medicine_successful_message,
                    Toast.LENGTH_SHORT).show();
        }

        String nameStr = medicineName.getText().toString();
        Double dosageDbl = Double.parseDouble(dosage.getText().toString());
        String dosageUnitsStr = dosageUnits.getText().toString();
        int timeActiveInt = Integer.parseInt(timeActive.getText().toString());
        String medicineNotesStr = medicineNotes.getText().toString();


        // MAKE A NEW Medicine OBJECT
        Medicine medicine = new Medicine(nameStr,dosageDbl,dosageUnitsStr,timeActiveInt,medicineNotesStr);

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
    /*
    public void clickNotesButton(View view){

        notesButton = (Button) findViewById(R.id.edit_medicine_buttonNote);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle("Please enter a note");
        final EditText input = new EditText(EditMedicineProfileActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialogBuilder.setView(input);
        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Done",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        medicineNotes = input.getText().toString();
                        // if this button is clicked, close
                        // current activity
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
    */

    public void clickCancelButton(View view){
        Intent intent = new Intent(EditMedicineProfileActivity.this, MedicineActivity.class);
        startActivity(intent);
    }
}
