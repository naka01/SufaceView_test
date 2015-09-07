package com.example.so.testapp;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.VideoView;

public class VideoViewFragment extends Fragment {


    private RelativeLayout rootView;
    private Context context;
    private VideoView videoview;
    MediaController mediacontroller;
    private Handler handler = new Handler();

    private final int BS = 100;
    private final int MB = 20;

    public VideoViewFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //String VideoURL = "http://www.androidbegin.com/tutorial/AndroidCommercial.3gp";
        String VideoURL = "http://devimages.apple.com/iphone/samples/bipbop/gear1/prog_index.m3u8";
        context = getActivity().getApplicationContext();

        //root
        rootView = (RelativeLayout)inflater.inflate(R.layout.fragment_video_view, container, false);
        rootView.setOnClickListener(null);
        //rootView.setBackgroundColor(Color.argb(255, 255, 255, 255));

        videoview = (VideoView) rootView.findViewById(R.id.VideoView);

        try {
            // Start the MediaController
            //mediacontroller = new MediaController(this.getActivity());
            //mediacontroller.setAnchorView(videoview);
            // Get the URL from String VideoURL
            Uri video = Uri.parse(VideoURL);
            //videoview.setMediaController(mediacontroller);
            videoview.setVideoURI(video);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        videoview.requestFocus();
        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                //videoview.seekTo(videoview.getCurrentPosition() + 10000);
                mp.setWakeMode(context, PowerManager.PARTIAL_WAKE_LOCK);
                mp.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                    @Override
                    public boolean onError(MediaPlayer mp, int what, int extra) {
                        Log.v("log", String.format("onError: %s %s", what, extra));
                        return true;
                    }
                });
                mp.start();

                /*mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        Log.v("log", "complete");
                        mp.start();
                    }
                });*/
                //videoview.start();

                /*handler.post(new Runnable() {

                    @Override
                    public void run() {
                        mediacontroller.setEnabled(true);
                        mediacontroller.show(0);

                    }
                });*/
            }
        });

        Button button = new Button(context);
        button.setId(R.id.layout_vf1);
        button.setText("▶");
        button.setTextColor(Color.WHITE);
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(BS,BS);
        param.setMargins(MB, MB, MB, MB);
        param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        param.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        rootView.addView(button, param);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoview.seekTo(videoview.getCurrentPosition() + 5000);
            }
        });

        button = new Button(context);
        button.setId(R.id.layout_vf2);
        button.setText("◀");
        button.setTextColor(Color.WHITE);
        param = new RelativeLayout.LayoutParams(BS,BS);
        param.setMargins(MB, MB, MB, MB);
        param.addRule(RelativeLayout.LEFT_OF, R.id.layout_vf1);
        param.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        rootView.addView(button, param);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoview.seekTo(videoview.getCurrentPosition() - 5000);
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
