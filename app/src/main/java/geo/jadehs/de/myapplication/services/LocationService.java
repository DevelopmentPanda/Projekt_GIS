package geo.jadehs.de.myapplication.services;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Maik on 30.06.2015.
 */
public class LocationService extends Service{

    private NotificationManager mNM;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
