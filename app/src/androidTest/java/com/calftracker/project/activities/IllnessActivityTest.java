package com.calftracker.project.activities;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.widget.ListView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.calftracker.project.calftracker.R;

/**
 * Created by Lisa on 11/27/2017.
 */

public class IllnessActivityTest {

    @Rule
    public ActivityTestRule<IllnessActivity> mActivityTestRule = new ActivityTestRule(AddVaccineActivity.class);
    private IllnessActivity mActivity = null;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
        Intents.init();
    }

  /*  @Test
    public void testShouldShowTheItemDetailWhenAnItemIsClicked() throws Exception {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        final ListView listview = (ListView) EditVaccineActivity.findViewById(R.id.listview_illness);

        instrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                int position = 0;
                listview.performItemClick(listview.getChildAt(position), position, listview.getAdapter().getItemId(position));
            }
        });

        Instrumentation.ActivityMonitor monitor = instrumentation.addMonitor(ItemDetailActivity.class.getName(), null, false);
        Activity itemDetailActivity = instrumentation.waitForMonitorWithTimeout(monitor, 5000);

        TextView detailView = (TextView) itemDetailActivity.findViewById(R.id.item_detail);
        assertThat(detailView.getText().toString(), is("Item 1"));
    } */
}
