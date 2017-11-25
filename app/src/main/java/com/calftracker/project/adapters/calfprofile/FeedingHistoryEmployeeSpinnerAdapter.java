package com.calftracker.project.adapters.calfprofile;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Employee;

import java.util.ArrayList;

/**
 * Created by JT on 11/25/2017.
 */

public class FeedingHistoryEmployeeSpinnerAdapter extends BaseAdapter {

    private ArrayList<Employee> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView mEmployeeName;
    }

    public FeedingHistoryEmployeeSpinnerAdapter(ArrayList data, Context context) {
        this.dataSet = data;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public Employee getItem(int position) {
        return dataSet.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        FeedingHistoryEmployeeSpinnerAdapter.ViewHolder viewHolder;
        final View result;

        if (convertView == null) {
            viewHolder = new FeedingHistoryEmployeeSpinnerAdapter.ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.feeding_history_employe_spinner_item, parent, false);
            viewHolder.mEmployeeName = (TextView) convertView.findViewById(R.id.textViewEmployeeName);

            result=convertView;
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (FeedingHistoryEmployeeSpinnerAdapter.ViewHolder) convertView.getTag();
            result=convertView;
        }

        Employee item = getItem(position);


        viewHolder.mEmployeeName.setText(item.getName());


        return result;
    }
}
