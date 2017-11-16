package com.calftracker.project.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.calftracker.project.models.Medicine;
import com.calftracker.project.models.Illness;
import com.calftracker.project.models.Treatment_Protocol;
import com.calftracker.project.adapters.MedicineAdapter;
import com.calftracker.project.calftracker.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class EditIllnessActivity extends AppCompatActivity {

    private AlertDialog alertDialog;
    private EditText illnessName;
    private String illnessNotes;
    private ListView lvTreatmentProtocolMedicines;
    private List<Illness> illnessList;
    private List<Medicine> medicineList;
    private List<Medicine> tempMedicineList;
    private MedicineAdapter medicineAdapter;
    private Button addNotesButton;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_illness);

        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        if(mPreferences.contains("IllnessList")) {
            SharedPreferences.Editor editor = mPreferences.edit();

            Gson gson = new Gson();
            String json = mPreferences.getString("IllnessList", "");
            illnessList = gson.fromJson(json, new TypeToken<ArrayList<Illness>>() {
            }.getType());
        } else { illnessList = new ArrayList<Illness>(); }

        SharedPreferences mPreferences1 = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        if(mPreferences1.contains("MedicineList")) {
            SharedPreferences.Editor editor = mPreferences1.edit();

            Gson gson1 = new Gson();
            String json1 = mPreferences1.getString("MedicineList", "");
            medicineList = gson1.fromJson(json1, new TypeToken<ArrayList<Medicine>>() {
            }.getType());
        } else { medicineList = new ArrayList<Medicine>(); }

        lvTreatmentProtocolMedicines = (ListView) findViewById(R.id.listViewEditIllness);
      /*  medicineAdapter = new MedicineAdapter(getApplicationContext(), medicineList);
        lvTreatmentProtocolMedicines.setAdapter(medicineAdapter); */

        lvTreatmentProtocolMedicines.setAdapter(new ArrayAdapter<Medicine>(this, android.R.layout.simple_list_item_multiple_choice, medicineList));
        lvTreatmentProtocolMedicines.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL); // user HAS to interact with window before continuing
        //lvTreatmentProtocolMedicines.setItemChecked();

        lvTreatmentProtocolMedicines.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*if(view.isSelected()) {
                    view.setSelected(true);
                    tempMedicineList.add(medicineList.get(position)); */
                String selected = "";
                int countChoice = lvTreatmentProtocolMedicines.getCount();
                SparseBooleanArray sparseBooleanArray = lvTreatmentProtocolMedicines.getCheckedItemPositions();
                for (int i = 0; i < countChoice; i++){
                    if (sparseBooleanArray.get(i)){
                        selected += lvTreatmentProtocolMedicines.getItemAtPosition(i).toString() + "\n";
                    }
                }
                Toast.makeText(EditIllnessActivity.this, selected, Toast.LENGTH_LONG).show();
            }
        });

        addNotesButton = (Button) findViewById(R.id.EditIllnessAddNotesButton);
        // add button listener
        addNotesButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set title
                alertDialogBuilder.setTitle("Please enter a note");
                final EditText input = new EditText(EditIllnessActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialogBuilder.setView(input);
                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Done",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                illnessNotes = input.getText().toString();
                                // if this button is clicked, close
                                // current activity
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });
    }

    public void clickAddIllnessButton(View view){

        illnessName = (EditText) findViewById(R.id.editTextIllnessEnterName);

        if (illnessName.getText().toString().matches("")){
            Toast.makeText(EditIllnessActivity.this, R.string.EditIllnessActivity_emptyFieldMsg, Toast.LENGTH_SHORT).show();
        }

        // Make a new illness object
        String name = illnessName.getText().toString();
        Illness newIllness = new Illness(name, new Treatment_Protocol(tempMedicineList, illnessNotes));

        illnessList.add(newIllness);

        // SAVE NEW Medicine
        SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(illnessList);
        prefsEditor.putString("IllnessList", json);
        prefsEditor.apply();

        Intent intent = new Intent(this, IllnessActivity.class);
        startActivity(intent);

    }

    public void clickEditIllnessCancelButton(View view){
        Intent intent = new Intent(EditIllnessActivity.this, IllnessActivity.class);
        startActivity(intent);
    }
}

