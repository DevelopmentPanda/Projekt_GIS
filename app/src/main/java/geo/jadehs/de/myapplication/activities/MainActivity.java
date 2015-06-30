package geo.jadehs.de.myapplication.activities;

import android.app.Activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.view.View;


import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

import geo.jadehs.de.myapplication.R;
import geo.jadehs.de.myapplication.offlinedatabase.SpatialiteDatabase;
import geo.jadehs.de.myapplication.services.LocationService;
import geo.jadehs.de.myapplication.utilities.ActivityHelper;

import jsqlite.Callback;

import jsqlite.Stmt;
import jsqlite.TableResult;


import java.io.File;
import java.util.Arrays;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MainActivity extends Activity implements View.OnClickListener {
    @SuppressWarnings("unused")
    private static final String TAG = MainActivity.class.getName();
    private Switch mySwitch;
    private SpatialiteDatabase db;
    GoogleMap googleMap;

    // AB HIER TEST TEST TEST


    private LocationService mBoundService;

    /**
     * Flag indicating whether we have called bind on the service.
     */
    boolean mIsBound;

    protected ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with the service has been
            // established, giving us the service object we can use to
            // interact with the service.  Because we have bound to a explicit
            // service that we know is running in our own process, we can
            // cast its IBinder to a concrete class and directly access it.
            mBoundService = ((LocationService.LocalBinder) service).getService();

            // Tell the user about this for our demo.
            Toast.makeText(MainActivity.this, R.string.local_service_connected,
                    Toast.LENGTH_SHORT).show();
        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            // Because it is running in our same process, we should never
            // see this happen.
            mBoundService = null;
            Toast.makeText(MainActivity.this, R.string.local_service_disconnected,
                    Toast.LENGTH_SHORT).show();
        }
    };

    void doBindService() {
        // Establish a connection with the service.  We use an explicit
        // class name because we want a specific service implementation that
        // we know will be running in our own process (and thus won't be
        // supporting component replacement by other applications).
        bindService(new Intent(this,
                LocationService.class), mConnection, Context.BIND_AUTO_CREATE);

        mIsBound = true;
    }

    void doUnbindService() {
        if (mIsBound) {
            // Detach our existing connection.
            unbindService(mConnection);
            mIsBound = false;
        }
    }

    // BIS HIER TEST TEST TEST


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mySwitch = (Switch) findViewById(R.id.swtForCreatePoints);
        mySwitch.setChecked(false);
        mySwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    System.out.println("Switch is currently ON");
                    doBindService();


                } else {
                    System.out.println("Switch is currently OFF");
                    doUnbindService();
                }

            }
        });
        createMapView();
        addMarker();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        doUnbindService();
    }

    @Override
    public void onClick(View v) {


        db = SpatialiteDatabase.getInstance(getApplicationContext());

        /*
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
        */
        if (v.getId() == R.id.btnForTracking) {
            System.out.println("Track geklickt");
        }
        if (v.getId() == R.id.swtForCreatePoints) {
            System.out.println("Switch geschaltet");
        }
    }

    /**
     * Initialises the mapview
     */
    private void createMapView() {
        /**
         * Catch the null pointer exception that
         * may be thrown when initialising the map
         */
        try {
            if (null == googleMap) {
                googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                        R.id.mapView)).getMap();

                /**
                 * If the map is still null after attempted initialisation,
                 * show an error to the user
                 */
                if (null == googleMap) {
                    Toast.makeText(getApplicationContext(),
                            "Error creating map", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (NullPointerException exception) {
            Log.e("mapApp", exception.toString());
        }
    }

    /**
     * Adds a marker to the map
     */
    private void addMarker() {

        /** Make sure that the map has been initialised **/
        if (null != googleMap) {
            googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(0, 0))
                            .title("Marker")
                            .draggable(true)
            );
        }
    }

    public void meineMethode() {

        Toast.makeText(this, "Meine Methode gestartet", Toast.LENGTH_SHORT).show();
        try {
            String dbFile;
            try {
                // Find the database
                //dbFile = ActivityHelper.getDataBase(this,
                //        getString(R.string.test_db));
                /**
                 * TODO - wenn String = unknown error in open...
                 * wenn Activity helper FoleNot Found Exception
                 */
                Toast.makeText(this, "bin in try", Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                ActivityHelper.showAlert(this,
                        getString(R.string.error_locate_failed));
                throw e;
            }
            Toast.makeText(this, "vor dem open", Toast.LENGTH_LONG).show();
            // Open the database

            File dir = getFilesDir();

            File spatialDbFile = new File(dir, "test4242");

            if (spatialDbFile.exists()) {
                System.out.println("Datei exisitiert!");
            }

            System.out.println(spatialDbFile.getAbsolutePath());
            System.out.println(spatialDbFile.getAbsolutePath());
            jsqlite.Database db = new jsqlite.Database();

            db.open(spatialDbFile.getAbsolutePath(), jsqlite.Constants.SQLITE_OPEN_READWRITE | jsqlite.Constants.SQLITE_OPEN_CREATE);
            Toast.makeText(this, "nach open", Toast.LENGTH_LONG).show();
            db.spatialite_create();
            Toast.makeText(this, "DB erstellt und oder geoeffnet", Toast.LENGTH_LONG).show();
            queryVersions(db);


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

    public void createSpatialiteDatabase() {


    }
}






