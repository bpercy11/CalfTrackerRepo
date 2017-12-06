package com.calftracker.project.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.calftracker.project.adapters.calfprofile.GrowthHistoryHeightAdapter;
import com.calftracker.project.adapters.calfprofile.GrowthHistoryWeightAdapter;
import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Calf;
import com.calftracker.project.models.Physical_Metrics_And_Date;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CalfProfileGrowthHistoryActivity extends AppCompatActivity {

    String calfID;
    ArrayList<Calf> calfList;
    private Calf calf;

    GrowthHistoryWeightAdapter weightAdapter;
    GrowthHistoryHeightAdapter heightAdapter;
    private int weightCount;
    private int heightCount;
    private double avgWeight;
    private double avgHeight;
    private ArrayList<Physical_Metrics_And_Date> weights;
    private ArrayList<Physical_Metrics_And_Date> heights;

    ListView listview;
    TextView averageGrowth;

    Button heightButton;
    Button weightButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calf_profile_growth_history);

        retrieveData();

        // try and get calf object made by main activity
        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        Gson gson = new Gson();
        String json = mPreferences.getString("CalfList", "");
        json = mPreferences.getString("calfToViewInProfile", "");
        calfID = gson.fromJson(json, String.class);

        // Search through the calfList to find the correct calf by ID
        for (int i = 0; i < calfList.size(); i++) {
            if (calfList.get(i).getFarmId().equals(calfID)) {
                calf = calfList.get(i);
                break;
            }
        }

        // Stylize action bar to use back button and custom title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Calf " + calfID + " Growth History");

        averageGrowth = (TextView) findViewById(R.id.textViewAverageGrowth);

        heightCount = 0;
        weightCount = 0;
        avgHeight = 0;
        avgWeight = 0;
        weights = new ArrayList<>();
        heights = new ArrayList<>();
        getCountsAndAverages();

        listview = (ListView) findViewById(R.id.listViewGrowthHistory);
        if (weightCount == 0) {
            findViewById(R.id.textViewNoWeightRecorded).setVisibility(View.VISIBLE);
            findViewById(R.id.textViewNoHeightRecorded).setVisibility(View.GONE);
        } else {
            findViewById(R.id.textViewNoWeightRecorded).setVisibility(View.GONE);
            findViewById(R.id.textViewNoHeightRecorded).setVisibility(View.GONE);
            // Only create a graph if there are at least three points to plot
            if (weightCount >= 3) {
                createWeightGraph();
            } else {
                findViewById(R.id.textViewNoGraphShown).setVisibility(View.VISIBLE);
                findViewById(R.id.graphViewWeightHistory).setVisibility(View.GONE);
                findViewById(R.id.graphViewHeightHistory).setVisibility(View.GONE);
            }
        }

        weightAdapter = new GrowthHistoryWeightAdapter(getApplicationContext(), calf.getPhysicalHistory());
        listview.setAdapter(weightAdapter);

        heightButton = (Button) findViewById(R.id.buttonHeight);
        weightButton = (Button) findViewById(R.id.buttonWeight);

        // Background tint only works on 15 & up.
        // This should probably be changed to Background Color but that overrides styles and I'm lazy
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            weightButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorMedGrey));
            weightButton.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        }
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
        if (weightCount == 0) {
            findViewById(R.id.textViewNoWeightRecorded).setVisibility(View.VISIBLE);
            findViewById(R.id.textViewNoHeightRecorded).setVisibility(View.GONE);
            findViewById(R.id.graphViewHeightHistory).setVisibility(View.GONE);
            findViewById(R.id.graphViewWeightHistory).setVisibility(View.GONE);
            findViewById(R.id.textViewNoGraphShown).setVisibility(View.GONE);
            averageGrowth.setVisibility(View.GONE);
        } else {
            findViewById(R.id.textViewNoWeightRecorded).setVisibility(View.GONE);
            findViewById(R.id.textViewNoHeightRecorded).setVisibility(View.GONE);
            // Only create a graph if there are at least three points to plot
            if (weightCount >= 3) {
                createWeightGraph();
            } else {
                findViewById(R.id.textViewNoGraphShown).setVisibility(View.VISIBLE);
                findViewById(R.id.graphViewWeightHistory).setVisibility(View.GONE);
                findViewById(R.id.graphViewHeightHistory).setVisibility(View.GONE);
                averageGrowth.setVisibility(View.GONE);
            }
        }
        weightAdapter = new GrowthHistoryWeightAdapter(getApplicationContext(),calf.getPhysicalHistory());
        listview.setAdapter(weightAdapter);

        // Background tint only works on 15 & up.
        // This should probably be changed to Background Color but that overrides styles and I'm lazy
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            weightButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorMedGrey));
            weightButton.setTextColor(ContextCompat.getColor(this, android.R.color.white));
            heightButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorLightGrey));
            heightButton.setTextColor(ContextCompat.getColor(this, android.R.color.black));
        }
    }

    public void onClickHeightButton(View view) {
        if (heightCount == 0) {
            findViewById(R.id.textViewNoHeightRecorded).setVisibility(View.VISIBLE);
            findViewById(R.id.textViewNoWeightRecorded).setVisibility(View.GONE);
            findViewById(R.id.graphViewHeightHistory).setVisibility(View.GONE);
            findViewById(R.id.graphViewWeightHistory).setVisibility(View.GONE);
            findViewById(R.id.textViewNoGraphShown).setVisibility(View.GONE);
            averageGrowth.setVisibility(View.GONE);
        } else {
            findViewById(R.id.textViewNoWeightRecorded).setVisibility(View.GONE);
            findViewById(R.id.textViewNoHeightRecorded).setVisibility(View.GONE);
            // Only create a graph if there are at least three points to plot, otherwise don't show the graphs
            if (heightCount >= 3) {
                createHeightGraph();
            } else {
                findViewById(R.id.textViewNoGraphShown).setVisibility(View.VISIBLE);
                findViewById(R.id.graphViewWeightHistory).setVisibility(View.GONE);
                findViewById(R.id.graphViewHeightHistory).setVisibility(View.GONE);
                averageGrowth.setVisibility(View.GONE);
            }
        }
        heightAdapter = new GrowthHistoryHeightAdapter(getApplicationContext(),calf.getPhysicalHistory());
        listview.setAdapter(heightAdapter);

        // Background tint only works on 15 & up.
        // This should probably be changed to Background Color but that overrides styles and I'm lazy
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heightButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorMedGrey));
            heightButton.setTextColor(ContextCompat.getColor(this, android.R.color.white));
            weightButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorLightGrey));
            weightButton.setTextColor(ContextCompat.getColor(this, android.R.color.black));
        }
    }

    public void getCountsAndAverages() {
        for (int i = 0; i < calf.getPhysicalHistory().size(); i++) {
            if (calf.getPhysicalHistory().get(i).getWeight() != -1) {
                weights.add(calf.getPhysicalHistory().get(i));
            }
            if (calf.getPhysicalHistory().get(i).getHeight() != -1) {
                heights.add(calf.getPhysicalHistory().get(i));
            }
        }
        weightCount = weights.size();
        heightCount = heights.size();
        if (weights.size() >= 3) {
            double weightDaysBetween = (double) calendarDaysBetween(weights.get(0).makeDateRecorded(), weights.get(weights.size()-1).makeDateRecorded());
            double weightDiff = weights.get(weights.size()-1).getWeight() - weights.get(0).getWeight();
            avgWeight = weightDiff/weightDaysBetween;
            // Only show up to two decimal places
            avgWeight = Math.floor(avgWeight * 100) / 100;
        }
        if (heights.size() >= 3) {
            double heightDaysBetween = (double) calendarDaysBetween(heights.get(0).makeDateRecorded(), heights.get(heights.size()-1).makeDateRecorded());
            double heightDiff = heights.get(heights.size()-1).getHeight() - heights.get(0).getHeight();
            avgHeight = heightDiff/heightDaysBetween;
            // Only show up to two decimal places
            avgHeight = Math.floor(avgHeight * 100) / 100;
        }
    }

    public void createWeightGraph() {
        findViewById(R.id.textViewNoGraphShown).setVisibility(View.GONE);
        findViewById(R.id.graphViewHeightHistory).setVisibility(View.INVISIBLE);
        findViewById(R.id.graphViewWeightHistory).setVisibility(View.VISIBLE);
        averageGrowth.setVisibility(View.VISIBLE);
        averageGrowth.setText("Average Weight Gain: " + avgWeight + " lbs/day");
        GraphView graph = (GraphView) findViewById(R.id.graphViewWeightHistory);
        graph.removeAllSeries();
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();
        for (int i = 0; i < calf.getPhysicalHistory().size(); i++) {
            if (calf.getPhysicalHistory().get(i).getWeight() != -1) {
                Date tempDate = calf.getPhysicalHistory().get(i).makeDateRecorded().getTime();
                series.appendData(new DataPoint(tempDate, calf.getPhysicalHistory().get(i).getWeight()), true, calf.getPhysicalHistory().size());
            }
        }
        // Draw individual data points
        series.setDrawDataPoints(true);
        graph.addSeries(series);
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));

        // Set the number of horizontal labels to 4 unless there are only 3 recordings to avoid duplicate labels
        if (weightCount == 3) {
            graph.getGridLabelRenderer().setNumHorizontalLabels(3);
        } else {
            graph.getGridLabelRenderer().setNumHorizontalLabels(4);
        }
        // set manual x bounds to have nice steps
        graph.getViewport().setMinX(series.getLowestValueX());
        graph.getViewport().setMaxX(series.getHighestValueX());
        // set manual y bounds
        graph.getViewport().setMinY(series.getLowestValueY());
        graph.getViewport().setMaxY(series.getHighestValueY());
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setYAxisBoundsManual(true);
        // as we use dates as labels, the human rounding to nice readable numbers is not necessary
        graph.getGridLabelRenderer().setHumanRounding(false);
    }

    public void createHeightGraph() {
        findViewById(R.id.textViewNoGraphShown).setVisibility(View.GONE);
        findViewById(R.id.graphViewWeightHistory).setVisibility(View.INVISIBLE);
        findViewById(R.id.graphViewHeightHistory).setVisibility(View.VISIBLE);
        averageGrowth.setVisibility(View.VISIBLE);
        averageGrowth.setText("Average Height Gain: " + avgHeight + " in/day");
        GraphView graph = (GraphView) findViewById(R.id.graphViewHeightHistory);
        graph.removeAllSeries();
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();
        for (int i = 0; i < calf.getPhysicalHistory().size(); i++) {
            if (calf.getPhysicalHistory().get(i).getHeight() != -1) {
                Date tempDate = calf.getPhysicalHistory().get(i).makeDateRecorded().getTime();
                series.appendData(new DataPoint(tempDate, calf.getPhysicalHistory().get(i).getHeight()), true, calf.getPhysicalHistory().size());
            }
        }
        // Draw individual data points
        series.setDrawDataPoints(true);
        graph.addSeries(series);
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
        // Set the number of horizontal labels to 4 unless there are only 3 recordings to avoid duplicate labels
        if (heightCount == 3) {
            graph.getGridLabelRenderer().setNumHorizontalLabels(3);
        } else {
            graph.getGridLabelRenderer().setNumHorizontalLabels(4);
        }
        // set manual x bounds to have nice steps
        graph.getViewport().setMinX(series.getLowestValueX());
        graph.getViewport().setMaxX(series.getHighestValueX());
        // set manual y bounds
        graph.getViewport().setMinY(series.getLowestValueY());
        graph.getViewport().setMaxY(series.getHighestValueY());
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setYAxisBoundsManual(true);
        // as we use dates as labels, the human rounding to nice readable numbers is not necessary
        graph.getGridLabelRenderer().setHumanRounding(false);
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

    private static long calendarDaysBetween(Calendar firstDay, Calendar lastDay) {
        // Create copies so we don't update the original calendars.
        Calendar start = Calendar.getInstance();
        start.setTimeZone(firstDay.getTimeZone());
        start.setTimeInMillis(firstDay.getTimeInMillis());

        Calendar end = Calendar.getInstance();
        end.setTimeZone(lastDay.getTimeZone());
        end.setTimeInMillis(lastDay.getTimeInMillis());

        // Set the copies to be at midnight, but keep the day information.
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MILLISECOND, 0);

        end.set(Calendar.HOUR_OF_DAY, 0);
        end.set(Calendar.MINUTE, 0);
        end.set(Calendar.SECOND, 0);
        end.set(Calendar.MILLISECOND, 0);

        // At this point, each calendar is set to midnight on
        // their respective days. Now use TimeUnit.MILLISECONDS to
        // compute the number of full days between the two of them.
        return TimeUnit.MILLISECONDS.toDays(
                end.getTimeInMillis() - start.getTimeInMillis());
    }
}
