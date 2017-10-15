package com.example.akat2.myfootball.competitionDetails.competitionDetails_interface;

import com.example.akat2.myfootball.competitionDetails.competitionDetails_model.team_model;

import java.util.ArrayList;

/**
 * Created by akat2 on 15-10-2017.
 */

public interface team_interface {

    public void teamListRecieved(ArrayList<team_model> teamModels);

    public void teamListFailed(String message);

}
