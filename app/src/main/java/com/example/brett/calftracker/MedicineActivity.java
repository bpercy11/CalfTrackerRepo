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

public class MedicineActivity extends BaseActivity {

    private List<Illness> illnessList;
    private ListView lvIllness;
    private IllnessAdapter iAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_protocol_medicine, frameLayout);
        mNavigationView.getMenu().findItem(R.id.nav_protocols).setChecked(true);

        ListView lvIllness = (ListView) findViewById(R.id.listview_medicine);
        illnessList = new ArrayList<>();

        //sample data
        illnessList.add(new Illness("Illness1",new Treatment_Protocol(new Medicine(
                "Medicine1",15,"ml",25,new ArrayList<MedicineFrequency>(8))
                ,"hi")));
        illnessList.add(new Illness("Illness2",new Treatment_Protocol(new Medicine(
                "Medicine2",10,"ml",50,new ArrayList<MedicineFrequency>(7))
                ,"hello")));
        illnessList.add(new Illness("Illness3",new Treatment_Protocol(new Medicine(
                "Medicine3",15,"ml",25,new ArrayList<MedicineFrequency>(6))
                ,"hi")));
        illnessList.add(new Illness("Illness4",new Treatment_Protocol(new Medicine(
                "Medicine4",10,"ml",50,new ArrayList<MedicineFrequency>(5))
                ,"hello")));


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
