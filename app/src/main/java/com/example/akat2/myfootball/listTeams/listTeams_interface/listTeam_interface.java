package com.example.akat2.myfootball.listTeams.listTeams_interface;

import com.example.akat2.myfootball.listTeams.listTeams_model.listTeams_model;

import java.util.ArrayList;

/**
 * Created by akat2 on 04-08-2017.
 */

public interface listTeam_interface {

    public void teamListRecieved(ArrayList<listTeams_model> listTeamsModels);

    public void teamListFailed(String message);
}
