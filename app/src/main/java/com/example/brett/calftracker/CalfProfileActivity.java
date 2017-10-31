package com.example.brett.calftracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Calendar;

public class CalfProfileActivity extends AppCompatActivity {

    private TextView mIDValue;
    private TextView mGenderValue;
    private TextView mDOBValue;
    private TextView mSireValue;
    private TextView mDamValue;
    private TextView mWeightValue;
    private TextView mHeightValue;
    private TextView mEditID;

    private Calf calf;
    private Calf tempCalf;
    private String tempID;

    private AlertDialog alert;

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
        int year = calf.getDateOfBirth().get(Calendar.YEAR);
        int month = calf.getDateOfBirth().get(Calendar.MONTH);
        int day = calf.getDateOfBirth().get(Calendar.DAY_OF_MONTH);
        mDOBValue.setText(month + "/" + day + "/" + year);

        tempID = mIDValue.getText().toString();
    }

    public void clickEditButton(View view) {
        mEditID = (TextView) findViewById(R.id.textViewIDValue);
        findViewById(R.id.floatingActionButtonEDIT).setVisibility(View.INVISIBLE);
        findViewById(R.id.buttonCancel).setVisibility(View.VISIBLE);
        findViewById(R.id.buttonApply).setVisibility(View.VISIBLE);
        findViewById(R.id.buttonFeedingHistory).setVisibility(View.INVISIBLE);
        findViewById(R.id.buttonGrowthHistory).setVisibility(View.INVISIBLE);
        findViewById(R.id.buttonMedicalHistory).setVisibility(View.INVISIBLE);
        tempCalf = calf;
        mIDValue.setBackgroundColor(Color.RED);
        mGenderValue.setBackgroundColor(Color.RED);
        mDOBValue.setBackgroundColor(Color.RED);
        mSireValue.setBackgroundColor(Color.RED);
        mDamValue.setBackgroundColor(Color.RED);
        mHeightValue.setBackgroundColor(Color.RED);
        mWeightValue.setBackgroundColor(Color.RED);
        mEditID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.show();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setHint("ID Number");
        builder.setTitle("Enter new ID");
        builder.setView(input);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mIDValue.setText(input.getText().toString());
                tempCalf.setFarmId(Integer.parseInt(input.getText().toString()));
            }
        });

        alert = builder.create();
    }

    public void clickApplyButton(View view) {
        calf = tempCalf;
        findViewById(R.id.buttonApply).setVisibility(View.INVISIBLE);
        findViewById(R.id.buttonCancel).setVisibility(View.INVISIBLE);
        findViewById(R.id.floatingActionButtonEDIT).setVisibility(View.VISIBLE);

        tempID = mIDValue.getText().toString();

        mIDValue.setBackgroundColor(Color.TRANSPARENT);
        mGenderValue.setBackgroundColor(Color.TRANSPARENT);
        mDOBValue.setBackgroundColor(Color.TRANSPARENT);
        mSireValue.setBackgroundColor(Color.TRANSPARENT);
        mDamValue.setBackgroundColor(Color.TRANSPARENT);
        mHeightValue.setBackgroundColor(Color.TRANSPARENT);
        mWeightValue.setBackgroundColor(Color.TRANSPARENT);

        SharedPreferences mPrefs = getSharedPreferences("test", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(calf);
        prefsEditor.putString("newCalf",json);
        prefsEditor.apply();
    }
    public void clickCancelButton(View view) {
        findViewById(R.id.buttonApply).setVisibility(View.INVISIBLE);
        findViewById(R.id.buttonCancel).setVisibility(View.INVISIBLE);
        findViewById(R.id.floatingActionButtonEDIT).setVisibility(View.VISIBLE);
        mIDValue.setBackgroundColor(Color.TRANSPARENT);
        mGenderValue.setBackgroundColor(Color.TRANSPARENT);
        mDOBValue.setBackgroundColor(Color.TRANSPARENT);
        mSireValue.setBackgroundColor(Color.TRANSPARENT);
        mDamValue.setBackgroundColor(Color.TRANSPARENT);
        mHeightValue.setBackgroundColor(Color.TRANSPARENT);
        mWeightValue.setBackgroundColor(Color.TRANSPARENT);

        mIDValue.setText(tempID);
    }
}
