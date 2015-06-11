package geo.jadehs.de.myapplication.offlinedatabase;

import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.io.FileNotFoundException;
import java.util.Arrays;

import geo.jadehs.de.myapplication.offlinedatabasetables.TrackingTable;
import geo.jadehs.de.myapplication.utilities.ActivityHelper;
import jsqlite.*;
import jsqlite.Exception;

import static geo.jadehs.de.myapplication.offlinedatabasetables.TrackingTable.*;

/**
 * Created by Maik on 28.05.2015.
 */

/**
 * JSQLlite
 * <p/>
 * The purpose of this class is to simplify SQLite database commands even further and to create a
 * <p/>
 * flexible and reusable class that can be put into any new project and have a database up and
 * <p/>
 * running extremely quickly.
 *
 * @author Lemoncog - https://lemonbloggywog.wordpress.com/
 */

public class SpatialiteDatabase {


    private static final String TAG = "Spatialite";


    public static final String DATENBANK_NAME = "spatialite.db";

    // Singleton Variablen
    public static SpatialiteDatabase sINSTANCE;
    private static Object sLOCK = "";

    private String dbFile;


    // weitere Variablen
    private Context context;

    private jsqlite.Database db;

    private SQLiteStatement insertStmt;
    private Callback cb;

    private SpatialiteDatabase(Context contex) {


        try {
            dbFile = ActivityHelper.getDataBase(context,
                    DATENBANK_NAME);
            jsqlite.Database db = new jsqlite.Database();
            cb = new CallbackClass();


            // eventuell folgenden Zugriff noch in einer Asynchronen Methode auslagern, falls Performanceprobleme auftauchen!
            db.open(dbFile.toString(), jsqlite.Constants.SQLITE_OPEN_READWRITE | Constants.SQLITE_OPEN_CREATE);


        } catch (FileNotFoundException | jsqlite.Exception e) {
            e.printStackTrace();
        }


        // Woanders einfügen;
        // database.spatialite_create();


    }

    public static SpatialiteDatabase getInstance(Context context) {
        if (sINSTANCE == null) {
            synchronized (sLOCK) {
                if (sINSTANCE == null) {
                    sINSTANCE = new SpatialiteDatabase(context.getApplicationContext());
                }
            }
        }
        return sINSTANCE;


    }


    public void createDefaultTable() {


        try {
            db.exec(TrackingTable.SQL_CREATE, cb);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

