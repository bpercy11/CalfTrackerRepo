package com.calftracker.project.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Illness;
import com.calftracker.project.models.Medicine;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class IllnessProfileActivity extends BaseActivity {

    private Illness illness;
    private int i;
    private String medicines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getLayoutInflater().inflate(R.layout.activity_illness_profile, frameLayout);
        mNavigationView.getMenu().findItem(R.id.nav_protocols).setChecked(true);

        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        if(mPreferences.contains("IllnessProfile")) {
            SharedPreferences.Editor editor = mPreferences.edit();

            Gson gson = new Gson();
            String json = mPreferences.getString("IllnessProfile", "");
            illness = gson.fromJson(json, new TypeToken<Illness>() {
            }.getType());
        } else { }

        TextView illnessName = (TextView) findViewById(R.id.illness_profile_nameData);
        TextView medicineTreatment = (TextView) findViewById(R.id.illness_profile_treatmentData);
        TextView illnessNotes = (TextView) findViewById(R.id.illness_profile_notesData);

        illnessName.setText(illness.getName());
        for (int i = 0; i < illness.getTreatmentProtocol().getMedicines().size(); i++){
           medicines = medicines + "/n" + illness.getTreatmentProtocol().getMedicines().get(i).getName();
        }
        medicineTreatment.setText(medicines);
        illnessNotes.setText(illness.getTreatmentProtocol().getNotes());


    }
}
