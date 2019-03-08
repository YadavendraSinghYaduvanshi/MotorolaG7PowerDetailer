package com.cpm.motoroladetailer.fragments.rearCamera;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cpm.motoroladetailer.R;

public class RearCameraFunctionFargment extends Fragment implements View.OnClickListener {

    ImageView high_resolution_zoom_img,portrait_mode_img,beautification_mode_img,spot_color_img,cinemagraph_img;
    String backStateName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_rear_camera_function, container, false);
        decalration(view);
        return view;
    }



    private void decalration(View view) {
        high_resolution_zoom_img = (ImageView)view.findViewById(R.id.high_resolution_zoom);
        portrait_mode_img = (ImageView)view.findViewById(R.id.portrait_mode);

        spot_color_img = (ImageView)view.findViewById(R.id.spot_color);
        beautification_mode_img = (ImageView)view.findViewById(R.id.beautification_mode);
        cinemagraph_img = (ImageView)view.findViewById(R.id.cinemagraph);

        high_resolution_zoom_img.setOnClickListener(this);
        portrait_mode_img.setOnClickListener(this);

        spot_color_img.setOnClickListener(this);
        beautification_mode_img.setOnClickListener(this);
        cinemagraph_img.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.high_resolution_zoom:
                // do your code
                loadFragment(new RearHighResultionCameraFragment());
                break;

            case R.id.portrait_mode:
                // do your code
                loadFragment(new RearPortraitFragment());
                break;


            case R.id.spot_color:
                // do your code
                loadFragment(new RearSpotColorFragment());
                break;

            case R.id.beautification_mode:
                // do your code
                loadFragment(new RearBeautificationModeFragment());
                break;

            case R.id.cinemagraph:
                // do your code
                loadFragment(new RearCinemaGraphFragment());
                break;

        }
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
