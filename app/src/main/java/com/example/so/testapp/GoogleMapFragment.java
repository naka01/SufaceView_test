package com.example.so.testapp;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;


/**
 * A simple {@link Fragment} subclass.
 */
public class GoogleMapFragment extends MapFragment {
    private Context context;
    private final int MP = WindowManager.LayoutParams.MATCH_PARENT;

    public GoogleMapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /*context = getActivity().getApplicationContext();

        RelativeLayout relativeLayout_main;
        relativeLayout_main = new RelativeLayout(context);
        relativeLayout_main.setOnClickListener(null);
//        relativeLayout_main.setBackgroundColor(Color.rgb(255, 170, 255));
        relativeLayout_main.setLayoutParams( new RelativeLayout.LayoutParams(MP, MP));

        GoogleMap map = getMap();
        relativeLayout_main.addView(map);

        return relativeLayout_main;*/
        return super.onCreateView(inflater, container, savedInstanceState);
    }


}
