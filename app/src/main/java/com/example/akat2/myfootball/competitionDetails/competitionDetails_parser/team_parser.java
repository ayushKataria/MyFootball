package com.example.akat2.myfootball.competitionDetails.competitionDetails_parser;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.akat2.myfootball.competitionDetails.Fragments.compTeam.CompetitionTeamFragment;
import com.example.akat2.myfootball.competitionDetails.competitionDetails_model.team_model;
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
 * Created by akat2 on 15-10-2017.
 */

public class team_parser extends AsyncTask<String, Integer, ArrayList<team_model>> {

    Context context;
    ArrayList<team_model> teamModels;
    String errorMessage;
    String compId;

    public team_parser(Context context, String compId){
        this.context = context;
        this.compId = compId;
    }

    @Override
    protected ArrayList<team_model> doInBackground(String... strings) {
        Boolean connected = utils.isNetworkAvailable(context);
        if (!connected) {
            Intent intent = new Intent(context, noConnection.class);
            intent.putExtra("caller", listCompetitionsDetails.class.getName().toString());
            context.startActivity(intent);
        }else{
            try {
                OkHttpClient client = utils.getOkHttpClient();
                String url = API_constants.baseURL + API_constants.API_competion_list + compId + API_constants.API_team_list;
                Response response = utils.getRequest(client, url);
                String responseJSON = response.body().string();
                int responseCode = response.code();

                JSONObject jsonObject = new JSONObject(responseJSON);
                if (responseCode == 200) {
                    teamModels = new ArrayList<team_model>();
                    JSONArray jsonArray = jsonObject.getJSONArray("teams");
                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject teamObject = jsonArray.getJSONObject(i);
                        team_model teamModel = new team_model();
                        teamModel.setName(teamObject.getString("name"));
                        teamModel.setCode(teamObject.getString("code"));
                        teamModel.setShortName(teamObject.getString("shortName"));
                        teamModel.setCrestURL(teamObject.getString("crestUrl"));
                        teamModel.setSelfURL(teamObject.getJSONObject("_links").getJSONObject("self").getString("href"));
                        teamModel.setFixturesURL(teamObject.getJSONObject("_links").getJSONObject("fixtures").getString("href"));
                        teamModel.setPlayerURL(teamObject.getJSONObject("_links").getJSONObject("players").getString("href"));

                        teamModels.add(teamModel);
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
        return teamModels;
    }

    @Override
    protected void onPostExecute(ArrayList<team_model> teamModels) {
        super.onPostExecute(teamModels);
        if(teamModels != null){
            CompetitionTeamFragment.getInstance().teamListRecieved(teamModels);
        }else {
            CompetitionTeamFragment.getInstance().teamListFailed(errorMessage);
        }
    }
}
