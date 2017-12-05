package com.calftracker.project.adapters.calfprofile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Physical_Metrics_And_Date;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Jared on 11/15/2017.
 */

public class GrowthHistoryWeightAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Physical_Metrics_And_Date> sizes;
    private LayoutInflater inflater;

    public class ViewHolder {
        TextView weight;
        TextView date;
        TextView empty;
    }

    public GrowthHistoryWeightAdapter(Context context, ArrayList<Physical_Metrics_And_Date> sizes) {
        this.context = context;
        this.sizes = sizes;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {return sizes.size();}
    @Override
    public Object getItem(int i) {return sizes.get(i);}
    @Override
    public long getItemId(int i) {return i;}
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.calf_profile_growth_history_weight_item_layout, null);
            holder.weight = (TextView) convertView.findViewById(R.id.textViewWeight);
            holder.date = (TextView) convertView.findViewById(R.id.textViewWeightDate);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Physical_Metrics_And_Date size = this.sizes.get(position);

        int year = size.makeDateRecorded().get(Calendar.YEAR);
        int month = size.makeDateRecorded().get(Calendar.MONTH) + 1;
        int day = size.makeDateRecorded().get(Calendar.DAY_OF_MONTH);

        holder.weight.setText(Double.toString(sizes.get(position).getWeight()) + " lbs");
        holder.date.setText(month + "/" + day + "/" + year);

        if (sizes.get(position).getWeight() == -1) {
            holder.weight.setVisibility(View.GONE);
            holder.date.setVisibility(View.GONE);
        }

//        if (sizes.size() == 0) {
//            holder.empty = (TextView) convertView.findViewById(R.id.textViewWeight);
//            holder.empty.setText("No weight recordings have been made for this calf.");
//            holder.weight.setVisibility(View.GONE);
//            holder.date.setVisibility(View.GONE);
//        }

        return convertView;
    }
}
