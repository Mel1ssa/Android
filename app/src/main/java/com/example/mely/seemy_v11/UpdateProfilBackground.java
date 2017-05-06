package com.example.mely.seemy_v11;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

/**
 * Created by MELY on 4/30/2017.
 */

public class UpdateProfilBackground extends AsyncTask {
    Context context;

    public UpdateProfilBackground(Context context) {
    }


    @Override
    protected String doInBackground(Object[] objects) {
        String cas = (String) objects[0];
        String username = (String) objects[1];
        String tag = (String) objects[2];

        switch (cas) {
            case "Tag":

                String link = "http://10.127.209.87/android/insert_user_tag.php?Pseudo=" + username + "&Contenu_tag=" + tag;

                try {
                    URL url = new URL(link);
                    HttpClient client = new DefaultHttpClient();
                    HttpGet request = new HttpGet();
                    request.setURI(new URI(link));
                    HttpResponse response = client.execute(request);

                    BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {
                        sb.append(line);

                    }
                    String res = sb.toString();
                    //verif if success !
                    in.close();
                    return res;

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("error", e.getMessage());
                }
                break;
            case "Distance":
                String distance = (String) objects[2];
                 link = "http://10.127.209.87/android/updateDistance.php?Pseudo="+username+"&Distance_param="+Integer.parseInt(distance);

                try {
                    URL url = new URL(link);
                    HttpClient client = new DefaultHttpClient();
                    HttpGet request = new HttpGet();
                    request.setURI(new URI(link));
                    HttpResponse response = client.execute(request);

                    BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {
                        sb.append(line);

                    }
                    String res = sb.toString();
                    in.close();
                    return res;

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("error", e.getMessage());
                }
                break;
            case "Localisation":
                Log.d("bubu ","debut loc");
                String longitude = (String) objects[2];
                String latitude = (String) objects[3];
                String altitude = (String) objects[4];
                link = "http://10.127.209.87/android/updateLocalisation.php?Pseudo="+username+"&Longitude="+longitude+"&Latitude="+latitude+"&Altitude="+altitude;
                Log.d("longitude ",longitude);
                try {
                    URL url = new URL(link);
                    HttpClient client = new DefaultHttpClient();
                    HttpGet request = new HttpGet();
                    request.setURI(new URI(link));
                    HttpResponse response = client.execute(request);

                    BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {
                        sb.append(line);

                    }
                    String res = sb.toString();
                    in.close();
                    return res;

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("error", e.getMessage());
                }
                break;

        }
return  null;
    }


}
