package com.example.mely.seemy_v11;

/**
 * Created by MELY on 4/17/2017.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Deconnexion extends Fragment{
    public Deconnexion() {
        Intent intent = new Intent(getContext(),LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        return null;
    }
}