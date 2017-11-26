package com.calftracker.project.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.calftracker.project.adapters.tasks.TasksObservationAdapter;
import com.calftracker.project.adapters.tasks.TasksVaccinationAdapter;
import com.calftracker.project.calftracker.R;
import com.calftracker.project.interfaces.TasksMethods;
import com.calftracker.project.models.Calf;
import com.calftracker.project.models.Illness;
import com.calftracker.project.models.Medicine;
import com.calftracker.project.models.Task;
import com.calftracker.project.models.VaccineTaskItem;
import com.calftracker.project.models.Vaccine_With_Count;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TasksActivity extends BaseActivity implements TasksMethods {
    private Task task;
    private List<Vaccine_With_Count> vaccCountList;
    private ArrayList<Calf> calfList;
    private ListView listView;
    private TasksVaccinationAdapter vaccineAdapter;
    private TextView date;

    private ArrayList<Calf> observeCalves;
    private ArrayList<Illness> illnessList;

    private TasksObservationAdapter observationAdapter;

    TextView mLeft;
    TextView mRight;

    TextView mCalfIDObserved;
    TextView mDaysOberserved;

    TextView mCalfIDIllness;
    TextView mCenter;
    TextView mCurrentMed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_tasks, frameLayout);
        mNavigationView.getMenu().findItem(R.id.nav_tasks).setChecked(true);

        // Custom title
        getSupportActionBar().setTitle(R.string.tasks_title);

        mLeft = (TextView) findViewById(R.id.textViewVaccineNameTitle);
        mRight = (TextView) findViewById(R.id.textViewElligibleTitle);
        mCenter = (TextView) findViewById(R.id.textViewIllnessNameTasks);


        // Load in the Task and CalfList
        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPreferences.getString("Task", "");
        task = gson.fromJson(json, new TypeToken<Task>() {}.getType());
        json = mPreferences.getString("CalfList", "");
        calfList = gson.fromJson(json, new TypeToken<ArrayList<Calf>>() {}.getType());

        json = mPreferences.getString("IllnessList", "");
        illnessList = gson.fromJson(json, new TypeToken<ArrayList<Illness>>() {}.getType());

        task.updateTasks();

        SharedPreferences.Editor prefsEditor = mPreferences.edit();
        json = gson.toJson(task);
        prefsEditor.putString("Task",json);
        prefsEditor.apply();

        // ArrayList that holds all of the Vaccine Tasks for the current day
        ArrayList<VaccineTaskItem> todayTasks = new ArrayList<VaccineTaskItem>();

        // find only the START DATE vaccinetask objects so no doubles from END DATEs
        for(int i = 0; i < task.getVaccinesToAdminister().get(0).size(); i++)
            if(task.getVaccinesToAdminister().get(0).get(i).isStart())
                todayTasks.add(new VaccineTaskItem(false, task.getVaccinesToAdminister().get(0).get(i)));

        for(int i = 0; i < task.getOverdueVaccinations().size(); i++)
            todayTasks.add(new VaccineTaskItem(true, task.getOverdueVaccinations().get(i)));

        // Create the ListView and create a header message for the activity
        listView = (ListView) findViewById(R.id.listViewTasks);
        Calendar today = Calendar.getInstance();
        int year = today.get(Calendar.YEAR);
        int month = today.get(Calendar.MONTH) + 1;
        int day = today.get(Calendar.DATE);
        String dateStr = month + "/" + day + "/" + year;
        date = (TextView) findViewById(R.id.textViewTaskDate);
        date.setText("Tasks for " + dateStr);

        vaccineAdapter = new TasksVaccinationAdapter(getApplicationContext(), todayTasks, calfList);
        listView.setAdapter(vaccineAdapter);

    }

    public void dumbDateChange(View view) {
        startActivity(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS));
    }

    public void onClickObservations(View view) {
        setObservationColumnNames();

        observeCalves = new ArrayList<Calf>();

        for(int i = 0; i < calfList.size(); i++)
            if(calfList.get(i).isNeedToObserveForIllness())
                observeCalves.add(calfList.get(i));



        observationAdapter = new TasksObservationAdapter(observeCalves, getApplicationContext(), TasksActivity.this);

        listView.setAdapter(observationAdapter);
    }

    public void onClickVaccineTasks(View view) {
        setVaccineColumnNames();

        // ArrayList that holds all of the Vaccine Tasks for the current day
        ArrayList<VaccineTaskItem> todayTasks = new ArrayList<VaccineTaskItem>();

        // find only the START DATE vaccinetask objects so no doubles from END DATEs
        for(int i = 0; i < task.getVaccinesToAdminister().get(0).size(); i++)
            if(task.getVaccinesToAdminister().get(0).get(i).isStart())
                todayTasks.add(new VaccineTaskItem(false, task.getVaccinesToAdminister().get(0).get(i)));

        for(int i = 0; i < task.getOverdueVaccinations().size(); i++)
            todayTasks.add(new VaccineTaskItem(true, task.getOverdueVaccinations().get(i)));

        vaccineAdapter = new TasksVaccinationAdapter(getApplicationContext(), todayTasks, calfList);
        listView.setAdapter(vaccineAdapter);
    }

    public void showObservationDialog(Calf calf) {
        AlertDialog.Builder builder = new AlertDialog.Builder(TasksActivity.this);

        LayoutInflater inflater = TasksActivity.this.getLayoutInflater();


        View dialogView = inflater.inflate(R.layout.tasks_observation_dialog, null);

        final Spinner mIllnessSpinner = (Spinner) dialogView.findViewById(R.id.spinnerIllnessDialog);
        final Spinner mMedicationSpinner = (Spinner) dialogView.findViewById(R.id.spinnerMedicationDialog);

        ArrayAdapter spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, illnessList);

        mIllnessSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Illness selected = (Illness) mIllnessSpinner.getSelectedItem();
                ArrayAdapter medicationSpinAdapter = new ArrayAdapter(adapterView.getContext(), android.R.layout.simple_spinner_item, selected.getTreatmentProtocol().getMedicines());
                mMedicationSpinner.setAdapter(medicationSpinAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mIllnessSpinner.setAdapter(spinnerAdapter);



        final Calf calfcopy = calf;
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(dialogView)
                // Add action buttons
                .setPositiveButton("Confirm Illness", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Illness illness = (Illness) mIllnessSpinner.getSelectedItem();
                        Medicine medicine = (Medicine) mMedicationSpinner.getSelectedItem();

                        removeCalfFromObservations(calfcopy.getFarmId());

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });


        builder.create().show();
    }

    public void removeCalfFromObservations(String calfID) {
        for(int i = 0; i < observeCalves.size(); i++) {
            if(observeCalves.get(i).getFarmId().equals(calfID)) {
                observeCalves.remove(i);
                break;
            }

        }

        // Search through the calfList to find the correct calf by ID
        for (int i = 0; i < calfList.size(); i++) {
            if (calfList.get(i).getFarmId().equals(calfID)) {
                calfList.get(i).setNeedToObserveForIllness(!calfList.get(i).isNeedToObserveForIllness());
                break;
            }
        }

        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        Gson gson = new Gson();
        String json;

        SharedPreferences.Editor prefsEditor = mPreferences.edit();
        json = gson.toJson(task);
        prefsEditor.putString("Task",json);
        prefsEditor.apply();

        json = gson.toJson(calfList);
        prefsEditor.putString("CalfList",json);
        prefsEditor.apply();

        observationAdapter = new TasksObservationAdapter(observeCalves, getApplicationContext(), TasksActivity.this);

        listView.setAdapter(observationAdapter);
    }

    public void clickObservationItem(Calf calf) {

    }

    public void onClickIllnessTasks(View view) {
        setIllnessColumnNames();
    }

    public void setObservationColumnNames() {
        mLeft.setText(R.string.tasks_observations_calf_id);
        mRight.setText(R.string.tasks_observations_days_observed);
        if(mCenter.getVisibility() == View.VISIBLE)
            mCenter.setVisibility(View.GONE);
    }

    public void setVaccineColumnNames() {
        mLeft.setText(R.string.tasks_vaccine_name);
        mRight.setText(R.string.tasks_vaccine_elligible);
        if(mCenter.getVisibility() == View.VISIBLE)
            mCenter.setVisibility(View.GONE);
    }

    public void setIllnessColumnNames() {
        mLeft.setText(R.string.tasks_illness_calf_id);
        mRight.setText(R.string.tasks_illness_current_med);
        mCenter.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }
}
