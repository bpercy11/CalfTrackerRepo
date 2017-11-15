package com.calftracker.project.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Vaccine;
import com.calftracker.project.models.Vaccine_With_Date;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by JT on 11/14/2017.
 */

public class MedicalHistoryAdministeredVaccineAdapter extends BaseAdapter {

    private ArrayList<Vaccine_With_Date> vaccines;

    private final Context context;

    // View lookup cache
    private static class ViewHolder {
        TextView mName;
        TextView mDate;
    }

    public MedicalHistoryAdministeredVaccineAdapter(Context context, ArrayList<Vaccine_With_Date> vaccines) {
        this.context = context;
        this.vaccines = vaccines;
    }

    @Override
    public int getCount() {
        return vaccines.size();
    }

    @Override
    public Vaccine_With_Date getItem(int i) {
        return vaccines.get(i);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;
        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.calf_profile_medical_history_administered_item_layout, parent, false);
            viewHolder.mName = (TextView) convertView.findViewById(R.id.textViewVaccName);
            viewHolder.mDate = (TextView) convertView.findViewById(R.id.textViewVaccDate);

            result=convertView;
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }
        Vaccine_With_Date holder = this.vaccines.get(position);

        int year = holder.getDate().get(Calendar.YEAR);
        int month = holder.getDate().get(Calendar.MONTH) + 1;
        int day = holder.getDate().get(Calendar.DAY_OF_MONTH);

        viewHolder.mName.setText(holder.getVaccine().getName());

        viewHolder.mDate.setText(month + "/" + day + "/" + year);


        return result;
    }
}
