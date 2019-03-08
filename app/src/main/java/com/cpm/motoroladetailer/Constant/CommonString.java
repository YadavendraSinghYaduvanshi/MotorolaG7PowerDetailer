package com.cpm.motoroladetailer.Constant;

import android.os.Environment;

/**
 * Created by neeraj on 19-02-2019.
 */

public class CommonString {

    public static final String KEY_USERNAME = "username";
    public static final String URL2 = "http://li.parinaam.in/Webservices/Lorealservice.svc/";
    public static final String URL3 = "http://li.parinaam.in/LoralMerchandising.asmx/";
    public static final String IMAGE_PATH = Environment.getExternalStorageDirectory() + "/.MotorolaDetailer_All_Images/";
    public static final String VIDEO_PATH = Environment.getExternalStorageDirectory().getPath() + "/MotoralDetailer_All_Videos/";
    public static final String KEY_VISIT_DATE = "Visited_Date";
    public static final String KEY_ID = "_id";
    public static final String KEY_EMP_CD = "Emp_Cd";
    public static final String TABLE_TOTAL_VISIT_COUNT = "Total_Visit_Count";
    public static final String KEY_SMART_CAMERA_COUNTER = "Smart_Camera_Counter";
    public static final String KEY_SUPER_FAST_PROCESSOR_COUNTER = "Super_Fast_Processor_Counter";
    public static final String KEY_U_NOTCH_DISPLAY_COUNTER = "U_Notch_Display_Counter";
    public static final String KEY_TURBO_POWER_CHARGING_COUNTER = "Turbo_Power_Charging_Counter";


    public static final String CREATE_TABLE_TOTAL_VISIT_COUNT = "CREATE TABLE  IF NOT EXISTS " + TABLE_TOTAL_VISIT_COUNT
            + " ("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + KEY_VISIT_DATE + " VARCHAR,"
            + KEY_EMP_CD + " VARCHAR,"
            + KEY_SMART_CAMERA_COUNTER + " INTEGER,"
            + KEY_SUPER_FAST_PROCESSOR_COUNTER + " INTEGER,"
            + KEY_U_NOTCH_DISPLAY_COUNTER + " INTEGER,"
            + KEY_TURBO_POWER_CHARGING_COUNTER + " INTEGER)";

}
