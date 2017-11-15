package com.calftracker.project.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.VaccineSelectionItem;

import java.util.ArrayList;

public class VaccineSelectionListViewAdapter extends ArrayAdapter {

    private ArrayList<VaccineSelectionItem> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        CheckBox checkBox;
    }

    public VaccineSelectionListViewAdapter(ArrayList data, Context context) {
        super(context, R.layout.new_calf_vaccine_selection_vaccine_row_item, data);
        this.dataSet = data;
        this.mContext = context;

    }
    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public VaccineSelectionItem getItem(int position) {
        return dataSet.get(position);
    }


    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;
        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_calf_vaccine_selection_vaccine_row_item, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);

            result=convertView;
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        VaccineSelectionItem item = getItem(position);


        viewHolder.txtName.setText(item.getVaccine().getName());
        viewHolder.checkBox.setChecked(item.getChecked());


        return result;
    }
}