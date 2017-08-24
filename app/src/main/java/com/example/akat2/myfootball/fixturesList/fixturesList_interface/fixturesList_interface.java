package com.example.akat2.myfootball.fixturesList.fixturesList_interface;

import com.example.akat2.myfootball.fixturesList.fixturesList_model.fixturesList_model;

import java.util.ArrayList;

/**
 * Created by akat2 on 21-08-2017.
 */

public interface fixturesList_interface {

    public void fixturesListRecieved(ArrayList<fixturesList_model> fixturesListModels);

    public void fixturesListFailed(String messsage);
}
