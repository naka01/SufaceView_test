package com.example.so.testapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;


public class MainActivity extends ActionBarActivity {

    private FragmentManager manager;

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

    }


    //メニュー
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

    //セッティング
    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
