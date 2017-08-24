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
    private TextView homeName, awayName, score, status, time, league;

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
        score = (TextView) convertView.findViewById(R.id.score);
        status = (TextView) convertView.findViewById(R.id.status);
        time = (TextView) convertView.findViewById(R.id.time);
        league = (TextView) convertView.findViewById(R.id.league);


        fixturesList_model fixturesModel = fixturesListModels.get(i);

        homeName.setText(fixturesModel.getHomeTeamName());
        awayName.setText(fixturesModel.getAwayTeamName());
        status.setText(fixturesModel.getStatus());

        String compURL = fixturesModel.getCompetitionURL();
        String id = compURL.substring(compURL.lastIndexOf("/")+1);
        String compName = utils.getCompetitionName(Integer.parseInt(id));
        league.setText(compName);

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
