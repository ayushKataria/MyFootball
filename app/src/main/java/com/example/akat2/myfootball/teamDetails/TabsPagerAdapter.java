package com.example.akat2.myfootball.teamDetails;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.akat2.myfootball.teamDetails.Fragments.fixtures_fragment.fixtures_fragment;
import com.example.akat2.myfootball.teamDetails.Fragments.player_fragment.player_fragment;
import com.example.akat2.myfootball.teamDetails.Fragments.table_fragment.table_fragment;

/**
 * Created by akat2 on 05-08-2017.
 */

public class TabsPagerAdapter extends FragmentStatePagerAdapter {

    Context context;

    public TabsPagerAdapter(FragmentManager fm, Context context){
        super(fm);
        this.context=context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new fixtures_fragment();
            case 1:
                return new table_fragment();
            case 2:
                return new player_fragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
