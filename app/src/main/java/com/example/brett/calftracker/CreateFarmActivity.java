package com.example.brett.calftracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * Created by BrendonLapp on 10/31/17.
 */

public class CreateFarmActivity extends AppCompatActivity {


    private EditText farmName;
    private EditText farmLocation;
    private EditText farmOwner;

    private TextView farmNameRequired;
    private TextView farmLocationRequired;
    private TextView farmOwnerRequired;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_farm);

        Button doneButton = (Button) findViewById(R.id.Create_Farm_Btn);

        farmName = (EditText) findViewById(R.id.Farm_Name_txt);
        farmLocation = (EditText) findViewById(R.id.Farm_Location_txt);
        farmOwner = (EditText) findViewById(R.id.Farm_Owner_txt);

        farmNameRequired = (TextView) findViewById(R.id.FarmName_Required_tv);
        farmLocationRequired = (TextView) findViewById(R.id.FarmLocation_Required_tv);
        farmOwnerRequired = (TextView) findViewById(R.id.FarmOwner_Required_tv);


        doneButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){

                boolean requirementsNotMet = false;

                if(farmName.getText().toString().equals("")){
                    requirementsNotMet = true;
                    farmNameRequired.setVisibility(View.VISIBLE);
                }else{
                    farmNameRequired.setVisibility(View.INVISIBLE);
                }
                if(farmOwner.getText().toString().equals("")){
                    requirementsNotMet = true;
                    farmOwnerRequired.setVisibility(View.VISIBLE);
                }else{
                    farmOwnerRequired.setVisibility(View.INVISIBLE);
                }
                if(farmLocation.getText().toString().equals("")){
                    requirementsNotMet = true;
                    farmLocationRequired.setVisibility(View.VISIBLE);
                }else{
                    farmLocationRequired.setVisibility(View.INVISIBLE);
                }



                //dialogBox();

                if(!requirementsNotMet) {
                    Farm farm = new Farm(farmName.toString(), farmOwner.toString(), farmLocation.toString());
                    callDashboard();
               }



            }
        });


    }

    private void callDashboard() {

        Intent intent = new Intent(this, DashboardActivity.class);
        finish();
        startActivity(intent);

    }

    /* EXAMPLE Dialog Box code for later
    private void dialogBox(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title
        alertDialogBuilder.setTitle(farmLocation.getText().toString());

        // set dialog message
        alertDialogBuilder
                .setMessage(farmLocation.getText().toString())
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
                        // MainActivity.this.finish();
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }
    */

}
