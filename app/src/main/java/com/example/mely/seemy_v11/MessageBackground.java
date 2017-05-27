package com.example.mely.seemy_v11;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Message;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by MELY on 5/24/2017.
 */

public class MessageBackground extends AsyncTask {

    public MessageBackground(Context c){}

    @Override
    protected Object doInBackground(Object[] objects) {
        String cas = (String) objects[0];
        switch (cas) {
            case "envoie":
                String emetteur = (String) objects[1];
                String recepteur = (String) objects[2];
                String message = (String) objects[3];
                message=message.replace(" ","+");
                String link = "http://nicolasdke.cluster023.hosting.ovh.net/seemy/envoi_message.php?id_emetteur=" + emetteur + "&id_recepteur=" + recepteur + "&message=" + message;
                Log.e("link",link);
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
                    Log.e("resultat",res);
                    in.close();
                    //JSON Parsing
                    JSONObject jObject = new JSONObject(res);
                    int success = jObject.getInt("success");
                    Log.e("resultat success",""+success);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            break;
            case "recup":
                emetteur = (String) objects[1];
                recepteur = (String) objects[2];

                link = "http://nicolasdke.cluster023.hosting.ovh.net/seemy/get_message.php?id_emetteur=" + emetteur + "&id_recepteur=" + recepteur;

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
                    //JSON Parsing
                    ArrayList<ChatMessage> liste_messages = new ArrayList<ChatMessage> ();
                    JSONObject jObject = new JSONObject(res);
                    int success = jObject.getInt("success");
                    if (success == 1) {
                        JSONArray obj = jObject.getJSONArray("info_message");

                        for (int i = 0; i < obj.length(); i++) {
                            JSONObject m= obj.getJSONObject(i);
                            String mess= m.getString("Contenu_message");
                            boolean prov= m.getBoolean("Orientation");
                            ChatMessage M = new ChatMessage(prov,mess);
                            liste_messages.add(M);

                        }

                    }

                    return liste_messages;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "discussion":
                emetteur = (String) objects[1];

                link = "http://nicolasdke.cluster023.hosting.ovh.net/seemy/get_discussion2.php?id_emetteur=" + emetteur ;
                ArrayList<Map<String, Object>> liste = new ArrayList<Map<String, Object>>();

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

                        JSONArray user = jObject.getJSONArray("discussion");

                        for (int i = 0; i < user.length(); i++) {
                            Map<String, Object> ret = new HashMap<String, Object>();
                            JSONObject obj= user.getJSONObject(i);
                            ret.put("id",obj.getString("id_utilisateur"));
                            ret.put("dernier_msg",obj.getString("dernier_msg"));
                            ret.put("pseudo",obj.getString("pseudo"));
                            if(obj.getString("sexe").equals("H"))
                                ret.put("sexe",R.drawable.user_male);
                            else
                                ret.put("sexe",R.drawable.user_female);

                            liste.add(ret);
                        }

                        return liste;

                    }
                    else
                        return liste;
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("error log", e.getMessage());
                }
                break;
        }
        return null;
    }
}
