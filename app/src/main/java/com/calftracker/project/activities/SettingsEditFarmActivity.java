package com.calftracker.project.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.method.KeyListener;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.calftracker.project.calftracker.R;
import com.google.gson.Gson;

public class SettingsEditFarmActivity extends AppCompatActivity {


    private TextView farmName;
    private TextView farmOwner;
    private TextView farmLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_edit_farm);

        // Stylize action bar to use back button and custom title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Farm Information");

        FloatingActionButton editFieldsButton = (FloatingActionButton) findViewById(R.id.EditFarmEditFieldsBtn);

        //populateFields();
        farmName = (TextView) findViewById(R.id.settingsFarmNameText);
        farmOwner = (TextView) findViewById(R.id.settingsFarmOwnerText);
        farmLocation = (TextView) findViewById(R.id.settingsFarmLocationText);
        KeyListener mKeyListener = farmName.getKeyListener();
        farmName.setKeyListener(null);
        mKeyListener = farmOwner.getKeyListener();
        farmOwner.setKeyListener(null);
        mKeyListener = farmLocation.getKeyListener();
        farmLocation.setKeyListener(null);


        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        Gson gson = new Gson();
        //String json;
        //json = mPreferences.getString("farmName","");

        //Populates the fields entered when creating farm on first use
        if(mPreferences.contains("farmName")){
            String name = mPreferences.getString("farmName","Farm Name not set");
            int end = name.length();
            name = name.substring(1, end-1);
            farmName.setHint(name);
        }
        if(mPreferences.contains("farmOwner")){
            String name = mPreferences.getString("farmOwner","Farm Owner not set");
            int end = name.length();
            name = name.substring(1, end-1);
            farmOwner.setHint(name);
        }
        if(mPreferences.contains("farmLocation")){
            String name = mPreferences.getString("farmLocation","Farm Location not set");
            int end = name.length();
            name = name.substring(1, end-1);
            farmLocation.setHint(name);
        }

        editFieldsButton.setOnClickListener(new View.OnClickListener(){
            Intent intent;
            public void onClick(View v){
                intent = new Intent(SettingsEditFarmActivity.this, SettingsChangeFarmInfoActivity.class);
                startActivity(intent);
            }
        });


    }

    /**
     * function to populate fields entered when farm is created
     */
    private void populateFields(){

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,SettingsActivity.class);
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(intent);
        return true;
    }
}
