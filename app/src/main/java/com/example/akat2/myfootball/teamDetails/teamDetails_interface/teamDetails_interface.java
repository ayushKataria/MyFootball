package com.example.akat2.myfootball.teamDetails.teamDetails_interface;

import com.example.akat2.myfootball.teamDetails.teamDetails_model.teamDetails_model;

/**
 * Created by akat2 on 05-08-2017.
 */

public interface teamDetails_interface {

    public void teamDetailsSuccessful(teamDetails_model teamDetailsModel);

    public void teamDetailsFailed(String message);
}
