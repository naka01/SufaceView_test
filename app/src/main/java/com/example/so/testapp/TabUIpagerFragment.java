package com.example.so.testapp;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;

public class TabUIpagerFragment extends Fragment {


    private RelativeLayout rootView;
    private Context context;
    private GoogleMap googleMap;

    private TabLayout tabLayout;

    private Polyline polyline1,polyline2,polyline3,polyline4;
    private ArrayList<Polyline> ArrayPolylineSet;

    public TabUIpagerFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = getActivity().getApplicationContext();

        ArrayPolylineSet = new ArrayList<Polyline>();

        //root
        rootView = (RelativeLayout)inflater.inflate(R.layout.fragment_tab_pager, container, false);
        rootView.setOnClickListener(null);
        rootView.setBackgroundColor(Color.argb(255, 255, 255, 255));

        tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        tabLayout.setBackgroundColor(Color.BLACK);
        tabLayout.setTabTextColors(Color.WHITE, Color.YELLOW);
        tabLayout.addTab(tabLayout.newTab().setText("ユーザ情報").setTag("1"));
        tabLayout.addTab(tabLayout.newTab().setText("ウォーキング").setTag("2"));
        tabLayout.addTab(tabLayout.newTab().setText("統計").setTag("3"));

        final ViewPager viewPager = (ViewPager)rootView.findViewById(R.id.pager);
        final FragmentPagerAdapterTest adapter;
        adapter = new FragmentPagerAdapterTest(getFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        /**
         * Floating Action Button
         * Android Design support library
         */


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
