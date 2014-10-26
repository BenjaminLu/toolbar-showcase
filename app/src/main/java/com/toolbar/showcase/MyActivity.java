package com.toolbar.showcase;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.toolbar.showcase.fragment.RegisterClass;

import java.util.ArrayList;

public class MyActivity extends ActionBarActivity
{
    ViewPager viewPager;
    ActionBar actionBar;
    private String[] mPlanetTitles = new String[]{
            "Home",
            "My Account",
            "Payment",
            "Setting",
    };

    private int[] drawerItemIconIDs = new int[]{
            R.drawable.phone,
            R.drawable.computer,
            R.drawable.camera,
            R.drawable.email
    };

    private ArrayList<NavDrawerItem> navDrawerItems = new ArrayList<NavDrawerItem>();

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private void prepareDrawerList()
    {
        int listSize = mPlanetTitles.length;
        for (int i = 0; i < listSize; i++) {
            NavDrawerItem item = new NavDrawerItem();
            item.setTitle(mPlanetTitles[i]);
            item.setIcon(drawerItemIconIDs[i]);
            navDrawerItems.add(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        //drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        //drawer toggle listener
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close)
        {
            // Called when a drawer has settled in a completely closed state.
            public void onDrawerClosed(View view)
            {
                super.onDrawerClosed(view);
            }

            // Called when a drawer has settled in a completely open state.
            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // Set the adapter for the drawer list view
        prepareDrawerList();
        mDrawerList.setAdapter(new NavDrawerListAdapter(this, navDrawerItems));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        //View Pager
        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(myFragmentPagerAdapter);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.phone:
                viewPager.setCurrentItem(0);
                break;
            case R.id.computer:
                viewPager.setCurrentItem(1);
                break;
            case R.id.camera:
                viewPager.setCurrentItem(2);
                break;
            case R.id.email:
                viewPager.setCurrentItem(3);
                break;
            case android.R.id.home:
                if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
                    mDrawerLayout.closeDrawer(mDrawerList);
                } else {
                    mDrawerLayout.openDrawer(mDrawerList);
                }
                break;
        }
        return true;
    }

    public class DrawerItemClickListener implements ListView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id)
        {
            exchangeFragment(position);
            viewPager.setCurrentItem(position);
        }

        private void exchangeFragment(int position)
        {
            // Create a new fragment and specify the planet to show based on position
            Fragment fragment = new RegisterClass();
            Bundle args = new Bundle();
            args.putInt("viewIndex", position);
            fragment.setArguments(args);

            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();

            // Highlight the selected item, update the title, and close the drawer
            mDrawerList.setItemChecked(position, true);
            actionBar.setTitle(mPlanetTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

}
