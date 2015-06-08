package com.example.aleksandr.mycars_zloj.date_base.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.aleksandr.mycars_zloj.date_base.DateBaseModel;
import com.example.aleksandr.mycars_zloj.entity.Brend;
import com.example.aleksandr.mycars_zloj.entity.Model;
import com.example.aleksandr.mycars_zloj.staticVariables.StaticVariables;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Aleksandr on 07.06.2015.
 */
public class ModelDataBaseImpl extends SQLiteOpenHelper implements DateBaseModel, StaticVariables {

    private SQLiteDatabase db;
    private ContentValues values;
    private Cursor cursor;


    public ModelDataBaseImpl(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MODEL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void addModel(Model model, int idBrend) {
        Log.d(LOG_TAG, "--- add new model ---");

        db = this.getWritableDatabase();
        values = new ContentValues();
        values.put(NAME_COLUMN, model.getNameModel());
        Log.d(LOG_TAG, "--- model" + model.getNameModel().toString() +" ---");

        values.put(ID_BREND_COLUMN, idBrend);
        Log.d(LOG_TAG, "--- model" + idBrend +" ---");

        db.insert(MODEL_TABLE, null, values);
        Log.d(LOG_TAG, "--- insert brend ---");

        db.close();
        Log.d(LOG_TAG, "---db close---");
    }

    @Override
    public List<Model> getListModels(int id) {
        List<Model> models = new ArrayList<>();
        Log.d(LOG_TAG, "--- get all models ---");

        String query = "SELECT * FROM "+MODEL_TABLE+";";
        db = this.getReadableDatabase();
        cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if(cursor.moveToFirst()) {
            Model model = null;
            do {
                if (cursor.getInt(2) == id) {
                    model = new Model();
                    model.setId(cursor.getInt(0));
                    model.setNameModel(cursor.getString(1));
                    model.setId_brend(cursor.getInt(2));
                    models.add(model);
                }
            } while (cursor.moveToNext());
        }
        db.close();
        return models;

    }

    @Override
    public List<Model> getAllModel() {
        List<Model> models = new ArrayList<>();
        Log.d(LOG_TAG, "--- get all models ---");

        String query = "SELECT * FROM "+MODEL_TABLE+";";
        Log.d(LOG_TAG, query);

        db = this.getReadableDatabase();
        cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            Model model = null;
            do {
                    model = new Model();
                    model.setId(cursor.getInt(0));
                    model.setNameModel(cursor.getString(1));
                    model.setId_brend(cursor.getInt(2));
                    models.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return models;
    }

    @Override
    public void renameModel(String name1, String name2) {
        List<Model> models = getAllModel();;
        for (Model model : models) {
            if (model.getNameModel().toString().equals(name1)) {
                int id = model.getId();
                int id_br = model.getId_brend();
                deleteModel(id);
                addModel(new Model(id, name2, id_br), id_br);
            }
        }
    }

    @Override
    public void deleteModel(int id) {
        String query = "id = " + id;
        db = this.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys = ON;");
        db.delete(MODEL_TABLE,query, null );
        db.close();
    }

    @Override
    public void deleteAllModels() {

    }

    @Override
    public int getIdModel(String name) {
        List<Model> models = new ArrayList<>();
        String query = "SELECT * FROM "+MODEL_TABLE+";";
        db = this.getReadableDatabase();
        cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            Model model;
            do {
                model = new Model();
                model.setId(cursor.getInt(0));
                model.setNameModel(cursor.getString(1));
                models.add(model);
            } while (cursor.moveToNext());
        }
        db.close();

        int id = 0;
        for (Model model : models) {
            if (model.getNameModel().equals(name)) {
                id = model.getId();
            }
        }
        return id;
    }
}
