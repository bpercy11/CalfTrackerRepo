package com.example.brett.calftracker;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AlexanderGlowacki on 10/31/17.
 */

public class VaccineAdapter extends BaseAdapter {

    private Context context;
    private List<Vaccine> vaccineList;

    public VaccineAdapter(Context applicationContext, List<Vaccine> vaccineList) {
        this.context = context;
        this.vaccineList = vaccineList;
    }

    //Constructor
  /*  public void vaccineAdapter (Context context, List<Vaccine> vaccineList){
        this.context = context;
        this.vaccineList = vaccineList;
    }*/

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
        TextView vaccineMethod = (TextView)v.findViewById(R.id.vaccine_methodOfAdministration);

        //Set text for TextView
        vaccineName.setText(vaccineList.get(position).getName());
        vaccineAge.setText(vaccineList.get(position).getToBeAdministered());
        vaccineMethod.setText(vaccineList.get(position).getMethodOfAdministration());

        return v;
    }
}
