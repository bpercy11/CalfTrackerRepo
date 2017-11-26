package com.calftracker.project.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.calftracker.project.calftracker.R;

import java.util.Locale;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_settings, frameLayout);
        mNavigationView.getMenu().findItem(R.id.nav_settings).setChecked(true);

        // Custom title
        getSupportActionBar().setTitle(R.string.settings_title);

        Button editFarmButton = (Button) findViewById(R.id.settingsFarmButton);
        Button editEmployeesButton = (Button) findViewById(R.id.settingsEmployeeButton);
        Button englishButton = (Button) findViewById(R.id.settingsEnglishButton);
        Button spanishButton = (Button) findViewById(R.id.settingsSpanishButton);

        editFarmButton.setOnClickListener(new View.OnClickListener(){
            Intent intent;
            public void onClick(View v){
                intent = new Intent(SettingsActivity.this, SettingsEditFarmActivity.class);
                startActivity(intent);

            }
        });

        editEmployeesButton.setOnClickListener(new View.OnClickListener(){
            Intent intent;
            public void onClick(View v){
                intent = new Intent(SettingsActivity.this, SettingsEditEmployeesActivity.class);
                startActivity(intent);

            }
        });

        englishButton.setOnClickListener(new View.OnClickListener(){
            Intent intent;
            public void onClick(View v){

                setLocale("en");
            }
        });

        spanishButton.setOnClickListener(new View.OnClickListener(){
            Intent intent;
            public void onClick(View v){
                setLocale("es");
            }
        });




        //SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        //Map<String, ?> allEntries = mPrefs.getAll();
        //for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            //Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
         //   mDatabase.child("child").setValue(entry.getKey() + ": " + entry.getValue().toString());
        //}
        //ArrayList<Calf> calfList;
        //String json;
        //Gson gson = new Gson();
        //json = mPrefs.getString("CalfList", "");
        //calfList = gson.fromJson(json, new TypeToken<ArrayList<Calf>>() {}.getType());
        //mDatabase.child("Root").child("key").setValue("test");


        //mDatabaseRead = FirebaseDatabase.getInstance().getReference().child("Farm Name");


        //mInfoView = (TextView) findViewById(R.id.info_view);

        //mFirebaseButton = (Button) findViewById(R.id.firebase_btn);
        //mFirebaseReadButton = (Button) findViewById(R.id.firebase_read_btn);


        //mFarmName = (EditText) findViewById(R.id.farmNameText);
        //mFarmOwner = (EditText) findViewById(R.id.farmOwnerText);
        //mFarmLocation = (EditText) findViewById(R.id.farmLocationText);

    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, SettingsActivity.class);
        startActivity(refresh);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,DashboardActivity.class);
        startActivity(intent);
    }
}
