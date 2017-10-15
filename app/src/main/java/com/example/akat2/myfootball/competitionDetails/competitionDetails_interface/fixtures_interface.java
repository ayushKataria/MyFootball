package com.example.akat2.myfootball.competitionDetails.competitionDetails_interface;

import com.example.akat2.myfootball.competitionDetails.competitionDetails_model.fixtures_model;

import java.util.ArrayList;

/**
 * Created by akat2 on 11-10-2017.
 */

public interface fixtures_interface {

    public void fixtures_list_recieved(ArrayList<fixtures_model> fixturesModels);

    public void fixtures_list_failed(String message);
}
