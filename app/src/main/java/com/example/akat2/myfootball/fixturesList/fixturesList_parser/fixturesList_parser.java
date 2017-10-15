package com.example.akat2.myfootball.fixturesList.fixturesList_parser;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.akat2.myfootball.fixturesList.fixturesList;
import com.example.akat2.myfootball.fixturesList.fixturesList_model.fixturesList_model;
import com.example.akat2.myfootball.listCompetitionsTeams.listCompetitionsTeams;
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
 * Created by akat2 on 21-08-2017.
 */

public class fixturesList_parser extends AsyncTask<String, Integer, ArrayList<fixturesList_model>> {

    Context context;
    String errorMessage;
    ArrayList<fixturesList_model> fixturesListModels;

    public fixturesList_parser(Context context){
        this.context = context;
    }

    @Override
    protected ArrayList<fixturesList_model> doInBackground(String... strings) {
        Boolean connected = utils.isNetworkAvailable(context);
        if (!connected) {
            Intent intent = new Intent(context, noConnection.class);
            intent.putExtra("caller", fixturesList.class.getName().toString());
            context.startActivity(intent);
        }else{
            try {
                OkHttpClient client = utils.getOkHttpClient();
                String url = API_constants.baseURL + API_constants.API_fixtures;
                Response response = utils.getRequest(client, url);
                String responseJSON = response.body().string();
                int responseCode = response.code();

                JSONObject jsonObject = new JSONObject(responseJSON);
                if (responseCode == 200) {
                    fixturesList.dateStart = jsonObject.getString("timeFrameStart");
                    fixturesList.dateEnd = jsonObject.getString("timeFrameEnd");
                    fixturesListModels = new ArrayList<>();
                    JSONArray jsonArray = jsonObject.getJSONArray("fixtures");
                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject fixturesObject = jsonArray.getJSONObject(i);
                        fixturesList_model fixturesModel = new fixturesList_model();
                        fixturesModel.setDate(fixturesObject.getString("date"));
                        fixturesModel.setStatus(fixturesObject.getString("status"));
                        fixturesModel.setMatchday(fixturesObject.getString("matchday"));
                        fixturesModel.setHomeTeamName(fixturesObject.getString("homeTeamName"));
                        fixturesModel.setAwayTeamName(fixturesObject.getString("awayTeamName"));
                        fixturesModel.setHomeTeamGoals(fixturesObject.getJSONObject("result").getString("goalsHomeTeam"));
                        fixturesModel.setAwayTeamGoals(fixturesObject.getJSONObject("result").getString("goalsAwayTeam"));
                        fixturesModel.setHomeTeamURL(fixturesObject.getJSONObject("_links").getJSONObject("homeTeam").getString("href"));
                        fixturesModel.setAwayTeamURL(fixturesObject.getJSONObject("_links").getJSONObject("homeTeam").getString("href"));
                        fixturesModel.setCompetitionURL(fixturesObject.getJSONObject("_links").getJSONObject("competition").getString("href"));

                        fixturesListModels.add(fixturesModel);
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
        return fixturesListModels;
    }

    @Override
    protected void onPostExecute(ArrayList<fixturesList_model> fixturesList_models) {
        super.onPostExecute(fixturesList_models);
        if(fixturesList_models != null){
            fixturesList.getInstance().fixturesListRecieved(fixturesList_models);
        }else{
            fixturesList.getInstance().fixturesListFailed(errorMessage);
        }
    }
}
