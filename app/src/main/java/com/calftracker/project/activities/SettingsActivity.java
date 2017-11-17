package com.calftracker.project.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import com.calftracker.project.calftracker.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button editFarmButton = (Button) findViewById(R.id.settingsFarmButton);
        Button editEmployeesButton = (Button) findViewById(R.id.settingsEmployeeButton);
        Button englishButton = (Button) findViewById(R.id.settingsEnglishButton);
        Button spanishButton = (Button) findViewById(R.id.settingsFarmButton);


    }
}
