package com.calftracker.project.activities;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;

import com.calftracker.project.calftracker.R;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

public class DashboardActivityTest {
    @Rule
    public IntentsTestRule<DashboardActivity> mIntentsTestRuleDashboard = new IntentsTestRule<DashboardActivity>(DashboardActivity.class);

    @Test
    public void testOpenCalfList() throws Exception {
        onView(allOf(withText(R.string.calf_list_title), withId(R.id.grid_text))).perform(click());
        intended(hasComponent(CalfListActivity.class.getName()));
    }

    @Test
    public void testOpenAddCalf() throws Exception {
        onView(allOf(withText(R.string.add_calf_title), withId(R.id.grid_text))).perform(click());
        intended(hasComponent(AddCalfActivity.class.getName()));
    }

    @Test
    public void testOpenTasks() throws Exception {
        onView(allOf(withText(R.string.tasks_title), withId(R.id.grid_text))).perform(click());
        intended(hasComponent(TasksActivity.class.getName()));
    }

    @Test
    public void testOpenProtocols() throws Exception {
        onView(allOf(withText(R.string.protocols_title), withId(R.id.grid_text))).perform(click());
        intended(hasComponent(VaccineActivity.class.getName()));
    }

    @Test
    public void testOpenSettings() throws Exception {
        onView(ViewMatchers.withId(R.id.grid)).perform(ViewActions.swipeUp());  // Scroll to bottom so settings is visible
        onView(allOf(withText(R.string.settings_title), withId(R.id.grid_text))).perform(click());
        intended(hasComponent(SettingsActivity.class.getName()));
    }
}