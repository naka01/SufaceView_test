package com.example.so.testapp;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
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
            ,mTextview5
            ,mTextview6;
    private SensorManager mSensorManager;

    private static final int MATRIX_SIZE = 16;
    /* 回転行列 */
    float[]  inR = new float[MATRIX_SIZE];
    float[] outR = new float[MATRIX_SIZE];//output
    float[]    I = new float[MATRIX_SIZE];

    /* センサーの値 */
    float[] orientationValues   = new float[3];
    float[] magneticValues      = new float[3];
    float[] accelerometerValues = new float[3];
    float[] valuesGravity = new float[3];
    float[] orientationValuesBuf   = new float[3];//バッファ

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

        mTextview6 = new TextView(context);
        mTextview6.setBackgroundColor(Color.rgb(80, 80, 255));
        mTextview6.setText("");
        mTextview6.setId(R.id.text6);
        param =  new RelativeLayout.LayoutParams(dpTopx(270),dpTopx(50));
        param.setMargins(dpTopx(20), dpTopx(0), dpTopx(10), dpTopx(20));
        param.addRule(RelativeLayout.BELOW,R.id.text5);
        param.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        relativeLayout_main.addView(mTextview6, param);

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
    public void onStop() {
        super.onStop();
        // Listenerの登録解除
        mSensorManager.unregisterListener(this);
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
                accelerometerValues = event.values.clone();
                break;

            case Sensor.TYPE_PRESSURE:
                mTextview2.setText(String.format("圧力: %s", event.values[0]));
                break;

            case Sensor.TYPE_GRAVITY:
                valuesGravity = event.values.clone();
                mTextview3.setText(String.format("重力　:x %s\n　　　y %s\n　　　z %s", event.values[0], event.values[1], event.values[2]));
                break;

            case Sensor.TYPE_GYROSCOPE:
                mTextview4.setText(String.format("ジャイロ: %s\n　　　　%s\n　　　　%s", event.values[0], event.values[1], event.values[2]));
                break;

            case Sensor.TYPE_MAGNETIC_FIELD:
                mTextview5.setText(String.format("磁場: %s\n　　　%s\n　　　%s", event.values[0], event.values[1], event.values[2]));
                magneticValues = event.values.clone();
                break;
        }

        /*if (magneticValues != null && accelerometerValues != null) {

            SensorManager.getRotationMatrix(inR, I, accelerometerValues, magneticValues);

            //Activityの表示が縦固定の場合。横向きになる場合、修正が必要です
            SensorManager.remapCoordinateSystem(inR, SensorManager.AXIS_X, SensorManager.AXIS_Z, outR);
            orientationValues = SensorManager.getOrientation(outR, orientationValues);

            setLookAt( orientationValues[1],orientationValues[2]);

        }*/

        if (SensorManager.getRotationMatrix(inR, null, accelerometerValues, magneticValues)) {
            SensorManager.remapCoordinateSystem(inR, SensorManager.AXIS_X, SensorManager.AXIS_Z, outR);
            SensorManager.getOrientation(outR,  orientationValues);

            if(valuesGravity[2] < 0)                                     {
                if ( orientationValues[1] > 0) {
                     orientationValues[1] = (float) (Math.PI -  orientationValues[1]);
                }
                else {
                     orientationValues[1] = (float) (-Math.PI -  orientationValues[1]);
                }
            }


            updateOrientationBuffer(orientationValues);

            /*for (int i = 0; i <  orientationValues.length; i++) {
                 orientationValues[i] /= Math.PI;
                 orientationValues[i] *= 100;
                 orientationValues[i] = (int) orientationValues[i];
                 orientationValues[i] /= 100;
            }*/


            //updateOrientationBuffer( orientationValues);

            //quadcopter.onEvent(new Event(Event.Codes.ORIENTATION_CHANGED, calculateSmoothedOrientation()));
        }
        else {
            Log.d("Quadcopter-SM", "Matrix rotate error");
        }
    }

    /**
     * バッファの保存 ローパスフィルタ
     * @param orientationValues
     */
    public void updateOrientationBuffer(float[] orientationValues){

        orientationValuesBuf[0] = 0.9f*orientationValuesBuf[0] + 0.1f*orientationValues[0];
        orientationValuesBuf[1] = 0.9f*orientationValuesBuf[1] + 0.1f*orientationValues[1];
        orientationValuesBuf[2] = 0.9f*orientationValuesBuf[2] + 0.1f*orientationValues[2];

        mTextview6.setText(String.format("傾き: %s\n　　　%s\n　　　%s", orientationValuesBuf[0], orientationValuesBuf[1],orientationValuesBuf[2]));
        setLookAt( orientationValuesBuf[1],orientationValuesBuf[2]);
    }


    /**
     * ビューポートの計算
     * @param z
     * @param y
     */


    public void setLookAt(float z,float y){

        float cosz = (float)Math.cos(z);
        float sinz = (float)Math.sin(z);
        float cosy = (float)Math.cos(y);
        float siny = (float)Math.sin(y);

        /*float lookx = (-5*cosz*cosy)+(3*siny*cosz)+5;
        float looky = (-5*sinz)-(3*cosz)+3;
        float lookz =(5*siny*cosz)-(3*cosz*cosy);*/

        float lookx = (-5*cosz*cosy)+(3*sinz*cosy)+5;
        float looky = (-5*sinz)-(3*cosz)+3;
        float lookz =(5*siny*cosz)-(3*sinz*siny);

        //mTextview6.setText(String.format("傾き: %s\n　　　%s\n　　　%s", lookx,looky,lookz));

    }

    /*
     * dpからpxに変換
     */
    public int dpTopx(float dp){
        float d = context.getResources().getDisplayMetrics().density;
        return (int) Math.round(dp * d);
    }


}
