package com.calftracker.project.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.calftracker.project.adapters.TasksAdapter;
import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Calf;
import com.calftracker.project.models.Task;
import com.calftracker.project.models.Vaccine;
import com.calftracker.project.models.VaccineTask;
import com.calftracker.project.models.Vaccine_With_Count;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TasksActivity extends AppCompatActivity {
    private Task task;
    private List<Vaccine_With_Count> vaccCountList;
    private ArrayList<Calf> calfList;
    private ListView listView;
    private TasksAdapter adapter;
    private TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPreferences.getString("Task", "");
        task = gson.fromJson(json, new TypeToken<Task>() {}.getType());

        json = mPreferences.getString("CalfList", "");
        calfList = gson.fromJson(json, new TypeToken<ArrayList<Calf>>() {}.getType());

        ArrayList<VaccineTask> todayTasks = task.getVaccinesToAdminister().get(0);

        List<Vaccine> allVaccines = new ArrayList<Vaccine>();
        for (int i = 0; i < todayTasks.size(); i++) {
            allVaccines.add(todayTasks.get(i).getVaccine());
        }

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
