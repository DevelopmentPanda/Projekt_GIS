package geo.jadehs.de.myapplication.offlinedatabase;

import android.content.pm.LauncherApps;
import android.util.Log;

import java.util.Arrays;

/**
 * Created by Maik on 04.06.2015.
 */
public class CallbackClass implements jsqlite.Callback {

    final String TAG = "CallbackClass";

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
        return false;
    }
}
