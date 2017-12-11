package com.calftracker.project.activities;

import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;

import com.calftracker.project.calftracker.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Created by AlexanderGlowacki on 11/25/17.
 */
public class VaccineProfileActivityTest {

    @Rule
    public ActivityTestRule<VaccineProfileActivity> mActivityTestRule = new ActivityTestRule(VaccineProfileActivity.class);
    private VaccineProfileActivity mActivity = null;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
        Intents.init();
    }

//    @Test
//    public void testVProfileEditButton() throws Exception {
//        onView(withId(R.id.vaccine_profile_editButton)).perform(click());
//        intended(hasComponent(EditVaccineProfileActivity.class.getName()));
//    }

    @Test
    public void testVProfileRemoveButton() throws Exception {
        onView(withId(R.id.vaccine_profile_removeButton)).perform(click());
        intended(hasComponent(VaccineActivity.class.getName()));
    }
    @After
    public void tearDown() throws Exception {
        mActivity = null;
        Intents.release();
    }

}