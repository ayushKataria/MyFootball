package com.example.akat2.myfootball.teamDetails.teamDetails_interface;

import com.example.akat2.myfootball.teamDetails.teamDetails_model.players_model;

import java.util.ArrayList;

/**
 * Created by akat2 on 19-08-2017.
 */

public interface players_interface {

    public void playersListRecieved(ArrayList<players_model> playersModels);

    public void playersListFailed(String message);
}
