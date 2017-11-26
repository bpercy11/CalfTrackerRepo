package com.calftracker.project.adapters.tasks;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.calftracker.project.calftracker.R;
import com.calftracker.project.interfaces.TasksMethods;
import com.calftracker.project.models.IllnessTask;

import java.util.ArrayList;

/**
 * Created by JT on 11/26/2017.
 */

public class TasksIllnessListViewAdapter extends BaseAdapter {

    private ArrayList<IllnessTask> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView mCalfID;
        TextView mIllness;
        TextView mMedication;
    }

    public TasksIllnessListViewAdapter(ArrayList<IllnessTask> data, Context context) {
        this.dataSet = data;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public IllnessTask getItem(int position) {
        return dataSet.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        TasksIllnessListViewAdapter.ViewHolder viewHolder;
        final View result;

        if (convertView == null) {
            viewHolder = new TasksIllnessListViewAdapter.ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tasks_illness_adapter_row_item, parent, false);

            viewHolder.mCalfID = (TextView) convertView.findViewById(R.id.textViewIllnessRowCalfID);
            viewHolder.mIllness = (TextView) convertView.findViewById(R.id.textViewIllnessRowIllness);
            viewHolder.mMedication = (TextView) convertView.findViewById(R.id.textViewIllnessRowMedication);

            viewHolder.mCalfID.setText(getItem(position).getCalf().getFarmId());
            viewHolder.mIllness.setText(getItem(position).getIllness().getName());
            viewHolder.mMedication.setText(getItem(position).getMedicine().getName());


            result=convertView;
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (TasksIllnessListViewAdapter.ViewHolder) convertView.getTag();
            result=convertView;
        }


        return result;
    }
}
