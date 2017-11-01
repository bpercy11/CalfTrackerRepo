package com.example.brett.calftracker;

import android.app.Activity;
import android.app.AlertDialog;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocol_medicine);

        Button editIllness = (Button) findViewById(R.id.editIllness);
        editIllness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ProtocolActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.activity_edit_illness, null);

                final EditText mName = (EditText) mView.findViewById(R.id.editTextIllness);
                final EditText mTreatment = (EditText) mView.findViewById(R.id.editTextTreatment);
                final EditText mDosage = (EditText) mView.findViewById(R.id.editTextDosage);
                final EditText mTimeActive = (EditText) mView.findViewById(R.id.editTextTimeActive);
                final EditText mAdminMethod = (EditText) mView.findViewById(R.id.editTextAdminMethod);
                final Button mAddIllness = (Button) mView.findViewById(R.id.buttonAddIllness);

                mAddIllness.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!mName.getText().toString().isEmpty() && !mTreatment.getText().toString().isEmpty() &&
                                !mDosage.getText().toString().isEmpty() && !mTimeActive.getText().toString().isEmpty()
                                &&  !mTimeActive.getText().toString().isEmpty() && !mAdminMethod.getText().toString().isEmpty()){
                            Toast.makeText(ProtocolActivity.this, R.string.add_illness_successful_message,
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(ProtocolActivity.this, R.string.empty_fields_message,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });
    }
/*
        // try and get Medicine object made by main activity
        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();

        Gson gson = new Gson();
        String json = mPreferences.getString("Medical","");
        medicine = gson.fromJson(json, Medicine.class);
        tempMedicine = gson.fromJson(json, Medicine.class);
*/

     /*   lvMedicine = (ListView)findViewById(R.id.listview_medicine);
        //sample data

        mAdapter = new MedicineAdapter(getApplicationContext(), medicineList);
        lvMedicine.setAdapter(mAdapter);

        lvMedicine.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Do something

            }
        });
    }

    */


 /*   public void onVaccineButtonClick(View view) {
        setContentView(R.layout.activity_protocol_vaccine);

        // try and get Vaccine object made by main activity
        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();

        Gson gson = new Gson();
        String json = mPreferences.getString("Medical","");
        vaccine = gson.fromJson(json, Vaccine.class);
        tempVaccine = gson.fromJson(json, Vaccine.class);

*/

 /*       lvVaccine = (ListView)findViewById(R.id.listview_vaccine);
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
        });
    }
    public void onMedicineButtonClick(View view) {
        //Do nothing since it is already on the page.
    }*/
    public void onEditVaccineButtonClick(View view) {
/*
        //Write to local device
        SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(tempIllness);
        prefsEditor.putString("newIllness",json);
        prefsEditor.apply();
*/
    }
    public void onEditIllnessButtonClick(View view) {
/*
        //Write to local device
        SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(tempMedicine);
        prefsEditor.putString("newMedicine",json);
        prefsEditor.apply();
*/
    }
}
