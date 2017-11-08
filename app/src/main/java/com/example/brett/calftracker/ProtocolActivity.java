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

public class ProtocolActivity extends BaseActivity {

    private ListView lvVaccine;

    private VaccineAdapter vAdapter;

    private List<String> vaccineList;


    // TODO: Fix listview vaccine, implement edit of UI vaccines and local storage

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_protocol_vaccine, frameLayout);
        mNavigationView.getMenu().findItem(R.id.nav_protocols).setChecked(true);

        //Only use if you change 'BaseAdapter' to 'ArrayAdapter'
/*      ArrayAdapter adapter construct
        // Construct the data source
        ArrayList<Vaccine> arrayOfVaccines = new ArrayList<Vaccine>();
        // Create the adapter to convert the array to views
        VaccineAdapter adapter = new VaccineAdapter(this, arrayOfVaccines);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.listview_vaccine);
        listView.setAdapter(adapter);
*/

        ListView lvVaccine = (ListView)findViewById(R.id.listview_vaccine);
        vaccineList = new ArrayList<>();
        //sample data
        //vaccineList.add(new Vaccine("POOP",10,5,"ml","needle"));
        //vaccineList.add(new Vaccine("alex",6,15,"ml","pill"));
        vaccineList.add("Parainfluenza-3");
        vaccineList.add("Respiratory Syncytical Virus");
        vaccineList.add("Haemophilus Somnus");
        vaccineList.add("Needle");
        vaccineList.add("Powder");
        vaccineList.add("Pill");
        vaccineList.add("Liquid");
        vaccineList.add("Gas");
        vaccineList.add("Food");
        VaccineAdapter vAdapter = new VaccineAdapter(getApplicationContext(), vaccineList);
        lvVaccine.setAdapter(vAdapter);

        lvVaccine.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Do something
            }
        });
    }
    public void onMedicineButtonClick(View view) {
        Intent intent = new Intent(ProtocolActivity.this,MedicineActivity.class);
        startActivity(intent);
    }
    public void onEditVaccineButtonClick(View view){
        Intent intent = new Intent(ProtocolActivity.this, EditVaccineActivity.class);
        startActivity(intent);
    }

    public void onBackPressed() {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }
}

