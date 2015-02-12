package com.example.so.testapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

public class OrientationViewFragment extends Fragment implements SensorEventListener {

    /** Fragmentで保持しておくデータ */
    private int mData;

    private Context context;

    private final int MP = WindowManager.LayoutParams.MATCH_PARENT;
    private FragmentManager manager;

    private final int BS = 100;
    private final int MB = 20;

    private SensorManager mSensorManager;   // センサマネージャ
    private Sensor mAccelerometer;  // 加速度センサ
    private Sensor mMagneticField;  // 磁気センサ

    private SurfaceViewExt mSurfaceViewExt; // サーフェイスビュー

    // 使わない
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    // 加速度センサの値
    private float[] mAccelerometerValue = new float[3];
    // 磁気センサの値
    private float[] mMagneticFieldValue = new float[3];
    // 磁気センサの更新がすんだか
    private boolean mValidMagneticFiled = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // FragmentのViewを返却
        context = getActivity().getApplicationContext();
        manager = getFragmentManager();

        // センサーを取り出す
        this.mSensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
        this.mAccelerometer = this.mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.mMagneticField = this.mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);


        RelativeLayout relativeLayout_main;
        relativeLayout_main = new RelativeLayout(context);
        relativeLayout_main.setOnClickListener(null);
        relativeLayout_main.setBackgroundColor(Color.rgb(255, 170, 255));
        relativeLayout_main.setLayoutParams( new RelativeLayout.LayoutParams(MP, MP));





        /*
        戻るボタン

         */

        Button button = new Button(context);
        button.setId(R.id.layout_vf1);
        button.setText("back");
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(BS,BS);
        param.setMargins(MB,MB,MB,MB);
        param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        relativeLayout_main.addView(button, param);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.popBackStack();
            }
        });



        //Framelayout
        FrameLayout frameLayout = new FrameLayout(context);
        param = new RelativeLayout.LayoutParams(MP, MP);
        param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        param.addRule(RelativeLayout.BELOW,R.id.layout_vf1);
        relativeLayout_main.addView(frameLayout,param);

        mSurfaceViewExt = new SurfaceViewExt(context);
        frameLayout.addView(mSurfaceViewExt, new ViewGroup.LayoutParams(MP,MP));





        return relativeLayout_main;
    }

    // ポーズ
    @Override
    public void onPause() {
        super.onPause();

        // リスナーの登録解除
        this.mSensorManager.unregisterListener(this);
    }

    // ////////////////////////////////////////////////////////////
    // 再開
    @Override
    public void onResume() {
        super.onResume();

        // リスナーの登録
        this.mSensorManager.registerListener(
                this, this.mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        this.mSensorManager.registerListener(
                this, this.mMagneticField, SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    public void onDestroy() {
        //画像解放
        //Log.v("log", "Surface fragment Destroy:");
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //Log.v("log", "Surface fragment onDestroyView:");
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        // センサーごとの処理
        switch (event.sensor.getType()) {
            // 加速度センサー
            case Sensor.TYPE_ACCELEROMETER:
                // cloneで配列がコピーできちゃうんだね。へえ
                this.mAccelerometerValue = event.values.clone();
                break;
            // 磁気センサー
            case Sensor.TYPE_MAGNETIC_FIELD:
                this.mMagneticFieldValue = event.values.clone();
                this.mValidMagneticFiled = true;
                break;
        }

        // 値が更新された角度を出す準備ができた
        if (this.mValidMagneticFiled) {
            // 方位を出すための変換行列
            float[] rotate = new float[16]; // 傾斜行列？
            float[] inclination = new float[16];    // 回転行列

            // うまいこと変換行列を作ってくれるらしい
            SensorManager.getRotationMatrix(
                    rotate, inclination,
                    this.mAccelerometerValue,
                    this.mMagneticFieldValue);

            // 方向を求める
            float[] orientation = new float[3];
            this.getOrientation(rotate, orientation);

            // デグリー角に変換する
            float degreeDir = (float)Math.toDegrees(orientation[0]);
            Log.v("onSensorChanged", "角度:" + degreeDir);

            // アローを回転させる
            this.mSurfaceViewExt.setArrowDir(degreeDir);
        }
    }


    // ////////////////////////////////////////////////////////////
    // 画面が回転していることを考えた方角の取り出し
    public void getOrientation(float[] rotate, float[] out) {

        // ディスプレイの回転方向を求める(縦もちとか横持ちとか)
        Display disp = getActivity().getWindowManager().getDefaultDisplay();
        // ↓コレを使うためにはAPIレベルを8にする必要がある
        int dispDir = disp.getRotation();

        // 画面回転してない場合はそのまま
        if (dispDir == Surface.ROTATION_0) {
            SensorManager.getOrientation(rotate, out);

            // 回転している
        } else {

            float[] outR = new float[16];

            // 90度回転
            if (dispDir == Surface.ROTATION_90) {
                SensorManager.remapCoordinateSystem(
                        rotate, SensorManager.AXIS_Y,SensorManager.AXIS_MINUS_X, outR);
                // 180度回転
            } else if (dispDir == Surface.ROTATION_180) {
                float[] outR2 = new float[16];

                SensorManager.remapCoordinateSystem(
                        rotate, SensorManager.AXIS_Y,SensorManager.AXIS_MINUS_X, outR2);
                SensorManager.remapCoordinateSystem(
                        outR2, SensorManager.AXIS_Y,SensorManager.AXIS_MINUS_X, outR);
                // 270度回転
            } else if (dispDir == Surface.ROTATION_270) {
                SensorManager.remapCoordinateSystem(
                        outR, SensorManager.AXIS_MINUS_Y,SensorManager.AXIS_MINUS_X, outR);
            }
            SensorManager.getOrientation(outR, out);
        }
    }

}