package com.calftracker.project.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.calftracker.project.calftracker.R;

public class SettingsEditFarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_edit_farm);

        Button editFieldsButton = (Button) findViewById(R.id.EditFarmEditFieldsBtn);
        Button saveFieldsButton = (Button) findViewById(R.id.EditFarmSaveFieldsBtn);

        TextView farmName = (TextView) findViewById(R.id.settingsFarmNameText);
        TextView farmOwner = (TextView) findViewById(R.id.settingsFarmOwnerText);
        TextView farmLocation = (TextView) findViewById(R.id.settingsFarmLocationText);

        // not currently working
        SharedPreferences prefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        Editor editor = prefs.edit();
        editor.putString("farmName", "testings");
        String name = prefs.getString("farmName", "null");

        farmName.setText(name);
        //farmName.setText("hello");

    }
}
