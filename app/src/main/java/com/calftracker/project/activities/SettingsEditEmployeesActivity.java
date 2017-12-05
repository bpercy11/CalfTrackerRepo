package com.calftracker.project.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.calftracker.project.adapters.calflist.CalfListListViewAdapter;
import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Employee;
import com.calftracker.project.models.Farm;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class SettingsEditEmployeesActivity extends AppCompatActivity {

    private ListView mListView;
    private ListView listView;
    private CalfListListViewAdapter adapter;
    private ArrayList<String> idArrayList = new ArrayList<String>();
    private ArrayList<Employee> employeeArrayList;
    private Farm farm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_edit_employees);

        // Stylize action bar to use back button and custom title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Employees List");

        Button addEmployeeButton = (Button) findViewById(R.id.addEmployeeBtn);
        mListView = (ListView) findViewById(R.id.employeesList);

        retrieveData();

        String[] listItems = new String[employeeArrayList.size()];

        for(int i = 0; i < employeeArrayList.size(); i++){
            listItems[i] = employeeArrayList.get(i).getName();
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems);
        mListView.setAdapter(adapter);


        mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                Gson gson = new Gson();
                String json = gson.toJson(employeeArrayList.get(position));
                prefsEditor.putString("EmployeeProfile",json);
                prefsEditor.apply();

                Intent intent = new Intent(SettingsEditEmployeesActivity.this, SettingsEmployeeProfileActivity.class);
                startActivity(intent);
            }
        });


        addEmployeeButton.setOnClickListener(new View.OnClickListener(){
            Intent intent;
            public void onClick(View v){
                intent = new Intent(SettingsEditEmployeesActivity.this, SettingsAddEmployee.class);
                startActivity(intent);
            }
        });

    }

    // TODO
    public void saveData() {
        // EMPTY METHOD TO KEEP CONSISTENCY
        // NO DATA IS SAVED IN THIS ACTIVITY
    }

    public void retrieveData() {
        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        Gson gson = new Gson();
        String json;

        if(mPreferences.contains("EmployeeList")) {
            json = mPreferences.getString("EmployeeList", "");
            employeeArrayList = gson.fromJson(json, new TypeToken<ArrayList<Employee>>() {
            }.getType());
        } else { employeeArrayList = new ArrayList<Employee>(); }

        if(mPreferences.contains("FarmProfile")){
            //Log.d("TAG", "THE FARM PROFILE EXISTS");
            json = mPreferences.getString("FarmProfile", "");
            farm = gson.fromJson(json, Farm.class);
            farm.setEmployeeList(employeeArrayList);
            //Employee n = (Employee) farm.getEmployees().get(0);
            //Log.d("Employee 1", n.getName());
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,SettingsActivity.class);
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(intent);
        return true;
    }
}
