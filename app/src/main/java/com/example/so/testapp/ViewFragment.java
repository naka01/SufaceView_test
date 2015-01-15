package com.example.so.testapp;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.view.WindowManager.LayoutParams;

public class ViewFragment extends Fragment {

    /** Fragmentで保持しておくデータ */
    private int mData;

    private Context context;

    private final int MP = LayoutParams.MATCH_PARENT;
    private FragmentManager manager;
    private SurfaceAnimation surfaceanim;

    private final int BS = 100;
    private final int MB = 20;

    public static ViewFragment newInstance(int index) {
        ViewFragment fragment = new ViewFragment();

        // 引数を設定
        Bundle args = new Bundle();
        args.putInt("index", index);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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

        button = new Button(context);
        button.setId(R.id.layout_vf2);
        button.setText("CP");
        param = new RelativeLayout.LayoutParams(BS,BS);
        param.setMargins(MB,MB,MB,MB);
        param.addRule(RelativeLayout.LEFT_OF,R.id.layout_vf1);
        relativeLayout_main.addView(button, param);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                surfaceanim.topointChange(700, 400);
            }
        });


        surfaceanim = new SurfaceAnimation(context);
        surfaceanim.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //Log.d("TouchEvent", "X:" + event.getX() + ",Y:" + event.getY());

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        surfaceanim.topointChange((int) event.getX(), (int) event.getY());
                        break;
                    case MotionEvent.ACTION_UP:

                        break;
                    case MotionEvent.ACTION_MOVE:
                        surfaceanim.topointChange((int) event.getX(), (int) event.getY());
                        break;
                    case MotionEvent.ACTION_CANCEL:

                        break;
                }
                return true;
            }
        });

        param = new RelativeLayout.LayoutParams(MP, MP);
        param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        param.addRule(RelativeLayout.BELOW,R.id.layout_vf1);
        relativeLayout_main.addView(surfaceanim,param);

        return relativeLayout_main;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            // onSaveInstanceStateで保存されたデータを復元
            mData = savedInstanceState.getInt("data");
        }



    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Fragment内で残しておきたいデータを保存
        outState.putInt("data", mData);
    }

}