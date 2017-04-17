package com.example.mely.seemy_v11;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by MELY on 4/17/2017.
 */

public class Rechercher extends Fragment {
    public Rechercher() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.rechercher, container, false);

        return rootView;
    }
}
