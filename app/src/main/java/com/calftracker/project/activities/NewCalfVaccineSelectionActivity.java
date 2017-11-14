package com.calftracker.project.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.calftracker.project.adapters.VaccineSelectionListViewAdapter;
import com.calftracker.project.models.Calf;
import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Vaccine;
import com.calftracker.project.models.VaccineSelectionItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;

public class NewCalfVaccineSelectionActivity extends AppCompatActivity {

    ArrayList<VaccineSelectionItem> adapterArray;
    VaccineSelectionListViewAdapter adapter;

    private Calf calf;
    private ArrayList<Calf> calfList;
    private String calfID;
    private Calendar calfCal;
    private String calfGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_calf_vaccine_selection);

        ListView listView = (ListView) findViewById(R.id.listViewVaccineSelection);
        TextView mNoVaccines = (TextView) findViewById(R.id.textViewNoVaccines);
        Button mNextButton = (Button) findViewById(R.id.buttonConfirmVaccines);

        ArrayList<Vaccine> vaccineList;

        // set up shared preference variables
        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        Gson gson = new Gson();
        String json;

        // check if there's an existing calf list and store it for use
        // create a new list if existing not found
        if(mPreferences.contains("CalfList")) {
            json = mPreferences.getString("CalfList", "");
            calfList = gson.fromJson(json, new TypeToken<ArrayList<Calf>>() {
            }.getType());
        } else { calfList = new ArrayList<Calf>(); }

        json = mPreferences.getString("newCalfID","");
        calfID = gson.fromJson(json, String.class);

        json = mPreferences.getString("newCalfCal","");
        calfCal = gson.fromJson(json, new TypeToken<Calendar>() {
        }.getType());

        json = mPreferences.getString("newCalfGender","");
        calfGender = gson.fromJson(json, String.class);

        // get vaccine list from shared preferences
        if(mPreferences.contains("VaccineList")) {
            json = mPreferences.getString("VaccineList", "");
            vaccineList = gson.fromJson(json, new TypeToken<ArrayList<Vaccine>>() {
            }.getType());

            adapterArray = new ArrayList<VaccineSelectionItem>();
            for(int i = 0; i < vaccineList.size(); i++) {
                adapterArray.add(new VaccineSelectionItem(vaccineList.get(i),false));
            }
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
        } else {
            listView.setVisibility(View.GONE);
            mNoVaccines.setVisibility(View.VISIBLE);
            mNextButton.setText("Continue");
        }


    }

    public void onConfirmVaccines(View view) {

        calf = new Calf(calfID,calfGender,calfCal);

        if(adapterArray != null) {
            for (int i = 0; i < adapterArray.size(); i++) {
                if (adapterArray.get(i).getChecked()) {
                    calf.getNeededVaccines().add(adapterArray.get(i).getVaccine());
                }
            }
        }

        calfList.add(calf);

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

    public void onClickBack(View view) {
        // go back to add new calf screen
        Intent intent = new Intent(this,AddCalfActivity.class);
        startActivity(intent);
    }

    public void onClickSelectAll(View view) {
        for(int i = 0; i < adapterArray.size(); i++) {
            adapterArray.get(i).setChecked(true);
        }
        adapter.notifyDataSetChanged();
    }
}
