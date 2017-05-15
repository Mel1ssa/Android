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
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by MELY on 4/30/2017.
 */

public class UpdateProfilBackground extends AsyncTask {
    Context context;

    public UpdateProfilBackground(Context context) {
    }


    @Override
    protected Object doInBackground(Object[] objects) {
        String cas = (String) objects[0];
        String username = (String) objects[1];


        switch (cas) {
            case "Tag":
                String tag = (String) objects[2];
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
                String Id= (String) objects[1];
                String longitude = (String) objects[2];
                String latitude = (String) objects[3];
                String altitude = (String) objects[4];

                //link = "http://10.127.209.87/android/updateLocalisation.php?Pseudo="+username+"&Longitude="+longitude+"&Latitude="+latitude+"&Altitude="+altitude;
                link = "http://nicolasdke.cluster023.hosting.ovh.net/seemy/updateLocalisation_PDO.php?Id="+Id+"&Longitude="+longitude+"&Latitude="+latitude+"&Altitude="+altitude;
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
                    line = in.readLine();// pour supprimer les db_conf en attendant de trouver mieux
                    Log.e("debug : ligne tag",line);

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
            case "Recherche":
                //link = "http://10.127.209.87/android/GetUsersByDistance.php?Pseudo=" + username;
                link = "http://nicolasdke.cluster023.hosting.ovh.net/seemy/GetUsersByDistance_PDO.php?Id=" + objects[1];
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
                    while ((line = in.readLine()) != null) {
                        sb.append(line);

                    }
                    String res = sb.toString();
                    in.close();
                    //JSON Parsing
                    JSONObject jObject = new JSONObject(res);
                    int success = jObject.getInt("success");
                    if (success == 1) {
                        JSONArray ids = jObject.getJSONArray("utilisateurs");
                        String retour ="";

                        for (int i = 0; i < ids.length(); i++) {

                            String id = (String) ids.get(i);
                            retour = retour + id + " ";
                        }
                        return retour;

                    }
                    else
                    return res;

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("error log", e.getMessage());
            }
                break;
            case "RecupUsers":
                //link = "http://10.127.209.87/android/getUsersById.php?id=" + objects[1];
                link =  "http://nicolasdke.cluster023.hosting.ovh.net/seemy/getUsersById_PDO.php?id=" + objects[1];
                Map<String, Object> ret = new HashMap<String, Object>();
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
                    while ((line = in.readLine()) != null) {
                        sb.append(line);

                    }
                    String res = sb.toString();
                    in.close();
                    //JSON Parsing
                    JSONObject jObject = new JSONObject(res);
                    int success = jObject.getInt("success");

                    if (success == 1) {
                        Log.e("recup user","ty");
                        ret.put("success",Integer.toString(success));
                        JSONArray user = jObject.getJSONArray("utilisateur");
                        String retour =" ";

                        for (int i = 0; i < user.length(); i++) {
                            JSONObject obj= user.getJSONObject(i);
                            ret.put("Pseudo",obj.getString("Pseudo"));
                            ret.put("Age",obj.getString("Age")+" ans");
                            if(obj.getString("Sexe").equals("H"))
                                ret.put("Sexe",R.drawable.user_male);
                            else
                                ret.put("Sexe",R.drawable.user_female);
                        }

                        return ret;

                    }
                    else
                        return res;

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("error log", e.getMessage());
                }
                break;

        }
return  null;
    }


}
