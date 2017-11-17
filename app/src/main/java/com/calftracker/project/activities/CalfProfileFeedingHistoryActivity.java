package com.calftracker.project.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.calftracker.project.calftracker.R;

import java.util.Calendar;

public class CalfProfileFeedingHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calf_profile_feeding_history);

        Button mFirstFeedingButton = (Button) findViewById(R.id.buttonFirstFeeding);

        mFirstFeedingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CalfProfileFeedingHistoryActivity.this);
                // Get the layout inflater
                LayoutInflater inflater = CalfProfileFeedingHistoryActivity.this.getLayoutInflater();

                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                builder.setView(inflater.inflate(R.layout.calf_profile_feeding_history_dialog, null))
                        // Add action buttons
                        .setPositiveButton("Set Feeding", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                // sign in the user ...
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                builder.create().show();
            }
        });

        Button mSecondFeedingButton = (Button) findViewById(R.id.buttonSecondFeeding);

        mSecondFeedingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CalfProfileFeedingHistoryActivity.this);
                // Get the layout inflater
                LayoutInflater inflater = CalfProfileFeedingHistoryActivity.this.getLayoutInflater();

                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                builder.setView(inflater.inflate(R.layout.calf_profile_feeding_history_dialog, null))
                        // Add action buttons
                        .setPositiveButton("Set Feeding", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                // sign in the user ...
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                builder.create().show();
            }
        });
    }
}
