package com.cpm.motoroladetailer.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cpm.motoroladetailer.R;

public class SmartProcessorFinalFragment extends Fragment{

    ImageView processor_img,os_img;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_smart_processor_final_screen, container, false);
        decalration(view);
        return view;
    }

    private void decalration(View view) {
        processor_img = (ImageView)view.findViewById(R.id.processor_img);
        os_img = (ImageView)view.findViewById(R.id.os_img);

        processor_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new ProcessorVideoFragment());
            }
        });

        os_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new OSVideoFargment());
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        //   backStateName = fragment.getClass().getName();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit(); // save the changes
    }

}
