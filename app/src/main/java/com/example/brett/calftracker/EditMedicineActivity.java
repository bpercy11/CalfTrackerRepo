package com.example.brett.calftracker;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class EditMedicineActivity extends AppCompatActivity {

    private AlertDialog alertDialog;
    private TextView illnessName;
    private TextView treatment;
    private TextView dosage;
    private TextView timeActive;
    private TextView frequency;
    private List<Illness> illnessList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_medicine);

/*
        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        if(mPreferences.contains("IllnessList")) {
            SharedPreferences.Editor editor = mPreferences.edit();

            Gson gson = new Gson();
            String json = mPreferences.getString("VaccineList", "");
            illnessList = gson.fromJson(json, new TypeToken<ArrayList<Illness>>() {
            }.getType());
        } else { illnessList = new ArrayList<Illness>(); }
 */
        android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder(EditMedicineActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.activity_edit_medicine, null);

        final EditText mName = (EditText) mView.findViewById(R.id.editTextIllness);
        final EditText mTreatment = (EditText) mView.findViewById(R.id.editTextTreatment);
        final EditText mDosage = (EditText) mView.findViewById(R.id.editTextDosage);
        final EditText mTimeActive = (EditText) mView.findViewById(R.id.editTextTimeActive);
        final EditText mAdminMethod = (EditText) mView.findViewById(R.id.editTextMedicineFrequency);
        final Button mAddIllness = (Button) mView.findViewById(R.id.buttonAddMedicine);
        final Button mCancel = (Button) mView.findViewById(R.id.buttonCancel);

        mAddIllness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mName.getText().toString().isEmpty() && !mTreatment.getText().toString().isEmpty() &&
                        !mDosage.getText().toString().isEmpty() && !mTimeActive.getText().toString().isEmpty()
                        &&  !mTimeActive.getText().toString().isEmpty() && !mAdminMethod.getText().toString().isEmpty()){
                    Toast.makeText(EditMedicineActivity.this, R.string.add_medicine_successful_message,
                            Toast.LENGTH_SHORT).show();
                    clickAddMedicineButton(view);
                }
                else {
                    Toast.makeText(EditMedicineActivity.this, R.string.empty_fields_message,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        //mCancel.setOnClickListener(new View.OnClickListener(){

        //});

        mBuilder.setView(mView);
        android.app.AlertDialog dialog = mBuilder.create();
        dialog.show();
    }



    public void clickAddMedicineButton(View view){
        EditText name = (EditText) findViewById(R.id.editTextIllness);
        EditText treatment = (EditText) findViewById(R.id.editTextTreatment);
        EditText dosage = (EditText) findViewById(R.id.editTextDosage);
        EditText timeActive = (EditText) findViewById(R.id.editTextTimeActive);
        EditText frequency = (EditText) findViewById(R.id.editTextMedicineFrequency);

        String treatmentStr = treatment.getText().toString();
        Double dosageDbl = Double.parseDouble(dosage.getText().toString());
        int timeActiveInt = Integer.parseInt(timeActive.getText().toString());
        String frequencyString = frequency.getText().toString();
        String nameStr = name.getText().toString();
/*
        // MAKE A NEW ILLNESS OBJECT
        Medicine medicine = new Medicine(treatmentStr,dosageDbl,"null",timeActiveInt,new ArrayList<MedicineFrequency>());
        Treatment_Protocol tp = new Treatment_Protocol(medicine, "");
        Illness newIllness = new Illness(nameStr,tp);

        // SAVE NEW ILLNESS
        SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(newIllness);
        prefsEditor.putString("IllnessList",json);
        prefsEditor.apply();
*/
        Intent intent = new Intent(this,MedicineActivity.class);
        startActivity(intent);
    }
    public void clickCancelButton(View view){
        Intent intent = new Intent(EditMedicineActivity.this, MedicineActivity.class);
        startActivity(intent);
    }

}
