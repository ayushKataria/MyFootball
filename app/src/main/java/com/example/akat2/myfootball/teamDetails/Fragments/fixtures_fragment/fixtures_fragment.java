package com.example.akat2.myfootball.teamDetails.Fragments.fixtures_fragment;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.ListView;

import com.example.akat2.myfootball.R;
import com.example.akat2.myfootball.teamDetails.teamDetails;
import com.example.akat2.myfootball.teamDetails.teamDetails_interface.fixtures_interface;
import com.example.akat2.myfootball.teamDetails.teamDetails_model.fixtures_model;
import com.example.akat2.myfootball.teamDetails.teamDetails_parser.fixtures_parser;

import java.util.ArrayList;

/**
 * Created by akat2 on 05-08-2017.
 */

public class fixtures_fragment extends Fragment implements fixtures_interface {

    RecyclerView rv;
    LinearLayoutManager linearLayoutManager;
    Context context;
    static fixtures_fragment l1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context= getActivity().getApplicationContext();
        l1 = this;
        View view;
        view = inflater.inflate(R.layout.fixtures_fragment, container, false);
        rv=(RecyclerView) view.findViewById(R.id.fixtureslv);
        rv.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(linearLayoutManager);
        fixtures_parser parser = new fixtures_parser(context);
        parser.execute();
        return view;
    }

    @Override
    public void fixturesRecieved(ArrayList<fixtures_model> fixturesModels) {

        rv.setAdapter(new fixturesListAdapter(context, fixturesModels));
    }

    @Override
    public void fixturesFailed(String message) {

        Snackbar.make(getActivity().findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();

    }

    public static fixtures_fragment getInstance(){
        return l1;
    }
}
