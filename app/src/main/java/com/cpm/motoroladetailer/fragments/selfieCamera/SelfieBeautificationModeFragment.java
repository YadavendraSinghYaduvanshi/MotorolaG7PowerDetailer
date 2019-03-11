package com.cpm.motoroladetailer.fragments.selfieCamera;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cpm.motoroladetailer.R;


public class SelfieBeautificationModeFragment extends Fragment implements View.OnClickListener{

    ImageView btnLeftImg,btnRightImg,btnNormalImg,btnHighResolutionImg;
    RelativeLayout relative_layout_view;
    final int set1=1,set2=2,set3=3;
    int currentset,normalImg,highResolutionImg,leftMode,rightMode,btn_normal,btn_green;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_selfie_beautification_mode, container, false);
        decalartion(view);
        getActivity().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        return view;
    }

    private void decalartion(View view) {
        btnLeftImg = (ImageView)view.findViewById(R.id.left_btn);
        btnRightImg = (ImageView)view.findViewById(R.id.right_btn);
        btnNormalImg = (ImageView)view.findViewById(R.id.btn_normal);
        relative_layout_view = (RelativeLayout) view.findViewById(R.id.relative_layout_set1);
        btnHighResolutionImg = (ImageView)view.findViewById(R.id.btn_high_resolution);

        btnLeftImg.setOnClickListener(this);
        btnRightImg.setOnClickListener(this);
        btnNormalImg.setOnClickListener(this);
        btnHighResolutionImg.setOnClickListener(this);
        selectSets(set1);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.left_btn:
                // do your code
                selectSets(leftMode);
                break;

            case R.id.right_btn:
                // do your code
                selectSets(rightMode);
                break;

            case R.id.btn_normal:
                // do your code
                btnHighResolutionImg.setImageResource(btn_normal);
                btnNormalImg.setImageResource(R.drawable.btn_normal_green);
                relative_layout_view.setBackgroundResource(normalImg);
                break;

            case R.id.btn_high_resolution:
                // do your code
                btnHighResolutionImg.setImageResource(btn_green);
                btnNormalImg.setImageResource(R.drawable.btn_normal_white);
                relative_layout_view.setBackgroundResource(highResolutionImg);
                break;
        }
    }

    private void selectSets(int set){
        switch (set){
            case set1:
                setCurrentMode(set1,R.drawable.selfie_beautification_mode_normal,R.drawable.selfie_beautification_mode,0,set2,false,true,R.drawable.btn_beautification_mode_green,R.drawable.btn_beautification_mode_white);
                break;

            case set2:
                setCurrentMode(set2,R.drawable.portrait_mode_selfie_normal,R.drawable.portrait_mode_selfie,set1,set3,true,true, R.drawable.btn_portrait_green, R.drawable.btn_portrait_white);
                break;

            case set3:
                setCurrentMode(set3,R.drawable.group_selfie_normal,R.drawable.group_selfie,set2,0,true,false, R.drawable.group_selfie_green_btn, R.drawable.group_selfie_white_btn);
                break;

        }
    }


    private void setCurrentMode(int currentset, int normalImg, int highResolutionImg, int leftMode, int rightMode, boolean leftImgVisible, boolean rightImgVisible, int btn_green, int btn_normal){
        this.currentset = currentset;
        this.normalImg = normalImg;
        this.highResolutionImg = highResolutionImg;
        this.leftMode = leftMode;
        this.rightMode = rightMode;
        this.btn_normal = btn_normal;
        this.btn_green = btn_green;

        btnHighResolutionImg.setImageResource(btn_normal);
        btnNormalImg.setImageResource(R.drawable.btn_normal_green);
        relative_layout_view.setBackgroundResource(normalImg);

        if(leftImgVisible) {
            btnLeftImg.setVisibility(View.VISIBLE);
        }else{
            btnLeftImg.setVisibility(View.INVISIBLE);
        }

        if(rightImgVisible) {
            btnRightImg.setVisibility(View.VISIBLE);
        }else{
            btnRightImg.setVisibility(View.INVISIBLE);
        }
    }
}
