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

public class SaveTagBackground extends AsyncTask {
    Context context;

    public SaveTagBackground(Context context) {
    }


    @Override
    protected String doInBackground(Object[] objects) {

        String username = (String) objects[0];
        String tag = (String) objects[1];
        String cas = (String) objects[2];
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
                    in.close();
                    return res;

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("error", e.getMessage());
                }
                break;
            case "Distance":

                 link = "http://10.127.209.87/android/updateDistance.php?Pseudo="+objects[0]+"&Distance_param="+Integer.parseInt((String)objects[1]);

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
