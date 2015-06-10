package com.example.aleksandr.mycars_zloj.entity;

import com.example.aleksandr.mycars_zloj.staticVariables.StaticVariables;

/**
 * Created by Aleksandr on 01.06.2015.
 */
public class Model implements StaticVariables{
    private int id;
    private String nameModel;
    private int id_brend;
    private int yearsFrom;
    private int yearsTo;

    public Model() {
    }


    public Model(int id, String nameModel, int id_brend) {
        this.id = id;
        this.nameModel = nameModel;
        this.id_brend = id_brend;
    }

    public Model(String nameModel, int id_brend) {
        this.nameModel = nameModel;
        this.id_brend = id_brend;
    }

    public Model(String s) {
        this.nameModel = s;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameModel() {
        return nameModel;
    }

    public void setNameModel(String nameModel) {
        this.nameModel = nameModel;
    }

    public int getId_brend() {
        return id_brend;
    }

    public void setId_brend(int id_brend) {
        this.id_brend = id_brend;
    }

    public int getYearsFrom() {
        return yearsFrom;
    }

    public void setYearsFrom(int yearsFrom) {
        this.yearsFrom = yearsFrom;
    }

    public int getYearsTo() {
        return yearsTo;
    }

    public void setYearsTo(int yearsTo) {
        this.yearsTo = yearsTo;
    }

    public Model(String nameModel, int id_brend, int yearsFrom, int yearsTo) {
        this.nameModel = nameModel;
        this.id_brend = id_brend;
        this.yearsFrom = yearsFrom;
        this.yearsTo = yearsTo;
    }
}
