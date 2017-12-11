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
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class InsightsActivity extends BaseActivity {

    public static ArrayList<Calf> calfList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_insights, frameLayout);
        mNavigationView.getMenu().findItem(R.id.nav_insights).setChecked(true);

        // Custom title
        getSupportActionBar().setTitle(R.string.insights_title);

        // Load in the calf list
        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        if(mPreferences.contains("CalfList")) {
            Gson gson = new Gson();
            String json = mPreferences.getString("CalfList", "");
            calfList = gson.fromJson(json, new TypeToken<ArrayList<Calf>>() {
            }.getType());
        }

        getGenderData();
        getHealthData();
        getDOBData();
    }

    public void getGenderData() {
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

    public void getHealthData() {
        // Load in the calf list
        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        if(mPreferences.contains("CalfList")) {
            Gson gson = new Gson();
            String json = mPreferences.getString("CalfList", "");
            calfList = gson.fromJson(json, new TypeToken<ArrayList<Calf>>() {
            }.getType());
        }

        float observeCount = 0;
        float healthyCount = 0;

        if (calfList != null) {
            for (int i = 0; i < calfList.size(); i++) {
                if (calfList.get(i).isNeedToObserveForIllness()) {
                    observeCount++;
                }
            }
            healthyCount = calfList.size() - observeCount;
        }

        float observePercent = (observeCount/(observeCount + healthyCount)) * 100;
        float healthyPercent = (healthyCount/(observeCount + healthyCount)) * 100;

        PieChart healthyChart = (PieChart) findViewById(R.id.healthChart);
        healthyChart.setDrawHoleEnabled(false);
        healthyChart.getDescription().setEnabled(false);
        healthyChart.setTouchEnabled(false);
        healthyChart.setDrawEntryLabels(false);
        healthyChart.setExtraOffsets(0,0,0,-10);

        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(observePercent, "Need to Observe"));
        entries.add(new PieEntry(healthyPercent, "Healthy"));

        PieDataSet dataSet = new PieDataSet(entries, "");
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(getResources().getColor(R.color.colorObserveOrange));
        colors.add(getResources().getColor(R.color.colorHealthyGreen));
        dataSet.setColors(colors);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(14f);
        dataSet.setValueFormatter(new PercentFormatter());

        PieData data = new PieData(dataSet);
        healthyChart.setData(data);

        Legend legend = healthyChart.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setTextSize(16f);
        legend.setXEntrySpace(24f);
    }

    public void getDOBData() {
        // Get dates in the last week
        Calendar date1 = Calendar.getInstance();
        date1.add(Calendar.DAY_OF_YEAR, -6);
        int year1 = date1.get(Calendar.YEAR);
        int month1 = date1.get(Calendar.MONTH) + 1;
        int day1 = date1.get(Calendar.DATE);

        Calendar date2 = Calendar.getInstance();
        date2.add(Calendar.DAY_OF_YEAR, -5);
        int year2 = date2.get(Calendar.YEAR);
        int month2 = date2.get(Calendar.MONTH) + 1;
        int day2 = date2.get(Calendar.DATE);

        Calendar date3 = Calendar.getInstance();
        date3.add(Calendar.DAY_OF_YEAR, -4);
        int year3 = date3.get(Calendar.YEAR);
        int month3 = date3.get(Calendar.MONTH) + 1;
        int day3 = date3.get(Calendar.DATE);

        Calendar date4 = Calendar.getInstance();
        date4.add(Calendar.DAY_OF_YEAR, -3);
        int year4 = date4.get(Calendar.YEAR);
        int month4 = date4.get(Calendar.MONTH) + 1;
        int day4 = date4.get(Calendar.DATE);

        Calendar date5 = Calendar.getInstance();
        date5.add(Calendar.DAY_OF_YEAR, -2);
        int year5 = date5.get(Calendar.YEAR);
        int month5 = date5.get(Calendar.MONTH) + 1;
        int day5 = date5.get(Calendar.DATE);

        Calendar date6 = Calendar.getInstance();
        date6.add(Calendar.DAY_OF_YEAR, -1);
        int year6 = date6.get(Calendar.YEAR);
        int month6 = date6.get(Calendar.MONTH) + 1;
        int day6 = date6.get(Calendar.DATE);

        Calendar date7 = Calendar.getInstance();
        int year7 = date7.get(Calendar.YEAR);
        int month7 = date7.get(Calendar.MONTH) + 1;
        int day7 = date7.get(Calendar.DATE);

        String monthDay1 = month1 + "/" + day1;
        String monthDay2 = month2 + "/" + day2;
        String monthDay3 = month3 + "/" + day3;
        String monthDay4 = month4 + "/" + day4;
        String monthDay5 = month5 + "/" + day5;
        String monthDay6 = month6 + "/" + day6;
        String monthDay7 = month7 + "/" + day7;

        int countDay1 = 0;
        int countDay2 = 0;
        int countDay3 = 0;
        int countDay4 = 0;
        int countDay5 = 0;
        int countDay6 = 0;
        int countDay7 = 0;

        if (calfList != null) {
            for (int i = 0; i < calfList.size(); i++) {
                int calfYear = calfList.get(i).getCalfyear();
                int calfMonth = calfList.get(i).getCalfmonth() + 1;
                int calfDay = calfList.get(i).getCalfday();

                if (calfYear == year1 && calfMonth == month1 && calfDay == day1) {
                    countDay1++;
                }
                else if (calfYear == year2 && calfMonth == month2 && calfDay == day2) {
                    countDay2++;
                }
                else if (calfYear == year3 && calfMonth == month3 && calfDay == day3) {
                    countDay3++;
                }
                else if (calfYear == year4 && calfMonth == month4 && calfDay == day4) {
                    countDay4++;
                }
                else if (calfYear == year5 && calfMonth == month5 && calfDay == day5) {
                    countDay5++;
                }
                else if (calfYear == year6 && calfMonth == month6 && calfDay == day6) {
                    countDay6++;
                }
                else if (calfYear == year7 && calfMonth == month7 && calfDay == day7) {
                    countDay7++;
                }
            }
        }

        ArrayList<Integer> counts = new ArrayList<>();
        counts.add(countDay1);
        counts.add(countDay2);
        counts.add(countDay3);
        counts.add(countDay4);
        counts.add(countDay5);
        counts.add(countDay6);
        counts.add(countDay7);

        int maxCount = 0;
        for (int i = 0; i < counts.size(); i++) {
            if (counts.get(i) > maxCount) {
                maxCount = counts.get(i);
            }
        }

        final HashMap<Integer, String> numMap = new HashMap<>();
        numMap.put(1, monthDay1);
        numMap.put(2, monthDay2);
        numMap.put(3, monthDay3);
        numMap.put(4, monthDay4);
        numMap.put(5, monthDay5);
        numMap.put(6, monthDay6);
        numMap.put(7, monthDay7);

        LineChart dobChart = (LineChart) findViewById(R.id.dobChart);
        dobChart.getDescription().setEnabled(false);
        dobChart.setTouchEnabled(false);
        dobChart.setExtraOffsets(0,-10,0,10);

        XAxis xAxis = dobChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setYOffset(10);
        xAxis.setAxisMinimum(1);
        xAxis.setAxisMaximum(7);
        xAxis.setTextSize(10f);
        xAxis.setValueFormatter(new DefaultAxisValueFormatter(0) {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return numMap.get((int)value);
            }
        });

        dobChart.getAxisRight().setEnabled(false);
        YAxis yAxis = dobChart.getAxisLeft();
        yAxis.setGranularity(1f);
        yAxis.setAxisMinimum(0);
        yAxis.setAxisMaximum(maxCount);
        yAxis.setTextSize(10f);
        yAxis.setValueFormatter(new DefaultAxisValueFormatter(0) {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return "" + ((int) value);
            }
        });

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(1, countDay1));
        entries.add(new Entry(2, countDay2));
        entries.add(new Entry(3, countDay3));
        entries.add(new Entry(4, countDay4));
        entries.add(new Entry(5, countDay5));
        entries.add(new Entry(6, countDay6));
        entries.add(new Entry(7, countDay7));

        LineDataSet dataSet = new LineDataSet(entries, "");
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(getResources().getColor(R.color.colorLinePurple));
        dataSet.setColors(colors);
        dataSet.setCircleColor(getResources().getColor(R.color.colorLinePurple));
        dataSet.setCircleRadius(4);
        dataSet.setDrawValues(false);
        dataSet.setLineWidth(2);

        LineData data = new LineData(dataSet);
        dobChart.setData(data);

        Legend legend = dobChart.getLegend();
        legend.setEnabled(false);
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
