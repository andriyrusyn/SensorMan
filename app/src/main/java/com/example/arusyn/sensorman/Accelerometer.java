package com.example.arusyn.sensorman;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class Accelerometer extends AppCompatActivity implements SensorEventListener {
    private SensorManager sm;
    private List<Sensor> l;
    private PlotView pv;
    private Sensor acc;
    private long lastPrinted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        acc = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this, acc, 100000000);
    }

    public void clickBack(View v){
        Intent intent = new Intent(this, MainActivity.class);
        sm.unregisterListener(this, acc);
        startActivity(intent);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if((event.timestamp - lastPrinted) > 1e9 && event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            float point = (float) Math.sqrt(x*x + y*y + z*z);  // math
            pv = (PlotView) findViewById(R.id.accelerometerPlotView);
            pv.addPoint(point);
            pv.invalidate();

            lastPrinted = event.timestamp;
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        pv = (PlotView) findViewById(R.id.accelerometerPlotView);
        int maxValue = (int) sensor.getMaximumRange();
//        pv.setMaxValue(maxValue);
//        pv.invalidate();
    }
}
