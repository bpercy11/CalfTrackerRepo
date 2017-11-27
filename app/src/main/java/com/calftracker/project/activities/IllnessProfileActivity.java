package com.calftracker.project.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.sip.SipSession;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Illness;
import com.calftracker.project.models.Medicine;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class IllnessProfileActivity extends BaseActivity {

    private ArrayList<Illness> illnessList;
    private Illness illness;
    private String illnessNotesStr;
    private int illnessPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLayoutInflater().inflate(R.layout.activity_illness_profile, frameLayout);
        mNavigationView.getMenu().findItem(R.id.nav_protocols).setChecked(true);

        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);

        //Load clicked Illness
        if(mPreferences.contains("IllnessProfile")) {
            SharedPreferences.Editor editor = mPreferences.edit();

            Gson gson = new Gson();
            String json = mPreferences.getString("IllnessProfile", "");
            illness = gson.fromJson(json, new TypeToken<Illness>() {
            }.getType());
        } else { }

        //Load IllnessList
        if(mPreferences.contains("IllnessList")) {
            SharedPreferences.Editor editor = mPreferences.edit();

            Gson gson = new Gson();
            String json = mPreferences.getString("IllnessList", "");
            illnessList = gson.fromJson(json, new TypeToken<ArrayList<Illness>>() {
            }.getType());
        } else { }

        //Finding position of illness object
        for (int i = 0; i < illnessList.size(); i++){
            if(illnessList.get(i).getName().equals(illness.getName())){
                illnessPosition = i;
            }
        }

        String treatmentProtocolStr = "";
        List<Medicine> tpMedicines = illness.getTreatmentProtocol().getMedicines();

        //Creating Treatment String
        for (int i = 0; i < tpMedicines.size(); i++){
            treatmentProtocolStr += tpMedicines.get(i).getName();
            if (i != tpMedicines.size()-1){
                treatmentProtocolStr += ", ";
            }
        }

        //Finding where Data needs to be displayed
        TextView illnessName = (TextView) findViewById(R.id.illness_profile_nameData);
        TextView treatmentProtocol = (TextView) findViewById(R.id.illness_profile_treatmentData);
        TextView illnessNotes = (TextView) findViewById(R.id.illness_profile_notesData);

        //Setting Data with correct illness information
        illnessName.setText(illness.getName());
        treatmentProtocol.setText(treatmentProtocolStr);
        illnessNotes.setText(illness.getTreatmentProtocol().getNotes());

    }

    public void onIllnessProfile_EditButton(View view){
        Intent intent = new Intent(IllnessProfileActivity.this, EditIllnessProfileActivity.class);
        startActivity(intent);
    }

    public void onIllnessProfile_RemoveButton(View view){

        illnessList.remove(illnessPosition);

        //Save updated IllnessList
        SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(illnessList);
        prefsEditor.putString("IllnessList",json);
        prefsEditor.apply();

        Intent intent = new Intent(IllnessProfileActivity.this,IllnessActivity.class);
        startActivity(intent);

    }
}
