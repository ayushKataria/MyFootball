package com.example.akat2.myfootball.competitionDetails.competitionDetails_parser;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.akat2.myfootball.competitionDetails.Fragments.compTable.CompetitionTableFragment;
import com.example.akat2.myfootball.competitionDetails.competitionDetails_model.table_model;
import com.example.akat2.myfootball.listCompetitionsDetails.listCompetitionsDetails;
import com.example.akat2.myfootball.noConnection.noConnection;
import com.example.akat2.myfootball.utils.API_constants;
import com.example.akat2.myfootball.utils.utils;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

/**
 * Created by akat2 on 11-10-2017.
 */

public class table_parser extends AsyncTask<Integer, String, ArrayList<table_model>> {

    Context context;
    String errorMessage;
    ArrayList<table_model> tableModels;
    String compId;

    public table_parser(Context context, String compId){
        this.context = context;
        this.compId = compId;
    }


    @Override
    protected ArrayList<table_model> doInBackground(Integer... integers) {
        Boolean connected = utils.isNetworkAvailable(context);
        if (!connected) {
            Intent intent = new Intent(context, noConnection.class);
            intent.putExtra("caller", listCompetitionsDetails.class.getName().toString());
            context.startActivity(intent);
        }else{
            try {
                OkHttpClient client = utils.getOkHttpClient();
                String url = API_constants.baseURL + API_constants.API_competion_list + compId + API_constants.API_League_Table;
                Response response = utils.getRequest(client, url);
                String responseJSON = response.body().string();
                int responseCode = response.code();

                JSONObject jsonObject = new JSONObject(responseJSON);
                if(jsonObject.has("error")){
                    errorMessage = jsonObject.getString("error");
                }else if(responseCode == 200) {

                    tableModels = new ArrayList<>();
                    JSONArray jsonArray = jsonObject.getJSONArray("standing");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject tableObject = jsonArray.getJSONObject(i);
                        table_model tableModel = new table_model();
                        tableModel.setPosition(tableObject.getString("position"));
                        tableModel.setTeamName(tableObject.getString("teamName"));
                        tableModel.setCrestURL(tableObject.getString("crestURI"));
                        tableModel.setPlayedGames(tableObject.getString("playedGames"));
                        tableModel.setPoints(tableObject.getString("points"));
                        tableModel.setGoalsFor(tableObject.getString("goals"));
                        tableModel.setGoalsAgainst(tableObject.getString("goalsAgainst"));
                        tableModel.setGoalsDifference(tableObject.getString("goalDifference"));
                        tableModel.setWins(tableObject.getString("wins"));
                        tableModel.setDraws(tableObject.getString("draws"));
                        tableModel.setLosses(tableObject.getString("losses"));

                        tableModels.add(tableModel);
                    }
                } else {
                    errorMessage = jsonObject.getString("Message");
                }
            }
            catch (SocketTimeoutException e) {
                errorMessage = "Failed to connect to the server";
            }
            catch (IOException e) {
                Intent intent = new Intent(context, noConnection.class);
                intent.putExtra("caller", listCompetitionsDetails.class.getName().toString());
                context.startActivity(intent);
            } catch (JSONException e) {
                errorMessage = "Invalid data";
            } catch (Exception e){
                errorMessage = e.getMessage();
            }
        }
        return tableModels;
    }

    @Override
    protected void onPostExecute(ArrayList<table_model> tableModels) {
        super.onPostExecute(tableModels);
        if(tableModels != null){
            CompetitionTableFragment.getInstance().table_recieved(tableModels);
        }else {
            CompetitionTableFragment.getInstance().table_failed(errorMessage);
        }
    }
}
