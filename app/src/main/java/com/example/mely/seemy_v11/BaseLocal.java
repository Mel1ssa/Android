package com.example.mely.seemy_v11;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nicolas on 23/05/17.
 */

public class BaseLocal extends SQLiteOpenHelper {
    public static final int BASE_VERSION = 1;
    public static final String BASE_NOM = "Seemy.db";
    public static final String TABLE = "message";

    public static final String MESSAGE_ID = "Id_Message";
    public static final String MESSAGE_CONTENU = "Contenu_Message";
    public static final String MESSAGE_ID_DISC= "Id_Discussion";
    public static final String MESSAGE_ID_EMETTEUR= "Id_Emetteur";
    public static final String MESSAGE_DATE= "Date_Envoi";
    /**
     * La requête de création de la structure de la base de données.
     */
    private static final String REQUETE_CREATION_BD = "create table "
            + TABLE + " (" + MESSAGE_ID
            + " integer primary key autoincrement, " + MESSAGE_CONTENU
            + " text not null, " + MESSAGE_ID_DISC + " integer not null, "
            +MESSAGE_ID_EMETTEUR + " integer not null" +MESSAGE_DATE+" datetime );";


    private SQLiteDatabase SuperBD;
    private BaseLocal MaBD;

    /*--------------------- Constrcteur ----------------------*/
    public BaseLocal(Context context) {
        super(context, BASE_NOM, null, BASE_VERSION);
    }

    /*--------------------- Methodes ----------------------*/
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(REQUETE_CREATION_BD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table" + TABLE + ";");
        onCreate(db);
    }


    public SQLiteDatabase getDb() {
        return SuperBD;
    }

}
