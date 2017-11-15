package com.example.brett.calftracker;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class EditIllnessActivity extends AppCompatActivity {

    private AlertDialog alertDialog;
    private EditText illnessName;
    private ListView lvTreatmentProtocolMedicines;
    private List<Illness> illnessList;
    private List<Medicine> medicineList;
    private MedicineAdapter medicineAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_illness);

        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        if(mPreferences.contains("IllnessList")) {
            SharedPreferences.Editor editor = mPreferences.edit();

            Gson gson = new Gson();
            String json = mPreferences.getString("IllnessList", "");
            illnessList = gson.fromJson(json, new TypeToken<ArrayList<Illness>>() {
            }.getType());
        } else { illnessList = new ArrayList<Illness>(); }
    }

    public void clickAddIllnessButton(View view){
        illnessName = (EditText) findViewById(R.id.editTextIllnessEnterName);

        if (illnessName.getText().toString().matches("")){
            Toast.makeText(EditIllnessActivity.this, R.string.EditIllnessActivity_emptyFieldMsg, Toast.LENGTH_SHORT).show();
        }
        lvTreatmentProtocolMedicines = (ListView) findViewById(R.id.listViewEditIllness);
        medicineAdapter = new MedicineAdapter(getApplicationContext(), medicineList);
        lvTreatmentProtocolMedicines.setAdapter(medicineAdapter);

        lvTreatmentProtocolMedicines.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Do something
            }
        });

        // Make a new illness object
        String name = illnessName.getText().toString();
        Illness newIllness = new Illness(name, new Treatment_Protocol(medicineList,null));

        illnessList.add(newIllness);

        // SAVE NEW Medicine
        SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(illnessList);
        prefsEditor.putString("IllnessList",json);
        prefsEditor.apply();

        Intent intent = new Intent(this,IllnessActivity.class);
        startActivity(intent);

    }

    public void clickCancelIllnessButton(View view){
        Intent intent = new Intent(EditIllnessActivity.this, IllnessActivity.class);
        startActivity(intent);
    }
}
