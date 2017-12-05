package com.calftracker.project.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.calftracker.project.adapters.protocols.MedicineSelectionListViewAdapter;
import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Firebase;
import com.calftracker.project.models.Illness;
import com.calftracker.project.models.Medicine;
import com.calftracker.project.models.MedicineSelectionItem;
import com.calftracker.project.models.Treatment_Protocol;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class EditIllnessProfileMedicineSelectionActivity extends AppCompatActivity {

    private ArrayList<Illness> illnessList;
    private ArrayList<Medicine> medicineList;
    ArrayList<MedicineSelectionItem> adapterArray;
    MedicineSelectionListViewAdapter adapter;
    private String illnessNotesStr;
    private String illnessNameStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_illness_profile_medicine_selection);


        ListView lv = (ListView) findViewById(R.id.medicineSelectionEditIllness_listView);
        Button confirmButton = (Button) findViewById(R.id.medicineSelectionEditIllness_confirmButton);

        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        if(mPreferences.contains("IllnessList")) {
            Gson gson = new Gson();
            String json;
            json = mPreferences.getString("IllnessList", "");
            illnessList = gson.fromJson(json, new TypeToken<ArrayList<Illness>>() {}.getType());
        } else { illnessList = new ArrayList<Illness>(); }

        // get medicine list from shared preferences
        if(mPreferences.contains("MedicineList")) {
            Gson gson1 = new Gson();
            String json1 = mPreferences.getString("MedicineList", "");
            medicineList = gson1.fromJson(json1, new TypeToken<ArrayList<Medicine>>() {
            }.getType());

            adapterArray = new ArrayList<MedicineSelectionItem>();
            for (int i = 0; i < medicineList.size(); i++){
                adapterArray.add(new MedicineSelectionItem(medicineList.get(i), false));
            }
            adapter = new MedicineSelectionListViewAdapter(adapterArray, getApplicationContext());

            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    MedicineSelectionItem dataModel = adapterArray.get(position);
                    dataModel.setChecked(!dataModel.getChecked());
                    adapter.notifyDataSetChanged();
                }
            });
        }
        else {
            medicineList = new ArrayList<Medicine>();
            lv.setVisibility(View.GONE);
            confirmButton.setText("Continue");
        }
    }

    public void medicineSelectionConfirmButtonClick(View view) {


        ArrayList<Medicine> tempMedicineList = new ArrayList<Medicine>();

        if(adapterArray != null) {
            for (int i = 0; i < adapterArray.size(); i++) {
                if (adapterArray.get(i).getChecked()) {
                    tempMedicineList.add(adapterArray.get(i).getMedicine());
                }
            }
        }

        // get illness information from local storage
        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPreferences.edit();
        Gson gson = new Gson();
        String json;

        json = mPreferences.getString("illnessName","");
        illnessNameStr = gson.fromJson(json, String.class);

        json = mPreferences.getString("illnessNotes","");
        illnessNotesStr = gson.fromJson(json, String.class);

        // create a new illness object
        Illness illness = new Illness(illnessNameStr, new Treatment_Protocol(tempMedicineList,illnessNotesStr));

        // add the illness to the list of local illnesses, save to local storage
        illnessList.add(illness);
        json = gson.toJson(illnessList);
        prefsEditor.putString("IllnessList",json);
        prefsEditor.apply();

        //TODO
        Firebase fb = (Firebase) getApplicationContext();
        fb.saveData("IllnessList", illnessList);


        // go to Add Illness activity
        Intent intent = new Intent(this,IllnessActivity.class);
        startActivity(intent);
    }

    public void onClickBackEditIllnessProfileMedicines(View view) {
        // go back to Add Illness screen
        Intent intent = new Intent(this,EditIllnessProfileActivity.class);
        startActivity(intent);
    }

    public void onClickEditIllnessProfileSelectAllMedicines(View view) {
        for(int i = 0; i < adapterArray.size(); i++) {
            adapterArray.get(i).setChecked(true);
        }
        adapter.notifyDataSetChanged();
    }
}
