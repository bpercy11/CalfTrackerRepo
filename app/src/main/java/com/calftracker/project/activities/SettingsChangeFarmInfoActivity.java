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

public class SettingsChangeFarmInfoActivity extends AppCompatActivity {

    private TextView farmName;
    private TextView farmOwner;
    private TextView farmLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_change_farm_info);
        Button saveFieldsButton = (Button) findViewById(R.id.EditFarmSaveFieldsBtn);

        //farmNameChangeText
        farmName = (TextView) findViewById(R.id.farmNameChangeText);
        farmOwner = (TextView) findViewById(R.id.farmOwnerChangeText);
        farmLocation = (TextView) findViewById(R.id.farmLocationChangeText);



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
                    /*SharedPreferences sharedPref = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    Gson gson = new Gson();
                    String json;

                    json = gson.toJson(farmName.getText().toString());
                    editor.putString("farmName", json);

                    json = gson.toJson(farmOwner.getText().toString());
                    editor.putString("farmOwner", json);

                    json = gson.toJson(farmLocation.getText().toString());
                    editor.putString("farmLocation", json);
                    editor.apply();*/

                    Firebase fb = (Firebase) getApplicationContext();
                    fb.saveData("farmName", farmName.getText().toString());
                    fb.saveData("farmOwner", farmOwner.getText().toString());
                    fb.saveData("farmLocation", farmLocation.getText().toString());

                    back();
                }




            }
        });


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
