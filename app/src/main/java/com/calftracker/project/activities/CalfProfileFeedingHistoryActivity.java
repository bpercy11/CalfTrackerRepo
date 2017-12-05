package com.calftracker.project.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.calftracker.project.adapters.calfprofile.FeedingHistoryEmployeeSpinnerAdapter;
import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Calf;
import com.calftracker.project.models.Employee;
import com.calftracker.project.models.Feeding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class CalfProfileFeedingHistoryActivity extends AppCompatActivity {

    private Calf calf;
    private String calfID;
    private ArrayList<Calf> calfList;
    private ArrayList<Employee> employeeArrayList;

    TextView mFirstFeeder;
    TextView mFirstMethod;
    TextView mFirstLiters;

    TextView mSecondFeeder;
    TextView mSecondMethod;
    TextView mSecondLiters;

    Button mFirstFeedingButton;
    Button mSecondFeedingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calf_profile_feeding_history);

        // Stylize action bar to use back button and custom title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Feeding History");

        mFirstFeedingButton = (Button) findViewById(R.id.buttonFirstFeeding);
        mSecondFeedingButton = (Button) findViewById(R.id.buttonSecondFeeding);

        mFirstFeeder = (TextView) findViewById(R.id.textViewFirstFeederValue);
        mFirstMethod = (TextView) findViewById(R.id.textViewFirstMethodValue);
        mFirstLiters = (TextView) findViewById(R.id.textViewFirstLitersValue);

        mSecondFeeder = (TextView) findViewById(R.id.textViewSecondFeederValue);
        mSecondMethod = (TextView) findViewById(R.id.textViewSecondMethodValue);
        mSecondLiters = (TextView) findViewById(R.id.textViewSecondLitersValue);

        retrieveData();

        // Search through the calfList to find the correct calf by ID
        for (int i = 0; i < calfList.size(); i++) {
            if (calfList.get(i).getFarmId().equals(calfID)) {
                calf = calfList.get(i);
                break;
            }
        }

        if(calf.getFeedingHistory()[0] != null) {
            Feeding feeding1 = calf.getFeedingHistory()[0];
            mFirstFeeder.setText(feeding1.getFedBy().getName());
            mFirstMethod.setText(feeding1.getMethodOfFeeding());
            mFirstLiters.setText(feeding1.getLitersFed() + " L");
            mFirstFeedingButton.setText(R.string.calf_profile_feeding_history_first_button_data);
        }

        if(calf.getFeedingHistory()[1] != null) {
            Feeding feeding2 = calf.getFeedingHistory()[1];
            mSecondFeeder.setText(feeding2.getFedBy().getName());
            mSecondMethod.setText(feeding2.getMethodOfFeeding());
            mSecondLiters.setText(feeding2.getLitersFed() + " L");
            mSecondFeedingButton.setText(R.string.calf_profile_feeding_history_second_button_data);
        }



        mFirstFeedingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(CalfProfileFeedingHistoryActivity.this);
                // Get the layout inflater
                LayoutInflater inflater = CalfProfileFeedingHistoryActivity.this.getLayoutInflater();

                final View dialogView = inflater.inflate(R.layout.calf_profile_feeding_history_dialog, null);

                final Spinner mEmployeeSpinner = (Spinner) dialogView.findViewById(R.id.spinnerFeederDialog);

                FeedingHistoryEmployeeSpinnerAdapter adapter = new FeedingHistoryEmployeeSpinnerAdapter(employeeArrayList, getApplicationContext());

                mEmployeeSpinner.setAdapter(adapter);

                final Spinner mMethodSpinner = (Spinner) dialogView.findViewById(R.id.spinnerMethodDialog);

                // Create an ArrayAdapter using the string array and a default spinner layout
                ArrayAdapter<CharSequence> methodAdapter = ArrayAdapter.createFromResource(getApplicationContext(),
                        R.array.methods_of_administration, android.R.layout.simple_spinner_item);
                // Specify the layout to use when the list of choices appears
                methodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // Apply the adapter to the spinner
                mMethodSpinner.setAdapter(methodAdapter);

                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                builder.setView(dialogView)
                        // Add action buttons
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                EditText mLiters = (EditText) dialogView.findViewById(R.id.editTextLitersDialog);
                                String ed_text = mLiters.getText().toString().trim();

                                if(ed_text.isEmpty() || ed_text.length() == 0 || ed_text.equals("") || ed_text == null) {
                                    Context context = getApplicationContext();
                                    CharSequence text = "Must set amount fed";
                                    int duration = Toast.LENGTH_SHORT;
                                    Toast toast = Toast.makeText(context, text, duration);
                                    toast.show();
                                    return;
                                }
                                Employee fedBy = (Employee) mEmployeeSpinner.getSelectedItem();
                                String method = (String) mMethodSpinner.getSelectedItem();
                                if(calf.getFeedingHistory()[0] == null) {

                                    Double liters = Double.parseDouble(mLiters.getText().toString());

                                    calf.getFeedingHistory()[0] = new Feeding(fedBy, method, liters);
                                } else {
                                    calf.getFeedingHistory()[0].setMethodOfFeeding(method);
                                    calf.getFeedingHistory()[0].setFedBy(fedBy);
                                    calf.getFeedingHistory()[0].setLitersFed(Double.parseDouble(mLiters.getText().toString()));
                                }

                                mFirstFeedingButton.setText("Edit First Feeding");

                                mFirstFeeder.setText(calf.getFeedingHistory()[0].getFedBy().getName());
                                mFirstMethod.setText(calf.getFeedingHistory()[0].getMethodOfFeeding());
                                mFirstLiters.setText(calf.getFeedingHistory()[0].getLitersFed() + " L");

                                saveData();
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



        mSecondFeedingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CalfProfileFeedingHistoryActivity.this);
                // Get the layout inflater
                LayoutInflater inflater = CalfProfileFeedingHistoryActivity.this.getLayoutInflater();

                final View dialogView = inflater.inflate(R.layout.calf_profile_feeding_history_dialog, null);

                final Spinner mEmployeeSpinner = (Spinner) dialogView.findViewById(R.id.spinnerFeederDialog);

                FeedingHistoryEmployeeSpinnerAdapter adapter = new FeedingHistoryEmployeeSpinnerAdapter(employeeArrayList, getApplicationContext());

                mEmployeeSpinner.setAdapter(adapter);

                final Spinner mMethodSpinner = (Spinner) dialogView.findViewById(R.id.spinnerMethodDialog);

                // Create an ArrayAdapter using the string array and a default spinner layout
                ArrayAdapter<CharSequence> methodAdapter = ArrayAdapter.createFromResource(getApplicationContext(),
                        R.array.methods_of_administration, android.R.layout.simple_spinner_item);
                // Specify the layout to use when the list of choices appears
                methodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // Apply the adapter to the spinner
                mMethodSpinner.setAdapter(methodAdapter);

                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                builder.setView(dialogView)
                        // Add action buttons
                        .setPositiveButton("Set Feeding", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                EditText mLiters = (EditText) dialogView.findViewById(R.id.editTextLitersDialog);
                                String ed_text = mLiters.getText().toString().trim();

                                if(ed_text.isEmpty() || ed_text.length() == 0 || ed_text.equals("") || ed_text == null) {
                                    Context context = getApplicationContext();
                                    CharSequence text = "Must set amount fed";
                                    int duration = Toast.LENGTH_SHORT;
                                    Toast toast = Toast.makeText(context, text, duration);
                                    toast.show();
                                    return;
                                }
                                Employee fedBy = (Employee) mEmployeeSpinner.getSelectedItem();
                                String method = (String) mMethodSpinner.getSelectedItem();
                                if(calf.getFeedingHistory()[1] == null) {

                                    Double liters = Double.parseDouble(mLiters.getText().toString());

                                    calf.getFeedingHistory()[1] = new Feeding(fedBy, method, liters);
                                } else {
                                    calf.getFeedingHistory()[1].setMethodOfFeeding(method);
                                    calf.getFeedingHistory()[1].setFedBy(fedBy);
                                    calf.getFeedingHistory()[1].setLitersFed(Double.parseDouble(mLiters.getText().toString()));
                                }

                                mSecondFeedingButton.setText("Edit Second Feeding");

                                mSecondFeeder.setText(calf.getFeedingHistory()[1].getFedBy().getName());
                                mSecondMethod.setText(calf.getFeedingHistory()[1].getMethodOfFeeding());
                                mSecondLiters.setText(calf.getFeedingHistory()[1].getLitersFed() + " L");

                                saveData();
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

    // TODO
    public void saveData() {
        // Save the calfList to local storage
        SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(calfList);
        prefsEditor.putString("CalfList",json);
        prefsEditor.apply();
    }

    public void retrieveData() {
        // try and get calf object made by main activity
        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();

        Gson gson = new Gson();
        String json = mPreferences.getString("CalfList","");
        calfList = gson.fromJson(json, new TypeToken<ArrayList<Calf>>(){}.getType());

        json = mPreferences.getString("calfToViewInProfile","");
        calfID = gson.fromJson(json, String.class);

        if(mPreferences.contains("EmployeeList")) {
            json = mPreferences.getString("EmployeeList", "");
            employeeArrayList = gson.fromJson(json, new TypeToken<ArrayList<Employee>>() {
            }.getType());
        } else { employeeArrayList = new ArrayList<Employee>(); }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, CalfProfileActivity.class);
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(getApplicationContext(), CalfProfileActivity.class);
        startActivity(intent);
        return true;
    }
}
