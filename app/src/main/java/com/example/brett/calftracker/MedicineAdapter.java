package com.example.brett.calftracker;

import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.brett.calftracker.Illness;
import com.example.brett.calftracker.Treatment_Protocol;
import com.example.brett.calftracker.R;

        import android.app.Activity;
        import android.content.Context;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.TextView;

        import java.util.ArrayList;
import java.util.List;

/**
 * Created by AlexanderGlowacki on 10/31/17.
 */

public class MedicineAdapter extends BaseAdapter {
    private Context context;
    private List<Illness> illnessList;
    private List<Medicine> medicineList;
    private Treatment_Protocol treatment_protocol;

    //Constructor
    public MedicineAdapter(Context applicationContext, List<Medicine> medicineList) {
        this.context = context;
        this.medicineList = medicineList;
    }

    public void IllnessAdapter (Context applicationContext, List<Illness> medicineList){
        this.context = context;
        this.illnessList = illnessList;
    }


    public int getCount() {
        return illnessList.size();
    }
    public Object getItem(int position) {
        return illnessList.get(position);
    }
    public long getItemId(int position) {
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.medicine_list, null);
        TextView illnessIllness = (TextView)v.findViewById(R.id.illness_name);
        TextView medicineName = (TextView)v.findViewById(R.id.medicine_name);
        TextView medicineTimeActive = (TextView)v.findViewById(R.id.medicine_frequency);

        //Set text for TextView
        illnessIllness.setText(illnessList.get(position).getName());
        medicineName.setText(medicineList.get(position).getName());
        medicineTimeActive.setText(medicineList.get(position).getTimeActive());

        return v;
    }
}



