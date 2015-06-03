package geo.jadehs.de.myapplication.offlinedatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.content.*;

import java.io.FileNotFoundException;
import java.lang.Exception;

import geo.jadehs.de.myapplication.R;
import geo.jadehs.de.myapplication.activities.MainActivity;
import geo.jadehs.de.myapplication.offlinedatabasetables.TrackingTable;

import static geo.jadehs.de.myapplication.offlinedatabasetables.TrackingTable.SQL_CREATE;
import static geo.jadehs.de.myapplication.offlinedatabasetables.TrackingTable.SQL_DROP;

import geo.jadehs.de.myapplication.utilities.ActivityHelper;
import jsqlite.*;

/**
 * Created by Maik on 02.06.2015.
 */
public class SpatialiteDatabaseManager {

    private static jsqlite.Database database;

    private static final String TAG = "spatialite";

    private static final String DATENBANK_NAME = "spatialite.db";


    private static Object sLOCK = "";
    private String dbFile;


    private SpatialiteDatabaseManager(Context context) {


        try {
            dbFile = ActivityHelper.getDataBase(context,
                    DATENBANK_NAME);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        jsqlite.Database db = new jsqlite.Database();
        database.spatialite_create();


    }


    public static SpatialiteDatabaseManager getInstance(Context context) {
        if (database == null) {
            synchronized (sLOCK) {
                if (database == null) {


                }
                }
            }
        }
        return database;

    }


}
