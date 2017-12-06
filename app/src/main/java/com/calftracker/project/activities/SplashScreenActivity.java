package com.calftracker.project.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.content.SharedPreferences;

import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Calf;
import com.calftracker.project.models.Firebase;
import com.calftracker.project.models.IllnessTask;
import com.calftracker.project.models.Task;
import com.calftracker.project.models.VaccineTask;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by project on 10/31/17.
 */
public class SplashScreenActivity extends Activity {
    boolean hasBeenUsed;
    Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // enable offline usability
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        DatabaseReference employeeList = FirebaseDatabase.getInstance().getReference("EmployeeList");
        employeeList.keepSynced(true);

        //final SharedPreferences sharedPref = getSharedPreferences("test",Activity.MODE_PRIVATE);
        //final SharedPreferences.Editor editor = sharedPref.edit();
        //editor.putBoolean("usedApp", false);
        //editor.commit();

        Firebase fb = (Firebase) getApplicationContext();
        fb.setDataChangeListeners();

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(500);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    retrieveData();

                    ArrayList<Calf> emptyCalfList = new ArrayList<>();
                    ArrayList<ArrayList<VaccineTask>> emptyTaskList = new ArrayList<ArrayList<VaccineTask>>();
                    ArrayList<VaccineTask> emptyOverdueList = new ArrayList<>();
                    ArrayList<ArrayList<IllnessTask>> emptyIllnessTaskList = new ArrayList<ArrayList<IllnessTask>>();

                    // Create emptyTaskList to hold a full year of entries
                    for (int i = 0; i < 365; i++) {
                        emptyTaskList.add(new ArrayList<VaccineTask>());
                    }

                    for (int i = 0; i < 40; i++) {
                        emptyIllnessTaskList.add(new ArrayList<IllnessTask>());
                    }

                    task = new Task(Calendar.getInstance(), emptyCalfList, emptyTaskList, emptyOverdueList, emptyIllnessTaskList);

                    if(!hasBeenUsed) {
                        Intent intent = new Intent(SplashScreenActivity.this, CreateFarmActivity.class);

                        saveData();

                        startActivity(intent);
                    }else{
                        //Intent intent = new Intent(SplashScreenActivity.this, DashboardActivity.class);
                        //startActivity(intent);
                        Intent intent = new Intent(SplashScreenActivity.this, DashboardActivity.class);
                        startActivity(intent);
                    }
                }
            }
        };
        timerThread.start();
    }

    // TODO
    public void saveData() {
        SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json;
        prefsEditor = mPrefs.edit();

        prefsEditor.putBoolean("usedApp", true);
        prefsEditor.commit();

        // Save the initial Task Object to system
        json = gson.toJson(task);
        prefsEditor.putString("Task",json);
        prefsEditor.apply();

        Firebase fb = (Firebase) getApplicationContext();
        fb.saveData("Task", task);

    }

    public void retrieveData() {
        SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        hasBeenUsed = mPrefs.getBoolean("usedApp", false);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }

}