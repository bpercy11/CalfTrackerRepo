package com.calftracker.project.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Illness;
import com.calftracker.project.models.Treatment_Protocol;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class EditIllnessProfileActivity extends AppCompatActivity {

    private Illness illness;
    private ArrayList<Illness> illnessList;
    private EditText illnessName;
    private EditText illnessNotes;
    private String illnessNotesStr;
    final Context context = this;
    private int illnessPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_illness_profile);


        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        if(mPreferences.contains("IllnessProfile")) {
            SharedPreferences.Editor editor = mPreferences.edit();

            Gson gson = new Gson();
            String json = mPreferences.getString("IllnessProfile", "");
            illness = gson.fromJson(json, new TypeToken<Illness>() {
            }.getType());
        } else { }

        if(mPreferences.contains("IllnessList")) {
            SharedPreferences.Editor editor = mPreferences.edit();

            Gson gson = new Gson();
            String json = mPreferences.getString("IllnessList", "");
            illnessList = gson.fromJson(json, new TypeToken<ArrayList<Illness>>() {
            }.getType());
        } else { }

        // get the position of the illness to be edited
        for (int i = 0; i < illnessList.size(); i++){
            if(illnessList.get(i).getName().equals(illness.getName())){
                illnessPosition = i;
            }
        }

        illnessName = (EditText) findViewById(R.id.editIllnessProfile_enterName);
        illnessNotes = (EditText) findViewById(R.id.editIllnessProfile_enterNotes);

        illnessName.setText(illness.getName());
        illnessNotes.setText((CharSequence) illness.getTreatmentProtocol().getNotes());



    }


    public void addEditedIllnessButton(View view){

        illnessList.remove(illnessPosition);

        illnessName = (EditText) findViewById(R.id.editIllnessProfile_enterName);
        illnessNotes = (EditText) findViewById(R.id.editIllnessProfile_enterNotes);

        if (illnessName.getText().toString().matches("")){
            Toast.makeText(EditIllnessProfileActivity.this, "Please fill any empty fields",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        String illnessNameStr = illnessName.getText().toString();
        illnessNotesStr = illnessNotes.getText().toString();

        SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(illnessNameStr);
        prefsEditor.putString("illnessName",json);
        prefsEditor.apply();

        json = gson.toJson(illnessNotesStr);
        prefsEditor.putString("illnessNotes", json);
        prefsEditor.apply();

        json = gson.toJson(illnessList);
        prefsEditor.putString("IllnessList",json);
        prefsEditor.apply();

        Intent intent = new Intent(EditIllnessProfileActivity.this, EditIllnessProfileMedicineSelectionActivity.class);
        startActivity(intent);

    }

    public void cancelEditedIllnessButton(View view){
        Intent intent = new Intent(EditIllnessProfileActivity.this, IllnessActivity.class);
        startActivity(intent);
    }

}
