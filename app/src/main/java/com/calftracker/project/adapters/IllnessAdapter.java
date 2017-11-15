package com.calftracker.project.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.calftracker.project.models.Illness;
import com.calftracker.project.calftracker.R;

import java.util.List;

/**
 * Created by AlexanderGlowacki on 11/1/17.
 */

public class IllnessAdapter extends BaseAdapter {

    private Context context;
    private List<Illness> illnessList;

    public IllnessAdapter(Context context, List<Illness> illnessList) {
        this.context = context;
        this.illnessList = illnessList;
    }

    public int getCount() {
        return illnessList.size();
    }
    public Illness getItem(int position) {
        return illnessList.get(position);
    }
    public long getItemId(int position) {
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.illness_list, null);
        TextView illnessIllness = (TextView)v.findViewById(R.id.illness_name);
        TextView illnessTreatment = (TextView)v.findViewById(R.id.treatment_protocol);

        //Set text for TextView
        illnessIllness.setText(illnessList.get(position).getName());
        //illnessTreatment.setText(illnessList.get(position).getTreatmentProtocol().getMedicine().getName());

        return v;
    }
}
