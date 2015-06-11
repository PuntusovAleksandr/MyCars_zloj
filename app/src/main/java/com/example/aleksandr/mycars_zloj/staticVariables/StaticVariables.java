package com.example.aleksandr.mycars_zloj.staticVariables;

/**
 * Created by Aleksandr on 01.06.2015.
 */
public interface StaticVariables {
    public static final String BREND_TABLE ="brend";
    public static final String MODEL_TABLE="model";
    public static final String ENGINE_TABLE="engine";
    public static final String ID_COLUMN = "id";
    public static final String NAME_COLUMN = "name";
    public static final String VALUE_COLUMN = "value";
    public static final String ID_BREND_COLUMN = "id_brend";
    public static final String ID_MODEL_COLUMN = "id_model";

    public static final int VERSION_TABLE = 1;
    public static final String LOG_TAG = "myLogs";

    public static final String CREATE_BREND_TABLE = "CREATE TABLE "+ BREND_TABLE+
            "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            "name TEXT UNIQUE NOT NULL);";
     public static final String CREATE_MODEL_TABLE = "CREATE TABLE "+ MODEL_TABLE+
             "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
             "name TEXT NOT NULL, " +
             "id_brend INTEGER NOT NULL," +
             "yearsFrom INTEGER, "+
             "yearsTo INTEGER, "+
             " FOREIGN KEY(id_brend) REFERENCES brend(id) ON DELETE CASCADE);";
    public static final String CREATE_ENGINE_TABLE = "CREATE TABLE "+ENGINE_TABLE+
            "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            "value INTEGER NOT NULL, " +
            "id_model INTEGER NOT NULL," +
            " FOREIGN KEY(id_model) REFERENCES model(id) ON DELETE CASCADE);";

    public static final String COMAND_BREND = "BREND";
    public static final String COMAND_MODEL = "MODEL";
    public static final String COMAND_ENGINE = "ENGINE";


}
