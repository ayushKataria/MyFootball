package com.example.akat2.myfootball.teamDetails.Fragments.player_fragment;

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
import com.example.akat2.myfootball.teamDetails.teamDetails_interface.players_interface;
import com.example.akat2.myfootball.teamDetails.teamDetails_model.players_model;
import com.example.akat2.myfootball.teamDetails.teamDetails_parser.players_parser;

import java.util.ArrayList;

/**
 * Created by akat2 on 05-08-2017.
 */

public class player_fragment extends Fragment implements players_interface {

    RecyclerView rv1;
    LinearLayoutManager linearLayoutManager1;
    public static player_fragment l1;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context= getActivity().getApplicationContext();
        View view;
        view = inflater.inflate(R.layout.player_fragment, container, false);
        l1 =this;
        rv1=(RecyclerView) view.findViewById(R.id.playerlv1);
        rv1.setHasFixedSize(true);
        linearLayoutManager1 = new LinearLayoutManager(getActivity());
        rv1.setLayoutManager(linearLayoutManager1);
        players_parser parser = new players_parser(context);
        parser.execute();
        return view;
    }

    public static player_fragment getInstance(){
        return l1;
    }

    @Override
    public void playersListRecieved(ArrayList<players_model> playersModels) {
        ArrayList<players_model> defenderList = new ArrayList<>();
        ArrayList<players_model> midfielderList = new ArrayList<>();
        ArrayList<players_model> forwardList = new ArrayList<>();
        ArrayList<players_model> keeperList = new ArrayList<>();
        ArrayList<players_model> wingerList = new ArrayList<>();
        ArrayList<players_model> playersList = new ArrayList<>();

        for(int i=0; i<playersModels.size(); i++){
            players_model playersModel = playersModels.get(i);
            if(playersModel.getPosition().equals("Keeper")){
                keeperList.add(playersModel);

            }else if(playersModel.getPosition().equals("Centre-Back")||playersModel.getPosition().equals("Left-Back")||
                    playersModel.getPosition().equals("Right-Back")){
                defenderList.add(playersModel);

            }else if(playersModel.getPosition().equals("Defensive Midfield")||playersModel.getPosition().equals("Central Midfield")){
                midfielderList.add(playersModel);

            }else if(playersModel.getPosition().equals("Centre-Forward")){
                forwardList.add(playersModel);

            }else if(playersModel.getPosition().equals("Left Wing")||playersModel.getPosition().equals("Right Wing")){
                wingerList.add(playersModel);
            }
        }

        playersList.addAll(keeperList);
        playersList.addAll(defenderList);
        playersList.addAll(midfielderList);
        playersList.addAll(wingerList);
        playersList.addAll(forwardList);

        rv1.setAdapter(new playerListAdapter(context, playersList));
    }

    @Override
    public void playersListFailed(String message) {
        Snackbar.make(getActivity().findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();

    }
}
