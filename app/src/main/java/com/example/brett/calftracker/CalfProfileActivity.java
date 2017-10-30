package com.example.brett.calftracker;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;

public class CalfProfileActivity extends AppCompatActivity {

    private TextView mIDValue;
    private TextView mGenderValue;
    private TextView mDOBValue;
    private TextView mSireValue;
    private TextView mDamValue;
    private TextView mWeightValue;
    private TextView mHeightValue;

    private Calf calf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calf_profile);


        // try and get calf object made by main activity
        SharedPreferences mPreferences = getSharedPreferences("test", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();

        Gson gson = new Gson();
        String json = mPreferences.getString("newCalf","");
        calf = gson.fromJson(json, Calf.class);


        // print to log calf cbject information
        mIDValue = (TextView) findViewById(R.id.textViewIDValue);
        mGenderValue = (TextView) findViewById(R.id.textViewGenderValue);
        mDOBValue = (TextView) findViewById(R.id.textViewDOBValue);
        mSireValue = (TextView) findViewById(R.id.textViewSireValue);
        mDamValue = (TextView) findViewById(R.id.textViewDamValue);
        mWeightValue = (TextView) findViewById(R.id.textViewWeightValue);
        mHeightValue = (TextView) findViewById(R.id.textViewHeightValue);

        int farmID = calf.getFarmId();
        mIDValue.setText(Integer.toString(farmID));
        mGenderValue.setText(calf.getGender());
    }
}
