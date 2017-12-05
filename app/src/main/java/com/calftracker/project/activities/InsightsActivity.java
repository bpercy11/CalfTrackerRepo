package com.calftracker.project.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Calf;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class InsightsActivity extends BaseActivity {

    public static ArrayList<Calf> calfList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_insights, frameLayout);
        mNavigationView.getMenu().findItem(R.id.nav_insights).setChecked(true);

        // Custom title
        getSupportActionBar().setTitle(R.string.insights_title);

        getGenderData();
    }

    public void getGenderData() {
        // Load in the calf list
        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        if(mPreferences.contains("CalfList")) {
            Gson gson = new Gson();
            String json = mPreferences.getString("CalfList", "");
            calfList = gson.fromJson(json, new TypeToken<ArrayList<Calf>>() {
            }.getType());
        }

        float femaleCount = 0;
        float maleCount = 0;

        if (calfList != null) {
            for (int i = 0; i < calfList.size(); i++) {
                if (calfList.get(i).getGender().equals("Female")) {
                    femaleCount++;
                }
                else if (calfList.get(i).getGender().equals("Male")){
                    maleCount++;
                }
            }
        }

        float femalePercent = (femaleCount / (maleCount + femaleCount)) * 100;
        float malePercent = (maleCount / (maleCount + femaleCount)) * 100;

        PieChart genderChart = (PieChart) findViewById(R.id.genderChart);
        genderChart.setDrawHoleEnabled(false);
        genderChart.getDescription().setEnabled(false);
        genderChart.setTouchEnabled(false);
        genderChart.setDrawEntryLabels(false);
        genderChart.setExtraOffsets(0,0,0,-10);

        ArrayList<PieEntry> entries = new ArrayList<>();
            entries.add(new PieEntry(femalePercent, "Female"));
            entries.add(new PieEntry(malePercent, "Male"));

        PieDataSet dataSet = new PieDataSet(entries, "");
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(getResources().getColor(R.color.colorFemalePink));
        colors.add(getResources().getColor(R.color.colorMaleBlue));
        dataSet.setColors(colors);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(14f);
        dataSet.setValueFormatter(new PercentFormatter());

        PieData data = new PieData(dataSet);
        genderChart.setData(data);

        Legend legend = genderChart.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setTextSize(16f);
        legend.setXEntrySpace(24f);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(this, DashboardActivity.class);
            startActivity(intent);
        }
    }
}
