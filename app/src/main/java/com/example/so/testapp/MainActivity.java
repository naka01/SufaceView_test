package com.example.so.testapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;


public class MainActivity extends ActionBarActivity {

    private FragmentManager manager;

    //画面サイズ
    private static int g_wi;
    private static int g_hi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        manager = getFragmentManager();

        RelativeLayout baseview = (RelativeLayout)this.getLayoutInflater().inflate(R.layout.activity_main,null);

        RelativeLayout contentview = (RelativeLayout)baseview.findViewById(R.id.mainview);
        Log.v("log", String.format("contentview: %s", contentview));
        Button button = new Button(this);
        button.setId(R.id.layout1);
        button.setText("cam");
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(200,200);
        param.setMargins(50,50,50,50);
        param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        contentview.addView(button,param);

        //setContentView(R.layout.activity_main);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SubActivity.class);
                startActivity(intent);
            }
        });

        button = new Button(this);
        button.setId(R.id.layout2);
        button.setText("surfaceView");
        param = new RelativeLayout.LayoutParams(200,200);
        param.setMargins(50,50,50,50);
        param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        param.addRule(RelativeLayout.BELOW,R.id.layout1);
        contentview.addView(button, param);


        setContentView(baseview);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = manager.beginTransaction();
                ViewFragment fragment = new ViewFragment();
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


    //画面サイズ取得
    public static int percentHeight(int in){
        float per = (float)in / 100f;
        return (int) (g_hi*per);
    }

    public static int percentWidth(int in){
        float per = (float)in / 100f;
        return (int) (g_wi*per);
    }


}
