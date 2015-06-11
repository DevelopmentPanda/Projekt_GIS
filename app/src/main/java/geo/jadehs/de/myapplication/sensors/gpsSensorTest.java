package geo.jadehs.de.myapplication.sensors;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import geo.jadehs.de.myapplication.R;

/**
 * Created by Olver Frink on 04.06.2015.
 *
 *
 * Permissions
 * Damit der Code auch genutzt werden kann, müssen noch Permissions im Manifest-Files gesetzt werden.
 *
 * Für die Positionierung per GPS:
 *
 * <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"></uses-permission>
 *
 * Quelle : http://de.wikibooks.org/wiki/Googles_Android/_Positionsbestimmung
 */
public class gpsSensorTest {

    /**
     * Context : Übergebener Context aus dem Konstruktor
     */

    Context context;

    /**
     * Locationmanager
     */

    double latitude = 0;
    double longitude = 0;

    LocationManager mlocManager;
    LocationListener mlocListener;

    /**
     * Konstruktor
     *
     * @param c - Der Context
     */

    gpsSensorTest(Context c)
    {
        context = c;
        getGPSLocation(); // Hier oder intervallweise aufrufen?
    }

    public String getGPSLocation()
    {
        mlocManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        mlocListener = new MyLocationListener();


        mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 1000, 0, mlocListener);


        return null;
    }


    /* Class My Location Listener */

    public class MyLocationListener implements LocationListener

    {
        // Wird Aufgerufen, wenn eine neue Position durch den LocationProvider bestimmt wurde

        @Override
        public void onLocationChanged(Location location) {

            latitude = location.getLatitude();
            longitude = location.getLongitude();

        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
        @Override
        public void onProviderEnabled(String provider) {

        }
        @Override
        public void onProviderDisabled(String provider) {

        }

    }/* End of Class MyLocationListener */
}
