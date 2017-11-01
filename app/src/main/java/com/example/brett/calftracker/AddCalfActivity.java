package com.example.brett.calftracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class AddCalfActivity extends AppCompatActivity {

    private static final String TAG = "AddCalfActivity";

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private TextView mGender;
    private Dialog mGenderListDialog;
    private String[] gender = {"Male","Female"};
    private AlertDialog alert;

    private int calfYear;
    private int calfMonth;
    private int calfDay;

    private String calfGender;

    private ArrayList<Calf> calfList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_calf);

        calfList = new ArrayList<Calf>();

        // Load the calfList
//        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
//        SharedPreferences.Editor editor = mPreferences.edit();
//
//        Gson gson = new Gson();
//        String json = mPreferences.getString("CalfList","");
//        calfList = gson.fromJson(json, new TypeToken<ArrayList<Calf>>(){}.getType());

        // Sets the default date to be today in case this field is left blank
        Calendar today = Calendar.getInstance();
        calfYear = today.get(Calendar.YEAR);
        calfMonth = today.get(Calendar.MONTH);
        calfDay = today.get(Calendar.DATE);

        mDisplayDate = (TextView) findViewById(R.id.textViewDisplayDate);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddCalfActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        // this happens when the user has selected a date in the dialog and presses "OK"
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calfYear = year;
                calfMonth = month;
                calfDay = day;
                month = month + 1;
                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
            }
        };

        mGender = (TextView) findViewById(R.id.textViewSelectGender);

        mGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.show();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Gender");
        builder.setItems(gender, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mGender.setText(gender[i]);
                calfGender = gender[i];
            }
        });
        alert = builder.create();
    }

    public void clickAddCalfButton(View view) {
        // GET USER INPUT FOR ID NUMBER FROM EDITTEXT
        EditText ID = (EditText) findViewById(R.id.editTextGetID);
        String calfID = ID.getText().toString();
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        // Go here if the user does not enter a Calf ID
        if (calfID.matches("") || mGender.getText().toString().matches("")) {
            CharSequence text = "Please complete all fields";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        if (calfID.length() > 9 || calfID.length() < 1) {
            CharSequence text = "ID number must be between 1 and 9 digits";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        // Make new calf object and add it to the calfList
        Calendar calfCal = new GregorianCalendar(calfYear,calfMonth,calfDay);
        Calf calf = new Calf(calfID,calfGender, calfCal);
        calfList.add(calf);

        // Save the calfList to local storage
        SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(calfList);
        prefsEditor.putString("CalfList",json);
        prefsEditor.apply();

        prefsEditor = mPrefs.edit();
        json = gson.toJson(calf.getFarmId());
        prefsEditor.putString("calfToViewInProfile",json);
        prefsEditor.apply();

        // GO TO NEWLWY CREATED CALF PROFILE
        Intent intent = new Intent(this,CalfProfileActivity.class);
        startActivity(intent);
    }

    public void clickCancelButton(View view) {
        Intent intent = new Intent(this,DashboardActivity.class);
        startActivity(intent);
    }
}
