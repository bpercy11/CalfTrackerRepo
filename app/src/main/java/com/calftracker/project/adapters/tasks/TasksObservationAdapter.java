package com.calftracker.project.adapters.tasks;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.calftracker.project.adapters.calfprofile.FeedingHistoryEmployeeSpinnerAdapter;
import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Calf;
import com.calftracker.project.models.Employee;

import java.util.ArrayList;

/**
 * Created by JT on 11/25/2017.
 */

public class TasksObservationAdapter extends BaseAdapter {

    private ArrayList<Calf> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView mCalfID;
        TextView mDaysObserved;
    }

    public TasksObservationAdapter(ArrayList<Calf> data, Context context) {
        this.dataSet = data;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public Calf getItem(int position) {
        return dataSet.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        TasksObservationAdapter.ViewHolder viewHolder;
        final View result;

        if (convertView == null) {
            viewHolder = new TasksObservationAdapter.ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tasks_observation_adapter_row_item, parent, false);
            viewHolder.mCalfID = (TextView) convertView.findViewById(R.id.textViewObservationRowCalfID);
            viewHolder.mCalfID.setText(getItem(position).getFarmId());
            viewHolder.mDaysObserved = (TextView) convertView.findViewById(R.id.textViewObservationRowDaysObserved);
            viewHolder.mDaysObserved.setText("" + 3);

            result=convertView;
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (TasksObservationAdapter.ViewHolder) convertView.getTag();
            result=convertView;
        }
//
//        Employee item = getItem(position);
//
//
//        viewHolder.mEmployeeName.setText(item.getName());


        return result;
    }
}
