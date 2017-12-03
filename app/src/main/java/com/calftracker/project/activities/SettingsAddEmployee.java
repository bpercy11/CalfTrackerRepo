package com.calftracker.project.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Employee;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class SettingsAddEmployee extends AppCompatActivity {

    private EditText name;
    private EditText id;
    private EditText position;
    private Button addEmployeeButton;
    private ArrayList<Employee> employeeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_add_employee);

        // Custom title
        getSupportActionBar().setTitle("Add Employee");

        name = (EditText) findViewById(R.id.addEmployeeNameText);
        id = findViewById(R.id.addEmployeeIDText);
        position = findViewById(R.id.addEmployeePositionText);
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
                    Employee emp = new Employee(name.getText().toString(), id.getText().toString(), position.getText().toString());
                    employeeList.add(emp);
                }

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

    public void clickCancelEmployeeButton(View view){
        Intent intent = new Intent(this, SettingsEditEmployeesActivity.class);
        startActivity(intent);
    }

    private void back() {
        Intent intent = new Intent(this, SettingsEditEmployeesActivity.class);
        finish();
        startActivity(intent);
    }
}
