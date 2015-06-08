package com.example.aleksandr.mycars_zloj.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.aleksandr.mycars_zloj.R;
import com.example.aleksandr.mycars_zloj.date_base.DateBaseBrend;
import com.example.aleksandr.mycars_zloj.date_base.DateBaseModel;
import com.example.aleksandr.mycars_zloj.date_base.impl.BrendDataBaseImpl;
import com.example.aleksandr.mycars_zloj.date_base.impl.ModelDataBaseImpl;
import com.example.aleksandr.mycars_zloj.entity.Model;
import com.example.aleksandr.mycars_zloj.staticVariables.StaticVariables;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aleksandr on 30.05.2015.
 */
public class ModelActivity extends Fragment implements StaticVariables{

    public static final String TAG ="ModelActivity";

    public static Spinner spModel;
    private Button btEditModel;

    private DateBaseModel db;
    private ArrayAdapter<String> adapter;
    private View viewModel;
    private List<Model> masModel;

    private BrendActivity brendActivity;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        viewModel = View.inflate(getActivity(), R.layout.model, null);
        spModel = (Spinner) viewModel.findViewById(R.id.spModel);
        btEditModel = (Button) viewModel.findViewById(R.id.btEditModel);
        showModel();

        return viewModel;
    }

    public void showModel() {
        db = new ModelDataBaseImpl(getActivity(), MODEL_TABLE, null,  VERSION_TABLE);
        Log.d(StaticVariables.LOG_TAG, "--- testing MODELS ---");

        masModel = db.getAllModel();
        Log.d(LOG_TAG, "--- testing Model  2" +masModel.size() + " ---");

        if (masModel == null) {
            masModel = new ArrayList<>();
        }
        if (masModel.size() <= 0) {

            db.addModel(new Model("525i", 1), 1);
            db.addModel(new Model("725i", 1), 1);
            db.addModel(new Model("Cadet", 3), 3);
            db.addModel(new Model("300E", 4), 4);
            db.addModel(new Model("Caien", 4), 4);
            db.addModel(new Model("Focus", 2), 2);
            db.addModel(new Model("fiesta", 2), 2);
            db.addModel(new Model("Omega", 3), 3);
            db.addModel(new Model("Tuaro", 2), 2);
            db.addModel(new Model("310", 1), 1);
        }

        DateBaseBrend dbBrend = new BrendDataBaseImpl(getActivity(), BREND_TABLE, null, VERSION_TABLE);
        masModel = db.getListModels(dbBrend.getIdBrend(BrendActivity.spBrend.getSelectedItem().toString()));
        Log.d(LOG_TAG, "--- testing Model  2 sixe is " +masModel.size() + " ---");
        Log.d(LOG_TAG, "--- Models ---");

        List<String> masString = new ArrayList<>();
        for (Model model : masModel) {
                masString.add(model.getNameModel().toString());
        }
        Log.d(LOG_TAG, "---end testing Model ---");

        adapter = new ArrayAdapter<String>(viewModel.getContext(), android.R.layout.simple_spinner_item, masString);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.notifyDataSetChanged();
        spModel.setAdapter(adapter);
    }

    public int getModelSelectedSpinner() {
        return db.getIdModel(spModel.getSelectedItem().toString());
    }

}
