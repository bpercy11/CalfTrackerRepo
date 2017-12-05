package com.calftracker.project.activities;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.IllnessTask;
import com.calftracker.project.models.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Calendar;

/**
 * Created by JT on 12/5/2017.
 */

public class TaskIllnessDetailsActivity extends BaseActivity {

    IllnessTask illnessTask;
    Task task;

    TextView mCalfID;
    TextView mIllness;
    TextView mMedication;
    TextView mDateLastAdministered;

    int dateLastAdministered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_task_illness_details, frameLayout);
        mNavigationView.getMenu().findItem(R.id.nav_tasks).setChecked(true);

        // Custom title
        getSupportActionBar().setTitle(R.string.tasks_title);

        mCalfID = (TextView) findViewById(R.id.textViewTaskIllnessDetailsIDValue);
        mIllness = (TextView) findViewById(R.id.textViewTaskIllnessDetailsIllnessValue);
        mMedication = (TextView) findViewById(R.id.textViewTaskIllnessDetailsMedicationValue);
        mDateLastAdministered = (TextView) findViewById(R.id.textViewTaskIllnessDetailsDateValue);


        retrieveData();

        // Load in the IllnessTask object to view
        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPreferences.getString("TaskIllnessDetails", "");
        illnessTask = gson.fromJson(json, new TypeToken<IllnessTask>() {}.getType());

        OUTER:for(int i = 0; i < task.getIllnessTracker().size(); i++)
            for(int j = 0; j < task.getIllnessTracker().get(i).size(); j++)
                if(task.getIllnessTracker().get(i).get(j).equals(illnessTask)) {
                    illnessTask = task.getIllnessTracker().get(i).get(j);
                    dateLastAdministered = i;
                    break OUTER;
                }

        mCalfID.setText(illnessTask.getCalf().getFarmId());
        mIllness.setText(illnessTask.getIllness().getName());
        mMedication.setText(illnessTask.getMedicine().getName());

        if(dateLastAdministered != 0) {
            Calendar today = Calendar.getInstance();
            today.add(Calendar.DATE, -dateLastAdministered);
            int year = today.get(Calendar.YEAR);
            int month = today.get(Calendar.MONTH) + 1;
            int day = today.get(Calendar.DAY_OF_MONTH);

            mDateLastAdministered.setText(month + "/" + day + "/" + year);
        } else {
            mDateLastAdministered.setText("Has not been administered");
        }
    }

    public void saveData() {

    }

    public void retrieveData() {
        // Load in the Task object
        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPreferences.getString("Task", "");
        task = gson.fromJson(json, new TypeToken<Task>() {}.getType());
    }


}
