package geo.jadehs.de.myapplication.sensors;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import geo.jadehs.de.myapplication.R;

/**
 * Created by BetaXLC on 04.06.2015.
 *
 * Quelle : http://www.android-hilfe.de/android-app-entwicklung/529516-wert-aus-beschleunigungssensor-auslesen.html
 */
public class accelerationSensorActivityTest extends Activity implements SensorEventListener{

    Sensor accelerometer;
    SensorManager sm;
    float [] mAcceleration = new float[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this, accelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);

        // Das hier geht noch nicht mit der Orientation
    //
      //  sm.getOrientation(sm.getRotationMatrix(),mAcceleration);


    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        System.arraycopy(event.values, 0, mAcceleration, 0, 3);
        int x = (int) getAccelerationOnXAxis();
        int y = (int) getAccelerationOnYAxis();
        int z = (int) getAccelerationOnZAxis();

    }


    public float getAccelerationOnXAxis(){
        return mAcceleration[0];
    }

    public float getAccelerationOnYAxis(){
        return mAcceleration[1];
    }

    public float getAccelerationOnZAxis() {
        return mAcceleration[2];
    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
