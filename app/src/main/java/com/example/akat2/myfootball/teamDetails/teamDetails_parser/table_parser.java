package com.example.akat2.myfootball.teamDetails.teamDetails_parser;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.akat2.myfootball.listCompetitionsTeams.listCompetitionsTeams;
import com.example.akat2.myfootball.noConnection.noConnection;
import com.example.akat2.myfootball.teamDetails.Fragments.table_fragment.table_fragment;
import com.example.akat2.myfootball.teamDetails.teamDetails;
import com.example.akat2.myfootball.teamDetails.teamDetails_model.table_model;
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
 * Created by akat2 on 11-08-2017.
 */

public class table_parser extends AsyncTask<String, Integer, ArrayList<table_model>>{

    Context context;
    String errorMessage;
    ArrayList<table_model> tableModels;

    public table_parser(Context context){
        this.context = context;
    }

    @Override
    protected ArrayList<table_model> doInBackground(String... strings) {
        Boolean connected = utils.isNetworkAvailable(context);
        if (!connected) {
            Intent intent = new Intent(context, noConnection.class);
            intent.putExtra("caller", listCompetitionsTeams.class.getName().toString());
            context.startActivity(intent);
        }else{
            try {
                OkHttpClient client = utils.getOkHttpClient();
                String url = API_constants.baseURL + API_constants.API_competion_list + teamDetails.compId + API_constants.API_League_Table;
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
                intent.putExtra("caller", listCompetitionsTeams.class.getName().toString());
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
            table_fragment.getInstance().tableRecieved(tableModels);
        }else{
            table_fragment.getInstance().tableFailed(errorMessage);
        }
    }
}
