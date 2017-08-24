package com.example.akat2.myfootball.teamDetails.teamDetails_parser;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.akat2.myfootball.noConnection.noConnection;
import com.example.akat2.myfootball.teamDetails.teamDetails;
import com.example.akat2.myfootball.teamDetails.teamDetails_model.teamDetails_model;
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
 * Created by akat2 on 05-08-2017.
 */

public class teamDetails_parser extends AsyncTask<String, Integer, teamDetails_model>{

    Context context;
    teamDetails_model details;
    String selfURL, errorMessage;

    public teamDetails_parser(Context context, String selfURL){
        this.context = context;
        this.selfURL = selfURL;
    }

    @Override
    protected teamDetails_model doInBackground(String... strings) {
        Boolean connected = utils.isNetworkAvailable(context);
        if (!connected) {
            Intent intent = new Intent(context, noConnection.class);
            intent.putExtra("caller", teamDetails.class.getName().toString());
            context.startActivity(intent);
        }else{
            try {
                OkHttpClient client = utils.getOkHttpClient();
                String url = selfURL;
                Response response = utils.getRequest(client, url);
                String responseJSON = response.body().string();
                int responseCode = response.code();

                JSONObject teamObject = new JSONObject(responseJSON);
                if (responseCode == 200) {
                        details = new teamDetails_model();
                        details.setName(teamObject.getString("name"));
                        details.setCode(teamObject.getString("code"));
                        details.setShortName(teamObject.getString("shortName"));
                        details.setCrestURL(teamObject.getString("crestUrl"));
                        details.setSelfURL(teamObject.getJSONObject("_links").getJSONObject("self").getString("href"));
                        details.setFixturesURL(teamObject.getJSONObject("_links").getJSONObject("fixtures").getString("href"));
                        details.setPlayerURL(teamObject.getJSONObject("_links").getJSONObject("players").getString("href"));
                } else {
                    errorMessage = teamObject.getString("Message");
                }
            }
            catch (SocketTimeoutException e) {
                errorMessage = "Failed to connect to the server";
            }
            catch (IOException e) {
                Intent intent = new Intent(context, noConnection.class);
                intent.putExtra("caller", teamDetails.class.getName().toString());
                context.startActivity(intent);
            } catch (JSONException e) {
                errorMessage = "Invalid data";
            } catch (Exception e){
                errorMessage = e.getMessage();
            }
        }
        return details;
    }

    @Override
    protected void onPostExecute(teamDetails_model teamDetailsModel) {
        super.onPostExecute(teamDetailsModel);
        if(teamDetailsModel != null){
            teamDetails.getInstance().teamDetailsSuccessful(teamDetailsModel);
        }else {
            teamDetails.getInstance().teamDetailsFailed(errorMessage);
        }
    }
}
