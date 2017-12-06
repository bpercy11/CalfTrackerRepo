package com.calftracker.project.activities;

import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import com.calftracker.project.calftracker.R;
import com.calftracker.project.models.Medicine;

import java.util.ArrayList;

/**
 * Created by Lisa on 11/27/2017.
 */

public class AddIllnessMedicineSelectionActivityTest {

    @Rule
    public ActivityTestRule<AddIllnessMedicineSelectionActivity> mActivityTestRule = new ActivityTestRule(AddIllnessMedicineSelectionActivity.class);
    private AddIllnessMedicineSelectionActivity mActivity = null;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
        Intents.init();
    }

    @Test
    public void testConfirmMedicinesButton() throws Exception{
        onView(withId(R.id.buttonConfirmMedicines)).perform(click());
        intended(hasComponent(IllnessActivity.class.getName()));
    }

    @Test
    public void testClickBackMedicinesButton() throws Exception{
        onView(withId(R.id.buttonCancelSelectMedicines)).perform(click());
        intended(hasComponent(AddIllnessActivity.class.getName()));
    }

    @Test
    public void testSelectAllMedicinesButton() throws Exception{
        ArrayList<Medicine> tempMedicineList = new ArrayList<Medicine>();
        tempMedicineList.add(new Medicine("medicine1",12,"liters",5,"none"));

        onView(withId(R.id.buttonSelectAllMedicines)).perform(click());

    }



}
