package com.example.mely.seemy_v11;

/**
 * Created by MELY on 4/17/2017.
 */
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

import butterknife.Bind;

import static android.app.Activity.RESULT_OK;

@SuppressLint("ValidFragment")
public class Profil extends Fragment implements View.OnClickListener {
    Utilisateur user;

    @SuppressLint("ValidFragment")
    public Profil(Utilisateur user) {
        this.user=user;

    }

    TextView edt_tags;
    String select;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.profil, container, false);
        TextView edt = (TextView) rootView.findViewById(R.id.user_profile_name);
        edt.setText(user.getPseudo());
        edt_tags = (TextView) rootView.findViewById(R.id.user_profile_short_bio);
        //affichage des tags
        this.setTags();

        //affichage de la photo de profil selon le sexe
        ImageView imB = (ImageView) rootView.findViewById(R.id.user_profile_photo);
        if(user.getSexe().equals("H"))
            imB.setImageResource(R.drawable.user_male);
        else
            imB.setImageResource(R.drawable.user_female);

        //appel a la même méthode onclick selon le bouton
        Button suppTags=(Button)rootView.findViewById(R.id.btn_supp_tag);
        suppTags.setOnClickListener(this);

        Button addTags = (Button)rootView.findViewById(R.id.btn_add_tags);
        addTags.setOnClickListener(this);

        Button addDistance = (Button)rootView.findViewById(R.id.btn_add_distance);
        addDistance.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_add_tags:
                this.openTagDialog();
                break;
            case R.id.btn_add_distance:
                this.openCheckDistance();
                break;
            case R.id.btn_supp_tag:
                this.openCheckDeleteTags();
                break;
        }
    }


    private void setTags(){
        if(user.getTags()!=null) {
            String t="";
            for(String s : user.getTags())
                if(!s.equals(""))
                    t=t+"#"+s+" ";
            edt_tags.setText(t);
        }
    }

    private void openTagDialog(){
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View subView = inflater.inflate(R.layout.listeview_tags, null);
        final EditText _tag = (EditText)subView.findViewById(R.id.EditTag);

        //ouvre une fenetre pour rajouter un tag
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.addtags);
        builder.setView(subView);
        builder.setPositiveButton(R.string.add, null);
        builder.setNegativeButton(R.string.close, null);
        final AlertDialog alertDialog = builder.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {
                Button button = ((AlertDialog) dialog).getButton(alertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            if (!_tag.getText().toString().equals("")) {
                                edt_tags.append(" #" + _tag.getText().toString()); // affichage du tag+ #
                                //Ajoute le tag a la bd
                                AsyncTask AT = new UpdateProfilBackground(getActivity()).execute( "Tag",user.getId(), _tag.getText().toString());
                                user.add_Tag(_tag.getText().toString());
                                //raz ddu champ tag
                                _tag.setText("");
                            }

                        } catch (Exception e) {
                            Toast.makeText(getActivity(), R.string.error+ e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
                }
            }
        );

        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener()
        { //ne fait rien si on annule
            @Override
            public void onClick(DialogInterface dialog, int which) {}

        });
        //affichage
        alertDialog.show();
    }

    private  void openCheckDistance(){
        Dialog dialog;
        final CharSequence items[] = {"100","200","500","Aucun"};
        final ArrayList itemsSelected = new ArrayList();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.reglerdistance);

        builder.setSingleChoiceItems(items,-1,  new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int selected) {
                select= (String) items[selected];

            }
        }).setPositiveButton(R.string.valider, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //maj de la base de donnée
                if(!select.equals("Aucun")){
                    AsyncTask AT=  new UpdateProfilBackground(getActivity()).execute("Distance",user.getId(),select);
                    user.setDist(select);
                }
                else{
                    AsyncTask AT=  new UpdateProfilBackground(getActivity()).execute("Distance",user.getId(),"0");
                    user.setDist("0");
                }

            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            // ne fait rien si on annule
            @Override
            public void onClick(DialogInterface dialog, int which) {}

        });
        //affichage
        dialog = builder.create();
        dialog.show();

    }

    private void openCheckDeleteTags(){
        Dialog dialog;
        final CharSequence[] items= new CharSequence[user.getTags().size()];

        for(int i=0;i<user.getTags().size();i++){

            items[i]= (CharSequence) user.getTags().get(i) ;


        }

        final ArrayList itemsSelected = new ArrayList();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.supptagdialog);
        builder.setMultiChoiceItems(items, null,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which,
                                        boolean isChecked) {
                        if (isChecked) {
                            // If the user checked the item, add it to the selected items
                            itemsSelected.add(which);
                        } else if (itemsSelected.contains(which)) {
                            // Else, if the item is already in the array, remove it
                            itemsSelected.remove(Integer.valueOf(which));
                        }
                    }

        }).setPositiveButton(R.string.supp, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //maj de la base de donnée

                   for(int i=0;i<itemsSelected.size();i++){

                       AsyncTask AT = new UpdateProfilBackground(getActivity()).execute("SuppTag", user.getId(), (String) items[(int) itemsSelected.get(i)]);
                       user.remove_Tag((String) items[(int) itemsSelected.get(i)]);

                   }
                setTags();
                Toast.makeText(getActivity(),R.string.deleteelem,Toast.LENGTH_SHORT).show();


            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            // ne fait rien si on annule
            @Override
            public void onClick(DialogInterface dialog, int which) {}

        });
        //affichage
        dialog = builder.create();
        dialog.show();

    }
}
