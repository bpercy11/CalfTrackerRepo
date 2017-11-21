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
import android.util.Log;
import com.calftracker.project.models.Calf;


import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Calf;
import com.calftracker.project.models.Farm;
import com.calftracker.project.models.TestClass;
import com.calftracker.project.models.TestClass2;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

public class Firebase extends Application {

    //private SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);;
    private SharedPreferences.Editor editor;
    private static Context c;
    private String firebaseTime = "";
    private String prefTime = "";

    private DatabaseReference mDatabase;

//    public Firebase(Context c){
//
//        SharedPreferences mPreferences = c.getSharedPreferences("ClafTracker", Activity.MODE_PRIVATE);
//        editor = mPreferences.edit();
//    }


    public Firebase(){}

    public void compareToFirebase(){
        //Compare the Time Last Edited saved in shared preferences to that in Firebase
        //If they are different. Copy the newest one over the Old verstion

        //SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_MULTI_PROCESS);
        //prefTime = mPreferences.getString("LastEditTime", "0");

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Log.d("LAST", "SLEEp");
        SystemClock.sleep(10000);


        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                firebaseTime = dataSnapshot.child("LastEditTime").getValue().toString();

                SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_MULTI_PROCESS);
                prefTime = mPreferences.getString("LastEditTime", "0");

                Log.d("IN LAST EDIT TIME", firebaseTime);
                Log.d("prefTime", prefTime);
                Log.d("firebaseTime", firebaseTime);
                Log.d("prefTime.compareTo()", Integer.toString(prefTime.compareTo(firebaseTime)));

                if(prefTime.compareTo(firebaseTime) < 0){
                    //Firebase is older than prefs. Copy prefs to firebase
                    Log.d("Less THAN 0", "1");

                    prefsToFirebase();

                }else if(prefTime.compareTo(firebaseTime) > 0) {
                    //Prefs older than firebase. Copy firebase to prefs
                    Log.d("Greater THAN 0", "-1");

                    //firebaseToPrefs();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Err LastEditTime", "ERR");
                firebaseTime = "0";
            }
        });

        //mDatabase.child("usedApp").setValue("false");
        //mDatabase.child("usedApp").setValue("true");

        Log.d("prefTime", prefTime);
        Log.d("firebaseTime", firebaseTime);

        Log.d("prefTime.compareTo()", Integer.toString(prefTime.compareTo(firebaseTime)));

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

    private void prefsToFirebase(){

        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_MULTI_PROCESS);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        Map<String, ?> allEntries = mPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            mDatabase.child(entry.getKey()).setValue(entry.getValue().toString());
        }


//            try {
//                Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
//
//                JSONObject testJSON = new JSONObject(entry.getKey().toString());
//                //mDatabase.child(entry.getKey()).setValue(testJSON);
//                Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
//
//            }catch (JSONException e){
//                Log.d("map values", "ERROR ERRROR ERROR");
//
//            }
//            //testJSON.(entry.getKey(), entry.getValue().toString());
//        }

    }

    public void editSharedPreferences(String id, String json){
        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = mPreferences.edit();

        //SharedPreferences.Editor = editor = mPreferences.edit();

        editor.putString(id, json);
        editor.apply();

        Long time = Calendar.getInstance().getTimeInMillis();
        Gson gson = new Gson();
        String json2 = gson.toJson(time.toString());

        editor.putString("LastEditTime", json2);
        editor.apply();
        prefTime = json2;
        Log.d("UPDATE PREFS", json2);
        Log.d("LastEditTime", mPreferences.getString("LastEditTime", "0"));

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







}
