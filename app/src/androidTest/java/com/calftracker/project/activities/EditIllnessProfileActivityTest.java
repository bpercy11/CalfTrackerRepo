package com.calftracker.project.activities;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

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

import com.calftracker.project.calftracker.R;

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
        onView(withId(R.id.editIllnessProfile_illnessName)).perform(click());
        onView(withId(R.id.editIllnessProfile_illnessName)).perform(ViewActions.typeText(name),closeSoftKeyboard());
        onView(withId(R.id.editIllnessProfile_illnessName)).check(matches(withText(name)));

    }

    @Test
    public void testEditNotes() throws Exception{
        String notes = "these are my notes";
        onView(withId(R.id.editIllnessProfile_notes)).perform(click());
        onView(withId(R.id.editIllnessProfile_notes)).perform(ViewActions.typeText(notes),closeSoftKeyboard());
        onView(withId(R.id.editIllnessProfile_notes)).check(matches(withText(notes)));
    }

    @Test
    public void testCancelEditIllnessButton() throws Exception{
        onView(withId(R.id.editIllness_buttonCancel)).perform(click());
        intended(hasComponent(IllnessProfileActivity.class.getName()));
    }

    @Test
    public void testAddEditedIllnessButton() throws Exception{
        onView(withId(R.id.editIllness_buttonAdd)).perform(click());
        intended(hasComponent(EditIllnessProfileMedicineSelectionActivity.class.getName()));
    }

}
