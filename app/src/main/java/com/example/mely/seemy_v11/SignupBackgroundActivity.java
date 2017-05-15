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

/**
 * Created by MELY on 4/3/2017.
 */

public class SignupBackgroundActivity extends AsyncTask {

    Context context;
    public SignupBackgroundActivity(Context context){}

    @Override
    protected String doInBackground(Object[] objects) {
        String username = (String) objects[0];
        String password = (String) objects[1];
        String age =      (String) objects[2];
        String email=     (String) objects[3];
        String sexe =     (String) objects[4];

        //String link = "http://10.127.209.87/android/inscription.php?Pseudo="+username+"&MotDePasse="+password+"&Email="+email+"&Age="+age+"&Sexe="+sexe;
        String link = "http://nicolasdke.cluster023.hosting.ovh.net/seemy/inscription_PDO.php?Pseudo="+username+"&MotDePasse="+password+"&Email="+email+"&Age="+age+"&Sexe="+sexe;
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


            return Integer.toString(success);

        } catch (Exception e) {
            e.printStackTrace();
            return new String("Exception: " + e.getMessage());
        }

    }
}
