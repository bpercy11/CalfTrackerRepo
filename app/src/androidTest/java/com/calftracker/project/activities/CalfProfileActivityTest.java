package com.calftracker.project.activities;

import android.content.ComponentName;
import android.provider.MediaStore;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.PickerActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.widget.DatePicker;

import com.calftracker.project.calftracker.R;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;

import java.util.Calendar;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class CalfProfileActivityTest {
    @Rule
    public IntentsTestRule<CalfProfileActivity> mIntentsTestRule = new IntentsTestRule(CalfProfileActivity.class);

    @Test
    public void testEditAddPhoto() throws Exception {
        onView(withId(R.id.floatingActionButtonEDIT)).perform(click());
        onView(withId(R.id.buttonAddPhoto)).perform(click());
        intended(hasAction(MediaStore.ACTION_IMAGE_CAPTURE));
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

    @Test
    public void testRecordWeight() throws Exception {
        String newWeight = "64.2";
        onView(withId(R.id.buttonAddWeight)).perform(click());
        onView(withId(R.id.dialogTextInput)).perform(ViewActions.typeText(newWeight), closeSoftKeyboard());
        onView(withText("Ok")).perform(click());
        onView(withId(R.id.textViewWeightValue)).check(matches(withText(newWeight + " lbs")));
    }

    @Test
    public void testRecordHeight() throws Exception {
        String newHeight = "54.3";
        onView(withId(R.id.buttonAddHeight)).perform(click());
        onView(withId(R.id.dialogTextInput)).perform(ViewActions.typeText(newHeight), closeSoftKeyboard());
        onView(withText("Ok")).perform(click());
        onView(withId(R.id.textViewHeightValue)).check(matches(withText(newHeight + " in")));
    }

    @Test
    public void testOpenGrowthHistory() throws Exception {
        onView(withId(R.id.buttonGrowthHistory)).perform(click());
        intended(hasComponent(new ComponentName(getTargetContext(), CalfProfileGrowthHistoryActivity.class)));
    }

    @Test
    public void testOpenFeedingHistory() throws Exception {
        onView(withId(R.id.buttonFeedingHistory)).perform(click());
        intended(hasComponent(new ComponentName(getTargetContext(), CalfProfileFeedingHistoryActivity.class)));
    }

    @Test
    public void testOpenMedicalHistory() throws Exception {
        onView(withId(R.id.buttonCreateNewNote)).perform(scrollTo());   // Have to do this to get around the FAB button
        onView(withId(R.id.buttonMedicalHistory)).perform(click());
        intended(hasComponent(new ComponentName(getTargetContext(), CalfProfileMedicalHistoryActivity.class)));
    }

    @Test
    public void testAddNote() throws Exception {
        String newNote = "Test note";
        Calendar currentDate = Calendar.getInstance();
        int day = currentDate.get(Calendar.DAY_OF_MONTH);
        int month = currentDate.get(Calendar.MONTH);
        int year = currentDate.get(Calendar.YEAR);
        String date = month + "/" + day + "/" + year;

        onView(withId(R.id.buttonCreateNewNote))
                .perform(scrollTo())
                .perform(click());
        onView(withId(R.id.dialogTextInput)).perform(ViewActions.typeText(newNote), closeSoftKeyboard());
        onView(withText("Ok")).perform(click());
        // TODO
    }
}