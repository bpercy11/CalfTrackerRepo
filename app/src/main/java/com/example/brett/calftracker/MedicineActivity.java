package com.example.brett.calftracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MedicineActivity extends AppCompatActivity {

    private List<String> illnessList;
    private ListView lvIllness;
    private IllnessAdapter iAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocol_medicine);

        ListView lvIllness = (ListView) findViewById(R.id.listview_medicine);
        illnessList = new ArrayList<>();

        //sample data
        illnessList.add("Liquid");
        illnessList.add("Gas");
        illnessList.add("Food");
        illnessList.add("Parainfluenza-3");
        illnessList.add("Needle");
        illnessList.add("Powder");
        illnessList.add("Pill");
        illnessList.add("Respiratory Syncytical Virus");
        illnessList.add("Haemophilus Somnus");


        iAdapter = new IllnessAdapter(getApplicationContext(), illnessList);
        lvIllness.setAdapter(iAdapter);

        lvIllness.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick (AdapterView < ? > parent, View view,int position, long id){
            //Do something

            }
        });
    }
    public void onVaccineButtonClick(View view) {
        Intent intent = new Intent(MedicineActivity.this,ProtocolActivity.class);
        startActivity(intent);

    }
    public void onEditMedicineButtonClick(View view){
        Intent intent = new Intent(MedicineActivity.this, EditMedicineActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }
}
