package com.example.so.testapp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class SubActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout baseview = (RelativeLayout)this.getLayoutInflater().inflate(R.layout.sub_layout,null);
        CameraView cv = new CameraView(this);
        baseview.addView(cv);

        setContentView(baseview);
        //setContentView(R.layout.activity_main);


    }


}
