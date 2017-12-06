package com.calftracker.project.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.calftracker.project.adapters.protocols.MedicineAdapter;
import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Illness;
import com.calftracker.project.models.Medicine;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class AddIllnessActivity extends AppCompatActivity {

    private AlertDialog alertDialog;
    private String illnessNameStr;
    private String illnessNotes;
    private ListView lvTreatmentProtocolMedicines;
    private List<Illness> illnessList;
    private List<Medicine> medicineList;
    private MedicineAdapter medicineAdapter;
    private List<Medicine> tempMedicineList;
    private Button addNotesButton;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_illness);

        // Custom title
        getSupportActionBar().setTitle(R.string.protocols_illness_add);

        // Custom layout for text dialogs
        inflater = getLayoutInflater();


        addNotesButton = (Button) findViewById(R.id.EditIllnessAddNotesButton);
        // add button listener
        addNotesButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AddIllnessActivity.this);
                View dialogLayout_Notes = inflater.inflate(R.layout.custom_dialog_input, null);
                final EditText input =  (EditText) dialogLayout_Notes.findViewById(R.id.dialogTextInput);

                // set title
                alertDialogBuilder.setTitle("Enter Note");
                alertDialogBuilder.setView(dialogLayout_Notes);
                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Add",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                illnessNotes = input.getText().toString();
                                // if this button is clicked, close current activity
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close the dialog box and do nothing
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

        EditText illnessName = (EditText) findViewById(R.id.editTextIllnessEnterName);
        illnessNameStr = illnessName.getText().toString();

        if (illnessNameStr.matches("")) {
            Toast.makeText(AddIllnessActivity.this, R.string.EditIllnessActivity_emptyFieldMsg, Toast.LENGTH_SHORT).show();
            return;
        }

        // save the illness name/notes to local storage
        SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        Gson gson = gsonBuilder.create();
        String json;

        json = gson.toJson(illnessName.getText().toString());
        prefsEditor.putString("thisIllnessName", json);
        prefsEditor.apply();

        json = gson.toJson(illnessNotes);
        prefsEditor.putString("illnessNotes", json);
        prefsEditor.apply();

        Intent intent = new Intent(this, AddIllnessMedicineSelectionActivity.class);
        startActivity(intent);

    }

    public void clickEditIllnessCancelButton(View view){
        Intent intent = new Intent(AddIllnessActivity.this, IllnessActivity.class);
        startActivity(intent);
    }
}


