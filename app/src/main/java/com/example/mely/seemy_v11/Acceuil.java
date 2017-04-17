package com.example.mely.seemy_v11;

/**
 * Created by MELY on 4/17/2017.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Acceuil extends Fragment {

    public Acceuil() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.acceuil, container, false);

        return rootView;
    }
}
