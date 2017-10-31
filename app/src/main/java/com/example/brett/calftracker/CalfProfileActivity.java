package com.example.brett.calftracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

import com.google.gson.Gson;

import java.util.Calendar;

public class CalfProfileActivity extends AppCompatActivity {

    private TextView mIDValue;
    private TextView mGenderValue;
    private TextView mDOBValue;
    private TextView mSireValue;
    private TextView mDamValue;
    private TextView mWeightValue;
    private TextView mHeightValue;

    private Dialog mGenderListDialog;
    private String[] gender = {"Male","Female"};
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private ListView mNoteListView;

    private Calf calf;

    private Calf tempCalf;
    private String tempID;
    private String tempSire;
    private String tempDam;
    private String tempWeight;
    private String tempHeight;

    private AlertDialog alertID;
    private AlertDialog alertGender;
    private AlertDialog alertSire;
    private AlertDialog alertDam;
    private AlertDialog alertWeight;
    private AlertDialog alertHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calf_profile);


        // try and get calf object made by main activity
        SharedPreferences mPreferences = getSharedPreferences("test", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();

        Gson gson = new Gson();
        String json = mPreferences.getString("newCalf","");
        calf = gson.fromJson(json, Calf.class);
        tempCalf = gson.fromJson(json, Calf.class);


        // print to log calf cbject information
        mIDValue = (TextView) findViewById(R.id.textViewIDValue);
        mGenderValue = (TextView) findViewById(R.id.textViewGenderValue);
        mDOBValue = (TextView) findViewById(R.id.textViewDOBValue);
        mSireValue = (TextView) findViewById(R.id.textViewSireValue);
        mDamValue = (TextView) findViewById(R.id.textViewDamValue);
        mWeightValue = (TextView) findViewById(R.id.textViewWeightValue);
        mHeightValue = (TextView) findViewById(R.id.textViewHeightValue);

        int farmID = calf.getFarmId();
        mIDValue.setText(Integer.toString(farmID));
        mGenderValue.setText(calf.getGender());
        int year = calf.getDateOfBirth().get(Calendar.YEAR);
        int month = calf.getDateOfBirth().get(Calendar.MONTH) + 1;
        int day = calf.getDateOfBirth().get(Calendar.DAY_OF_MONTH);
        mDOBValue.setText(month + "/" + day + "/" + year);

        tempID = mIDValue.getText().toString();
        tempSire = mSireValue.getText().toString();
        tempDam = mDamValue.getText().toString();
        tempWeight = mWeightValue.getText().toString();
        tempHeight = mHeightValue.getText().toString();

        // SET UP NOTE LISTVIEW
        updateNoteListView();

        mNoteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog noteContentPopup;

                AlertDialog.Builder builder = new AlertDialog.Builder(CalfProfileActivity.this);
                final TextView output = new TextView(CalfProfileActivity.this);
                output.setText(calf.getNoteNdx(position).getMessage());
                output.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

                Calendar noteDate = calf.getNoteNdx(position).getDateEntered();
                int year = calf.getDateOfBirth().get(Calendar.YEAR);
                int month = calf.getDateOfBirth().get(Calendar.MONTH) + 1;
                int day = calf.getDateOfBirth().get(Calendar.DAY_OF_MONTH);
                builder.setTitle("Note entered " + month + "/" + day + "/" + year);
                builder.setView(output);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

                noteContentPopup = builder.create();

                noteContentPopup.show();
            }
        });
    }

    public void updateNoteListView() {
        ArrayList<String> allNoteDates = new ArrayList<String>();
        for(int i = 0; i < calf.getNotesSize(); i++) {
            Calendar noteDate = calf.getNoteNdx(i).getDateEntered();
            int year = calf.getDateOfBirth().get(Calendar.YEAR);
            int month = calf.getDateOfBirth().get(Calendar.MONTH) + 1;
            int day = calf.getDateOfBirth().get(Calendar.DAY_OF_MONTH);
            allNoteDates.add(month + "/" + day + "/" + year);
        }

        if(calf.getNotesSize() == 0)
            allNoteDates.add("No Notes!");

        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, allNoteDates);
        mNoteListView = (ListView) findViewById(R.id.listViewNotes);
        mNoteListView.setAdapter(itemsAdapter);

    }

    public void clickEditButton(View view) {
        findViewById(R.id.floatingActionButtonEDIT).setVisibility(View.INVISIBLE);
        findViewById(R.id.buttonCancel).setVisibility(View.VISIBLE);
        findViewById(R.id.buttonApply).setVisibility(View.VISIBLE);
        findViewById(R.id.buttonFeedingHistory).setVisibility(View.INVISIBLE);
        findViewById(R.id.buttonGrowthHistory).setVisibility(View.INVISIBLE);
        findViewById(R.id.buttonMedicalHistory).setVisibility(View.INVISIBLE);

        mIDValue.setBackgroundColor(Color.RED);
        mGenderValue.setBackgroundColor(Color.RED);
        mDOBValue.setBackgroundColor(Color.RED);
        mSireValue.setBackgroundColor(Color.RED);
        mDamValue.setBackgroundColor(Color.RED);
        mHeightValue.setBackgroundColor(Color.RED);
        mWeightValue.setBackgroundColor(Color.RED);

        // Edit ID Number
        // TODO: Figure out a better way to do this AlertDialog...
        mIDValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertID.show();
            }
        });
        AlertDialog.Builder builderID = new AlertDialog.Builder(this);
        final EditText inputID = new EditText(this);
        inputID.setInputType(InputType.TYPE_CLASS_NUMBER);
        inputID.setHint("ID Number");
        builderID.setTitle("Enter new ID");
        builderID.setView(inputID);
        builderID.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mIDValue.setText(inputID.getText().toString());
                tempCalf.setFarmId(Integer.parseInt(inputID.getText().toString()));
            }
        });
        builderID.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertID = builderID.create();

        // EDIT GENDERVALUE
        mGenderValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertGender.show();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Gender");
        builder.setItems(gender, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mGenderValue.setText(gender[i]);
                // TODO: SET TEMP CALF TO NEW GENDER
            }
        });
        alertGender = builder.create();

        // CREATE DIALOG WHEN USER CLICKS DOBVALUE
        mDOBValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        CalfProfileActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        // this happens when the user has selected a date in the dialog and presses "OK"
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = month + "/" + day + "/" + year;
                mDOBValue.setText(date);
                // TODO: SET CALF TO NEW DOB
            }
        };

        // Edit Sire ID Number
        mSireValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertSire.show();
            }
        });
        AlertDialog.Builder builderSire = new AlertDialog.Builder(this);
        final EditText inputSire = new EditText(this);
        inputSire.setInputType(InputType.TYPE_CLASS_NUMBER);
        inputSire.setHint("Sire ID Number");
        builderSire.setTitle("Enter Sire ID");
        builderSire.setView(inputSire);
        builderSire.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mSireValue.setText(inputSire.getText().toString());
                tempCalf.setSire(inputSire.getText().toString());
            }
        });
        builderSire.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertSire = builderSire.create();

        // Edit Dam ID Number
        mDamValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDam.show();
            }
        });
        AlertDialog.Builder builderDam = new AlertDialog.Builder(this);
        final EditText inputDam = new EditText(this);
        inputDam.setInputType(InputType.TYPE_CLASS_NUMBER);
        inputDam.setHint("Dam ID Number");
        builderDam.setTitle("Enter Dam ID");
        builderDam.setView(inputDam);
        builderDam.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mDamValue.setText(inputDam.getText().toString());
                tempCalf.setDam(inputDam.getText().toString());
            }
        });
        builderDam.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDam = builderDam.create();

        // Edit Weight
        mWeightValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertWeight.show();
            }
        });
        AlertDialog.Builder builderWeight = new AlertDialog.Builder(this);
        final EditText inputWeight = new EditText(this);
        inputWeight.setInputType(InputType.TYPE_CLASS_NUMBER);
        inputWeight.setHint("Weight");
        builderWeight.setTitle("Enter calf weight");
        builderWeight.setView(inputWeight);
        builderWeight.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mWeightValue.setText(inputWeight.getText().toString());
                //tempCalf.setWeight();
            }
        });
        builderWeight.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertWeight = builderWeight.create();

        // Edit Height
        mHeightValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertHeight.show();
            }
        });
        AlertDialog.Builder builderHeight = new AlertDialog.Builder(this);
        final EditText inputHeight = new EditText(this);
        inputHeight.setInputType(InputType.TYPE_CLASS_NUMBER);
        inputHeight.setHint("Height");
        builderHeight.setTitle("Enter calf height");
        builderHeight.setView(inputHeight);
        builderHeight.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mHeightValue.setText(inputHeight.getText().toString());
                //tempCalf.setHeight();
            }
        });
        builderHeight.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertHeight = builderHeight.create();
    }

    public void clickApplyButton(View view) {
        calf = tempCalf;
        findViewById(R.id.buttonApply).setVisibility(View.INVISIBLE);
        findViewById(R.id.buttonCancel).setVisibility(View.INVISIBLE);
        findViewById(R.id.floatingActionButtonEDIT).setVisibility(View.VISIBLE);
        findViewById(R.id.buttonFeedingHistory).setVisibility(View.VISIBLE);
        findViewById(R.id.buttonGrowthHistory).setVisibility(View.VISIBLE);
        findViewById(R.id.buttonMedicalHistory).setVisibility(View.VISIBLE);

        mIDValue.setOnClickListener(null);
        mSireValue.setOnClickListener(null);
        mDamValue.setOnClickListener(null);
        mHeightValue.setOnClickListener(null);
        mWeightValue.setOnClickListener(null);

        tempID = mIDValue.getText().toString();
        tempSire = mSireValue.getText().toString();
        tempDam = mDamValue.getText().toString();
        tempWeight = mWeightValue.getText().toString();
        tempHeight = mHeightValue.getText().toString();

        mIDValue.setBackgroundColor(Color.TRANSPARENT);
        mGenderValue.setBackgroundColor(Color.TRANSPARENT);
        mDOBValue.setBackgroundColor(Color.TRANSPARENT);
        mSireValue.setBackgroundColor(Color.TRANSPARENT);
        mDamValue.setBackgroundColor(Color.TRANSPARENT);
        mHeightValue.setBackgroundColor(Color.TRANSPARENT);
        mWeightValue.setBackgroundColor(Color.TRANSPARENT);

        SharedPreferences mPrefs = getSharedPreferences("test", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(tempCalf);
        prefsEditor.putString("newCalf",json);
        prefsEditor.apply();
    }

    public void clickCancelButton(View view) {
        findViewById(R.id.buttonApply).setVisibility(View.INVISIBLE);
        findViewById(R.id.buttonCancel).setVisibility(View.INVISIBLE);
        findViewById(R.id.floatingActionButtonEDIT).setVisibility(View.VISIBLE);
        findViewById(R.id.buttonFeedingHistory).setVisibility(View.VISIBLE);
        findViewById(R.id.buttonGrowthHistory).setVisibility(View.VISIBLE);
        findViewById(R.id.buttonMedicalHistory).setVisibility(View.VISIBLE);

        mIDValue.setOnClickListener(null);
        mSireValue.setOnClickListener(null);
        mDamValue.setOnClickListener(null);
        mHeightValue.setOnClickListener(null);
        mWeightValue.setOnClickListener(null);

        mIDValue.setBackgroundColor(Color.TRANSPARENT);
        mGenderValue.setBackgroundColor(Color.TRANSPARENT);
        mDOBValue.setBackgroundColor(Color.TRANSPARENT);
        mSireValue.setBackgroundColor(Color.TRANSPARENT);
        mDamValue.setBackgroundColor(Color.TRANSPARENT);
        mHeightValue.setBackgroundColor(Color.TRANSPARENT);
        mWeightValue.setBackgroundColor(Color.TRANSPARENT);

        mIDValue.setText(tempID);
        mSireValue.setText(tempSire);
        mDamValue.setText(tempDam);
        mWeightValue.setText(tempWeight);
        mHeightValue.setText(tempHeight);

        // WHAT JT DID
        mGenderValue.setText(calf.getGender());

        int year = calf.getDateOfBirth().get(Calendar.YEAR);
        int month = calf.getDateOfBirth().get(Calendar.MONTH) + 1;
        int day = calf.getDateOfBirth().get(Calendar.DAY_OF_MONTH);
        mDOBValue.setText(month + "/" + day + "/" + year);
    }

    public void clickNewNoteButton(View view) {
        AlertDialog newNoteAlert;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setHint("Enter Note Here");
        builder.setTitle("Create a New Note");
        builder.setView(input);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String noteMessage = input.getText().toString();
                Calendar noteDate = Calendar.getInstance();

                Note newNote = new Note(noteMessage,noteDate);

                calf.addNote(newNote);

                SharedPreferences mPrefs = getSharedPreferences("test", Activity.MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                Gson gson = new Gson();
                String json = gson.toJson(calf);
                prefsEditor.putString("newCalf",json);
                prefsEditor.apply();

                updateNoteListView();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        newNoteAlert = builder.create();

        newNoteAlert.show();
    }
}
