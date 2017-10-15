package com.example.akat2.myfootball.teamDetails.Fragments.fixtures_fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.akat2.myfootball.R;
import com.example.akat2.myfootball.teamDetails.teamDetails_model.fixtures_model;
import com.example.akat2.myfootball.utils.utils;

/**
 * Created by akat2 on 19-08-2017.
 */

public class ViewHolder extends RecyclerView.ViewHolder {

    private TextView homeName, awayName, homeScore, awayScore, status, time, compName;
    private Context context;

    public ViewHolder(Context context, View view) {
        super(view);
        this.context = context;
        homeName = (TextView) view.findViewById(R.id.homeName);
        awayName = (TextView) view.findViewById(R.id.awayName);
        homeScore = (TextView) view.findViewById(R.id.homeScore);
        awayScore = (TextView) view.findViewById(R.id.awayScore);
        status = (TextView) view.findViewById(R.id.status);
        time = (TextView) view.findViewById(R.id.time);
        compName = (TextView) view.findViewById(R.id.compName);
    }

    public void bindFixtures(fixtures_model fixturesModel){
        homeName.setText(fixturesModel.getHomeTeamName());
        awayName.setText(fixturesModel.getAwayTeamName());
        if(fixturesModel.getStatus().equals("null")||fixturesModel.getStatus().equals("FINISHED")
                ||fixturesModel.getStatus().equals("SCHEDULED")||fixturesModel.getStatus().equals("TIMED")){
            status.setVisibility(View.INVISIBLE);
        }else if(fixturesModel.getStatus().equals("IN_PLAY")) {
            status.setText("LIVE");
        }

        String compURL = fixturesModel.getCompetitionURL();
        String id = compURL.substring(compURL.lastIndexOf("/")+1);
        String competitionName = utils.getCompetitionName(Integer.parseInt(id));
        compName.setText(competitionName);

        String s;
        if(fixturesModel.getStatus().equals("SCHEDULED")||fixturesModel.getStatus().equals("TIMED")) {
            String dateTime[] = fixturesModel.getDate().split("T");
            s = dateTime[0] + " " + dateTime[1].substring(0, dateTime[1].length() - 1);
            time.setText(s);
            homeScore.setVisibility(View.GONE);
            awayScore.setVisibility(View.GONE);
            time.setVisibility(View.VISIBLE);
            status.setVisibility(View.GONE);
        }else if(fixturesModel.getStatus().equals("IN_PLAY")){
            homeScore.setText(fixturesModel.getHomeTeamGoals());
            awayScore.setText(fixturesModel.getAwayTeamGoals());
            homeScore.setVisibility(View.VISIBLE);
            awayScore.setVisibility(View.VISIBLE);
            time.setVisibility(View.GONE);
            status.setVisibility(View.VISIBLE);
        }else if(fixturesModel.getStatus().equals("FINISHED")){
            homeScore.setText(fixturesModel.getHomeTeamGoals());
            awayScore.setText(fixturesModel.getAwayTeamGoals());
            homeScore.setVisibility(View.VISIBLE);
            awayScore.setVisibility(View.VISIBLE);
            time.setVisibility(View.GONE);
            status.setVisibility(View.GONE);
        }
    }

}
