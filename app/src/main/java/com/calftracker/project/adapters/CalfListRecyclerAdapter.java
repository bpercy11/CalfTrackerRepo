package com.calftracker.project.adapters;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.calftracker.project.activities.AddCalfActivity;
import com.calftracker.project.activities.CalfListActivity;
import com.calftracker.project.activities.CalfProfileActivity;
import com.calftracker.project.calftracker.R;
import com.google.gson.Gson;

import static com.calftracker.project.activities.CalfListActivity.arrayExists;

public class CalfListRecyclerAdapter extends BaseAdapter {
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

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
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
        holder.calfID.setOnClickListener(new OnClickListener() {
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

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder  { //extends RecyclerView.ViewHolder
        // each data item is just a string in this case
//        public TextView calfIDStatement;
        public TextView calfID;
//        public View layout;
//
//        public ViewHolder(View v) {
//            super(v);
//            layout = v;
//            calfIDStatement = (TextView) v.findViewById(R.id.textViewCalfID);
//            calfID = (TextView) v.findViewById(R.id.textViewCalfID);
//        }
    }

    public void add(int position, String item) {
        calfIDList.add(position, item);
        notifyDataSetChanged();
        //notifyItemInserted(position);
    }

    public void remove(int position) {
        calfIDList.remove(position);
        notifyDataSetChanged();
        //notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CalfListRecyclerAdapter(Context con, List<String> calfIDs) {
        mCon = con;
        calfIDList = calfIDs;
        this.layoutInflater = LayoutInflater.from(mCon);
        this.calfIDArray = new ArrayList<String>();
        this.calfIDArray.addAll(calfIDs);
    }

    // Create new views (invoked by the layout manager)
//    @Override
//    public CalfListRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        // create a new view
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        View v = inflater.inflate(R.layout.calf_list_recycler_view_item_layout, parent, false);
//        // set the view's size, margins, paddings and layout parameters
//        ViewHolder vh = new ViewHolder();
//        return vh;
//    }
//
//    // Replace the contents of a view (invoked by the layout manager)
//    @Override
//    public void onBindViewHolder(ViewHolder holder, final int position) {
//        // - get element from your dataset at this position
//        // - replace the contents of the view with that element
//        final String name = values.get(position);
//        holder.calfID.setText(name);
//        holder.calfID.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(!arrayExists) {
//                    Intent intent = new Intent(mCon,AddCalfActivity.class);
//                    mCon.startActivity(intent);
//                } else {
//                    SharedPreferences mPrefs = mCon.getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
//                    SharedPreferences.Editor prefsEditor = mPrefs.edit();
//                    Gson gson = new Gson();
//                    String json = gson.toJson(CalfListActivity.calfList.get(position).getFarmId());
//                    prefsEditor.putString("calfToViewInProfile", json);
//                    prefsEditor.apply();
//                    Intent intent = new Intent(mCon, CalfProfileActivity.class);
//                    mCon.startActivity(intent);
//                }
//            }
//        });
//    }

    // Return the size of your dataset (invoked by the layout manager)
//    @Override
//    public int getItemCount() {
//        return values.size();
//    }
}

// RecyclerView.Adapter<CalfListRecyclerAdapter.ViewHolder>
// extends RecyclerView.ViewHolder
