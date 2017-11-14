package com.example.brett.calftracker;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class IllnessActivity extends AppCompatActivity {
    private List<Illness> illnessList;
    private ListView lvIllness;
    private IllnessAdapter iAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // getLayoutInflater().inflate(R.layout.activity_protocol_illness, frameLayout);
       // mNavigationView.getMenu().findItem(R.id.nav_protocols).setChecked(true);

        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        if(mPreferences.contains("IllnessList")) {
            SharedPreferences.Editor editor = mPreferences.edit();

            Gson gson = new Gson();
            String json = mPreferences.getString("IllnessList", "");
            illnessList = gson.fromJson(json, new TypeToken<ArrayList<Illness>>() {
            }.getType());
        } else { illnessList = new ArrayList<Illness>(); }

        ListView lvIllness = (ListView) findViewById(R.id.listview_illness);

        IllnessAdapter iAdapter = new IllnessAdapter(getApplicationContext(), illnessList);
        lvIllness.setAdapter(iAdapter);

        lvIllness.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Do something
            }
        });

    }
    public void onVaccineButtonClick(View view) {
        Intent intent = new Intent(IllnessActivity.this,ProtocolActivity.class);
        startActivity(intent);

    }
    public void onIllnessButtonClick(View view){
        Intent intent = new Intent(IllnessActivity.this, IllnessActivity.class);
        startActivity(intent);
    }
    public void onMedicineButtonClick(View view){
        Intent intent = new Intent(IllnessActivity.this, MedicineActivity.class);
        startActivity(intent);
    }
  /*  public void onEditIllnessButtonClick(View view){
        Intent intent = new Intent(IllnessActivity.this, EditIllnessActivity.class);
        startActivity(intent);
    }*/

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }
}
