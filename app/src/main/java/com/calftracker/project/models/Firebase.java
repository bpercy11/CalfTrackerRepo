package com.calftracker.project.models;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.FeatureInfo;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.Semaphore;

import android.util.Log;

import com.calftracker.project.activities.SettingsEditEmployeesActivity;
import com.calftracker.project.models.Calf;


import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Calf;
import com.calftracker.project.models.Farm;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.firebase.database.DatabaseReference;

import org.json.JSONException;
import org.json.JSONObject;



/**
 * Created by BrendonLapp on 11/19/17.
 */



public class Firebase extends Application{

    private SharedPreferences.Editor editor;
    private String firebaseTime = "";
    private String prefTime = "";
    private ArrayList<Employee> rObj = null;

    private DatabaseReference mDatabase;

    public Firebase(){}

    public void compareToFirebase(){

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        mDatabase = FirebaseDatabase.getInstance().getReference();


        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                firebaseTime = dataSnapshot.child("LastEditTime").getValue().toString();

                SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_MULTI_PROCESS);
                prefTime = mPreferences.getString("LastEditTime", "0");


                if(prefTime.compareTo(firebaseTime) < 0){
                    //Firebase is older than prefs. Copy prefs to firebase
                    prefsToFirebase();

                }else if(prefTime.compareTo(firebaseTime) > 0) {
                    //Prefs older than firebase. Copy firebase to prefs
                    firebaseToPrefs();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Err LastEditTime", "ERR");
                firebaseTime = "0";
            }
        });


    }

    private void firebaseToPrefs(){

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> map =  (Map<String,Object>) dataSnapshot.getValue();

                SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_MULTI_PROCESS);
                SharedPreferences.Editor editor = mPreferences.edit();

                for(String key : map.keySet()){

                    editor.putString(key, map.get(key).toString());
                    editor.apply();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    public void prefsToFirebase(){

        final SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_MULTI_PROCESS);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        Log.d("In", "change");

        Map<String, ?> allEntries = mPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            mDatabase.child(entry.getKey()).setValue(entry.getValue().toString());
        }

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("COMPARISON F2P", Integer.toString(mPreferences.getAll().toString().compareTo(dataSnapshot.getValue().toString())));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void saveToFirebase(String str, Object obj){

    }

    public void saveData(String id, String json){


        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = mPreferences.edit();

        //SharedPreferences.Editor = editor = mPreferences.edit();

        //editor.putString(id, json);
        //editor.apply();

        Long time = Calendar.getInstance().getTimeInMillis();
        Gson gson = new Gson();
        String json2 = gson.toJson(time.toString());

        editor.putString("LastEditTime", json2);
        editor.apply();
        prefTime = json2;


        //Get time and set that
        //TODO Push changes to Firebase

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(id).setValue(json);
        mDatabase.child("LastEditTime").setValue(json2);

    }

    public SharedPreferences getPreferences() {


    return getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);

        //return getApplicationContext().getSharedPreferences("ClafTracker", Activity.MODE_PRIVATE);
        //return null;
    }


    public void calfListStuff(ArrayList<Calf> calfList){

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("CalfListTest").setValue(calfList);

        Map<String, String> calfMap = new HashMap<>();
        //Map<String, Calf> calfMap = new HashMap<>();

        for(int i = 0; i < calfList.size(); i++){
            if(calfList.get(i).isActive()){
                calfMap.put(Integer.toString(i), calfList.get(i).getFarmId());
            }

        }

        mDatabase.child("CalfIdList").setValue(calfMap);
    }


    public interface OnGetDataListener {
        //this is for callbacks
        void onSuccess(DataSnapshot dataSnapshot);
        void onStart();
        void onFailure();
    }

    public void readData(DatabaseReference ref, final OnGetDataListener listener) {
        listener.onStart();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailure();
            }
        });

    }


    public void readFirebase(String var){

        final String v = var;

        Log.d("before on data change", "before on data change");

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase = mDatabase.child("EmployeeList");

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Map<String, Object> map =  (Map<String,Object>) dataSnapshot.getValue();

                GenericTypeIndicator<ArrayList<Employee>> t = new GenericTypeIndicator<ArrayList<Employee>>() {};

                ArrayList<Employee> e = dataSnapshot.getValue(t);

                Log.d("In on data change", "in on data change");

                Log.d("data1" , e.toString());
                //Log.d("data1" , e.getName());

                //ArrayList<Employee> = (ArrayList<Employee>) map.get("EmployeeList");


                //returnObj(e);

                Log.d("data1" , "after return");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    public void saveData(String str, Object o){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(str).setValue(o);
    }

    public void setDataChangeListeners(){

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase = mDatabase.child("CalfList");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                GenericTypeIndicator<ArrayList<Calf>> t = new GenericTypeIndicator<ArrayList<Calf>>() {};
                ArrayList<Calf> calfList = dataSnapshot.getValue(t);

                SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                Gson gson = new Gson();
                String json = gson.toJson(calfList);
                prefsEditor.putString("CalfList",json);
                prefsEditor.apply();



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }



}
