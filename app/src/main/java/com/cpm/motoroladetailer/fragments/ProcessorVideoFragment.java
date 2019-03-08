package com.cpm.motoroladetailer.fragments;

import android.media.MediaPlayer;
import android.media.SubtitleData;
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


public class ProcessorVideoFragment extends Fragment {

    VideoView videoView;
    ImageView next_btn,sd_btn;
    int playVideo = 1;
    boolean flag = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_processor_video, container, false);
        decalartion(view);
        return view;
    }


    private void decalartion(final View view) {
        videoView =
                (VideoView) view.findViewById(R.id.video_view);

        next_btn = (ImageView)view.findViewById(R.id.media_tek);
        sd_btn = (ImageView)view.findViewById(R.id.sd_btn);

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if(playVideo == 1) {
                    flag = true;
                }else{
                    if(playVideo == 2){
                        flag = true;
                    }else{
                        flag = false;
                    }
                }
            }
        });

//        videoView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if(flag){
//                    if(playVideo == 1) {
//                        CommonFunctions.setVideoSize(getActivity(), videoView, R.raw.pubg_video_hd);
//                    }else{
//                        CommonFunctions.setVideoSize(getActivity(),videoView, R.raw.pubg_video_mediatek);
//                    }
//                }
//                return true;
//            }
//        });

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playVideo=1;
                next_btn.setVisibility(View.GONE);
                sd_btn.setVisibility(View.VISIBLE);
                CommonFunctions.setVideoSize(getActivity(),videoView, R.raw.pubg_video_mediatek);
            }
        });

        sd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playVideo=2;
                next_btn.setVisibility(View.VISIBLE);
                sd_btn.setVisibility(View.GONE);
                CommonFunctions.setVideoSize(getActivity(),videoView, R.raw.pubg_video_hd);
            }
        });

        CommonFunctions.setVideoSize(getActivity(),videoView, R.raw.pubg_video_hd);
    }
}
