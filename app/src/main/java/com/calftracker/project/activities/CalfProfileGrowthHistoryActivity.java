package com.calftracker.project.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.calftracker.project.adapters.calfprofile.GrowthHistoryHeightAdapter;
import com.calftracker.project.adapters.calfprofile.GrowthHistoryWeightAdapter;
import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Calf;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Date;

public class CalfProfileGrowthHistoryActivity extends AppCompatActivity {

    String calfID;
    ArrayList<Calf> calfList;
    private Calf calf;

    GrowthHistoryWeightAdapter weightAdapter;
    GrowthHistoryHeightAdapter heightAdapter;
    private boolean noWeights = true;
    private boolean noHeights = true;

    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calf_profile_growth_history);

        // Stylize action bar to use back button and custom title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Growth History");

        retrieveData();

        // try and get calf object made by main activity
        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        Gson gson = new Gson();
        String json = mPreferences.getString("CalfList","");
        json = mPreferences.getString("calfToViewInProfile","");
        calfID = gson.fromJson(json, String.class);

        // Search through the calfList to find the correct calf by ID
        for (int i = 0; i < calfList.size(); i++) {
            if (calfList.get(i).getFarmId().equals(calfID)) {
                calf = calfList.get(i);
                break;
            }
        }
        noRecordingsCheck();

        listview = (ListView) findViewById(R.id.listViewGrowthHistory);
        if (calf.getPhysicalHistory().isEmpty() || noWeights) {
            findViewById(R.id.textViewNoWeightRecorded).setVisibility(View.VISIBLE);
            findViewById(R.id.graphViewHeightHistory).setVisibility(View.GONE);
            findViewById(R.id.graphViewWeightHistory).setVisibility(View.GONE);
            findViewById(R.id.textViewNoHeightRecorded).setVisibility(View.GONE);
        } else {
            findViewById(R.id.textViewNoWeightRecorded).setVisibility(View.GONE);
            findViewById(R.id.textViewNoHeightRecorded).setVisibility(View.GONE);
            findViewById(R.id.graphViewHeightHistory).setVisibility(View.GONE);
            GraphView graph = (GraphView) findViewById(R.id.graphViewWeightHistory);
            graph.removeAllSeries();
            LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();
            for (int i = 0; i < calf.getPhysicalHistory().size(); i++) {
                if (calf.getPhysicalHistory().get(i).getWeight() != -1) {
                    Date tempDate = calf.getPhysicalHistory().get(i).makeDateRecorded().getTime();
                    series.appendData(new DataPoint(tempDate, calf.getPhysicalHistory().get(i).getWeight()), true, calf.getPhysicalHistory().size());
                }
            }
            graph.addSeries(series);
            graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
            graph.getGridLabelRenderer().setNumHorizontalLabels(4); // only 4 because of the space
            // set manual x bounds to have nice steps
            graph.getViewport().setMinX(series.getLowestValueX());
            graph.getViewport().setMaxX(series.getHighestValueX());
            graph.getViewport().setXAxisBoundsManual(true);
            // as we use dates as labels, the human rounding to nice readable numbers is not necessary
            graph.getGridLabelRenderer().setHumanRounding(false);
        }

        weightAdapter = new GrowthHistoryWeightAdapter(getApplicationContext(),calf.getPhysicalHistory());
        listview.setAdapter(weightAdapter);

    }

    // TODO
    public void saveData() {
        // EMPTY METHOD TO KEEP CONSISTENCY
        // NO DATA IS SAVED IN THIS ACTIVITY
    }

    public void retrieveData() {
        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPreferences.getString("CalfList","");
        calfList = gson.fromJson(json, new TypeToken<ArrayList<Calf>>(){}.getType());
    }

    public void onClickWeightButton(View view) {
        noRecordingsCheck();
        if (calf.getPhysicalHistory().isEmpty() || noWeights) {
            findViewById(R.id.textViewNoWeightRecorded).setVisibility(View.VISIBLE);
            findViewById(R.id.textViewNoHeightRecorded).setVisibility(View.GONE);
            findViewById(R.id.graphViewHeightHistory).setVisibility(View.GONE);
            findViewById(R.id.graphViewWeightHistory).setVisibility(View.GONE);
        } else {
            findViewById(R.id.textViewNoWeightRecorded).setVisibility(View.GONE);
            findViewById(R.id.textViewNoHeightRecorded).setVisibility(View.GONE);
            findViewById(R.id.graphViewHeightHistory).setVisibility(View.GONE);
            findViewById(R.id.graphViewWeightHistory).setVisibility(View.VISIBLE);
        }
        GraphView graph = (GraphView) findViewById(R.id.graphViewWeightHistory);
        graph.removeAllSeries();
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();
        for (int i = 0; i < calf.getPhysicalHistory().size(); i++) {
            if (calf.getPhysicalHistory().get(i).getWeight() != -1) {
                Date tempDate = calf.getPhysicalHistory().get(i).makeDateRecorded().getTime();
                series.appendData(new DataPoint(tempDate, calf.getPhysicalHistory().get(i).getWeight()), true, calf.getPhysicalHistory().size());
            }
        }
        graph.addSeries(series);
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
        graph.getGridLabelRenderer().setNumHorizontalLabels(4); // only 4 because of the space
        // set manual x bounds to have nice steps
        graph.getViewport().setMinX(series.getLowestValueX());
        graph.getViewport().setMaxX(series.getHighestValueX());
        graph.getViewport().setXAxisBoundsManual(true);
        // as we use dates as labels, the human rounding to nice readable numbers is not necessary
        graph.getGridLabelRenderer().setHumanRounding(false);
        weightAdapter = new GrowthHistoryWeightAdapter(getApplicationContext(),calf.getPhysicalHistory());
        listview.setAdapter(weightAdapter);
    }

    public void onClickHeightButton(View view) {
        noRecordingsCheck();
        if (calf.getPhysicalHistory().isEmpty() || noHeights) {
            findViewById(R.id.textViewNoHeightRecorded).setVisibility(View.VISIBLE);
            findViewById(R.id.textViewNoWeightRecorded).setVisibility(View.GONE);
            findViewById(R.id.graphViewHeightHistory).setVisibility(View.GONE);
            findViewById(R.id.graphViewWeightHistory).setVisibility(View.GONE);
        } else {
            findViewById(R.id.textViewNoWeightRecorded).setVisibility(View.GONE);
            findViewById(R.id.textViewNoHeightRecorded).setVisibility(View.GONE);
            findViewById(R.id.graphViewWeightHistory).setVisibility(View.INVISIBLE);
            findViewById(R.id.graphViewHeightHistory).setVisibility(View.VISIBLE);
        }
        GraphView graph = (GraphView) findViewById(R.id.graphViewHeightHistory);
        graph.removeAllSeries();
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();
        for (int i = 0; i < calf.getPhysicalHistory().size(); i++) {
            if (calf.getPhysicalHistory().get(i).getHeight() != -1) {
                Date tempDate = calf.getPhysicalHistory().get(i).makeDateRecorded().getTime();
                series.appendData(new DataPoint(tempDate, calf.getPhysicalHistory().get(i).getHeight()), true, calf.getPhysicalHistory().size());
            }
        }
        graph.addSeries(series);
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
        graph.getGridLabelRenderer().setNumHorizontalLabels(4); // only 4 because of the space
        // set manual x bounds to have nice steps
        graph.getViewport().setMinX(series.getLowestValueX());
        graph.getViewport().setMaxX(series.getHighestValueX());
        graph.getViewport().setXAxisBoundsManual(true);
        // as we use dates as labels, the human rounding to nice readable numbers is not necessary
        graph.getGridLabelRenderer().setHumanRounding(false);
        heightAdapter = new GrowthHistoryHeightAdapter(getApplicationContext(),calf.getPhysicalHistory());
        listview.setAdapter(heightAdapter);
    }

    public void noRecordingsCheck() {
        for (int i = 0; i < calf.getPhysicalHistory().size(); i++) {
            if (calf.getPhysicalHistory().get(i).getWeight() != -1) {
                noWeights = false;
            }
            if (calf.getPhysicalHistory().get(i).getHeight() != -1) {
                noHeights = false;
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, CalfProfileActivity.class);
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(getApplicationContext(), CalfProfileActivity.class);
        startActivity(intent);
        return true;
    }
}
