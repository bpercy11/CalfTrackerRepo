package com.example.brett.calftracker;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

public class CalfListRecyclerAdapter extends RecyclerView.Adapter<CalfListRecyclerAdapter.ViewHolder> {
    private List<String> values;
    private Context mCon;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView calfIDStatement;
        public TextView calfID;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            calfIDStatement = (TextView) v.findViewById(R.id.textViewCalfID);
            calfID = (TextView) v.findViewById(R.id.textViewCalfID);
        }
    }

    public void add(int position, String item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CalfListRecyclerAdapter(Context con, List<String> myDataset) {
        mCon = con;
        values = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CalfListRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.calf_list_recycler_view_item_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final String name = values.get(position);
        holder.calfID.setText(name);
        holder.calfID.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences mPrefs = mCon.getSharedPreferences("CalfTracker",Activity.MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                Gson gson = new Gson();
                String json = gson.toJson(CalfListActivity.calfList.get(position).getFarmId());
                prefsEditor.putString("calfToViewInProfile",json);
                prefsEditor.apply();
                Intent intent = new Intent(mCon,CalfProfileActivity.class);
                mCon.startActivity(intent);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }
}
