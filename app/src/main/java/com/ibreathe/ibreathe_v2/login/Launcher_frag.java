package com.ibreathe.ibreathe_v2.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ibreathe.ibreathe_v2.R;

/**
 * Created by aloknerurkar on 8/9/15.
 */
public class Launcher_frag extends Fragment {

    public View mView;

    public static Launcher_frag newInstance(int bkg_res) {
        Launcher_frag newFrag = new Launcher_frag();
        Bundle args = new Bundle();
        args.putInt("bkg_res", bkg_res);
        newFrag.setArguments(args);
        return newFrag;
    }
    public Launcher_frag(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.launcher_frag, container, false);
        Bundle bundle = getArguments();
        int bkg_res = 0;
        if (bundle != null) {
            bkg_res = bundle.getInt("bkg_res");
            mView.setBackgroundResource(bkg_res);
        }
        return mView;
    }
}
