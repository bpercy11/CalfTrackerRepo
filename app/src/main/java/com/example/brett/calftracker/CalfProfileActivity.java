package com.example.brett.calftracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CalfProfileActivity extends AppCompatActivity {

    private TextView mIDValue;
    private TextView mGenderValue;
    private TextView mDOBValue;
    private TextView mSireValue;
    private TextView mDamValue;
    private TextView mWeightValue;
    private TextView mHeightValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calf_profile);

        fillTextViewValues();
    }

    private void fillTextViewValues() {
        mIDValue = (TextView) findViewById(R.id.textViewIDValue);
        mGenderValue = (TextView) findViewById(R.id.textViewGenderValue);
        mDOBValue = (TextView) findViewById(R.id.textViewDOBValue);
        mSireValue = (TextView) findViewById(R.id.textViewSireValue);
        mDamValue = (TextView) findViewById(R.id.textViewDamValue);
        mWeightValue = (TextView) findViewById(R.id.textViewWeightValue);
        mHeightValue = (TextView) findViewById(R.id.textViewHeightValue);

        // TEXTVIEW.SETTEXT FOR EACH TEXTVIEW, USING DATA FROM CALF MODEL
        // PROBABLY OBTAINED FROM SQL DATABASE
    }
}
