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
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Base64;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Calf;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class AddCalfActivity extends BaseActivity {
    // honestly not sure what this does -JT
    private static final String TAG = "AddCalfActivity";

    private ArrayList<Calf> calfList;

    // variables related to date selection
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private int calfYear;
    private int calfMonth;
    private int calfDay;

    // variables related to gender selection
    private TextView mGender;
    private Dialog mGenderListDialog;
    private String[] gender = {"Male","Female"};
    private AlertDialog alert;
    private String calfGender;

    private EditText ID;

    // variables related to taking a photo
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView mPhoto;
    private String encodedImage;
    private ConstraintLayout mConstraintLayout;
    private Button mButtonAddPhoto;
    private Button mButtonDeletePhoto;
    private Button mButtonChangePhoto;
    private int dp32;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_add_calf, frameLayout);
        mNavigationView.getMenu().findItem(R.id.nav_add).setChecked(true);

        // Custom title
        getSupportActionBar().setTitle(R.string.add_calf_title);

        // set UI to hide keyboard when user clicks anywhere off the keyboard
        setupUI(findViewById(R.id.addCalfParent));



        // Sets the default date to be today in case this field is left blank
        Calendar today = Calendar.getInstance();
        calfYear = today.get(Calendar.YEAR);
        calfMonth = today.get(Calendar.MONTH);
        calfDay = today.get(Calendar.DATE);

        // get needed UI elements
        ID = (EditText) findViewById(R.id.editTextGetID);
        mDisplayDate = (TextView) findViewById(R.id.textViewDisplayDate);
        mGender = (TextView) findViewById(R.id.textViewSelectGender);
        mPhoto = (ImageView) findViewById(R.id.imageViewCaptured);
        mConstraintLayout = (ConstraintLayout) findViewById(R.id.addCalfLayout);
        mButtonAddPhoto = (Button) findViewById(R.id.buttonNewAddPhoto);
        mButtonDeletePhoto = (Button) findViewById(R.id.buttonNewDeletePhoto);
        mButtonChangePhoto = (Button) findViewById(R.id.buttonNewChangePhoto);

        // set up shared preference variables
        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        Gson gson = new Gson();
        String json;

        // Load the Task object from storage
        json = mPreferences.getString("BackToAddScreen", "");
        Calf calfFromBack= gson.fromJson(json, new TypeToken<Calf>() {}.getType());

        if(calfFromBack != null) {
            ID.setText(calfFromBack.getFarmId());
            mGender.setText(calfFromBack.getGender());

            int year = calfFromBack.makeCalendarDOB().get(Calendar.YEAR);
            int month = calfFromBack.makeCalendarDOB().get(Calendar.MONTH) + 1;
            int day = calfFromBack.makeCalendarDOB().get(Calendar.DAY_OF_MONTH);

            String date = month + "/" + day + "/" + year;
            mDisplayDate.setText(date);

            calfFromBack = null;

            SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
            SharedPreferences.Editor prefsEditor = mPrefs.edit();
            json = gson.toJson(calfFromBack);
            prefsEditor.putString("BackToAddScreen",json);
            prefsEditor.apply();
        }

        // Calculate 32dp in pixels, to be used when changing margins for photo tools
        Resources r = getResources();
        dp32 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, r.getDisplayMetrics());

        // Hide photo buttons except for add
        mButtonDeletePhoto.setVisibility(View.GONE);
        mButtonChangePhoto.setVisibility(View.GONE);
        mPhoto.setVisibility(View.GONE);

        // when the date entry field is clicked open a dialog for the user
        // to select a date, using the android datepicker fragment
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddCalfActivity.this,
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
                calfYear = year;
                calfMonth = month;
                calfDay = day;
                month = month + 1;
                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
            }
        };

        // build an alert for selecting the gender input field
        mGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.show();
            }
        });

        // display a dialog that prompted the user to selected "male" or "female"
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Gender");
        builder.setItems(gender, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mGender.setText(gender[i]);
                calfGender = gender[i];
            }
        });
        alert = builder.create();
    }

    // TODO
    public void saveData() {
        // EMPTY METHOD TO KEEP CONSISTENCY
        // NO DATA IS SAVED IN THIS ACTIVITY
    }

    public void retrieveData() {
        SharedPreferences mPreferences = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPreferences.getString("CalfList","");
        calfList = gson.fromJson(json, new TypeToken<ArrayList<Calf>>(){}.getType());
    }

    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(AddCalfActivity.this);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    public void clickAddCalfButton(View view) {
        // GET USER INPUT FOR ID NUMBER FROM EDITTEXT
        String calfID = ID.getText().toString();
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        // Go here if the user does not enter a Calf ID
        if (calfID.matches("") || mGender.getText().toString().matches("")) {
            CharSequence text = "Please complete all fields";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }

        // Check to make sure a duplicate calf is not being created
        retrieveData();
        for (int i = 0; i < calfList.size(); i++) {
            if (calfList.get(i).getFarmId().equals(calfID)) {
                ID.setText("");
                CharSequence text = "A calf with this ID already exists, please choose a new ID or delete the other calf";
                Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
                toast.show();
                return;
            }
        }

        // error checking on calf id input length
        if (calfID.length() > 9 || calfID.length() < 1) {
            ID.setText("");
            CharSequence text = "ID number must be between 1 and 9 digits";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }

        // make a calendar object out of the user input to be used in
        // new calf object in next view (vaccine selection)
        Calendar calfCal = new GregorianCalendar(calfYear,calfMonth,calfDay);

        // Save the calf information to shared prefences so the vaccine selection view
        // can get the user input
        SharedPreferences mPrefs = getSharedPreferences("CalfTracker", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json;
        prefsEditor = mPrefs.edit();

        // save calf ID
        json = gson.toJson(calfID);
        prefsEditor.putString("newCalfID",json);
        prefsEditor.apply();

        // save calf gender
        json = gson.toJson(calfGender);
        prefsEditor.putString("newCalfGender",json);
        prefsEditor.apply();

        // save calf image
        if (encodedImage != null) {
            json = gson.toJson(encodedImage);
            prefsEditor.putString("newCalfPhoto",json);
            prefsEditor.apply();
        }
        else {
            json = gson.toJson(null);
            prefsEditor.putString("newCalfPhoto",json);
            prefsEditor.apply();
        }

        // save calf calendar
        json = gson.toJson(calfCal);
        prefsEditor.putString("newCalfCal",json);
        prefsEditor.apply();

        // GO TO SELECT VACCINES ACTIVITY
        Intent intent = new Intent(this,NewCalfVaccineSelectionActivity.class);
        startActivity(intent);
    }

    // cancel just brings you back to the dashboard and no information entered by the
    // user is saved
    public void clickCancelButton(View view) {
        Intent intent = new Intent(this,DashboardActivity.class);
        startActivity(intent);
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

            // Set image thumbnail
            mPhoto.setImageBitmap(imageBitmap);

            // Prepare image for saving
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
        }
    }

    public void onClickDeletePhotoButton(View view) {
        mPhoto.setVisibility(View.GONE);
        mButtonDeletePhoto.setVisibility(View.GONE);
        mButtonChangePhoto.setVisibility(View.GONE);
        showAddPhotoButton();
    }

    public void showAddPhotoButton() {
        mButtonAddPhoto.setVisibility(View.VISIBLE);
        ConstraintSet set = new ConstraintSet();
        set.clone(mConstraintLayout);
        set.connect(R.id.textViewIDNumber, ConstraintSet.TOP, mButtonAddPhoto.getId(), ConstraintSet.BOTTOM, dp32);
        set.applyTo(mConstraintLayout);
    }

    public void showDeleteChangePhotoButton() {
        mButtonDeletePhoto.setVisibility(View.VISIBLE);
        mButtonChangePhoto.setVisibility(View.VISIBLE);
        ConstraintSet set = new ConstraintSet();
        set.clone(mConstraintLayout);
        set.connect(R.id.textViewIDNumber, ConstraintSet.TOP, mButtonDeletePhoto.getId(), ConstraintSet.BOTTOM, dp32);
        set.applyTo(mConstraintLayout);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(this, DashboardActivity.class);
            startActivity(intent);
        }
    }
}
