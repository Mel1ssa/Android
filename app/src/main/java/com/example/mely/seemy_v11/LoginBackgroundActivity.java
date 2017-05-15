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
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by MELY on 3/31/2017.
 */

public class LoginBackgroundActivity extends AsyncTask {

    public LoginBackgroundActivity(Context context){}


    @Override
    protected Map<String, String> doInBackground(Object[] objects) {

        String username = (String) objects[0];
        String password = (String) objects[1];
        Map<String, String> retour = new HashMap<String, String>();

        //String link = "http://10.127.209.87/android/auth.php?Pseudo="+username+"&MotDePasse="+password;
        String link = "http://nicolasdke.cluster023.hosting.ovh.net/seemy/auth_PDO.php?Pseudo="+username+"&MotDePasse="+password;

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
            Log.e("debug : coucou1",""+success);
            retour.put("success",Integer.toString(success));
            if(success==1){
                Log.e("debug : coucou","x");
                retour.put("Id",jObject.getString("id"));
                retour.put("Age",jObject.getString("age"));
                retour.put("Sexe",jObject.getString("sexe"));
                retour.put("Dist",jObject.getString("dist"));
                Log.e("debug : coucou",jObject.getString("id"));
            }

            return retour;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
