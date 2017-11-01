package com.example.brett.calftracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiConfiguration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ProtocolActivity extends AppCompatActivity {

    private ListView lvVaccine;
    private ListView lvMedicine;
    private VaccineAdapter vAdapter;
    private MedicineAdapter mAdapter;
    private List<Vaccine> vaccineList;
    private List<Illness> illnessList;
    private List<Medicine> medicineList;

    // TODO: Fix listview vaccine, implement edit of UI vaccines and local storage

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocol_vaccine);

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
        vaccineList.add(new Vaccine("POOP",5,15,"ml","needle"));
        vaccineList.add(new Vaccine("alex",6,10,"ml","pill"));

        VaccineAdapter vAdapter = new VaccineAdapter(getApplicationContext(), vaccineList);
        lvVaccine.setAdapter(vAdapter);


        //App crashes on setOnItemClickListener
        lvVaccine.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Do something
               /* Button editIllness = (Button) findViewById(R.id.editIllness);
                editIllness.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ProtocolActivity.this, EditIllnessActivity.class);
                        startActivity(intent);
                    }
                });*/
            }
        });
    }

    public void onVaccineButtonClick(View view) {
       // setContentView(R.layout.activity_protocol_vaccine);

                /* lvVaccine = (ListView)findViewById(R.id.listview_vaccine);
                   //sample data
                   vaccineList.add(new Vaccine("POOP",5,15,"ml","needle"));
                   vaccineList.add(new Vaccine("alex",6,10,"ml","pill"));

                   vAdapter = new VaccineAdapter(getApplicationContext(), vaccineList);
                   lvVaccine.setAdapter(vAdapter);

                   lvVaccine.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                       @Override
                       public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                           //Do something

                       }
                   });*/
               }
    public void onMedicineButtonClick(View view) {
        //Do nothing since it is already on the page.
    }
}

