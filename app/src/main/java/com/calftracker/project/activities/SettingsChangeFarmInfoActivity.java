package com.calftracker.project.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Farm;
import com.calftracker.project.models.Firebase;
import com.google.firebase.database.FirebaseDatabase;
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

        //farmNameChangeText
        farmName = (TextView) findViewById(R.id.farmNameChangeText);
        farmOwner = (TextView) findViewById(R.id.farmOwnerChangeText);
        farmLocation = (TextView) findViewById(R.id.farmLocationChangeText);

        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        String json;
        if(mPreferences.contains("Farm")) {
            json = mPreferences.getString("Farm", "");
            Gson gson = new Gson();
            farm = gson.fromJson(json, new TypeToken<Farm>() {
            }.getType());

            farmName.setHint(farm.getName());
            farmOwner.setHint(farm.getOwner());
            farmLocation.setHint(farm.getLocation());
        }



        saveFieldsButton.setOnClickListener(new View.OnClickListener(){
            Intent intent;
            public void onClick(View v){

                boolean requirementsMet = true;

                if(farmName.getText().toString().equals("")){
                    requirementsMet = false;
                }
                if(farmOwner.getText().toString().equals("")) {
                    requirementsMet = false;
                }
                if(farmLocation.getText().toString().equals("")) {
                    requirementsMet = false;
                }


                if(requirementsMet) {
                    saveData();
                    back();
                }

            }
        });


    }

    public void saveData(){
        SharedPreferences sharedPref = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String json;

        farm = new Farm(farmName.getText().toString(), farmOwner.getText().toString(), farmLocation.getText().toString());
        json = gson.toJson(farm);
        editor.putString("Farm",json);
        editor.apply();

        json = gson.toJson(farmName.getText().toString());
        editor.putString("farmName", json);
        editor.apply();

        json = gson.toJson(farmOwner.getText().toString());
        editor.putString("farmOwner", json);
        editor.apply();

        json = gson.toJson(farmLocation.getText().toString());
        editor.putString("farmLocation", json);
        editor.apply();


        Firebase fb = (Firebase) getApplicationContext();
        fb.saveData("Farm", farm);
        fb.saveData("farmName", farmName.getText().toString());
        fb.saveData("farmOwner", farmOwner.getText().toString());
        fb.saveData("farmLocation", farmLocation.getText().toString());

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,SettingsEditFarmActivity.class);
        startActivity(intent);
    }

    public void back(){
        Intent intent;
        intent = new Intent(SettingsChangeFarmInfoActivity.this, SettingsEditFarmActivity.class);
        startActivity(intent);
    }


}
