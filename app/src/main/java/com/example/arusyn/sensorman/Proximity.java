package com.example.arusyn.sensorman;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.List;

public class Proximity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sm;
    private List<Sensor> l;
    private PlotView pv;
    private Sensor prox;
    private long lastTimestamp;
    private float lastValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximity);
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        prox = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        int maxAccValue = (int) prox.getMaximumRange();
        pv = (PlotView) findViewById(R.id.proximityPlotView);
//        pv.setMaxValue(maxAccValue);

        sm.registerListener(this, prox, 100000000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sendValue();
                new Handler().postDelayed(this, 1000);
            }
        }, 1000);

    }

    public void clickBack(View v){
        Intent intent = new Intent(this, MainActivity.class);
        sm.unregisterListener(this, prox);
        startActivity(intent);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if((event.timestamp - lastTimestamp) > 1e9 && event.sensor.getType() == Sensor.TYPE_PROXIMITY){
//        System.out.println("hi from accelerometer, x: " + x + ", y:" + y + ", z:"+z);
//        System.out.println("about to pass the following point:" + point);
//            pv = (PlotView) findViewById(R.id.proximityPlotView);
//            pv.addPoint(event.values[0]);
//            pv.invalidate();
            lastValue = event.values[0];
            lastTimestamp = event.timestamp;
        }
    }

    private void sendValue(){
        pv = (PlotView) findViewById(R.id.proximityPlotView);
        pv.addPoint(lastValue);
        pv.invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        pv = (PlotView) findViewById(R.id.proximityPlotView);
        int maxValue = (int) sensor.getMaximumRange();
//        pv.setMaxValue(maxValue);
//        pv.invalidate();
    }
}
