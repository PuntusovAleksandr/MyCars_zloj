package com.example.aleksandr.mycars_zloj.date_base;

import com.example.aleksandr.mycars_zloj.entity.Brend;

import java.util.List;
import java.util.Objects;

/**
 * Created by Aleksandr on 07.06.2015.
 */
public interface DateBaseBrend {



    public void addBrend(Brend brend);
    public List<Brend> getListBrends(int id);
    public List<Brend> getAllBrend();
    public void renameBrend(String name1, String name2);
    public void deleteBrend(int id);
    public void deleteAllBrends();
    public int getIdBrend(String name);

}
