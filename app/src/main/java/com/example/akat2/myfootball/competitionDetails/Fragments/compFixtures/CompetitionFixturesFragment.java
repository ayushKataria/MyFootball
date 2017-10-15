package com.example.akat2.myfootball.competitionDetails.Fragments.compFixtures;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.akat2.myfootball.R;
import com.example.akat2.myfootball.competitionDetails.CompetitionDetails;
import com.example.akat2.myfootball.competitionDetails.competitionDetails_interface.fixtures_interface;
import com.example.akat2.myfootball.competitionDetails.competitionDetails_model.fixtures_model;
import com.example.akat2.myfootball.competitionDetails.competitionDetails_parser.fixtures_parser;
import com.example.akat2.myfootball.utils.utils;

import java.util.ArrayList;

/**
 * Created by akat2 on 09-10-2017.
 */

public class CompetitionFixturesFragment extends Fragment implements fixtures_interface {

    Context context;
    RecyclerView rv;
    LinearLayoutManager linearLayoutManager;
    static CompetitionFixturesFragment instance;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        context= getActivity().getApplicationContext();
        View view = inflater.inflate(R.layout.comp_fixtures_fragment,container,false);
        instance = this;
        rv=(RecyclerView) view.findViewById(R.id.recyclerView);
        rv.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(linearLayoutManager);

        fixtures_parser parser = new fixtures_parser(context, Integer.parseInt(CompetitionDetails.compId));
        parser.execute();

        return view;
    }

    public static CompetitionFixturesFragment getInstance(){
        return instance;
    }

    @Override
    public void fixtures_list_recieved(ArrayList<fixtures_model> fixturesModels) {
        for(int i=0;i<fixturesModels.size();i++){
            fixtures_model fixturesModel = fixturesModels.get(i);
            String dateTime[] = fixturesModel.getDate().split("T");
            fixturesModel.setDate(utils.getDateForTimeZone(dateTime[0],dateTime[1]));
        }

        rv.setAdapter(new CompetitionFixturesAdapter(context, fixturesModels));
    }

    @Override
    public void fixtures_list_failed(String message) {
        Snackbar.make(getActivity().findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
    }
}
