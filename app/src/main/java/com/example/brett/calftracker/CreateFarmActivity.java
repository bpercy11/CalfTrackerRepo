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
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.google.gson.Gson;

import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * Created by BrendonLapp on 10/31/17.
 */

public class CreateFarmActivity extends AppCompatActivity {


    private EditText farmName;
    private EditText farmLocation;
    private EditText farmOwner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_farm);

        Button doneButton = (Button) findViewById(R.id.Create_Farm_Btn);

        farmName = (EditText) findViewById(R.id.Farm_Name_txt);
        farmLocation = (EditText) findViewById(R.id.Farm_Location_txt);
        farmOwner = (EditText) findViewById(R.id.Farm_Owner_txt);


        doneButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){

                Farm farm = new Farm(farmName.toString(), farmOwner.toString(), farmLocation.toString());




            }
        });


    }

}
