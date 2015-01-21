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

public class TextureFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    /** Fragmentで保持しておくデータ */
    private Context context;

    private final int MP = WindowManager.LayoutParams.MATCH_PARENT;
    private FragmentManager manager;
    private TextureAnimation textureanim = null;
    private TextureGraphic texturegraphic = null;

    private final int BS = 100;
    private final int MB = 20;


    public TextureFragment() {
        // Required empty public constructor
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

        //bundle
        int page = getArguments().getInt("page",1);

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
        param.addRule(RelativeLayout.BELOW,R.id.layout_vf1);
        relativeLayout_main.addView(frameLayout,param);

        if(page == 1) {
            textureanim = new TextureAnimation(context);
            textureanim.setOpaque(false);
            frameLayout.addView(textureanim, new ViewGroup.LayoutParams(MP, MP));
        }else{
            texturegraphic = new TextureGraphic(context);
            texturegraphic.setOpaque(false);
            frameLayout.addView(texturegraphic, new ViewGroup.LayoutParams(MP, MP));
        }

        return relativeLayout_main;
    }

    @Override
    public void onDestroy() {
        //画像解放
        Log.v("log", "Texture fragment Destroy:");
        //textureanim = null;
        //texturegraphic = null;
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.v("log", "Texture fragment onDestroyView:");
        textureanim = null;
        texturegraphic = null;
    }

}
