package com.ibreathe.ibreathe_v2.home;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;

import com.ibreathe.ibreathe_v2.R;
import com.ibreathe.ibreathe_v2.login.Launcher_frag;

import java.util.ArrayList;
import java.util.List;


public class Home extends AppCompatActivity {

    public FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        mTabHost.addTab(
                mTabHost.newTabSpec("Home").setIndicator(getTabIndicator(mTabHost.getContext(),
                        R.drawable.ic_home_black_24dp)),
                        HomeFragment.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("My Events").setIndicator(getTabIndicator(mTabHost.getContext(),
                        R.drawable.ic_event_black_24dp)),
                        MyEventsFragment.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("Profile").setIndicator(getTabIndicator(mTabHost.getContext(),
                        R.drawable.ic_person_black_24dp)),
                        Launcher_frag.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("Notfications").setIndicator(getTabIndicator(mTabHost.getContext(),
                        R.drawable.ic_public_black_24dp)),
                        Launcher_frag.class, null);
        tabSelected(0);

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                int tab = mTabHost.getCurrentTab();
                View v;
                switch (tab){
                    case 0:
                        tabSelected(0);
                        tabUnselected(1);
                        tabUnselected(2);
                        tabUnselected(3);
                        break;
                    case 1:
                        tabSelected(1);
                        tabUnselected(0);
                        tabUnselected(2);
                        tabUnselected(3);
                        break;
                    case 2:
                        tabSelected(2);
                        tabUnselected(0);
                        tabUnselected(1);
                        tabUnselected(3);
                        break;
                    case 3:
                        tabSelected(3);
                        tabUnselected(0);
                        tabUnselected(1);
                        tabUnselected(2);
                        break;
                }
            }
        });
    }

    private void tabSelected(int tab) {
        View v;
        ImageView ic;

        switch (tab) {
            case 0:
                v = mTabHost.getTabWidget().getChildAt(tab);
                if (v != null) {
                    ic = (ImageView)v.findViewById(R.id.imageView);
                    ic.setImageResource(R.drawable.ic_home_white_24dp);
                }
                break;
            case 1:
                v = mTabHost.getTabWidget().getChildAt(tab);
                if (v != null) {
                    ic = (ImageView)v.findViewById(R.id.imageView);
                    ic.setImageResource(R.drawable.ic_event_white_24dp);
                }
                break;
            case 2:
                v = mTabHost.getTabWidget().getChildAt(tab);
                if (v != null) {
                    ic = (ImageView)v.findViewById(R.id.imageView);
                    ic.setImageResource(R.drawable.ic_person_white_24dp);
                }
                break;
            case 3:
                v = mTabHost.getTabWidget().getChildAt(tab);
                if (v != null) {
                    ic = (ImageView)v.findViewById(R.id.imageView);
                    ic.setImageResource(R.drawable.ic_public_white_24dp);
                }
                break;
        }
    }

    private void tabUnselected(int tab) {
        View v;
        ImageView ic;

        switch (tab) {
            case 0:
                v = mTabHost.getTabWidget().getChildAt(tab);
                if (v != null) {
                    ic = (ImageView)v.findViewById(R.id.imageView);
                    ic.setImageResource(R.drawable.ic_home_black_24dp);
                }
                break;
            case 1:
                v = mTabHost.getTabWidget().getChildAt(tab);
                if (v != null) {
                    ic = (ImageView)v.findViewById(R.id.imageView);
                    ic.setImageResource(R.drawable.ic_event_black_24dp);
                }
                break;
            case 2:
                v = mTabHost.getTabWidget().getChildAt(tab);
                if (v != null) {
                    ic = (ImageView)v.findViewById(R.id.imageView);
                    ic.setImageResource(R.drawable.ic_person_black_24dp);
                }
                break;
            case 3:
                v = mTabHost.getTabWidget().getChildAt(tab);
                if (v != null) {
                    ic = (ImageView)v.findViewById(R.id.imageView);
                    ic.setImageResource(R.drawable.ic_public_black_24dp);
                }
                break;
        }
    }

    private View getTabIndicator(Context context, int icon) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_layout, null);
        ImageView iv = (ImageView) view.findViewById(R.id.imageView);
        iv.setImageResource(icon);
        return view;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
