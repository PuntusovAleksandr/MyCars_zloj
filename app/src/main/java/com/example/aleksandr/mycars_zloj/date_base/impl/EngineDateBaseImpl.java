package com.example.aleksandr.mycars_zloj.date_base.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.aleksandr.mycars_zloj.date_base.DateBaseBrend;
import com.example.aleksandr.mycars_zloj.date_base.DateBaseEngine;
import com.example.aleksandr.mycars_zloj.entity.Brend;
import com.example.aleksandr.mycars_zloj.entity.Engine;
import com.example.aleksandr.mycars_zloj.entity.Model;
import com.example.aleksandr.mycars_zloj.staticVariables.StaticVariables;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Aleksandr on 07.06.2015.
 */
public class EngineDateBaseImpl extends SQLiteOpenHelper implements DateBaseEngine, StaticVariables {

    private SQLiteDatabase db;
    private ContentValues values;
    private Cursor cursor;

    public EngineDateBaseImpl(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ENGINE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    @Override
    public void addEngine(Engine engine, int id_model) {
        Log.d(LOG_TAG, "--- add new engine ---");

        db = this.getWritableDatabase();
        values = new ContentValues();
        values.put(VALUE_COLUMN, engine.getVolume());
        Log.d(LOG_TAG, "--- model" + engine.getVolume().toString() +" ---");

        values.put(ID_MODEL_COLUMN, id_model);
        Log.d(LOG_TAG, "--- model" + id_model +" ---");

        db.insert(ENGINE_TABLE, null, values);
        Log.d(LOG_TAG, "--- insert engine ---");

        db.close();
        Log.d(LOG_TAG, "---db close---");
    }

    @Override
    public List<Engine> getListEngines(int id) {
        List<Engine> engines = new ArrayList<>();
        Log.d(LOG_TAG, "--- get all Engine ---");

        String query = "SELECT * FROM "+ENGINE_TABLE+";";
        db = this.getReadableDatabase();
        cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            Engine engine = null;
            do {
                if (cursor.getInt(2) == id) {
                    engine = new Engine();
                    engine.setId(cursor.getInt(0));
                    engine.setVolume(cursor.getString(1));
                    engine.setId_model(cursor.getInt(2));
                    engines.add(engine);
                }
            } while (cursor.moveToNext());
        }
        db.close();
        return engines;
    }

    @Override
    public List<Engine> getAllEngine() {
        List<Engine> engines = new ArrayList<>();
        Log.d(LOG_TAG, "--- get all Engine ---");

        String query = "SELECT * FROM "+ENGINE_TABLE+";";
        db = this.getReadableDatabase();
        cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            Engine engine = null;
            do {
                    engine = new Engine();
                    engine.setId(cursor.getInt(0));
                    engine.setVolume(cursor.getString(1));
                    engine.setId_model(cursor.getInt(2));
                    engines.add(engine);
            } while (cursor.moveToNext());
        }
        db.close();
        return engines;
    }

    @Override
    public void renameEngine(String name1, String name2) {
        List<Engine> engines = getAllEngine();;
        for (Engine engine : engines) {
            if (engine.getVolume().toString().equals(name1)) {
                int id = engine.getId();
                int id_br = engine.getId_model();
                deleteEngine(id);
                addEngine(new Engine(id, name2, id_br), id_br);
            }
        }
    }

    @Override
    public void deleteEngine(int id) {
        String query = "id = " + id;
        db = this.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys = ON;");
        db.delete(ENGINE_TABLE,query, null );
        db.close();
    }

    @Override
    public void deleteAllEngines() {

    }

    @Override
    public int getIdEngine(String name) {
        List<Engine> engines = new ArrayList<>();
        String query = "SELECT * FROM "+ENGINE_TABLE+";";
        db = this.getReadableDatabase();
        cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            Engine engine;
            do {
                engine = new Engine();
                engine.setId(cursor.getInt(0));
                engine.setVolume(cursor.getString(1));
                engines.add(engine);
            } while (cursor.moveToNext());
        }
        db.close();

        int id = 0;
        for (Engine engine : engines) {
            if (engine.getVolume().equals(name)) {
                id = engine.getId();
            }
        }
        return id;
    }
}
