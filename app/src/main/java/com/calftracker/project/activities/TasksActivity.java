package com.calftracker.project.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.calftracker.project.adapters.tasks.TasksIllnessAdapter;
import com.calftracker.project.adapters.tasks.TasksObservationAdapter;
import com.calftracker.project.adapters.tasks.TasksVaccinationAdapter;
import com.calftracker.project.calftracker.R;
import com.calftracker.project.interfaces.TasksMethods;
import com.calftracker.project.models.Calf;
import com.calftracker.project.models.Calf_Illness;
import com.calftracker.project.models.Firebase;
import com.calftracker.project.models.Illness;
import com.calftracker.project.models.IllnessTask;
import com.calftracker.project.models.Medicine;
import com.calftracker.project.models.Task;
import com.calftracker.project.models.Vaccine;
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
    private ArrayList<Vaccine> vaccineList;

    private TasksObservationAdapter observationAdapter;

    TextView mLeftLabel;
    TextView mRightLabel;
    TextView mCenterLabel;

    Button vaccinesButton;
    Button observationsButton;
    Button illnessesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_tasks, frameLayout);
        mNavigationView.getMenu().findItem(R.id.nav_tasks).setChecked(true);

        // Custom title
        getSupportActionBar().setTitle(R.string.tasks_title);

        mLeftLabel = (TextView) findViewById(R.id.textViewVaccineNameTitle);
        mRightLabel = (TextView) findViewById(R.id.textVieweligibleTitle);
        mCenterLabel = (TextView) findViewById(R.id.textViewIllnessNameTasks);


        retrieveData();

        task.updateTasks();

        saveData();

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

        vaccineAdapter = new TasksVaccinationAdapter(getApplicationContext(), todayTasks, vaccineList, calfList, TasksActivity.this);
        listView.setAdapter(vaccineAdapter);

        vaccinesButton = (Button) findViewById(R.id.buttonVaccineTasks);
        observationsButton = (Button) findViewById(R.id.buttonObservationTasks);
        illnessesButton = (Button) findViewById(R.id.buttonIllnessTasks);

        // Background tint only works on 15 & up.
        // This should probably be changed to Background Color but that overrides styles and I'm lazy
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            vaccinesButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorMedGrey));
            vaccinesButton.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        }
    }

    // TODO
    public void saveData() {
        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        Gson gson = new Gson();
        String json;
        SharedPreferences.Editor prefsEditor = mPreferences.edit();
        json = gson.toJson(task);
        prefsEditor.putString("Task",json);
        prefsEditor.apply();

        Firebase fb = (Firebase) getApplicationContext();
        fb.saveData("Task", task);

        json = gson.toJson(calfList);
        prefsEditor.putString("CalfList",json);
        prefsEditor.apply();

        fb.saveData("CalfList", calfList);

    }

    public void retrieveData() {
        // Load in the Task and CalfList
        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPreferences.getString("Task", "");
        task = gson.fromJson(json, new TypeToken<Task>() {}.getType());
        json = mPreferences.getString("CalfList", "");
        calfList = gson.fromJson(json, new TypeToken<ArrayList<Calf>>() {}.getType());

        json = mPreferences.getString("IllnessList", "");
        illnessList = gson.fromJson(json, new TypeToken<ArrayList<Illness>>() {}.getType());

        json = mPreferences.getString("VaccineList", "");
        vaccineList = gson.fromJson(json, new TypeToken<ArrayList<Vaccine>>() {
        }.getType());

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

        // Background tint only works on 15 & up.
        // This should probably be changed to Background Color but that overrides styles and I'm lazy
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            vaccinesButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorLightGrey));
            vaccinesButton.setTextColor(ContextCompat.getColor(this, android.R.color.black));
            observationsButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorMedGrey));
            observationsButton.setTextColor(ContextCompat.getColor(this, android.R.color.white));
            illnessesButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorLightGrey));
            illnessesButton.setTextColor(ContextCompat.getColor(this, android.R.color.black));
        }
    }

    public void showObservationDialog(Calf calf) {
        if(illnessList == null) {
            Toast.makeText(getApplicationContext(), "No illnesses defined", Toast.LENGTH_SHORT).show();
            return;
        } else if(illnessList.isEmpty()) {
            Toast.makeText(getApplicationContext(), "No illnesses defined", Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(TasksActivity.this);

        LayoutInflater inflater = TasksActivity.this.getLayoutInflater();


        final View dialogView = inflater.inflate(R.layout.tasks_observation_dialog, null);

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
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Illness illness = (Illness) mIllnessSpinner.getSelectedItem();
                        Medicine medicine = (Medicine) mMedicationSpinner.getSelectedItem();

                        if(illness == null) {
                            Toast.makeText(getApplicationContext(), "No illness defined", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if(medicine == null) {
                            Toast.makeText(getApplicationContext(), "No medicine defined", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        removeCalfFromObservations(illness, medicine, calfcopy.getFarmId());

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });


        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        Button mUnmark = (Button) dialogView.findViewById(R.id.buttonUnmarkObservationDialog);

        mUnmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeCalfFromObservations(null, null, calfcopy.getFarmId());
                alertDialog.dismiss();
            }
        });
    }

    public void removeCalfFromObservations(Illness illness, Medicine medication, String calfID) {
        Calf calfToRemove = null;

        for(int i = 0; i < observeCalves.size(); i++) {
            if(observeCalves.get(i).getFarmId().equals(calfID)) {
                observeCalves.remove(i);
                break;
            }
        }

        // Search through the calfList to find the correct calf by ID
        for (int i = 0; i < calfList.size(); i++) {
            if (calfList.get(i).getFarmId().equals(calfID)) {
                calfToRemove = calfList.get(i);
                calfToRemove.setNeedToObserveForIllness(!calfList.get(i).isNeedToObserveForIllness());
                break;
            }
        }

        if(illness != null) {
            calfToRemove.getIllnessHistory().add(new Calf_Illness(illness, Calendar.getInstance(), "test note"));
            task.getIllnessTracker().get(0).add(new IllnessTask(illness, medication, calfToRemove));
        }

        saveData();

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

        vaccineAdapter = new TasksVaccinationAdapter(getApplicationContext(), todayTasks, vaccineList, calfList, TasksActivity.this);
        listView.setAdapter(vaccineAdapter);

        // Background tint only works on 15 & up.
        // This should probably be changed to Background Color but that overrides styles and I'm lazy
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            vaccinesButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorMedGrey));
            vaccinesButton.setTextColor(ContextCompat.getColor(this, android.R.color.white));
            observationsButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorLightGrey));
            observationsButton.setTextColor(ContextCompat.getColor(this, android.R.color.black));
            illnessesButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorLightGrey));
            illnessesButton.setTextColor(ContextCompat.getColor(this, android.R.color.black));
        }
    }

    public void onClickIllnessTasks(View view) {
        setIllnessColumnNames();

        TasksIllnessAdapter illnessAdapter = new TasksIllnessAdapter(task.getIllnessTracker(), getApplicationContext(), TasksActivity.this);

        listView.setAdapter(illnessAdapter);

        // Background tint only works on 15 & up.
        // This should probably be changed to Background Color but that overrides styles and I'm lazy
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            vaccinesButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorLightGrey));
            vaccinesButton.setTextColor(ContextCompat.getColor(this, android.R.color.black));
            observationsButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorLightGrey));
            observationsButton.setTextColor(ContextCompat.getColor(this, android.R.color.black));
            illnessesButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorMedGrey));
            illnessesButton.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        }
    }

    public void gotoIllnessDetails(IllnessTask illnessTask) {
        SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(illnessTask);
        prefsEditor.putString("TaskIllnessDetails",json);
        prefsEditor.apply();

        Intent intent = new Intent(this, TaskIllnessDetailsActivity.class);
        startActivity(intent);
    }

    public void setObservationColumnNames() {
        mLeftLabel.setText(R.string.tasks_observations_calf_id);
        mRightLabel.setText(R.string.tasks_observations_days_observed);
        if(mCenterLabel.getVisibility() == View.VISIBLE)
            mCenterLabel.setVisibility(View.GONE);
    }

    public void setVaccineColumnNames() {
        mLeftLabel.setText(R.string.tasks_vaccine_name);
        mRightLabel.setText(R.string.tasks_vaccine_eligible);
        if(mCenterLabel.getVisibility() == View.VISIBLE)
            mCenterLabel.setVisibility(View.GONE);
    }

    public void setIllnessColumnNames() {
        mLeftLabel.setText(R.string.tasks_illness_calf_id);
        mRightLabel.setText(R.string.tasks_illness_current_med);
        mCenterLabel.setVisibility(View.VISIBLE);
    }

    public void gotoTaskVaccDetails() {
        Intent intent = new Intent(this, TaskDetailsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(this, DashboardActivity.class);
            startActivity(intent);
        }
    }
}
