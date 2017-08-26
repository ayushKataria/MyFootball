package com.example.akat2.myfootball.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;

import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGParser;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Created by KATARIA on 11-07-2017.
 */

public class utils {


    public static String getDateForTimeZone(String date, String time){
        String[] d = date.split("-");
        String[] t = time.split(":");
        int year = Integer.parseInt(d[0]);
        int month = Integer.parseInt(d[1]);
        int day = Integer.parseInt(d[2]);
        int hour = Integer.parseInt(t[0]);
        int minutes = Integer.parseInt(t[1]);

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.getDefault());
        Date currentLocalTime = calendar.getTime();
        DateFormat format = new SimpleDateFormat("z", Locale.getDefault());
        String localTime = format.format(currentLocalTime);

        String timeZone = localTime.substring(4);
        if(localTime.charAt(3) == '+'){
            String timeZoneSplit[] = timeZone.split(":");
            int hourTimeZone = Integer.parseInt(timeZoneSplit[0]);
            int minTimeZone = Integer.parseInt(timeZoneSplit[1]);
            minutes += minTimeZone;
            if(minutes>=60){
                hour++;
                minutes -= 60;
            }
            hour += hourTimeZone;
            if(hour>=24){
                hour -= 24;
                day++;
            }
            if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                if (day > 31) {
                    day -= 31;
                    month++;
                }
            }else if (month == 2) {
                    if (day > 28) {
                        day -= 28;
                        month++;
                    }
            } else {
                if(day > 30) {
                    day -= 30;
                    month++;
                }
            }
            if (month > 12){
                month -= 12;
                year++;
            }
        }
        DecimalFormat dformat = new DecimalFormat("00");
        String twodigitMonth = dformat.format(month);
        String twoDigitDay = dformat.format(day);
        String twoDigitHour = dformat.format(hour);
        String twoDigitminutes = dformat.format(minutes);
        String dateFinal = year + "-"  + twodigitMonth + "-" + twoDigitDay;
        String timeFinal = twoDigitHour + ":" + twoDigitminutes + ":";
        return dateFinal+"T"+timeFinal+t[2];
    }

    public static String getCompetitionName(int id){
        switch (id){
            case 444:
                return "Campeonato Brasileiro da Série A";
            case 445:
                return "Premier League 2017/18";
            case 446:
                return "Championship 2017/18";
            case 447:
                return "League One 2017/18";
            case 448:
                return "League Two 2017/18";
            case 449:
                return "Eredivisie 2017/18";
            case 450:
                return "Ligue 1 2017/18";
            case 451:
                return "Ligue 2 2017/18";
            case 452:
                return "1. Bundesliga 2017/18";
            case 453:
                return "2. Bundesliga 2017/18";
            case 455:
                return "Primera Division 2017";
            case 456:
                return "Serie A 2017/18";
            case 457:
                return "Primeira Liga 2017/18";
            case 458:
                return "DFB-Pokal 2017/18";
            case 459:
                return "Serie B 2017/18";
        }
        return "";
    }

    public static OkHttpClient getOkHttpClient(){
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(50, TimeUnit.SECONDS);
        client.setReadTimeout(50, TimeUnit.SECONDS);
        client.setWriteTimeout(50, TimeUnit.SECONDS);

        return client;

    }

    public static Response postRequest(OkHttpClient client, String url, String json) throws IOException {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .header("X-Auth-Token", "1bd311bc4178458ab47184f49cb7622d")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }

    public static Response putRequest(OkHttpClient client, String url, String json) throws IOException {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .header("X-Auth-Token", "1bd311bc4178458ab47184f49cb7622d")
                .put(body)
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }

    public static Response getRequest(OkHttpClient client, String url) throws IOException {

        Request request = new Request.Builder()
                .url(url)
                .header("X-Auth-Token", "1bd311bc4178458ab47184f49cb7622d")
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }

    //Check if connected to network
    public static boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager)
                    context.getSystemService(context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            // if no network is available networkInfo will be null
            // otherwise check if we are connected
            return (networkInfo != null && networkInfo.isConnected());
        } catch (Exception ex) {
            return false;
        }
        /*boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;*/

    }


}
