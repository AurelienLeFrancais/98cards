package com.example.tpfinal;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Vector;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper instance;
    private SQLiteDatabase database;



    public static DatabaseHelper getInstance(Context context)
    {
        if(instance == null)
            instance = new DatabaseHelper(context.getApplicationContext());//erreur dans un premier temps
        return  instance;
    }

    public DatabaseHelper(Context context) {
        super(context,"nom",null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table scoreTotal(_id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, point INTEGER, minute INTEGER, seconds INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists scoreTotal");//supprime la table
        onCreate(db);//recree la table

    }

    public void ajoutScore(AjouterScore as){
        ContentValues cv = new ContentValues();
        cv.put("date", as.getDate());
        cv.put("point", as.getPoints());
        cv.put("minute", as.getMinute());
        cv.put("seconds",as.getSeconds());
        database.insert("scoreTotal", null, cv);

    }

    public void ouvrirBD()
    {

        database = this.getWritableDatabase();

    }
    public void fermerBD()
    {
        database.close();
    }

    public Vector<String> retournerScore()
    {
        Vector<String> v = new Vector();
        Cursor c;
        int i = 1;
        //on prend les 10 meilleurs resultats
        c = database.rawQuery("select date, point, minute, seconds from scoreTotal order by point desc limit 10",null);
        while(c.moveToNext())
        {
            String date = c.getString(0);
            int point = c.getInt(1);
            int minute = c.getInt(2);
            int seconds = c.getInt(3);
            String score = i +  "/ Le " + date + " : " + point + " points" + " en "+ minute + " : "+ seconds + " min";
            v.add(score);
            i++;

        }

        c.close();
        return  v;
    }
}
