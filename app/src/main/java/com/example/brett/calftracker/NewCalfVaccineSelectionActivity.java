package com.example.brett.calftracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class NewCalfVaccineSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_calf_vaccine_selection);
    }

    public void onConfirmVaccines(View view) {
        Intent intent = new Intent(this,CalfProfileActivity.class);
        startActivity(intent);
    }
}
