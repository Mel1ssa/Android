package com.example.mely.seemy_v11;

import android.content.Context;
import android.os.AsyncTask;

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
                String id= (String) objects[1];
                String tag = (String) objects[2];
                String link = "http://nicolasdke.cluster023.hosting.ovh.net/seemy/insert_tag_PDO.php?Id=" + id + "&Contenu_tag=" + tag;

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
                }
                break;
            case "SuppTag":
                id= (String) objects[1];
                tag = (String) objects[2];
                link = "http://nicolasdke.cluster023.hosting.ovh.net/seemy/delete_tag_PDO.php?id=" + id + "&tag=" + tag;

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
                }
                break;
            case "Distance":
                id= (String) objects[1];
                String distance = (String) objects[2];
                link="http://nicolasdke.cluster023.hosting.ovh.net/seemy/UpdateDistance_PDO.php?Id="+id+"&Distance_param="+Integer.parseInt(distance);
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
                }
                break;
            case "Localisation":
                String Id= (String) objects[1];
                String longitude = (String) objects[2];
                String latitude = (String) objects[3];
                String altitude = (String) objects[4];
                link = "http://nicolasdke.cluster023.hosting.ovh.net/seemy/updateLocalisation_PDO.php?Id="+Id+"&Longitude="+longitude+"&Latitude="+latitude+"&Altitude="+altitude;

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


                    while ((line = in.readLine()) != null) {
                        sb.append(line);

                    }
                    String res = sb.toString();
                    in.close();
                    return res;

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Recherche":
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

                             id = (String) ids.get(i);
                            retour = retour + id + " ";
                        }
                        return retour;

                    }
                    else
                    return res;

            } catch (Exception e) {
                e.printStackTrace();
            }
                break;
            case "RecupUsers":
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
                        ret.put("success",Integer.toString(success));
                        JSONArray user = jObject.getJSONArray("utilisateur");


                        for (int i = 0; i < user.length(); i++) {
                            JSONObject obj= user.getJSONObject(i);
                            ret.put("Id",obj.getString("Id"));
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
                }
                break;
            case "Deconnexion":
                id= (String) objects[1];
                link="http://nicolasdke.cluster023.hosting.ovh.net/seemy/Deconnexion_PDO.php?Id="+id;

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
                }
                break;

        }
return  null;
    }


}
