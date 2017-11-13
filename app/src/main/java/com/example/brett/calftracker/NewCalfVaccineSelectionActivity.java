package com.example.brett.calftracker;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class NewCalfVaccineSelectionActivity extends AppCompatActivity {

    ArrayList<VaccineSelectionItem> adapterArray;
    VaccineSelectionListViewAdapter adapter;

    private Calf calf;
    private String calfID;
    private ArrayList<Calf> calfList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_calf_vaccine_selection);

        ListView listView = (ListView) findViewById(R.id.listViewVaccineSelection);

        ArrayList<Vaccine> vaccineList;

        // get calf list and calf to add needed vaccines to
        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();

        Gson gson = new Gson();
        String json = mPreferences.getString("CalfList","");
        calfList = gson.fromJson(json, new TypeToken<ArrayList<Calf>>(){}.getType());

        json = mPreferences.getString("calfToViewInProfile","");
        calfID = gson.fromJson(json, String.class);

        // Search through the calfList to find the correct calf by ID
        for (int i = 0; i < calfList.size(); i++) {
            if (calfList.get(i).getFarmId().equals(calfID)) {
                calf = calfList.get(i);
                break;
            }
        }

        // get vaccine list from shared preferences
        if(mPreferences.contains("VaccineList")) {
            json = mPreferences.getString("VaccineList", "");
            vaccineList = gson.fromJson(json, new TypeToken<ArrayList<Vaccine>>() {
            }.getType());

            adapterArray = new ArrayList<VaccineSelectionItem>();
            for(int i = 0; i < vaccineList.size(); i++) {
                adapterArray.add(new VaccineSelectionItem(vaccineList.get(i),false));
            }
        } else { adapterArray = new ArrayList<VaccineSelectionItem>(); }

//        VaccineSelectionItem item = new VaccineSelectionItem(new Vaccine("derp",new ArrayList<Vacc_Range>(),12,"herp","merp"), false);
//        VaccineSelectionItem item2 = new VaccineSelectionItem(new Vaccine("derp1",new ArrayList<Vacc_Range>(),12,"herp","merp"), false);
//        VaccineSelectionItem item3 = new VaccineSelectionItem(new Vaccine("derp2",new ArrayList<Vacc_Range>(),12,"herp","merp"), false);
//        VaccineSelectionItem item4 = new VaccineSelectionItem(new Vaccine("derp3",new ArrayList<Vacc_Range>(),12,"herp","merp"), false);
//        VaccineSelectionItem item5 = new VaccineSelectionItem(new Vaccine("derp4",new ArrayList<Vacc_Range>(),12,"herp","merp"), false);
//        VaccineSelectionItem item6 = new VaccineSelectionItem(new Vaccine("derp5",new ArrayList<Vacc_Range>(),12,"herp","merp"), false);
//        adapterArray = new ArrayList<VaccineSelectionItem>();
//
//        adapterArray.add(item);
//        adapterArray.add(item2);
//        adapterArray.add(item3);
//        adapterArray.add(item4);
//        adapterArray.add(item5);
//        adapterArray.add(item6);

        adapter = new VaccineSelectionListViewAdapter(adapterArray, getApplicationContext());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {

                VaccineSelectionItem dataModel= adapterArray.get(position);
                dataModel.setChecked(!dataModel.getChecked());
                adapter.notifyDataSetChanged();


            }
        });
    }

    public void onConfirmVaccines(View view) {

        for(int i = 0; i < adapterArray.size(); i++) {
            if(adapterArray.get(i).getChecked()) {
                calf.getNeededVaccines().add(adapterArray.get(i).getVaccine());
            }
        }


        // Save the calfList to local storage
        SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(calfList);
        prefsEditor.putString("CalfList",json);
        prefsEditor.apply();

        prefsEditor = mPrefs.edit();
        json = gson.toJson(calf.getFarmId());
        prefsEditor.putString("calfToViewInProfile",json);
        prefsEditor.apply();

        // go to calf profile for new calf
        Intent intent = new Intent(this,CalfProfileActivity.class);
        startActivity(intent);
    }

    public void onClickSelectAll(View view) {
        for(int i = 0; i < adapterArray.size(); i++) {
            adapterArray.get(i).setChecked(true);
        }
        adapter.notifyDataSetChanged();
    }
}
