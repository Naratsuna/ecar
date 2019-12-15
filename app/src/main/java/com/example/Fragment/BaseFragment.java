package com.example.Fragment;

import android.util.Log;
import android.view.View;

import com.example.ecar.ExecuteActivity;

import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {

    private static final String TAG="BaseFragment";
    public static final int SIGN_NAME = 999;
    private FragmentConfig config;

    public void setConfig(FragmentConfig config) {
        this.config = config;
        Log.d(TAG, "setConfig: "+config);
    }

    public FragmentConfig getConfig() {
        return config;
    }

    public void nextFragment(){
        ExecuteActivity activity = (ExecuteActivity) getActivity();
        if(activity != null){
            activity.nextFragment();
        }
    }

    public static class FragmentConfig{//每个碎片的配置，包括是否有返回，标题，是否可跳过，下一个碎片是什么
        boolean hasBack;
        String title;
        boolean hasSkip;
        Fragment nextFragment;

        public FragmentConfig(boolean hasBack, String title, boolean hasSkip, Fragment nextFragment) {
            this.hasBack = hasBack;
            this.title = title;
            this.hasSkip = hasSkip;
            this.nextFragment = nextFragment;
        }

        public int isHasBack() {
            if(hasBack)
                return View.VISIBLE;
            return View.INVISIBLE;
        }

        public void setHasBack(boolean hasBack) {
            this.hasBack = hasBack;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int isHasSkip() {
            if(hasSkip)
                return View.VISIBLE;
            return View.INVISIBLE;
        }

        public void setHasSkip(boolean hasSkip) {
            this.hasSkip = hasSkip;
        }

        public Fragment getNextFragment() {
            return nextFragment;
        }

        public void setNextFragment(Fragment nextFragment) {
            this.nextFragment = nextFragment;
        }
    }
}
