package com.calftracker.project.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.calftracker.project.activities.AddCalfActivity;
import com.calftracker.project.activities.CalfListActivity;
import com.calftracker.project.activities.CalfProfileActivity;
import com.calftracker.project.calftracker.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.calftracker.project.activities.CalfListActivity.arrayExists;

/**
 * Created by Jared on 11/14/2017.
 */

public class CalfListListViewAdapter extends BaseAdapter {
    private Context mCon;
    private LayoutInflater layoutInflater;
    private List<String> calfIDList;
    private ArrayList<String> calfIDArray;

    @Override
    public int getCount() {
        return calfIDList.size();
    }
    @Override
    public Object getItem(int i) {
        return calfIDList.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }

    // Constructor
    public CalfListListViewAdapter(Context con, List<String> calfIDs) {
        mCon = con;
        calfIDList = calfIDs;
        this.layoutInflater = LayoutInflater.from(mCon);
        this.calfIDArray = new ArrayList<String>();
        this.calfIDArray.addAll(calfIDs);
    }

    public class ViewHolder  {
        public TextView calfID;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        // Basic adapter code, set up holder and use it to add items to View
        final ViewHolder holder;
        final int position = i;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.model_item, null);
            holder.calfID = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.calfID.setText(calfIDList.get(i));
        holder.calfID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!arrayExists) {
                    Intent intent = new Intent(mCon,AddCalfActivity.class);
                    mCon.startActivity(intent);
                } else {
                    SharedPreferences mPrefs = mCon.getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor prefsEditor = mPrefs.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(CalfListActivity.calfList.get(position).getFarmId());
                    prefsEditor.putString("calfToViewInProfile", json);
                    prefsEditor.apply();
                    Intent intent = new Intent(mCon, CalfProfileActivity.class);
                    mCon.startActivity(intent);
                }
            }
        });
        return convertView;
    }

    public void add(int position, String item) {
        calfIDList.add(position, item);
        notifyDataSetChanged();
    }

    public void myFilter(String id) {
        id = id.toLowerCase(Locale.getDefault());
        calfIDList.clear();
        if (id.length() == 0) {
            calfIDList.addAll(calfIDArray);
        } else {
            for (String ID : calfIDArray) {
                if (ID.toLowerCase(Locale.getDefault()).contains(id)) {
                    calfIDList.add(ID);
                }
            }
        }
        notifyDataSetChanged();
    }
}
