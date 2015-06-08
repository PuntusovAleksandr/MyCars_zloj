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
import com.example.aleksandr.mycars_zloj.date_base.impl.BrendDataBaseImpl;
import com.example.aleksandr.mycars_zloj.entity.Brend;
import com.example.aleksandr.mycars_zloj.staticVariables.StaticVariables;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aleksandr on 30.05.2015.
 */
public class BrendActivity extends Fragment implements StaticVariables {

    public static final String TAG ="BrendActivity";

    private Button btEditBrend;
    public static Spinner spBrend;

    private DateBaseBrend dbBrend;
    private ArrayAdapter<String> adapter;
    private List<Brend> masBrend;
    private View viewBrend;

    private ModelActivity modelActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        viewBrend = View.inflate(getActivity(), R.layout.brend, null);
        spBrend = (Spinner) viewBrend.findViewById(R.id.spBrend);
        btEditBrend = (Button) viewBrend.findViewById(R.id.btEditBrend);
        showBrend();

        return viewBrend;
    }

    public void showBrend() {
        dbBrend = new BrendDataBaseImpl(getActivity(), BREND_TABLE, null,  VERSION_TABLE);
        Log.d(StaticVariables.LOG_TAG, "--- testing ---");

        masBrend =dbBrend.getAllBrend();
        Log.d(StaticVariables.LOG_TAG, "--- testing  2 ---");

        if (masBrend == null) {
            masBrend = new ArrayList<>();
        }

        if (masBrend.size() <= 0) {
            dbBrend.addBrend(new Brend("BMW"));
            dbBrend.addBrend(new Brend("Ford"));
            dbBrend.addBrend(new Brend("Opel"));
            dbBrend.addBrend(new Brend("Mersedes"));
            dbBrend.addBrend(new Brend("Porshe"));

            masBrend = dbBrend.getAllBrend();
        }
        Log.d(StaticVariables.LOG_TAG, "--- Brends ---");

        List<String> masString = new ArrayList<>();
        for (Brend brend : masBrend) {
            masString.add(brend.getNameBrend().toString());
        }
        Log.d(StaticVariables.LOG_TAG, "---end testing ---");

        adapter = new ArrayAdapter<String>(viewBrend.getContext(), android.R.layout.simple_spinner_item, masString);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.notifyDataSetChanged();
        spBrend.setAdapter(adapter);
    }

    public int getBrendSelectedSpinner() {
        return dbBrend.getIdBrend(spBrend.getSelectedItem().toString());
    }
}
