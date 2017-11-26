package com.calftracker.project.adapters.protocols;

import android.view.LayoutInflater;
import android.widget.TextView;

import android.content.Context;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;

import com.calftracker.project.models.Medicine;
import com.calftracker.project.calftracker.R;

import java.util.List;

/**
 * Created by AlexanderGlowacki on 10/31/17.
 */

public class MedicineAdapter extends BaseAdapter {
    private Context context;
    private List<Medicine> medicineList;
    private LayoutInflater layoutInflater;
    private int viewReourceID;

    //Constructor
    public MedicineAdapter(Context applicationContext, int viewResourceID, List<Medicine> medicineList) {
        this.context = applicationContext;
        this.medicineList = medicineList;
        this.layoutInflater = LayoutInflater.from(context);
        this.viewReourceID = viewResourceID;
    }

    public class ViewHolder  {
        public TextView medicineName;
        public TextView medicineTimeActive;
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
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.medicine_list, null);
            holder.medicineName = (TextView) convertView.findViewById(R.id.medicine_list_editTextMedicine);
            holder.medicineTimeActive = (TextView) convertView.findViewById(R.id.medicine_list_editTextTimeActive);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.medicineName.setText(medicineList.get(position).getName());
        holder.medicineTimeActive.setText(Integer.toString(medicineList.get(position).getTimeActive()));
        return convertView;
    }
}



