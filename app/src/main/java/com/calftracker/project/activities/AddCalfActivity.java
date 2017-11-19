package com.calftracker.project.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
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
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class AddCalfActivity extends BaseActivity {
    // honestly not sure what this does -JT
    private static final String TAG = "AddCalfActivity";

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

    // variables related to taking a photo
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Button mAddPhoto;
    private ImageView mImageCaptured;
    private String encodedImage = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_add_calf, frameLayout);
        mNavigationView.getMenu().findItem(R.id.nav_add).setChecked(true);

        // set UI to hide keyboard when user clicks anywhere off the keyboard
        setupUI(findViewById(R.id.addCalfParent));

        // Sets the default date to be today in case this field is left blank
        Calendar today = Calendar.getInstance();
        calfYear = today.get(Calendar.YEAR);
        calfMonth = today.get(Calendar.MONTH);
        calfDay = today.get(Calendar.DATE);

        // get needed UI elements
        mDisplayDate = (TextView) findViewById(R.id.textViewDisplayDate);
        mGender = (TextView) findViewById(R.id.textViewSelectGender);
        mAddPhoto = (Button) findViewById(R.id.buttonTakePhoto);
        mImageCaptured = (ImageView) findViewById(R.id.imageViewCaptured);

        // hide unneeded UI elements
        mImageCaptured.setVisibility(View.GONE);

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
        EditText ID = (EditText) findViewById(R.id.editTextGetID);
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

        // error checking on calf id input length
        if (calfID.length() > 9 || calfID.length() < 1) {
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
            prefsEditor.putString("newCalfPhoto",json);     //TODO
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
    public void clickPhotoButton(View view) {
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
            mAddPhoto.setVisibility(View.GONE);
            mImageCaptured.setVisibility(View.VISIBLE);
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            // Set image thumbnail
            mImageCaptured.setImageBitmap(imageBitmap);

            // Prepare image for saving
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
        }
    }
}
