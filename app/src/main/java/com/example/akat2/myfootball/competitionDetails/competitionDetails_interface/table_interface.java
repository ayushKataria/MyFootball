package com.example.akat2.myfootball.competitionDetails.competitionDetails_interface;

import com.example.akat2.myfootball.competitionDetails.competitionDetails_model.table_model;

import java.util.ArrayList;

/**
 * Created by akat2 on 11-10-2017.
 */

public interface table_interface {

    public void table_recieved(ArrayList<table_model> tableModels);

    public void table_failed(String message);
}
