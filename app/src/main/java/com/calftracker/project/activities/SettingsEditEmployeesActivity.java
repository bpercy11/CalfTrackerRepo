package com.calftracker.project.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.calftracker.project.adapters.CalfListListViewAdapter;
import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Calf;
import com.calftracker.project.models.Employee;
import com.calftracker.project.models.Farm;
import com.calftracker.project.models.Employee;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.*;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;

import android.widget.ListView;

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

        Button addEmployeeButton = (Button) findViewById(R.id.addEmployeeBtn);
        mListView = (ListView) findViewById(R.id.employeesList);

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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,SettingsActivity.class);
        startActivity(intent);
    }


}
