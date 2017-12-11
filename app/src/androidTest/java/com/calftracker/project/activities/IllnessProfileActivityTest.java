package com.calftracker.project.activities;

import android.support.test.espresso.PerformException;
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

import com.calftracker.project.calftracker.R;

/**
 * Created by AlexanderGlowacki on 11/26/17.
 */
public class IllnessProfileActivityTest {

    @Rule
    public ActivityTestRule<IllnessProfileActivity> mActivityTestRule = new ActivityTestRule(IllnessProfileActivity.class);
    private IllnessProfileActivity mActivity = null;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
        Intents.init();
    }

//    @Test
//    public void testIProfileEditButton() throws Exception {
//        onView(withId(R.id.illnessProfileEditButton)).perform(click());
//        intended(hasComponent(EditIllnessProfileActivity.class.getName()));
//    }

    @Test
    public void testIProfileRemoveButton() throws Exception {
        try {
            onView(withId(R.id.illness_profile_removeButton)).perform(click());
            intended(hasComponent(IllnessActivity.class.getName()));
        }
        catch (PerformException e) { }  // Case no illnesses
    }
    @After
    public void tearDown() throws Exception {
        mActivity = null;
        Intents.release();
    }

}