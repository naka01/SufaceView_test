package com.example.so.testapp;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

/*
センサーの値を取得するサンプル

 */
public class SensorFragment extends Fragment implements SensorEventListener {

    private Context context;
    private final int MP = WindowManager.LayoutParams.MATCH_PARENT;
    private TextView mTextview1
            ,mTextview2
            ,mTextview3
            ,mTextview4
            ,mTextview5;
    private SensorManager mSensorManager;

    public SensorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = getActivity().getApplicationContext();
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        RelativeLayout relativeLayout_main;
        relativeLayout_main = new RelativeLayout(context);
        relativeLayout_main.setOnClickListener(null);
//        relativeLayout_main.setBackgroundColor(Color.rgb(255, 170, 255));
        relativeLayout_main.setLayoutParams( new RelativeLayout.LayoutParams(MP, MP));

        mTextview1 = new TextView(context);
        mTextview1.setBackgroundColor(Color.rgb(80, 80, 255));
        mTextview1.setText("sensor");
        mTextview1.setId(R.id.text1);
        RelativeLayout.LayoutParams param =  new RelativeLayout.LayoutParams(dpTopx(270),dpTopx(50));
        param.setMargins(dpTopx(20), dpTopx(10), dpTopx(10), dpTopx(20));
        param.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        relativeLayout_main.addView(mTextview1, param);

        mTextview2 = new TextView(context);
        mTextview2.setBackgroundColor(Color.rgb(80, 80, 255));
        mTextview2.setText("");
        mTextview2.setId(R.id.text2);
        param =  new RelativeLayout.LayoutParams(dpTopx(270),dpTopx(20));
        param.setMargins(dpTopx(20), dpTopx(0), dpTopx(10), dpTopx(20));
        param.addRule(RelativeLayout.BELOW,R.id.text1);
        param.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        relativeLayout_main.addView(mTextview2, param);

        mTextview3 = new TextView(context);
        mTextview3.setBackgroundColor(Color.rgb(80, 80, 255));
        mTextview3.setText("");
        mTextview3.setId(R.id.text3);
        param =  new RelativeLayout.LayoutParams(dpTopx(270),dpTopx(50));
        param.setMargins(dpTopx(20), dpTopx(0), dpTopx(10), dpTopx(20));
        param.addRule(RelativeLayout.BELOW,R.id.text2);
        param.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        relativeLayout_main.addView(mTextview3, param);

        mTextview4 = new TextView(context);
        mTextview4.setBackgroundColor(Color.rgb(80, 80, 255));
        mTextview4.setText("");
        mTextview4.setId(R.id.text4);
        param =  new RelativeLayout.LayoutParams(dpTopx(270),dpTopx(50));
        param.setMargins(dpTopx(20), dpTopx(0), dpTopx(10), dpTopx(20));
        param.addRule(RelativeLayout.BELOW,R.id.text3);
        param.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        relativeLayout_main.addView(mTextview4, param);

        mTextview5 = new TextView(context);
        mTextview5.setBackgroundColor(Color.rgb(80, 80, 255));
        mTextview5.setText("");
        mTextview5.setId(R.id.text5);
        param =  new RelativeLayout.LayoutParams(dpTopx(270),dpTopx(50));
        param.setMargins(dpTopx(20), dpTopx(0), dpTopx(10), dpTopx(20));
        param.addRule(RelativeLayout.BELOW,R.id.text4);
        param.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        relativeLayout_main.addView(mTextview5, param);

        return relativeLayout_main;
    }

    @Override
    public void onResume() {
        super.onResume();
        // サブクラスからタイプを取得してセンサーを取得
        //センサーを指定してSensorを取得
        Sensor sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //List<Sensor> sensors = mSensorManager.getSensorList(Sensor.TYPE_LIGHT);
        mSensorManager.registerListener(this, sensor,
                SensorManager.SENSOR_DELAY_UI);

        sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        //List<Sensor> sensors = mSensorManager.getSensorList(Sensor.TYPE_LIGHT);
        mSensorManager.registerListener(this, sensor,
                SensorManager.SENSOR_DELAY_UI);

        sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        //List<Sensor> sensors = mSensorManager.getSensorList(Sensor.TYPE_LIGHT);
        mSensorManager.registerListener(this, sensor,
                SensorManager.SENSOR_DELAY_UI);

        sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        //List<Sensor> sensors = mSensorManager.getSensorList(Sensor.TYPE_LIGHT);
        mSensorManager.registerListener(this, sensor,
                SensorManager.SENSOR_DELAY_UI);

        sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        //List<Sensor> sensors = mSensorManager.getSensorList(Sensor.TYPE_LIGHT);
        mSensorManager.registerListener(this, sensor,
                SensorManager.SENSOR_DELAY_UI);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // センサーの精度が変更されると呼ばれる
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType() ) {
            case Sensor.TYPE_ACCELEROMETER:
                mTextview1.setText(String.format("加速度: %s\n　　　%s\n　　　%s", event.values[0], event.values[1], event.values[2]));
                break;

            case Sensor.TYPE_PRESSURE:
                mTextview2.setText(String.format("圧力: %s", event.values[0]));
                break;

            case Sensor.TYPE_GRAVITY:
                mTextview3.setText(String.format("重力　: %s\n　　　%s\n　　　%s", event.values[0], event.values[1], event.values[2]));
                break;

            case Sensor.TYPE_GYROSCOPE:
                mTextview4.setText(String.format("ジャイロ: %s\n　　　　%s\n　　　　%s", event.values[0], event.values[1], event.values[2]));
                break;

            case Sensor.TYPE_MAGNETIC_FIELD:
                mTextview5.setText(String.format("磁場: %s\n　　　%s\n　　　%s", event.values[0], event.values[1], event.values[2]));
                break;
        }
    }
    /*
     * dpからpxに変換
     */
    public int dpTopx(float dp){
        float d = context.getResources().getDisplayMetrics().density;
        return (int) Math.round(dp * d);
    }


}
