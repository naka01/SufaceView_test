package com.example.so.testapp;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;

public class TabUiTest extends Fragment {


    private LinearLayout rootView;
    private Context context;


    public TabUiTest(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = getActivity().getApplicationContext();

        //root
        rootView = (LinearLayout)inflater.inflate(R.layout.fragment_tab, container, false);
        //rootView.setBackgroundColor(Color.argb(255, 255, 255, 255));

        TabHost tabhost = (TabHost)rootView.findViewById(android.R.id.tabhost);
        tabhost.setup();

        tabhost.addTab(tabhost
                .newTabSpec("tab1")
                .setContent(android.R.id.tabcontent));

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
