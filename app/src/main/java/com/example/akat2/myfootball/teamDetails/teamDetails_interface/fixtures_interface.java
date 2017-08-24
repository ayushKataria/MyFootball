package com.example.akat2.myfootball.teamDetails.teamDetails_interface;

import com.example.akat2.myfootball.teamDetails.teamDetails_model.fixtures_model;

import java.util.ArrayList;

/**
 * Created by akat2 on 10-08-2017.
 */

public interface fixtures_interface {

    public void fixturesRecieved(ArrayList<fixtures_model> fixturesModels);

    public void fixturesFailed(String message);
}
