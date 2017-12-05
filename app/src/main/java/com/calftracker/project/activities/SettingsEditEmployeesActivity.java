package com.calftracker.project.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.calftracker.project.adapters.calflist.CalfListListViewAdapter;
import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Employee;
import com.calftracker.project.models.Farm;
import com.calftracker.project.models.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Map;

public class SettingsEditEmployeesActivity extends AppCompatActivity {

    private ListView mListView;
    private ListView listView;
    private CalfListListViewAdapter adapter;
    private ArrayList<String> idArrayList = new ArrayList<String>();
    private ArrayList<Employee> employeeArrayList = new ArrayList<Employee>();
    private Farm farm;
    private ArrayList<Map> mapList;
    private boolean employeeListExists;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_edit_employees);

        Button addEmployeeButton = (Button) findViewById(R.id.addEmployeeBtn);
        mListView = (ListView) findViewById(R.id.employeesList);

        // Stylize action bar to use back button and custom title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Edit Employees");

        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        Gson gson = new Gson();
        String json;


        // FIREBASE READ DATA TEMPLATE //
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
                for(Employee temp : employeeArrayList){
                    Log.d("NAME", temp.getName());
                }

                //got data from database....now you can use the retrieved data
                String[] listItems = new String[employeeArrayList.size()];

                for(int i = 0; i < employeeArrayList.size(); i++)
                    listItems[i] = employeeArrayList.get(i).getName();

                ArrayAdapter adapter = new ArrayAdapter(SettingsEditEmployeesActivity.this, android.R.layout.simple_list_item_1, listItems);
                mListView.setAdapter(adapter);
            }

            @Override
            public void onStart() {
                //when starting
                Log.d("ONSTART", "Started");


            }

            @Override
            public void onFailure() {
                Log.d("ONFAILURE", "Failed");
            }
        });
        */

        /*
        Log.d("ELIST", "HELLO11");
        SharedPreferences sharedPref = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        SharedPreferences.Editor e1 = sharedPref.edit();
        Gson gson1 = new Gson();
        String json1;

        Employee e = new Employee("bobbbby");
        employeeArrayList.add(e);
        json1 = gson1.toJson(employeeArrayList);
        editor.putString("EmployeeList",json1);
        editor.apply();
        */

        employeeListExists = false;
        if (mPreferences.contains("EmployeeList")) {
            employeeListExists = true;
            json = mPreferences.getString("EmployeeList", "");
            employeeArrayList = gson.fromJson(json, new TypeToken<ArrayList<Employee>>() {
            }.getType());
        }


        if (mPreferences.contains("FarmProfile")) {
            //Log.d("TAG", "THE FARM PROFILE EXISTS");
            json = mPreferences.getString("FarmProfile", "");
            farm = gson.fromJson(json, Farm.class);
            farm.setEmployeeList(employeeArrayList);

        }

        if (employeeListExists){

            String[] listItems = new String[employeeArrayList.size()];

            for (int i = 0; i < employeeArrayList.size(); i++)
                listItems[i] = employeeArrayList.get(i).getName();

            ArrayAdapter adapter = new ArrayAdapter(SettingsEditEmployeesActivity.this, android.R.layout.simple_list_item_1, listItems);
            mListView.setAdapter(adapter);


            mListView.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                    Intent intent = new Intent(SettingsEditEmployeesActivity.this, SettingsEmployeeProfileActivity.class);
                    startActivity(intent);
                }
             });
        }

        addEmployeeButton.setOnClickListener(new View.OnClickListener(){
            Intent intent;
            public void onClick(View v){
                intent = new Intent(SettingsEditEmployeesActivity.this, SettingsAddEmployee.class);
                startActivity(intent);

            }
        });

    }
/*
    public interface OnGetDataListener {
        //this is for callbacks
        void onSuccess(DataSnapshot dataSnapshot);
        void onStart();
        void onFailure();
    }

    public void readData(DatabaseReference ref, final OnGetDataListener listener) {
        listener.onStart();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailure();
            }
        });

    }
*/
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
