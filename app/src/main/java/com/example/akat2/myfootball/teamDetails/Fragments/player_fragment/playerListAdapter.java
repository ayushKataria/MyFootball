package com.example.akat2.myfootball.teamDetails.Fragments.player_fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.akat2.myfootball.R;
import com.example.akat2.myfootball.teamDetails.teamDetails_model.players_model;

import java.util.ArrayList;

/**
 * Created by akat2 on 19-08-2017.
 */

public class playerListAdapter extends RecyclerView.Adapter<ViewHolder> {

    Context context;
    ArrayList<players_model> playersModels;

    public playerListAdapter(Context context, ArrayList<players_model> playersModels){
        this.context = context;
        this.playersModels = playersModels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.player_item, parent, false);
        return new ViewHolder(context, view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        players_model playersModel = playersModels.get(position);
        holder.bindPlayers(playersModel);
    }

    @Override
    public int getItemCount() {
        return playersModels.size();
    }
}
