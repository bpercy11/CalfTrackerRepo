package com.calftracker.project.activities;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.PickerActions;
import android.support.test.rule.ActivityTestRule;
import android.widget.DatePicker;

import com.calftracker.project.calftracker.R;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class CalfProfileActivityTest {
    @Rule
    public ActivityTestRule<CalfProfileActivity> mActivityTestRule = new ActivityTestRule(CalfProfileActivity.class);
    private CalfProfileActivity mActivity = null;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void testEditId() throws Exception {
        String newId = "123";
        onView(withId(R.id.floatingActionButtonEDIT)).perform(click());
        onView(withId(R.id.textViewIDValue)).perform(click());
        onView(withId(R.id.dialogTextInput)).perform(ViewActions.typeText(newId), closeSoftKeyboard());
        onView(withText("Ok")).perform(click());
        onView(withId(R.id.textViewIDValue)).check(matches(withText(newId)));
    }

    @Test
    public void testEditGender() throws Exception {
        onView(withId(R.id.floatingActionButtonEDIT)).perform(click());
        String gender = "Female";
        onView(withId(R.id.textViewGenderValue)).perform(click());
        onView(withText(gender)).perform(click());
        onView(withId(R.id.textViewGenderValue)).check(matches(withText(gender)));
    }

    @Test
    public void testEditDOB() throws Exception {
        int year = 2017;
        int month = 11;
        int day = 24;

        onView(withId(R.id.floatingActionButtonEDIT)).perform(click());
        onView(withId(R.id.textViewDOBValue)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(year, month, day));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.textViewDOBValue)).check(matches(withText(month + "/" + day + "/" + year)));
    }

    @Test
    public void testEditSire() throws Exception {
        String newId = "456";
        onView(withId(R.id.floatingActionButtonEDIT)).perform(click());
        onView(withId(R.id.textViewSireValue)).perform(click());
        onView(withId(R.id.dialogTextInput)).perform(ViewActions.typeText(newId), closeSoftKeyboard());
        onView(withText("Ok")).perform(click());
        onView(withId(R.id.textViewSireValue)).check(matches(withText(newId)));
    }

    @Test
    public void testEditDam() throws Exception {
        String newId = "789";
        onView(withId(R.id.floatingActionButtonEDIT)).perform(click());
        onView(withId(R.id.textViewDamValue)).perform(click());
        onView(withId(R.id.dialogTextInput)).perform(ViewActions.typeText(newId), closeSoftKeyboard());
        onView(withText("Ok")).perform(click());
        onView(withId(R.id.textViewDamValue)).check(matches(withText(newId)));
    }

    // TODO
    /*@Test
    public void testAddPhoto() throws Exception {
        mActivityTestRule.getActivity().findViewById(R.id.textViewIDValue).getSystemUiVisibility();
        onView(withId(R.id.buttonAddPhoto)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }*/

    //TODO
    /*@Test
    public void testCancel() throws Exception {

        // Get current values
        mActivityTestRule.getActivity().findViewById(R.id.textViewIDValue);

        // Edit these values
        onView(withId(R.id.floatingActionButtonEDIT)).perform(click());


        // Cancel and assert values haven't changed
        onView(withId(R.id.buttonCancel)).perform(click());

    }*/

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }
}