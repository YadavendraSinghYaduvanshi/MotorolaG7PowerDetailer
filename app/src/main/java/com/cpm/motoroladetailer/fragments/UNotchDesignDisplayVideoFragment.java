package com.cpm.motoroladetailer.fragments;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import com.cpm.motoroladetailer.Constant.CommonFunctions;
import com.cpm.motoroladetailer.R;


public class UNotchDesignDisplayVideoFragment extends Fragment {

    ImageView next_btn;
    VideoView videoView;
    boolean flag = false;
    int playVideo = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_u_notch_design_display_video, container, false);
        decalartion(view);
        return view;
    }

    private void decalartion(final View view) {
        videoView =
                (VideoView) view.findViewById(R.id.video_view);
        next_btn = (ImageView)view.findViewById(R.id.next_btn);

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if(playVideo == 1) {
                    next_btn.setVisibility(View.VISIBLE);
                }
                flag = true;
            }
        });

        videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(flag){
                    if(playVideo == 1) {
                        CommonFunctions.setVideoSize(getActivity(), videoView, R.raw.u_notch_display_starting_video);
                    }else{
                        CommonFunctions.setVideoSize(getActivity(),videoView, R.raw.u_notch_display_design);
                    }
                    next_btn.setVisibility(View.GONE);
                }
                return true;
            }
        });

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playVideo=2;
                next_btn.setVisibility(View.GONE);
                CommonFunctions.setVideoSize(getActivity(),videoView, R.raw.u_notch_display_design);
            }
        });

        CommonFunctions.setVideoSize(getActivity(),videoView, R.raw.u_notch_display_starting_video);
    }
}
