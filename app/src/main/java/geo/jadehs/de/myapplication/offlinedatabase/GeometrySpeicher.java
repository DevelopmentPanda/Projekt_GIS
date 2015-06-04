package geo.jadehs.de.myapplication.offlinedatabase;

import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.io.FileNotFoundException;

import geo.jadehs.de.myapplication.utilities.ActivityHelper;

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

public class GeometrySpeicher {


    private static jsqlite.Database database;


    private String dbFile;

    public static final String DATENBANK_NAME = "spatialite.db";


    // weitere Variablen
    private Context context;

    private jsqlite.Database db;

    private SQLiteStatement insertStmt;

    String[] dbFields;

    int[] dbFieldIDs;


    /**
     * Constructer for JSQLlite to create the table (if not already created).
     * <p/>
     * <p/>
     * <p/>
     * <p/>
     * String[] TABLE_FIELDS = { "title" , "people" };
     */

    public GeometrySpeicher(Context contex) {


        try {
            dbFile = ActivityHelper.getDataBase(context,
                    DATENBANK_NAME);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        jsqlite.Database db = new jsqlite.Database();
        database.spatialite_create();


    }


}

