package com.calftracker.project.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
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

public class SettingsActivity extends BaseActivity {

    private GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_settings, frameLayout);
        mNavigationView.getMenu().findItem(R.id.nav_settings).setChecked(true);

        // Custom title
        getSupportActionBar().setTitle(R.string.settings_title);

        Button editFarmButton = (Button) findViewById(R.id.settingsFarmButton);
        Button editEmployeesButton = (Button) findViewById(R.id.settingsEmployeeButton);
        final Button englishButton = (Button) findViewById(R.id.settingsEnglishButton);
        final Button spanishButton = (Button) findViewById(R.id.settingsSpanishButton);
        Button signOutButton = (Button) findViewById(R.id.SignOutButton);

        // set up for the sign out activity
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();


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
            public void onClick(View v){
                setLocale("en");

                // Background tint only works on 15 & up.
                // This should probably be changed to Background Color but that overrides styles and I'm lazy
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    spanishButton.setBackgroundTintList(ContextCompat.getColorStateList(SettingsActivity.this, R.color.colorLightGrey));
                    spanishButton.setTextColor(ContextCompat.getColor(SettingsActivity.this, android.R.color.black));
                    englishButton.setBackgroundTintList(ContextCompat.getColorStateList(SettingsActivity.this, R.color.colorMedGrey));
                    englishButton.setTextColor(ContextCompat.getColor(SettingsActivity.this, android.R.color.white));
                }
            }
        });

        spanishButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                setLocale("es");

                // Background tint only works on 15 & up.
                // This should probably be changed to Background Color but that overrides styles and I'm lazy
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    englishButton.setBackgroundTintList(ContextCompat.getColorStateList(SettingsActivity.this, R.color.colorLightGrey));
                    englishButton.setTextColor(ContextCompat.getColor(SettingsActivity.this, android.R.color.black));
                    spanishButton.setBackgroundTintList(ContextCompat.getColorStateList(SettingsActivity.this, R.color.colorMedGrey));
                    spanishButton.setTextColor(ContextCompat.getColor(SettingsActivity.this, android.R.color.white));
                }
            }
        });

        // sign out activity
        signOutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                // ...
                                Toast.makeText(getApplicationContext(), "User Logged Out", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), SplashScreenActivity.class);
                                startActivity(i);
                            }
                        });
            }
        });

        // Background tint only works on 15 & up.
        // This should probably be changed to Background Color but that overrides styles and I'm lazy
        // Also - on app open locale is not set, but by default this is English
        Locale myLocale = new Locale("es");
        if (getResources().getConfiguration().locale.equals(myLocale)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                spanishButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorMedGrey));
                spanishButton.setTextColor(ContextCompat.getColor(this, android.R.color.white));
            }
        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                englishButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorMedGrey));
                englishButton.setTextColor(ContextCompat.getColor(this, android.R.color.white));
            }
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
