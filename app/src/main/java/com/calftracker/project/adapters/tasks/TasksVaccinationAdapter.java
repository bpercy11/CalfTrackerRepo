package com.calftracker.project.adapters.tasks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.calftracker.project.activities.TaskDetailsActivity;
import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Calf;
import com.calftracker.project.models.Vaccine;
import com.calftracker.project.models.VaccineTaskItem;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by Jared on 11/16/2017.
 */

public class TasksVaccinationAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<VaccineTaskItem> todayTasks;
    private LayoutInflater inflater;
    private boolean isElligble = false;
    private ArrayList<Calf> calfList;
    private ArrayList<String> usedVaccNames = new ArrayList<>();

    public class ViewHolder {
        TextView vaccine;
        TextView elligible;
        ImageView overdueNotification;
    }

    public TasksVaccinationAdapter(Context context, ArrayList<VaccineTaskItem> tasks, ArrayList<Calf> calfList) {
        this.context = context;
        this.todayTasks = tasks;
        this.inflater = LayoutInflater.from(context);
        this.calfList = calfList;
    }

    @Override
    public int getCount() {return todayTasks.size();}
    @Override
    public VaccineTaskItem getItem(int i) {return todayTasks.get(i);}
    @Override
    public long getItemId(int i) {return i;}
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        VaccineTaskItem task = this.todayTasks.get(position);
        boolean vaccUsed = false;
        for (int i = 0; i < usedVaccNames.size(); i++) {
            if (usedVaccNames.get(i).equals(task.getVaccineTask().getVaccine().getName())) {
                vaccUsed = true;
                break;
            }
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.tasks_vaccine_item, null);
            holder.vaccine = (TextView) convertView.findViewById(R.id.textViewTaskItemVaccineName);
            holder.elligible = (TextView) convertView.findViewById(R.id.textViewElligible);
            holder.overdueNotification = (ImageView) convertView.findViewById(R.id.imageViewOverdue);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        int elligibleCount = 0;
        final Vaccine currVacc = task.getVaccineTask().getVaccine();

        if (!vaccUsed) {
//            for (int i = 0; i < calfList.size(); i++) {
//                for (int j = 0; j < calfList.get(i).getNeededVaccines().size(); j++) {
//                    if (calfList.get(i).getNeededVaccines().get(j).getName().equals((currVacc).getName())) {
//                        elligibleCount++;
//                    }
//                }
//            }
            for (int i = 0; i < todayTasks.size(); i++) {
                if (todayTasks.get(i).getVaccineTask().getVaccine().getName().equals(currVacc.getName())) {
                    for (int j = 0; j < todayTasks.get(i).getVaccineTask().getCalf().getNeededVaccines().size(); j++) {
                        if (todayTasks.get(i).getVaccineTask().getCalf().getNeededVaccines().get(j).getName().equals(currVacc.getName())) {
                            elligibleCount++;
                            if(todayTasks.get(i).isOverdue())
                                holder.overdueNotification.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }


            holder.vaccine.setText(todayTasks.get(position).getVaccineTask().getVaccine().getName());
            holder.elligible.setText(Integer.toString(elligibleCount));
            usedVaccNames.add(currVacc.getName());
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences mPrefs = context.getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor prefsEditor = mPrefs.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(currVacc);
                    prefsEditor.putString("vaccToViewInTaskDetails", json);
                    prefsEditor.apply();
                    Intent intent = new Intent(context, TaskDetailsActivity.class);
                    context.startActivity(intent);
                }
            });
        } else {
            holder.vaccine.setVisibility(View.GONE);
            holder.elligible.setVisibility(View.GONE);
        }

        return convertView;
    }
}
