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

/**
 * Created by AlexanderGlowacki on 11/26/17.
 */
public class MedicineProfileActivityTest {

    @Rule
    public ActivityTestRule<MedicineProfileActivity> mActivityTestRule = new ActivityTestRule(MedicineProfileActivity.class);
    private MedicineProfileActivity mActivity = null;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
        Intents.init();
    }

    @Test
    public void testMProfileEditButton() throws Exception {
        onView(withId(R.id.medicine_profile_editButton)).perform(click());
        intended(hasComponent(EditMedicineProfileActivity.class.getName()));
    }

    @Test
    public void testMProfileRemoveButton() throws Exception {
        try {
            onView(withId(R.id.medicine_profile_removeButton)).perform(click());
            intended(hasComponent(MedicineActivity.class.getName()));
        }
        catch (PerformException e) { }  // Case no medicines to delete
    }
    @After
    public void tearDown() throws Exception {
        mActivity = null;
        Intents.release();
    }

}