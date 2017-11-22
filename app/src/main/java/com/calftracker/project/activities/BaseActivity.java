package com.calftracker.project.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.calftracker.project.calftracker.R;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    protected FrameLayout frameLayout;
    protected NavigationView mNavigationView;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_menu);;

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        mNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);

        frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);

                String title = (String) getTitle();
                if (mNavigationView.getMenu().findItem(R.id.nav_list).isChecked()) {
                    title = getString(R.string.calf_list_title);
                }
                else if (mNavigationView.getMenu().findItem(R.id.nav_add).isChecked()) {
                    title = getString(R.string.add_calf_title);
                }
                else if (mNavigationView.getMenu().findItem(R.id.nav_protocols).isChecked()) {
                    title = getString(R.string.protocols_title);
                }
                getSupportActionBar().setTitle(title);

                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                String title = (String) getTitle();
                if (mNavigationView.getMenu().findItem(R.id.nav_list).isChecked()) {
                    title = getString(R.string.calf_list_title);
                }
                else if (mNavigationView.getMenu().findItem(R.id.nav_add).isChecked()) {
                    title = getString(R.string.add_calf_title);
                }
                else if (mNavigationView.getMenu().findItem(R.id.nav_protocols).isChecked()) {
                    title = getString(R.string.protocols_title);
                }
                getSupportActionBar().setTitle(title);

                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerLayout.addDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent intent;
        switch(item.getItemId()) {
            case R.id.nav_home: intent = new Intent(BaseActivity.this,DashboardActivity.class);
                if (!getIntent().filterEquals(intent)) {
                    item.setChecked(true);
                    startActivity(intent);
                }
                break;
            case R.id.nav_list: intent = new Intent(BaseActivity.this,CalfListActivity.class);
                if (!getIntent().filterEquals(intent)) {
                    item.setChecked(true);
                    startActivity(intent);
                }
                break;
            case R.id.nav_add: intent = new Intent(BaseActivity.this,AddCalfActivity.class);
                if (!getIntent().filterEquals(intent)) {
                    item.setChecked(true);
                    startActivity(intent);
                }
                break;
            case R.id.nav_protocols: intent = new Intent(BaseActivity.this,VaccineActivity.class);
                if (!getIntent().filterEquals(intent)) {
                    Intent medicalIntent = new Intent(BaseActivity.this, MedicineActivity.class);
                    if (!getIntent().filterEquals(intent) && !getIntent().filterEquals(medicalIntent)) {
                        item.setChecked(true);
                        startActivity(intent);
                    }
                }
                break;
            // TODO add cases for other menu options as they are implemented
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}