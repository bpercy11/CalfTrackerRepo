package com.calftracker.project.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.calftracker.project.models.Medicine;
import com.calftracker.project.models.Illness;
import com.calftracker.project.models.Treatment_Protocol;
import com.calftracker.project.adapters.MedicineAdapter;
import com.calftracker.project.calftracker.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class EditIllnessActivity extends AppCompatActivity {

    private AlertDialog alertDialog;
    private EditText illnessName;
    private String illnessNotes;
    private ListView lvTreatmentProtocolMedicines;
    private List<Illness> illnessList;
    private List<Medicine> medicineList;
    private MedicineAdapter medicineAdapter;
    private List<Medicine> tempMedicineList;
    private Button addNotesButton;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_illness);

        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        if (mPreferences.contains("IllnessList")) {
            SharedPreferences.Editor editor = mPreferences.edit();

            Gson gson = new Gson();
            String json = mPreferences.getString("IllnessList", "");
            illnessList = gson.fromJson(json, new TypeToken<ArrayList<Illness>>() {
            }.getType());
        } else {
            illnessList = new ArrayList<Illness>();
        }

        SharedPreferences mPreferences1 = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        if (mPreferences1.contains("MedicineList")) {
            SharedPreferences.Editor editor = mPreferences1.edit();

            Gson gson1 = new Gson();
            String json1 = mPreferences1.getString("MedicineList", "");
            medicineList = gson1.fromJson(json1, new TypeToken<ArrayList<Medicine>>() {
            }.getType());
        } else {
            medicineList = new ArrayList<Medicine>();
        }


//        lvTreatmentProtocolMedicines = (ListView) findViewById(R.id.listViewEditIllness);
//        MedicineAdapter newMedicineAdapter = new MedicineAdapter(getApplicationContext(), android.R.layout.simple_list_item_multiple_choice,medicineList);
//        lvTreatmentProtocolMedicines.setAdapter(newMedicineAdapter);
//        lvTreatmentProtocolMedicines.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//
//        lvTreatmentProtocolMedicines.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                Intent intent = new Intent(EditIllnessActivity.this, AddIllnessMedicineSelectionActivity.class);
//                startActivity(intent);
//
//
//            }
//        });
    }
    public void onAddNotesButton(View view){


        addNotesButton = (Button) findViewById(R.id.EditIllnessAddNotesButton);
        // add button listener
        addNotesButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set title
                alertDialogBuilder.setTitle("Please enter a note");
                final EditText input = new EditText(EditIllnessActivity.this);
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
                                illnessNotes = input.getText().toString();
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
        });
    }

    public void clickAddIllnessButton(View view){

        illnessName = (EditText) findViewById(R.id.editTextIllnessEnterName);

        if (illnessName.getText().toString().matches("")){
            Toast.makeText(EditIllnessActivity.this, R.string.EditIllnessActivity_emptyFieldMsg, Toast.LENGTH_SHORT).show();
            return;
        }

        // save the illness name/notes to local storage
        SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(illnessName);
        prefsEditor.putString("Illness Name", json);
        prefsEditor.apply();

        prefsEditor = mPrefs.edit();
        json = gson.toJson(illnessNotes);
        prefsEditor.putString("Illness Notes", json);
        prefsEditor.apply();

        Intent intent = new Intent(EditIllnessActivity.this, AddIllnessMedicineSelectionActivity.class);
        startActivity(intent);

    }

    public void clickEditIllnessCancelButton(View view){
        Intent intent = new Intent(EditIllnessActivity.this, IllnessActivity.class);
        startActivity(intent);
    }
}

