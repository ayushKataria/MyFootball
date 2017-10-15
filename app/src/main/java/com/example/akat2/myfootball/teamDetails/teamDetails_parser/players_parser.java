package com.example.akat2.myfootball.teamDetails.teamDetails_parser;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.akat2.myfootball.listCompetitionsTeams.listCompetitionsTeams;
import com.example.akat2.myfootball.noConnection.noConnection;
import com.example.akat2.myfootball.teamDetails.Fragments.player_fragment.player_fragment;
import com.example.akat2.myfootball.teamDetails.teamDetails;
import com.example.akat2.myfootball.teamDetails.teamDetails_model.players_model;
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
 * Created by akat2 on 19-08-2017.
 */

public class players_parser extends AsyncTask<String, Integer, ArrayList<players_model>> {

    Context context;
    String errorMessage;
    ArrayList<players_model> playersModels;

    public players_parser(Context context) {
        this.context = context;
    }

    @Override
    protected ArrayList<players_model> doInBackground(String... strings) {
        Boolean connected = utils.isNetworkAvailable(context);
        if (!connected) {
            Intent intent = new Intent(context, noConnection.class);
            intent.putExtra("caller", listCompetitionsTeams.class.getName().toString());
            context.startActivity(intent);
        }else{
            try {
                OkHttpClient client = utils.getOkHttpClient();
                String url = teamDetails.playerURL;
                Response response = utils.getRequest(client, url);
                String responseJSON = response.body().string();
                int responseCode = response.code();

                JSONObject jsonObject = new JSONObject(responseJSON);
                if (responseCode == 200) {
                    playersModels = new ArrayList<>();
                    JSONArray jsonArray = jsonObject.getJSONArray("players");
                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject playersObject = jsonArray.getJSONObject(i);
                        players_model playersModel = new players_model();
                        playersModel.setPosition(playersObject.getString("position"));
                        playersModel.setName(playersObject.getString("name"));
                        playersModel.setJerseyNumber(playersObject.getString("jerseyNumber"));
                        playersModel.setNationality(playersObject.getString("nationality"));
                        playersModel.setDOB(playersObject.getString("dateOfBirth"));
                        playersModel.setContractUntil(playersObject.getString("contractUntil"));

                        playersModels.add(playersModel);
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
        return playersModels;
    }

    @Override
    protected void onPostExecute(ArrayList<players_model> playersModels) {
        super.onPostExecute(playersModels);
        if(playersModels!=null){
            player_fragment.getInstance().playersListRecieved(playersModels);
        }else{
            player_fragment.getInstance().playersListFailed(errorMessage);
        }
    }
}
