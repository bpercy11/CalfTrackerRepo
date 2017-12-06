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
import android.widget.TextView;

import com.calftracker.project.adapters.addcalf.VaccineSelectionListViewAdapter;
import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Calf;
import com.calftracker.project.models.Feeding;
import com.calftracker.project.models.Firebase;
import com.calftracker.project.models.Sire;
import com.calftracker.project.models.Task;
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
    private Task task;
    private ArrayList<Vaccine> vaccineList;
    private String calfPhoto;
    private boolean containsVaccineList;
    private ListView listView;
    private TextView mNoVaccines;
    private Button mNextButton;
    private Button mSelectAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_calf_vaccine_selection);

        // get needed UI elements
        listView = (ListView) findViewById(R.id.listViewVaccineSelection);
        mNoVaccines = (TextView) findViewById(R.id.textViewNoVaccines);
        mNextButton = (Button) findViewById(R.id.buttonConfirmVaccines);
        mSelectAll = (Button) findViewById(R.id.buttonSelectAll);

        retrieveData();



        // get stuff from shared preferences that isn't supposed to go in firebase
        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        Gson gson = new Gson();
        String json;

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

        // get calf photo, this field comes from the user input in add calf activity
        // photos are optional so check if null
        json = mPreferences.getString("newCalfPhoto","");
        if (json != null) {
            calfPhoto = gson.fromJson(json, String.class);
        }

        // setup listview of available vaccines
        if(containsVaccineList) {

            if (vaccineList != null) {
                adapterArray = new ArrayList<VaccineSelectionItem>();
                for (int i = 0; i < vaccineList.size(); i++) {
                    adapterArray.add(new VaccineSelectionItem(vaccineList.get(i), false));
                }
                adapter = new VaccineSelectionListViewAdapter(adapterArray, getApplicationContext());

                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView parent, View view, int position, long id) {

                        VaccineSelectionItem dataModel = adapterArray.get(position);
                        dataModel.setChecked(!dataModel.getChecked());
                        adapter.notifyDataSetChanged();
                    }
                });
            }

        } else {
            // if there's no vaccines defined the user needs to know that
            // change button to say continue instead of add needed vaccines
            mSelectAll.setVisibility(View.GONE);
            listView.setVisibility(View.GONE);
            mNoVaccines.setVisibility(View.VISIBLE);
            mNextButton.setText("Continue");
        }




    }

    public void onConfirmVaccines(View view) {
        // make a new calf object from user input from add calf activity
        calf = new Calf(calfPhoto,calfID,calfGender,calfCal);

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

                    task.placeVaccineInTasks(adapterArray.get(i).getVaccine(), calf);

//                    // Set up variables to be used in calculating dates and differences
//                    int calfYear = calfCal.get(Calendar.YEAR);
//                    int calfMonth = calfCal.get(Calendar.MONTH);
//                    int calfDay = calfCal.get(Calendar.DATE);
//                    int todayYear = Calendar.getInstance().get(Calendar.YEAR);
//                    int todayMonth = Calendar.getInstance().get(Calendar.MONTH);
//                    int todayDay = Calendar.getInstance().get(Calendar.DATE);
//                    Calendar today = Calendar.getInstance();
//                    Calendar startDate = new GregorianCalendar(calfYear, calfMonth, calfDay);
//                    Calendar endDate = new GregorianCalendar(calfYear, calfMonth, calfDay);
//                    startDate.add(Calendar.DAY_OF_YEAR, adapterArray.get(i).getVaccine().getToBeAdministered().get(0).getSpan()[0]);
//                    endDate.add(Calendar.DAY_OF_YEAR, adapterArray.get(i).getVaccine().getToBeAdministered().get(0).getSpan()[1]);
//
//                    int startYear = startDate.get(Calendar.YEAR);
//                    int startMonth = startDate.get(Calendar.MONTH);
//                    int startDay = startDate.get(Calendar.DATE);
//                    int endYear = endDate.get(Calendar.YEAR);
//                    int endMonth = endDate.get(Calendar.MONTH);
//                    int endDay = endDate.get(Calendar.DATE);
//
//                    // Calculate the number of days between today and the calf's DOB
//                    int diff = 0;
//                    if (todayMonth == calfMonth) {
//                        diff = Math.abs(todayDay - calfDay);
//                    } else if (todayMonth > calfMonth) {
//                        int monthDiff = todayMonth - calfMonth;
//                        int daysInMonth = calfCal.getActualMaximum(Calendar.DAY_OF_MONTH);
//                        diff += daysInMonth - calfDay;
//                        diff += todayDay;
//                        if (monthDiff > 1) {
//                            for (int j = 1; j < monthDiff; j++) {
//                                today.add(Calendar.MONTH, -1);
//                                diff += today.getActualMaximum(Calendar.DAY_OF_MONTH);
//                            }
//                        }
//                    }
//
//                    // Testing stuff for dates and adding days
//                    //Log.i("", "today : " + startMonth + "/" + startDay + "/" + startYear);
//                    Log.i("vaccine", "vaccine : " + vaccineList.get(i).getName());
//                    Log.i("startint", "start int : " + adapterArray.get(i).getVaccine().getToBeAdministered().get(0).getSpan()[0]);
//                    Log.i("start", "startDate : " + startMonth + "/" + startDay + "/" + startYear);
//                    Log.i("endint", "end int : " + adapterArray.get(i).getVaccine().getToBeAdministered().get(0).getSpan()[1]);
//                    Log.i("end", "endDate : " + endMonth + "/" + endDay + "/" + endYear);
//
//
//                    VaccineTask vaccTaskStart = new VaccineTask(vaccineList.get(i), calf, true);
//                    VaccineTask vaccTaskEnd = new VaccineTask(vaccineList.get(i), calf, false);
//
//                    int startIndex = vaccineList.get(i).getToBeAdministered().get(0).getSpan()[0] - diff;
//                    int endIndex = vaccineList.get(i).getToBeAdministered().get(0).getSpan()[1] - diff;
//
//                    task.getVaccinesToAdminister().get(startIndex).add(vaccTaskStart);
//                    task.getVaccinesToAdminister().get(endIndex).add(vaccTaskEnd);

                }
            }
        }
        calf.setFeedingHistory(new Feeding[2]);
        calf.setSire(new Sire(null,null));
        // gotta add it to the list
        calfList.add(calf);

        // Save the calfList, task, and vaccineList to local storage, change to firebase
        saveData();


//


        // this is a lazy way to pass the newly created calf to the calf profile but whatever.
        // in calf profile we use the string in this shared preference object to iterate through
        // the master calf list until this string matches a calf object to display.
        // this could cause problems if there are mutliple calves with the same ID
        SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json;
        prefsEditor = mPrefs.edit();
        json = gson.toJson(calf.getFarmId());
        prefsEditor.putString("calfToViewInProfile",json);
        prefsEditor.apply();

        // go to calf profile for new calf
        Intent intent = new Intent(this,CalfProfileActivity.class);
        startActivity(intent);
    }


    // TODO
    public void saveData() {
        SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(calfList);
        prefsEditor.putString("CalfList",json);
        prefsEditor.apply();

        Firebase fb = (Firebase) getApplicationContext();
        fb.saveData("CalfList", calfList);

        json = gson.toJson(task);
        prefsEditor.putString("Task",json);
        prefsEditor.apply();

        fb.saveData("Task", task);

        if(containsVaccineList) {
            json = gson.toJson(vaccineList);
            prefsEditor.putString("VaccineList", json);
            prefsEditor.apply();

            fb.saveData("VaccineList", vaccineList);
        }


    }

    public void retrieveData() {
        // set up shared preference variables
        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
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

        // get vaccine list from shared preferences
        if(mPreferences.contains("VaccineList")) {
            containsVaccineList = true;

            json = mPreferences.getString("VaccineList", "");
            vaccineList = gson.fromJson(json, new TypeToken<ArrayList<Vaccine>>() {
            }.getType());

        } else {
            containsVaccineList = false;
        }
    }

    public void onClickBack(View view) {
        SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(new Calf(null, calfID, calfGender, calfCal));
        prefsEditor.putString("BackToAddScreen",json);
        prefsEditor.apply();


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
