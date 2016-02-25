package com.example.arusyn.sensorman;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by arusyn on 2/21/2016.
 */

public class PlotView extends View {

    ArrayList<Float> points = new ArrayList<Float>(10);
    ArrayList<Float> avgs = new ArrayList<Float>(10);
    ArrayList<Float> stdDevs = new ArrayList<Float>(10);

    private int time = 0;
    private int maxValue = 10;

    private Paint gridLine;
    private Paint valLine;
    private Paint avgLine;
    private Paint stdDevLine;
    private Paint stdDevPoint;
    private Paint valPoint;
    private Paint avgPoint;
    private Paint text;

    public PlotView(Context context) {
        super(context);
        init();
    }

    public PlotView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PlotView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public PlotView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas c){
        super.onDraw(c);

        //increment time since onDraw gets called every second
        time++;

        //grid variables
        int buffer = 50;
        int gridHeight = getHeight()-buffer;
        int gridWidth = getWidth()-buffer;
        int cellHeight = gridHeight/10;
        int cellWidth = gridWidth/10;

        c.drawColor(Color.WHITE);


        if (time < 10){
            for(int i = 0; i <10; i++){
                int timeX = i * cellWidth + buffer;
                c.drawText(Integer.toString(i), timeX, gridHeight + 30, text); //draw time labels
            }
        } else {
            int lowestVal = time-10;
            for(int i = time-10; i <time; i++){
                int timeX = ((i-lowestVal) * cellWidth + buffer);
                c.drawText(Integer.toString(i), timeX, gridHeight + 30, text); //draw time labels
            }
        }

        for(int i = 0; i<= 10; i++){
            //draw grid lines
            c.drawLine(buffer, i * cellHeight, getWidth(), (i * cellHeight), gridLine);   //draw horizontal lines
            c.drawLine((i * cellWidth) + buffer, 0, (i * cellWidth) + buffer, getHeight() - buffer, gridLine);  //draw vertical lines

            //draw labels
            c.drawText(Integer.toString(i*(maxValue/10)), 0, (10-i)*cellHeight+buffer-30, text);  //draw vertical labels for all except '0'

            //draw points
            for (int j = 0; j< points.size(); j++){
                float calculatedX = (j*cellWidth + buffer);
                float lastCalculatedX = (j-1)*cellWidth + buffer;
                float calculatedY = gridHeight - points.get(j)*(gridHeight / (maxValue+10));
                float calculatedYmean = gridHeight - avgs.get(j) * (gridHeight / (maxValue+10));
                float calculatedYvar = gridHeight - stdDevs.get(j)*(gridHeight / (maxValue+10));
                float lastCalculatedY;
                float lastCalculatedYmean;
                float lastCalculatedYvar;

                System.out.println("x: " + calculatedX + " and y: " + calculatedY);
                c.drawCircle(calculatedX, calculatedY, 5, valPoint);
                c.drawCircle(calculatedX, calculatedYmean, 5, avgPoint);
                c.drawCircle(calculatedX, calculatedYvar, 5,stdDevPoint);


                if(j!=0){ //draw lines between the points
                    lastCalculatedY = gridHeight - points.get(j-1)*(gridHeight / (maxValue+10));
                    lastCalculatedYmean = gridHeight - avgs.get(j-1) * (gridHeight / (maxValue+10));
                    lastCalculatedYvar = gridHeight - stdDevs.get(j-1)*(gridHeight / (maxValue+10));
                    c.drawLine(lastCalculatedX, lastCalculatedY, calculatedX, calculatedY, valLine);
                    c.drawLine(lastCalculatedX, lastCalculatedYmean, calculatedX, calculatedYmean, avgLine);
                    c.drawLine(lastCalculatedX, lastCalculatedYvar, calculatedX, calculatedYvar, stdDevLine);
                }
            }
        }
    }

    public void addPoint(float point){
        if(point > maxValue){
            this.maxValue = (int) point;
        }
        points.add(point);
        avgs.add(getMean(points));
        stdDevs.add(getStdDevs(points));
        if(points.size() > 10){
            points.remove(0);
        }
        if(avgs.size() > 10){
            avgs.remove(0);
        }
        if(stdDevs.size() > 10){
            stdDevs.remove(0);
        }
    }

    public float getMean(ArrayList<Float> list){
        float sum = 0;
        for(Float f : list)
            sum += f;
        return sum/list.size();
    }

    public float getStdDevs(ArrayList<Float> list){
        float mean = getMean(list);
        float temp = 0;
        for(Float f : list)
            temp += (mean-f)*(mean-f);
        return (float) Math.sqrt(temp/list.size());
    }

    private void init(){
        gridLine = new Paint();
        gridLine.setColor(Color.GRAY);
        gridLine.setStrokeWidth(1);

        valLine = new Paint();
        valLine.setColor(Color.BLACK);
        valLine.setStyle(Paint.Style.FILL);
        valLine.setStrokeWidth(6);

        valPoint = new Paint();
        valPoint.setColor(Color.BLACK);
        valPoint.setStyle(Paint.Style.FILL_AND_STROKE);

        avgLine = new Paint();
        avgLine.setColor(Color.BLUE);
        avgLine.setStyle(Paint.Style.FILL_AND_STROKE);
        avgLine.setStrokeWidth(6);

        avgPoint = new Paint();
        avgPoint.setColor(Color.BLUE);
        avgPoint.setStyle(Paint.Style.FILL_AND_STROKE);

        stdDevLine = new Paint();
        stdDevLine.setColor(Color.RED);
        stdDevLine.setStyle(Paint.Style.STROKE);
        stdDevLine.setStrokeWidth(6);

        stdDevPoint = new Paint();
        stdDevPoint.setColor(Color.RED);
        stdDevPoint.setStyle(Paint.Style.STROKE);

        text = new Paint();
        text.setColor(Color.BLACK);
        text.setStyle(Paint.Style.FILL_AND_STROKE);
        text.setTextSize(20);
    }

}