package com.example.mely.seemy_v11;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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

public class InfoProfilBackgroundActivity extends AsyncTask {

    Context context;
    public InfoProfilBackgroundActivity(Context context){}


    @Override
    protected String doInBackground(Object[] objects) {

        String username = (String) objects[0];
        String link = "http://10.127.209.87/android/get_user_tags.php?Pseudo=" + username;

        try {
            URL url = new URL(link);
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(link));
            HttpResponse response = client.execute(request);

            BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String line = "";
            line = in.readLine();
            line = in.readLine();// pour supprimer les db_conf en attendant de trouver mieux
            while ((line = in.readLine()) != null) {
                sb.append(line);

            }
            String res = sb.toString();
            in.close();
            //JSON Parsing
            JSONObject jObject = new JSONObject(res);
            int success = jObject.getInt("success");

            if (success == 1) {

                JSONArray tags = jObject.getJSONArray("tag");

                String retour =" ";

                for (int i = 0; i < tags.length(); i++) {
                    JSONObject obj= tags.getJSONObject(i);
                    String tag = obj.getString("Contenu_tag");
                    retour = retour + tag + " ";
                }
                return retour;

            }
            else
                return null;

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("error",e.getMessage());
        }

    return null;
    }
}
