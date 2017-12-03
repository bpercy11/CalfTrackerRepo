package com.calftracker.project.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Base64;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Calf;
import com.calftracker.project.models.Note;
import com.calftracker.project.models.Physical_Metrics_And_Date;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static com.calftracker.project.activities.AddCalfActivity.REQUEST_IMAGE_CAPTURE;

public class CalfProfileActivity extends AppCompatActivity {

    private ImageView mPhoto;
    private ConstraintLayout mConstraintLayout;
    private Button mButtonAddPhoto;
    private Button mButtonDeletePhoto;
    private Button mButtonChangePhoto;
    private Bitmap currentImage = null;
    private Bitmap originalImage = null;
    private int dp32;

    private TextView mIDValue;
    private TextView mGenderValue;
    private TextView mDOBValue;
    private TextView mSireValue;
    private TextView mSireNameValue;
    private TextView mDamValue;
    private TextView mWeightValue;
    private TextView mHeightValue;

    LayoutInflater inflater;

    private Dialog mGenderListDialog;
    private String[] gender = {"Male","Female"};
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private ListView mNoteListView;

    private Calf calf;
    private String calfID;
    private ArrayList<Calf> calfList;

    private Calf tempCalf;
    private String tempID;
    private String tempSire;
    private String tempSireName;
    private String tempDam;
    private String tempDOBString;
    private String tempGender;
    private Calendar tempDOB;

    private AlertDialog alertID;
    private AlertDialog alertGender;
    private AlertDialog alertSire;
    private AlertDialog alertSireName;
    private AlertDialog alertDam;

    private double mostRecentWeight;
    private double mostRecentHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calf_profile);

        // Stylize action bar to use back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        retrieveData();

        // try and get calf object made by main activity
        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        Gson gson = new Gson();
        String json;
        json = mPreferences.getString("calfToViewInProfile","");
        calfID = gson.fromJson(json, String.class);
        getSupportActionBar().setTitle("Calf " + calfID);

        // Search through the calfList to find the correct calf by ID
        for (int i = 0; i < calfList.size(); i++) {
            if (calfList.get(i).getFarmId().equals(calfID)) {
                calf = calfList.get(i);
                tempCalf = calfList.get(i);
                break;
            }
        }

        // print to log calf cbject information
        mPhoto = (ImageView) findViewById(R.id.calfPhoto);
        mConstraintLayout = (ConstraintLayout) findViewById(R.id.calfProfileLayout);
        mButtonAddPhoto = (Button) findViewById(R.id.buttonAddPhoto);
        mButtonDeletePhoto = (Button) findViewById(R.id.buttonDeletePhoto);
        mButtonChangePhoto = (Button) findViewById(R.id.buttonChangePhoto);
        mIDValue = (TextView) findViewById(R.id.textViewIDValue);
        mGenderValue = (TextView) findViewById(R.id.textViewGenderValue);
        mDOBValue = (TextView) findViewById(R.id.textViewDOBValue);
        mSireValue = (TextView) findViewById(R.id.textViewSireValue);
        mSireNameValue = (TextView) findViewById(R.id.textViewSireNameValue);
        mDamValue = (TextView) findViewById(R.id.textViewDamValue);
        mWeightValue = (TextView) findViewById(R.id.textViewWeightValue);
        mHeightValue = (TextView) findViewById(R.id.textViewHeightValue);

        // Create these variables for code readability
        if (!calf.getPhysicalHistory().isEmpty()) {
            mostRecentWeight = calf.getPhysicalHistory().get(calf.getPhysicalHistory().size()-1).getWeight();
            mostRecentHeight = calf.getPhysicalHistory().get(calf.getPhysicalHistory().size()-1).getHeight();
        }

        // Custom layout for text dialogs
        inflater = getLayoutInflater();

        // Calculate 32dp in pixels, to be used when changing margins for photo tools
        Resources r = getResources();
        dp32 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, r.getDisplayMetrics());

        // Hide photo buttons until edit
        mButtonAddPhoto.setVisibility(View.GONE);
        mButtonDeletePhoto.setVisibility(View.GONE);
        mButtonChangePhoto.setVisibility(View.GONE);

        // Get photo if there is one
        String encodedImage = calf.getPhoto();

        // Show photo if available
        if (encodedImage != null) {
            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            originalImage = decodedByte;
            currentImage = decodedByte;
            mPhoto.setImageBitmap(decodedByte);
            showPhoto();
        }
        // If no photo, hide image view
        else {
            mPhoto.setVisibility(View.GONE);
        }

        String farmID = calf.getFarmId();
        mIDValue.setText(farmID);
        mGenderValue.setText(calf.getGender());
        int year = calf.getDateOfBirth().get(Calendar.YEAR);
        int month = calf.getDateOfBirth().get(Calendar.MONTH) + 1;
        int day = calf.getDateOfBirth().get(Calendar.DAY_OF_MONTH);
        mDOBValue.setText(month + "/" + day + "/" + year);
        if (calf.getSire().getId() != null) {mSireValue.setText(calf.getSire().getId());}
        if (calf.getSire().getName() != null) { mSireNameValue.setText(calf.getSire().getName()); }
        if (calf.getDam() != null) {mDamValue.setText(calf.getDam());}

        // Display weight and/or height information only if there has been a recording
        if (!calf.getPhysicalHistory().isEmpty() && mostRecentWeight != -1) {
            mWeightValue.setText(Double.toString(mostRecentWeight) + " lbs");
        }
        if (!calf.getPhysicalHistory().isEmpty() && mostRecentHeight != -1) {
            mHeightValue.setText(Double.toString(mostRecentHeight) + " in");
        }

        // Set up temporary values, will be updated and applied to the current calf upon user clicking apply
        tempID = mIDValue.getText().toString();
        tempSire = mSireValue.getText().toString();
        tempSireName = mSireNameValue.getText().toString();
        tempDam = mDamValue.getText().toString();
        tempDOBString = mDOBValue.getText().toString();
        tempGender = mGenderValue.getText().toString();

        // SET UP NOTE LISTVIEW
        updateNoteListView(calf);

        mNoteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if(calf.getNotesSize() == 0)
                    return;

                AlertDialog noteContentPopup;

                AlertDialog.Builder builder = new AlertDialog.Builder(CalfProfileActivity.this);
                final TextView output = new TextView(CalfProfileActivity.this);
                // Space at the start to make notes easier to read
                output.setText(calf.getNoteNdx(position).getMessage());
                output.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

                Calendar noteDate = calf.getNoteNdx(position).getDateEntered();
                int year = noteDate.get(Calendar.YEAR);
                int month = noteDate.get(Calendar.MONTH) + 1;
                int day = noteDate.get(Calendar.DAY_OF_MONTH);
                builder.setTitle("Note entered " + month + "/" + day + "/" + year);
                builder.setView(output);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        calf.getNotes().remove(position);
                        saveData();
                        updateNoteListView(calf);
                    }
                });

                noteContentPopup = builder.create();

                noteContentPopup.show();
            }
        });

        if(calf.isNeedToObserveForIllness()) {
            Button mMark = (Button) findViewById(R.id.buttonMarkObservation);
            mMark.setText(R.string.calf_profile_unmark_oberservation);
        }

    }

    public void retrieveData() {
        // try and get calf object made by main activity
        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();

        Gson gson = new Gson();
        String json = mPreferences.getString("CalfList","");
        calfList = gson.fromJson(json, new TypeToken<ArrayList<Calf>>(){}.getType());
    }

    public void saveData() {
        SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(calfList);
        prefsEditor.putString("CalfList",json);
        prefsEditor.apply();
    }

    public void updateNoteListView(Calf calf) {
        ArrayList<String> noteContents = new ArrayList<>();
        for(int i = 0; i < calf.getNotesSize(); i++) {
            noteContents.add(calf.getNotes().get(i).getMessage());
        }

        if(calf.getNotesSize() == 0)
            noteContents.add("No Notes!");

        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, noteContents);
        mNoteListView = (ListView) findViewById(R.id.listViewNotes);
        mNoteListView.setAdapter(itemsAdapter);
    }

    public void clickEditButton(View view) {
        findViewById(R.id.listViewNotes).setVisibility(View.GONE);
        findViewById(R.id.floatingActionButtonEDIT).setVisibility(View.GONE);
        mWeightValue.setVisibility(View.GONE);
        mHeightValue.setVisibility(View.GONE);
        findViewById(R.id.textViewWeightField).setVisibility(View.GONE);
        findViewById(R.id.textViewHeightField).setVisibility(View.GONE);
        findViewById(R.id.buttonFeedingHistory).setVisibility(View.GONE);
        findViewById(R.id.buttonGrowthHistory).setVisibility(View.GONE);
        findViewById(R.id.buttonMedicalHistory).setVisibility(View.GONE);
        findViewById(R.id.buttonAddWeight).setVisibility(View.GONE);
        findViewById(R.id.buttonAddHeight).setVisibility(View.GONE);
        findViewById(R.id.buttonCreateNewNote).setVisibility(View.GONE);
        findViewById(R.id.buttonMarkObservation).setVisibility(View.GONE);
        findViewById(R.id.textViewNotes).setVisibility(View.GONE);
        findViewById(R.id.buttonCancel).setVisibility(View.VISIBLE);
        findViewById(R.id.buttonApply).setVisibility(View.VISIBLE);
        findViewById(R.id.buttonDeleteCalf).setVisibility(View.VISIBLE);

        mIDValue.setPaintFlags(mIDValue.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        mGenderValue.setPaintFlags(mGenderValue.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        mDOBValue.setPaintFlags(mDOBValue.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        mSireValue.setPaintFlags(mSireValue.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        mSireNameValue.setPaintFlags(mSireNameValue.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        mDamValue.setPaintFlags(mDamValue.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        // Disable back navigation
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("Edit Calf " + calfID);

        // Edit Photo
        // Case no calf photo set
        if (currentImage == null) {
            showAddPhotoButton();
        }
        // Case calf photo set
        else {
            showDeleteChangePhotoButton();
        }

        // Edit ID Number
        mIDValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertID.show();
            }
        });
        AlertDialog.Builder builderID = new AlertDialog.Builder(this);
        View dialogLayout_ID = inflater.inflate(R.layout.calf_profile_simple_edittext_dialog, null);
        final EditText dialogText_ID = (EditText) dialogLayout_ID.findViewById(R.id.dialogTextInput);

        dialogText_ID.setInputType(InputType.TYPE_CLASS_NUMBER);
        dialogText_ID.setHint("ID Number");
        builderID.setTitle("Enter new ID");
        builderID.setView(dialogLayout_ID);
        builderID.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (dialogText_ID.getText().toString().matches("")) {
                    Context context = getApplicationContext();
                    CharSequence text = "Calf ID cannot be left blank";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    return;
                }
                if (dialogText_ID.length() > 9 || dialogText_ID.length() < 1) {
                    Context context = getApplicationContext();
                    CharSequence text = "ID must be 9 digits or less";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    return;
                }
                mIDValue.setText(dialogText_ID.getText().toString());
                tempCalf.setFarmId(dialogText_ID.getText().toString());
            }
        });
        builderID.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        alertID = builderID.create();

        // EDIT GENDERVALUE
        mGenderValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertGender.show();
            }
        });
        AlertDialog.Builder builderGender = new AlertDialog.Builder(this);
        builderGender.setTitle("Select Gender");
        builderGender.setItems(gender, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mGenderValue.setText(gender[i]);
                tempCalf.setGender(mGenderValue.getText().toString());
            }
        });
        alertGender = builderGender.create();

        // CREATE DIALOG WHEN USER CLICKS DOBVALUE
        mDOBValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        CalfProfileActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        // this happens when the user has selected a date in the dialog and presses "OK"
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                tempDOB = new GregorianCalendar(year,month,day);
                month = month + 1;
                String date = month + "/" + day + "/" + year;
                mDOBValue.setText(date);
                tempCalf.setDateOfBirth(tempDOB);
            }
        };

        // Edit Sire ID Number
        mSireValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertSire.show();
            }
        });
        AlertDialog.Builder builderSire = new AlertDialog.Builder(this);
        View dialogLayout_Sire = inflater.inflate(R.layout.calf_profile_simple_edittext_dialog, null);
        final EditText dialogText_Sire = (EditText) dialogLayout_Sire.findViewById(R.id.dialogTextInput);

        dialogText_Sire.setInputType(InputType.TYPE_CLASS_NUMBER);
        dialogText_Sire.setHint("Sire ID Number");
        builderSire.setTitle("Enter Sire ID");
        builderSire.setView(dialogLayout_Sire);
        builderSire.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (dialogText_Sire.length() > 9 || dialogText_Sire.length() < 1) {
                    Context context = getApplicationContext();
                    CharSequence text = "ID must be 9 digits or less";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    return;
                }
                mSireValue.setText(dialogText_Sire.getText().toString());
                tempCalf.getSire().setId(dialogText_Sire.getText().toString());
            }
        });
        builderSire.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertSire = builderSire.create();

        // Edit Sire Name
        mSireNameValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertSireName.show();
            }
        });
        AlertDialog.Builder builderSireName = new AlertDialog.Builder(this);
        View dialogLayout_SireName = inflater.inflate(R.layout.calf_profile_simple_edittext_dialog, null);
        final EditText dialogText_SireName = (EditText) dialogLayout_SireName.findViewById(R.id.dialogTextInput);

        dialogText_SireName.setInputType(InputType.TYPE_CLASS_TEXT);
        dialogText_SireName.setHint("Sire Name");
        builderSireName.setTitle("Enter Sire Name");
        builderSireName.setView(dialogLayout_SireName);
        builderSireName.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String ed_text = dialogText_SireName.getText().toString().trim();

                if(ed_text.isEmpty() || ed_text.length() == 0 || ed_text.equals("") || ed_text == null) {
                    Context context = getApplicationContext();
                    CharSequence text = "Please enter calf sire name";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    return;
                }
                mSireNameValue.setText(dialogText_SireName.getText().toString());
                tempCalf.getSire().setName(dialogText_SireName.getText().toString());
            }
        });
        builderSireName.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertSireName = builderSireName.create();

        // Edit Dam ID Number
        mDamValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDam.show();
            }
        });
        AlertDialog.Builder builderDam = new AlertDialog.Builder(this);
        View dialogLayout_Dam = inflater.inflate(R.layout.calf_profile_simple_edittext_dialog, null);
        final EditText dialogText_Dam = (EditText) dialogLayout_Dam.findViewById(R.id.dialogTextInput);

        dialogText_Dam.setInputType(InputType.TYPE_CLASS_NUMBER);
        dialogText_Dam.setHint("Dam ID Number");
        builderDam.setTitle("Enter Dam ID");
        builderDam.setView(dialogText_Dam);
        builderDam.setView(dialogLayout_Dam);
        builderDam.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (dialogText_Dam.length() > 9 || dialogText_Dam.length() < 1) {
                    Context context = getApplicationContext();
                    CharSequence text = "ID must be 9 digits or less";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    return;
                }
                mDamValue.setText(dialogText_Dam.getText().toString());
                tempCalf.setDam(dialogText_Dam.getText().toString());
            }
        });
        builderDam.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDam = builderDam.create();
    }

    // Take a photo
    public void onClickAddPhotoButton(View view) {
        dispatchTakePictureIntent();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            mButtonAddPhoto.setVisibility(View.GONE);
            mPhoto.setVisibility(View.VISIBLE);
            mButtonDeletePhoto.setVisibility(View.VISIBLE);
            mButtonChangePhoto.setVisibility(View.VISIBLE);
            showDeleteChangePhotoButton();

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            currentImage = imageBitmap;

            // Set image thumbnail
            mPhoto.setImageBitmap(imageBitmap);

            // Prepare image for saving
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
            tempCalf.setPhoto(encodedImage);
        }
    }

    public void onClickDeletePhotoButton(View view) {
        mPhoto.setVisibility(View.GONE);
        mButtonDeletePhoto.setVisibility(View.GONE);
        mButtonChangePhoto.setVisibility(View.GONE);
        showAddPhotoButton();
        currentImage = null;
    }

    public void showPhoto() {
        mPhoto.setVisibility(View.VISIBLE);
        ConstraintSet set = new ConstraintSet();
        set.clone(mConstraintLayout);
        set.connect(R.id.textViewIDField, ConstraintSet.TOP, mPhoto.getId(), ConstraintSet.BOTTOM, dp32);;
        set.applyTo(mConstraintLayout);
    }

    public void hidePhoto() {
        mPhoto.setVisibility(View.GONE);
        ConstraintSet set = new ConstraintSet();
        set.clone(mConstraintLayout);
        set.connect(R.id.textViewIDField, ConstraintSet.TOP, mConstraintLayout.getId(), ConstraintSet.TOP, dp32);
        set.applyTo(mConstraintLayout);
    }

    public void showAddPhotoButton() {
        mButtonAddPhoto.setVisibility(View.VISIBLE);
        ConstraintSet set = new ConstraintSet();
        set.clone(mConstraintLayout);
        set.connect(R.id.textViewIDField, ConstraintSet.TOP, mButtonAddPhoto.getId(), ConstraintSet.BOTTOM, dp32);
        set.applyTo(mConstraintLayout);
    }

    public void hideAddPhotoButton() {
        mButtonAddPhoto.setVisibility(View.GONE);
        ConstraintSet set = new ConstraintSet();
        set.clone(mConstraintLayout);
        set.connect(R.id.textViewIDField, ConstraintSet.TOP, mConstraintLayout.getId(), ConstraintSet.TOP, dp32);
        set.applyTo(mConstraintLayout);
    }

    public void showDeleteChangePhotoButton() {
        mButtonDeletePhoto.setVisibility(View.VISIBLE);
        mButtonChangePhoto.setVisibility(View.VISIBLE);
        ConstraintSet set = new ConstraintSet();
        set.clone(mConstraintLayout);
        set.connect(R.id.textViewIDField, ConstraintSet.TOP, mButtonDeletePhoto.getId(), ConstraintSet.BOTTOM, dp32);
        set.applyTo(mConstraintLayout);
    }

    public void hideDeleteChangePhotoButton() {
        mButtonDeletePhoto.setVisibility(View.GONE);
        mButtonChangePhoto.setVisibility(View.GONE);
        ConstraintSet set = new ConstraintSet();
        set.clone(mConstraintLayout);
        set.connect(R.id.textViewIDField, ConstraintSet.TOP, mPhoto.getId(), ConstraintSet.BOTTOM, dp32);
        set.applyTo(mConstraintLayout);
    }

    public void clickApplyButton(View view) {
        calf = tempCalf;

        for (int i = 0; i < calfList.size(); i++) {
            if (calfList.get(i).getFarmId().equals(calfID)) {
                calfList.set(i, calf);
            }
        }

        resetVisibility();

        tempID = mIDValue.getText().toString();
        tempSire = mSireValue.getText().toString();
        tempSireName = mSireNameValue.getText().toString();
        tempDam = mDamValue.getText().toString();
        tempGender = mGenderValue.getText().toString();
        tempDOBString = mDOBValue.getText().toString();

        saveData();

        originalImage = currentImage;

        // Scroll back to top since elements are shifting
        ((ScrollView) findViewById(R.id.calfProfileScrollLayout)).smoothScrollTo(0,0);

        // Update action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Calf " + tempID);
    }

    public void clickCancelButton(View view) {
        resetVisibility();

        mIDValue.setText(tempID);
        mSireValue.setText(tempSire);
        mSireNameValue.setText(tempSireName);
        mDamValue.setText(tempDam);
        mGenderValue.setText(tempGender);
        mDOBValue.setText(tempDOBString);

        // Deleted original photo = hide "add" button, show original photo
        if (currentImage == null && originalImage != null) {
            mButtonAddPhoto.setVisibility(View.GONE);
            currentImage = originalImage;
            mPhoto.setImageBitmap(originalImage);
            showPhoto();
        }

        // No original photo and no photo change = hide "add" button
        else if (currentImage == null && originalImage == null) {
            hideAddPhotoButton();
        }

        // Added new photo = hide photo, hide "delete/change" buttons
        else if (currentImage != null && originalImage == null) {
            currentImage = null;
            mButtonDeletePhoto.setVisibility(View.GONE);
            mButtonChangePhoto.setVisibility(View.GONE);
            hidePhoto();
        }

        // Changed original photo = hide "delete/change" buttons, change photo to original
        else if (currentImage != originalImage) {
            currentImage = originalImage;
            hideDeleteChangePhotoButton();
            mPhoto.setImageBitmap(originalImage);
        }

        // No changes = hide "delete/change" buttons
        else if (currentImage == originalImage) {
            hideDeleteChangePhotoButton();
        }

        // Scroll back to top since elements are shifting
        ((ScrollView) findViewById(R.id.calfProfileScrollLayout)).smoothScrollTo(0,0);

        // Update action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Calf " + calfID);
    }

    public void clickNewNoteButton(View view) {
        AlertDialog newNoteAlert;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogLayout_Note = inflater.inflate(R.layout.calf_profile_simple_edittext_dialog, null);
        final EditText dialogText_Note = (EditText) dialogLayout_Note.findViewById(R.id.dialogTextInput);

        dialogText_Note.setInputType(InputType.TYPE_CLASS_TEXT);
        dialogText_Note.setHint("Enter Text");
        builder.setTitle("Add Note");
        builder.setView(dialogLayout_Note);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String noteMessage = dialogText_Note.getText().toString();
                Calendar noteDate = Calendar.getInstance();

                Note newNote = new Note(noteMessage,noteDate);

                calf.addNote(newNote);

                for (int j = 0; j < calfList.size(); j++) {
                    if (calfList.get(j).getFarmId().equals(calfID)) {
                        calfList.set(j, calf);
                    }
                }

                saveData();

                updateNoteListView(calf);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        newNoteAlert = builder.create();

        newNoteAlert.show();
    }

    public void clickDeleteCalf(View view) {
        AlertDialog.Builder builderDelete = new AlertDialog.Builder(this);
        builderDelete.setMessage("Are you sure you want to delete this calf? This action cannot be undone.")
                .setTitle("Delete Calf");
        builderDelete.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Remove the calf from the list and save the new list to local storage
                calfList.remove(calf);
                saveData();

                // Show a toast saying that the calf was removed
                Context context = getApplicationContext();
                CharSequence text = "Calf " + calfID + " successfully removed";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                // Go back to the CalfList View
                Intent intent = new Intent(CalfProfileActivity.this, CalfListActivity.class);
                startActivity(intent);
            }
        });
        builderDelete.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Do nothing
            }
        });

        AlertDialog alertDelete = builderDelete.create();
        alertDelete.show();
    }

    public void onClickAddWeight(View view) {
        AlertDialog.Builder builderWeight = new AlertDialog.Builder(this);
        View dialogLayout_Weight = inflater.inflate(R.layout.calf_profile_simple_edittext_dialog, null);
        final EditText dialogText_Weight = (EditText) dialogLayout_Weight.findViewById(R.id.dialogTextInput);

        builderWeight.setTitle("Record Weight Measurement");

        dialogText_Weight.setHint("Weight (lbs)");
        builderWeight.setView(dialogLayout_Weight);
        builderWeight.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(dialogText_Weight.getText().toString().matches("")) {
                    Context context = getApplicationContext();
                    CharSequence text = "No weight entered";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    return;
                }

                double weight = Double.parseDouble(dialogText_Weight.getText().toString());
                Calendar today = Calendar.getInstance();
                Physical_Metrics_And_Date size = new Physical_Metrics_And_Date(weight, Calendar.getInstance());
                if (!calf.getPhysicalHistory().isEmpty()
                        && today.get(Calendar.YEAR) == calf.getPhysicalHistory().get(calf.getPhysicalHistory().size()-1).getDateRecorded().get(Calendar.YEAR)
                        && today.get(Calendar.MONTH) == calf.getPhysicalHistory().get(calf.getPhysicalHistory().size()-1).getDateRecorded().get(Calendar.MONTH)
                        && today.get(Calendar.DATE) == calf.getPhysicalHistory().get(calf.getPhysicalHistory().size()-1).getDateRecorded().get(Calendar.DATE)) {
                    if (calf.getPhysicalHistory().get(calf.getPhysicalHistory().size()-1).getWeight() != -1) {
                        Context context = getApplicationContext();
                        CharSequence text = "Previous weight recording from this day overwritten";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                    calf.getPhysicalHistory().get(calf.getPhysicalHistory().size()-1).setWeight(weight);
                } else {
                    calf.getPhysicalHistory().add(size);
                }
                mWeightValue.setText(Double.toString(weight) + " lbs");

                for (int j = 0; j < calfList.size(); j++) {
                    if (calfList.get(j).getFarmId().equals(calfID)) {
                        calfList.set(j, calf);
                    }
                }

                saveData();
            }
        });
        builderWeight.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertWeight = builderWeight.create();
        alertWeight.show();

    }

    public void onClickAddHeight(View view) {
        AlertDialog.Builder builderHeight = new AlertDialog.Builder(this);
        View dialogLayout_Height = inflater.inflate(R.layout.calf_profile_simple_edittext_dialog, null);
        final EditText dialogText_Height = (EditText) dialogLayout_Height.findViewById(R.id.dialogTextInput);

        builderHeight.setTitle("Record Height Measurement");
        dialogText_Height.setHint("Height (in)");
        builderHeight.setView(dialogLayout_Height);
        builderHeight.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(dialogText_Height.getText().toString().matches("")) {
                    Context context = getApplicationContext();
                    CharSequence text = "No weight entered";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    return;
                }

                double height = Double.parseDouble(dialogText_Height.getText().toString());
                Calendar today = Calendar.getInstance();
                Physical_Metrics_And_Date size = new Physical_Metrics_And_Date(Calendar.getInstance(), height);

                if (!calf.getPhysicalHistory().isEmpty()
                        && today.get(Calendar.YEAR) == calf.getPhysicalHistory().get(calf.getPhysicalHistory().size()-1).getDateRecorded().get(Calendar.YEAR)
                        && today.get(Calendar.MONTH) == calf.getPhysicalHistory().get(calf.getPhysicalHistory().size()-1).getDateRecorded().get(Calendar.MONTH)
                        && today.get(Calendar.DATE) == calf.getPhysicalHistory().get(calf.getPhysicalHistory().size()-1).getDateRecorded().get(Calendar.DATE)) {
                    if (calf.getPhysicalHistory().get(calf.getPhysicalHistory().size()-1).getHeight() != -1) {
                        Context context = getApplicationContext();
                        CharSequence text = "Previous height recording from this day overwritten";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                    calf.getPhysicalHistory().get(calf.getPhysicalHistory().size()-1).setHeight(height);

                } else {
                    calf.getPhysicalHistory().add(size);
                }
                mHeightValue.setText(Double.toString(height) + " in");

                for (int j = 0; j < calfList.size(); j++) {
                    if (calfList.get(j).getFarmId().equals(calfID)) {
                        calfList.set(j, calf);
                    }
                }

                saveData();
            }
        });
        builderHeight.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertHeight = builderHeight.create();
        alertHeight.show();
    }

    public void onClickGrowthHistory(View view) {
        Intent intent = new Intent(this, CalfProfileGrowthHistoryActivity.class);
        startActivity(intent);
    }

    public void onClickFeedingHistory(View view) {
        Intent intent = new Intent(this,CalfProfileFeedingHistoryActivity.class);
        startActivity(intent);
    }

    public void onClickMedicalHistory(View view) {
        Intent intent = new Intent(this,CalfProfileMedicalHistoryActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, CalfListActivity.class);
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(getApplicationContext(), CalfListActivity.class);
        startActivity(intent);
        return true;
    }

    public void resetVisibility() {
        mButtonAddPhoto.setVisibility(View.GONE);
        mButtonDeletePhoto.setVisibility(View.GONE);
        mButtonChangePhoto.setVisibility(View.GONE);
        mNoteListView.setVisibility(View.VISIBLE);
        mWeightValue.setVisibility(View.VISIBLE);
        mHeightValue.setVisibility(View.VISIBLE);
        findViewById(R.id.buttonApply).setVisibility(View.GONE);
        findViewById(R.id.buttonCancel).setVisibility(View.GONE);
        findViewById(R.id.buttonDeleteCalf).setVisibility(View.GONE);
        findViewById(R.id.buttonAddWeight).setVisibility(View.VISIBLE);
        findViewById(R.id.buttonAddHeight).setVisibility(View.VISIBLE);
        findViewById(R.id.buttonCreateNewNote).setVisibility(View.VISIBLE);
        findViewById(R.id.floatingActionButtonEDIT).setVisibility(View.VISIBLE);
        findViewById(R.id.buttonFeedingHistory).setVisibility(View.VISIBLE);
        findViewById(R.id.buttonGrowthHistory).setVisibility(View.VISIBLE);
        findViewById(R.id.buttonMedicalHistory).setVisibility(View.VISIBLE);
        findViewById(R.id.textViewNotes).setVisibility(View.VISIBLE);
        findViewById(R.id.textViewWeightField).setVisibility(View.VISIBLE);
        findViewById(R.id.textViewHeightField).setVisibility(View.VISIBLE);
        findViewById(R.id.buttonMarkObservation).setVisibility(View.VISIBLE);

        mIDValue.setPaintFlags(mIDValue.getPaintFlags() & (~Paint.UNDERLINE_TEXT_FLAG));
        mGenderValue.setPaintFlags(mGenderValue.getPaintFlags() & (~Paint.UNDERLINE_TEXT_FLAG));
        mDOBValue.setPaintFlags(mDOBValue.getPaintFlags() & (~Paint.UNDERLINE_TEXT_FLAG));
        mSireValue.setPaintFlags(mSireValue.getPaintFlags() & (~Paint.UNDERLINE_TEXT_FLAG));
        mSireNameValue.setPaintFlags(mSireNameValue.getPaintFlags() & (~Paint.UNDERLINE_TEXT_FLAG));
        mDamValue.setPaintFlags(mDamValue.getPaintFlags() & (~Paint.UNDERLINE_TEXT_FLAG));

        mIDValue.setOnClickListener(null);
        mSireValue.setOnClickListener(null);
        mSireNameValue.setOnClickListener(null);
        mDamValue.setOnClickListener(null);
        mGenderValue.setOnClickListener(null);
        mDOBValue.setOnClickListener(null);
    }

    public void onClickMarkObservation(View view) {
        Button mMarkObservation = (Button) findViewById(R.id.buttonMarkObservation);
        if(calf.isNeedToObserveForIllness())
            mMarkObservation.setText(R.string.calf_profile_mark_oberservation);
        else
            mMarkObservation.setText(R.string.calf_profile_unmark_oberservation);

        calf.setNeedToObserveForIllness(!calf.isNeedToObserveForIllness());

        saveData();
    }
}
