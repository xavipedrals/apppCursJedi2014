package com.example.xavivaio.appxavi.Dades;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by xavivaio on 02/02/2015.
 */
public class GestorBD extends SQLiteOpenHelper {

    //Declaracion del nombre de la base de datos
    public static final int DATABASE_VERSION = 1;

    //Declaracion global de la version de la base de datos
    public static final String DATABASE_NAME = "Dades";

    //Declaracion del nombre de la tabla
    public static final String PUNTUACIO_TABLE ="Puntuacio";

    //sentencia global de cracion de la base de datos
    public static final String PUNTUACIO_TABLE_CREATE = "CREATE TABLE " + PUNTUACIO_TABLE + "(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, punts INTEGER);";

    public static final String PUNTUACIO_TABLE_RESET = "DELETE FROM " + PUNTUACIO_TABLE;

    public GestorBD(Context context){
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PUNTUACIO_TABLE_CREATE);
    }

    public void insertPuntuacio (String name, int punts){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("name", name);
        contentValues.put("punts", punts);

        db.insert(PUNTUACIO_TABLE, null, contentValues);
    }


    public Cursor getPuntuacions() {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"name", "punts"};
        Cursor c = db.query(
                PUNTUACIO_TABLE,                        // The table to query
                columns,                                // The columns to return
                null,                                   // The columns for the WHERE clause
                null,                                   // The values for the WHERE clause
                null,                                   // don't group the rows
                null,                                   // don't filter by row groups
                null                                    // The sort order
        );
        return c;
    }

    public void resetTablePuntuacio(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(PUNTUACIO_TABLE_RESET);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }
}
