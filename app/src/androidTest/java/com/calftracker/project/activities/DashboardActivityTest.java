package com.calftracker.project.activities;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.NavigationViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.view.Gravity;

import com.calftracker.project.calftracker.R;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.contrib.DrawerMatchers.isOpen;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

public class DashboardActivityTest {
    @Rule
    public IntentsTestRule<DashboardActivity> mIntentsTestRuleDashboard = new IntentsTestRule<DashboardActivity>(DashboardActivity.class);

    @Test
    public void testOpenCloseMenu() throws Exception {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open());
        onView(withId(R.id.drawer_layout))
                .check(matches(isOpen(Gravity.LEFT)))
                .perform(DrawerActions.close());
        onView(withId(R.id.drawer_layout)).check(matches(isClosed(Gravity.LEFT)));
    }

    @Test
    public void testOpenCalfListMenu() throws Exception {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open());
        onView(withId(R.id.nav_view))
                .perform(NavigationViewActions.navigateTo(R.id.nav_list));
        intended(hasComponent(CalfListActivity.class.getName()));
    }

    @Test
    public void testOpenAddCalfMenu() throws Exception {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open());
        onView(withId(R.id.nav_view))
                .perform(NavigationViewActions.navigateTo(R.id.nav_add));
        intended(hasComponent(AddCalfActivity.class.getName()));
    }

    @Test
    public void testOpenTasksMenu() throws Exception {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open());
        onView(withId(R.id.nav_view))
                .perform(NavigationViewActions.navigateTo(R.id.nav_tasks));
        intended(hasComponent(TasksActivity.class.getName()));
    }

    @Test
    public void testOpenProtocolsMenu() throws Exception {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open());
        onView(withId(R.id.nav_view))
                .perform(NavigationViewActions.navigateTo(R.id.nav_protocols));
        intended(hasComponent(VaccineActivity.class.getName()));
    }

    @Test
    public void testOpenSettingsMenu() throws Exception {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open());
        onView(withId(R.id.nav_view))
                .perform(NavigationViewActions.navigateTo(R.id.nav_settings));
        intended(hasComponent(SettingsActivity.class.getName()));
    }

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

    @After
    public void tearDown() throws Exception {
        mIntentsTestRuleDashboard = null;
    }
}