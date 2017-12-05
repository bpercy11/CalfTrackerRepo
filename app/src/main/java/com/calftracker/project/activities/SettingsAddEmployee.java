package com.calftracker.project.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;

import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Calf;
import com.calftracker.project.models.Employee;
import com.calftracker.project.models.Farm;
import com.calftracker.project.models.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class SettingsAddEmployee extends AppCompatActivity {

    private EditText name;
    private EditText id;
    private Button addEmployeeButton;
    private ArrayList<Employee> employeeArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_add_employee);

        name = (EditText) findViewById(R.id.addEmployeeNameText);
        id = findViewById(R.id.addEmployeeIDText);
        addEmployeeButton = (Button) findViewById(R.id.settingsAddEmployeeAcceptBtn);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Employee");

        retrieveData();



        // TEMPLATE FOR READING IN FROM FIREBASE //
        /*
        Firebase fb = (Firebase) getApplicationContext();

        //Data read method
        DatabaseReference root = FirebaseDatabase.getInstance().getReference();
        fb.readData(root.child("EmployeeList"), new Firebase.OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                Log.d("ONSUCCESS", "success");
                employeeArrayList = new ArrayList<Employee>();
                GenericTypeIndicator<ArrayList<Employee>> t = new GenericTypeIndicator<ArrayList<Employee>>() {};
                ArrayList<Employee> e = dataSnapshot.getValue(t);

                employeeArrayList = e;

            }
            @Override
            public void onStart() {
                //when starting
                Log.d("ONSTART", "Started");
                // Stylize action bar to use back button and custom title



            }
            @Override
            public void onFailure() {
                Log.d("ONFAILURE", "Failed");
            }
        });
        */


        addEmployeeButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                boolean requirementsNotMet = false;

                if(name.getText().toString().equals("")){
                    requirementsNotMet = true;
                } else {
                    Employee emp = new Employee(name.getText().toString());
                    employeeArrayList.add(emp);
                }

                if(!requirementsNotMet) {

                    SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor prefsEditor = mPrefs.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(employeeArrayList);
                    prefsEditor.putString("EmployeeList",json);
                    prefsEditor.apply();

                    DatabaseReference root = FirebaseDatabase.getInstance().getReference();
                    root.child("EmployeeList").setValue(employeeArrayList);

                    back();
                }


            }
        });


    }

    private void retrieveData(){
        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        Gson gson = new Gson();
        String json;


        if(mPreferences.contains("EmployeeList")) {
            json = mPreferences.getString("EmployeeList", "");
            employeeArrayList = gson.fromJson(json, new TypeToken<ArrayList<Employee>>() {
            }.getType());
        } else { employeeArrayList = new ArrayList<Employee>(); }
    }


    private void back() {

        Intent intent = new Intent(this, SettingsEditEmployeesActivity.class);
        finish();
        startActivity(intent);

    }
}
