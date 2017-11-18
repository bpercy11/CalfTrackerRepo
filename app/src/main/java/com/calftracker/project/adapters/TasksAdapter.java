package com.calftracker.project.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Calf;
import com.calftracker.project.models.TODO;
import com.calftracker.project.models.VaccineTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Jared on 11/16/2017.
 */

public class TasksAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<VaccineTask> tasks;
    private LayoutInflater inflater;
    private boolean isElligble = false;
    private int elligbleCount;
    private ArrayList<Calf> calfList;

    public class ViewHolder {
        TextView vaccine;
        TextView elligible;
    }

    public TasksAdapter(Context context, ArrayList<VaccineTask> tasks, ArrayList<Calf> calfList) {
        this.context = context;
        this.tasks = tasks;
        this.inflater = LayoutInflater.from(context);
        this.calfList = calfList;
    }

    @Override
    public int getCount() {return tasks.size();}
    @Override
    public VaccineTask getItem(int i) {return tasks.get(i);}
    @Override
    public long getItemId(int i) {return i;}
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.tasks_vaccine_item, null);
            holder.vaccine = (TextView) convertView.findViewById(R.id.textViewVaccineName);
            holder.elligible = (TextView) convertView.findViewById(R.id.textViewElligible);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        VaccineTask task = this.tasks.get(position);
        for (int i = 0; i < calfList.size(); i++) {
            for (int j = 0; j < calfList.get(i).getNeededVaccines().size(); j++) {
                if (calfList.get(i).getNeededVaccines().get(j).getName().equals((task.getVaccine()).getName())) {
                    elligbleCount++;
                }
            }
        }

        holder.vaccine.setText(tasks.get(position).getVaccine().getName());
        holder.elligible.setText(Integer.toString(elligbleCount));

        //TODO create the onClickListener that takes user to the TaskDetailsActivity

        return convertView;
    }
}
