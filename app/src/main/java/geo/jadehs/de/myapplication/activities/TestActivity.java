package geo.jadehs.de.myapplication.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.util.Arrays;

import geo.jadehs.de.myapplication.R;
import geo.jadehs.de.myapplication.utilities.ActivityHelper;
import jsqlite.Callback;
import jsqlite.Constants;
import jsqlite.Stmt;



/**
 * Created by Maik on 13.05.2015.
 */
public class TestActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "TestActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button1) {
            runTest();
        }
    }

    private void runTest() {
        try {

            // Reset TextView to failed
            TextView view = (TextView) findViewById(R.id.txt_result);
            view.setText("Result: Failed");

            String dbFile;
            try {
                // Find the database
                dbFile = ActivityHelper.getDataBase(this,
                        getString(R.string.test_db));
            } catch (FileNotFoundException e) {
                // Database was not found alert the user and stop the test
                ActivityHelper.showAlert(this,
                        getString(R.string.error_locate_failed));
                throw e;
            }

            // Open the database
            jsqlite.Database db = new jsqlite.Database();
            db.open(dbFile.toString(), Constants.SQLITE_OPEN_CREATE);

            // Callback used to display query results in Android LogCat
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

            // Test prepare statements
            String query = "SELECT name, peoples, AsText(Geometry) from Towns where peoples > 350000";
            Stmt st = db.prepare(query);
            st.step();
            st.close();

            // Test various queries
            db.exec("select Distance(PointFromText('point(-77.35368 39.04106)', 4326), PointFromText('point(-77.35581 39.01725)', 4326));",
                    cb);
            db.exec("SELECT name, peoples, AsText(Geometry), GeometryType(Geometry), NumPoints(Geometry), SRID(Geometry), IsValid(Geometry) from Towns where peoples > 350000;",
                    cb);
            db.exec("SELECT Distance( Transform(MakePoint(4.430174797, 51.01047063, 4326), 32631), Transform(MakePoint(4.43001276, 51.01041585, 4326),32631));",
                    cb);

            // Close the database
            db.close();

            // If we got here everything "worked"
            view.setText("Result: Passed");
        } catch (jsqlite.Exception e) {
            Log.e(TAG, e.getMessage());
        } catch (FileNotFoundException e) {
            Log.e(TAG, e.getMessage());
        }
    }
}