package com.example.mely.seemy_v11;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class MainUserActivity extends AppCompatActivity {

    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    Toolbar toolbar;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;
    LocationManager lm;
    Utilisateur user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);

        mTitle = mDrawerTitle = getTitle();
        //récup les titres du menu
        mNavigationDrawerItemTitles= getResources().getStringArray(R.array.navigation_items);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        //mise en place de la barre des tâches
        setupToolbar();

        //Remplissage du menu avec icones et titres
        DataModel[] drawerItem = new DataModel[4];

        drawerItem[0] = new DataModel(R.drawable.acceuil, "Acceuil");
        drawerItem[1] = new DataModel(R.drawable.profil, "Profil");
        drawerItem[2] = new DataModel(R.drawable.message, "Messages");
        drawerItem[3] = new DataModel(R.drawable.deconnexion, "Deconnexion");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);

        //on rajoute le tout à l'adapteur
        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.list_view_item_row, drawerItem);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        setupDrawerToggle();

        //récup du user (envoyé depuis login)
        user= (Utilisateur) getIntent().getSerializableExtra("USER");

        //localisation du user afin de récupérer les autres users à proximité
        try
        {
            localise();
        } catch (Exception e) { e.printStackTrace(); }

        //selectionner Acceuil
        selectItem(0);

    }



    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        // selectionne la page a ouvrir selon la position
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }

    }

    private void selectItem(int position) {
    // selectionne la page a ouvrir selon la position
        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new Acceuil(user);
                break;
            case 1:
                //   Toast.makeText(this.getBaseContext(),user.toString(),Toast.LENGTH_SHORT).show();
                fragment = new Profil(user);
                break;
            case 2:
                fragment = new Messages();
                break;
            case 3:
                fragment = new Deconnexion();
                break;

            default:
                break;
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(mNavigationDrawerItemTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //bouton sert à actualiser la position actuelle;
        //(la position est calculée à chaque fois que l'on va sur la page d'acceuil)
        ImageButton ref_btn = (ImageButton) toolbar.findViewById(R.id.refresh);
        ref_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {localise();} catch (Exception e) {e.printStackTrace();}}
        });
    }

    void setupDrawerToggle() {
        mDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        //This is necessary to change the icon of the Drawer Toggle upon state change.
        mDrawerToggle.syncState();
    }

    public void localise() throws ExecutionException, InterruptedException {

        Location location = null;
        String latitude, longitude, altitude;
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //vérifie qu'on a accès au gps
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(getBaseContext()," L'application n'a pas accès à votre position ", Toast.LENGTH_LONG).show();

        }
        //recuperation des données transmise par le gps
        location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        latitude = String.valueOf(location.getLatitude());
        longitude = String.valueOf(location.getLongitude());
        altitude = String.valueOf(location.getAltitude());

        //envoie des données a la base
        AsyncTask AT=  new UpdateProfilBackground(this).execute("Localisation",user.getPseudo(),longitude,latitude,altitude);
        String S= (String) AT.get();

    }


}
