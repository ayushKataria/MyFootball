package com.example.akat2.myfootball.teamDetails.teamDetails_parser;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.akat2.myfootball.listCompetitionsTeams.listCompetitionsTeams;
import com.example.akat2.myfootball.noConnection.noConnection;
import com.example.akat2.myfootball.teamDetails.Fragments.fixtures_fragment.fixtures_fragment;
import com.example.akat2.myfootball.teamDetails.teamDetails;
import com.example.akat2.myfootball.teamDetails.teamDetails_model.fixtures_model;
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
 * Created by akat2 on 10-08-2017.
 */

public class fixtures_parser extends AsyncTask<String, Integer, ArrayList<fixtures_model>> {

    Context context;
    String errorMessage;
    ArrayList<fixtures_model> fixturesModels;

    public fixtures_parser(Context context){
        this.context = context;
    }

    @Override
    protected ArrayList<fixtures_model> doInBackground(String... strings) {
        Boolean connected = utils.isNetworkAvailable(context);
        if (!connected) {
            Intent intent = new Intent(context, noConnection.class);
            intent.putExtra("caller", listCompetitionsTeams.class.getName().toString());
            context.startActivity(intent);
        }else{
            try {
                OkHttpClient client = utils.getOkHttpClient();
                String url = teamDetails.fixturesURL;
                Response response = utils.getRequest(client, url);
                String responseJSON = response.body().string();
                int responseCode = response.code();

                JSONObject jsonObject = new JSONObject(responseJSON);
                if (responseCode == 200) {
                    fixturesModels = new ArrayList<>();
                    JSONArray jsonArray = jsonObject.getJSONArray("fixtures");
                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject fixturesObject = jsonArray.getJSONObject(i);
                        fixtures_model fixturesModel = new fixtures_model();
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
                        fixturesModels.add(fixturesModel);
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
        return fixturesModels;
    }

    @Override
    protected void onPostExecute(ArrayList<fixtures_model> fixturesModels) {
        super.onPostExecute(fixturesModels);
        if(fixturesModels != null){
            fixtures_fragment.getInstance().fixturesRecieved(fixturesModels);
        }else{
            fixtures_fragment.getInstance().fixturesFailed(errorMessage);
        }
    }
}
