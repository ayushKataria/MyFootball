package com.example.akat2.myfootball.teamDetails.Fragments.table_fragment;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.akat2.myfootball.R;
import com.example.akat2.myfootball.teamDetails.teamDetails_interface.table_interface;
import com.example.akat2.myfootball.teamDetails.teamDetails_model.table_model;
import com.example.akat2.myfootball.teamDetails.teamDetails_parser.table_parser;

import java.util.ArrayList;

/**
 * Created by akat2 on 05-08-2017.
 */

public class table_fragment extends Fragment implements table_interface {

    Context context;
    RecyclerView rv;
    LinearLayoutManager linearLayoutManager;
    static table_fragment l1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context= getActivity().getApplicationContext();
        View view;
        l1 = this;
        view = inflater.inflate(R.layout.table_fragment, container, false);
        rv=(RecyclerView) view.findViewById(R.id.tablelv);
        rv.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(linearLayoutManager);
        table_parser parser = new table_parser(context);
        parser.execute();
        return view;
    }

    @Override
    public void tableRecieved(ArrayList<table_model> tableModels) {
        rv.setAdapter(new tableListAdapter(context, tableModels));
    }

    @Override
    public void tableFailed(String message) {
        if(!message.equals("Ever seen a league table for that competition?")) {
            Snackbar.make(getActivity().findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
        }
    }

    public static table_fragment getInstance(){
        return l1;
    }
}
