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

/**
 * Created by Alayna on 11/21/2017.
 */
public class AddCalfActivityTest {
    @Rule
    public ActivityTestRule<AddCalfActivity> mActivityTestRule = new ActivityTestRule(AddCalfActivity.class);
    private AddCalfActivity mActivity = null;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void testEnterId() throws Exception {
        onView(withId(R.id.editTextGetID)).perform(ViewActions.typeText("123"), closeSoftKeyboard());
        onView(withId(R.id.editTextGetID)).check(matches(withText("123")));
    }

    @Test
    public void testEnterGender() throws Exception {
        int year = 2017;
        int month = 11;
        int day = 21;

        onView(withId(R.id.textViewDisplayDate)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(year, month, day));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.textViewDisplayDate)).check(matches(withText(month + "/" + day + "/" + year)));
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }

}