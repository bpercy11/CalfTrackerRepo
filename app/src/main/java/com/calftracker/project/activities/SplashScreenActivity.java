package com.calftracker.project.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.os.SystemClock;

import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Firebase;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by project on 10/31/17.
 */
public class SplashScreenActivity extends Activity {

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        Firebase test = (Firebase) getApplicationContext();

        test.compareToFirebase();

        //test.editSharedPreferences("usedApp", "false");
        //test.editSharedPreferences("usedApp", "true");


        //final SharedPreferences sharedPref = getSharedPreferences("test",Activity.MODE_PRIVATE);
        //final SharedPreferences.Editor editor = sharedPref.edit();
        //editor.putBoolean("usedApp", false);
        //editor.commit();

        //Thread timerThread = new Thread(){
            //public void run(){


                try{

                    Thread.sleep(3000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    test.editSharedPreferences("test", "test");
                    SharedPreferences sharedPref = getSharedPreferences("usedApp",Activity.MODE_PRIVATE);
                    //boolean hasBeenUsed = sharedPref.getBoolean("usedApp", false);
                    boolean hasBeenUsed = sharedPref.getBoolean("usedApp", false);

                    if(!hasBeenUsed) {
                        Intent intent = new Intent(SplashScreenActivity.this, CreateFarmActivity.class);

                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putBoolean("usedApp", true);
                        editor.commit();

                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(SplashScreenActivity.this, DashboardActivity.class);
                        startActivity(intent);

                    }
                }
            //}
        //};
        //timerThread.start();
    }



    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }

}