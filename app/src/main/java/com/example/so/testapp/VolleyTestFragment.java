package com.example.so.testapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;

public class VolleyTestFragment extends Fragment {

    //private OnFragmentInteractionListener mListener;
    private Context context;
    private TextView textview;

    private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;

    public RequestQueue mQueue;

    public VolleyTestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = getActivity().getApplicationContext();

        // 東京都の天気情報  リクエストREST
        final String url =
                "http://weather.livedoor.com/forecast/webservice/json/v1?city=130010";

        RelativeLayout baseframe = (RelativeLayout)inflater.inflate(R.layout.fragment_volley_test, container, false);

        baseframe.setOnTouchListener(null);
        baseframe.setOnClickListener(null);
        Button button = new Button(context);
        button.setText("volley");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("log", "click!!!");
                mQueue = Volley.newRequestQueue(context);
                mQueue.add(new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // JSONObjectのパース、List、Viewへの追加等

                                try {
                                    textview.setText(String.format("%s", response.getJSONObject("forecasts")
                                    .getJSONArray("dateLabel")));
                                } catch (JSONException e) {
                                    Log.v("log", String.format("log: %s", response));
                                    e.printStackTrace();
                                }
                            }
                        },

                        new Response.ErrorListener() {
                            @Override public void onErrorResponse(VolleyError error) {
                                // エラー処理 error.networkResponseで確認
                                // エラー表示など
                                Log.v("log", String.format("log error: %s",  error));
                                textview.setText(String.format("%s",  error));
                            }
                        }));
            }
        });

        //button.setBackgroundColor(Color.BLUE);
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(200,200);
        button.setPadding(50,50,50,50);
        param.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        button.setLayoutParams(param);
        baseframe.addView(button,param);

        textview = new TextView(context);
        textview.setText("test");
        param = new RelativeLayout.LayoutParams(WC,WC);
        param.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        textview.setLayoutParams(param);
        baseframe.addView(textview,param);

        return baseframe;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        /*try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    /*public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }*/

}
