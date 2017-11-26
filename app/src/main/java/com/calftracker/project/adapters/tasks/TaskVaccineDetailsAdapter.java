package com.calftracker.project.adapters.tasks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Calf;
import com.calftracker.project.models.TaskDetailsCalfSelectionItem;
import com.calftracker.project.models.Vaccine;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by Jared on 11/20/2017.
 */

public class TaskVaccineDetailsAdapter extends ArrayAdapter {
    private ArrayList<TaskDetailsCalfSelectionItem> itemList;
    Context context;
    private Vaccine vaccine;

    private static class ViewHolder {
        TextView id;
//        TextView dose;
//        TextView method;
//        TextView dueDate;
        CheckBox checkBox;
    }

    public TaskVaccineDetailsAdapter(ArrayList data, Context context, Vaccine vacc) {
        super(context, R.layout.task_details_item, data);
        this.itemList = data;
        this.context = context;
        this.vaccine = vacc;

    }
    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public TaskDetailsCalfSelectionItem getItem(int position) {
        return itemList.get(position);
    }


    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_details_item, parent, false);
            holder.id = (TextView) convertView.findViewById(R.id.textViewCalfID);
//            holder.dose = (TextView) convertView.findViewById(R.id.textViewDose);
//            holder.method = (TextView) convertView.findViewById(R.id.textViewMethod);
//            holder.dueDate = (TextView) convertView.findViewById(R.id.textViewDueDate);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        TaskDetailsCalfSelectionItem item = getItem(position);

        holder.id.setText(item.getId());
        holder.checkBox.setChecked(item.getChecked());


        return convertView;
    }
}
