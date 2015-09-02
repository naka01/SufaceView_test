package com.example.so.testapp;

import android.app.Fragment;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public VideoViewFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String VideoURL = "http://www.androidbegin.com/tutorial/AndroidCommercial.3gp";
        //String VideoURL = "http://devimages.apple.com/iphone/samples/bipbop/gear1/prog_index.m3u8";
        context = getActivity().getApplicationContext();

        //root
        rootView = (RelativeLayout)inflater.inflate(R.layout.fragment_video_view, container, false);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //rootView.setBackgroundColor(Color.argb(255, 255, 255, 255));

        videoview = (VideoView) rootView.findViewById(R.id.VideoView);

        try {
            // Start the MediaController
            mediacontroller = new MediaController(
                    this.getActivity());
            mediacontroller.setAnchorView(videoview);
            // Get the URL from String VideoURL
            Uri video = Uri.parse(VideoURL);
            videoview.setMediaController(mediacontroller);
            videoview.setVideoURI(video);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        videoview.requestFocus();
        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                videoview.seekTo(videoview.getCurrentPosition() + 15000);
                videoview.start();

                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        mediacontroller.setEnabled(true);
                        mediacontroller.show(0);

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
