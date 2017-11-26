package com.calftracker.project.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;

import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Vacc_Range;
import com.calftracker.project.models.Vaccine;
import com.google.gson.Gson;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Created by AlexanderGlowacki on 11/25/17.
 */
public class EditVaccineActivityTest {


    @Rule
    public ActivityTestRule<EditVaccineActivity> mActivityTestRule = new ActivityTestRule(EditVaccineActivity.class);
    private EditVaccineActivity mActivity = null;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
        Intents.init();
    }

    @Test
    public void testEditVaccineName() throws Exception {
        String newName = "Vaccine Name";
        onView(withId(R.id.edit_vaccine_editTextName)).perform(click());
        onView(withId(R.id.edit_vaccine_editTextName)).perform(ViewActions.typeText(newName),closeSoftKeyboard());
        onView(withId(R.id.edit_vaccine_editTextName)).check(matches(withText(newName)));
    }

    @Test
    public void testEditVaccineRangeStart()  throws Exception {
        int rangeStart = 4;
        onView(withId(R.id.edit_vaccine_editTextAdminStart)).perform(click());
        onView(withId(R.id.edit_vaccine_editTextAdminStart)).perform(ViewActions.typeText(Integer.toString(rangeStart)),closeSoftKeyboard());
        onView(withId(R.id.edit_vaccine_editTextAdminStart)).check(matches(withText(Integer.toString(rangeStart))));
    }

    @Test
    public void testEditVaccineRangeStartSpinner()  throws Exception {
        String week = "Week";
        onView(withId(R.id.edit_vaccine_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(week))).perform(click());
        onView(withId(R.id.edit_vaccine_spinner)).check(matches(withSpinnerText(containsString(week))));
    }

    @Test
    public void testEditVaccineRangeEndSpinner()  throws Exception {
        String week = "Week";
        onView(withId(R.id.edit_vaccine_spinner1)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(week))).perform(click());
        onView(withId(R.id.edit_vaccine_spinner1)).check(matches(withSpinnerText(containsString(week))));
    }

    @Test
    public void testEditVaccineRangeEnd() throws Exception {
        int rangeEnd = 8;
        onView(withId(R.id.edit_vaccine_editTextAdminEnd)).perform(click());
        onView(withId(R.id.edit_vaccine_editTextAdminEnd)).perform(ViewActions.typeText(Integer.toString(rangeEnd)),closeSoftKeyboard());
        onView(withId(R.id.edit_vaccine_editTextAdminEnd)).check(matches(withText(Integer.toString(rangeEnd))));

    }

    @Test
    public void testEditVaccineDosage() throws Exception {
        Double newDosage = 15.2;
        onView(withId(R.id.edit_vaccine_editTextDosage)).perform(click());
        onView(withId(R.id.edit_vaccine_editTextDosage)).perform(ViewActions.typeText(Double.toString(newDosage)),closeSoftKeyboard());
        onView(withId(R.id.edit_vaccine_editTextDosage)).check(matches(withText(Double.toString(newDosage))));

    }

    @Test
    public void testEditVaccineDosageUnits() throws Exception {
        String newDosageUnits = "Dosage Units";
        onView(withId(R.id.edit_vaccine_editTextDosageUnits)).perform(click());
        onView(withId(R.id.edit_vaccine_editTextDosageUnits)).perform(ViewActions.typeText(newDosageUnits),closeSoftKeyboard());
        onView(withId(R.id.edit_vaccine_editTextDosageUnits)).check(matches(withText(newDosageUnits)));
    }

    @Test
    public void testEditVaccineAdminMethod() throws Exception {
        String newAdminMethod = "Admin Method";
        onView(withId(R.id.edit_vaccine_editTextAdminMethod)).perform(click());
        onView(withId(R.id.edit_vaccine_editTextAdminMethod)).perform(ViewActions.typeText(newAdminMethod),closeSoftKeyboard());
        onView(withId(R.id.edit_vaccine_editTextAdminMethod)).check(matches(withText(newAdminMethod)));
    }

    @Test
    public void testCancelButton() throws Exception {
        onView(withId(R.id.edit_vaccine_buttonCancel)).perform(click());
        intended(hasComponent(VaccineActivity.class.getName()));
    }

    @Test
    public void testAddButton() throws Exception {
        String newName = "Vaccine Name";
        int rangeStart = 4;
        String day = "Day";
        int rangeEnd = 8;
        Double newDosage = 15.2;
        String newDosageUnits = "Dosage Units";
        String newAdminMethod = "Admin Method";

        //Vaccine Name
        onView(withId(R.id.edit_vaccine_editTextName)).perform(click());
        onView(withId(R.id.edit_vaccine_editTextName)).perform(ViewActions.typeText(newName),closeSoftKeyboard());
        //Vaccine Range Start
        onView(withId(R.id.edit_vaccine_editTextAdminStart)).perform(click());
        onView(withId(R.id.edit_vaccine_editTextAdminStart)).perform(ViewActions.typeText(Integer.toString(rangeStart)),closeSoftKeyboard());
        //Vaccine Start Spinner
        onView(withId(R.id.edit_vaccine_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(day))).perform(click());
        //Vaccine Range End
        onView(withId(R.id.edit_vaccine_editTextAdminEnd)).perform(click());
        onView(withId(R.id.edit_vaccine_editTextAdminEnd)).perform(ViewActions.typeText(Integer.toString(rangeEnd)),closeSoftKeyboard());
        //Vaccine End Spinner
        onView(withId(R.id.edit_vaccine_spinner1)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(day))).perform(click());
        //Vaccine Dosage
        onView(withId(R.id.edit_vaccine_editTextDosage)).perform(click());
        onView(withId(R.id.edit_vaccine_editTextDosage)).perform(ViewActions.typeText(Double.toString(newDosage)),closeSoftKeyboard());
        //Vaccine Dosage Units
        onView(withId(R.id.edit_vaccine_editTextDosageUnits)).perform(click());
        onView(withId(R.id.edit_vaccine_editTextDosageUnits)).perform(ViewActions.typeText(newDosageUnits),closeSoftKeyboard());
        //Vaccine Administration Method
        onView(withId(R.id.edit_vaccine_editTextAdminMethod)).perform(click());
        onView(withId(R.id.edit_vaccine_editTextAdminMethod)).perform(ViewActions.typeText(newAdminMethod),closeSoftKeyboard());

        onView(withId(R.id.edit_vaccine_buttonAddVaccine)).perform(click());
        intended(hasComponent(VaccineActivity.class.getName()));
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
        Intents.release();
    }

}