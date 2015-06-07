package com.example.aleksandr.mycars_zloj.entity;

import com.example.aleksandr.mycars_zloj.staticVariables.StaticVariables;

/**
 * Created by Aleksandr on 01.06.2015.
 */
public class Brend implements StaticVariables{

    private int id;
    private String nameBrend;

    public Brend() {
    }

    public Brend(int id, String nameBrend) {
        this.id = id;
        this.nameBrend = nameBrend;
    }

    public Brend(String nameBrend) {
        this.nameBrend = nameBrend;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameBrend() {
        return nameBrend;
    }

    public void setNameBrend(String nameBrend) {
        this.nameBrend = nameBrend;
    }


}
