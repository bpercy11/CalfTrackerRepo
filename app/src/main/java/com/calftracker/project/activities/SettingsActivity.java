package com.calftracker.project.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.calftracker.project.calftracker.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

public class SettingsActivity extends BaseActivity implements View.OnClickListener {

    private GoogleApiClient mGoogleApiClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_settings, frameLayout);
        mNavigationView.getMenu().findItem(R.id.nav_settings).setChecked(true);

        // Custom title
        getSupportActionBar().setTitle(R.string.settings_title);

        // initialize the 5 buttons in settings screen
        Button editFarmButton = (Button) findViewById(R.id.settingsFarmButton);
        Button editEmployeesButton = (Button) findViewById(R.id.settingsEmployeeButton);
        Button englishButton = (Button) findViewById(R.id.settingsEnglishButton);
        Button spanishButton = (Button) findViewById(R.id.settingsSpanishButton);
        Button signOutButton = (Button) findViewById(R.id.SignOutButton);

        // set onclick listeners for the buttons
        // upon a click, action is performed in onClick() method
        editFarmButton.setOnClickListener(this);
        editEmployeesButton.setOnClickListener(this);
        englishButton.setOnClickListener(this);
        spanishButton.setOnClickListener(this);
        signOutButton.setOnClickListener(this);

        // set up for the sign out activity
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();

    }

    /**
     * method using switch statement to act on which button is pressed
     * @param v
     */
    public void onClick(View v){
        Intent intent;
        switch(v.getId()) {
            case R.id.settingsFarmButton:
                intent = new Intent(SettingsActivity.this, SettingsEditFarmActivity.class);
                startActivity(intent);
                break;
            case R.id.settingsEmployeeButton:
                intent = new Intent(SettingsActivity.this, SettingsEditEmployeesActivity.class);
                startActivity(intent);
                break;
            case R.id.settingsEnglishButton:
                setLocale("en");
                break;
            case R.id.settingsSpanishButton:
                setLocale("es");
                break;
            case R.id.SignOutButton:
                FirebaseAuth.getInstance().signOut();
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                // ...
                                Toast.makeText(getApplicationContext(), "User Logged Out", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), SplashScreenActivity.class);
                                startActivity(intent);
                            }
                        });
                break;

        }
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
