package geo.jadehs.de.myapplication.offlinedatabase;

import android.content.Context;

import android.database.sqlite.SQLiteStatement;
import android.widget.Toast;


import java.io.FileNotFoundException;

import geo.jadehs.de.myapplication.offlinedatabasetables.TrackingTable;
import geo.jadehs.de.myapplication.utilities.ActivityHelper;
import jsqlite.*;
import jsqlite.Exception;

import com.vividsolutions.jts.geom.*;


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


    public static final String DATENBANK_NAME = "spatialite.sqlite";

    // Singleton Variablen
    public static SpatialiteDatabase sINSTANCE;
    private static Object sLOCK = "";

    private String dbFile;


    // weitere Variablen
    private Context context;

    private jsqlite.Database db;

    private SQLiteStatement insertStmt;
    private Callback cb;

    private SpatialiteDatabase(Context con) {
        context = con;

        try {
            db = new jsqlite.Database();

            dbFile = ActivityHelper.getDataBase(con,
                    DATENBANK_NAME);
            cb = new CallbackClass();


            // eventuell folgenden Zugriff noch in einer Asynchronen Methode auslagern, falls Performanceprobleme auftauchen!
            db.open(dbFile, jsqlite.Constants.SQLITE_OPEN_READWRITE | Constants.SQLITE_OPEN_CREATE);

            db.exec("select initspatialmetadata();", cb);

            System.out.println("DB Version:" + db.dbversion());


            createDefaultTable();
            createSpatialiteColumns();
            // insertPointSMT("Track1", "Speed", "zeit", 2, 3);
            getSomeShit();


        } catch (FileNotFoundException | jsqlite.Exception e) {
            e.printStackTrace();
        }


    }

    public static SpatialiteDatabase getInstance(Context context) {
        if (sINSTANCE == null) {
            synchronized (sLOCK) {
                if (sINSTANCE == null) {
                    sINSTANCE = new SpatialiteDatabase(context);
                }
            }
        }
        return sINSTANCE;


    }

    // Testmethode zum Ausgeben von vorhandenen Datenbankeintraegen
    private void getSomeShit() throws Exception {

        Stmt stmt = null;
        try {
            stmt = db
                    .prepare("SELECT * FROM trackingtable;");

            while (stmt.step()) {
                String tableName = stmt.column_string(0);
                String type = stmt.column_string(1);
                String srid = stmt.column_string(2);
                Geometry mygeometry;

                System.out.println(tableName + " ....." + type + "......" + srid);

            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private void createDefaultTable() {


        System.out.println(SQL_CREATE);
        try {
            db.exec(TrackingTable.SQL_CREATE, cb);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createSpatialiteColumns() {
        System.out.println(CREATE_SPATIALITE_COLUMN);

        db.spatialite_create();

        try {
            db.exec(TrackingTable.CREATE_SPATIALITE_COLUMN, cb);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void insertPointSMT(String trackname, String speed, String timestamp, double lat, double lon) {


        try {
         //   System.out.println(STMT_INSERT_TRACK + "(" + "'" + trackname + "'" + "," + "'" + speed + "'" + "," + "'" + timestamp + "'," + "GeomFromText('POINT(" + lat + " " + lon + ")', " + 4258 + "));");

            db.exec(STMT_INSERT_TRACK + "(" + "'" + trackname + "'" + "," + "'" + speed + "'" + "," + "'" + timestamp + "'," + "GeomFromText('POINT(" + lat + " " + lon + ")', " + 4258 + "));", cb);
            Toast.makeText(context, "Punkt in DB gespeichert!!!" +
                    STMT_INSERT_TRACK + "(" + "'" + trackname + "'" + "," + "'" + speed + "'" + "," + "'" + timestamp + "'," + "GeomFromText('POINT(" + lat + " " + lon + ")', " + 4258 + "));", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ;


}

