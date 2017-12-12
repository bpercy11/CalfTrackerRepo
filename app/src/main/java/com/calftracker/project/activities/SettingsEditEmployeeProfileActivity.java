package com.calftracker.project.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Employee;
import com.calftracker.project.models.Firebase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class SettingsEditEmployeeProfileActivity extends AppCompatActivity {

    private Employee employee;
    private String originalName;
    private TextView employeeName;
    private TextView employeeID;
    private TextView employeePosition;
    private ArrayList<Employee> employeeArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_edit_employee_profile);

        // Custom title
        getSupportActionBar().setTitle("Edit Employee");

        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);

        //Load clicked Employee
        if(mPreferences.contains("EmployeeProfile")) {
            SharedPreferences.Editor editor = mPreferences.edit();

            Gson gson = new Gson();
            String json = mPreferences.getString("EmployeeProfile", "");
            employee = gson.fromJson(json, new TypeToken<Employee>() {
            }.getType());
        }

        if(mPreferences.contains("EmployeeList")) {
            SharedPreferences.Editor editor = mPreferences.edit();

            Gson gson = new Gson();
            String json = mPreferences.getString("EmployeeList", "");
            employeeArrayList = gson.fromJson(json, new TypeToken<ArrayList<Employee>>() {
            }.getType());
        }

        originalName = employee.getName();

        employeeName = (TextView) findViewById(R.id.employeeNameText);
        employeeID = (TextView) findViewById(R.id.employeeIDText);
        employeePosition = (TextView) findViewById(R.id.employeePositionText);

        employeeName.setText(employee.getName());
        employeeID.setText(employee.getId());
        employeePosition.setText(employee.getPosition());
    }

    public void addEditedEmployeeButton(View view) {
        if (employeeName.getText().toString().matches("")){
            Toast.makeText(this, "Please fill any empty fields",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        employee.setName(employeeName.getText().toString());
        employee.setId(employeeID.getText().toString());
        employee.setPosition(employeePosition.getText().toString());

        for (int i = 0; i < employeeArrayList.size(); i++) {
            if (employeeArrayList.get(i).getName().equals(originalName)) {
                employeeArrayList.set(i, employee);
            }
        }

        SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(employeeArrayList);
        prefsEditor.putString("EmployeeList",json);
        prefsEditor.apply();

        Firebase fb = (Firebase) getApplicationContext();
        fb.saveData("EmployeeList", employeeArrayList);

        Intent intent = new Intent(this, SettingsEditEmployeesActivity.class);
        startActivity(intent);
    }

    public void cancelEditedEmployeeButton(View view){
        Intent intent = new Intent(this, SettingsEditEmployeesActivity.class);
        startActivity(intent);
    }

    public void deleteEditedEmployeeButton(View view) {
        AlertDialog.Builder builderDelete = new AlertDialog.Builder(this);
        builderDelete.setMessage("Are you sure you want to delete this employee? This action cannot be undone.")
                .setTitle("Delete Employee");
        builderDelete.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Remove the employee from the list and save the new list to local storage
                for (int j = 0; j < employeeArrayList.size(); j++) {
                    if (employeeArrayList.get(j).getName().equals(originalName)) {
                        employeeArrayList.remove(j);
                    }
                }
                SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                Gson gson = new Gson();
                String json = gson.toJson(employeeArrayList);
                prefsEditor.putString("EmployeeList",json);
                prefsEditor.apply();

                Firebase fb = (Firebase) getApplicationContext();
                fb.saveData("EmployeeList", employeeArrayList);

                // Show a toast saying that the employee was removed
                Context context = getApplicationContext();
                CharSequence text = "Employee successfully deleted";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                // Go back to the EmployeeList View
                Intent intent = new Intent(SettingsEditEmployeeProfileActivity.this, SettingsEditEmployeesActivity.class);
                startActivity(intent);
            }
        });
        builderDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Do nothing
            }
        });

        AlertDialog alertDelete = builderDelete.create();
        alertDelete.show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, SettingsEditEmployeesActivity.class);
        startActivity(intent);
    }
}
