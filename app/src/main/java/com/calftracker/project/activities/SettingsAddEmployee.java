package com.calftracker.project.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;

import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Calf;
import com.calftracker.project.models.Employee;
import com.calftracker.project.models.Farm;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class SettingsAddEmployee extends AppCompatActivity {

    private EditText name;
    private EditText id;
    private Button addEmployeeButton;
    private ArrayList<Employee> employeeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_add_employee);

        name = (EditText) findViewById(R.id.addEmployeeNameText);
        id = findViewById(R.id.addEmployeeIDText);
        addEmployeeButton = (Button) findViewById(R.id.settingsAddEmployeeAcceptBtn);

        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        Gson gson = new Gson();
        String json;

        if(mPreferences.contains("EmployeeList")) {
            json = mPreferences.getString("EmployeeList", "");
            employeeList = gson.fromJson(json, new TypeToken<ArrayList<Employee>>() {
            }.getType());
        } else { employeeList = new ArrayList<Employee>(); }


        addEmployeeButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                boolean requirementsNotMet = false;

                if(name.getText().toString().equals("")){
                    requirementsNotMet = true;
                } else {
                    Employee emp = new Employee(name.getText().toString());
                    employeeList.add(emp);
                }

                //dialogBox();

                if(!requirementsNotMet) {
                    SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor prefsEditor = mPrefs.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(employeeList);
                    prefsEditor.putString("EmployeeList",json);
                    prefsEditor.apply();

                    back();
                }


            }
        });


    }

    private void back() {

        Intent intent = new Intent(this, SettingsEditEmployeesActivity.class);
        finish();
        startActivity(intent);

    }
}
