package com.example.aleksandr.mycars_zloj.date_base.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.aleksandr.mycars_zloj.date_base.DateBaseBrend;
import com.example.aleksandr.mycars_zloj.entity.Brend;
import com.example.aleksandr.mycars_zloj.staticVariables.StaticVariables;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Aleksandr on 07.06.2015.
 */
public class BrendDataBaseImpl extends SQLiteOpenHelper implements DateBaseBrend, StaticVariables {

    private SQLiteDatabase db;
    private ContentValues values;
    private Cursor cursor;

    public BrendDataBaseImpl(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BREND_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void addBrend(Brend brend) {
        Log.d(LOG_TAG, "--- add new brend ---");

        db = this.getWritableDatabase();
        values = new ContentValues();
        values.put(NAME_COLUMN, brend.getNameBrend());
        Log.d(LOG_TAG, "--- brend" + brend.getNameBrend().toString() +" ---");

        db.insert(BREND_TABLE, null, values);
        Log.d(LOG_TAG, "--- insert brend ---");

        db.close();
        Log.d(LOG_TAG, "---db close---");
    }

    @Override
    public List<Brend> getListBrends(int id) {
        List<Brend> brendList = new ArrayList<>();
        Log.d(LOG_TAG, "--- get all brends ---");

        String query = "SELECT * FROM "+BREND_TABLE+";";
        db = this.getReadableDatabase();
        cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            Brend brend = null;
            do {
                if (cursor.getInt(0) == id) {
                    brend = new Brend();
                    brend.setId(cursor.getInt(0));
                    brend.setNameBrend(cursor.getString(1));
                    brendList.add(brend);
                }
            } while (cursor.moveToNext());
        }
        db.close();
        return brendList;
    }

    @Override
    public List<Brend> getAllBrend() {
        List<Brend> brendList = new ArrayList<>();
        Log.d(LOG_TAG, "--- get all brends ---");

        String query = "SELECT * FROM "+BREND_TABLE+";";
        db = this.getReadableDatabase();
        cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            Brend brend = null;
            do {
                brend = new Brend();
                brend.setId(cursor.getInt(0));
                brend.setNameBrend(cursor.getString(1));
                brendList.add(brend);
            } while (cursor.moveToNext());
        }
        db.close();
        return brendList;
    }

    @Override
    public void renameBrend(String name1, String name2) {
        List<Brend> brendList = getAllBrend();;
        for (Brend brend : brendList) {
            if (brend.getNameBrend().toString().equals(name1)) {
                int id = brend.getId();
                deleteBrend(id);
                addBrend(new Brend(id, name2));
            }
        }
    }

    @Override
    public void deleteBrend(int id) {
        String query = "id = " + id;
        db = this.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys = ON;");
        db.delete(BREND_TABLE,query, null );
        db.close();
    }

    @Override
    public void deleteAllBrends() {

    }

    @Override
    public int getIdBrend(String name) {
        List<Brend> brendList = new ArrayList<>();
        String query = "SELECT * FROM "+BREND_TABLE+";";
        db = this.getReadableDatabase();
        cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            Brend brend;
            do {
                brend = new Brend();
                brend.setId(cursor.getInt(0));
                brend.setNameBrend(cursor.getString(1));
                brendList.add(brend);
            } while (cursor.moveToNext());
        }
        db.close();

        int id = 0;
        for (Brend brend : brendList) {
            if (brend.getNameBrend().equals(name)) {
                id = brend.getId();
            }
        }
        return id;
    }
}
