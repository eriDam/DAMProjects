package com.dam.datos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    final static String TABLE_NAME = "artistas";
    final static String ARTIST_NAME = "nombre";
    final static String ID = "_id";
    final static String[] columns = { ID, ARTIST_NAME };

    final private static String CREATE_CMD =

            "CREATE TABLE artistas (" + ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + ARTIST_NAME + " TEXT NOT NULL)";

    final private static String NAME = "artistas_db";
    final private static Integer VERSION = 1;
    final private Context mContext;

    public DataBaseHelper(Context context) {
        super(context, NAME, null, VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CMD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // N/A
    }

    void deleteDatabase() {
        mContext.deleteDatabase(NAME);
    }
}
