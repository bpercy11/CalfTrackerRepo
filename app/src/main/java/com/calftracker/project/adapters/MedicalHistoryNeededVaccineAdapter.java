package com.calftracker.project.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.calftracker.project.calftracker.R;
import com.calftracker.project.interfaces.MedicalHistoryVaccineMethods;
import com.calftracker.project.models.Vaccine;

import java.util.ArrayList;

/**
 * Created by JT on 11/14/2017.
 */

public class MedicalHistoryNeededVaccineAdapter extends BaseAdapter {

    private ArrayList<Vaccine> vaccines;

    private final Context context;

    private MedicalHistoryVaccineMethods NVA;

    // View lookup cache
    private static class ViewHolder {
        TextView mName;
        Button mView;
        Button mAdminister;
    }

    public MedicalHistoryNeededVaccineAdapter(Context context, ArrayList<Vaccine> vaccines, MedicalHistoryVaccineMethods NVA) {
        this.context = context;
        this.vaccines = vaccines;
        this.NVA = NVA;
    }

    @Override
    public int getCount() {
        return vaccines.size();
    }

    @Override
    public Vaccine getItem(int i) {
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.calf_profile_medical_history_needed_item_layout, parent, false);
            viewHolder.mName = (TextView) convertView.findViewById(R.id.textViewNeededVac);
            viewHolder.mView = (Button) convertView.findViewById(R.id.buttonViewVacc);
            viewHolder.mAdminister = (Button) convertView.findViewById(R.id.buttonAdminister);

            result=convertView;
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }


        viewHolder.mName.setText(this.vaccines.get(position).getName());

        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // OPEN DIALOG TO VIEW SELECTED VACCINE INFORMATION
            }
        });

        viewHolder.mAdminister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NVA.administerVaccine(vaccines.get(position));
            }
        });


        return result;
    }
}
