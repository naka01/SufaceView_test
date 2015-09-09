package com.example.so.testapp;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;

public class TabUiTest extends Fragment {


    private RelativeLayout rootView;
    private Context context;
    private GoogleMap googleMap;

    private TabLayout tabLayout;

    private Polyline polyline1,polyline2,polyline3,polyline4;
    private ArrayList<Polyline> ArrayPolylineSet;

    public TabUiTest(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = getActivity().getApplicationContext();

        ArrayPolylineSet = new ArrayList<Polyline>();

        //root
        rootView = (RelativeLayout)inflater.inflate(R.layout.fragment_tab, container, false);
        rootView.setOnClickListener(null);
        rootView.setBackgroundColor(Color.argb(255, 255, 255, 255));

        tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        tabLayout.setBackgroundColor(Color.BLACK);
        tabLayout.setTabTextColors(Color.WHITE, Color.YELLOW);
        tabLayout.addTab(tabLayout.newTab().setText("陣取りゲーム").setTag("1"));
        tabLayout.addTab(tabLayout.newTab().setText("情報").setTag("2"));
        tabLayout.addTab(tabLayout.newTab().setText("ウォーキング").setTag("3"));


        //Goomap インスタンス取得
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap gMap) {
                googleMap = gMap;

                //初期位置
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(35.694922, 139.705361), 11));

                //ポリラインの描画
                PolylineOptions rectOptions = new PolylineOptions()
                        .add(new LatLng(35.687894, 139.676166),
                                new LatLng(35.556467, 139.643636),
                                new LatLng(35.548171, 139.614504));
                rectOptions.color(Color.BLUE);
                rectOptions.width(5);
                polyline1 = googleMap.addPolyline(rectOptions);

                rectOptions = new PolylineOptions()
                        .add(new LatLng(35.715888, 139.745597),
                                new LatLng(35.645293, 139.773273),
                                new LatLng(35.575425, 139.741713));
                rectOptions.color(Color.BLUE);
                rectOptions.width(5);
                polyline2 = googleMap.addPolyline(rectOptions);

                rectOptions = new PolylineOptions()
                        .add(new LatLng(35.717465, 139.599938),
                                new LatLng(35.636217, 139.629070),
                                new LatLng(35.535924, 139.625185));
                rectOptions.color(Color.BLUE);
                rectOptions.width(5);
                polyline3 = googleMap.addPolyline(rectOptions);

                rectOptions = new PolylineOptions()
                        .add(new LatLng(35.740326, 139.569835),
                                new LatLng(35.647660, 139.564494),
                                new LatLng(35.569106, 139.592169));
                rectOptions.color(Color.BLUE);
                rectOptions.width(5);
                polyline4 = googleMap.addPolyline(rectOptions);

                ArrayPolylineSet.add(polyline1);
                ArrayPolylineSet.add(polyline2);
                ArrayPolylineSet.add(polyline3);
                ArrayPolylineSet.add(polyline4);

                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        //ポリラインの選択状態をリセット
                        for(Polyline tarline : ArrayPolylineSet){
                                tarline.setColor(Color.BLUE);
                        }


                        //ポリラインのタップを検知
                        for(Polyline tarline : ArrayPolylineSet){
                            if (PolyUtil.isLocationOnPath(latLng, tarline.getPoints(), true, 100)) {
                                tarline.setColor(Color.rgb(255,0,255));
                                break;
                            }
                        }
                    }
                });

                tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {

                        switch (tab.getPosition()) {
                            case 0:
                                //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(35.630062, 139.733086), 11));
                                displayAllLine();
                                break;

                            case 1:
                                //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(35.679704, 139.697616), 11));
                                displayAllLine();
                                break;

                            case 2:
                                //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(35.683915, 139.765614), 11));
                                displaySelectedLine();
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


        /**
         * Floating Action Button
         * Android Design support library
         */

        /*FloatingActionButton fab = new FloatingActionButton(context);
        fab.setBackgroundDrawable(null);
        fab.setBackgroundColor(Color.CYAN);
        fab.getBackgroundTintList();
        fab.setRippleColor(Color.GREEN);
        fab.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT
                                    ,LinearLayout.LayoutParams.MATCH_PARENT));


        rootView.addView(fab);*/

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

    private void displaySelectedLine(){
        for(Polyline tarline : ArrayPolylineSet){
            if(tarline.getColor() == Color.rgb(255,0,255)){
                tarline.setVisible(true);
            }else{
                tarline.setVisible(false);
            }
        }
    }

    private void displayAllLine(){
        for(Polyline tarline : ArrayPolylineSet){
                tarline.setVisible(true);
        }
    }

}
