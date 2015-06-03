package geo.jadehs.de.myapplication.offlinedatabase;

import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

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

    private SpatialiteDatabaseManager spatialiteDB;


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
        this.context = context;


        dbFields = databaseFields;

        OpenHelper openHelper = new OpenHelper(this);

        this.db = openHelper.getWritableDatabase();

        dbFieldIDs = new int[dbFields.length];

        ih = new DatabaseUtils.InsertHelper(db, TABLE_NAME);


    }


    public class OpenHelper extends SQLiteOpenHelper

    {


        private OfflineDatabaseManager(Context context) {

            super(sqlite.context, DATENBANK_NAME, null, DATENBANK_VERSION);


        }


        public static GeometrySpeicher getInstance(Context context) {
            if (sINSTANCE == null) {
                synchronized (sLOCK) {
                    if (sINSTANCE == null) {
                        sINSTANCE = new GeometrySpeicher(context.getApplicationContext());
                    }
                }
            }
            return sINSTANCE;


        }


        @Override

        public void onCreate(SQLiteDatabase db) {

            String query = SQL_CREATE;

            db.execSQL(query);

        }

        @Override

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            Log.w("Example", "Upgrading database, this will drop tables and recreate.");

            db.execSQL(SQL_DROP);

            onCreate(db);

        }

    }
}
