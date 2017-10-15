package com.example.akat2.myfootball.listTeams.listTeams_parser;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.akat2.myfootball.listCompetitionsTeams.listCompetitionsTeams;
import com.example.akat2.myfootball.listTeams.listTeams;
import com.example.akat2.myfootball.listTeams.listTeams_model.listTeams_model;
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
 * Created by akat2 on 04-08-2017.
 */

public class listTeams_parser extends AsyncTask<String, Integer, ArrayList<listTeams_model>>{

    Context context;
    ArrayList<listTeams_model> listTeamsModels;
    String errorMessage;
    String compId;

    public listTeams_parser(Context context, String compId){
        this.context = context;
        this.compId = compId;
    }


    @Override
    protected ArrayList<listTeams_model> doInBackground(String... strings) {
        Boolean connected = utils.isNetworkAvailable(context);
        if (!connected) {
            Intent intent = new Intent(context, noConnection.class);
            intent.putExtra("caller", listCompetitionsTeams.class.getName().toString());
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
                    listTeamsModels = new ArrayList<listTeams_model>();
                    JSONArray jsonArray = jsonObject.getJSONArray("teams");
                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject teamObject = jsonArray.getJSONObject(i);
                        listTeams_model listTeamsModel = new listTeams_model();
                        listTeamsModel.setName(teamObject.getString("name"));
                        listTeamsModel.setCode(teamObject.getString("code"));
                        listTeamsModel.setShortName(teamObject.getString("shortName"));
                        listTeamsModel.setCrestURL(teamObject.getString("crestUrl"));
                        listTeamsModel.setSelfURL(teamObject.getJSONObject("_links").getJSONObject("self").getString("href"));
                        listTeamsModel.setFixturesURL(teamObject.getJSONObject("_links").getJSONObject("fixtures").getString("href"));
                        listTeamsModel.setPlayerURL(teamObject.getJSONObject("_links").getJSONObject("players").getString("href"));

                        listTeamsModels.add(listTeamsModel);
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
        return listTeamsModels;
    }

    @Override
    protected void onPostExecute(ArrayList<listTeams_model> listTeamsModels) {
        super.onPostExecute(listTeamsModels);
        if(listTeamsModels != null){
            listTeams.getInstance().teamListRecieved(listTeamsModels);
        }else {
            listTeams.getInstance().teamListFailed(errorMessage);
        }
    }
}
