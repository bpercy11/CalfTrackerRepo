package com.calftracker.project.adapters.tasks;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.calftracker.project.adapters.addcalf.VaccineSelectionListViewAdapter;
import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Illness;
import com.calftracker.project.models.VaccineSelectionItem;

import java.util.ArrayList;

/**
 * Created by JT on 11/25/2017.
 */

public class IllnessObservationDialogSpinnerAdapter extends BaseAdapter {

    private ArrayList<Illness> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView mIllnessName;
    }

    public IllnessObservationDialogSpinnerAdapter(ArrayList<Illness> data, Context context) {
        this.dataSet = data;
        this.mContext = context;
    }


    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public Illness getItem(int position) {
        return dataSet.get(position);
    }


    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        IllnessObservationDialogSpinnerAdapter.ViewHolder viewHolder;
        final View result;

        if (convertView == null) {
            viewHolder = new IllnessObservationDialogSpinnerAdapter.ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tasks_observation_dialog_spinner_item, parent, false);
            viewHolder.mIllnessName = (TextView) convertView.findViewById(R.id.textViewIllnessDialog);

            result = convertView;
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (IllnessObservationDialogSpinnerAdapter.ViewHolder) convertView.getTag();
            result=convertView;
        }

        Illness item = getItem(position);


        viewHolder.mIllnessName.setText(item.getName());


        return result;
    }
}
