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
    private List<Vaccine> vaccineList;

    public VaccineAdapter(Context context, List<Vaccine> vaccineList) {
        this.context = context;
        this.vaccineList = vaccineList;
    }

    @Override
    public int getCount(){
        return vaccineList.size();
    }
    @Override
    public Vaccine getItem(int position){
        return vaccineList.get(position);
    }
    @Override
    public long getItemId(int position){
        return position;
    }
    public View getView(int position, View courtView, ViewGroup parent){
        View v = View.inflate(context, R.layout.vaccine_list,null);
        TextView vaccineName = (TextView)v.findViewById(R.id.vaccine_list_vaccine_name);

        vaccineName.setText(vaccineList.get(position).getName());

        return v;
    }

}
