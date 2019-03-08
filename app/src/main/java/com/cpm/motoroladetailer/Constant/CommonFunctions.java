package com.cpm.motoroladetailer.Constant;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import com.cpm.motoroladetailer.LoginActivity;
import com.cpm.motoroladetailer.R;

/**
 * Created by deepakp on 2/16/2017.
 */

public class CommonFunctions {

    public static boolean checkNetIsAvailable(LoginActivity loginActivity) {
        ConnectivityManager cm = (ConnectivityManager) loginActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }




    public static void setScreenFullView(Activity activity){
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public static void setVideoSize(FragmentActivity activity, VideoView videoView, int video) {

//        DisplayMetrics metrics = new DisplayMetrics();
//        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        android.widget.LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) videoView.getLayoutParams();
//        params.width =  metrics.widthPixels;
//        params.height = metrics.heightPixels;
//        params.leftMargin = 0;
//        videoView.setLayoutParams(params);

        activity.getWindow().setFormat(PixelFormat.UNKNOWN);
        videoView.setVideoURI(Uri.parse("android.resource://" + "com.cpm.motoroladetailer" + "/" + video));
        MediaController mediaController = new
                MediaController(activity);
        mediaController.setAnchorView(videoView);

        mediaController.setVisibility(View.GONE);
        videoView.setMediaController(mediaController);
        videoView.start();


    }
}
