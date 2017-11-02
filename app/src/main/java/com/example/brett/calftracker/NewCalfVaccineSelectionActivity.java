package com.example.brett.calftracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class NewCalfVaccineSelectionActivity extends AppCompatActivity {

    ArrayList<VaccineSelectionItem> adapterArray;
    VaccineSelectionListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_calf_vaccine_selection);

        ListView listView = (ListView) findViewById(R.id.listViewVaccineSelection);

        VaccineSelectionItem item = new VaccineSelectionItem(new Vaccine("derp",new ArrayList<Vacc_Range>(),12,"herp","merp"), false);
        VaccineSelectionItem item2 = new VaccineSelectionItem(new Vaccine("derp1",new ArrayList<Vacc_Range>(),12,"herp","merp"), false);
        VaccineSelectionItem item3 = new VaccineSelectionItem(new Vaccine("derp2",new ArrayList<Vacc_Range>(),12,"herp","merp"), false);
        VaccineSelectionItem item4 = new VaccineSelectionItem(new Vaccine("derp3",new ArrayList<Vacc_Range>(),12,"herp","merp"), false);
        VaccineSelectionItem item5 = new VaccineSelectionItem(new Vaccine("derp4",new ArrayList<Vacc_Range>(),12,"herp","merp"), false);
        VaccineSelectionItem item6 = new VaccineSelectionItem(new Vaccine("derp5",new ArrayList<Vacc_Range>(),12,"herp","merp"), false);
        adapterArray = new ArrayList<VaccineSelectionItem>();

        adapterArray.add(item);
        adapterArray.add(item2);
        adapterArray.add(item3);
        adapterArray.add(item4);
        adapterArray.add(item5);
        adapterArray.add(item6);

        adapter = new VaccineSelectionListViewAdapter(adapterArray, getApplicationContext());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {

                VaccineSelectionItem dataModel= adapterArray.get(position);
                dataModel.setChecked(!dataModel.getChecked());
                adapter.notifyDataSetChanged();


            }
        });
    }

    public void onConfirmVaccines(View view) {
        for(int i = 0; i < adapterArray.size(); i++) {
            if(adapterArray.get(i).getChecked()) {
                //add to neededvaccines list
            }
        }
        Intent intent = new Intent(this,CalfProfileActivity.class);
        startActivity(intent);
    }

    public void onClickSelectAll(View view) {
        for(int i = 0; i < adapterArray.size(); i++) {
            adapterArray.get(i).setChecked(true);
        }
        adapter.notifyDataSetChanged();
    }
}
