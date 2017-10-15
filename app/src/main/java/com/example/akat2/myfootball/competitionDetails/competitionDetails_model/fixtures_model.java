package com.example.akat2.myfootball.competitionDetails.competitionDetails_model;

/**
 * Created by akat2 on 11-10-2017.
 */

public class fixtures_model {

    String date;
    String status;
    String matchday;
    String homeTeamName, homeTeamURL;
    String awayTeamName, awayTeamURL;
    String homeTeamGoals, awayTeamGoals;
    String odds;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMatchday() {
        return matchday;
    }

    public void setMatchday(String matchday) {
        this.matchday = matchday;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    public String getHomeTeamURL() {
        return homeTeamURL;
    }

    public void setHomeTeamURL(String homeTeamURL) {
        this.homeTeamURL = homeTeamURL;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public void setAwayTeamName(String awayTeamName) {
        this.awayTeamName = awayTeamName;
    }

    public String getAwayTeamURL() {
        return awayTeamURL;
    }

    public void setAwayTeamURL(String awayTeamURL) {
        this.awayTeamURL = awayTeamURL;
    }

    public String getHomeTeamGoals() {
        return homeTeamGoals;
    }

    public void setHomeTeamGoals(String homeTeamGoals) {
        this.homeTeamGoals = homeTeamGoals;
    }

    public String getAwayTeamGoals() {
        return awayTeamGoals;
    }

    public void setAwayTeamGoals(String awayTeamGoals) {
        this.awayTeamGoals = awayTeamGoals;
    }

    public String getOdds() {
        return odds;
    }

    public void setOdds(String odds) {
        this.odds = odds;
    }
}
