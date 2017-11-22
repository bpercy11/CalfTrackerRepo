package com.calftracker.project.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.calftracker.project.calftracker.R;

public class EditIllnessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocols_edit_illness);

        // Custom title
        getSupportActionBar().setTitle(R.string.protocols_vaccine_edit);

        // Stylize action bar to use back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, IllnessActivity.class);
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(getApplicationContext(), IllnessActivity.class);
        startActivity(intent);
        return true;
    }
}
