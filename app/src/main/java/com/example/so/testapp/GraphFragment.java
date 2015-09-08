package com.example.so.testapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class GraphFragment extends Fragment {


    /** Fragmentで保持しておくデータ */
    private Context context;

    private final int MP = WindowManager.LayoutParams.MATCH_PARENT;
    private FragmentManager manager;
    private GraphView graphview = null;

    private final int BS = 100;
    private final int MB = 20;


    public GraphFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // FragmentのViewを返却
        context = getActivity().getApplicationContext();
        manager = getFragmentManager();

        RelativeLayout relativeLayout_main;
        relativeLayout_main = new RelativeLayout(context);
        relativeLayout_main.setOnClickListener(null);
        relativeLayout_main.setBackgroundColor(Color.rgb(255, 170, 255));
        relativeLayout_main.setLayoutParams( new RelativeLayout.LayoutParams(MP, MP));

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
        param.addRule(RelativeLayout.BELOW, R.id.layout_vf1);
        relativeLayout_main.addView(frameLayout, param);

        graphview = new GraphView(context);
        graphview.setOpaque(false);
        graphview.setDatasetList(setDate());
        graphview.setScale_y(20);
        frameLayout.addView(graphview, new ViewGroup.LayoutParams(MP, MP));

        return relativeLayout_main;
    }

    @Override
    public void onDestroy() {
        //画像解放
        Log.v("log", "Texture fragment Destroy:");
        //graphview = null;
        //texturegraphic = null;
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.v("log", "Texture fragment onDestroyView:");
        graphview = null;
    }

    private ArrayList<Float> setDate(){

        //サンプルデータ作成
        ArrayList<Float> setdata = new ArrayList<Float>();
        setdata.add((float)1);
        setdata.add((float)3.2);
        setdata.add((float)4.5);
        setdata.add((float)4.5);
        setdata.add((float)4.7);
        setdata.add((float)3);
        setdata.add((float)2.1);
        setdata.add((float)1.9);
        setdata.add((float)3.3);
        setdata.add((float)1.3);
        setdata.add((float)1.2);
        setdata.add((float)2.4);
        setdata.add((float)2.9);
        setdata.add((float)1.2);
        setdata.add((float)3.1);
        setdata.add((float)1);
        setdata.add((float)0.95);
        setdata.add((float)1.7);
        setdata.add((float)0.65);
        setdata.add((float)0.73);
        setdata.add((float)0.6);
        setdata.add((float)0.52);
        setdata.add((float)1.1);
        setdata.add((float)0.58);
        setdata.add((float)0.77);
        setdata.add((float)2.8);
        setdata.add((float)1.8);
        setdata.add((float)0.75);
        setdata.add((float)1.3);
        setdata.add((float)5);
        setdata.add((float)3.3);
        setdata.add((float)1.7);
        setdata.add((float)1.8);
        setdata.add((float)3.7);
        setdata.add((float)2.3);
        setdata.add((float)1.4);
        setdata.add((float)3.5);
        setdata.add((float)2.9);
        setdata.add((float)3.3);
        setdata.add((float)4);
        setdata.add((float)4);
        setdata.add((float)1.8);
        setdata.add((float)1);
        setdata.add((float)6.9);
        setdata.add((float)7);
        setdata.add((float)5.4);
        setdata.add((float)4.5);
        setdata.add((float)2.6);
        setdata.add((float)2.3);
        setdata.add((float)2.2);
        setdata.add((float)2.3);
        setdata.add((float)3.1);
        setdata.add((float)2.3);
        setdata.add((float)2.1);
        setdata.add((float)2.3);
        setdata.add((float)4.6);
        setdata.add((float)2.8);
        setdata.add((float)1.9);
        setdata.add((float)4.6);
        setdata.add((float)5.2);
        setdata.add((float)3.8);
        setdata.add((float)8.7);
        setdata.add((float)4);
        setdata.add((float)5.7);
        setdata.add((float)4.1);
        setdata.add((float)4.5);
        setdata.add((float)3.5);
        setdata.add((float)5.4);
        setdata.add((float)10);
        setdata.add((float)13);
        setdata.add((float)5.6);
        setdata.add((float)6.2);
        setdata.add((float)6.9);
        setdata.add((float)12);
        setdata.add((float)13);
        setdata.add((float)4.9);
        setdata.add((float)9.5);
        setdata.add((float)2.5);
        setdata.add((float)2.1);
        setdata.add((float)5.2);
        setdata.add((float)7);
        setdata.add((float)5.8);
        setdata.add((float)5.1);
        setdata.add((float)4.3);
        setdata.add((float)0.59);
        setdata.add((float)0.49);
        setdata.add((float)0.46);
        setdata.add((float)0.32);
        setdata.add((float)0.27);
        setdata.add((float)0.21);
        setdata.add((float)0.17);
        setdata.add((float)0.17);
        setdata.add((float)0.11);
        setdata.add((float)0.11);
        setdata.add((float)0.06);
        setdata.add((float)0.14);
        setdata.add((float)0.3);
        setdata.add((float)0.4);
        setdata.add((float)0.23);
        setdata.add((float)0.5);
        return setdata;
    }
}
