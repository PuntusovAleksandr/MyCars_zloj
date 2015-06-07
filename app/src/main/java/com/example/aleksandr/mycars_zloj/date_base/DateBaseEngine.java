package com.example.aleksandr.mycars_zloj.date_base;

import com.example.aleksandr.mycars_zloj.entity.Brend;
import com.example.aleksandr.mycars_zloj.entity.Engine;

import java.util.List;

/**
 * Created by Aleksandr on 07.06.2015.
 */
public interface DateBaseEngine {
    public void addEngine(Engine engine, int id_model);
    public List<Engine> getListEngines(int id);
    public List<Engine> getAllEngine();
    public void renameEngine(String name1, String name2);
    public void deleteEngine(int id);
    public void deleteAllEngines();
    public int getIdEngine(String name);
}
