package com.ibreathe.ibreathe_v2.login;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by aloknerurkar on 8/9/15.
 */
public class LauncherPagerAdapter extends FragmentPagerAdapter {

    public LauncherPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    public Fragment mFragment;
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                mFragment = Launcher_frag.newInstance();
                //mFragment.getView().setBackgroundResource();
                return mFragment;
            case 1:
                mFragment = Launcher_frag.newInstance();
                //mFragment.getView().setBackgroundResource();
                return mFragment;
            case 2:
                mFragment = Launcher_frag.newInstance();
                //mFragment.getView().setBackgroundResource();
                return mFragment;

        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
