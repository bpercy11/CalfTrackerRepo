package com.calftracker.project.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Employee;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class SettingsEmployeeProfileActivity extends AppCompatActivity {

    private Employee employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_employee_profile);

        // Stylize action bar to use back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);

        //Load clicked Employee
        if(mPreferences.contains("EmployeeProfile")) {
            SharedPreferences.Editor editor = mPreferences.edit();

            Gson gson = new Gson();
            String json = mPreferences.getString("EmployeeProfile", "");
            employee = gson.fromJson(json, new TypeToken<Employee>() {
            }.getType());
        }

        // Custom title
        getSupportActionBar().setTitle(employee.getName());

        TextView employeeName = (TextView) findViewById(R.id.employeeNameText);
        TextView employeeID = (TextView) findViewById(R.id.employeeIDText);
        TextView employeePosition = (TextView) findViewById(R.id.employeePositionText);

        if (employee.getName() != null && !employee.getName().equals("")) {
            employeeName.setText(employee.getName());
        }
        else {
            employeeName.setText(R.string.settings_employee_no_name);
        }

        if (employee.getId() != null && !employee.getId().equals("")) {
            employeeID.setText(employee.getId());
        }
        else {
            employeeID.setText(R.string.settings_employee_no_id);
        }

        if (employee.getPosition() != null && !employee.getPosition().equals("")) {
            employeePosition.setText(employee.getPosition());
        }
        else {
            employeePosition.setText(R.string.settings_employee_no_position);
        }
    }

    public void onEmployeeProfile_EditButton(View view){
        Intent intent = new Intent(this, SettingsEditEmployeeProfileActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,SettingsEditEmployeesActivity.class);
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(getApplicationContext(), SettingsEditEmployeesActivity.class);
        startActivity(intent);
        return true;
    }

    // TODO
    public void saveData() {
        // EMPTY METHOD TO KEEP CONSISTENCY
        // NO DATA IS SAVED IN THIS ACTIVITY
    }

    public void retrieveData() {
        // EMPTY METHOD TO KEEP CONSISTENCY
        // NO DATA IS RETRIEVED IN THIS ACTIVITY
    }
}
