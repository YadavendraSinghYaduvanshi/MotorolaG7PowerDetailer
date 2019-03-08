package com.cpm.motoroladetailer.Constant;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;


/**
 * Created by neeraj on 10/27/2018.
 */

@SuppressWarnings("deprecation")
public class AlertandMessages {

    private String data, condition;
    private Activity activity;

    public AlertandMessages(Activity activity, String data, String condition, Exception exception) {
        this.activity = activity;
        this.data = data;
        this.condition = condition;
    }

    public static void showSnackbarMsg(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    public static void showSnackbarMsg(Context context, String message) {
        Snackbar.make(((Activity) context).getCurrentFocus(), message, Snackbar.LENGTH_SHORT).show();
    }


    public static void showToastMsg(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

//
//    public static void showAlert(final Context context, String str, final Boolean activityFinish) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("Parinaam");
//        builder.setMessage(str).setCancelable(false).setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                if (activityFinish) {
//                    ((Activity)context).finish();
//                } else {
//                    dialog.dismiss();
//                }
//
//            }
//        });
//        AlertDialog alert = builder.create();
//        alert.show();
//        CommonFunctions.setButtonTextColor(alert);
//    }
//
//    public static void showAlert1(final String empCd, final Context context, String str, final Boolean activityFinish) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("Parinaam");
//        builder.setMessage(str).setCancelable(false).setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                if (activityFinish) {
//                    ((Activity)context).finish();
//                } else {
//                    Intent intent = new Intent(context, PicWithMerchandiser.class);
//                    intent.putExtra("Emp_Cd",empCd);
//                    context.startActivity(intent);
//                    ((Activity)context).overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
//                    dialog.dismiss();
//                }
//
//            }
//        });
//        AlertDialog alert = builder.create();
//        alert.show();
//        CommonFunctions.setButtonTextColor(alert);
//    }
//
//
//    public static void backpressedAlert(final Context context) {
//
//        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("Alert");
//        builder.setMessage("Do you want to exit? Filled data will be lost").setCancelable(false)
//                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        ((Activity) context).finish();
//                        ((Activity) context).overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
//
//                    }
//                });
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//        AlertDialog alert = builder.create();
//        alert.show();
//        CommonFunctions.setButtonTextColor(alert);
//    }
//
//
//
//    public static void showAlertMessage(final Activity activity, String str, final Boolean activityFinish, final String upload_flag) {
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//        builder.setTitle("Parinaam");
//        builder.setMessage(str).setCancelable(false)
//                .setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        if (activityFinish) {
//                            activity.finish();
//                        } else {
//                            dialog.dismiss();
//                        }
//
//                    }
//                });
//        AlertDialog alert = builder.create();
//        alert.show();
//        CommonFunctions.setButtonTextColor(alert);
//    }

}
