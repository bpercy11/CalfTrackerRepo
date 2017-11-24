package com.calftracker.project.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.calftracker.project.adapters.TasksAdapter;
import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Calf;
import com.calftracker.project.models.Task;
import com.calftracker.project.models.VaccineTask;
import com.calftracker.project.models.Vaccine_With_Count;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TasksActivity extends BaseActivity {
    private Task task;
    private List<Vaccine_With_Count> vaccCountList;
    private ArrayList<Calf> calfList;
    private ListView listView;
    private TasksAdapter adapter;
    private TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_tasks, frameLayout);
        mNavigationView.getMenu().findItem(R.id.nav_tasks).setChecked(true);

        // Custom title
        getSupportActionBar().setTitle(R.string.tasks_title);

        // Load in the Task and CalfList
        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPreferences.getString("Task", "");
        task = gson.fromJson(json, new TypeToken<Task>() {}.getType());
        json = mPreferences.getString("CalfList", "");
        calfList = gson.fromJson(json, new TypeToken<ArrayList<Calf>>() {}.getType());

        // ArrayList that holds all of the Vaccine Tasks for the current day
        ArrayList<VaccineTask> todayTasks = task.getVaccinesToAdminister().get(0);

        // Create the ListView and create a header message for the activity
        listView = (ListView) findViewById(R.id.listViewTasks);
        Calendar today = Calendar.getInstance();
        int year = today.get(Calendar.YEAR);
        int month = today.get(Calendar.MONTH) + 1;
        int day = today.get(Calendar.DATE);
        String dateStr = month + "/" + day + "/" + year;
        date = (TextView) findViewById(R.id.textViewTaskDate);
        date.setText("Tasks for " + dateStr);

        adapter = new TasksAdapter(getApplicationContext(), todayTasks, calfList);
        listView.setAdapter(adapter);

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }
}
