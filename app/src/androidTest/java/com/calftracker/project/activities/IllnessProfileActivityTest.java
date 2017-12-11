package com.calftracker.project.activities;

import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;

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

    @After
    public void tearDown() throws Exception {
        mActivity = null;
        Intents.release();
    }

}