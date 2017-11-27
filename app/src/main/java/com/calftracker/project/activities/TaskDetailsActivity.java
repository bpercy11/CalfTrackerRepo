package com.calftracker.project.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.calftracker.project.adapters.tasks.TaskVaccineDetailsAdapter;
import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Calf;
import com.calftracker.project.models.Task;
import com.calftracker.project.models.TaskDetailsCalfSelectionItem;
import com.calftracker.project.models.Vaccine;
import com.calftracker.project.models.VaccineTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TaskDetailsActivity extends AppCompatActivity {

    ArrayList<TaskDetailsCalfSelectionItem> adapterArray;
    TaskVaccineDetailsAdapter adapter;

    private Vaccine vaccine;
    private ArrayList<Calf> calfList;
    private TextView vaccName;
    private Calf calf;
    private Task task;
    ArrayList<VaccineTask> todayTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        // get needed UI elements
        ListView listView = (ListView) findViewById(R.id.listViewVaccineSelection);

        // set up shared preference variables
        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        Gson gson = new Gson();
        String json;

        json = mPreferences.getString("CalfList", "");
        calfList = gson.fromJson(json, new TypeToken<ArrayList<Calf>>() {}.getType());

        json = mPreferences.getString("vaccToViewInTaskDetails","");
        vaccine = gson.fromJson(json, new TypeToken<Vaccine>() {}.getType());

        json = mPreferences.getString("Task", "");
        task = gson.fromJson(json, new TypeToken<Task>() {}.getType());

        adapterArray = new ArrayList<>();
        ArrayList<Calf> vaccineCalfList = new ArrayList<>();
        todayTasks = task.getVaccinesToAdminister().get(0);

        // Add the eligible calves for this vaccine to a list
        for (int i = 0; i < todayTasks.size(); i++)
            if (todayTasks.get(i).getVaccine().getName().equals(vaccine.getName()))
                if(todayTasks.get(i).isStart())
                    vaccineCalfList.add(todayTasks.get(i).getCalf());

        for(int i = 0; i < task.getOverdueVaccinations().size(); i++)
            if(task.getOverdueVaccinations().get(i).getVaccine().getName().equals(vaccine.getName()))
                vaccineCalfList.add(task.getOverdueVaccinations().get(i).getCalf());


        for(int i = 0; i < vaccineCalfList.size(); i++)
            adapterArray.add(new TaskDetailsCalfSelectionItem(vaccineCalfList.get(i).getFarmId(),false));


        adapter = new TaskVaccineDetailsAdapter(adapterArray, getApplicationContext(), vaccine);

        vaccName = (TextView) findViewById(R.id.textViewTaskItemVaccineName);
        vaccName.setText(vaccine.getName());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {

                TaskDetailsCalfSelectionItem dataModel= adapterArray.get(position);
                dataModel.setChecked(!dataModel.getChecked());
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void onClickSelectAll(View view) {

        for(int i = 0; i < adapterArray.size(); i++) {
            adapterArray.get(i).setChecked(true);
        }
        adapter.notifyDataSetChanged();
    }

    public void onClickConfirm(View view) {
        // Check through each item in the array to see if it is checked
        for (int i = 0; i < adapterArray.size(); i++) {
            if (adapterArray.get(i).getChecked()) {
                // Get the specific calf from the calfList
                for (int j = 0; j < calfList.size(); j++) {
                    if (calfList.get(j).getFarmId().equals(adapterArray.get(i).getId())) {
                        calf = calfList.get(j);
                        break;
                    }
                }
                // Update the calf's needed/administered vaccine fields
                for(int j = 0; j < calf.getNeededVaccines().size(); j++) {
                    if (calf.getNeededVaccines().get(j).getName().equals(vaccine.getName())) {
                        calf.getNeededVaccines().remove(j);

                        Calendar today = Calendar.getInstance();
                        int todayYear = today.get(Calendar.YEAR);
                        int todayMonth = today.get(Calendar.MONTH);
                        int todayDay = today.get(Calendar.DATE);

                        calf.addAdministeredVaccine(vaccine,new GregorianCalendar(todayYear,todayMonth,todayDay));
                        break;
                    }
                }

                // I'm like 90% sure that this loop will take out extra calves in the tasks.
                // a situtation where like calf #100 is elligble for both Vaccine A and Vaccine B
                // if you were on the taskdetails for Vaccine A it would probably take out
                // Vaccine B as well. haven't tested yet so it might not be a problem -JT
                for (int j = 0; j < todayTasks.size(); j++) {
                    if (todayTasks.get(j).getCalf().getFarmId().equals(calf.getFarmId())) {
                        todayTasks.remove(j);
                    }
                }

                for(int j = 0; j < task.getOverdueVaccinations().size(); j++)
                    if(task.getOverdueVaccinations().get(j).getCalf().getFarmId().equals(calf.getFarmId()))
                        task.getOverdueVaccinations().remove(j);


                // Save the calf to the device
                SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                Gson gson = new Gson();
                String json = gson.toJson(calfList);
                prefsEditor.putString("CalfList",json);
                prefsEditor.apply();

                json = gson.toJson(task);
                prefsEditor.putString("Task",json);
                prefsEditor.apply();

                adapter.notifyDataSetChanged();
            }
        }

        Intent intent = new Intent(this, TasksActivity.class);
        startActivity(intent);
    }


}


