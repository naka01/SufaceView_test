package com.example.so.testapp;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
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



public class ViewMultiDrag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /** Fragmentで保持しておくデータ */
    private Context context;

    private final int MP = WindowManager.LayoutParams.MATCH_PARENT;
    private FragmentManager manager;
    private SurfaceDrag surfacedrag;

    private final int BS = 100;
    private final int MB = 20;



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewMultiDrag.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewMultiDrag newInstance(String param1, String param2) {
        ViewMultiDrag fragment = new ViewMultiDrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ViewMultiDrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
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
        param.addRule(RelativeLayout.BELOW,R.id.layout_vf1);
        relativeLayout_main.addView(frameLayout,param);


        surfacedrag = new SurfaceDrag(context);

        frameLayout.addView(surfacedrag, new ViewGroup.LayoutParams(MP,MP));



        return relativeLayout_main;
    }




    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}
