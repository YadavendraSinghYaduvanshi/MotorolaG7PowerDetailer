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
import com.cpm.motoroladetailer.fragments.rearCamera.RearCameraFunctionFargment;
import com.cpm.motoroladetailer.fragments.selfieCamera.SelfieBeautificationModeFragment;

public class SmartCameraSelectionOptionFragment extends Fragment implements View.OnClickListener {
    ImageView rearImg1,selfieImg2;
    String backStateName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_smart_camera_selection_option, container, false);
        decalration(view);
        return view;
    }

    private void decalration(View view) {
        rearImg1 = (ImageView)view.findViewById(R.id.rear_camera);
        selfieImg2 = (ImageView)view.findViewById(R.id.selfie_camera);

        rearImg1.setOnClickListener(this);
        selfieImg2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.rear_camera:
                // do your code
                loadFragment(new RearCameraFunctionFargment());
                break;

            case R.id.selfie_camera:
                // do your code
                loadFragment(new SelfieBeautificationModeFragment());
                break;
        }
    }

    private void loadFragment(Fragment fragment) {
        backStateName = fragment.getClass().getName();
        FragmentManager fm = getActivity().getSupportFragmentManager();
       // fragmentPopped = fm.popBackStackImmediate (backStateName, 0);

      //  if (!fragmentPopped) { //fragment not in back stack, create it.
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
           // fragmentTransaction.setCustomAnimations(R.anim.alpha, R.anim.translate);
            fragmentTransaction.replace(R.id.frameLayout, fragment);
            fragmentTransaction.addToBackStack(null); // adding fragment to back stack
            fragmentTransaction.commit(); // save the changes
       // }
    }
}
