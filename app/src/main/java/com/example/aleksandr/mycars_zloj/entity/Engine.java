package com.example.aleksandr.mycars_zloj.entity;

import com.example.aleksandr.mycars_zloj.staticVariables.StaticVariables;

/**
 * Created by Aleksandr on 01.06.2015.
 */

public class Engine implements StaticVariables {
    private int id;
    private String volume;
    private int id_model;

    public Engine() {
    }

    public Engine(int id, String volume, int id_model) {
        this.id = id;
        this.volume = volume;
        this.id_model = id_model;
    }

    public Engine(String volume, int id_model) {
        this.volume = volume;
        this.id_model = id_model;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public int getId_model() {
        return id_model;
    }

    public void setId_model(int id_model) {
        this.id_model = id_model;
    }
}
