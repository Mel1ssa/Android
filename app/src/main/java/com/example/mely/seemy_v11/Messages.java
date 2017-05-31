package com.example.mely.seemy_v11;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by MELY on 4/17/2017.
 */

@SuppressLint("ValidFragment")
public class Messages extends ListFragment {
    Utilisateur user;
    public Messages(Utilisateur user) {
        this.user=user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.messages, container, false);
        List<Map<String, Object>> userList = new ArrayList<Map<String, Object>>();

        try {
            AsyncTask AT = new MessageBackground(getActivity()).execute("discussion", user.getId()); // recup les discussions existantes
            userList = (ArrayList<Map<String, Object>> ) AT.get();



        } catch (Exception e) {
            e.printStackTrace();
        }


        // Clés utilisées dans la  Hashmap
        String[] from = {"pseudo", "sexe","id","dernier_msg"};

        // les items dans lequels les valeurs de hashmap seront placés
        int[] to = {R.id.user_profile_name,  R.id.user_photo,R.id.hidden_id, R.id.user_last_msg};

        //placer la liste dans un SimpleAdapter
        SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), userList, R.layout.discussion_row, from, to);

        //affichage
        setListAdapter(adapter);


        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int pos, long row) {
        Intent in = new Intent(getActivity(),MessageClass.class);
        // compilation de la regex
        Pattern pt = Pattern.compile(".*sexe=(\\d*)[ |,|}].*id=(\\d*)[ |,|}].*pseudo=(.*), .*");
        //matcher avec les informations qui se trouve dan sla liste et à cette position (pos)
        Matcher ma = pt.matcher(l.getItemAtPosition(pos).toString());
        boolean b = ma.matches();

        in.putExtra("ID_RECEP",ma.group(2));
        if (ma.group(1).equals("2130837634")) // id de l'image
            in.putExtra("SEXE","H");
        else
            in.putExtra("SEXE","F");
        in.putExtra("LOGIN",ma.group(3));
        in.putExtra("ID_EMET",user.getId());
        startActivity(in);





        /*
        //pseudo du recepteur pour l'afficher
        String m[]=l.getItemAtPosition(pos).toString().split("pseudo=");
        String p[]= m[1].split(",");

        if(p.length>0)
            in.putExtra("LOGIN",p[0]);
        else
            in.putExtra("LOGIN",m[1]);
        //id du recepteur pour la requete sql
        String  n[] =l.getItemAtPosition(pos).toString().split("id=");

        p= n[1].split(",");
        in.putExtra("ID_RECEP",p[0]);
        // id de l'emetteur
        in.putExtra("ID_EMET",user.getId()); */

    }
}
