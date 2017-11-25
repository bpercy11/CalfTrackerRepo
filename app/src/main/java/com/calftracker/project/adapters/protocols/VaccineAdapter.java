package com.calftracker.project.adapters.protocols;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Vaccine;

import java.util.List;

/**
 * Created by AlexanderGlowacki on 11/1/17.
 */

public class VaccineAdapter extends BaseAdapter {

    private Context context;
    private List<Vaccine> vaccineList;
    private LayoutInflater layoutInflater;

    public VaccineAdapter(Context context, List<Vaccine> vaccineList) {
        this.context = context;
        this.vaccineList = vaccineList;
        this.layoutInflater = LayoutInflater.from(context);
    }
    public class ViewHolder{
        public TextView vaccineName;
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
    public View getView(int position, View convertView, ViewGroup parent){
        final ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.vaccine_list,null);
            holder.vaccineName = (TextView) convertView.findViewById(R.id.vaccine_list_vaccine_name);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.vaccineName.setText(vaccineList.get(position).getName());
        return convertView;
    }

}
