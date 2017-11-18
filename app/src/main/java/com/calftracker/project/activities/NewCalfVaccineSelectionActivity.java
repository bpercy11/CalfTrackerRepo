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
import com.calftracker.project.models.Task;
import com.calftracker.project.models.Feeding;
import com.calftracker.project.models.Vaccine;
import com.calftracker.project.models.VaccineSelectionItem;
import com.calftracker.project.models.VaccineTask;
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
    private Task task;
    private Calendar startDate;
    private Calendar endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_calf_vaccine_selection);

        // get needed UI elements
        ListView listView = (ListView) findViewById(R.id.listViewVaccineSelection);
        TextView mNoVaccines = (TextView) findViewById(R.id.textViewNoVaccines);
        Button mNextButton = (Button) findViewById(R.id.buttonConfirmVaccines);

        ArrayList<Vaccine> vaccineList;

        // set up shared preference variables
        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        Gson gson = new Gson();
        String json;

        // Load the Task object from storage
        json = mPreferences.getString("Task", "");
        task = gson.fromJson(json, new TypeToken<Task>() {}.getType());

        // check if there's an existing calf list and store it for use
        // create a new list if existing not found
        if(mPreferences.contains("CalfList")) {
            json = mPreferences.getString("CalfList", "");
            calfList = gson.fromJson(json, new TypeToken<ArrayList<Calf>>() {
            }.getType());
        } else { calfList = new ArrayList<Calf>(); }

        // get calf ID, this field comes from the user input in add calf activity
        json = mPreferences.getString("newCalfID","");
        calfID = gson.fromJson(json, String.class);

        // get calf calendar, this field comes from the user input in add calf activity
        json = mPreferences.getString("newCalfCal","");
        calfCal = gson.fromJson(json, new TypeToken<Calendar>() {
        }.getType());

        // get calf gender, this field comes from the user input in add calf activity
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
            // if there's no vaccines defined the user needs to know that
            // change button to say continue instead of add needed vaccines
            listView.setVisibility(View.GONE);
            mNoVaccines.setVisibility(View.VISIBLE);
            mNextButton.setText("Continue");
        }


    }

    public void onConfirmVaccines(View view) {
        // make a new calf object from user input from add calf activity
        calf = new Calf(calfID,calfGender,calfCal);

        // this is kind of dumb but I'm using the same onclick method for
        // when there are no vaccines defined or when there are vaccines
        // defined. Since its the same onclick for both we need to check
        // if the adapter array has anything in it or else it'll try to access a null list
        if(adapterArray != null) {
            // run through the adapterarray and find the vaccines that have been check marked
            // by the user and add those vaccines to the calf object
            for (int i = 0; i < adapterArray.size(); i++) {
                if (adapterArray.get(i).getChecked()) {
                    calf.getNeededVaccines().add(adapterArray.get(i).getVaccine());

                    startDate = Calendar.getInstance();
                    startDate.add(Calendar.DAY_OF_YEAR, adapterArray.get(i).getVaccine().getToBeAdministered().get(0).getSpan()[0]);
                    endDate = Calendar.getInstance();
                    endDate.add(Calendar.DAY_OF_YEAR, adapterArray.get(i).getVaccine().getToBeAdministered().get(0).getSpan()[1]);
                    VaccineTask vaccTaskStart = new VaccineTask(adapterArray.get(i).getVaccine(), calf, true);
                    VaccineTask vaccTaskEnd = new VaccineTask(adapterArray.get(i).getVaccine(), calf, false);

                    task.getVaccinesToAdminister().get(adapterArray.get(i).getVaccine().getToBeAdministered().get(0).getSpan()[0]).add(vaccTaskStart);
                    task.getVaccinesToAdminister().get(adapterArray.get(i).getVaccine().getToBeAdministered().get(0).getSpan()[1]).add(vaccTaskEnd);
                }
            }
            task.getCalvesToObserve().add(calf);

        }

        calf.setFeedingHistory(new Feeding[2]);

        // gotta add it to the list
        calfList.add(calf);

        // Save the calfList to local storage
        SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(calfList);
        prefsEditor.putString("CalfList",json);
        prefsEditor.apply();

        json = gson.toJson(task);
        prefsEditor.putString("Task",json);
        prefsEditor.apply();

        // this is a lazy way to pass the newly created calf to the calf profile but whatever
        // in calf profile we use the string in this shared preference object to iterate through
        // the master calf list until this string matches a calf object to display.
        // this could cause problems if there are mutliple calves with the same ID
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

    // if the select all button is pressed iterate through the adapter array
    // to make all objects in array checked and notify adapter
    public void onClickSelectAll(View view) {
        for(int i = 0; i < adapterArray.size(); i++) {
            adapterArray.get(i).setChecked(true);
        }
        adapter.notifyDataSetChanged();
    }
}
