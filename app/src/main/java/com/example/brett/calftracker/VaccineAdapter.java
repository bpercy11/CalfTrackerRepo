package com.example.brett.calftracker;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AlexanderGlowacki on 10/31/17.
 */

public class VaccineAdapter extends BaseAdapter {

    private Context context;
    private List<Vaccine> vaccineList;

    //Constructor
    public VaccineAdapter(Context getApplicationContext, List<Vaccine> vaccineList) {

        this.context = context;
        this.vaccineList = vaccineList;
    }

    public int getCount() {
        return vaccineList.size();
    }
    public Object getItem(int position) {
        return vaccineList.get(position);
    }
    public long getItemId(int position) {
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.vaccine_list, null);
        TextView vaccineName = (TextView)v.findViewById(R.id.vaccine_name);
        TextView vaccineAge = (TextView)v.findViewById(R.id.vaccine_toBeAdministered);
       // TextView vaccineMethod = (TextView)v.findViewById(R.id.vaccine_methodOfAdministration);

        //Set text for TextView
        vaccineName.setText(vaccineList.get(position).getName());
        vaccineAge.setText(vaccineList.get(position).getToBeAdministered());
       // vaccineMethod.setText(vaccineList.get(position).getMethodOfAdministration());

        return v;
    }



/* method of changing 'BaseAdapter' to 'ArrayAdapter' in class
    //ArrayAdapter Constructor
    public VaccineAdapter(Context context, ArrayList<Vaccine> vaccineList) {
        super(context, 0, vaccineList);
    }
    //ArrayAdapter getView
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.vaccine_list, parent, false);
        }
        // Lookup view for data population
        TextView vaccineName = (TextView)convertView.findViewById(R.id.vaccine_name);
        TextView vaccineAge = (TextView)convertView.findViewById(R.id.vaccine_toBeAdministered);
        // Populate the data into the template view using the data object
        vaccineName.setText(vaccineList.get(position).getName());
        vaccineAge.setText(vaccineList.get(position).getToBeAdministered());
        // Return the completed view to render on screen
        return convertView;
    }

    */
}
