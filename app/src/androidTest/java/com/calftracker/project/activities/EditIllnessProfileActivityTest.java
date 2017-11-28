package com.calftracker.project.activities;

import android.support.test.espresso.PerformException;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;

import com.calftracker.project.calftracker.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Lisa on 11/27/2017.
 */

public class EditIllnessProfileActivityTest {

    @Rule
    public ActivityTestRule<EditIllnessProfileActivity > mActivityTestRule = new ActivityTestRule(EditIllnessProfileActivity.class);
    private EditIllnessProfileActivity mActivity = null;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
        Intents.init();
    }

    @Test
    public void testEditIllnessName() throws Exception{
        String name = "illnessName";
        onView(withId(R.id.editIllnessProfile_enterName)).perform(click());
        onView(withId(R.id.editIllnessProfile_enterName)).perform(replaceText(name),closeSoftKeyboard());
        onView(withId(R.id.editIllnessProfile_enterName)).check(matches(withText(name)));
    }

    @Test
    public void testEditNotes() throws Exception{
        String notes = "these are my notes";
        onView(withId(R.id.editIllnessProfile_enterNotes)).perform(click());
        onView(withId(R.id.editIllnessProfile_enterNotes)).perform(ViewActions.replaceText(notes), closeSoftKeyboard());
        onView(withId(R.id.editIllnessProfile_enterNotes)).check(matches(withText(notes)));
    }

    @Test
    public void testCancelEditIllnessButton() throws Exception{
        closeSoftKeyboard();
        onView(withId(R.id.editIllness_buttonCancel)).perform(click());
        intended(hasComponent(IllnessActivity.class.getName()));
    }

    @Test
    public void testAddEditedIllnessButton() throws Exception{
        try {
            closeSoftKeyboard();
            onView(withId(R.id.editIllness_buttonAdd)).perform(click());
            intended(hasComponent(EditIllnessProfileMedicineSelectionActivity.class.getName()));
        }
        catch (PerformException e) { }  // Case no illnesses
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
        Intents.release();
    }
}
