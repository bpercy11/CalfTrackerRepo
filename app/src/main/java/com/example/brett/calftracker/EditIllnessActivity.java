package com.example.brett.calftracker;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiConfiguration;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

public class EditIllnessActivity extends AppCompatActivity {

    private AlertDialog alertDialog;
    private TextView illnessName;
    private TextView treatment;
    private TextView dosage;
    private TextView timeActive;
    private TextView adminMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_illness);

    }

    public void clickAddIllnessButton(View view){
        EditText name = (EditText) findViewById(R.id.editTextIllness);
        EditText treatment = (EditText) findViewById(R.id.editTextTreatment);
        EditText dosage = (EditText) findViewById(R.id.editTextDosage);
        EditText timeActive = (EditText) findViewById(R.id.editTextTimeActive);
        EditText adminMethod = (EditText) findViewById(R.id.editTextAdminMethod);

        String treatmentStr = treatment.getText().toString();
        Double dosageDbl = Double.parseDouble(dosage.getText().toString());
        int timeActiveInt = Integer.parseInt(timeActive.getText().toString());
        String adminMethodString = adminMethod.getText().toString();
        String nameStr = name.getText().toString();

        // MAKE A NEW ILLNESS OBJECT
        Medicine medicine = new Medicine(treatmentStr,dosageDbl,null,timeActiveInt,adminMethodString);
        Treatment_Protocol tp = new Treatment_Protocol(medicine, "");
        Illness newIllness = new Illness(nameStr,tp);

        // SAVE NEW ILLNESS
        SharedPreferences mPrefs = getSharedPreferences("test", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(newIllness);
        prefsEditor.putString("newCalf",json);
        prefsEditor.apply();
        // GO TO NEWLWY CREATED CALF PROFILE

        Intent intent = new Intent(this,ProtocolActivity.class);
        startActivity(intent);
    }

}
