package com.example.akat2.myfootball.competitionDetails.Fragments.compTeam;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.akat2.myfootball.R;
import com.example.akat2.myfootball.competitionDetails.CompetitionDetails;
import com.example.akat2.myfootball.competitionDetails.Fragments.compFixtures.CompetitionFixturesAdapter;
import com.example.akat2.myfootball.competitionDetails.competitionDetails_interface.team_interface;
import com.example.akat2.myfootball.competitionDetails.competitionDetails_model.team_model;
import com.example.akat2.myfootball.competitionDetails.competitionDetails_parser.team_parser;
import com.example.akat2.myfootball.listTeams.listTeams_model.listTeams_model;
import com.example.akat2.myfootball.teamDetails.teamDetails;

import java.util.ArrayList;

/**
 * Created by akat2 on 09-10-2017.
 */

public class CompetitionTeamFragment extends Fragment implements team_interface {

    Context context;
    ListView listView;
    LinearLayoutManager linearLayoutManager;
    static CompetitionTeamFragment instance;
    ArrayList<team_model> team_models = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        context= getActivity().getApplicationContext();
        View view = inflater.inflate(R.layout.comp_team_fragment,container,false);
        instance = this;
        listView = (ListView) view.findViewById(R.id.listView);

        team_parser parser = new team_parser(context, CompetitionDetails.compId);
        parser.execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context, teamDetails.class);
                intent.putExtra("selfURL", team_models.get(i).getSelfURL());
                intent.putExtra("compId", CompetitionDetails.compId);
                intent.putExtra("teamName",team_models.get(i).getName());
                startActivity(intent);
            }
        });
        return view;
    }

    public static CompetitionTeamFragment getInstance(){
        return instance;
    }

    @Override
    public void teamListRecieved(ArrayList<team_model> teamModels) {
        team_models = teamModels;
        listView.setAdapter(new CompetitionTeamAdapter(context, teamModels));
    }

    @Override
    public void teamListFailed(String message) {
        Snackbar.make(getActivity().findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
    }
}
