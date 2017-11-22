package com.calftracker.project.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.calftracker.project.adapters.MedicineSelectionListViewAdapter;
import com.calftracker.project.calftracker.R;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.calftracker.project.models.Illness;
import com.calftracker.project.models.Medicine;
import com.calftracker.project.models.MedicineSelectionItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;


public class AddIllnessMedicineSelectionActivity extends AppCompatActivity {

    ArrayList<MedicineSelectionItem> adapterArray;
    MedicineSelectionListViewAdapter adapter;
    private ArrayList<Illness> illnessList;
    private ArrayList<Medicine> medicineList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_illness_medicine_selection);

        ListView lv = (ListView) findViewById(R.id.listViewMedicineSelection);
        Button confirmButton = (Button) findViewById(R.id.buttonConfirmMedicines);

        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        if(mPreferences.contains("IllnessList")) {
            SharedPreferences.Editor editor = mPreferences.edit();

            Gson gson = new Gson();
            String json;
            json = mPreferences.getString("IllnessList", "");
            illnessList = gson.fromJson(json, new TypeToken<ArrayList<Illness>>() {
            }.getType());
        } else { illnessList = new ArrayList<Illness>(); }

        // get medicine list from shared preferences
        SharedPreferences mPreferences1 = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        if(mPreferences1.contains("MedicineList")) {
            SharedPreferences.Editor editor = mPreferences1.edit();

            Gson gson1 = new Gson();
            String json1 = mPreferences1.getString("MedicineList", "");
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

    public void onConfirmMedicines(View view) {

        ArrayList<Medicine> tempMedicineList = new ArrayList<Medicine>();

        if(adapterArray != null) {
            for (int i = 0; i < adapterArray.size(); i++) {
                if (adapterArray.get(i).getChecked()) {
                    tempMedicineList.add(adapterArray.get(i).getMedicine());
                }
            }
        }

        // Save the medicine list to local storage
        SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(tempMedicineList);
        prefsEditor.putString("Add Illness Medicine List",json);
        prefsEditor.apply();


        // go to Add Illness activity
        Intent intent = new Intent(this,IllnessActivity.class);
        startActivity(intent);
    }

    public void onClickBackMedicines(View view) {
        // go back to Add Illness screen
        Intent intent = new Intent(this,EditIllnessActivity.class);
        startActivity(intent);
    }

    public void onClickSelectAllMedicines(View view) {
        for(int i = 0; i < adapterArray.size(); i++) {
            adapterArray.get(i).setChecked(true);
        }
        adapter.notifyDataSetChanged();
    }

}
