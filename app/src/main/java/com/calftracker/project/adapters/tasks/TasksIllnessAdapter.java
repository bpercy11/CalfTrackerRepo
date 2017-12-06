package com.calftracker.project.adapters.tasks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.calftracker.project.activities.DashboardActivity;
import com.calftracker.project.activities.TaskIllnessDetailsActivity;
import com.calftracker.project.calftracker.R;
import com.calftracker.project.interfaces.TasksMethods;
import com.calftracker.project.models.IllnessTask;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by JT on 11/26/2017.
 */

public class TasksIllnessAdapter extends BaseAdapter {

    private ArrayList<ArrayList<IllnessTask>> dataSet;
    Context mContext;
    private TasksMethods TM;

    // View lookup cache
    private static class ViewHolder {
        LinearLayout days;
    }

    public TasksIllnessAdapter(ArrayList<ArrayList<IllnessTask>> data, Context context, TasksMethods TM) {
        this.dataSet = data;
        this.mContext = context;
        this.TM = TM;
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public ArrayList<IllnessTask> getItem(int position) {
        return dataSet.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        TasksIllnessAdapter.ViewHolder viewHolder;
        final View result;

        if (convertView == null) {
            viewHolder = new TasksIllnessAdapter.ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tasks_illness_linearlayout, parent, false);
            viewHolder.days = (LinearLayout) convertView.findViewById(R.id.linearLayoutRowItem);

            if(!getItem(position).isEmpty()) {
                LayoutInflater inflator = LayoutInflater.from(convertView.getContext());
                for(int i = 0; i < dataSet.get(position).size(); i++) {
                    View view = inflator.inflate(R.layout.tasks_illness_adapter_row_item, viewHolder.days, false);
                    TextView mCalfID = (TextView) view.findViewById(R.id.textViewIllnessRowCalfID);
                    TextView mIllness = (TextView) view.findViewById(R.id.textViewIllnessRowIllness);
                    TextView mMedication = (TextView) view.findViewById(R.id.textViewIllnessRowMedication);
                    ImageView mDueMedication = (ImageView) view.findViewById(R.id.imageViewDueMedication);

                    if(position == 0)
                        mDueMedication.setVisibility(View.VISIBLE);

                    view.setClickable(true);

                    mCalfID.setText(dataSet.get(position).get(i).getCalf().getFarmId());
                    mIllness.setText(dataSet.get(position).get(i).getIllness().getName());
                    mMedication.setText(dataSet.get(position).get(i).getMedicine().getName());

                    final int getdata = position;
                    final int j = i;

                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            SharedPreferences mPrefs = mContext.getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
                            SharedPreferences.Editor prefsEditor = mPrefs.edit();
                            Gson gson = new Gson();
                            String json = gson.toJson(dataSet.get(getdata).get(j));
                            prefsEditor.putString("TaskIllnessDetails",json);
                            prefsEditor.apply();

                            Intent intent = new Intent(mContext, TaskIllnessDetailsActivity.class);
                            mContext.startActivity(intent);
                        }
                    });

                    viewHolder.days.addView(view);
                }
            } else {
                viewHolder.days.setVisibility(View.GONE);
            }

            result=convertView;
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (TasksIllnessAdapter.ViewHolder) convertView.getTag();
            result=convertView;
        }


        return result;
    }
}
