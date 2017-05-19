package com.example.mely.seemy_v11;

/**
 * Created by MELY on 4/17/2017.
 */
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@SuppressLint("ValidFragment")
public class Deconnexion extends Fragment{
    String Id;
    @SuppressLint("ValidFragment")
    public Deconnexion(String Id) {
        this.Id=Id;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AsyncTask AT2=  new UpdateProfilBackground(getActivity()).execute("Deconnexion",Id);
        Log.e("deco","apres AT2");
        Intent intent = new Intent(getContext(),LoginActivity.class);
        startActivity(intent);
        return null;
    }
}
