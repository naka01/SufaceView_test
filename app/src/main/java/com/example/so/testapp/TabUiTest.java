package com.example.so.testapp;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TabHost;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

public class TabUiTest extends Fragment {


    private LinearLayout rootView;
    private Context context;
    private GoogleMap googleMap;

    private TabLayout tabLayout;

    public TabUiTest(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = getActivity().getApplicationContext();

        //root
        rootView = (LinearLayout)inflater.inflate(R.layout.fragment_tab, container, false);
        rootView.setOnClickListener(null);
        rootView.setBackgroundColor(Color.argb(255, 255, 255, 255));

        tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        tabLayout.setBackgroundColor(Color.BLACK);
        tabLayout.setTabTextColors(Color.WHITE, Color.YELLOW);
        tabLayout.addTab(tabLayout.newTab().setText("Tab 1").setTag("1"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 2").setTag("2"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 3").setTag("3"));


        //Goomap インスタンス取得
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap gMap) {
                googleMap = gMap;

                tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {

                        switch(tab.getPosition()){
                            case 0:
                                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(35.630062, 139.733086), 15));
                                break;

                            case 1:
                                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(35.679704, 139.697616), 15));
                                break;

                            case 2:
                                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(35.683915, 139.765614), 15));
                                break;
                        }

                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });
            }
        });





        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }



}
