package com.example.aleksandr.mycars_zloj;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.aleksandr.mycars_zloj.date_base.DateBaseBrend;
import com.example.aleksandr.mycars_zloj.date_base.DateBaseEngine;
import com.example.aleksandr.mycars_zloj.date_base.DateBaseModel;
import com.example.aleksandr.mycars_zloj.date_base.impl.BrendDataBaseImpl;
import com.example.aleksandr.mycars_zloj.date_base.impl.EngineDateBaseImpl;
import com.example.aleksandr.mycars_zloj.date_base.impl.ModelDataBaseImpl;
import com.example.aleksandr.mycars_zloj.entity.Brend;
import com.example.aleksandr.mycars_zloj.entity.Engine;
import com.example.aleksandr.mycars_zloj.entity.Model;
import com.example.aleksandr.mycars_zloj.fragments.BrendActivity;
import com.example.aleksandr.mycars_zloj.fragments.ModelActivity;
import com.example.aleksandr.mycars_zloj.staticVariables.StaticVariables;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements StaticVariables {

    private FragmentManager manager;
    private FragmentTransaction transaction;

    private BrendActivity brendActivity;
    private ModelActivity modelActivity;

    private Button btAdd;
    private Button btRemove;
    private Button btRename;
    private Button btBack;

    private ListView listView;
    private EditText editText;

    private DateBaseBrend dbBrend;
    private DateBaseModel dbModel;
    private DateBaseEngine dbEngine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btAdd = (Button) findViewById(R.id.btAdd);
        btRemove = (Button) findViewById(R.id.btRemove);
        btRename = (Button) findViewById(R.id.btRename);
        btBack = (Button) findViewById(R.id.btBack);

        listView = (ListView) findViewById(R.id.listView);
        editText = (EditText) findViewById(R.id.editText);

        manager = getSupportFragmentManager();
        brendActivity = new BrendActivity();
        modelActivity = new ModelActivity();
        transaction = manager.beginTransaction();
        transaction.add(R.id.ll_brend, brendActivity, BrendActivity.TAG);
        transaction.add(R.id.ll_model, modelActivity, ModelActivity.TAG);
        transaction.commit();


    }

    public void doTransaction(View view) {
        dbBrend = new BrendDataBaseImpl(getApplicationContext(), BREND_TABLE, null, VERSION_TABLE);
        dbModel = new ModelDataBaseImpl(getApplicationContext(), MODEL_TABLE, null, VERSION_TABLE);
        dbEngine = new EngineDateBaseImpl(getApplicationContext(), ENGINE_TABLE, null, VERSION_TABLE);

        transaction = manager.beginTransaction();
        Fragment modelFragmentByTag = manager.findFragmentByTag(ModelActivity.TAG);
        Fragment brendFragmentByTag = manager.findFragmentByTag(BrendActivity.TAG);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        switch (view.getId()) {
            case R.id.btEditBrend:
                if (modelFragmentByTag != null) {
                    transaction.remove(modelActivity);
                }
                break;

            case R.id.btEditModel:
                if (brendFragmentByTag != null) {
                    transaction.remove(brendActivity);
                }
                break;

            case R.id.btBack:
                if (!brendFragmentByTag.isVisible()) {
                    transaction.add(R.id.ll_brend, brendActivity, BrendActivity.TAG);
                }
                if (!(modelFragmentByTag.isVisible())) {
                    transaction.add(R.id.ll_model, modelActivity, ModelActivity.TAG);
                }
                break;

            case R.id.btAdd:
                if (brendFragmentByTag.isVisible() &&
                        !(modelFragmentByTag.isVisible())) {
                    alert.setTitle("Are you sure add "+editText.getText().toString()+"?");
                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dbBrend.addBrend(new Brend(editText.getText().toString()));
                            editText.setText("");
                            brendActivity.showBrend();
                            modelActivity.showModel();
                            showEngine();
                        }
                    });
                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alert.show();
                }
                if (!(brendFragmentByTag.isVisible()) &&
                        modelFragmentByTag.isVisible()) {
                    alert.setTitle("Are you sure add "+editText.getText().toString()+"?");
                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int id_brend = brendActivity.getBrendSelectedSpinner();
                            dbModel.addModel(new Model(editText.getText().toString(), id_brend), id_brend);
                            editText.setText("");
                            modelActivity.showModel();
                            showEngine();
                        }
                    });
                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alert.show();
                }
                if (brendFragmentByTag.isVisible() &&
                        modelFragmentByTag.isVisible()) {
                    alert.setTitle("Are you sure add "+editText.getText().toString()+"?");
                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int id_model = modelActivity.getModelSelectedSpinner();
                            dbEngine.addEngine(new Engine(editText.getText().toString(), id_model), id_model);
                            editText.setText("");
                            showEngine();
                        }
                    });
                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alert.show();
                }
                break;

            case R.id.btRemove:
                if (editText.getText() != null ||
                        !(editText.getText().toString().equals(""))) {
                    if (brendFragmentByTag.isVisible() &&
                            !(modelFragmentByTag.isVisible())) {
                        alert.setTitle("Are you sure delite " + editText.getText().toString() + "?");
                        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int idBr = brendActivity.getBrendSelectedSpinner();
                                dbBrend.deleteBrend(idBr);
                                editText.setText("");
                                brendActivity.showBrend();
                                modelActivity.showModel();
                                showEngine();
                            }
                        });
                        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        alert.show();
                    }
                    if (!(brendFragmentByTag.isVisible()) &&
                            modelFragmentByTag.isVisible()) {
                        alert.setTitle("Are you sure delite " + ModelActivity.spModel.getSelectedItem().toString() + "?");
                        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int idModel = modelActivity.getModelSelectedSpinner();
                                dbModel.deleteModel(idModel);
                                editText.setText("");
                                modelActivity.showModel();
                                showEngine();
                            }
                        });
                        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        alert.show();
                    }
                    if (brendFragmentByTag.isVisible() &&
                            modelFragmentByTag.isVisible()) {
                        alert.setTitle("Are you sure delite " + editText.getText().toString() + "?");
                        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int idEng = dbEngine.getIdEngine(listView.getSelectedItem().toString());
                                dbEngine.deleteEngine(idEng);
                                showEngine();
                            }
                        });
                        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        alert.show();
                    }
                } else {
                    Toast.makeText(this, "Null place", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btRename:
                if (editText.getText() != null ||
                        !(editText.getText().toString().equals(""))) {
                    if (brendFragmentByTag.isVisible() &&
                            !(modelFragmentByTag.isVisible())) {
                        alert.setTitle("Are you sure delite " + editText.getText().toString() + "?");
                        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String name1 = BrendActivity.spBrend.getSelectedItem().toString();
                                String name2 = editText.getText().toString();
                                dbBrend.renameBrend(name1, name2);
                                editText.setText("");
                                brendActivity.showBrend();
                                modelActivity.showModel();
                                showEngine();
                            }
                        });
                        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        alert.show();
                    }
                    if (!(brendFragmentByTag.isVisible()) &&
                            modelFragmentByTag.isVisible()) {
                        alert.setTitle("Are you sure delite " + editText.getText().toString() + "?");
                        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String name1 = ModelActivity.spModel.getSelectedItem().toString();
                                String name2 = editText.getText().toString();
                                dbModel.renameModel(name1, name2);
                                editText.setText("");
                                modelActivity.showModel();
                                showEngine();
                            }
                        });
                        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        alert.show();
                    }
                    if (brendFragmentByTag.isVisible() &&
                            modelFragmentByTag.isVisible()) {
                        alert.setTitle("Are you sure delite " + editText.getText().toString() + "?");
                        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String name1 = listView.getSelectedItem().toString();
                                String name2 = editText.getText().toString();
                                dbEngine.renameEngine(name1, name2);
                                editText.setText("");
                                showEngine();
                            }
                        });
                        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        alert.show();
                    }
                } else {
                    Toast.makeText(this, "Null place", Toast.LENGTH_SHORT).show();
                }

                break;
        }

        transaction.addToBackStack(null);
        transaction.commit();

    }

    private void showEngine() {
        dbEngine = new EngineDateBaseImpl(this, ENGINE_TABLE, null,  VERSION_TABLE);
        List<Engine> masEngines = dbEngine.getAllEngine();
        Log.d(StaticVariables.LOG_TAG, "--- testing Engine 1 ---");

        if (masEngines == null) {
            masEngines = new ArrayList<>();
        }
        if (masEngines.size() <= 0) {
            dbEngine.addEngine(new Engine("1.3", 1), 1);
            dbEngine.addEngine(new Engine("1.5", 1), 1);
            dbEngine.addEngine(new Engine("1.7", 2), 2);
            dbEngine.addEngine(new Engine("1.3 i", 4), 4);
            dbEngine.addEngine(new Engine("1.5 i", 5), 5);
            dbEngine.addEngine(new Engine("1.7 i", 3), 3);
            dbEngine.addEngine(new Engine("1.3", 6),6);
            dbEngine.addEngine(new Engine("1.5", 9), 9);
            dbEngine.addEngine(new Engine("1.7", 8), 8);
            dbEngine.addEngine(new Engine("1.3 i", 7), 7);
            dbEngine.addEngine(new Engine("1.5 i", 9), 9);
            dbEngine.addEngine(new Engine("1.7 i", 6), 6);

        }

        dbModel = new ModelDataBaseImpl(getApplicationContext(), MODEL_TABLE, null, VERSION_TABLE);

        boolean re = true;
        if (ModelActivity.spModel.getSelectedItem().toString().length() > 0) {
            String name = ModelActivity.spModel.getSelectedItem().toString();
            int idModel = dbModel.getIdModel(name);
            if (idModel <= 0) {
                masEngines.add(new Engine(""));
            }
            masEngines = dbEngine.getListEngines(idModel);
        } else masEngines = dbEngine.getAllEngine();

        Log.d(StaticVariables.LOG_TAG, "--- engine size is  " +masEngines.size() +"---");

        List<String> masString = new ArrayList<>();

        for (Engine engine : masEngines) {
                masString.add(engine.getVolume());
        }
        Log.d(StaticVariables.LOG_TAG, "---end testing Model ---");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, masString);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BrendActivity.spBrend.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                modelActivity.showModel();
                showEngine();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ModelActivity.spModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                showEngine();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        modelActivity.showModel();
        showEngine();
    }

}
