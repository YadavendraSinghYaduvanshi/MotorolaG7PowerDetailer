package com.cpm.motoroladetailer.fragments;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import com.cpm.motoroladetailer.Constant.CommonFunctions;
import com.cpm.motoroladetailer.R;

public class SuperFastProcessorVideoFargment extends Fragment {

    VideoView videoView;
    String backStateName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_super_fast_processor_video_fargment, container, false);
        decalartion(view);
        return view;
    }

    private void decalartion(View view) {
        videoView =
                (VideoView) view.findViewById(R.id.video_view);
        CommonFunctions.setVideoSize(getActivity(),videoView, R.raw.super_fast_processor);

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                loadFragment(new SmartProcessorFinalFragment());
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        backStateName = fragment.getClass().getName();
        FragmentManager fm = getActivity().getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.addToBackStack(null); // adding fragment to back stack
        fragmentTransaction.commit(); // save the changes

    }
}
