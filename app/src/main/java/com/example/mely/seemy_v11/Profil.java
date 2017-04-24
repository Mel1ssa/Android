package com.example.mely.seemy_v11;

/**
 * Created by MELY on 4/17/2017.
 */
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;

import butterknife.Bind;

import static android.app.Activity.RESULT_OK;

public class Profil extends Fragment implements View.OnClickListener {
    public Profil() {
    }
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    @Bind(R.id.user_profile_photo)ImageButton _profileImg;
    //ImageButton imB = (ImageButton)getView().findViewById(R.id.user_profile_photo);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.profil, container, false);
        ImageButton imB = (ImageButton)rootView.findViewById(R.id.user_profile_photo);

        imB.setOnClickListener(this);

        return rootView;
    }
    public void loadImagefromGallery(View view) {
        getActivity().startActivityForResult(
                new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI),
                RESULT_LOAD_IMG);
        Toast.makeText(getActivity(),"Text2!",Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast.makeText(getActivity(),"Text2!",Toast.LENGTH_SHORT).show();
        super.onActivityResult(requestCode, resultCode, data);Toast.makeText(getActivity(),"Text3",Toast.LENGTH_SHORT).show();
        //Detects request codes
        if (requestCode == RESULT_LOAD_IMG && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            // Set the Image in ImageView after decoding the String
            _profileImg.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
        }
        else{
           //Fragment fragment = new Rechercher();
            }


    }

    @Override
    public void onClick(View view) {
        loadImagefromGallery(this.getView());
    }
}
