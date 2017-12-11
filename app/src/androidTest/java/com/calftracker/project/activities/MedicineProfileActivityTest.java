package com.calftracker.project.activities;

import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;

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

//    @Test
//    public void testMProfileEditButton() throws Exception {
//        onView(withId(R.id.medicine_profile_editButton)).perform(click());
//        intended(hasComponent(EditMedicineProfileActivity.class.getName()));
//    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
        Intents.release();
    }

}