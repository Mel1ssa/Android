package com.example.mely.seemy_v11;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nicolas on 23/05/17.
 */

public class BaseLocal extends SQLiteOpenHelper {

    String REQUETE_CREATION_BD = "";

    public BaseLocal(Context context, String nom, SQLiteDatabase.CursorFactory cursorfactory, int version) {
        super(context, nom, cursorfactory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(REQUETE_CREATION_BD);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Dans notre cas, nous supprimons la base et les données pour en créer une nouvelle ensuite
        db.execSQL("drop table" + ";");
        // Création de la nouvelle structure.
        onCreate(db);
    }
}
