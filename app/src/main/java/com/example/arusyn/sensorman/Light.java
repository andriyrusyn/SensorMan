package com.example.arusyn.sensorman;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class Light extends AppCompatActivity implements SensorEventListener {
    private SensorManager sm;
    private List<Sensor> l;
    private PlotView pv;
    private Sensor light;
    private long lastTimestamp;
    private float lastValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        light = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
        pv = (PlotView) findViewById(R.id.lightPlotView);

        sm.registerListener(this, light, 100000000);
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
        sm.unregisterListener(this, light);
        startActivity(intent);
    }

    private void sendValue(){
        pv = (PlotView) findViewById(R.id.lightPlotView);
        pv.addPoint(lastValue);
        pv.invalidate();

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
            lastValue = event.values[0];
            lastTimestamp = event.timestamp;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
