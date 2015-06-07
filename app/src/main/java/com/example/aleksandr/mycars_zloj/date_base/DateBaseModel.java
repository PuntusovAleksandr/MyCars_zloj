package com.example.aleksandr.mycars_zloj.date_base;

import com.example.aleksandr.mycars_zloj.entity.Brend;
import com.example.aleksandr.mycars_zloj.entity.Model;

import java.util.List;

/**
 * Created by Aleksandr on 07.06.2015.
 */
public interface DateBaseModel {
    public void addModel(Model model, int idBrrend);
    public List<Model> getListModels(int id);
    public List<Model> getAllModel();
    public void renameModel(String name1, String name2);
    public void deleteModel(int id);
    public void deleteAllModels();
    public int getIdModel(String name);
}
