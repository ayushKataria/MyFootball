package com.example.akat2.myfootball.teamDetails.Fragments.fixtures_fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.akat2.myfootball.R;
import com.example.akat2.myfootball.teamDetails.teamDetails_model.fixtures_model;

/**
 * Created by akat2 on 19-08-2017.
 */

public class ViewHolder extends RecyclerView.ViewHolder {

    private TextView homeName, awayName, score, status, time;
    private Context context;

    public ViewHolder(Context context, View view) {
        super(view);
        this.context = context;
        homeName = (TextView) view.findViewById(R.id.homeName);
        awayName = (TextView) view.findViewById(R.id.awayName);
        score = (TextView) view.findViewById(R.id.score);
        status = (TextView) view.findViewById(R.id.status);
        time = (TextView) view.findViewById(R.id.time);
    }

    public void bindFixtures(fixtures_model fixturesModel){
        homeName.setText(fixturesModel.getHomeTeamName());
        awayName.setText(fixturesModel.getAwayTeamName());
        status.setText(fixturesModel.getStatus());

        String s;
        if(fixturesModel.getStatus().equals("SCHEDULED")||fixturesModel.getStatus().equals("TIMED")) {
            String dateTime[] = fixturesModel.getDate().split("T");
            s = dateTime[0] + "\n" + dateTime[1].substring(0, dateTime[1].length() - 1);
            time.setText(s);
            time.setVisibility(View.VISIBLE);
            score.setVisibility(View.GONE);
        }else if(fixturesModel.getStatus().equals("FINISHED")||fixturesModel.getStatus().equals("IN_PLAY")){
            s = fixturesModel.getHomeTeamGoals() + " : " + fixturesModel.getAwayTeamGoals();
            score.setText(s);
            time.setVisibility(View.GONE);
            score.setVisibility(View.VISIBLE);
        }
    }

}
