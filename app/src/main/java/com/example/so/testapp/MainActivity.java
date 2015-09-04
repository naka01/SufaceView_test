package com.example.so.testapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;


public class MainActivity extends ActionBarActivity {

    private FragmentManager manager;

    //画面サイズ
    private static int g_wi;
    private static int g_hi;

    //ボタンサイズ
    private int B_wi = 300;
    private int B_hi = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        manager = getFragmentManager();

        RelativeLayout baseview = (RelativeLayout)this.getLayoutInflater().inflate(R.layout.activity_main,null);

        RelativeLayout contentview = (RelativeLayout)baseview.findViewById(R.id.mainview);

        Button button = new Button(this);
        button.setId(R.id.layout1);
        button.setText("texture gra");
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(B_wi,B_hi);
        param.setMargins(20, 20, 50, 0);
        param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        contentview.addView(button,param);

        //setContentView(R.layout.activity_main);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //saveBtnF(getApplicationContext());
                FragmentTransaction transaction = manager.beginTransaction();
                Bundle arg = new Bundle();
                arg.putInt("page",2);
                TextureFragment fragment = new TextureFragment();
                fragment.setArguments(arg);
                transaction.add(R.id.fragtar,fragment );
                transaction.addToBackStack(null);//前のfragmentへもどるのに必要
                transaction.commit();
            }
        });

        button = new Button(this);
        button.setId(R.id.layout2);
        button.setText("surfaceView");
        param = new RelativeLayout.LayoutParams(B_wi,B_hi);
        param.setMargins(20, 20, 50, 0);
        param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        param.addRule(RelativeLayout.BELOW, R.id.layout1);
        contentview.addView(button, param);


        setContentView(baseview);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*boolean tes = loadBtn(getApplicationContext());
                Log.v("log", String.format("tes",tes));
                test(getApplicationContext());*/
                FragmentTransaction transaction = manager.beginTransaction();
                ViewFragment fragment = new ViewFragment();
                transaction.add(R.id.fragtar,fragment );
                transaction.addToBackStack(null);//前のfragmentへもどるのに必要
                transaction.commit();
            }
        });

        button = new Button(this);
        button.setId(R.id.layout3);
        button.setText("MultiDrag");
        param = new RelativeLayout.LayoutParams(B_wi,B_hi);
        param.setMargins(20,20,50,0);
        param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        param.addRule(RelativeLayout.BELOW,R.id.layout2);
        contentview.addView(button, param);


        setContentView(baseview);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*boolean tes = loadBtn(getApplicationContext());
                Log.v("log", String.format("tes",tes));
                test(getApplicationContext());*/
                FragmentTransaction transaction = manager.beginTransaction();
                ViewMultiDrag fragment = new ViewMultiDrag();
                transaction.add(R.id.fragtar,fragment );
                transaction.addToBackStack(null);//前のfragmentへもどるのに必要
                transaction.commit();
            }
        });

        button = new Button(this);
        button.setId(R.id.layout4);
        button.setText("Texture");
        param = new RelativeLayout.LayoutParams(B_wi,B_hi);
        param.setMargins(20,20,50,0);
        param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        param.addRule(RelativeLayout.BELOW,R.id.layout3);
        contentview.addView(button, param);


        setContentView(baseview);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*boolean tes = loadBtn(getApplicationContext());
                Log.v("log", String.format("tes",tes));
                test(getApplicationContext());*/
                FragmentTransaction transaction = manager.beginTransaction();
                Bundle arg = new Bundle();
                arg.putInt("page",1);
                TextureFragment fragment = new TextureFragment();
                fragment.setArguments(arg);
                transaction.add(R.id.fragtar,fragment );
                transaction.addToBackStack(null);//前のfragmentへもどるのに必要
                transaction.commit();
            }
        });

        button = new Button(this);
        button.setId(R.id.layout5);
        button.setText("cam");
        param = new RelativeLayout.LayoutParams(B_wi,B_hi);
        param.setMargins(20,20,50,0);
        param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        param.addRule(RelativeLayout.BELOW,R.id.layout4);
        contentview.addView(button, param);


        setContentView(baseview);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*boolean tes = loadBtn(getApplicationContext());
                Log.v("log", String.format("tes",tes));
                test(getApplicationContext());*/
                Intent intent = new Intent(MainActivity.this,SubActivity.class);
                startActivity(intent);


            }
        });

        button = new Button(this);
        button.setId(R.id.layout6);
        button.setText("sensor");
        param = new RelativeLayout.LayoutParams(B_wi,B_hi);
        param.setMargins(20,20,50,0);
        param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        param.addRule(RelativeLayout.BELOW,R.id.layout5);
        contentview.addView(button, param);

        setContentView(baseview);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*boolean tes = loadBtn(getApplicationContext());
                Log.v("log", String.format("tes",tes));
                test(getApplicationContext());*/
                Intent intent = new Intent(MainActivity.this,SensorActivity.class);
                startActivity(intent);


            }
        });

        button = new Button(this);
        button.setId(R.id.layout7);
        button.setText("OpenGL");
        param = new RelativeLayout.LayoutParams(B_wi,B_hi);
        param.setMargins(20,20,50,0);
        param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        param.addRule(RelativeLayout.BELOW,R.id.layout6);
        contentview.addView(button, param);

        setContentView(baseview);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*boolean tes = loadBtn(getApplicationContext());
                Log.v("log", String.format("tes",tes));
                test(getApplicationContext());*/
                Intent intent = new Intent(MainActivity.this,OpenGLActivity.class);
                startActivity(intent);

            }
        });

        button = new Button(this);
        button.setId(R.id.layout8);
        button.setText("Volley");
        param = new RelativeLayout.LayoutParams(B_wi,B_hi);
        param.setMargins(20,20,50,0);
        param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        param.addRule(RelativeLayout.BELOW,R.id.layout7);
        contentview.addView(button, param);


        setContentView(baseview);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*boolean tes = loadBtn(getApplicationContext());
                Log.v("log", String.format("tes",tes));
                test(getApplicationContext());*/
                FragmentTransaction transaction = manager.beginTransaction();
                VolleyTestFragment fragment = new VolleyTestFragment();
                transaction.add(R.id.fragtar,fragment );
                transaction.addToBackStack(null);//前のfragmentへもどるのに必要
                transaction.commit();
            }
        });

        //コンパス

        button = new Button(this);
        button.setId(R.id.layout9);
        button.setText("compass");
        param = new RelativeLayout.LayoutParams(B_wi,B_hi);
        param.setMargins(20,20,50,0);
        //param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        param.addRule(RelativeLayout.LEFT_OF,R.id.layout8);
        contentview.addView(button, param);


        setContentView(baseview);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*boolean tes = loadBtn(getApplicationContext());
                Log.v("log", String.format("tes",tes));
                test(getApplicationContext());*/
                FragmentTransaction transaction = manager.beginTransaction();
                OrientationViewFragment fragment = new OrientationViewFragment();
                transaction.add(R.id.fragtar, fragment);
                transaction.addToBackStack(null);//前のfragmentへもどるのに必要
                transaction.commit();
            }
        });

        /**
         * tab
         */
        button = new Button(this);
        button.setId(R.id.layout10);
        button.setText("tab");
        param = new RelativeLayout.LayoutParams(B_wi,B_hi);
        param.setMargins(20,20,50,0);
        //param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        param.addRule(RelativeLayout.BELOW,R.id.layout9);
        contentview.addView(button, param);


        setContentView(baseview);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*boolean tes = loadBtn(getApplicationContext());
                Log.v("log", String.format("tes",tes));
                test(getApplicationContext());*/
                FragmentTransaction transaction = manager.beginTransaction();
                TabUiTest fragment = new TabUiTest();
                transaction.add(R.id.fragtar,fragment );
                transaction.addToBackStack(null);//前のfragmentへもどるのに必要
                transaction.commit();
            }
        });

        /**
         * VideoView
         */
        button = new Button(this);
        button.setId(R.id.layout11);
        button.setText("Video");
        param = new RelativeLayout.LayoutParams(B_wi,B_hi);
        param.setMargins(20,20,50,0);
        //param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        param.addRule(RelativeLayout.BELOW,R.id.layout10);
        contentview.addView(button, param);


        setContentView(baseview);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*boolean tes = loadBtn(getApplicationContext());
                Log.v("log", String.format("tes",tes));
                test(getApplicationContext());*/
                FragmentTransaction transaction = manager.beginTransaction();
                VideoViewFragment fragment = new VideoViewFragment();
                transaction.add(R.id.fragtar,fragment );
                transaction.addToBackStack(null);//前のfragmentへもどるのに必要
                transaction.commit();
            }
        });

        //画面サイズ取得
        Display disp = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);
        g_wi = size.x;
        g_hi = size.y;

    }

    @Override
    public void onAttachFragment(Fragment fra){
        Log.v("log", "onAttachFragment");
    }

    //画面サイズ取得
    public static int percentHeight(int in){
        float per = (float)in /100f;
        return (int) (g_hi*per);
    }

    public static int percentWidth(int in){
        float per = (float)in / 100f;
        return (int) (g_wi*per);
    }

    @Override
    public void onBackPressed() {
        //バックスタックの登録数をチェックして0であればPopUpは存在しない
        if (0 != manager.getBackStackEntryCount()){
            manager.popBackStack();

            return;
        }
        super.onBackPressed();
    }
}
