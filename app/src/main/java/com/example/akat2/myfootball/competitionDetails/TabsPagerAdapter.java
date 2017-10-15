package com.example.akat2.myfootball.competitionDetails;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.example.akat2.myfootball.competitionDetails.Fragments.compFixtures.CompetitionFixturesFragment;
import com.example.akat2.myfootball.competitionDetails.Fragments.compTable.CompetitionTableFragment;
import com.example.akat2.myfootball.competitionDetails.Fragments.compTeam.CompetitionTeamFragment;

/**
 * Created by akat2 on 09-10-2017.
 */

public class TabsPagerAdapter extends FragmentStatePagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new CompetitionFixturesFragment();
            case 1:
                return new CompetitionTableFragment();
            case 2:
                return new CompetitionTeamFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
