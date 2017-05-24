package com.example.mely.seemy_v11;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * Created by MELY on 5/24/2017.
 */

public class MessageBackground extends AsyncTask {

    public MessageBackground(Context c){}

    @Override
    protected Object doInBackground(Object[] objects) {

        String emetteur = (String) objects[0];
        String recepteur = (String) objects[1];
        String message = (String) objects[2];

        String link= "http://nicolasdke.cluster023.hosting.ovh.net/seemy/envoi_message.php?id_emetteur="+emetteur+"&id_recepteur="+recepteur+"&message="+message;
        try {
            URL url = new URL(link);

        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet();
        request.setURI(new URI(link));
        HttpResponse response = client.execute(request);

        BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuffer sb = new StringBuffer("");
        String line="";
        Log.e("debug : ligne 1",line);
        line = in.readLine();// pour supprimer les db_conf en attendant de trouver mieux


        while ((line = in.readLine()) != null) {
            sb.append(line);

        }
            String res= sb.toString();
            in.close();
            //JSON Parsing
            JSONObject jObject = new JSONObject(res);
            int success = jObject.getInt("success");
            Log.e("debug : success message",""+success);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
