package geo.jadehs.de.myapplication.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.widget.Toast;

import geo.jadehs.de.myapplication.R;
import geo.jadehs.de.myapplication.utilities.ActivityHelper;
import geo.jadehs.de.myapplication.utilities.AssetHelper;
import jsqlite.Callback;
import jsqlite.Constants;
import jsqlite.Stmt;
import jsqlite.TableResult;

import java.sql.*;
import java.io.File;
import java.util.Arrays;


public class MainActivity extends Activity implements View.OnClickListener {
    @SuppressWarnings("unused")
    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        System.out.println("Olli was here");
        System.out.println("Ich auch 2!");
        System.out.println("Olli was here, nochmal");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_install_to_application) {
            try {
                AssetHelper.CopyAsset(this,
                        ActivityHelper.getPath(this, false),
                        getString(R.string.test_db));
            } catch (IOException e) {
                ActivityHelper.showAlert(this,
                        getString(R.string.error_copy_failed));
            }
        } else if (v.getId() == R.id.btn_install_to_external) {
            try {
                AssetHelper.CopyAsset(this, ActivityHelper.getPath(this, true),
                        getString(R.string.test_db));
            } catch (IOException e) {
                ActivityHelper.showAlert(this,
                        getString(R.string.error_copy_failed));
            }
        } else if (v.getId() == R.id.btn_run_tests) {
            Intent myIntent = new Intent(this, TestActivity.class);
            startActivity(myIntent);
        } else if (v.getId() == R.id.btn_browse_data) {
            Intent myIntent = new Intent(this, TableListActivity.class);
            startActivity(myIntent);
        } else if (v.getId() == R.id.btn_map) {
            //Intent myIntent = new Intent(this, MappingActivity.class);
            //startActivity(myIntent);
            meineMethode();
        }
    }

    public void meineMethode() {

        Toast.makeText(this, "Meine Methode gestartet", Toast.LENGTH_SHORT).show();
        try {
            String dbFile;
            try {
                // Find the database
                dbFile = ActivityHelper.getDataBase(this,
                        getString(R.string.test_db));
                /**
                 * TODO - wenn String = unknown error in open...
                 * wenn Activity helper FoleNot Found Exception
                 */
            } catch (Exception e) {
                ActivityHelper.showAlert(this,
                        getString(R.string.error_locate_failed));
                throw e;
            }

            // Open the database
            jsqlite.Database db = new jsqlite.Database();
            db.open(dbFile.toString(), jsqlite.Constants.SQLITE_OPEN_READWRITE | Constants.SQLITE_OPEN_CREATE);
            db.spatialite_create();
            Toast.makeText(this, "DB erstellt und oder geoeffnet", Toast.LENGTH_LONG).show();


            /**String test = "CREATE TABLE Testtabelle2 " +
             "(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
             " ORT           CHAR(50)    NOT NULL, " +
             " X            INTEGER     NOT NULL, " +
             " Y        INTEGER); ";
             */
            Callback cb = new Callback() {
                @Override
                public void columns(String[] coldata) {
                    Log.v(TAG, "Columns: " + Arrays.toString(coldata));


                }

                @Override
                public void types(String[] types) {
                    Log.v(TAG, "Types: " + Arrays.toString(types));


                }

                @Override
                public boolean newrow(String[] rowdata) {

                    Log.v(TAG, "Row: " + Arrays.toString(rowdata));

                    return false;

                }
            };

            // db.exec(test, cb);

            String insert = "INSERT INTO Testtabelle2 " +
                    "( ORT, X, Y) VALUES " +
                    "( 'Dorf', 3, 4);";

            String insert2 = "Select * FROM Testtabelle2;";

            db.exec(insert, cb);
            db.exec(insert2, cb);


            String[] test2 = {"ID", "ORT", "X", "Y"};

            TableResult ergebnis = db.get_table("Select * FROM Testtabelle2;");
            // cb.columns(test2);
            System.out.println(queryVersions(db));


        } catch (Exception e) {
            Toast.makeText(this, "Catch " + e, Toast.LENGTH_LONG).show();
        }
    }

    public String queryVersions(jsqlite.Database db) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("Check versions...\n");

        Stmt stmt01 = db.prepare("SELECT spatialite_version();");
        if (stmt01.step()) {
            sb.append("\t").append("SPATIALITE_VERSION: " + stmt01.column_string(0));
            sb.append("\n");
        }

        stmt01 = db.prepare("SELECT proj4_version();");
        if (stmt01.step()) {
            sb.append("\t").append("PROJ4_VERSION: " + stmt01.column_string(0));
            sb.append("\n");
        }

        stmt01 = db.prepare("SELECT geos_version();");
        if (stmt01.step()) {
            sb.append("\t").append("GEOS_VERSION: " + stmt01.column_string(0));
            sb.append("\n");
        }
        stmt01.close();

        sb.append("Done...\n");
        return sb.toString();
    }

}






