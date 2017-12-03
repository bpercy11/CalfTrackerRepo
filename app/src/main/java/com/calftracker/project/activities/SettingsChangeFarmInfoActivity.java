package com.calftracker.project.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Farm;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class SettingsChangeFarmInfoActivity extends AppCompatActivity {

    private TextView farmName;
    private TextView farmOwner;
    private TextView farmLocation;
    private Farm farm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_change_farm_info);
        Button saveFieldsButton = (Button) findViewById(R.id.EditFarmSaveFieldsBtn);

        getSupportActionBar().setTitle("Edit Farm Information");

        //farmNameChangeText
        farmName = (TextView) findViewById(R.id.farmNameChangeText);
        farmOwner = (TextView) findViewById(R.id.farmOwnerChangeText);
        farmLocation = (TextView) findViewById(R.id.farmLocationChangeText);

        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);

        //Load clicked Employee
        if(mPreferences.contains("Farm")) {
            SharedPreferences.Editor editor = mPreferences.edit();

            Gson gson = new Gson();
            String json = mPreferences.getString("Farm", "");
            farm = gson.fromJson(json, new TypeToken<Farm>() {
            }.getType());
        }

        farmName.setText(farm.getName());
        farmOwner.setText(farm.getOwner());
        farmLocation.setText(farm.getLocation());

        saveFieldsButton.setOnClickListener(new View.OnClickListener(){
            Intent intent;
            public void onClick(View v){

                boolean requirementsNotMet = false;

                if(farmName.getText().toString().equals("")){
                    requirementsNotMet = true;
                }
                if(farmOwner.getText().toString().equals("")) {
                    requirementsNotMet = true;
                }
                if(farmLocation.getText().toString().equals("")) {
                    requirementsNotMet = true;
                }

                if(!requirementsNotMet) {
                    farm.setName(farmName.getText().toString());
                    farm.setOwner(farmOwner.getText().toString());
                    farm.setLocation(farmLocation.getText().toString());

                    SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor prefsEditor = mPrefs.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(farm);
                    prefsEditor.putString("Farm",json);
                    prefsEditor.apply();

                    back();
                } else {
                    Toast.makeText(SettingsChangeFarmInfoActivity.this, "Please fill any empty fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, SettingsEditFarmActivity.class);
        startActivity(intent);
    }

    public void back(){
        Intent intent = new Intent(this, SettingsEditFarmActivity.class);
        startActivity(intent);
    }

    public void clickCancelFarmButton(View view){
        Intent intent = new Intent(this, SettingsEditFarmActivity.class);
        startActivity(intent);
    }
}
