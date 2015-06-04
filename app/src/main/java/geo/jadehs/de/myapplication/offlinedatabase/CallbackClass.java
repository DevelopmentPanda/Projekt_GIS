package geo.jadehs.de.myapplication.offlinedatabase;

import android.content.pm.LauncherApps;

/**
 * Created by Maik on 04.06.2015.
 */
public class CallbackClass implements jsqlite.Callback{


    @Override
    public void columns(String[] coldata) {
        
    }

    @Override
    public void types(String[] types) {

    }

    @Override
    public boolean newrow(String[] rowdata) {
        return false;
    }
}
