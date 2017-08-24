package com.example.akat2.myfootball.teamDetails.teamDetails_interface;

import com.example.akat2.myfootball.teamDetails.teamDetails_model.table_model;

import java.util.ArrayList;

/**
 * Created by akat2 on 11-08-2017.
 */

public interface table_interface {

    public void tableRecieved(ArrayList<table_model> tableModels);

    public void tableFailed(String message);
}
