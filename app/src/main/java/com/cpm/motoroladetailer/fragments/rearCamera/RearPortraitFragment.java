package com.cpm.motoroladetailer.fragments.rearCamera;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cpm.motoroladetailer.R;


public class RearPortraitFragment extends Fragment implements View.OnClickListener{

    ImageView btnLeftImg,btnRightImg,btnNormalImg,btnHighResolutionImg;
    String backStateName;
    boolean fragmentPopped;
    RelativeLayout relative_layout_view;
    final int set1=1;
    int currentset,normalImg,highResolutionImg,leftMode,rightMode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_rear_portrait, container, false);
        decalartion(view);
        getActivity().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        return view;
    }

    private void decalartion(View view) {
        btnLeftImg = (ImageView)view.findViewById(R.id.left_btn);
        btnRightImg = (ImageView)view.findViewById(R.id.right_btn);
        btnNormalImg = (ImageView)view.findViewById(R.id.btn_normal);
        btnHighResolutionImg = (ImageView)view.findViewById(R.id.btn_high_resolution);
        relative_layout_view = (RelativeLayout) view.findViewById(R.id.relative_layout_set1);

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
                btnHighResolutionImg.setImageResource(R.drawable.btn_portrait_white);
                btnNormalImg.setImageResource(R.drawable.btn_normal_green);
                relative_layout_view.setBackgroundResource(normalImg);
                break;

            case R.id.btn_high_resolution:
                // do your code
                btnHighResolutionImg.setImageResource(R.drawable.btn_portrait_green);
                btnNormalImg.setImageResource(R.drawable.btn_normal_white);
                relative_layout_view.setBackgroundResource(highResolutionImg);

                break;
        }
    }

    private void selectSets(int set){
        switch (set){
            case set1:
                setCurrentMode(set1,R.drawable.rear_portrait_normal_img_set1,R.drawable.rear_portrait_img_set1,0,0,false,false);
                break;


        }
    }


    private void setCurrentMode(int currentset,int normalImg,int highResolutionImg, int leftMode,int rightMode,boolean leftImgVisible,boolean rightImgVisible){
        this.currentset = currentset;
        this.normalImg = normalImg;
        this.highResolutionImg = highResolutionImg;
        this.leftMode = leftMode;
        this.rightMode = rightMode;

        btnHighResolutionImg.setImageResource(R.drawable.btn_portrait_white);
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
