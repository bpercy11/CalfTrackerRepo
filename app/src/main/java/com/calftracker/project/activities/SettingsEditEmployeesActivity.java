package com.calftracker.project.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Employee;
import java.util.*;
import android.widget.ArrayAdapter;

import android.widget.ListView;

public class SettingsEditEmployeesActivity extends AppCompatActivity {



    private ListView mListView;
    private ArrayList<Employee> employeeArrayList;//Employee.getRecipesFromFile("recipes.json", this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_edit_employees);

        mListView = (ListView) findViewById(R.id.employeesList);
        employeeArrayList = new ArrayList<Employee>();

        if(employeeArrayList.size() == 0){
        }
// 1
        //for(int i = 0; i < 9; i++){
        //    Employee emp = new Employee("employee"+i);
        //    employeeArrayList.add(emp);
        //}
// 2


        String[] listItems = new String[employeeArrayList.size()];
// 3
        for(int i = 0; i < employeeArrayList.size(); i++){
            listItems[i] = employeeArrayList.get(i).getName();
        }
// 4
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems);
        mListView.setAdapter(adapter);



    }

    private ArrayList<Employee> addEmployee(){

        return null;
    }

    private ArrayList<Employee> deleteEmployee(){

        return null;
    }

}
