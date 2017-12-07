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
import java.util.function.ToDoubleBiFunction;

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

    @Override
    public void onCreate() {
        super.onCreate();
    /* Enable disk persistence  */
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

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

        DatabaseReference mDatabse = FirebaseDatabase.getInstance().getReference();
        mDatabse.child(str).setValue(obj);

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

        //update last edit time w/ each save
        Long time = Calendar.getInstance().getTimeInMillis();
        Gson gson = new Gson();
        String json2 = gson.toJson(time.toString());
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("LastEditTime").setValue(json2);

    }

    /**
     * Method called at start of splashscreen
     * Used to turn on event listeners for different litsts stored in firebase
     * Upon a change to xList in Firebase, xValueEventListener will trigger and updated the xList
     *  in shared preferences
     */
    //TODO
    // update LastEditTime in shared pref each time an event listener fires
    // update LastEditTime in firebase each time we write to it
    // add LastEditTimes to each diff list stored in fb so we aren't pulling things we don't need to
    public void setDataChangeListeners(){

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        Log.d("DatabaseRef", mDatabase.toString());

        mDatabase.child("TestValueSet").setValue("testValue");
        Log.d("DatabaseRef", "testValue");

        mDatabase = mDatabase.getRoot();

        /////// CALF LIST DATABASE LISTENER ////////////////////////
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
            public void onCancelled(DatabaseError databaseError) { }
        });

        /////// EMPLOYEE LIST DATABASE LISTENER ////////////////////////
        mDatabase = FirebaseDatabase.getInstance().getReference(); //reset the reference to the root
        mDatabase = mDatabase.child("EmployeeList");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("EmployeeList", "OnDataChange");

                GenericTypeIndicator<ArrayList<Employee>> t = new GenericTypeIndicator<ArrayList<Employee>>() {};
                ArrayList<Employee> employeeList = dataSnapshot.getValue(t);

                SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                Gson gson = new Gson();
                String json = gson.toJson(employeeList);
                prefsEditor.putString("EmployeeList",json);
                prefsEditor.apply();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });

        /////// VACCINE LIST (in protocols) DATABASE LISTENER ////////////////////////
        mDatabase = FirebaseDatabase.getInstance().getReference(); //reset the reference to the root
        mDatabase = mDatabase.child("VaccineList");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<Vaccine>> t = new GenericTypeIndicator<List<Vaccine>>() {};
                List<Vaccine> vaccineList = dataSnapshot.getValue(t);

                SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                Gson gson = new Gson();
                String json = gson.toJson(vaccineList);
                prefsEditor.putString("VaccineList",json);
                prefsEditor.apply();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });

        /////// MEDICINE LIST (in protocols) DATABASE LISTENER ////////////////////////
        mDatabase = FirebaseDatabase.getInstance().getReference(); //reset the reference to the root
        mDatabase = mDatabase.child("MedicineList");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<Medicine>> t = new GenericTypeIndicator<List<Medicine>>() {};
                List<Medicine> medicineList = dataSnapshot.getValue(t);

                SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                Gson gson = new Gson();
                String json = gson.toJson(medicineList);
                prefsEditor.putString("MedicineList",json);
                prefsEditor.apply();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });

        //TODO
        //Not syncing after adding an illness to database manually, need to try w/ two emulators
        /////// ILLNESS LIST (in protocols) DATABASE LISTENER ////////////////////////
        mDatabase = FirebaseDatabase.getInstance().getReference(); //reset the reference to the root
        mDatabase = mDatabase.child("IllnessList");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<ArrayList<Illness>> t = new GenericTypeIndicator<ArrayList<Illness>>() {};
                ArrayList<Illness> illnessList = dataSnapshot.getValue(t);

                SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                Gson gson = new Gson();
                String json = gson.toJson(illnessList);
                prefsEditor.putString("IllnessList",json);
                prefsEditor.apply();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });

        //mDatabase = FirebaseDatabase.getInstance().getReference(); //reset the reference to the root
        mDatabase = mDatabase.getRoot().child("Farm");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("FarmLog", dataSnapshot.getValue().toString());
                GenericTypeIndicator<Farm> t = new GenericTypeIndicator<Farm>() {};
                Farm farm = dataSnapshot.getValue(t);

                SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                Gson gson = new Gson();
                String json = gson.toJson(farm);
                prefsEditor.putString("Farm",json);
                prefsEditor.apply();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });


        /*
        ArrayList<ArrayList<Integer>> testArLst = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> temp = new ArrayList<Integer>();

        temp.add(2);
        temp.add(45);

        testArLst.add(temp);
        temp.add(null);
        temp.add(2);

        testArLst.add(temp);

        mDatabase.getRoot().child("testNestedArrayList").setValue(testArLst);
        */


        mDatabase = mDatabase.getRoot().child("Task");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("TaksVal", dataSnapshot.getValue().toString());

                GenericTypeIndicator<Task> t = new GenericTypeIndicator<Task>() {};
                GenericTypeIndicator<Map<String, ArrayList<VaccineTask>>> t1 = new GenericTypeIndicator<Map<String, ArrayList<VaccineTask>>>() {};
                GenericTypeIndicator<Map<String, ArrayList<IllnessTask>>> t2 = new GenericTypeIndicator<Map<String, ArrayList<IllnessTask>>>() {};
                GenericTypeIndicator<Integer> t3 = new GenericTypeIndicator<Integer>() {};
                GenericTypeIndicator<ArrayList<VaccineTask>> t4 = new GenericTypeIndicator<ArrayList<VaccineTask>>() {};
                GenericTypeIndicator<ArrayList<ArrayList<IllnessTask>>> t5 = new GenericTypeIndicator<ArrayList<ArrayList<IllnessTask>>>() {};
                GenericTypeIndicator<ArrayList<ArrayList<VaccineTask>>> t6 = new GenericTypeIndicator<ArrayList<ArrayList<VaccineTask>>>() {};


                //ArrayList<Map<String, VaccineTask>> test = dataSnapshot.child("vaccinesToAdminister").getValue(t1);

                //Task task = dataSnapshot.getValue(t);

                ArrayList<ArrayList<VaccineTask>> vaccineTask;

                try{

                    ArrayList<ArrayList<VaccineTask>> vaccinesToAdminister = dataSnapshot.child("vaccinesToAdminister").getValue(t6);
                    vaccineTask = vaccinesToAdminister;
                    for(int i = vaccinesToAdminister.size(); i < 365; i++){
                        vaccineTask.add(new ArrayList<VaccineTask>());
                    }
                }catch (Exception e) {

                    Map<String, ArrayList<VaccineTask>> vaccinesToAdminister = dataSnapshot.child("vaccinesToAdminister").getValue(t1);

                    vaccineTask = new ArrayList<ArrayList<VaccineTask>>();

                    if (vaccinesToAdminister == null) {
                        Log.d("NULL", "NULL");
                        for (int i = 0; i < 365; i++) {
                            vaccineTask.add(new ArrayList<VaccineTask>());
                        }

                    } else {

                        for (int i = 0; i < 365; i++) {

                            // Log.d(Integer.toString(i), vaccinesToAdminister.get(Integer.toString(i)).toString());

                            if (vaccinesToAdminister.get(Integer.toString(i)) == null) {
                                Log.d("Got null", Integer.toString(i));

                                vaccineTask.add(new ArrayList<VaccineTask>());

                            } else {
                                Log.d("Not null", vaccinesToAdminister.get(Integer.toString(i)).toString());
                                vaccineTask.add(vaccinesToAdminister.get(Integer.toString(i)));

                            }
                        }
                    }

                }
                    ArrayList<ArrayList<IllnessTask>> illnessTask = new ArrayList<ArrayList<IllnessTask>>();

                try {
                    ArrayList<ArrayList<IllnessTask>> illnessTracker = dataSnapshot.child("illnessTracker").getValue(t5);

                    illnessTask = illnessTracker;
                    for(int i = illnessTracker.size(); i < 40; i++){
                        illnessTask.add(new ArrayList<IllnessTask>());
                    }

                }catch (Exception e) {

                    //READS IN AS ARRAYLIST NOW. CAN USE THIS IF NEED MAP
                    Map<String, ArrayList<IllnessTask>> illnessTracker = dataSnapshot.child("illnessTracker").getValue(t2);
                    illnessTask = new ArrayList<ArrayList<IllnessTask>>();

                if(illnessTracker == null){
                    for(int i = 0; i < 40; i++){
                        illnessTask.add(new ArrayList<IllnessTask>());
                    }


                }else {
                    for (int i = 0; i < 40; i++) {

                        if (illnessTracker.get(Integer.toString(i)) == null) {
                            Log.d("Illness Null", Integer.toString(i));
                            illnessTask.add(new ArrayList<IllnessTask>());
                        } else {
                            Log.d("Illness not null", Integer.toString(i));
                            illnessTask.add(illnessTracker.get(Integer.toString(i)));
                        }
                    }
                }


                }
                Integer day = dataSnapshot.child("day").getValue(t3);
                Integer month = dataSnapshot.child("month").getValue(t3);
                Integer year = dataSnapshot.child("year").getValue(t3);

                ArrayList<VaccineTask> overdue = dataSnapshot.child("overdueVaccinations").getValue(t4);

                if(overdue == null){
                    overdue = new ArrayList<VaccineTask>();
                }

                Task task = new Task(Calendar.getInstance(), new ArrayList<Calf>(), vaccineTask, overdue, illnessTask);
                task.setDay(dataSnapshot.child("day").getValue(t3));
                task.setMonth(dataSnapshot.child("month").getValue(t3));
                task.setYear(dataSnapshot.child("year").getValue(t3));

                SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                Gson gson = new Gson();
                String json = gson.toJson(task);
                prefsEditor.putString("Task",json);
                prefsEditor.apply();

                //String json = gson.toJson(task);
                Log.d("Task", json);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });





    }



}
