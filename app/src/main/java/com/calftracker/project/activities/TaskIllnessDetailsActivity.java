package com.calftracker.project.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.IllnessTask;
import com.calftracker.project.models.Medicine;
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
            dateLastAdministered = dateLastAdministered - illnessTask.getMedicine().getTimeActive();
            today.add(Calendar.DATE, dateLastAdministered);
            int year = today.get(Calendar.YEAR);
            int month = today.get(Calendar.MONTH) + 1;
            int day = today.get(Calendar.DAY_OF_MONTH);

            mDateLastAdministered.setText(month + "/" + day + "/" + year);
        } else {
            mDateLastAdministered.setText("No medication active");
        }
    }

    public void saveData() {
        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        Gson gson = new Gson();
        String json;
        SharedPreferences.Editor prefsEditor = mPreferences.edit();

        json = gson.toJson(task);
        prefsEditor.putString("Task",json);
        prefsEditor.apply();
    }

    public void retrieveData() {
        // Load in the Task object
        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPreferences.getString("Task", "");
        task = gson.fromJson(json, new TypeToken<Task>() {}.getType());
    }

    public void onClickAdministerMedication(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        String medication = illnessTask.getMedicine().getName();
        builder.setMessage("Are you sure you want to administer " + medication + "?");

        builder.setPositiveButton("Administer", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                task.administerMedication(illnessTask);

                saveData();

                Calendar today = Calendar.getInstance();
                dateLastAdministered = dateLastAdministered - illnessTask.getMedicine().getTimeActive();
                today.add(Calendar.DATE, dateLastAdministered);
                int year = today.get(Calendar.YEAR);
                int month = today.get(Calendar.MONTH) + 1;
                int day = today.get(Calendar.DAY_OF_MONTH);

                mDateLastAdministered.setText(month + "/" + day + "/" + year);

                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public void onClickChangeMedication(View view) {
        // display a dialog that prompted the user to select new medication
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Medication");

        String medicationnames[] = new String[illnessTask.getIllness().getTreatmentProtocol().getMedicines().size()];
        for(int i = 0; i < illnessTask.getIllness().getTreatmentProtocol().getMedicines().size(); i++) {
            medicationnames[i] = illnessTask.getIllness().getTreatmentProtocol().getMedicines().get(i).getName();
        }

        builder.setItems(medicationnames, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Medicine medicine = illnessTask.getIllness().getTreatmentProtocol().getMedicines().get(i);
                mMedication.setText(medicine.getName());
                task.editMedication(medicine, illnessTask);
                saveData();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    public void onClickMedicationHistory(View view) {
        // i have no idea what I want to do here but it seems like it could be nice -JT
    }

    public void onClickDiscontinueTreatment(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Are you sure you want to discontinue treatment?");

        final Context mcontext = getApplicationContext();

        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                task.removeIllnessTask(illnessTask);

                saveData();

                Intent intent = new Intent(mcontext, TasksActivity.class);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, TasksActivity.class);
        startActivity(intent);
    }
}
