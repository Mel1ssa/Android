package com.example.mely.seemy_v11;

/**
 * Created by MELY on 4/17/2017.
 */
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressLint("ValidFragment")
public class Acceuil extends ListFragment {

    Utilisateur user;

    @SuppressLint("ValidFragment")
    public Acceuil(Utilisateur user) {
        this.user = user;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        List<Map<String, Object>> userList = new ArrayList<Map<String, Object>>();
        AsyncTask AT = new UpdateProfilBackground(getActivity()).execute("Recherche", user.getId()); // recup les users aux alentours

        try {
            String S = (String) AT.get();
            String[] ids = S.split(" ");

            for (String id : ids) {
                Log.e("success reperage", id);
                AT = new UpdateProfilBackground(getActivity()).execute("RecupUsers", id); // recup les infos de chaque user
                Map<String, Object> usrs = (Map<String, Object>) AT.get();

                if (Integer.parseInt((String) usrs.get("success")) == 1) {
                    AsyncTask AT2 = new InfoProfilBackgroundActivity(getActivity()).execute(id); // recup les tags
                    String S2 = (String) AT2.get();
                    S2 = S2.replace(" ", " #"); // affiche le # car en bd le tag ne possède pas de #
                    usrs.put("Tags", S2);
                    userList.add(usrs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Clés utilisées dans la  Hashmap ( InfoProfilBackground)
        String[] from = {"Pseudo", "Age", "Sexe", "Tags"};

        // les items dans lequels les valeurs de hashmap seront placés
        int[] to = {R.id.user_profile_name, R.id.user_age, R.id.user_photo, R.id.user_profile_short_bio};

        //placer la liste dans un SimpleAdapter
        SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), userList, R.layout.user_row, from, to);

        //affichage
        setListAdapter(adapter);


        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int pos, long row) {
        Intent in = new Intent(getActivity(),MessageClass.class);

        String m[]=l.getItemAtPosition(pos).toString().split("Pseudo=");

        in.putExtra("LOGIN",m[1].substring(0,m[1].length()-1));
        startActivity(in);

    }

}
