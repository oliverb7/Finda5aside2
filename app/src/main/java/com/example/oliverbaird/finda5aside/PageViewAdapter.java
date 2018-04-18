package com.example.oliverbaird.finda5aside;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by oliverbaird on 02/04/2018.
 */

public class PageViewAdapter extends FragmentPagerAdapter {

    public PageViewAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Basic basicFragment = new Basic();
                return basicFragment;

            case 1:
                Host hostFragment = new Host();
                return hostFragment;

            case 2:
                Player playerFragment = new Player();
                return playerFragment;

            default:
                return null;
        }
    }
    @Override
    public int getCount(){
        return 3;
    }
}
