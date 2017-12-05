package com.calftracker.project.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class SettingsEditFarmActivity extends AppCompatActivity {


    private TextView farmName;
    private TextView farmOwner;
    private TextView farmLocation;
    private String temp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_edit_farm);


        // Stylize action bar to use back button and custom title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Edit Farm");

        Button editFieldsButton = (Button) findViewById(R.id.EditFarmEditFieldsBtn);

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
        String json;
        json = mPreferences.getString("farmName","");

        /*
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.child("farmName").exists()) {
                    temp = (String) snapshot.child("farmName").getValue();
                    farmName.setHint(temp);
                } else {
                    farmName.setHint("");
                }

                if (snapshot.child("farmOwner").exists()) {
                    temp = (String) snapshot.child("farmOwner").getValue();
                    farmOwner.setHint(temp);
                } else {
                    farmOwner.setHint("");
                }

                if (snapshot.child("farmLocation").exists()) {
                    temp = (String) snapshot.child("farmLocation").getValue();
                    farmLocation.setHint(temp);
                } else {
                    farmLocation.setHint("");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        */

        //Populates the fields entered when creating farm on first use
        if(mPreferences.contains("farmName")){
            String name = mPreferences.getString("farmName","Farm Name not set");
            int end = name.length();
            name = name.substring(1, end-1);

            Firebase fb = (Firebase) getApplicationContext();
            fb.saveData("farmName", name);

            farmName.setHint(name);
        }
        if(mPreferences.contains("farmOwner")){
            String name = mPreferences.getString("farmOwner","Farm Owner not set");
            int end = name.length();
            name = name.substring(1, end-1);

            Firebase fb = (Firebase) getApplicationContext();
            fb.saveData("farmOwner", name);
            farmOwner.setHint(name);
        }
        if(mPreferences.contains("farmLocation")){
            String name = mPreferences.getString("farmLocation","Farm Location not set");
            int end = name.length();
            name = name.substring(1, end-1);

            Firebase fb = (Firebase) getApplicationContext();
            fb.saveData("farmLocation", name);
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
