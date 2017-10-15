package com.example.akat2.myfootball.fixturesList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.akat2.myfootball.R;
import com.example.akat2.myfootball.fixturesList.fixturesList_model.fixturesList_model;
import com.example.akat2.myfootball.utils.utils;

import java.util.ArrayList;

/**
 * Created by akat2 on 21-08-2017.
 */

public class fixturesListAdapter extends BaseAdapter {

    Context context;
    int resourceId;
    ArrayList<fixturesList_model> fixturesListModels;
    private TextView homeName, awayName, homeScore, awayScore, status, time, league;

    public fixturesListAdapter(Context context, int resourceId, ArrayList<fixturesList_model> fixturesListModels){
        this.context = context;
        this.resourceId = resourceId;
        this.fixturesListModels = fixturesListModels;
    }

    @Override
    public int getCount() {
        return fixturesListModels.size();
    }

    @Override
    public Object getItem(int i) {
        return fixturesListModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View convertView = view;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resourceId, null);
        }
        homeName = (TextView) convertView.findViewById(R.id.homeName);
        awayName = (TextView) convertView.findViewById(R.id.awayName);
        homeScore = (TextView) convertView.findViewById(R.id.homeScore);
        awayScore = (TextView) convertView.findViewById(R.id.awayScore);
        status = (TextView) convertView.findViewById(R.id.status);
        time = (TextView) convertView.findViewById(R.id.time);
        league = (TextView) convertView.findViewById(R.id.compName);


        fixturesList_model fixturesModel = fixturesListModels.get(i);

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
        String compName = utils.getCompetitionName(Integer.parseInt(id));
        league.setText(compName);

        String s;
        if(fixturesModel.getStatus().equals("SCHEDULED")||fixturesModel.getStatus().equals("TIMED")
                ||fixturesModel.getStatus().equals("null")) {
            String dateTime[] = fixturesModel.getDate().split("T");
            s = dateTime[1].substring(0, dateTime[1].length() - 1);
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
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }
}
