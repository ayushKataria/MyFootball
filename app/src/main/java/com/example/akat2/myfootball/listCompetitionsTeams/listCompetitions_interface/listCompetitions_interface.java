package com.example.akat2.myfootball.listCompetitionsTeams.listCompetitions_interface;

import com.example.akat2.myfootball.listCompetitionsTeams.listCompetitions_model.listCompetitions_model;

import java.util.ArrayList;

/**
 * Created by akat2 on 02-08-2017.
 */

public interface listCompetitions_interface {

    public void competitionListRecieved(ArrayList<listCompetitions_model> listCompetionsModels);

    public void competitionListFailed(String message);
}
