package com.example.mely.seemy_v11;

import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

/**
 * Created by MELY on 3/31/2017.
 */

public class LoginBackgroundActivity extends AsyncTask {

    Context context;
    public LoginBackgroundActivity(Context context){}


    @Override
    protected String doInBackground(Object[] objects) {

        String username = (String) objects[0];
        String password = (String) objects[1];
        String link = "http://172.26.66.45/android/auth.php?Pseudo="+username+"&MotDePasse="+password;

        try {
            URL url = new URL(link);
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(link));
            HttpResponse response = client.execute(request);

            BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String line="";
            line = in.readLine();line = in.readLine();// pour supprimer les db_conf en attendant de trouver mieux
            while ((line = in.readLine()) != null) {
                sb.append(line);

            }
            String res= sb.toString();
            in.close();
            //JSON Parsing
            JSONObject jObject = new JSONObject(res);
            int success = jObject.getInt("success");


            return Integer.toString(success);

        } catch (Exception e) {
            e.printStackTrace();
            return new String("Exception: " + e.getMessage());
        }

    }

}
