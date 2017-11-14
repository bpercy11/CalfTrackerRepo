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
    private List<Medicine> medicineList;

    //Constructor
    public MedicineAdapter(Context applicationContext, List<Medicine> medicineList) {
        this.context = context;
        this.medicineList = medicineList;
    }


    public int getCount() {
        return medicineList.size();
    }
    public Medicine getItem(int position) {
        return medicineList.get(position);
    }
    public long getItemId(int position) {
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.medicine_list, null);
        TextView medicineName = (TextView)v.findViewById(R.id.medicine_list_editTextMedicine);
        TextView medicineTimeActive = (TextView)v.findViewById(R.id.medicine_list_editTextTimeActive);

        //Set text for TextView
        medicineName.setText(medicineList.get(position).getName());
        medicineTimeActive.setText(medicineList.get(position).getTimeActive());

        return v;
    }
}



