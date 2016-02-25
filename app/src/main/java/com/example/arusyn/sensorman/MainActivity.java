package com.example.arusyn.sensorman;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SensorManager sm;
    private List<Sensor> l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sm = (SensorManager) getSystemService (Context.SENSOR_SERVICE);
        l = sm.getSensorList(Sensor.TYPE_ALL);
        Sensor acc = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if(acc != null){
            TextView tv = (TextView) this.findViewById(R.id.accelerometerTextView);
            tv.setText("accelerometer is present, max range is: " + acc.getMaximumRange() + " and resolution is: " + acc.getResolution());
        }

        Sensor prox = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        if(prox != null){
            TextView tv = (TextView) this.findViewById(R.id.proximityTextView);
            tv.setText("proximity sensor is present, max range is: " + prox.getMaximumRange() + " and resolution is: " + prox.getResolution());
        }
        Sensor press = sm.getDefaultSensor(Sensor.TYPE_PRESSURE);
        if(press != null){
            TextView tv = (TextView) this.findViewById(R.id.pressureTextView);
            tv.setText("pressure sensor is present, max range is: " + press.getMaximumRange() + " and resolution is: " + press.getResolution());
        }

        Sensor light = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
        if(light != null){
            TextView tv = (TextView) this.findViewById(R.id.lightTextView);
            tv.setText("light sensor is present, max range is: " + light.getMaximumRange() + " and resolution is: " + light.getResolution());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void clickAccelerometer(View v){
        Intent intent = new Intent(this, Accelerometer.class);
        startActivity(intent);
    }

    public void clickProximity(View v){
        Intent intent = new Intent(this, Proximity.class);
        startActivity(intent);
    }
    public void clickPressure(View v){
        Intent intent = new Intent(this, Pressure.class);
        startActivity(intent);
    }

    public void clickLight(View v){
        Intent intent = new Intent(this, Light.class);
        startActivity(intent);
    }
}
