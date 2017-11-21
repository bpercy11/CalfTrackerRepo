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

import com.calftracker.project.adapters.TaskDetailsAdapter;
import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Calf;
import com.calftracker.project.models.TaskDetailsCalfSelectionItem;
import com.calftracker.project.models.Vaccine;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TaskDetailsActivity extends AppCompatActivity {

    ArrayList<TaskDetailsCalfSelectionItem> adapterArray;
    TaskDetailsAdapter adapter;

    private ArrayList<Vaccine> vaccineList;
    private Vaccine vaccine;
    private ArrayList<Calf> calfList;
    private TextView vaccName;
    private Calf calf;

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

        // get vaccine list from shared preferences
        json = mPreferences.getString("VaccineList", "");
        vaccineList = gson.fromJson(json, new TypeToken<ArrayList<Vaccine>>() {}.getType());
        adapterArray = new ArrayList<>();
        ArrayList<Calf> vaccineCalfList = new ArrayList<>();
        for (int i = 0; i < calfList.size(); i++) {
            for (int j = 0; j < calfList.get(i).getNeededVaccines().size(); j++) {
                if (calfList.get(i).getNeededVaccines().get(j).getName().equals(vaccine.getName())) {
                    vaccineCalfList.add(calfList.get(i));
                    break;
                }
            }
        }
        for(int i = 0; i < vaccineCalfList.size(); i++) {
            adapterArray.add(new TaskDetailsCalfSelectionItem(vaccineCalfList.get(i).getFarmId(),false));
        }
        adapter = new TaskDetailsAdapter(adapterArray, getApplicationContext(), vaccine);

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
        for (int i = 0; i < adapterArray.size(); i++) {
            if (adapterArray.get(i).getChecked()) {
                for (int j = 0; j < calfList.size(); j++) {
                    if (calfList.get(j).getFarmId().equals(adapterArray.get(i).getId())) {
                        calf = calfList.get(j);
                        break;
                    }
                }
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
                SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                Gson gson = new Gson();
                String json = gson.toJson(calfList);
                prefsEditor.putString("CalfList",json);
                prefsEditor.apply();

                adapter.notifyDataSetChanged();
            }
        }

        Intent intent = new Intent(this, TasksActivity.class);
        startActivity(intent);
    }


}


