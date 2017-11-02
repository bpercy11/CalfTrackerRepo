package com.example.brett.calftracker;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by AlexanderGlowacki on 11/1/17.
 */

public class VaccineAdapter extends BaseAdapter {
    private Context context;
    private List<String> vaccineList;

    public VaccineAdapter(Context context, List<String> vaccineList) {
        this.context = context;
        this.vaccineList = vaccineList;
    }

    @Override
    public int getCount(){
        return vaccineList.size();
    }
    @Override
    public String getItem(int position){
        return vaccineList.get(position);
    }
    @Override
    public long getItemId(int position){
        return position;
    }
    public View getView(int position, View courtView, ViewGroup parent){
        View v = View.inflate(context, R.layout.vaccine_list,null);
        TextView vaccineName = (TextView)v.findViewById(R.id.vaccine_name);
        //TextView vaccineAge = (TextView)v.findViewById(R.id.vaccine_toBeAdministered);

        vaccineName.setText(vaccineList.get(position));
       // vaccineAge.setText(vaccineList.get(position).getToBeAdministered());

        return v;
    }

}
