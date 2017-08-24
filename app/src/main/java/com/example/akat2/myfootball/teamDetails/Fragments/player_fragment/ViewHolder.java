package com.example.akat2.myfootball.teamDetails.Fragments.player_fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.akat2.myfootball.R;
import com.example.akat2.myfootball.teamDetails.teamDetails_model.players_model;

/**
 * Created by akat2 on 19-08-2017.
 */

public class ViewHolder extends RecyclerView.ViewHolder {

    private TextView position, name, number;
    private Context context;

    public ViewHolder(Context context,View view) {
        super(view);

        this.context = context;
        position = (TextView) view.findViewById(R.id.position);
        name = (TextView) view.findViewById(R.id.name);
        number = (TextView) view.findViewById(R.id.number);
    }

    public void bindPlayers(players_model playersModel){
        name.setText(playersModel.getName());
        if(playersModel.getJerseyNumber()!="null")
            number.setText(playersModel.getJerseyNumber());
        else
            number.setText("");
        String pos = playersModel.getPosition();
        if(pos.equals("Keeper")){
            position.setText("GK");
        }else if(pos.equals("Centre-Back")){
            position.setText("CB");
        }else if(pos.equals("Left-Back")){
            position.setText("LB");
        }else if(pos.equals("Right-Back")){
            position.setText("RB");
        }else if(pos.equals("Defensive Midfield")){
            position.setText("CDM");
        }else if(pos.equals("Central Midfield")){
            position.setText("CM");
        }else if(pos.equals("Left Wing")){
            position.setText("LW");
        }else if(pos.equals("Right Wing")){
            position.setText("RW");
        }else if(pos.equals("Centre-Forward")){
            position.setText("ST");
        }

    }
}
