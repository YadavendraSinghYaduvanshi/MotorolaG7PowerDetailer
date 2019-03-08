package com.cpm.motoroladetailer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.cpm.motoroladetailer.Constant.CommonString;
import com.cpm.motoroladetailer.GetterSetter.VisitCountGetterSetter;

/**
 * Created by neeraj on 21-04-2018.
 */

public class MotorolaDetailerDB extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MotorolaDetailer_DB";
    public static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;
    Context context;

    public MotorolaDetailerDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public void open() {
        try {
            db = this.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CommonString.CREATE_TABLE_TOTAL_VISIT_COUNT);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public int createtable(String sqltext) {
        try {
            db.execSQL(sqltext);
            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public boolean insertVisitCountData(String visit_date, int count, int index, String emp_id) {
        ContentValues values = new ContentValues();
        try {
                values.put(CommonString.KEY_EMP_CD, emp_id);
                values.put(CommonString.KEY_VISIT_DATE,visit_date);
                if(index == 1){
                    values.put(CommonString.KEY_SMART_CAMERA_COUNTER,count);
                }else if(index == 2){
                    values.put(CommonString.KEY_SUPER_FAST_PROCESSOR_COUNTER,count);
                }else if(index == 3){
                    values.put(CommonString.KEY_U_NOTCH_DISPLAY_COUNTER,count);
                }else {
                    values.put(CommonString.KEY_TURBO_POWER_CHARGING_COUNTER,count);
                }
                long id = db.insert(CommonString.TABLE_TOTAL_VISIT_COUNT, null, values);
                if (id == -1) {
                    throw new Exception();
                }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("Database Exception  ", ex.toString());
            return false;
        }
    }

    public VisitCountGetterSetter getInsertedVisitCountData(String emp_id, String visit_date) {
        VisitCountGetterSetter countObj = new VisitCountGetterSetter();
        Cursor dbcursor = null;
        try {
            dbcursor = db.rawQuery("Select * from " + CommonString.TABLE_TOTAL_VISIT_COUNT + " Where "+CommonString.KEY_EMP_CD+" = '" + emp_id + "' and "+CommonString.KEY_VISIT_DATE+" = '" + visit_date+ "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {

                    countObj.setEmp_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_EMP_CD)));
                    countObj.setSmart_camera_count(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_SMART_CAMERA_COUNTER)));
                    countObj.setSuper_fast_processor_count(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_SUPER_FAST_PROCESSOR_COUNTER)));
                    countObj.setU_notch_display_count(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_U_NOTCH_DISPLAY_COUNTER)));
                    countObj.setTurbo_power_charging_count(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_TURBO_POWER_CHARGING_COUNTER)));
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return countObj;
            }
        } catch (Exception e) {
            return countObj;
        }
        return countObj;
    }

    public boolean updateVisitCountData(String visit_date, String emp_id, int index, int count) {
        boolean flag = false;
        ContentValues values = new ContentValues();
        try {
            if(index == 1) {
                values.put(CommonString.KEY_SMART_CAMERA_COUNTER, count);
            }else if(index == 2){
                values.put(CommonString.KEY_SUPER_FAST_PROCESSOR_COUNTER, count);
            } else if(index == 3){
                values.put(CommonString.KEY_U_NOTCH_DISPLAY_COUNTER, count);
            }else {
                values.put(CommonString.KEY_TURBO_POWER_CHARGING_COUNTER, count);
            }
            long id = db.update(CommonString.TABLE_TOTAL_VISIT_COUNT, values, CommonString.KEY_EMP_CD + " = '" + emp_id + "' and " +CommonString.KEY_VISIT_DATE+ " = '"+visit_date+"'", null);
            if(id >0){
                flag =  true;
            }
        } catch (Exception ex) {
            Log.e("Exception", "checkOut Journey_Plan" + ex.toString());
            flag  = false;
        }
        return flag;
    }
}

//    public boolean insertSubTeamListData(SupTeamListGetterSetter supTeamListdata) {
//        db.delete("Sup_Team_List", null, null);
//        ContentValues values = new ContentValues();
//        List<SupTeamList> data = supTeamListdata.getSupTeamList();
//        try {
//            if (data.size() == 0) {
//                return false;
//            }
//
//            for (int i = 0; i < data.size(); i++) {
//                values.put("Emp_Id", data.get(i).getEmpId());
//                values.put("Merchandiser", data.get(i).getMerchandiser());
//                values.put("Today_PJP", data.get(i).getTodayPJP());
//                values.put("UserId", data.get(i).getUserId());
//                values.put("Visit_Date", data.get(i).getVisitDate());
//                values.put("Att_Status", data.get(i).getAttStatus());
//                values.put("Sup_Att", data.get(i).getSupAtt());
//                values.put("Tool_Kit_Status", data.get(i).getToolKitStatus());
//                long id = db.insert("Sup_Team_List", null, values);
//                if (id == -1) {
//                    throw new Exception();
//                }
//            }
//            return true;
//        } catch (Exception ex) {
//            Crashlytics.logException(ex);
//            ex.printStackTrace();
//            Log.d("Database Exception  ", ex.toString());
//            return false;
//        }
//    }
//
//
//    public boolean insertNonWorkingResionData(SupNonWorkingReasonGetterSetter nonWorkingdata) {
//        db.delete("Sup_Non_Working_Reason", null, null);
//        ContentValues values = new ContentValues();
//        List<SupNonWorkingReason> data = nonWorkingdata.getSupNonWorkingReason();
//        try {
//            if (data.size() == 0) {
//                return false;
//            }
//
//            for (int i = 0; i < data.size(); i++) {
//
//                values.put("Reason_Id", data.get(i).getReasonId());
//                values.put("Reason", data.get(i).getReason());
//                values.put("Entry_Allow", data.get(i).getEntryAllow());
//                values.put("Image_Allow", data.get(i).getImageAllow());
//                values.put("GPS_Mandatory", data.get(i).getGPSMandatory());
//                values.put("For_Att", data.get(i).getForatt());
//                values.put("Remark", data.get(i).getRemark());
//
//                long id = db.insert("Sup_Non_Working_Reason", null, values);
//                if (id == -1) {
//                    throw new Exception();
//                }
//            }
//            return true;
//        } catch (Exception ex) {
//            Crashlytics.logException(ex);
//            ex.printStackTrace();
//            Log.d("Database Exception  ", ex.toString());
//            return false;
//        }
//    }
//
//    public boolean insertNonWorkingSubReasonData(SupNonWorkingSubReasonGetterSetter nonWorkingsubdata) {
//        db.delete("Sup_non_working_sub_reason", null, null);
//        ContentValues values = new ContentValues();
//
//        List<SupNonWorkingSubReason> data = nonWorkingsubdata.getSupNonWorkingSubReason();
//        try {
//            if (data.size() == 0) {
//                return false;
//            }
//
//            for (int i = 0; i < data.size(); i++) {
//
//                values.put("Sub_Reason_Id", data.get(i).getSubReasonId());
//                values.put("Sub_Reason", data.get(i).getSubReason());
//                values.put("Reason_Id", data.get(i).getReasonId());
//
//                long id = db.insert("Sup_non_working_sub_reason", null, values);
//                if (id == -1) {
//                    throw new Exception();
//                }
//
//            }
//            return true;
//        } catch (Exception ex) {
//            Crashlytics.logException(ex);
//            ex.printStackTrace();
//            Log.d("Database non_working_  ", ex.toString());
//            return false;
//        }
//
//    }
//
//    public boolean insertMappingWindosData(SupMappingWindowReasonGetterSetter mappingWindowsdata) {
//        db.delete("Sup_Mapping_Window", null, null);
//        ContentValues values = new ContentValues();
//
//        List<SupMappingWindow> data = mappingWindowsdata.getSupMappingWindow();
//        try {
//            if (data.size() == 0) {
//                return false;
//            }
//
//            for (int i = 0; i < data.size(); i++) {
//
//                values.put("Brand_Group_Id", data.get(i).getBrandGroupId());
//                values.put("Classification_Id", data.get(i).getClassificationId());
//                values.put("Image_Path", data.get(i).getImagePath());
//                values.put("Planogram_Image", data.get(i).getPlanogramImage());
//                values.put("Store_Category_Id", data.get(i).getStoreCategoryId());
//                values.put("Store_Type_Id", data.get(i).getStoreTypeId());
//                values.put("Tier_Id", data.get(i).getTierId());
//                values.put("Trade_Area_Id", data.get(i).getTradeAreaId());
//                values.put("Window_Id", data.get(i).getWindowId());
//
//                long id = db.insert("Sup_Mapping_Window", null, values);
//                if (id == -1) {
//                    throw new Exception();
//                }
//
//            }
//            return true;
//        } catch (Exception ex) {
//            Crashlytics.logException(ex);
//            ex.printStackTrace();
//            Log.d("Database non_working_  ", ex.toString());
//            return false;
//        }
//
//    }
//
//    public boolean insertDisplayTermMasterData(SupDisplayTermMasterGetterSetter displayTermMasterdata) {
//        db.delete("Sup_Display_Term_Master", null, null);
//        ContentValues values = new ContentValues();
//
//        List<SupDisplayTermMaster> data = displayTermMasterdata.getSupDisplayTermMaster();
//        try {
//            if (data.size() == 0) {
//                return false;
//            }
//
//            for (int i = 0; i < data.size(); i++) {
//
//                values.put("Display_Term", data.get(i).getDisplayTerm());
//                values.put("Display_Term_Id", data.get(i).getDisplayTermId());
//
//
//                long id = db.insert("Sup_Display_Term_Master", null, values);
//                if (id == -1) {
//                    throw new Exception();
//                }
//
//            }
//            return true;
//        } catch (Exception ex) {
//            Crashlytics.logException(ex);
//            ex.printStackTrace();
//            Log.d("Database non_working_  ", ex.toString());
//            return false;
//        }
//
//    }
//
//    public boolean insertWindowLocationData(SupWindowLocationGetterSetter supWindowLocationdata) {
//        db.delete("Sup_Window_Location", null, null);
//        ContentValues values = new ContentValues();
//
//        List<SupWindowLocation> data = supWindowLocationdata.getSupWindowLocation();
//        try {
//            if (data.size() == 0) {
//                return false;
//            }
//
//            for (int i = 0; i < data.size(); i++) {
//
//                values.put("Secondary_Window", data.get(i).getSecondaryWindow());
//                values.put("SelfService", data.get(i).getSelfService());
//                values.put("Touch_Point", data.get(i).getTouchPoint());
//                values.put("Window_Location", data.get(i).getWindowLocation());
//                values.put("Window_Location_Id", data.get(i).getWindowLocationId());
//
//                long id = db.insert("Sup_Window_Location", null, values);
//                if (id == -1) {
//                    throw new Exception();
//                }
//
//            }
//            return true;
//        } catch (Exception ex) {
//            Crashlytics.logException(ex);
//            ex.printStackTrace();
//            Log.d("Database non_working_  ", ex.toString());
//            return false;
//        }
//
//    }
//
//
//    public boolean insertMaterialMasterMasterData(SupMaterialMasterGetterSetter materialMasterMasterdata) {
//        db.delete("Sup_Material_Master", null, null);
//        ContentValues values = new ContentValues();
//
//        List<SupMaterialMaster> data = materialMasterMasterdata.getSupMaterialMaster();
//        try {
//            if (data.size() == 0) {
//                return false;
//            }
//
//            for (int i = 0; i < data.size(); i++) {
//
//                values.put("Material", data.get(i).getMaterial());
//                values.put("Material_Id", data.get(i).getMaterialId());
//
//
//                long id = db.insert("Sup_Material_Master", null, values);
//                if (id == -1) {
//                    throw new Exception();
//                }
//
//            }
//            return true;
//        } catch (Exception ex) {
//            Crashlytics.logException(ex);
//            ex.printStackTrace();
//            Log.d("Database non_working_  ", ex.toString());
//            return false;
//        }
//
//    }
//
//    public boolean insertWindowChecklistData(WindowChecklistGetterSetter windowChecklistdata) {
//        db.delete("Window_Checklist", null, null);
//        ContentValues values = new ContentValues();
//
//        List<WindowChecklist> data = windowChecklistdata.getWindowChecklist();
//        try {
//            if (data.size() == 0) {
//                return false;
//            }
//
//            for (int i = 0; i < data.size(); i++) {
//
//                values.put("Checklist", data.get(i).getChecklist());
//                values.put("Checklist_Id", data.get(i).getChecklistId());
//                values.put("Checklist_Sequence", data.get(i).getChecklistSequence());
//
//
//                long id = db.insert("Window_Checklist", null, values);
//                if (id == -1) {
//                    throw new Exception();
//                }
//
//            }
//            return true;
//        } catch (Exception ex) {
//            Crashlytics.logException(ex);
//            ex.printStackTrace();
//            Log.d("Database non_working_  ", ex.toString());
//            return false;
//        }
//
//    }
//
//    public boolean insertJounryCallCycleData(SupJourneyCallCycleGetterSetter teamlistdata) {
//        db.delete("Sup_Journey_Call_Cycle", null, null);
//        ContentValues values = new ContentValues();
//
//        List<SupJourneyCallCycle> data = teamlistdata.getSupJourneyCallCycle();
//        try {
//            if (data.size() == 0) {
//                return false;
//            }
//
//            for (int i = 0; i < data.size(); i++) {
//
//                values.put("Emp_Id", data.get(i).getEmpId());
//                values.put("Visit_Date", data.get(i).getVisitDate());
//
//                long id = db.insert("Sup_Journey_Call_Cycle", null, values);
//                if (id == -1) {
//                    throw new Exception();
//                }
//
//            }
//            return true;
//        } catch (Exception ex) {
//            Crashlytics.logException(ex);
//            ex.printStackTrace();
//            Log.d("Database call cycle  ", ex.toString());
//            return false;
//        }
//    }
//
//    // this inserted visited date used for shows message
//    // is data downloaded for current date or not.
//
//    public boolean insertLoginVisitedDate(SupJourneyCallCycleGetterSetter teamlistdata) {
//        db.delete("LOGIN_VISITED_DATE", null, null);
//        ContentValues values = new ContentValues();
//
//        List<SupJourneyCallCycle> data = teamlistdata.getSupJourneyCallCycle();
//        try {
//            if (data.size() == 0) {
//                return false;
//            }
//
//            values.put(CommonString.KEY_VISIT_DATE, data.get(0).getVisitDate());
//
//            long id = db.insert("LOGIN_VISITED_DATE", null, values);
//            if (id == -1) {
//                throw new Exception();
//            }
//            return true;
//        } catch (Exception ex) {
//            Crashlytics.logException(ex);
//            ex.printStackTrace();
//            Log.d("Database call cycle ", ex.toString());
//            return false;
//        }
//    }
//
//
//    // this inserted visited date used for shows message
//    // is data downloaded for current date or not.
//
//    public boolean insertSupTeamListVisitedDate(SupTeamListGetterSetter supTeamListdata) {
//
//        db.delete("LOGIN_VISITED_DATE", null, null);
//        ContentValues values = new ContentValues();
//
//        List<SupTeamList> data = supTeamListdata.getSupTeamList();
//
//        try {
//            if (data.size() == 0) {
//                return false;
//            }
//
//            values.put(CommonString.KEY_VISIT_DATE, data.get(0).getVisitDate());
//            long id = db.insert("LOGIN_VISITED_DATE", null, values);
//            if (id == -1) {
//                throw new Exception();
//            }
//            return true;
//        } catch (Exception ex) {
//            Crashlytics.logException(ex);
//            ex.printStackTrace();
//            Log.d("Database sup team list", ex.toString());
//            return false;
//        }
//    }
//
//
//    public ArrayList<SupWindowLocation> getWindowLocationData() {
//
//        Log.d("FetchingStoredata-----", "------------------");
//        ArrayList<SupWindowLocation> list = new ArrayList<SupWindowLocation>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("SELECT  * from " + "Sup_Window_Location WHERE Secondary_Window= 1", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    SupWindowLocation sb = new SupWindowLocation();
//                    sb.setSecondaryWindow(Boolean.valueOf((dbcursor.getString(dbcursor.getColumnIndexOrThrow("Secondary_Window")))));
//                    sb.setSelfService(Boolean.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow("SelfService"))));
//                    sb.setTouchPoint(Boolean.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Touch_Point"))));
//                    sb.setWindowLocation(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Window_Location")));
//                    int value = dbcursor.getInt(dbcursor.getColumnIndexOrThrow("Window_Location_Id"));
//
//                    sb.setWindowLocationId(value);
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Crashlytics.logException(e);
//            Log.d("Exception when fetching",
//                    e.toString());
//            return list;
//        }
//        return list;
//    }
//
//
//    public long insertAttendenceData(MyAttendenceBean data) {
//
//        ContentValues values = new ContentValues();
//        try {
//
//            db.delete(CommonString.TABLE_ATTENDENCE_DATA, CommonString.KEY_VISIT_DATE
//                    + "='" + data.getVisitDate() + "'", null);
//
//            values.put(CommonString.KEY_DISTRIBUTOR_ID, "");
//            values.put(CommonString.KEY_USER_ID, data.getUserId());
//            values.put(CommonString.KEY_IN_TIME, data.getIntme());
//            values.put(CommonString.KEY_OUT_TIME, data.getOutTime());
//            values.put(CommonString.KEY_VISIT_DATE, data.getVisitDate());
//            values.put(CommonString.KEY_LATITUDE, data.getLat());
//            values.put(CommonString.KEY_LONGITUDE, data.getLon());
//            values.put(CommonString.KEY_STATUS, data.getStatus());
//            values.put(CommonString.KEY_IMAGE_SELFIE, data.getImage());
//            values.put(CommonString.KEY_REASON, data.getReasonid());
//            values.put(CommonString.KEY_REASON_ID, data.getReason());
//            values.put(CommonString.KEY_REMARK, data.getRemark());
//            values.put(CommonString.KEY_ENTRYALLOW, data.getEntryallow());
//
//            return db.insert(CommonString.TABLE_ATTENDENCE_DATA, null, values);
//
//
//        } catch (Exception ex) {
//            Crashlytics.logException(ex);
//            Log.d("Database Exception ", ex.getMessage());
//        }
//        return 0;
//    }
//
//    public ArrayList<MyAttendenceBean> getAttendenceData(String string, String visitdate) {
//
//        Log.d("FetchingStoredata-", "------------------");
//        ArrayList<MyAttendenceBean> list = new ArrayList<MyAttendenceBean>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("SELECT  * from  " + CommonString.TABLE_ATTENDENCE_DATA + " where "
//                            + CommonString.KEY_USER_ID + " = '" + string + "'" + " AND " + CommonString.KEY_VISIT_DATE + "='" + visitdate + "'",
//
//                    null, null);
//
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//
//                    MyAttendenceBean sb = new MyAttendenceBean();
//                    sb.setDistrbutorId(dbcursor.getString(dbcursor.getColumnIndexOrThrow("DISTRIBUTOR_ID")));
//                    sb.setUserId(dbcursor.getString(dbcursor.getColumnIndexOrThrow("USER_ID")));
//                    sb.setIntme(dbcursor.getString(dbcursor.getColumnIndexOrThrow("IN_TIME")));
//                    sb.setOutTime(dbcursor.getString(dbcursor.getColumnIndexOrThrow("OUT_TIME")));
//                    sb.setLat(dbcursor.getString(dbcursor.getColumnIndexOrThrow("LATITUDE")));
//                    sb.setLon(dbcursor.getString(dbcursor.getColumnIndexOrThrow("LONGITUDE")));
//                    sb.setStatus(dbcursor.getString(dbcursor.getColumnIndexOrThrow("STATUS")));
//                    sb.setReasonid(dbcursor.getString(dbcursor.getColumnIndexOrThrow("REASON")));
//                    sb.setImage(dbcursor.getString(dbcursor.getColumnIndexOrThrow("IMAGE_SELFIE")));
//                    sb.setVisitDate(dbcursor.getString(dbcursor.getColumnIndexOrThrow("VISIT_DATE")));
//                    sb.setRemark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("REMARK")));
//                    sb.setEntryallow(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ENTRYALLOW")));
//                    sb.setReason(dbcursor.getString(dbcursor.getColumnIndexOrThrow("REASON_ID")));
//
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Crashlytics.logException(e);
//            Log.d("Exception when fetchi", e.toString());
//            return list;
//        }
//
//        Log.d("FetchingStoredat", "-----------------");
//        return list;
//
//    }
//
//    public ArrayList<SupNonWorkingReason> GetMyAttendance(String status) {
//
//        String reason="",reasonId= "";
//        ArrayList<SupNonWorkingReason> list = new ArrayList<SupNonWorkingReason>();
//        Cursor dbcursor = null;
//        try {
//
//            dbcursor = db.rawQuery("SELECT  DISTINCT * from Sup_Non_Working_Reason where For_Att=1", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    SupNonWorkingReason sb = new SupNonWorkingReason();
//                    reason = dbcursor.getString(dbcursor.getColumnIndexOrThrow("Reason"));
//                    reasonId = dbcursor.getString(dbcursor.getColumnIndexOrThrow("Reason_Id"));
//
//                    // if status 1 team attendence status data getting
//                    // else my attendance data getting
//
//                    if(status.equalsIgnoreCase("1")) {
//                        if (!reason.equalsIgnoreCase("Present") || !reasonId.equalsIgnoreCase("13")) {
//                            sb.setReason(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Reason")));
//                            sb.setReasonId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Reason_Id"))));
//                            sb.setEntryAllow("1".equalsIgnoreCase(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Entry_Allow"))));
//                            sb.setImageAllow("1".equalsIgnoreCase(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Image_Allow"))));
//                            sb.setGPSMandatory("1".equalsIgnoreCase(dbcursor.getString(dbcursor.getColumnIndexOrThrow("GPS_Mandatory"))));
//                            sb.setForatt("1".equalsIgnoreCase(dbcursor.getString(dbcursor.getColumnIndexOrThrow("For_Att"))));
//                            sb.setRemark("1".equalsIgnoreCase(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Remark"))));
//                            list.add(sb);
//                        }
//                    }else{
//                        sb.setReason(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Reason")));
//                        sb.setReasonId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Reason_Id"))));
//                        sb.setEntryAllow("1".equalsIgnoreCase(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Entry_Allow"))));
//                        sb.setImageAllow("1".equalsIgnoreCase(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Image_Allow"))));
//                        sb.setGPSMandatory("1".equalsIgnoreCase(dbcursor.getString(dbcursor.getColumnIndexOrThrow("GPS_Mandatory"))));
//                        sb.setForatt("1".equalsIgnoreCase(dbcursor.getString(dbcursor.getColumnIndexOrThrow("For_Att"))));
//                        sb.setRemark("1".equalsIgnoreCase(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Remark"))));
//                        list.add(sb);
//                    }
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Crashlytics.logException(e);
//            Log.d("Exception when fetc", e.toString());
//            return list;
//        }
//
//        Log.d("FetchingStore", "-------------------");
//        return list;
//
//    }
//
//
//    public boolean isJournyCallCyclesDataFilled(String date) {
//        boolean filled = false;
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("SELECT * FROM Sup_Journey_Call_Cycle where Visit_Date = '" + date + "' ", null);
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                int icount = dbcursor.getInt(0);
//                dbcursor.close();
//                if (icount > 0) {
//                    filled = true;
//                } else {
//                    filled = false;
//                }
//            }
//        } catch (Exception e) {
////            Crashlytics.logException(e);
//            Log.d("Exception ", " when fetching Records!!!!!!!!!!!!!!!!!!!!! " + e.toString());
//            return filled;
//        }
//        return filled;
//    }
//
//    public boolean isteamAttendanceMTDDataFilled() {
//        boolean filled = false;
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("SELECT * FROM Sup_Team_Attendance_mtd ", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                int icount = dbcursor.getInt(0);
//                dbcursor.close();
//                if (icount > 0) {
//                    filled = true;
//                } else {
//                    filled = false;
//                }
//            }
//        } catch (Exception e) {
//            Crashlytics.logException(e);
//            Log.d("Exception ", " when fetching Records!!!!!!!!!!!!!!!!!!!!! " + e.toString());
//            return filled;
//        }
//        return filled;
//    }
//
//    public ArrayList<SupJourneyCallCycle> GetjounryCallCycle(String flag) {
//
//        ArrayList<SupJourneyCallCycle> list = new ArrayList<SupJourneyCallCycle>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("SELECT DISTINCT  SD.Emp_Id AS Emp_Id, SD.Merchandiser AS Merchandiser, CD.Visit_Date AS Visit_Date" +
//                    " FROM Sup_Journey_Call_Cycle CD INNER JOIN Sup_Team_List SD ON CD.Emp_Id = SD.Emp_Id  WHERE SD.Today_PJP = " + flag + " order by SD.Merchandiser", null);
//
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//
//                    SupJourneyCallCycle sb = new SupJourneyCallCycle();
//
//                    sb.setEmpId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Emp_Id"))));
//                    sb.setMerchandiser(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Merchandiser")));
//                    sb.setVisitDate(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Visit_Date")));
//
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Crashlytics.logException(e);
//            Log.d("Exception when fetc", e.toString());
//            return list;
//        }
//
//        Log.d("FetchingStore", "-------------------");
//        return list;
//
//    }
//
//    public boolean insertJCPData(JCPGetterSetter jcpdata, String jounry_plan_today) {
//
//        ContentValues values = new ContentValues();
//
//        List<JourneyPlan> data = jcpdata.getSupJounryPlan();
//        try {
//            if (data.size() == 0) {
//                return false;
//            }
//
//            for (int i = 0; i < data.size(); i++) {
//
//                values.put("Address1", data.get(i).getAddress1());
//                values.put("Address2", data.get(i).getAddress2());
//                values.put("City", data.get(i).getCity());
//                values.put("City_Id", data.get(i).getCityId());
//                values.put("Classification  ", data.get(i).getClassification());
//                values.put("Classification_Id", data.get(i).getClassificationId());
//                values.put("Distributor_Id", data.get(i).getDistributorId());
//                values.put("Emp_Id", data.get(i).getEmpId());
//                values.put("Geo_Tag", data.get(i).getGeoTag());
//                values.put("Landmark", data.get(i).getLandmark());
//                values.put("Pincode", data.get(i).getPincode());
//                values.put("Reason_Id", data.get(i).getReasonId());
//                values.put("Remark", data.get(i).getRemark());
//                values.put("Store_Category", data.get(i).getStoreCategory());
//                values.put("Store_Category_Id", data.get(i).getStoreCategoryId());
//                values.put("Store_Id", data.get(i).getStoreId());
//                values.put("Store_Name", data.get(i).getStoreName());
//                values.put("Store_Type", data.get(i).getStoreType());
//                values.put("Store_Type_Id", data.get(i).getStoreTypeId());
//                values.put("Sub_Reason_Id", data.get(i).getSubReasonId());
//                values.put("Tier_Id", data.get(i).getTierId());
//                values.put("Trade_Area_Id", data.get(i).getTradeAreaId());
//                values.put("Upload_Status", data.get(i).getUploadStatus());
//                values.put("Visit_Date", data.get(i).getVisitDate());
//
//                long id = db.insert(jounry_plan_today, null, values);
//                if (id == -1) {
//                    throw new Exception();
//                }
//
//            }
//            return true;
//        } catch (Exception ex) {
//            Crashlytics.logException(ex);
//            ex.printStackTrace();
//            Log.d("Db Sup Jounry PlanToday", ex.toString());
//            return false;
//        }
//
//    }
//
//    public boolean insertJCPDataPrevious(JCPGetterSetterPrevious jcpdata, String jounry_plan_previous_day) {
//
//        ContentValues values = new ContentValues();
//
//        List<JourneyPlan> data = jcpdata.getSupJounryPlan();
//        try {
//            if (data.size() == 0) {
//                return false;
//            }
//
//            for (int i = 0; i < data.size(); i++) {
//
//                values.put("Address1", data.get(i).getAddress1());
//                values.put("Address2", data.get(i).getAddress2());
//                values.put("City", data.get(i).getCity());
//                values.put("City_Id", data.get(i).getCityId());
//                values.put("Classification  ", data.get(i).getClassification());
//                values.put("Classification_Id", data.get(i).getClassificationId());
//                values.put("Distributor_Id", data.get(i).getDistributorId());
//                values.put("Emp_Id", data.get(i).getEmpId());
//                values.put("Geo_Tag", data.get(i).getGeoTag());
//                values.put("Landmark", data.get(i).getLandmark());
//                values.put("Pincode", data.get(i).getPincode());
//                values.put("Reason_Id", data.get(i).getReasonId());
//                values.put("Remark", data.get(i).getRemark());
//                values.put("Store_Category", data.get(i).getStoreCategory());
//                values.put("Store_Category_Id", data.get(i).getStoreCategoryId());
//                values.put("Store_Id", data.get(i).getStoreId());
//                values.put("Store_Name", data.get(i).getStoreName());
//                values.put("Store_Type", data.get(i).getStoreType());
//                values.put("Store_Type_Id", data.get(i).getStoreTypeId());
//                values.put("Sub_Reason_Id", data.get(i).getSubReasonId());
//                values.put("Tier_Id", data.get(i).getTierId());
//                values.put("Trade_Area_Id", data.get(i).getTradeAreaId());
//                values.put("Upload_Status", data.get(i).getUploadStatus());
//                values.put("Visit_Date", data.get(i).getVisitDate());
//                values.put(CommonString.KEY_MER_MID, data.get(i).getMerchandiserMID());
//                values.put(CommonString.Mer_Reason, data.get(i).getMerReason());
//                values.put(CommonString.Mer_Sub_Reason, data.get(i).getMerSubReason());
//
//                long id = db.insert(jounry_plan_previous_day, null, values);
//                if (id == -1) {
//                    throw new Exception();
//                }
//
//            }
//            return true;
//        } catch (Exception ex) {
//            Crashlytics.logException(ex);
//            ex.printStackTrace();
//            Log.d("Db Sup Jounry PlanToday", ex.toString());
//            return false;
//        }
//
//    }
//
//    public ArrayList<JourneyPlan> getStoreData(String visit_date, String empId, String outlet_today, String today_pjp) {
//
//        ArrayList<JourneyPlan> list = new ArrayList<JourneyPlan>();
//        Cursor dbcursor = null;
//
//
//        try {
//
//            dbcursor = db.rawQuery("SELECT distinct * from  " + outlet_today + " as jp inner join Sup_Team_List as stl on stl.Emp_Id = jp.Emp_Id where jp.Visit_Date ='" + visit_date + "' and jp.Emp_Id = " + empId + " and stl.Today_PJP = '" + today_pjp + "' order by jp.Store_Name", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//
//                    JourneyPlan sb = new JourneyPlan();
//                    sb.setEmpId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Emp_Id"))));
//                    sb.setAddress1(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Address1")));
//                    sb.setAddress2(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Address2")));
//                    sb.setCity(dbcursor.getString(dbcursor.getColumnIndexOrThrow("City")));
//                    sb.setCityId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("City_Id"))));
//                    sb.setClassification(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Classification")));
//                    sb.setClassificationId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Classification_Id"))));
//                    sb.setDistributorId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Distributor_Id"))));
//                    sb.setEmpId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Emp_Id"))));
//                    sb.setGeoTag(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Geo_Tag")));
//                    sb.setLandmark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Landmark")));
//                    sb.setPincode(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Pincode")));
//                    sb.setReasonId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Reason_Id"))));
//                    sb.setRemark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Remark")));
//                    sb.setStoreCategory(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Category")));
//                    sb.setStoreCategoryId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Category_Id"))));
//                    sb.setStoreId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Id"))));
//                    sb.setStoreName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Name")));
//                    sb.setStoreType(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Type")));
//                    sb.setStoreTypeId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Type_Id"))));
//                    sb.setSubReasonId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Sub_Reason_Id"))));
//                    sb.setTierId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Tier_Id"))));
//                    sb.setTradeAreaId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Trade_Area_Id"))));
//                    sb.setUploadStatus(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Upload_Status")));
//                    sb.setVisitDate(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Visit_Date")));
//
//                    if (outlet_today.equalsIgnoreCase("Sup_Jounry_Plan_Previous")) {
//                        sb.setMerchandiserMID(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_MER_MID))));
//                        sb.setMerReason(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Mer_Reason)));
//                        sb.setMerSubReason(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Mer_Sub_Reason)));
//                    } else {
//                        sb.setMerchandiserMID(0);
//                        sb.setMerReason("");
//                        sb.setMerSubReason("");
//                    }
//
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Crashlytics.logException(e);
//            Log.d("Exception when fetc", e.toString());
//            return list;
//        }
//
//        Log.d("FetchingStore", "-------------------");
//        return list;
//
//    }
//
//
//    public long InsertCoverageData(CoverageBean data) {
//        db.delete(CommonString.TABLE_COVERAGE_DATA, "STORE_ID" + "='" + data.getStoreId() + "'", null);
//        ContentValues values = new ContentValues();
//        long id = 0;
//        try {
//            values.put(CommonString.KEY_STORE_ID, data.getStoreId());
//            values.put(CommonString.KEY_VISIT_DATE, data.getVisitDate());
//            values.put(CommonString.KEY_LATITUDE, data.getLatitude());
//            values.put(CommonString.KEY_LONGITUDE, data.getLongitude());
//            values.put(CommonString.KEY_IMAGE, data.getImage());
//            values.put(CommonString.KEY_COVERAGE_REMARK, data.getRemark());
//            values.put(CommonString.KEY_REASON_ID, data.getReasonid());
//            values.put(CommonString.KEY_REASON, data.getReason());
//            values.put(CommonString.KEY_SUB_REASON_ID, data.getSub_reasonId());
//            values.put(CommonString.KEY_UPLOAD_STATUS, data.getUploadStatus());
//            values.put(CommonString.KEY_TYPE, data.getCoverage_type());
//            values.put(CommonString.KEY_CHECK_IN_IMAGE, data.getImage());
//            values.put(CommonString.KEY_MERCHENDISER_ID, data.getMerId());
//            values.put(CommonString.KEY_MER_MID, data.getMerchandiser_MID());
//
//            // values.put(CommonString.KEY_MERCHENDISER_ID,data());
//
//            id = db.insert(CommonString.TABLE_COVERAGE_DATA, null, values);
//            if (id > 0) {
//                return id;
//            } else {
//                return 0;
//            }
//        } catch (Exception ex) {
//            Log.d("Database Exception ", ex.toString());
//            return 0;
//        }
//
//    }
//
//    /*Merchandiser rating code @Author Neeraj 17-04-08 start*/
//
//    public long insertMerchantdiserRating(int rating, String userId, Integer storeId, Integer empId, String visitDate) {
//        db.delete("Merchandiser_Rating", "Store_Id" + "='" + storeId + "'", null);
//        ContentValues values = new ContentValues();
//        long id = 0;
//        try {
//            values.put(CommonString.KEY_STORE_ID, storeId);
//            values.put(CommonString.KEY_USER_ID, userId);
//            values.put(CommonString.KEY_MERCHENDISER_ID, empId);
//            values.put(CommonString.KEY_VISIT_DATE, visitDate);
//            values.put(CommonString.KEY_RATING, rating);
//
//            id = db.insert("Merchandiser_Rating", null, values);
//            if (id > 0) {
//                return id;
//            } else {
//                return 0;
//            }
//        } catch (Exception ex) {
//            Crashlytics.logException(ex);
//            ex.printStackTrace();
//            return 0;
//        }
//    }
//
//    public long updateUploadStatus(String id, String status, String table) {
//
//        ContentValues values = new ContentValues();
//        try {
//            values.put("Upload_Status", status);
//            return db.update(table, values, "Store_Id " + " = '" + id + "'", null);
//        } catch (Exception ex) {
//            Log.e("Exception", "checkOut Journey_Plan" + ex.toString());
//            return 0;
//        }
//    }
//
//
//    public boolean insertPosmMasterData(PosmMasterGetterSetter data) {
//        db.delete("Sup_Posm_Master", null, null);
//        List<PosmMaster> list = data.getPosmMaster();
//        ContentValues values = new ContentValues();
//        try {
//            if (list.size() == 0) {
//                return false;
//            }
//            for (int i = 0; i < list.size(); i++) {
//                values.put("Posm_Id", list.get(i).getPosmId());
//                values.put("Posm", list.get(i).getPosm());
//                long id = db.insert("Sup_Posm_Master", null, values);
//                if (id == -1) {
//                    throw new Exception();
//                }
//            }
//            return true;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            Log.d("Exception ", " in Sup_Posm_Master " + ex.toString());
//            return false;
//        }
//    }
//
//    public ArrayList<RatingGetterSetter> getMerchandiserRating(Integer storeId, String visitDate) {
//        ArrayList<RatingGetterSetter> list = new ArrayList<>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("SELECT  * from " + CommonString.Table_Merchandiser_Rating + " where " + CommonString.KEY_STORE_ID + "=" + storeId + " AND " + CommonString.KEY_VISIT_DATE + "='" + visitDate + "'",
//                    null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    RatingGetterSetter rgs = new RatingGetterSetter();
//
//                    rgs.setMerId(dbcursor.getInt(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_MERCHENDISER_ID)));
//                    rgs.setRating(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_RATING)));
//                    rgs.setUserId(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_USER_ID)));
//
//                    list.add(rgs);
//                    dbcursor.moveToNext();
//                }
//
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception get JCP!", e.toString());
//            return list;
//        }
//        return list;
//    }
//
//
//    public ArrayList<PrimarySelfData> getPrimarySelfFinalData(Integer storeId) {
//        Log.d("FetchingStored", "------------------");
//        ArrayList<PrimarySelfData> list = new ArrayList<>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("SELECT  * from "
//                    + CommonString.TABLE_SUP_PRIMARY_SELF + " where "
//                    + CommonString.KEY_STORE_ID + " = '" + storeId + "'", null);
//
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    PrimarySelfData psd = new PrimarySelfData();
//                    psd.setStoreId(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_STORE_ID)));
//                    psd.setMer_MID(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_MER_MID)));
//                    psd.setSku(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_SKU)));
//                    psd.setSku_Id(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_SKU_ID)));
//                    psd.setBrand(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_BRAND)));
//                    psd.setBrand_Id(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Brand_Id)));
//                    psd.setStock_Quy(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_MER_STOCK_QUY)));
//                    psd.setSup_data(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_SUP_STOCK_QUY)));
//                    psd.setTap_roll_check(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_TAPE_ROLL_CHECK)));
//                    psd.setFifo_chk(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_FIFO_CHCEK)));
//
//                    list.add(psd);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception ", e.getMessage());
//            return list;
//        }
//        return list;
//    }
//
//
//    public long insertPrimarySelfFinalData(HashMap<PrimarySelfData, List<PrimarySelfData>> listDataChild, ArrayList<PrimarySelfData> listDataHeader, PrimarySelfData primarySpinnerData, Integer storeId, String visit_date) {
//        long id = 0;
//        db.delete("Sup_Primary_Self", "Store_Id" + "='" + storeId + "'", null);
//        ContentValues values = new ContentValues();
//
//        String brand_Id = "", brand_name = "";
//        try {
//          for (int i = 0; i < listDataHeader.size(); i++) {
//
//                brand_Id = String.valueOf(listDataHeader.get(i).getBrand_Id());
//                brand_name = listDataHeader.get(i).getBrand();
//
//                for (int j = 0; j < listDataChild.get(listDataHeader.get(i)).size(); j++) {
//                    values.put(CommonString.KEY_MER_STOCK_QUY, listDataChild.get(listDataHeader.get(i)).get(j).getStock_Quy());
//                    values.put(CommonString.KEY_STORE_ID, listDataChild.get(listDataHeader.get(i)).get(j).getStoreId());
//                    values.put(CommonString.KEY_SUP_STOCK_QUY, listDataChild.get(listDataHeader.get(i)).get(j).getSup_data());
//                    values.put(CommonString.KEY_SKU_ID, listDataChild.get(listDataHeader.get(i)).get(j).getSku_Id());
//                    values.put(CommonString.KEY_EMP_ID, listDataChild.get(listDataHeader.get(i)).get(j).getEmp_Id());
//                    values.put(CommonString.KEY_MER_MID, listDataChild.get(listDataHeader.get(i)).get(j).getMer_MID());
//                    values.put(CommonString.Brand_Id, brand_Id);
//                    values.put(CommonString.KEY_BRAND, brand_name);
//                    values.put(CommonString.KEY_VISIT_DATE, visit_date);
//                    values.put(CommonString.KEY_FIFO_CHCEK, primarySpinnerData.getFifo_chk());
//                    values.put(CommonString.KEY_TAPE_ROLL_CHECK, primarySpinnerData.getTap_roll_check());
//
//                    id = db.insert("Sup_Primary_Self", null, values);
//                }
//            }
//
//            if (id > 0) {
//                return id;
//            } else {
//                return 0;
//            }
//        } catch (Exception ex) {
//            Log.d("Database Exception ", ex.toString());
//            return 0;
//        }
//    }
//
//
//    public long updateMerchantdiserRating(int rating, Integer storeId, String visitDate) {
//        try {
//            ContentValues values = new ContentValues();
//            values.put(CommonString.KEY_RATING, rating);
//
//            return db.update(CommonString.Table_Merchandiser_Rating, values,
//                    CommonString.KEY_STORE_ID + "='" + storeId + "' and " + CommonString.KEY_VISIT_DATE + " = '" + visitDate + "'", null);
//
//        } catch (Exception e) {
//            Crashlytics.logException(e);
//            return 0;
//        }
//    }
//
//    public boolean insertPrimarySelfAuditData(SupPrimarySelfAuditGetterSetter primarySelfObj) {
//        //  db.delete("Sup_Primary_Shelf_Audit", null, null);
//        ContentValues values = new ContentValues();
//
//        List<SupPrimaryShelfAudit> data = primarySelfObj.getSupPrimaryShelfAudit();
//
//        try {
//            if (data.size() == 0) {
//                return false;
//            }
//
//            for (int i = 0; i < data.size(); i++) {
//                values.put("store_Id", data.get(i).getStoreId());
//                values.put("Merchandiser_MID", data.get(i).getMerchandiserMID());
//                values.put("Sku_Id", data.get(i).getSkuId());
//                values.put("Stock_Qty", data.get(i).getStockQty());
//                values.put("Emp_Id", data.get(i).getEmpId());
//                values.put("Visit_Date",data.get(i).getVisitDate());
//
//                long id = db.insert("Sup_Primary_Shelf_Audit", null, values);
//                if (id == -1) {
//                    throw new Exception();
//                }
//
//            }
//            return true;
//        } catch (Exception ex) {
//            Crashlytics.logException(ex);
//            ex.printStackTrace();
//            Log.d("Database Primary_Shelf", ex.toString());
//            return false;
//        }
//    }
//
//    public boolean insertUserTouchPointData(SupTouchpointAuditGetterSetter touchPointObj) {
//        //db.delete("Sup_Touchpoint_Audit", null, null);
//        ContentValues values = new ContentValues();
//
//        List<SupTouchpointAudit> data = touchPointObj.getSupTouchpointAudit();
//        try {
//            if (data.size() == 0) {
//                return false;
//            }
//
//            for (int i = 0; i < data.size(); i++) {
//
//                values.put("Brand_Id", data.get(i).getBrandId());
//                values.put("store_Id", data.get(i).getStoreId());
//                values.put("Merchandiser_MID", data.get(i).getMerchandiserMID());
//                values.put("Posm_Id", data.get(i).getPosmId());
//                values.put("Type", data.get(i).getType());
//                values.put("Emp_Id", data.get(i).getEmpId());
//                values.put("Posm_Qty", data.get(i).getPosmQty());
//                values.put("Visit_Date",data.get(i).getVisitDate());
//
//                long id = db.insert("Sup_Touchpoint_Audit", null, values);
//                if (id == -1) {
//                    throw new Exception();
//                }
//
//            }
//            return true;
//        } catch (Exception ex) {
//            Crashlytics.logException(ex);
//            ex.printStackTrace();
//            Log.d("Database Sup_", ex.toString());
//            return false;
//        }
//    }
//
//    public ArrayList<CoverageBean> getCoverageWithStoreID_Data(String store_id, String visitDate) {
//        ArrayList<CoverageBean> list = new ArrayList<CoverageBean>();
//        Cursor dbcursor = null;
//        try {
//
//
//            dbcursor = db.rawQuery("SELECT  * from " + CommonString.TABLE_COVERAGE_DATA + " where " + CommonString.KEY_STORE_ID + "='" + store_id + "' AND " + CommonString.KEY_VISIT_DATE + "='" + visitDate + "'",
//                    null);
//
//
//            if (dbcursor != null) {
//
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    CoverageBean sb = new CoverageBean();
//
//                    sb.setStoreId(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_STORE_ID)));
//                    sb.setVisitDate((((dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_VISIT_DATE))))));
//                    sb.setLatitude(((dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_LATITUDE)))));
//                    sb.setLongitude(((dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_LONGITUDE)))));
//                    sb.setImage((((dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_IMAGE))))));
//
//                    sb.setReasonid((((dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_REASON_ID))))));
//                    if (dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_COVERAGE_REMARK)) == null) {
//                        sb.setRemark("");
//                    } else {
//                        sb.setRemark((((dbcursor.getString(dbcursor
//                                .getColumnIndexOrThrow(CommonString.KEY_COVERAGE_REMARK))))));
//                    }
//                    sb.setReason((((dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_REASON))))));
//
//                    sb.setMID(Integer.parseInt(((dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_ID))))));
//
//
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//        } catch (Exception e) {
//            Log.d("Exception get JCP!", e.toString());
//            return list;
//        }
//        return list;
//    }
//
//    public ArrayList<JourneyPlan> getStoreDataForNonWorking(String visit_date, String outlet, String store_id) {
//        ArrayList<JourneyPlan> list = new ArrayList<JourneyPlan>();
//        Cursor dbcursor = null;
//
//        try {
//
//            dbcursor = db.rawQuery("SELECT  * from  " + outlet + " where Visit_Date ='" + visit_date + "' AND Store_Id ='" + store_id + "' ", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//
//                    JourneyPlan sb = new JourneyPlan();
//                    sb.setEmpId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Emp_Id"))));
//                    sb.setAddress1(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Address1")));
//                    sb.setAddress2(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Address2")));
//                    sb.setCity(dbcursor.getString(dbcursor.getColumnIndexOrThrow("City")));
//                    sb.setCityId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("City_Id"))));
//                    sb.setClassification(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Classification")));
//                    sb.setClassificationId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Classification_Id"))));
//                    sb.setDistributorId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Distributor_Id"))));
//                    sb.setEmpId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Emp_Id"))));
//                    sb.setGeoTag(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Geo_Tag")));
//                    sb.setLandmark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Landmark")));
//                    sb.setPincode(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Pincode")));
//                    sb.setReasonId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Reason_Id"))));
//                    sb.setRemark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Remark")));
//                    sb.setStoreCategory(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Category")));
//                    sb.setStoreCategoryId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Category_Id"))));
//                    sb.setStoreId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Id"))));
//                    sb.setStoreName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Name")));
//                    sb.setStoreType(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Type")));
//                    sb.setStoreTypeId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Type_Id"))));
//                    sb.setSubReasonId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Sub_Reason_Id"))));
//                    sb.setTierId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Tier_Id"))));
//                    sb.setTradeAreaId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Trade_Area_Id"))));
//                    sb.setUploadStatus(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Upload_Status")));
//                    sb.setVisitDate(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Visit_Date")));
//
//                    if (outlet.equalsIgnoreCase("Sup_Jounry_Plan_Previous")) {
//                        sb.setMerchandiserMID(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_MER_MID))));
//                        sb.setMerReason(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Mer_Reason)));
//                        sb.setMerSubReason(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Mer_Sub_Reason)));
//                    } else {
//                        sb.setMerchandiserMID(0);
//                        sb.setMerReason("");
//                        sb.setMerSubReason("");
//                    }
//
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Crashlytics.logException(e);
//            Log.d("Exception when fetc", e.toString());
//            return list;
//        }
//
//        Log.d("FetchingStore", "-------------------");
//        return list;
//    }
//
//
//    public long updateStoreStatusOnLeave(String storeid, String visitdate, String status, String outlet) {
//        long id = 0;
//        try {
//            ContentValues values = new ContentValues();
//            values.put("UPLOAD_STATUS", status);
//
//            id = db.update(outlet, values, CommonString.KEY_STORE_ID + "='" + storeid + "' AND "
//                    + CommonString.KEY_VISIT_DATE + "='" + visitdate
//                    + "'", null);
//
//            return id;
//        } catch (Exception e) {
//            return 0;
//        }
//    }
//
//
//    public void deleteTableWithStoreID(String storeid) {
//        db.delete(CommonString.TABLE_COVERAGE_DATA, CommonString.KEY_STORE_ID + "='" + storeid + "'", null);
//        db.delete(CommonString.Table_Merchandiser_Rating, CommonString.KEY_STORE_ID + "='" + storeid + "'", null);
//        db.delete(CommonString.TABLE_SECONDARY_BACKWALL_DATA, CommonString.KEY_STORE_ID + "='" + storeid + "'", null);
//        db.delete(CommonString.TABLE_SECONDARY_WINDOW_HEADER, CommonString.KEY_STORE_ID + "='" + storeid + "'", null);
//        db.delete(CommonString.TABLE_SECONDARY_WINDOW_CHILD, CommonString.KEY_STORE_ID + "='" + storeid + "'", null);
//        db.delete(CommonString.TABLE_POSM, CommonString.KEY_STORE_ID + "='" + storeid + "'", null);
//        db.delete(CommonString.TABLE_SUP_PRIMARY_SELF, CommonString.KEY_STORE_ID + "='" + storeid + "'", null);
//        // deleting self service data
//        db.delete(CommonString.TABLE_SS_POSM, CommonString.KEY_STORE_ID + "='" + storeid + "'", null);
//        db.delete(CommonString.TABLE_SECONDARY_DISPLAY, CommonString.KEY_STORE_ID + "='" + storeid + "'", null);
//        db.delete(CommonString.TABLE_PROMOTION, CommonString.KEY_STORE_ID + "='" + storeid + "'", null);
//        db.delete(CommonString.TABLE_COMPETITION, CommonString.KEY_STORE_ID + "='" + storeid + "'", null);
//        db.delete(CommonString.TABLE_PRIMARY_BAY, CommonString.KEY_STORE_ID + "='" + storeid + "'", null);
//        db.delete(CommonString.TABLE_PRIMARY_BAY_IMAGE, CommonString.KEY_STORE_ID + "='" + storeid + "'", null);
//        db.delete(CommonString.TABLE_PRIMARY_BAY_CATEGORY_IMAGE, CommonString.KEY_STORE_ID + "='" + storeid + "'", null);
//    }
//
//
//    public void updateStoreStatus(String storeid, String visitdate,
//                                  String status, String outlet) {
//
//        try {
//            ContentValues values = new ContentValues();
//            values.put("Upload_Status", status);
//
//            db.update(outlet, values, "Store_Id ='" + storeid + "' AND Visit_Date ='" + visitdate + "'", null);
//        } catch (Exception e) {
//
//        }
//    }
//
//    // get NonWorking data
//    public ArrayList<SupNonWorkingReason> getNonWorkingData() {
//
//        ArrayList<SupNonWorkingReason> list = new ArrayList<SupNonWorkingReason>();
//        Cursor dbcursor = null;
//        try {
//            dbcursor = db.rawQuery("SELECT * FROM Sup_Non_Working_Reason WHERE For_Att=0", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    SupNonWorkingReason sb = new SupNonWorkingReason();
//
//                    sb.setReasonId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Reason_Id"))));
//                    sb.setReason(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Reason")));
//                    sb.setEntryAllow("1".equalsIgnoreCase(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Entry_Allow"))));
//                    sb.setImageAllow("1".equalsIgnoreCase(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Image_Allow"))));
//                    sb.setGPSMandatory("1".equalsIgnoreCase(dbcursor.getString(dbcursor.getColumnIndexOrThrow("GPS_Mandatory"))));
//                    sb.setForatt("1".equalsIgnoreCase(dbcursor.getString(dbcursor.getColumnIndexOrThrow("For_Att"))));
//                    sb.setRemark("1".equalsIgnoreCase(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Remark"))));
//
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//
//            return list;
//        }
//
//
//        return list;
//    }
//
//    //region Deepak_getNonWorkingSubReasonData
//    public ArrayList<SupNonWorkingSubReason> getNonWorkingSubReasonData(String reasonId) {
//
//        ArrayList<SupNonWorkingSubReason> list = new ArrayList<SupNonWorkingSubReason>();
//        Cursor dbcursor = null;
//        try {
//            dbcursor = db.rawQuery("SELECT * FROM Sup_non_working_sub_reason where Reason_Id = " + reasonId + "", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    SupNonWorkingSubReason sb = new SupNonWorkingSubReason();
//
//                    sb.setReasonId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Sub_Reason_Id"))));
//                    sb.setSubReason(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Sub_Reason")));
//
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//
//            return list;
//        }
//        return list;
//    }
//
//    public boolean insertSupMappingStockData(SupMappingStockGetterSetter supMappingStockObj) {
//        // db.delete("Sup_Mapping_Stock", null, null);
//        ContentValues values = new ContentValues();
//        List<SupMappingStock> data = supMappingStockObj.getSupMappingStock();
//
//        try {
//            if (data.size() == 0) {
//                return false;
//            }
//
//            for (int i = 0; i < data.size(); i++) {
//                values.put("Trade_Area_Id", data.get(i).getTradeAreaId());
//                values.put("Tier_Id", data.get(i).getTierId());
//                values.put("Store_Type_Id", data.get(i).getStoreTypeId());
//                values.put("Classification_Id", data.get(i).getClassificationId());
//                values.put("Store_Category_Id", data.get(i).getStoreCategoryId());
//                values.put("Sku_id", data.get(i).getSkuId());
//                values.put("Focus", data.get(i).getFocus());
//                values.put("Emp_Id", data.get(i).getEmpId());
//
//                long id = db.insert("Sup_Mapping_Stock", null, values);
//                if (id == -1) {
//                    throw new Exception();
//                }
//
//            }
//            return true;
//        } catch (Exception ex) {
//            Crashlytics.logException(ex);
//            ex.printStackTrace();
//            Log.d("Database Sup_Mapping_Stock", ex.toString());
//            return false;
//        }
//    }
//
//    public boolean insertSkuMasterData(SkuMasterGetterSetter skuMasterObj) {
//        db.delete("Sku_Master", null, null);
//        ContentValues values = new ContentValues();
//        List<SkuMaster> data = skuMasterObj.getSkuMaster();
//
//        try {
//            if (data.size() == 0) {
//                return false;
//            }
//
//            for (int i = 0; i < data.size(); i++) {
//                values.put("Sku_Id", data.get(i).getSkuId());
//                values.put("Sku", data.get(i).getSku());
//                values.put("Brand_Id", data.get(i).getBrandId());
//                values.put("Sku_Sequence", data.get(i).getSkuSequence());
//
//                long id = db.insert("Sku_Master", null, values);
//                if (id == -1) {
//                    throw new Exception();
//                }
//
//            }
//            return true;
//        } catch (Exception ex) {
//            Crashlytics.logException(ex);
//            ex.printStackTrace();
//            Log.d("Database Sku_Master", ex.toString());
//            return false;
//        }
//    }
//
//    public boolean insertCategoryMasterData(CategoryMasterGetterSetter categoryMasterObj) {
//        db.delete("Category_Master", null, null);
//        ContentValues values = new ContentValues();
//        List<CategoryMaster> data = categoryMasterObj.getCategoryMaster();
//
//        try {
//            if (data.size() == 0) {
//                return false;
//            }
//
//            for (int i = 0; i < data.size(); i++) {
//                values.put("Category_Id", data.get(i).getCategoryId());
//                values.put("Category", data.get(i).getCategory());
//                values.put("Category_Sequence", data.get(i).getCategorySequence());
//                values.put("Icon", data.get(i).getIcon());
//                values.put("Icon_Done", data.get(i).getIconDone());
//                values.put("Image_Path", data.get(i).getImagePath());
//
//                long id = db.insert("Category_Master", null, values);
//                if (id == -1) {
//                    throw new Exception();
//                }
//
//            }
//            return true;
//        } catch (Exception ex) {
//            Crashlytics.logException(ex);
//            ex.printStackTrace();
//            return false;
//        }
//    }
//
//    public boolean insertBrandGroupMasterData(BrandGroupMasterGetterSetter brandGroupMasterObj) {
//        db.delete("Brand_Group_Master", null, null);
//        ContentValues values = new ContentValues();
//        List<BrandGroupMaster> data = brandGroupMasterObj.getBrandGroupMaster();
//
//        try {
//            if (data.size() == 0) {
//                return false;
//            }
//
//            for (int i = 0; i < data.size(); i++) {
//                values.put("Brand_Group_Id", data.get(i).getBrandGroupId());
//                values.put("Brand_Group", data.get(i).getBrandGroup());
//                values.put("Category_Id", data.get(i).getCategoryId());
//                values.put("Company_Id", data.get(i).getCompanyId());
//                values.put("Brand_Group_Sequence", data.get(i).getBrandGroupSequence());
//
//                long id = db.insert("Brand_Group_Master", null, values);
//                if (id == -1) {
//                    throw new Exception();
//                }
//
//            }
//            return true;
//        } catch (Exception ex) {
//            Crashlytics.logException(ex);
//            ex.printStackTrace();
//            return false;
//        }
//    }
//
//    public boolean brandMasterObj(BrandMasterGetterSetter brandMasterObj) {
//        db.delete("Brand_Master", null, null);
//        ContentValues values = new ContentValues();
//        List<BrandMaster> data = brandMasterObj.getBrandMaster();
//
//        try {
//            if (data.size() == 0) {
//                return false;
//            }
//
//            for (int i = 0; i < data.size(); i++) {
//                values.put("Brand_Id", data.get(i).getBrandId());
//                values.put("Brand", data.get(i).getBrand());
//                values.put("Brand_Group_Id", data.get(i).getBrandGroupId());
//                values.put("Brand_Sequence", data.get(i).getBrandSequence());
//
//                long id = db.insert("Brand_Master", null, values);
//                if (id == -1) {
//                    throw new Exception();
//                }
//
//            }
//            return true;
//        } catch (Exception ex) {
//            Crashlytics.logException(ex);
//            ex.printStackTrace();
//            Log.d("Database Brand_Master", ex.toString());
//            return false;
//        }
//    }
//
//
//    //Mapping_posm Data
//    public ArrayList<BrandMaster> getPosmData(JourneyPlan jcp, String table, String type) {
//
//        ArrayList<BrandMaster> list = new ArrayList<>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("Select distinct br.Brand_Id, br.Brand from " + table + " m Inner join Sup_Posm_Master p on m.Posm_Id = p.Posm_Id Inner join Brand_Master br on m.Brand_Id = br.Brand_Id and m.Store_Id = " + jcp.getStoreId() + " and m.Type='" + type + "' and m.Emp_Id =" + jcp.getEmpId() + "", null);
//
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    BrandMaster brand = new BrandMaster();
//
//                    brand.setBrand(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_BRAND)));
//                    brand.setBrandId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Brand_Id"))));
//                    list.add(brand);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception Brands",
//                    e.toString());
//            return list;
//        }
//
//        return list;
//    }
//
//
//    //db_posm default data
//    public ArrayList<PosmGetterSetter> getPosmDefaultData(JourneyPlan jcp, String brandId, String table, String type) {
//
//        ArrayList<PosmGetterSetter> list = new ArrayList<>();
//        Cursor dbcursor = null;
//        try {
//            dbcursor = db.rawQuery("select distinct a.Merchandiser_MID, a.Type,a.Store_Id, a.Brand_Id, br.Brand, a.Posm_Id, p.Posm, a.Posm_Qty, ifnull(d.Qty,'') as QTY, ifnull(d.Image1,'') as IMAGE1 from " + table + " a " +
//                    "inner join Sup_Posm_Master p on a.Posm_Id = p.Posm_Id " +
//                    "inner join Brand_Master br on a.Brand_Id = br.Brand_Id " +
//                    "Left join (Select * from Dr_Posmm Where Brand_Id = '" + brandId + "' and Store_Id = '" + jcp.getStoreId() + "' and Visit_Date = '" + jcp.getVisitDate() + "' and Type = '" + type + "') as d on a.Posm_Id = d.Posm_Id " +
//                    "where a.Store_Id = '" + jcp.getStoreId() + "' and a.Type='" + type + "' and br.Brand_Id = '" + brandId + "'", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    PosmGetterSetter sku = new PosmGetterSetter();
//                    sku.setType(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Type")));
//                    sku.setPosm(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Posm")));
//                    sku.setPosm_id(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Posm_Id")));
//                    sku.setPosm_qty(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_QTY)));
//                    sku.setPosm_image(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE1)));
//                    sku.setPosm_mer_qty(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Posm_Qty")));
//                    sku.setMerchandiser_MID(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Merchandiser_MID")));
//
//                    list.add(sku);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            return list;
//        }
//        return list;
//
//    }
//
//
//    // SELF SERVICE POSM DATA
//    public ArrayList<PosmGetterSetter> getSSPosmDefaultData(JourneyPlan jcp, String brandId, String table, String type) {
//
//        ArrayList<PosmGetterSetter> list = new ArrayList<>();
//        Cursor dbcursor = null;
//        try {
//            dbcursor = db.rawQuery("select distinct a.Merchandiser_MID, a.Type,a.Store_Id, a.Brand_Id, br.Brand, a.Posm_Id, p.Posm, a.Posm_Qty, ifnull(d.Qty,'') as QTY, ifnull(d.Image1,'') as IMAGE1 from " + table + " a " +
//                    "inner join Sup_Posm_Master p on a.Posm_Id = p.Posm_Id " +
//                    "inner join Brand_Master br on a.Brand_Id = br.Brand_Id " +
//                    "Left join (Select * from " + CommonString.TABLE_SS_POSM + " Where Brand_Id = '" + brandId + "' and Store_Id = '" + jcp.getStoreId() + "' and Visit_Date = '" + jcp.getVisitDate() + "' and Type = '" + type + "') as d on a.Posm_Id = d.Posm_Id " +
//                    "where a.Store_Id = '" + jcp.getStoreId() + "' and a.Type='" + type + "' and br.Brand_Id = '" + brandId + "'", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    PosmGetterSetter sku = new PosmGetterSetter();
//                    sku.setType(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Type")));
//                    sku.setPosm(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Posm")));
//                    sku.setPosm_id(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Posm_Id")));
//                    sku.setPosm_qty(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_QTY)));
//                    sku.setPosm_image(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE1)));
//                    sku.setPosm_mer_qty(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Posm_Qty")));
//                    sku.setMerchandiser_MID(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Merchandiser_MID")));
//
//                    list.add(sku);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            return list;
//        }
//        return list;
//
//    }
//
//
//    public long insertPosmData(String visitdate, int store_id, ArrayList<BrandMaster> listDataHeader,
//                               HashMap<BrandMaster, List<PosmGetterSetter>> listDataChild, String type) {
//        db.delete(CommonString.TABLE_POSM, CommonString.KEY_STORE_ID + " = '" + store_id + "' and " + CommonString.KEY_VISIT_DATE + " = '" + visitdate + "' and " + CommonString.TYPE + " = '" + type + "'", null);
//
//        ContentValues values = new ContentValues();
//        long id = 0;
//
//        try {
//
//            for (int i = 0; i < listDataHeader.size(); i++) {
//
//                BrandMaster brand = listDataHeader.get(i);
//
//                List<PosmGetterSetter> childData = listDataChild.get(brand);
//
//                for (int j = 0; j < childData.size(); j++) {
//                    values.put(CommonString.KEY_STORE_ID, store_id);
//                    values.put(CommonString.KEY_VISIT_DATE, visitdate);
//                    values.put(CommonString.KEY_BRAND_ID, brand.getBrandId());
//                    values.put(CommonString.KEY_POSM_ID, Integer.parseInt(childData.get(j).getPosm_id()));
//                    values.put(CommonString.KEY_QTY, childData.get(j).getPosm_qty());
//                    values.put(CommonString.KEY_IMAGE1, childData.get(j).getPosm_image());
//                    values.put(CommonString.KEY_TYPE, childData.get(j).getType());
//                    values.put(CommonString.KEY_MER_MID, childData.get(j).getMerchandiser_MID());
//                    values.put(CommonString.KEY_MER_STOCK_QUY, childData.get(j).getPosm_mer_qty());
//
//                    id = db.insert(CommonString.TABLE_POSM, null, values);
//                }
//
//            }
//            return id;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return -1;
//        }
//    }
//
//
//    public long insertSSPosmData(String visitdate, int store_id, ArrayList<BrandMaster> listDataHeader,
//                                 HashMap<BrandMaster, List<PosmGetterSetter>> listDataChild, String type) {
//        db.delete(CommonString.TABLE_SS_POSM, CommonString.KEY_STORE_ID + " = '" + store_id + "' and " + CommonString.KEY_VISIT_DATE + " = '" + visitdate + "' and " + CommonString.TYPE + " = '" + type + "'", null);
//
//        ContentValues values = new ContentValues();
//        long id = 0;
//
//        try {
//
//            for (int i = 0; i < listDataHeader.size(); i++) {
//
//                BrandMaster brand = listDataHeader.get(i);
//
//                List<PosmGetterSetter> childData = listDataChild.get(brand);
//
//                for (int j = 0; j < childData.size(); j++) {
//                    values.put(CommonString.KEY_STORE_ID, store_id);
//                    values.put(CommonString.KEY_VISIT_DATE, visitdate);
//                    values.put(CommonString.KEY_BRAND_ID, brand.getBrandId());
//                    values.put(CommonString.KEY_POSM_ID, Integer.parseInt(childData.get(j).getPosm_id()));
//                    values.put(CommonString.KEY_QTY, childData.get(j).getPosm_qty());
//                    values.put(CommonString.KEY_IMAGE1, childData.get(j).getPosm_image());
//                    values.put(CommonString.KEY_TYPE, childData.get(j).getType());
//                    values.put(CommonString.KEY_MER_MID, childData.get(j).getMerchandiser_MID());
//                    values.put(CommonString.KEY_MER_STOCK_QUY, childData.get(j).getPosm_mer_qty());
//
//                    id = db.insert(CommonString.TABLE_SS_POSM, null, values);
//                }
//
//            }
//            return id;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return -1;
//        }
//    }
//
//
//    public ArrayList<PrimarySelfData> getPrimarySelfUserData(JourneyPlan jcpGetset) {
//        ArrayList<PrimarySelfData> list = new ArrayList<>();
//        Cursor dbcursor = null;
//
//        try {
//
//            dbcursor = db.rawQuery(" select distinct sk.Brand_Id,bm.Brand from Sup_Primary_Shelf_Audit m " +
//                    " inner join Sku_Master sk on m.Sku_Id = sk.Sku_Id " +
//                    " inner join Brand_Master bm on sk.Brand_Id = bm.Brand_Id " +
//                    " where m.Store_Id = " + jcpGetset.getStoreId() + " and Emp_Id = " + jcpGetset.getEmpId() + "", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    PrimarySelfData psd = new PrimarySelfData();
//
//                    psd.setBrand_Id(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Brand_Id)));
//
//                    psd.setBrand(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_BRAND)));
//
//                    list.add(psd);
//                    dbcursor.moveToNext();
//                }
//
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception get JCP!", e.toString());
//            return list;
//        }
//        return list;
//    }
//
//    public ArrayList<PosmGetterSetter> getInsertedPosmTypeData1(JourneyPlan jcp, String type) {
//
//        ArrayList<PosmGetterSetter> list = new ArrayList<>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("Select * from " + CommonString.TABLE_POSM + " Where Store_Id = '" + jcp.getStoreId() + "' and Visit_Date = '" + jcp.getVisitDate() + "' and Type = '" + type + "'", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    PosmGetterSetter sku = new PosmGetterSetter();
//                    sku.setStoreId(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_STORE_ID)));
//                    sku.setType(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_TYPE)));
//                    sku.setBrand_id(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_BRAND_ID)));
//                    sku.setPosm_id(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_POSM_ID)));
//                    sku.setPosm_qty(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_QTY)));
//                    sku.setPosm_image(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE1)));
//                    sku.setPosm_mer_qty(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_MER_STOCK_QUY)));
//                    sku.setMerchandiser_MID(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_MER_MID)));
//                    list.add(sku);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            return list;
//        }
//        return list;
//    }
//
//
//    public ArrayList<PosmGetterSetter> getInsertedSSPosmTypeData(JourneyPlan jcp, String type) {
//
//        ArrayList<PosmGetterSetter> list = new ArrayList<>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("Select * from " + CommonString.TABLE_SS_POSM + " Where Store_Id = '" + jcp.getStoreId() + "' and Visit_Date = '" + jcp.getVisitDate() + "' and Type = '" + type + "'", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    PosmGetterSetter sku = new PosmGetterSetter();
//                    sku.setStoreId(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_STORE_ID)));
//                    sku.setType(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_TYPE)));
//                    sku.setBrand_id(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_BRAND_ID)));
//                    sku.setPosm_id(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_POSM_ID)));
//                    sku.setPosm_qty(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_QTY)));
//                    sku.setPosm_image(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE1)));
//                    sku.setPosm_mer_qty(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_MER_STOCK_QUY)));
//                    sku.setMerchandiser_MID(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_MER_MID)));
//                    list.add(sku);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            return list;
//        }
//        return list;
//
//    }
//
//
//    public ArrayList<StockNewGetterSetter> getWindowChecklistData1(String store_Id, String window_Id, Integer empId) {
//
//        Log.d("FetchingStoredata-----", "------------------");
//        ArrayList<StockNewGetterSetter> list = new ArrayList<StockNewGetterSetter>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("SELECT  distinct c.Checklist_Id as Checklist_Id, c.Checklist as Checklist , t.Merchandiser_MID as Merchandiser_MID, t.Compliance as Compliance FROM Window_Checklist c Inner join" +
//                    "(SELECT * FROM Sup_Window_Audit WHERE Store_Id ='" + store_Id + "' and Window_Id ='" + window_Id + "') as t" +
//                    " on c.Checklist_Id = t.Checklist_Id where t.Emp_Id =" + empId + "", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    StockNewGetterSetter sb = new StockNewGetterSetter();
//                    sb.setChecklist((dbcursor.getString(dbcursor.getColumnIndexOrThrow("Checklist"))));
//                    sb.setChecklist_Id((dbcursor.getString(dbcursor.getColumnIndexOrThrow("Checklist_Id"))));
//                    sb.setMerchandiser_mid((dbcursor.getString(dbcursor.getColumnIndexOrThrow("Merchandiser_MID"))));
//                    sb.setCompliance((dbcursor.getString(dbcursor.getColumnIndexOrThrow("Compliance"))));
//
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Crashlytics.logException(e);
//            Log.d("Exception when fetching",
//                    e.toString());
//            return list;
//        }
//        return list;
//    }
//
//    public boolean insertWindowMasterData(WindowMasterGetterSetter teamAttendancemtddata) {
//        db.delete("Window_Master", null, null);
//        ContentValues values = new ContentValues();
//
//        List<WindowMaster> data = teamAttendancemtddata.getWindowMaster();
//        try {
//            if (data.size() == 0) {
//                return false;
//            }
//
//            for (int i = 0; i < data.size(); i++) {
//
//                values.put("Window", data.get(i).getWindow());
//                values.put("Window_Id", data.get(i).getWindowId());
//
//                long id = db.insert("Window_Master", null, values);
//                if (id == -1) {
//                    throw new Exception();
//                }
//
//            }
//            return true;
//        } catch (Exception ex) {
//            Crashlytics.logException(ex);
//            ex.printStackTrace();
//            Log.d("Database non_working_  ", ex.toString());
//            return false;
//        }
//
//    }
//
//
//    public ArrayList<StockNewGetterSetter> getWindowMasterData1(String Store_Id, Integer empId) {
//        ArrayList<StockNewGetterSetter> list = new ArrayList<StockNewGetterSetter>();
//        Cursor dbcursor = null;
//        try {
//
//
//            dbcursor = db.rawQuery("select distinct WM.Window_Id AS Window_Id, WM.Window AS Window,WA.Store_Id AS Store_Id,WA.Merchandiser_MID AS AUDIT," +
//                    " BGM.Brand_Group AS Brand_Group,BGM.Brand_Group_Id AS Brand_Group_Id,WA.Merchandiser_MID as Merchandiser_MID from Sup_Window_Audit WA" +
//                    " INNER JOIN Window_Master WM ON WA.Window_Id = WM.Window_Id" +
//                    " INNER JOIN Brand_Group_Master BGM ON WA.Brand_Group_Id = BGM.Brand_Group_Id where WA.Emp_Id= " + empId + " and WA.Store_Id = '" + Store_Id + "'", null);
//
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    StockNewGetterSetter sb = new StockNewGetterSetter();
//                    sb.setWindow_id(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Window_Id")));
//                    sb.setWindow(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Window")));
//                    sb.setStore_id(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Id")));
//                    sb.setAudit(dbcursor.getString(dbcursor.getColumnIndexOrThrow("AUDIT")));
//                    sb.setBrand(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Brand_Group")));
//                    sb.setBrand_Group_Id(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Brand_Group_Id")));
//                    sb.setMerchandiser_mid(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Merchandiser_MID")));
//
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Crashlytics.logException(e);
//            Log.d("Exception when fetc", e.toString());
//            return list;
//        }
//
//        Log.d("FetchingStore", "-------------------");
//        return list;
//
//    }
//
//    public long InsertsBackwallData(SecondaryWindowGetterSetter data, String store_id, String visit_date) {
//        db.delete(CommonString.TABLE_SECONDARY_BACKWALL_DATA, CommonString.KEY_STORE_ID + "='" + store_id + "'", null);
//
//        ContentValues values = new ContentValues();
//        long id = 0;
//        try {
//
//            values.put(CommonString.KEY_STORE_ID, store_id);
//            values.put(CommonString.KEY_VISIT_DATE, visit_date);
//            values.put(CommonString.KEY_IMAGE_BACKWALL, data.getBackwall_image());
//            values.put(CommonString.KEY_REDIOBUTTON_COLOR, data.getRediobutton_color());
//            values.put(CommonString.KEY_LOCATION, data.getLocation());
//            values.put(CommonString.KEY_LOCATION_CD, data.getLocation_id());
//
//            id = db.insert(CommonString.TABLE_SECONDARY_BACKWALL_DATA, null, values);
//            if (id > 0) {
//                return id;
//            } else {
//                return 0;
//            }
//        } catch (Exception ex) {
//            Log.d("Database Exception ", ex.toString());
//            return 0;
//        }
//
//    }
//
//    public void InsertSecondaryWindowlistData1(String storeid, String visit_date,
//                                               HashMap<StockNewGetterSetter, List<StockNewGetterSetter>> listDataChild,
//                                               List<StockNewGetterSetter> data, String window_location_cd, String window_location) {
//        db.delete(CommonString.TABLE_SECONDARY_WINDOW_HEADER, CommonString.KEY_STORE_ID + "='" + storeid + "'", null);
//        db.delete(CommonString.TABLE_SECONDARY_WINDOW_CHILD, CommonString.KEY_STORE_ID + "='" + storeid + "'", null);
//
//        ContentValues values = new ContentValues();
//        ContentValues values1 = new ContentValues();
//        try {
//            long id = 0;
//            for (int i = 0; i < data.size(); i++) {
//                values.put(CommonString.KEY_STORE_ID, storeid);
//                values.put(CommonString.KEY_VISIT_DATE, visit_date);
//                values.put(CommonString.KEY_WINDOW_ID, data.get(i).getWindow_id());
//                values.put(CommonString.KEY_IMAGE, data.get(i).getImage());
//                values.put(CommonString.KEY_WINDOW, data.get(i).getWindow());
//                values.put(CommonString.KEY_BRAND_GROUP, data.get(i).getBrand());
//                values.put(CommonString.KEY_BRAND_GROUP_ID, data.get(i).getBrand_Group_Id());
//                values.put(CommonString.KEY_REMARK, data.get(i).getRemark());
//                values.put(CommonString.KEY_LOCATION, window_location);
//                values.put(CommonString.KEY_LOCATION_CD, window_location_cd);
//                values.put(CommonString.KEY_MER_MID, data.get(i).getMerchandiser_mid());
//
//
//                if (data.get(i).isPresent() == true) {
//                    values.put(CommonString.KEY_EXIST, "1");
//                } else {
//                    values.put(CommonString.KEY_EXIST, "0");
//                }
//
//
//                id = db.insert(CommonString.TABLE_SECONDARY_WINDOW_HEADER, null, values);
//
//             /*   if(data.get(i).isPresent())
//                {*/
//                for (int k = 0; k < listDataChild.get(data.get(i)).size(); k++) {
//                    values1.put(CommonString.KEY_COMMON_ID, id);
//                    values1.put(CommonString.KEY_STORE_ID, storeid);
//                    values1.put(CommonString.KEY_VISIT_DATE, visit_date);
//                    values1.put(CommonString.KEY_WINDOW_ID, data.get(i).getWindow_id());
//                    values1.put(CommonString.KEY_LOCATION_CD, data.get(i).getLocation_id());
//                    values1.put(CommonString.KEY_CHECKLIST_ID, listDataChild.get(data.get(i)).get(k).getChecklist_Id());
//                    values1.put(CommonString.KEY_CHECKLIST, listDataChild.get(data.get(i)).get(k).getChecklist());
//                    values1.put(CommonString.KEY_REMARK, listDataChild.get(data.get(i)).get(k).getRemark());
//                    values1.put(CommonString.KEY_MER_MID, listDataChild.get(data.get(i)).get(k).getMerchandiser_mid());
//                    values1.put(CommonString.KEY_MER_COMPLIANCE, listDataChild.get(data.get(i)).get(k).getCompliance());
//                    values1.put(CommonString.KEY_SUP_COMPLIANCE, listDataChild.get(data.get(i)).get(k).getIsExit());
//
//                    if (listDataChild.get(data.get(i)).get(k).getCompliance() == null) {
//                        values1.put(CommonString.KEY_MER_COMPLIANCE, "");
//                    } else {
//                        values1.put(CommonString.KEY_MER_COMPLIANCE, listDataChild.get(data.get(i)).get(k).getCompliance());
//                    }
//
//                    db.insert(CommonString.TABLE_SECONDARY_WINDOW_CHILD, null, values1);
//                }
//                // }
//
//            }
//        } catch (Exception ex) {
//            Log.d("Database Exception", " while Insert Header Data " + ex.toString());
//        }
//    }
//
//
//    public ArrayList<StockNewGetterSetter> getSecondaryWindowInsertedData1(String storeid, String visitdate) {
//
//        Log.d("FetchingStored",
//                "------------------");
//        ArrayList<StockNewGetterSetter> list = new ArrayList<>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("SELECT  * from " + CommonString.TABLE_SECONDARY_WINDOW_HEADER + "  " +
//                    "where " + CommonString.KEY_STORE_ID + " =" + storeid + "", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    StockNewGetterSetter sb = new StockNewGetterSetter();
//                    sb.setImage(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE)));
//                    sb.setLocation_id(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_LOCATION_CD)));
//                    sb.setLocation(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_LOCATION)));
//                    sb.setBrand_Group_Id(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_BRAND_GROUP_ID)));
//                    sb.setWindow(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_WINDOW)));
//                    sb.setKey_id(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ID)));
//                    sb.setWindow_id(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_WINDOW_ID)));
//                    sb.setBrand(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_BRAND_GROUP)));
//                    sb.setMerchandiser_mid(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_MER_MID)));
//                    int i = dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_EXIST));
//
//                    if (i == 0) {
//                        sb.setPresent(false);
//                    } else {
//                        sb.setPresent(true);
//                    }
//
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception ", e.getMessage());
//            return list;
//        }
//
//        Log.d("FetchingS", "-------------------");
//        return list;
//
//    }
//
//
//    public ArrayList<StockNewGetterSetter> getSecondaryWindowInsertedchecklistData1(String storeid, String window_id) {
//
//        Log.d("FetchingStored", "------------------");
//        ArrayList<StockNewGetterSetter> list = new ArrayList<>();
//        Cursor dbcursor = null;
//        try {
//            dbcursor = db.rawQuery("SELECT  * from "
//                    + CommonString.TABLE_SECONDARY_WINDOW_CHILD + " where "
//                    + CommonString.KEY_STORE_ID + " = '" + storeid + "' AND "
//                    + CommonString.KEY_WINDOW_ID + " = '" + window_id + "'", null);
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    StockNewGetterSetter sb = new StockNewGetterSetter();
//                    sb.setKey_id(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ID)));
//                    sb.setCommon_id(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_COMMON_ID)));
//                    sb.setIsExit(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_SUP_COMPLIANCE)));
//                    sb.setWindow_id(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_WINDOW_ID)));
//                    sb.setChecklist_Id(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CHECKLIST_ID)));
//                    sb.setRemark(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REMARK)));
//                    sb.setChecklist(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CHECKLIST)));
//                    sb.setMerchandiser_mid(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_MER_MID)));
//                    sb.setCompliance(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_MER_COMPLIANCE)));
//                    sb.setLocation_id(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_LOCATION_CD)));
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception ", e.getMessage());
//            return list;
//        }
//
//        Log.d("FetchingS", "-------------------");
//        return list;
//
//    }
//
//
//    public SecondaryWindowGetterSetter getinsertSecWindowData(String store_id) {
//        Log.d("FetchinggetCity",
//                "------------------");
//        SecondaryWindowGetterSetter sb = new SecondaryWindowGetterSetter();
//        Cursor dbcursor = null;
//
//
//        try {
//
//            dbcursor = db.rawQuery("SELECT * FROM " + CommonString.TABLE_SECONDARY_BACKWALL_DATA + " WHERE " + CommonString.KEY_STORE_ID + " = " + store_id, null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//
//                    sb.setBackwall_image(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_IMAGE_BACKWALL)));
//                    sb.setRediobutton_color(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_REDIOBUTTON_COLOR)));
//                    sb.setLocation_id(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_LOCATION_CD)));
//                    sb.setLocation(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_LOCATION)));
//
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return sb;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception when",
//                    e.toString());
//            return sb;
//        }
//
//        Log.d("Fetching ",
//                "-------------------");
//        return sb;
//    }
//
//    /* upendra code ended*/
//
//    public boolean insertWindowAuditData(SupWindowAuditGetterSetter windowAuditObj) {
//        // db.delete("Sup_Window_Audit", null, null);
//        ContentValues values = new ContentValues();
//
//        List<SupWindowAudit> data = windowAuditObj.getSupWindowAudit();
//        try {
//            if (data.size() == 0) {
//                return false;
//            }
//
//            for (int i = 0; i < data.size(); i++) {
//
//                values.put("Checklist_Id", data.get(i).getChecklistId());
//                values.put("Compliance", data.get(i).getCompliance());
//                values.put("Merchandiser_MID", data.get(i).getMerchandiserMID());
//                values.put("Store_Id", data.get(i).getStoreId());
//                values.put("Window_Id", data.get(i).getWindowId());
//                values.put("Emp_Id", data.get(i).getEmpId());
//                values.put("Brand_Group_Id", data.get(i).getBrandGroupId());
//                values.put("Visit_Date",data.get(i).getVisitDate());
//                long id = db.insert("Sup_Window_Audit", null, values);
//                if (id == -1) {
//                    throw new Exception();
//                }
//
//            }
//            return true;
//        } catch (Exception ex) {
//            Crashlytics.logException(ex);
//            ex.printStackTrace();
//            return false;
//        }
//    }
//
//    public ArrayList<StockNewGetterSetter> getSecondaryWindowDone(JourneyPlan journeyPlan) {
//
//        Log.d("FetchingStored", "------------------");
//        ArrayList<StockNewGetterSetter> list = new ArrayList<>();
//        Cursor dbcursor = null;
//
//        try {
//
//            dbcursor = db.rawQuery("SELECT  * from "
//                    + CommonString.TABLE_SECONDARY_WINDOW_CHILD + " where "
//                    + CommonString.KEY_STORE_ID + " = '" + journeyPlan.getStoreId() + "'", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    StockNewGetterSetter sb = new StockNewGetterSetter();
//                    sb.setKey_id(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ID)));
//                    sb.setCommon_id(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_COMMON_ID)));
//                    sb.setIsExit(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_SUP_COMPLIANCE)));
//                    sb.setWindow_id(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_WINDOW_ID)));
//                    sb.setChecklist_Id(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CHECKLIST_ID)));
//                    sb.setRemark(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REMARK)));
//                    String value = dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CHECKLIST_ID));
//                    if (value.equals("1")) {
//                        sb.setChecklist_Id("1");
//                    } else {
//                        sb.setChecklist_Id("2");
//                    }
//                    sb.setChecklist(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CHECKLIST)));
//                    sb.setMerchandiser_mid(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_MER_MID)));
//                    sb.setCompliance(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_MER_COMPLIANCE)));
//
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception ", e.getMessage());
//            return list;
//        }
//
//        Log.d("FetchingS", "-------------------");
//        return list;
//
//    }
//
//
//    public long updateCheckoutStatus(String storeId, String status, String table) {
//        ContentValues values = new ContentValues();
//        long l = 0;
//        try {
//            values.put("Upload_Status", status);
//            l = db.update(table, values, "Store_Id =" + Integer.parseInt(storeId) + "", null);
//        } catch (Exception ex) {
//            Log.e("Exception", "checkOut Journey_Plan" + ex.toString());
//            return l;
//        }
//        return l;
//    }
//
//    public boolean insertSupDeviationReasonData(SupDeviationReasonGetterSetter supDeviationReasonObj) {
//        db.delete("Sup_Deviation_Reason", null, null);
//        ContentValues values = new ContentValues();
//        List<SupDeviationReason> data = supDeviationReasonObj.getSupDeviationReason();
//        try {
//            if (data.size() == 0) {
//                return false;
//            }
//
//            for (int i = 0; i < data.size(); i++) {
//
//                values.put(CommonString.DEVIATION_REASON_ID, data.get(i).getDeviationReasonId());
//                values.put(CommonString.DEVIATION_REASON, data.get(i).getDeviationReason());
//                values.put(CommonString.DEVIATION_ALLOW, data.get(i).getDeviationAllow());
//                values.put(CommonString.IMAGE_ALLOW, data.get(i).getImageAllow());
//
//                long id = db.insert("Sup_Deviation_Reason", null, values);
//                if (id == -1) {
//                    throw new Exception();
//                }
//            }
//            return true;
//        } catch (Exception ex) {
//            Crashlytics.logException(ex);
//            ex.printStackTrace();
//            Log.d("Database Exception  ", ex.toString());
//            return false;
//        }
//    }
//
//    public List<SupDeviationReason> getDeviationReasonList() {
//        Log.d("Fetching SupDeviat", "------------------");
//        ArrayList<SupDeviationReason> list = new ArrayList<>();
//        Cursor dbcursor = null;
//        SupDeviationReason sb1 = new SupDeviationReason();
//        try {
//
//            sb1.setDeviationReasonId(0);
//            sb1.setDeviationReason("Select Reason");
//            sb1.setDeviationAllow(true);
//            sb1.setImageAllow(false);
//
//            list.add(sb1);
//
//            dbcursor = db.rawQuery("SELECT  * from Sup_Deviation_Reason", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    SupDeviationReason sb2 = new SupDeviationReason();
//                    sb2.setDeviationReasonId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.DEVIATION_REASON_ID))));
//                    sb2.setDeviationReason(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.DEVIATION_REASON)));
//                    String value = dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.DEVIATION_ALLOW));
//                    if (value.equalsIgnoreCase("1")) {
//                        sb2.setDeviationAllow(true);
//                    } else {
//                        sb2.setDeviationAllow(false);
//                    }
//
//                    String value1 = dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.IMAGE_ALLOW));
//                    if (value1.equalsIgnoreCase("1")) {
//                        sb2.setImageAllow(true);
//                    } else {
//                        sb2.setImageAllow(false);
//                    }
//                    list.add(sb2);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception ", e.getMessage());
//            return list;
//        }
//
//        Log.d("FetchingS", "-------------------");
//        return list;
//    }
//
//    public long insertDeviationData(DeviationGetterSetter deviation) {
//        db.delete(CommonString.TABLE_PJP_DEVIATION, null, null);
//        ContentValues values = new ContentValues();
//        long id = 0;
//        try {
//            values.put(CommonString.KEY_USER_ID, deviation.getUserId());
//            values.put(CommonString.KEY_VISIT_DATE, deviation.getVisitedDate());
//            values.put(CommonString.KEY_REASON_ID, deviation.getReasonId());
//            values.put(CommonString.KEY_TYPE, deviation.getCoverageType());
//            values.put(CommonString.KEY_REASON_ALLOW, deviation.isEntryAllow());
//            values.put(CommonString.KEY_IMAGE, deviation.getImage());
//            values.put(CommonString.KEY_IMAGE_ALLOW, deviation.isImageAllow());
//
//            id = db.insert(CommonString.TABLE_PJP_DEVIATION, null, values);
//            if (id > 0) {
//                return id;
//            } else {
//                return 0;
//            }
//        } catch (Exception ex) {
//            Log.d("Database Exception ", ex.toString());
//            return 0;
//        }
//    }
//
//
//    public ArrayList<DeviationGetterSetter> getpjpDeviationData() {
//
//        ArrayList<DeviationGetterSetter> list = new ArrayList<DeviationGetterSetter>();
//        Cursor dbcursor = null;
//        try {
//            dbcursor = db.rawQuery("SELECT  * from " + CommonString.TABLE_PJP_DEVIATION + " ", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    DeviationGetterSetter dgs = new DeviationGetterSetter();
//                    dgs.setImage(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE)));
//                    dgs.setReasonId(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REASON_ID)));
//                    dgs.setEntryAllow(Boolean.parseBoolean(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REASON_ALLOW))));
//                    list.add(dgs);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Crashlytics.logException(e);
//            return list;
//        }
//        Log.d("Deviation data", "-------------------");
//        return list;
//
//    }
//
//    /* neeraj code ended*/
//
//    public boolean AttendanceStatusObj(SupAttendanceStatusSetterGetter supAttendancestatusObj) {
//        db.delete("Sup_Attendance_Status", null, null);
//        ContentValues values = new ContentValues();
//        List<SupAttendanceStatus> data = supAttendancestatusObj.getSupAttendanceStatus();
//        try {
//            if (data.size() == 0) {
//                return false;
//            }
//            for (int i = 0; i < data.size(); i++) {
//                values.put("Mark_Attendance", data.get(i).getMarkAttendance());
//                values.put("Visit_Date", data.get(i).getVisitDate());
//                long id = db.insert("Sup_Attendance_Status", null, values);
//                if (id == -1) {
//                    throw new Exception();
//                }
//            }
//            return true;
//        } catch (Exception ex) {
//            Crashlytics.logException(ex);
//            ex.printStackTrace();
//            Log.d("Database Brand_Master", ex.toString());
//            return false;
//        }
//    }
//
//    public ArrayList<SupAttendanceStatus> GetAttendanceStatusAtten(String date) {
//
//        ArrayList<SupAttendanceStatus> list = new ArrayList<SupAttendanceStatus>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("SELECT * FROM Sup_Attendance_Status where Visit_Date='" + date + "'", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    SupAttendanceStatus sb = new SupAttendanceStatus();
//                    sb.setMarkAttendance(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Mark_Attendance"))));
//                    sb.setVisitDate(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Visit_Date")));
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Crashlytics.logException(e);
//            return list;
//        }
//        Log.d("FetchingStore", "-------------------");
//        return list;
//
//    }
//
//    public ArrayList<SupNonWorkingReason> GetReasonFromReasonId(String resion_id) {
//
//        ArrayList<SupNonWorkingReason> list = new ArrayList<SupNonWorkingReason>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("SELECT  DISTINCT * from Sup_Non_Working_Reason where Reason_id=" + resion_id + " and For_Att=1", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    SupNonWorkingReason sb = new SupNonWorkingReason();
//                    sb.setEntryAllow("1".equalsIgnoreCase(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Entry_Allow"))));
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Crashlytics.logException(e);
//            return list;
//        }
//        Log.d("FetchingStore", "-------------------");
//        return list;
//
//    }
//
//    public ArrayList<SupTeamList> getTemListData() {
//
//        Log.d("FetchingStoredata-----", "------------------");
//        ArrayList<SupTeamList> list = new ArrayList<SupTeamList>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("SELECT  * from Sup_Team_List order by Merchandiser", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    SupTeamList sb = new SupTeamList();
//                    sb.setEmpId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Emp_Id"))));
//                    sb.setMerchandiser(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Merchandiser")));
//                    sb.setUserId(dbcursor.getString(dbcursor.getColumnIndexOrThrow("UserId")));
//                    sb.setAttStatus(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("Att_Status")));
//                    sb.setVisitDate(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Visit_Date")));
//                    sb.setSupAtt(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("Sup_Att")));
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Crashlytics.logException(e);
//            Log.d("Exception when fetching", e.toString());
//            return list;
//        }
//        return list;
//    }
//
//
//    public ArrayList<SupTeamList> getAttendanceStatus(Integer attStatus, Integer empId) {
//
//        Log.d("FetchingStoredata-----", "------------------");
//        ArrayList<SupTeamList> list = new ArrayList<SupTeamList>();
//        Cursor dbcursor = null;
//
//        try {
//
//            dbcursor = db.rawQuery(" SELECT distinct STL.Emp_Id,STL.Merchandiser,STL.Att_Status,STL.Visit_Date,SDR.Reason_Id,SDR.Reason,STL.UserId,STL.Sup_Att from Sup_Team_List STL " +
//                                        " inner join Sup_Non_Working_Reason SDR on STL.Att_Status = SDR.Reason_Id and STL.Att_Status = "+attStatus+" and STL.Emp_Id = '"+empId+"' ",null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    SupTeamList sb = new SupTeamList();
//                    sb.setEmpId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Emp_Id"))));
//                    sb.setMerchandiser(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Merchandiser")));
//                    sb.setMer_Reason_id(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Reason_Id")));
//                    sb.setReason(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Reason")));
//                    sb.setUserId(dbcursor.getString(dbcursor.getColumnIndexOrThrow("UserId")));
//                    sb.setAttStatus(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("Att_Status")));
//                    sb.setVisitDate(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Visit_Date")));
//                    sb.setSupAtt(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("Sup_Att")));
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Crashlytics.logException(e);
//            Log.d("Exception when fetching", e.toString());
//            return list;
//        }
//        return list;
//    }
//
//
//    public long updateSupAttendance(int emp_Id, Integer attStatus) {
//        long id = 0;
//        ContentValues values = new ContentValues();
//
//        try {
//            values.put("Sup_Att", attStatus);
//
//            id = db.update("Sup_Team_List", values,
//                    CommonString.KEY_EMP_ID + "='" + emp_Id + "'", null);
//
//            if (id > 0) {
//                return id;
//            } else {
//                return 0;
//            }
//        } catch (Exception ex) {
//            Log.d("Database Exception ", ex.toString());
//            return 0;
//        }
//    }
//
//
//    public ArrayList<SupTeamList> getSupAttendanceStatus(Integer supAtt, Integer empId) {
//
//        Log.d("FetchingStoredata-----", "------------------");
//        ArrayList<SupTeamList> list = new ArrayList<SupTeamList>();
//        Cursor dbcursor = null;
//
//        try {
//
//            dbcursor = db.rawQuery(" SELECT distinct STL.Emp_Id,STL.Merchandiser,STL.Att_Status,STL.Visit_Date,SDR.Reason_Id,SDR.Reason,STL.UserId,STL.Sup_Att from Sup_Team_List STL " +
//                    " inner join Sup_Non_Working_Reason SDR on STL.Sup_Att = SDR.Reason_Id and STL.Sup_Att = "+supAtt+" and STL.Emp_Id = '"+empId+"' ",null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    SupTeamList sb = new SupTeamList();
//                    sb.setEmpId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Emp_Id"))));
//                    sb.setMerchandiser(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Merchandiser")));
//                    sb.setMer_Reason_id(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Reason_Id")));
//                    sb.setReason(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Reason")));
//                    sb.setUserId(dbcursor.getString(dbcursor.getColumnIndexOrThrow("UserId")));
//                    sb.setAttStatus(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("Att_Status")));
//                    sb.setVisitDate(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Visit_Date")));
//                    sb.setSupAtt(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("Sup_Att")));
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Crashlytics.logException(e);
//            Log.d("Exception when fetching", e.toString());
//            return list;
//        }
//        return list;
//    }
//
//    public boolean DeviationStatusObj(SupDeviationStatusSetterGetter supDeviationStatusObj) {
//        db.delete("Sup_Deviation_Status", null, null);
//        ContentValues values = new ContentValues();
//        List<SupDeviationStatus> data = supDeviationStatusObj.getSupDeviationStatus();
//        try {
//            if (data.size() == 0) {
//                return false;
//            }
//            for (int i = 0; i < data.size(); i++) {
//                values.put(CommonString.SUP_DEVIATION_ID, data.get(i).getDeviationReasonId());
//
//                long id = db.insert("Sup_Deviation_Status", null, values);
//                if (id == -1) {
//                    throw new Exception();
//                }
//            }
//            return true;
//        } catch (Exception ex) {
//            Crashlytics.logException(ex);
//            ex.printStackTrace();
//            Log.d("Database Sup_Deviation_Status", ex.toString());
//            return false;
//        }
//    }
//
//    public ArrayList<SupDeviationStatus> getSupDeviationStatus() {
//        ArrayList<SupDeviationStatus> list = new ArrayList<SupDeviationStatus>();
//        Cursor dbcursor = null;
//        try {
//            dbcursor = db.rawQuery("SELECT  * from Sup_Deviation_Status ", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    SupDeviationStatus sds = new SupDeviationStatus();
//                    sds.setDeviationReasonId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.SUP_DEVIATION_ID))));
//                    list.add(sds);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Crashlytics.logException(e);
//            return list;
//        }
//        Log.d("Deviation data", "-------------------");
//        return list;
//    }
//
//
//    public ArrayList<JourneyPlan> getUploadStatusData(String visit_date, String outlet_today, String today_pjp) {
//
//        ArrayList<JourneyPlan> list = new ArrayList<JourneyPlan>();
//        Cursor dbcursor = null;
//
//
//        try {
//
//            dbcursor = db.rawQuery("SELECT distinct * from  " + outlet_today + " as jp inner join Sup_Team_List as stl on stl.Emp_Id = jp.Emp_Id where jp.Visit_Date ='" + visit_date + "' and Upload_Status = '" + CommonString.KEY_N + "'  and stl.Today_PJP = '" + today_pjp + "' ", null);
//
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//
//                    JourneyPlan sb = new JourneyPlan();
//                    sb.setUploadStatus(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Upload_Status")));
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Crashlytics.logException(e);
//            Log.d("Exception when fetc", e.toString());
//            return list;
//        }
//
//        Log.d("FetchingStore", "-------------------");
//        return list;
//
//    }
//
//    public ArrayList<SupJourneyCallCycle> GetjounryCallCycleforDeviation(String flag) {
//        ArrayList<SupJourneyCallCycle> list = new ArrayList<SupJourneyCallCycle>();
//        Cursor dbcursor = null;
//
//        try {
//
//            dbcursor = db.rawQuery("SELECT * FROM Sup_Team_List WHERE Today_PJP = '" + flag + "' order by Merchandiser ", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//
//                    SupJourneyCallCycle sb = new SupJourneyCallCycle();
//                    sb.setEmpId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Emp_Id"))));
//                    sb.setMerchandiser(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Merchandiser")));
//                    list.add(sb);
//
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Crashlytics.logException(e);
//            Log.d("Exception when fetc", e.toString());
//            return list;
//        }
//
//        Log.d("FetchingStore", "-------------------");
//        return list;
//    }
//
//
//    public ArrayList<SupTeamList> getMerchandiserList() {
//        ArrayList<SupTeamList> list = new ArrayList<SupTeamList>();
//        Cursor dbcursor = null;
//
//        try {
//
//            dbcursor = db.rawQuery("SELECT * FROM Sup_Team_List", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//
//                    SupTeamList sb = new SupTeamList();
//                    sb.setEmpId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Emp_Id"))));
//                    sb.setMerchandiser(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Merchandiser")));
//                    sb.setToolKitStatus(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("Tool_Kit_Status")));
//                    list.add(sb);
//
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Crashlytics.logException(e);
//            Log.d("Exception when fetc", e.toString());
//            return list;
//        }
//
//        Log.d("FetchingStore", "-------------------");
//        return list;
//    }
//
//
//    public JourneyPlan getEmployeeCheckedInCd(String sup_jounry_plan) {
//
//        JourneyPlan sb = new JourneyPlan();
//        Cursor dbcursor = null;
//        try {
//
//            dbcursor = db.rawQuery("SELECT * FROM " + sup_jounry_plan + " where Upload_Status = '" + CommonString.KEY_CHECK_IN + "' ", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//
//                    sb.setEmpId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Emp_Id"))));
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return sb;
//            }
//
//        } catch (Exception e) {
//            Crashlytics.logException(e);
//            Log.d("Exception when fetc", e.toString());
//            return sb;
//        }
//
//        Log.d("FetchingEmployee", "-------------------");
//        return sb;
//
//    }
//
//    public JourneyPlan getUploadStatusJcpStore(String visit_date, String sup_jounry_plan, String status, String today_pjp) {
//
//        JourneyPlan sb = new JourneyPlan();
//        Cursor dbcursor = null;
//        try {
//
//            dbcursor = db.rawQuery("SELECT * FROM " + sup_jounry_plan + " as jp inner join Sup_Team_List as stl on stl.Emp_Id = jp.Emp_Id where jp.Visit_Date ='" + visit_date + "' and Upload_Status = '" + status + "'  and stl.Today_PJP = '" + today_pjp + "'  ", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//
//                    sb.setUploadStatus(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Upload_Status")));
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return sb;
//            }
//
//        } catch (Exception e) {
//            Crashlytics.logException(e);
//            Log.d("Exception when fetc", e.toString());
//            return sb;
//        }
//
//        Log.d("FetchingEmployee", "-------------------");
//        return sb;
//    }
//
//
//    public JourneyPlan getUploadStatusDeviationStore(String visit_date, String sup_jounry_plan, String today_pjp) {
//
//        JourneyPlan sb = new JourneyPlan();
//        Cursor dbcursor = null;
//        try {
//            dbcursor = db.rawQuery("SELECT * FROM " + sup_jounry_plan + " as jp inner join Sup_Team_List as stl on stl.Emp_Id = jp.Emp_Id where jp.Visit_Date ='" + visit_date + "' and Upload_Status <> '" + CommonString.KEY_N + "'  and stl.Today_PJP = '" + today_pjp + "'  ", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//
//                    sb.setUploadStatus(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Upload_Status")));
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return sb;
//            }
//
//        } catch (Exception e) {
//            Crashlytics.logException(e);
//            Log.d("Exception when fetc", e.toString());
//            return sb;
//        }
//
//        Log.d("FetchingEmployee", "-------------------");
//        return sb;
//    }
//
//    public ArrayList<CoverageBean> getCompleteCoverageData(String date) {
//        ArrayList<CoverageBean> list = new ArrayList<CoverageBean>();
//        Cursor dbcursor = null;
//        try {
//            dbcursor = db.rawQuery("SELECT  * from " + CommonString.TABLE_COVERAGE_DATA + " where "
//                    + CommonString.KEY_VISIT_DATE + "='" + date + "' ", null);
//
//            if (dbcursor != null) {
//
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    CoverageBean sb = new CoverageBean();
//
//                    sb.setStoreId(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_STORE_ID)));
//                    sb.setVisitDate((((dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_VISIT_DATE))))));
//                    sb.setLatitude(((dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_LATITUDE)))));
//                    sb.setLongitude(((dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_LONGITUDE)))));
//                    sb.setImage((((dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_IMAGE))))));
//                    sb.setReason((((dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_REASON))))));
//                    sb.setReasonid((((dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_REASON_ID))))));
//                    sb.setSub_reasonId((((dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_SUB_REASON_ID))))));
//                    sb.setMID(Integer.parseInt(((dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_ID))))));
//
//                    sb.setCoverage_type((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_TYPE))));
//                    sb.setUploadStatus((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_UPLOAD_STATUS))));
//                    sb.setMerchandiser_MID((dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_MER_MID))));
//                    if (dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_COVERAGE_REMARK)) == null) {
//                        sb.setRemark("");
//                    } else {
//                        sb.setRemark((((dbcursor.getString(dbcursor
//                                .getColumnIndexOrThrow(CommonString.KEY_COVERAGE_REMARK))))));
//                    }
//
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//        } catch (Exception e) {
//            Log.d("Exception get JCP!", e.toString());
//            return list;
//        }
//        return list;
//    }
//
//
//    public ArrayList<CoverageBean> getPreviousDayCompleteCoverageData(String date) {
//        ArrayList<CoverageBean> list = new ArrayList<CoverageBean>();
//        Cursor dbcursor = null;
//        try {
//            dbcursor = db.rawQuery("SELECT  * from " + CommonString.TABLE_COVERAGE_DATA + " where "
//                    + CommonString.KEY_VISIT_DATE + "<>'" + date + "' ", null);
//
//            if (dbcursor != null) {
//
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    CoverageBean sb = new CoverageBean();
//
//                    sb.setStoreId(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_STORE_ID)));
//                    sb.setVisitDate((((dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_VISIT_DATE))))));
//                    sb.setLatitude(((dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_LATITUDE)))));
//                    sb.setLongitude(((dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_LONGITUDE)))));
//                    sb.setImage((((dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_IMAGE))))));
//                    sb.setReason((((dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_REASON))))));
//                    sb.setReasonid((((dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_REASON_ID))))));
//                    sb.setSub_reasonId((((dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_SUB_REASON_ID))))));
//                    sb.setMID(Integer.parseInt(((dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_ID))))));
//
//                    sb.setCoverage_type((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_TYPE))));
//                    sb.setUploadStatus((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_UPLOAD_STATUS))));
//
//                    if (dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_COVERAGE_REMARK)) == null) {
//                        sb.setRemark("");
//                    } else {
//                        sb.setRemark((((dbcursor.getString(dbcursor
//                                .getColumnIndexOrThrow(CommonString.KEY_COVERAGE_REMARK))))));
//                    }
//
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//        } catch (Exception e) {
//            Log.d("Exception get JCP!", e.toString());
//            return list;
//        }
//        return list;
//    }
//
//
//    public boolean isCoveragePreviousDataFilled(String date) {
//        boolean filled = false;
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("SELECT  * from " + CommonString.TABLE_COVERAGE_DATA + " where "
//                    + CommonString.KEY_VISIT_DATE + "<>'" + date + "' ", null);
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                int icount = dbcursor.getInt(0);
//                dbcursor.close();
//                if (icount > 0) {
//                    filled = true;
//                } else {
//                    filled = false;
//                }
//            }
//        } catch (Exception e) {
//            Log.d("Exception ", " when fetching Records!!!!!!!!!!!!!!!!!!!!! " + e.toString());
//            return filled;
//        }
//        return filled;
//    }
//
//
//    public ArrayList<CoverageBean> getCoverageDataPrevious(String date) {
//        ArrayList<CoverageBean> list = new ArrayList<CoverageBean>();
//        Cursor dbcursor = null;
//        try {
//            dbcursor = db.rawQuery("SELECT  * from " + CommonString.TABLE_COVERAGE_DATA + " where "
//                    + CommonString.KEY_VISIT_DATE + "<>'" + date + "' ", null);
//
//            if (dbcursor != null) {
//
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    CoverageBean sb = new CoverageBean();
//
//                    sb.setStoreId(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_STORE_ID)));
//                    sb.setVisitDate((((dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_VISIT_DATE))))));
//                    sb.setLatitude(((dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_LATITUDE)))));
//                    sb.setLongitude(((dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_LONGITUDE)))));
//                    sb.setImage((((dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_IMAGE))))));
//                    sb.setReason((((dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_REASON))))));
//                    sb.setReasonid((((dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_REASON_ID))))));
//                    sb.setSub_reasonId((((dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_SUB_REASON_ID))))));
//                    sb.setMID(Integer.parseInt(((dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_ID))))));
//
//                    sb.setCoverage_type((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_TYPE))));
//                    sb.setUploadStatus((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_UPLOAD_STATUS))));
//                    sb.setMerchandiser_MID((dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_MER_MID))));
//
//                    if (dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_COVERAGE_REMARK)) == null) {
//                        sb.setRemark("");
//                    } else {
//                        sb.setRemark((((dbcursor.getString(dbcursor
//                                .getColumnIndexOrThrow(CommonString.KEY_COVERAGE_REMARK))))));
//                    }
//
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//        } catch (Exception e) {
//            Log.d("Exception get JCP!", e.toString());
//            return list;
//        }
//        return list;
//    }
//
//
//    //get specific store data
//    public JourneyPlan getSpecificStoreData(String date, String store_id, String jcp) {
//        JourneyPlan sb = new JourneyPlan();
//        Cursor dbcursor = null;
//
//        try {
//
//            dbcursor = db.rawQuery("SELECT * FROM " + jcp + " where Visit_Date ='" + date + "' and Store_Id = '" + store_id + "' and Upload_Status = '" + CommonString.KEY_CHECK_IN + "'  ", null);
//
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    sb.setEmpId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Emp_Id"))));
//                    sb.setAddress1(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Address1")));
//                    sb.setAddress2(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Address2")));
//                    sb.setCity(dbcursor.getString(dbcursor.getColumnIndexOrThrow("City")));
//                    sb.setCityId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("City_Id"))));
//                    sb.setClassification(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Classification")));
//                    sb.setClassificationId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Classification_Id"))));
//                    sb.setDistributorId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Distributor_Id"))));
//                    sb.setEmpId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Emp_Id"))));
//                    sb.setGeoTag(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Geo_Tag")));
//                    sb.setLandmark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Landmark")));
//                    sb.setPincode(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Pincode")));
//                    sb.setReasonId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Reason_Id"))));
//                    sb.setRemark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Remark")));
//                    sb.setStoreCategory(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Category")));
//                    sb.setStoreCategoryId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Category_Id"))));
//                    sb.setStoreId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Id"))));
//                    sb.setStoreName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Name")));
//                    sb.setStoreType(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Type")));
//                    sb.setStoreTypeId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Type_Id"))));
//                    sb.setSubReasonId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Sub_Reason_Id"))));
//                    sb.setTierId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Tier_Id"))));
//                    sb.setTradeAreaId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Trade_Area_Id"))));
//                    sb.setUploadStatus(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Upload_Status")));
//                    sb.setVisitDate(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Visit_Date")));
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return sb;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception get JCP!", e.toString());
//            return sb;
//        }
//
//        return sb;
//    }
//
//
//    //get specific store data
//    public JourneyPlan getSpecificStoreDataforUpload(String date, String store_id, String jcp) {
//        JourneyPlan sb = new JourneyPlan();
//        Cursor dbcursor = null;
//
//        try {
//
//            dbcursor = db.rawQuery("SELECT * FROM " + jcp + " where Visit_Date ='" + date + "' and Store_Id = '" + store_id + "'   ", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    sb.setEmpId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Emp_Id"))));
//                    sb.setAddress1(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Address1")));
//                    sb.setAddress2(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Address2")));
//                    sb.setCity(dbcursor.getString(dbcursor.getColumnIndexOrThrow("City")));
//                    sb.setCityId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("City_Id"))));
//                    sb.setClassification(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Classification")));
//                    sb.setClassificationId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Classification_Id"))));
//                    sb.setDistributorId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Distributor_Id"))));
//                    sb.setEmpId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Emp_Id"))));
//                    sb.setGeoTag(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Geo_Tag")));
//                    sb.setLandmark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Landmark")));
//                    sb.setPincode(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Pincode")));
//                    sb.setReasonId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Reason_Id"))));
//                    sb.setRemark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Remark")));
//                    sb.setStoreCategory(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Category")));
//                    sb.setStoreCategoryId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Category_Id"))));
//                    sb.setStoreId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Id"))));
//                    sb.setStoreName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Name")));
//                    sb.setStoreType(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Type")));
//                    sb.setStoreTypeId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Type_Id"))));
//                    sb.setSubReasonId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Sub_Reason_Id"))));
//                    sb.setTierId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Tier_Id"))));
//                    sb.setTradeAreaId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Trade_Area_Id"))));
//                    sb.setUploadStatus(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Upload_Status")));
//                    sb.setVisitDate(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Visit_Date")));
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return sb;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception get JCP!", e.toString());
//            return sb;
//        }
//
//        return sb;
//    }
//
//
//    //get specific store data
//    public JourneyPlan getSpecificStoreUploadStatus(String date, String store_id, String jcp) {
//        JourneyPlan sb = new JourneyPlan();
//        Cursor dbcursor = null;
//
//        try {
//
//            dbcursor = db.rawQuery("SELECT * FROM " + jcp + " where Visit_Date ='" + date + "' and Store_Id = '" + store_id + "'", null);
//
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    sb.setEmpId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Emp_Id"))));
//                    sb.setAddress1(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Address1")));
//                    sb.setAddress2(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Address2")));
//                    sb.setCity(dbcursor.getString(dbcursor.getColumnIndexOrThrow("City")));
//                    sb.setCityId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("City_Id"))));
//                    sb.setClassification(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Classification")));
//                    sb.setClassificationId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Classification_Id"))));
//                    sb.setDistributorId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Distributor_Id"))));
//                    sb.setEmpId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Emp_Id"))));
//                    sb.setGeoTag(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Geo_Tag")));
//                    sb.setLandmark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Landmark")));
//                    sb.setPincode(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Pincode")));
//                    sb.setReasonId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Reason_Id"))));
//                    sb.setRemark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Remark")));
//                    sb.setStoreCategory(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Category")));
//                    sb.setStoreCategoryId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Category_Id"))));
//                    sb.setStoreId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Id"))));
//                    sb.setStoreName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Name")));
//                    sb.setStoreType(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Type")));
//                    sb.setStoreTypeId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Type_Id"))));
//                    sb.setSubReasonId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Sub_Reason_Id"))));
//                    sb.setTierId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Tier_Id"))));
//                    sb.setTradeAreaId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Trade_Area_Id"))));
//                    sb.setUploadStatus(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Upload_Status")));
//                    sb.setVisitDate(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Visit_Date")));
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return sb;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception get JCP!", e.toString());
//            return sb;
//        }
//
//        return sb;
//    }
//
//
//    //get specific store data
//    public JourneyPlan getSpecificPreviousDayStoreUploadStatus(String date, String store_id, String jcp) {
//        JourneyPlan sb = new JourneyPlan();
//        Cursor dbcursor = null;
//
//        try {
//
//            dbcursor = db.rawQuery("SELECT * FROM " + jcp + " where Visit_Date <>'" + date + "' and Store_Id = '" + store_id + "'", null);
//
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    sb.setEmpId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Emp_Id"))));
//                    sb.setAddress1(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Address1")));
//                    sb.setAddress2(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Address2")));
//                    sb.setCity(dbcursor.getString(dbcursor.getColumnIndexOrThrow("City")));
//                    sb.setCityId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("City_Id"))));
//                    sb.setClassification(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Classification")));
//                    sb.setClassificationId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Classification_Id"))));
//                    sb.setDistributorId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Distributor_Id"))));
//                    sb.setEmpId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Emp_Id"))));
//                    sb.setGeoTag(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Geo_Tag")));
//                    sb.setLandmark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Landmark")));
//                    sb.setPincode(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Pincode")));
//                    sb.setReasonId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Reason_Id"))));
//                    sb.setRemark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Remark")));
//                    sb.setStoreCategory(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Category")));
//                    sb.setStoreCategoryId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Category_Id"))));
//                    sb.setStoreId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Id"))));
//                    sb.setStoreName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Name")));
//                    sb.setStoreType(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Type")));
//                    sb.setStoreTypeId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Type_Id"))));
//                    sb.setSubReasonId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Sub_Reason_Id"))));
//                    sb.setTierId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Tier_Id"))));
//                    sb.setTradeAreaId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Trade_Area_Id"))));
//                    sb.setUploadStatus(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Upload_Status")));
//                    sb.setVisitDate(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Visit_Date")));
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return sb;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception get JCP!", e.toString());
//            return sb;
//        }
//
//        return sb;
//    }
//
//
//    //get specific store data
//    public JourneyPlan getSpecificStoreUploadStatusPreviousDay(String date, String store_id, String jcp) {
//        JourneyPlan sb = new JourneyPlan();
//        Cursor dbcursor = null;
//
//        try {
//
//            dbcursor = db.rawQuery("SELECT * FROM " + jcp + " where Visit_Date <>'" + date + "' and Store_Id = '" + store_id + "'", null);
//
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    sb.setEmpId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Emp_Id"))));
//                    sb.setAddress1(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Address1")));
//                    sb.setAddress2(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Address2")));
//                    sb.setCity(dbcursor.getString(dbcursor.getColumnIndexOrThrow("City")));
//                    sb.setCityId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("City_Id"))));
//                    sb.setClassification(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Classification")));
//                    sb.setClassificationId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Classification_Id"))));
//                    sb.setDistributorId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Distributor_Id"))));
//                    sb.setEmpId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Emp_Id"))));
//                    sb.setGeoTag(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Geo_Tag")));
//                    sb.setLandmark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Landmark")));
//                    sb.setPincode(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Pincode")));
//                    sb.setReasonId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Reason_Id"))));
//                    sb.setRemark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Remark")));
//                    sb.setStoreCategory(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Category")));
//                    sb.setStoreCategoryId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Category_Id"))));
//                    sb.setStoreId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Id"))));
//                    sb.setStoreName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Name")));
//                    sb.setStoreType(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Type")));
//                    sb.setStoreTypeId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Type_Id"))));
//                    sb.setSubReasonId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Sub_Reason_Id"))));
//                    sb.setTierId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Tier_Id"))));
//                    sb.setTradeAreaId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Trade_Area_Id"))));
//                    sb.setUploadStatus(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Upload_Status")));
//                    sb.setVisitDate(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Visit_Date")));
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return sb;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception get JCP!", e.toString());
//            return sb;
//        }
//
//        return sb;
//    }
//
//
//    public boolean isDeviationData(String status, String date) {
//
//        boolean filled = false;
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("SELECT  * from  Sup_Team_List where Today_PJP = '" + status + "' and Visit_Date ='" + date + "' ", null);
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                int icount = dbcursor.getInt(0);
//                dbcursor.close();
//                if (icount > 0) {
//                    filled = true;
//                } else {
//                    filled = false;
//                }
//            }
//        } catch (Exception e) {
//            Log.d("Exception ", " when fetching Records!!!!!!!!!!!!!!!!!!!!! " + e.toString());
//            return filled;
//        }
//        return filled;
//    }
//
//
//    public ArrayList<JourneyPlan> getStoreDataFiled(String visit_date, String outlet_today, String today_pjp) {
//
//        ArrayList<JourneyPlan> list = new ArrayList<JourneyPlan>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("SELECT distinct * from  " + outlet_today + " as jp inner join Sup_Team_List as stl on stl.Emp_Id = jp.Emp_Id where jp.Visit_Date ='" + visit_date + "' and stl.Today_PJP = '" + today_pjp + "' ", null);
//
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//
//                    JourneyPlan sb = new JourneyPlan();
//                    sb.setEmpId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Emp_Id"))));
//                    sb.setAddress1(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Address1")));
//                    sb.setAddress2(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Address2")));
//                    sb.setCity(dbcursor.getString(dbcursor.getColumnIndexOrThrow("City")));
//                    sb.setCityId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("City_Id"))));
//                    sb.setClassification(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Classification")));
//                    sb.setClassificationId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Classification_Id"))));
//                    sb.setDistributorId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Distributor_Id"))));
//                    sb.setEmpId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Emp_Id"))));
//                    sb.setGeoTag(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Geo_Tag")));
//                    sb.setLandmark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Landmark")));
//                    sb.setPincode(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Pincode")));
//                    sb.setReasonId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Reason_Id"))));
//                    sb.setRemark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Remark")));
//                    sb.setStoreCategory(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Category")));
//                    sb.setStoreCategoryId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Category_Id"))));
//                    sb.setStoreId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Id"))));
//                    sb.setStoreName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Name")));
//                    sb.setStoreType(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Type")));
//                    sb.setStoreTypeId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Type_Id"))));
//                    sb.setSubReasonId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Sub_Reason_Id"))));
//                    sb.setTierId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Tier_Id"))));
//                    sb.setTradeAreaId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Trade_Area_Id"))));
//                    sb.setUploadStatus(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Upload_Status")));
//                    sb.setVisitDate(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Visit_Date")));
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Crashlytics.logException(e);
//            Log.d("Exception when fetc", e.toString());
//            return list;
//        }
//
//        Log.d("FetchingStore", "-------------------");
//        return list;
//
//    }
//
//    public ArrayList<RatingGetterSetter> getMerchandiserRatingList(Integer storeId) {
//        ArrayList<RatingGetterSetter> list = new ArrayList<>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("SELECT  * from " + CommonString.Table_Merchandiser_Rating + " where " + CommonString.KEY_STORE_ID + "=" + storeId + " ",
//                    null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    RatingGetterSetter rgs = new RatingGetterSetter();
//                    rgs.setStoreId(String.valueOf(dbcursor.getInt(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_STORE_ID))));
//                    rgs.setMerId(dbcursor.getInt(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_MERCHENDISER_ID)));
//                    rgs.setRating(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_RATING)));
//                    rgs.setUserId(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_USER_ID)));
//                    rgs.setVisitedDate(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_VISIT_DATE)));
//
//                    list.add(rgs);
//                    dbcursor.moveToNext();
//                }
//
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception get JCP!", e.toString());
//            return list;
//        }
//        return list;
//    }
//
//    public ArrayList<PosmGetterSetter> getPOSMData(String storeId) {
//
//        ArrayList<PosmGetterSetter> list = new ArrayList<>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("SELECT  * from " + CommonString.TABLE_POSM + " where " + CommonString.KEY_STORE_ID + "=" + storeId + " ",
//                    null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    PosmGetterSetter POSM = new PosmGetterSetter();
//
//                    POSM.setStoreId(String.valueOf(dbcursor.getInt(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_STORE_ID))));
//                    POSM.setBrand_id(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_BRAND_ID)));
//                    POSM.setMerchandiser_MID(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_MER_MID)));
//                    POSM.setPosm_mer_qty(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_MER_STOCK_QUY)));
//                    POSM.setPosm_qty(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_QTY)));
//                    POSM.setPosm_id(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_POSM_ID)));
//                    POSM.setPosm_image(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_IMAGE1)));
//                    POSM.setType(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_TYPE)));
//
//                    list.add(POSM);
//                    dbcursor.moveToNext();
//                }
//
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception get JCP!", e.toString());
//            return list;
//        }
//        return list;
//    }
//
//
//    public ArrayList<PosmGetterSetter> getSSPOSMData(String storeId) {
//
//        ArrayList<PosmGetterSetter> list = new ArrayList<>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("SELECT  * from " + CommonString.TABLE_SS_POSM + " where " + CommonString.KEY_STORE_ID + "=" + storeId + " ",
//                    null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    PosmGetterSetter POSM = new PosmGetterSetter();
//
//                    POSM.setStoreId(String.valueOf(dbcursor.getInt(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_STORE_ID))));
//                    POSM.setBrand_id(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_BRAND_ID)));
//                    POSM.setMerchandiser_MID(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_MER_MID)));
//                    POSM.setPosm_mer_qty(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_MER_STOCK_QUY)));
//                    POSM.setPosm_qty(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_QTY)));
//                    POSM.setPosm_id(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_POSM_ID)));
//                    POSM.setPosm_image(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_IMAGE1)));
//                    POSM.setType(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_TYPE)));
//
//                    list.add(POSM);
//                    dbcursor.moveToNext();
//                }
//
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception get JCP!", e.toString());
//            return list;
//        }
//        return list;
//    }
//
//
//    public ArrayList<WindowMaster> getWindowDataUpload(String storeid) {
//
//        Log.d("FetchingStored",
//                "------------------");
//        ArrayList<WindowMaster> list = new ArrayList<>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("SELECT  * from " + CommonString.TABLE_SECONDARY_WINDOW_HEADER + "  " +
//                    "where " + CommonString.KEY_STORE_ID + " =" + storeid + "", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    WindowMaster sb = new WindowMaster();
//                    sb.setImage(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE)));
//                    sb.setLocation_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_LOCATION_CD)));
//                    sb.setLocation(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_LOCATION)));
//                    sb.setRemark(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REMARK)));
//                    sb.setWindow(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_WINDOW)));
//                    sb.setKey_id(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ID)));
//                    sb.setWindow_Exist(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_EXIST)));
//                    sb.setBrand_group_id(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_BRAND_GROUP_ID)));
//                    sb.setMerchandiser_MID(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_MER_MID)));
//                    sb.setWindowId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_WINDOW_ID))));
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception ", e.getMessage());
//            return list;
//        }
//
//        Log.d("FetchingS", "-------------------");
//        return list;
//
//    }
//
//    public ArrayList<WindowChecklist> getCheckListDataUpload(String storeid, String common_id) {
//
//        Log.d("FetchingStored", "------------------");
//        ArrayList<WindowChecklist> list = new ArrayList<>();
//        Cursor dbcursor = null;
//
//        try {
//
//            dbcursor = db.rawQuery("SELECT  * from "
//                    + CommonString.TABLE_SECONDARY_WINDOW_CHILD + " where "
//                    + CommonString.KEY_STORE_ID + " = '" + storeid + "' AND "
//                    + CommonString.KEY_COMMON_ID + " = '" + common_id + "'", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    WindowChecklist sb = new WindowChecklist();
//                    sb.setKey_id(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ID)));
//                    sb.setCommon_id(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_COMMON_ID)));
//                    sb.setIsExit(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_SUP_COMPLIANCE)));
//                    sb.setChecklistId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CHECKLIST_ID))));
//                    sb.setMerchandiser_mid(String.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_MER_MID))));
//                    sb.setChecklist(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CHECKLIST)));
//                    sb.setComplilance(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_MER_COMPLIANCE)));
//                    sb.setRemark(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REMARK)));
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception ", e.getMessage());
//            return list;
//        }
//
//        Log.d("FetchingS", "-------------------");
//        return list;
//
//    }
//
//    public ArrayList<JourneyPlan> getJCPData(String visitDate, String sup_jounry_plan) {
//        ArrayList<JourneyPlan> list = new ArrayList<JourneyPlan>();
//        Cursor dbcursor = null;
//        try {
//            dbcursor = db.rawQuery("SELECT distinct * from " + sup_jounry_plan + " where Visit_Date ='" + visitDate + "'", null);
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//
//                    JourneyPlan sb = new JourneyPlan();
//                    sb.setEmpId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Emp_Id"))));
//                    sb.setAddress1(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Address1")));
//                    sb.setAddress2(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Address2")));
//                    sb.setCity(dbcursor.getString(dbcursor.getColumnIndexOrThrow("City")));
//                    sb.setCityId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("City_Id"))));
//                    sb.setClassification(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Classification")));
//                    sb.setClassificationId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Classification_Id"))));
//                    sb.setDistributorId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Distributor_Id"))));
//                    sb.setEmpId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Emp_Id"))));
//                    sb.setGeoTag(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Geo_Tag")));
//                    sb.setLandmark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Landmark")));
//                    sb.setPincode(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Pincode")));
//                    sb.setReasonId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Reason_Id"))));
//                    sb.setRemark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Remark")));
//                    sb.setStoreCategory(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Category")));
//                    sb.setStoreCategoryId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Category_Id"))));
//                    sb.setStoreId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Id"))));
//                    sb.setStoreName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Name")));
//                    sb.setStoreType(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Type")));
//                    sb.setStoreTypeId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Type_Id"))));
//                    sb.setSubReasonId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Sub_Reason_Id"))));
//                    sb.setTierId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Tier_Id"))));
//                    sb.setTradeAreaId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Trade_Area_Id"))));
//                    sb.setUploadStatus(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Upload_Status")));
//                    sb.setVisitDate(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Visit_Date")));
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Crashlytics.logException(e);
//            Log.d("Exception when fetc", e.toString());
//            return list;
//        }
//
//        Log.d("FetchingStore", "-------------------");
//        return list;
//    }
//
//    public ArrayList<JourneyPlan> getTodayStoreData(String visit_date, String outlet_today) {
//
//        ArrayList<JourneyPlan> list = new ArrayList<JourneyPlan>();
//        Cursor dbcursor = null;
//
//        try {
//
//            dbcursor = db.rawQuery("SELECT distinct * from  " + outlet_today + " where Visit_Date <>'" + visit_date + "' ", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//
//                    JourneyPlan sb = new JourneyPlan();
//                    sb.setEmpId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Emp_Id"))));
//                    sb.setAddress1(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Address1")));
//                    sb.setAddress2(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Address2")));
//                    sb.setCity(dbcursor.getString(dbcursor.getColumnIndexOrThrow("City")));
//                    sb.setCityId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("City_Id"))));
//                    sb.setClassification(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Classification")));
//                    sb.setClassificationId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Classification_Id"))));
//                    sb.setDistributorId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Distributor_Id"))));
//                    sb.setEmpId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Emp_Id"))));
//                    sb.setGeoTag(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Geo_Tag")));
//                    sb.setLandmark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Landmark")));
//                    sb.setPincode(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Pincode")));
//                    sb.setReasonId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Reason_Id"))));
//                    sb.setRemark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Remark")));
//                    sb.setStoreCategory(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Category")));
//                    sb.setStoreCategoryId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Category_Id"))));
//                    sb.setStoreId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Id"))));
//                    sb.setStoreName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Name")));
//                    sb.setStoreType(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Type")));
//                    sb.setStoreTypeId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Type_Id"))));
//                    sb.setSubReasonId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Sub_Reason_Id"))));
//                    sb.setTierId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Tier_Id"))));
//                    sb.setTradeAreaId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Trade_Area_Id"))));
//                    sb.setUploadStatus(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Upload_Status")));
//                    sb.setVisitDate(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Visit_Date")));
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Crashlytics.logException(e);
//            Log.d("Exception when fetc", e.toString());
//            return list;
//        }
//
//        Log.d("FetchingStore", "-------------------");
//        return list;
//    }
//
//    public ArrayList<JourneyPlan> getPreviousDayStoreData(String visit_date, String outlet_today) {
//
//        ArrayList<JourneyPlan> list = new ArrayList<JourneyPlan>();
//        Cursor dbcursor = null;
//
//
//        try {
//
//            dbcursor = db.rawQuery("SELECT distinct * from  " + outlet_today + " where Visit_Date <>'" + visit_date + "' ", null);
//
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//
//                    JourneyPlan sb = new JourneyPlan();
//                    sb.setEmpId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Emp_Id"))));
//                    sb.setAddress1(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Address1")));
//                    sb.setAddress2(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Address2")));
//                    sb.setCity(dbcursor.getString(dbcursor.getColumnIndexOrThrow("City")));
//                    sb.setCityId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("City_Id"))));
//                    sb.setClassification(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Classification")));
//                    sb.setClassificationId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Classification_Id"))));
//                    sb.setDistributorId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Distributor_Id"))));
//                    sb.setEmpId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Emp_Id"))));
//                    sb.setGeoTag(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Geo_Tag")));
//                    sb.setLandmark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Landmark")));
//                    sb.setPincode(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Pincode")));
//                    sb.setReasonId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Reason_Id"))));
//                    sb.setRemark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Remark")));
//                    sb.setStoreCategory(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Category")));
//                    sb.setStoreCategoryId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Category_Id"))));
//                    sb.setStoreId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Id"))));
//                    sb.setStoreName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Name")));
//                    sb.setStoreType(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Type")));
//                    sb.setStoreTypeId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Type_Id"))));
//                    sb.setSubReasonId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Sub_Reason_Id"))));
//                    sb.setTierId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Tier_Id"))));
//                    sb.setTradeAreaId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Trade_Area_Id"))));
//                    sb.setUploadStatus(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Upload_Status")));
//                    sb.setVisitDate(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Visit_Date")));
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Crashlytics.logException(e);
//            Log.d("Exception when fetc", e.toString());
//            return list;
//        }
//
//        Log.d("FetchingStore", "-------------------");
//        return list;
//    }
//
//    public void deleteCompleteData(String sup_jounry_plan_previous) {
//        db.delete(sup_jounry_plan_previous, null, null);
//        db.delete("Sup_Primary_Shelf_Audit", null, null);
//        db.delete("Sup_Mapping_Stock", null, null);
//        db.delete("Sup_Touchpoint_Audit", null, null);
//        db.delete("Sup_Window_Audit", null, null);
//        db.delete("Sup_SelfService_SecondaryWindow_Audit", null, null);
//        db.delete("Sup_Mapping_Selfservice_Category", null, null);
//        db.delete("Sup_Mapping_Selfservice_Category_Display", null, null);
//        db.delete("Sup_SelfService_Promotion_Audit", null, null);
//        db.delete("Sup_Selfservice_Promotion_Competition_Audit", null, null);
//        db.delete("Sup_Selfservice_Touchpoint_Audit", null, null);
//        db.delete("Sup_SelfService_Primary_Audit", null, null);
//
//    }
//
//
//    public SecondaryWindowGetterSetter getBackwallDataSecondryWindow(String date) {
//        Cursor dbcursor = null;
//        SecondaryWindowGetterSetter sb = new SecondaryWindowGetterSetter();
//        try {
//
//            dbcursor = db.rawQuery("SELECT distinct * from  " + CommonString.TABLE_SECONDARY_BACKWALL_DATA + " where Visit_Date ='" + date + "' ", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    sb.setLocation_id(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_LOCATION_CD)));
//                    sb.setVisitDate(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_VISIT_DATE)));
//                    sb.setBackwall_image(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE_BACKWALL)));
//                    sb.setRediobutton_color(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REDIOBUTTON_COLOR)));
//                    sb.setLocation(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_LOCATION)));
//                    sb.setStore_id(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_STORE_ID)));
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return sb;
//            }
//
//        } catch (Exception e) {
//            Crashlytics.logException(e);
//            Log.d("Exception when fetc", e.toString());
//            return sb;
//        }
//
//        Log.d("FetchingStore", "-------------------");
//        return sb;
//    }
//
//    public boolean SupMyPerformanceObj(SupMyPerformanceGetterSetter supMyPerformanceObj) {
//        db.delete("Sup_My_Performance", null, null);
//        ContentValues values = new ContentValues();
//        List<SupMyPerformance> data = supMyPerformanceObj.getSupMyPerformance();
//        try {
//            if (data.size() == 0) {
//                return false;
//            }
//            for (int i = 0; i < data.size(); i++) {
//                values.put(CommonString.Manager_Id, data.get(i).getManagerId());
//                values.put(CommonString.Key_Performance, data.get(i).getKeyPerformance());
//                values.put(CommonString.CurrentMonth_Per, data.get(i).getCurrentMonthPer());
//                values.put(CommonString.LastMonth_Per, data.get(i).getLastMonthPer());
//                values.put(CommonString.LastQuarter_Per, data.get(i).getLastQuarterPer());
//
//                long id = db.insert("Sup_My_Performance", null, values);
//                if (id == -1) {
//                    throw new Exception();
//                }
//            }
//            return true;
//        } catch (Exception ex) {
//            Crashlytics.logException(ex);
//            ex.printStackTrace();
//            Log.d("Database Sup_My_Performance", ex.toString());
//            return false;
//        }
//    }
//
//    public boolean SupMyTeamPerformanceObj(SupMyTeamPerformanceGetterSetter supMyTeamPerformanceObj) {
//        db.delete("Sup_My_Team_Performance", null, null);
//        ContentValues values = new ContentValues();
//        List<SupMyTeamPerformance> data = supMyTeamPerformanceObj.getSupMyTeamPerformance();
//        try {
//            if (data.size() == 0) {
//                return false;
//            }
//            for (int i = 0; i < data.size(); i++) {
//                values.put(CommonString.Emp_Id, data.get(i).getEmpId());
//                values.put(CommonString.Legacy_Code, data.get(i).getLegacyCode());
//                values.put(CommonString.Employee, data.get(i).getEmployee());
//                values.put(CommonString.Key_Performance, data.get(i).getKeyPerformance());
//                values.put(CommonString.CurrentMonth_Per, data.get(i).getCurrentMonthPer());
//                values.put(CommonString.LastMonth_Per, data.get(i).getLastMonthPer());
//                values.put(CommonString.LastQuarter_Per, data.get(i).getLastQuarterPer());
//
//                long id = db.insert("Sup_My_Team_Performance", null, values);
//                if (id == -1) {
//                    throw new Exception();
//                }
//            }
//            return true;
//        } catch (Exception ex) {
//            Crashlytics.logException(ex);
//            ex.printStackTrace();
//            Log.d("Database Sup_My_Team_Performance", ex.toString());
//            return false;
//        }
//    }
//
//    public ArrayList<SupMyPerformance> getMyPerformanceData() {
//        ArrayList<SupMyPerformance> list = new ArrayList<SupMyPerformance>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("SELECT  * from Sup_My_Performance ORDER BY Key_Performance", null);
//
//            if (dbcursor != null) {
//
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    SupMyPerformance sb = new SupMyPerformance();
//
//                    sb.setManagerId(Integer.valueOf(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Manager_Id))));
//                    sb.setCurrentMonthPer(Integer.valueOf((((dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.CurrentMonth_Per)))))));
//                    sb.setKeyPerformance(((dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Key_Performance)))));
//                    sb.setLastMonthPer(Integer.valueOf(((dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.LastMonth_Per))))));
//                    sb.setLastQuarterPer(Integer.valueOf((((dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.LastQuarter_Per)))))));
//
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//        } catch (Exception e) {
//            Log.d("Exception get JCP!", e.toString());
//            return list;
//        }
//        return list;
//    }
//
//    public ArrayList<SupMyTeamPerformance> getMyTeamPerformanceData() {
//        ArrayList<SupMyTeamPerformance> list = new ArrayList<SupMyTeamPerformance>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("SELECT  * from Sup_My_Team_Performance", null);
//
//            if (dbcursor != null) {
//
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    SupMyTeamPerformance sb = new SupMyTeamPerformance();
//
//                    sb.setEmpId(Integer.valueOf(String.valueOf(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Emp_Id)))));
//                    sb.setLegacyCode(String.valueOf(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Legacy_Code))));
//                    sb.setEmployee(String.valueOf(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Employee))));
//                    sb.setCurrentMonthPer(Integer.valueOf((((dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.CurrentMonth_Per)))))));
//                    sb.setKeyPerformance(((dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Key_Performance)))));
//                    sb.setLastMonthPer(Integer.valueOf(((dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.LastMonth_Per))))));
//                    sb.setLastQuarterPer(Integer.valueOf((((dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.LastQuarter_Per)))))));
//
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//        } catch (Exception e) {
//            Log.d("Exception get JCP!", e.toString());
//            return list;
//        }
//        return list;
//    }
//
//    public ArrayList<SupMyTeamPerformance> getEmployeeUniqueList() {
//        ArrayList<SupMyTeamPerformance> list = new ArrayList<SupMyTeamPerformance>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("SELECT  distinct Emp_id, Legacy_Code, Employee from Sup_My_Team_Performance order by Employee", null);
//
//            if (dbcursor != null) {
//
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    SupMyTeamPerformance sb = new SupMyTeamPerformance();
//
//                    sb.setEmpId(Integer.valueOf(String.valueOf(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Emp_Id)))));
//
//                    sb.setLegacyCode(String.valueOf(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Legacy_Code))));
//
//                    sb.setEmployee(String.valueOf(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Employee))));
//
//
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//        } catch (Exception e) {
//            Log.d("Exception get JCP!", e.toString());
//            return list;
//        }
//        return list;
//    }
//
//
//    public ArrayList<SupTeamPerformanceList> getMyTeamPerformancelist(Integer empId) {
//        ArrayList<SupTeamPerformanceList> myTeamPerformanceList = new ArrayList<SupTeamPerformanceList>();
//        Cursor dbcursor = null;
//        try {
//            dbcursor = db.rawQuery("SELECT  * from Sup_My_Team_Performance where  Emp_id = " + empId + "", null);
//
//            if (dbcursor != null) {
//
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    SupTeamPerformanceList sb = new SupTeamPerformanceList();
//
//                    sb.setCurrentMonthPer(Integer.valueOf((((dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.CurrentMonth_Per)))))));
//                    sb.setKeyPerformance(((dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Key_Performance)))));
//                    sb.setLastMonthPer(Integer.valueOf(((dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.LastMonth_Per))))));
//                    sb.setLastQuarterPer(Integer.valueOf((((dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.LastQuarter_Per)))))));
//
//                    myTeamPerformanceList.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return myTeamPerformanceList;
//            }
//        } catch (Exception e) {
//            Log.d("Exception get JCP!", e.toString());
//            return myTeamPerformanceList;
//        }
//        return myTeamPerformanceList;
//    }
//
//
//    // get Visitor Login
//    public ArrayList<VisitorDetail> getVisitorLoginData(String visitdate) {
//
//        ArrayList<VisitorDetail> list = new ArrayList<VisitorDetail>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("SELECT  * from TABLE_VISITOR_LOGIN where VISIT_DATE = '" + visitdate + "'"
//                    , null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    VisitorDetail sb = new VisitorDetail();
//                    sb.setEmpId(Integer.valueOf(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_EMP_CD))));
//                    sb.setName(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_NAME)));
//                    sb.setDesignation(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_DESIGNATION)));
//                    sb.setIn_time_img(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_IN_TIME_IMAGE)));
//                    sb.setOut_time_img(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_OUT_TIME_IMAGE)));
//                    sb.setEmp_code(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_EMP_CODE)));
//                    sb.setVisit_date(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_VISIT_DATE)));
//                    sb.setIn_time(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_IN_TIME)));
//                    sb.setOut_time(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_OUT_TIME)));
//                    sb.setUpload_status(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_UPLOADSTATUS)));
//
//
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception when fetching Visitor Login!!!!!!!!!!!!!!!!!!!!!",
//                    e.toString());
//            return list;
//        }
//        return list;
//
//    }
//
//
//    //Insert Visitor login
//    public void InsertVisitorLogindata(ArrayList<VisitorDetail> visitorLoginGetterSetter) {
//
//        db.delete(CommonString.TABLE_VISITOR_LOGIN, null, null);
//        ContentValues values = new ContentValues();
//        try {
//            for (int i = 0; i < visitorLoginGetterSetter.size(); i++) {
//
//                values.put(CommonString.KEY_EMP_CD, visitorLoginGetterSetter.get(i).getEmpId());
//                values.put(CommonString.KEY_NAME, visitorLoginGetterSetter.get(i).getName());
//                values.put(CommonString.KEY_DESIGNATION, visitorLoginGetterSetter.get(i).getDesignation());
//                values.put(CommonString.KEY_IN_TIME_IMAGE, visitorLoginGetterSetter.get(i).getIn_time_img());
//                values.put(CommonString.KEY_OUT_TIME_IMAGE, visitorLoginGetterSetter.get(i).getOut_time_img());
//                values.put(CommonString.KEY_EMP_CODE, visitorLoginGetterSetter.get(i).getEmp_code());
//                values.put(CommonString.KEY_VISIT_DATE, visitorLoginGetterSetter.get(i).getVisit_date());
//                values.put(CommonString.KEY_IN_TIME, visitorLoginGetterSetter.get(i).getIn_time());
//                values.put(CommonString.KEY_OUT_TIME, visitorLoginGetterSetter.get(i).getOut_time());
//                values.put(CommonString.KEY_UPLOADSTATUS, visitorLoginGetterSetter.get(i).getUpload_status());
//
//                db.insert(CommonString.TABLE_VISITOR_LOGIN, null, values);
//
//            }
//        } catch (Exception ex) {
//            Log.d("Database Exception while Insert TABLE_VISITOR_LOGIN",
//                    ex.toString());
//        }
//    }
//
//
//    //Update out_time n image Visitor Login
//
//    public void updateOutTimeVisitorLoginData(String out_time_image, String out_time, String emp_id) {
//
//        try {
//            ContentValues values = new ContentValues();
//            values.put(CommonString.KEY_OUT_TIME_IMAGE, out_time_image);
//            values.put(CommonString.KEY_OUT_TIME, out_time);
//
//            db.update(CommonString.TABLE_VISITOR_LOGIN, values,
//                    CommonString.KEY_EMP_CD + "='" + emp_id + "'", null);
//
//        } catch (Exception e) {
//            Log.d("Exception when Udating Visitor Data!!!!!!!!!!!!!!!!!!!!!",
//                    e.toString());
//
//        }
//    }
//
//
//    // Check is data already exists Visitor Login
//    public boolean isVistorDataExists(String emp_id, String visitdate) {
//
//        Log.d("FetchingVisitor Login List --------------->Start<------------",
//                "------------------");
//        Cursor dbcursor = null;
//        try {
//            dbcursor = db.rawQuery("SELECT  * from TABLE_VISITOR_LOGIN where EMP_CD = '" + emp_id + "' and " + CommonString.KEY_VISIT_DATE + " = '" + visitdate + "'"
//                    , null);
//            int count = 0;
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//
//                    count++;
//
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//
//                if (count > 0) {
//                    return true;
//                } else {
//                    return false;
//                }
//
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception when fetching Visitor Login!!!!!!!!!!!!!!!!!!!!!",
//                    e.toString());
//            return false;
//        }
//
//        Log.d("FetchingVisitor Login---------------------->Stop<-----------",
//                "-------------------");
//        return false;
//    }
//
//    //Update Visitor Login Upload Data
//
//    public void updateVisitorUploadData(String empid) {
//
//        try {
//            ContentValues values = new ContentValues();
//            values.put(CommonString.KEY_UPLOADSTATUS, "U");
//
//            db.update(CommonString.TABLE_VISITOR_LOGIN, values,
//                    CommonString.KEY_EMP_CD + "='" + empid + "'", null);
//        } catch (Exception e) {
//            Log.d("Exception updating Visitor Upload status Data!!!!!!!!!!!!!!!!!!!!!",
//                    e.toString());
//
//        }
//    }
//
//
//    public ArrayList<SupMerchandiserKitMaster> getMerToolKitData() {
//
//        ArrayList<SupMerchandiserKitMaster> list = new ArrayList<SupMerchandiserKitMaster>();
//        Cursor dbcursor = null;
//        try {
//
//            dbcursor = db.rawQuery("SELECT * from Sup_Merchandiser_Kit_Master order by Kit_Name", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    SupMerchandiserKitMaster sb = new SupMerchandiserKitMaster();
//
//                    sb.setKitId(Integer.valueOf(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Kit_Id))));
//                    sb.setKitName(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Kit_Name)));
//                    sb.setUploadStatus(Integer.valueOf(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Upload_Status))));
//
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception  Mer Tool",
//                    e.toString());
//            return list;
//        }
//
//        Log.d("Fetching Mechandiser",
//                "-------------------");
//        return list;
//    }
//
//
//    // get CATEGORY_MASTER
//    public ArrayList<CategoryMaster> getCategoryList(JourneyPlan journeyPlan) {
//
//        Log.d("FetchingCategory List --------------->Start<------------",
//                "------------------");
//        ArrayList<CategoryMaster> list = new ArrayList<CategoryMaster>();
//        Cursor dbcursor = null;
//        try {
//            dbcursor = db.rawQuery(" select distinct * from Sup_Mapping_Selfservice_Category m inner join Category_master c on m.Category_Id = c.Category_Id " +
//                    "where m.Trade_Area_Id = " + journeyPlan.getTradeAreaId() + " and m.Tier_Id = " + journeyPlan.getTierId() + " and m.Store_Type_Id = " + journeyPlan.getStoreTypeId() + " and m.Classification_Id = " + journeyPlan.getClassificationId() + " and m.Store_Category_Id = " + journeyPlan.getStoreCategoryId() + "", null);
//
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    CategoryMaster sb = new CategoryMaster();
//                    sb.setCategoryId(Integer.valueOf(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow("Category_Id"))));
//                    sb.setCategory(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow("Category")));
//                    sb.setIcon(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow("Icon")));
//                    sb.setIconDone(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow("Icon_Done")));
//                    //sb.setIconImage(R.drawable.image_category);
//
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception when fetching Brands!!!!!!!!!!!!!!!!!!!!!",
//                    e.toString());
//            return list;
//        }
//
//        Log.d("FetchingCategory---------------------->Stop<-----------",
//                "-------------------");
//        return list;
//    }
//
//
//    public ArrayList<PrimaryBayMaster> getPrimaryBayMaster(JourneyPlan jcp, String categoryId) {
//
//        ArrayList<PrimaryBayMaster> list = new ArrayList<>();
//        Cursor dbcursor = null;
//        try {
//
//            dbcursor = db.rawQuery(" SELECT b.Bay_Id, b.Bay,b.Image_Mandatory,ifnull(I.Image,'') as Image from Sup_Primary_Bay_Master b" +
//                    " left join (select * From PRIMARYBAY_IMAGE where Store_Id = " + jcp.getStoreId() + " and CATEGORY_ID = '" + categoryId + "' and Visit_date ='" + jcp.getVisitDate() + "') as I" +
//                    " on b.Bay_Id = I.Bay_Id where b.Category_Id = " + categoryId + "", null);
//
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//
//                    PrimaryBayMaster primaryBayGetset = new PrimaryBayMaster();
//
//                    primaryBayGetset.setBayId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Bay_Id"))));
//                    primaryBayGetset.setBay(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Bay")));
//                    primaryBayGetset.setImageMandatory("1".equals(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Image_Mandatory"))));
//                    primaryBayGetset.setImage(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Image")));
//
//                    list.add(primaryBayGetset);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
//                    e.toString());
//            return list;
//        }
//
//        return list;
//
//    }
//
//
//    public ArrayList<PrimaryBayMaster> getCategoryPictureMasterInPrimaryBaySS(String storeid, String categoryId) {
//
//        ArrayList<PrimaryBayMaster> list = new ArrayList<>();
//        Cursor dbcursor = null;
//        try {
//
//            if(!categoryId.equalsIgnoreCase("3") && !categoryId.equalsIgnoreCase("4")) {
//
//                dbcursor = db.rawQuery("select t1.*,IFNULL(t2.image,'') as Image from (" +
//                        "Select 'Cat_1' as Category,1 as IMAGE_ID,1 as Image_Mandatory union " +
//                        "Select 'Cat_2' as Category,2 as IMAGE_ID,0 as Image_Mandatory union " +
//                        "Select 'Cat_3' as Category,3 as IMAGE_ID,0 as Image_Mandatory union " +
//                        "Select 'Cat_4' as Category,4 as IMAGE_ID,0 as Image_Mandatory " +
//                        ") as t1 left outer  join " +
//                        "(Select * from PRIMARYBAY_CATEGORY_IMAGE) as t2 " +
//                        "on t1.IMAGE_ID = t2.IMAGE_ID and t2.STORE_ID = " + storeid + " and t2.CATEGORY_ID = " + categoryId + "", null);
//            }else{
//                dbcursor = db.rawQuery("select t1.*,IFNULL(t2.image,'') as Image from (" +
//                        "Select 'Cat_1' as Category,1 as IMAGE_ID,0 as Image_Mandatory union " +
//                        "Select 'Cat_2' as Category,2 as IMAGE_ID,0 as Image_Mandatory union " +
//                        "Select 'Cat_3' as Category,3 as IMAGE_ID,0 as Image_Mandatory union " +
//                        "Select 'Cat_4' as Category,4 as IMAGE_ID,0 as Image_Mandatory " +
//                        ") as t1 left outer  join " +
//                        "(Select * from PRIMARYBAY_CATEGORY_IMAGE) as t2 " +
//                        "on t1.IMAGE_ID = t2.IMAGE_ID and t2.STORE_ID = " + storeid + " and t2.CATEGORY_ID = " + categoryId + "", null);
//            }
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    PrimaryBayMaster primaryBayGetset = new PrimaryBayMaster();
//
//                    primaryBayGetset.setCategoryName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Category")));
//                    primaryBayGetset.setImageMandatory("1".equals(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Image_Mandatory"))));
//                    primaryBayGetset.setImage(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Image")));
//                    primaryBayGetset.setImageId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("IMAGE_ID")));
//
//                    list.add(primaryBayGetset);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
//                    e.toString());
//            return list;
//        }
//
//        return list;
//
//    }
//
//
//    public boolean insertSupShelfServiceSecondryWindowData(SupSelfServiceSecondaryWindowAuditGetterSetter supSelfServiceSecondaryWindowAuditObj) {
//        // db.delete("Sup_Window_Audit", null, null);
//        ContentValues values = new ContentValues();
//
//        List<SupSelfServiceSecondaryWindowAudit> data = supSelfServiceSecondaryWindowAuditObj.getSupSelfServiceSecondaryWindowAudit();
//        try {
//            if (data.size() == 0) {
//                return false;
//            }
//            for (int i = 0; i < data.size(); i++) {
//                values.put(CommonString.Brand_Id, data.get(i).getBrandId());
//                values.put(CommonString.Category_id, data.get(i).getCategoryId());
//                values.put(CommonString.Display_Id, data.get(i).getDisplayId());
//                values.put(CommonString.Store_Id, data.get(i).getStoreId());
//                values.put(CommonString.Facing, data.get(i).getFacing());
//                values.put(CommonString.Location_Id, data.get(i).getLocationId());
//                values.put(CommonString.Mer_MID, data.get(i).getMerMID());
//                values.put(CommonString.Total_Stock, data.get(i).getTotalStock());
//                values.put(CommonString.KEY_EMP_ID, data.get(i).getEmpId());
//                values.put("Visit_Date", data.get(i).getVisitDate());
//                long id = db.insert("Sup_SelfService_SecondaryWindow_Audit", null, values);
//                if (id == -1) {
//                    throw new Exception();
//                }
//
//            }
//            return true;
//        } catch (Exception ex) {
//            Crashlytics.logException(ex);
//            ex.printStackTrace();
//            return false;
//        }
//    }
//
//    public boolean insertSupMappingShelfServiceCategoryData(SupMappingSelfserviceCategoryGetterSetter supMappingSelfserviceCategoryObj) {
//        // db.delete("Sup_Window_Audit", null, null);
//        ContentValues values = new ContentValues();
//
//        List<SupMappingSelfserviceCategory> data = supMappingSelfserviceCategoryObj.getSupMappingSelfserviceCategory();
//        try {
//            if (data.size() == 0) {
//                return false;
//            }
//            for (int i = 0; i < data.size(); i++) {
//                values.put(CommonString.Classification_Id, data.get(i).getClassificationId());
//                values.put(CommonString.Category_id, data.get(i).getCategoryId());
//                values.put(CommonString.Store_Category_Id, data.get(i).getStoreCategoryId());
//                values.put(CommonString.Store_Type_Id, data.get(i).getStoreTypeId());
//                values.put(CommonString.Tier_Id, data.get(i).getTierId());
//                values.put(CommonString.Trade_Area_Id, data.get(i).getTradeAreaId());
//                long id = db.insert("Sup_Mapping_Selfservice_Category", null, values);
//                if (id == -1) {
//                    throw new Exception();
//                }
//
//            }
//            return true;
//        } catch (Exception ex) {
//            Crashlytics.logException(ex);
//            ex.printStackTrace();
//            return false;
//        }
//    }
//
//
//    // get Brand List
//    public ArrayList<BrandMaster> getBrandListInTouchpoint(String categtoryCd, String StoreTypeCD, String companyCd) {
//
//        ArrayList<BrandMaster> list = new ArrayList<>();
//        Cursor dbcursor = null;
//
//        try {
//            if (companyCd != null && companyCd.equalsIgnoreCase("1")) {
//                  dbcursor = db.rawQuery("select distinct bm.Brand_Id,bm.Brand from Brand_Master bm inner join Brand_Group_Master bgm on bm.Brand_Group_Id =" +
//                        " bgm.Brand_Group_Id where bgm.Company_Id <> 1 and bgm.Category_Id = " + categtoryCd + "", null);
//            } else {
//
//                dbcursor = db.rawQuery("select distinct bm.Brand_Id,bm.Brand from Brand_Master bm inner join Brand_Group_Master bgm on bm.Brand_Group_Id =" +
//                        " bgm.Brand_Group_Id where bgm.Company_Id = 1 and bgm.Category_Id = " + categtoryCd + "", null);
//            }
//
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    BrandMaster sb = new BrandMaster();
//
//                    sb.setBrandId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Brand_Id"))));
//                    sb.setBrand(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Brand")));
//
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception when fetching Brands!!!!!!!!!!!!!!!!!!!!!",
//                    e.toString());
//            return list;
//        }
//        return list;
//    }
//
//    // get WINDOW_LOCATION_SELFSERVICE
//
//    public ArrayList<WindowLocation> getWINDOW_LOCATION_SELFSERVICEData() {
//
//        Log.d("Fetchingloc->Start<-",
//                "------------------");
//        ArrayList<WindowLocation> list = new ArrayList<>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("SELECT * from Sup_Window_Location where SelfService = 1", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    WindowLocation sb = new WindowLocation();
//
//                    sb.setWindowLocationId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Window_Location_Id"))));
//                    sb.setWindowLocation(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Window_Location")));
//
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception location",
//                    e.getMessage());
//            return list;
//        }
//
//        Log.d("FetchStoredat->Stop<-",
//                "-");
//        return list;
//    }
//
//
//    //MAPPING_DISPLAY_CATEGORY
//    public ArrayList<SupMappingSelfserviceCategoryDisplay> getMAPPING_DISPLAY_CATEGORYData(String categoryCD) {
//
//        ArrayList<SupMappingSelfserviceCategoryDisplay> list = new ArrayList<>();
//        Cursor dbcursor = null;
//
//        try {
//
//            dbcursor = db.rawQuery("select m.Category_Id,m.Display_Id,d.Display,m.Image_Url from Sup_Mapping_Selfservice_Category_Display m INNER JOIN Sup_Display_Master d on m.Display_Id = d.Display_Id WHERE m.Category_Id = " + categoryCD, null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    SupMappingSelfserviceCategoryDisplay sb = new SupMappingSelfserviceCategoryDisplay();
//
//                    sb.setCategoryId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Category_Id"))));
//                    sb.setDisplayId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Display_Id"))));
//                    sb.setDisplay(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Display")));
//                    sb.setImageUrl(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Image_Url")));
//
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception location",
//                    e.getMessage());
//            return list;
//        }
//        return list;
//    }
//
//    // get Promotype list
//    public ArrayList<SupPromoTypeMaster> getPromotypeList() {
//
//        ArrayList<SupPromoTypeMaster> list = new ArrayList<SupPromoTypeMaster>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("SELECT * from Sup_Promo_Type_Master"
//                    , null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    SupPromoTypeMaster sb = new SupPromoTypeMaster();
//
//                    sb.setPromoTypeId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Promo_Type_Id"))));
//                    sb.setPromoType(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Promo_Type")));
//
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//
//            Log.d("Exception when fetching promotype!!!!!!!!!!!!!!!!!!!!!",
//                    e.toString());
//            return list;
//        }
//        return list;
//
//    }
//
//    public boolean insertSupMappingShelfServiceCategoryDisplayData(SupMappingSelfserviceCategoryDisplayGetterSetter data) {
//        // db.delete("Sup_Mapping_Selfservice_Category_Display", null, null);
//        List<SupMappingSelfserviceCategoryDisplay> list = data.getSupMappingSelfserviceCategoryDisplay();
//        ContentValues values = new ContentValues();
//        try {
//            if (list.size() == 0) {
//                return false;
//            }
//            for (int i = 0; i < list.size(); i++) {
//                values.put("Category_Id", list.get(i).getCategoryId());
//                values.put("Display_Id", list.get(i).getDisplayId());
//                values.put("Image_Url", list.get(i).getImageUrl());
//                long id = db.insert("Sup_Mapping_Selfservice_Category_Display", null, values);
//                if (id == -1) {
//                    throw new Exception();
//                }
//            }
//            return true;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            Log.d("Exception ", " in Sup_Mapping_Selfservice_Category_Display " + ex.toString());
//            return false;
//        }
//    }
//
//    public boolean insertSupSelfServicePromotionData(SupSelfServicePromotionAuditGetterSetter data) {
//        List<SupSelfServicePromotionAudit> list = data.getSupSelfServicePromotionAudit();
//        ContentValues values = new ContentValues();
//        try {
//            if (list.size() == 0) {
//                return false;
//            }
//            for (int i = 0; i < list.size(); i++) {
//                values.put(CommonString.Category_Id, list.get(i).getCategoryId());
//                values.put(CommonString.Description, list.get(i).getDescription());
//                values.put(CommonString.Brand_Id, list.get(i).getBrandId());
//                values.put(CommonString.Store_Id, list.get(i).getStoreId());
//                values.put(CommonString.Posm_Present, list.get(i).getPosmPresent());
//                values.put(CommonString.Promo_Type_Id, list.get(i).getPromoTypeId());
//                values.put(CommonString.Running, list.get(i).getRunning());
//                values.put(CommonString.Mer_MID, list.get(i).getMerMID());
//                values.put(CommonString.Total_Stock, list.get(i).getTotalStock());
//                values.put(CommonString.KEY_EMP_ID, list.get(i).getEmpId());
//                values.put("Visit_Date", list.get(i).getVisitDate());
//
//                long id = db.insert("Sup_SelfService_Promotion_Audit", null, values);
//                if (id == -1) {
//                    throw new Exception();
//                }
//            }
//            return true;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            Log.d("Exception ", " in Sup_SelfService_Promotion_Audit " + ex.toString());
//            return false;
//        }
//    }
//
//    public boolean insertDisplayMasterData(SupDisplayMasterGetterSetter supDisplayMasterObj) {
//        db.delete("Sup_Display_Master", null, null);
//        ContentValues values = new ContentValues();
//        List<SupDisplayMaster> data = supDisplayMasterObj.getSupDisplayMaster();
//        try {
//            if (data.size() == 0) {
//                return false;
//            }
//            for (int i = 0; i < data.size(); i++) {
//                values.put(CommonString.SUP_DISPLAY_ID, data.get(i).getDisplayId());
//                values.put(CommonString.SUP_DISPLAY_NAME, data.get(i).getDisplay());
//
//                long id = db.insert("Sup_Display_Master", null, values);
//                if (id == -1) {
//                    throw new Exception();
//                }
//            }
//            return true;
//        } catch (Exception ex) {
//            Crashlytics.logException(ex);
//            ex.printStackTrace();
//            Log.d("Database Sup_Display_Master", ex.toString());
//            return false;
//        }
//    }
//
//    public boolean insertPromoTypeMasterData(SupPromoTypeMasterGetterSetter supPromoTypeMasterObj) {
//        db.delete("Sup_Promo_Type_Master", null, null);
//        ContentValues values = new ContentValues();
//        List<SupPromoTypeMaster> data = supPromoTypeMasterObj.getSupPromoTypeMaster();
//        try {
//            if (data.size() == 0) {
//                return false;
//            }
//            for (int i = 0; i < data.size(); i++) {
//                values.put(CommonString.Promo_Type_Id, data.get(i).getPromoTypeId());
//                values.put(CommonString.Promo_Type, data.get(i).getPromoType());
//
//                long id = db.insert("Sup_Promo_Type_Master", null, values);
//                if (id == -1) {
//                    throw new Exception();
//                }
//            }
//            return true;
//        } catch (Exception ex) {
//            Crashlytics.logException(ex);
//            ex.printStackTrace();
//            Log.d("Database Sup_Promo_Type_Master", ex.toString());
//            return false;
//        }
//    }
//
//    // getting promotion data
//    public ArrayList<PromotionData> getMerchandiserPromotionData(String store_id) {
//        ArrayList<PromotionData> list = new ArrayList<PromotionData>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("select distinct PA.Mer_MID,PA.Posm_Present,PA.Total_Stock,PA.Running,PA.Description,PA.Store_Id," +
//                    "CM.Category,BM.Brand,PTM.Promo_Type,BM.Brand_Id,PTM.Promo_Type_Id,CM.Category_Id from Sup_SelfService_Promotion_Audit PA " +
//                    "INNER JOIN  Category_Master CM ON PA.Category_Id = CM.Category_Id " +
//                    "INNER JOIN  Brand_Master BM ON PA.Brand_Id = BM.Brand_Id " +
//                    "INNER JOIN Sup_Promo_Type_Master PTM ON PA.Promo_Type_Id = PTM.Promo_Type_Id " +
//                    "where PA.Store_Id = " + store_id + " ", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    PromotionData sb = new PromotionData();
//
//                    sb.setStore_Id(Integer.parseInt(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_STORE_ID))));
//                    sb.setBrand(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_BRAND)));
//                    sb.setCategory((dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_CATEGORY))));
//                    sb.setDesc(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Description)));
//                    sb.setMer_MID(Integer.parseInt(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Mer_MID))));
//                    sb.setMer_Total_Stock(Integer.valueOf(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Total_Stock))));
//                    sb.setPosm_Present(Integer.parseInt(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Posm_Present))));
//                    sb.setPromo_Type(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Promo_Type)));
//                    sb.setRunning(Integer.parseInt(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Running))));
//
//                    sb.setCategory_Id(Integer.valueOf(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Category_Id))));
//                    sb.setBrand_Id(Integer.parseInt(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Brand_Id))));
//
//                    sb.setPromo_Type_Id(Integer.parseInt(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Promo_Type_Id))));
//
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception get JCP!", e.toString());
//            return list;
//        }
//        return list;
//    }
//
//    public boolean insertMerchandiserKitMasterData(SupMerchandiserKitMasterGetterSetter supMerchandiserKitMasterObj) {
//        db.delete("Sup_Merchandiser_Kit_Master", null, null);
//        ContentValues values = new ContentValues();
//        List<SupMerchandiserKitMaster> data = supMerchandiserKitMasterObj.getSupMerchandiserKitMaster();
//        try {
//            if (data.size() == 0) {
//                return false;
//            }
//            for (int i = 0; i < data.size(); i++) {
//                values.put(CommonString.Kit_Id, data.get(i).getKitId());
//                values.put(CommonString.Kit_Name, data.get(i).getKitName());
//                values.put(CommonString.Upload_Status, data.get(i).getUploadStatus());
//
//                long id = db.insert("Sup_Merchandiser_Kit_Master", null, values);
//                if (id == -1) {
//                    throw new Exception();
//                }
//            }
//            return true;
//        } catch (Exception ex) {
//            Crashlytics.logException(ex);
//            ex.printStackTrace();
//            Log.d("Database Sup_Merchandiser_Kit_Master", ex.toString());
//            return false;
//        }
//    }
//
//    public long insertMerchandiserKitFinalData(ArrayList<SupMerchandiserKitMaster> toolKitList, String visit_date, String user_id, String merId) {
//        long id = 0;
//       // db.delete(CommonString.TABLE_SUP_MERCHANDISER_TOOL_AUDIT, null, null);
//        ContentValues values = new ContentValues();
//        try {
//
//            for (int i = 0; i < toolKitList.size(); i++) {
//                values.put("Mer_Id",merId);
//                values.put("User_Id", user_id);
//                values.put(CommonString.KEY_VISIT_DATE, visit_date);
//                values.put(CommonString.KEY_STATUS, toolKitList.get(i).getKitStatus());
//                values.put(CommonString.Kit_Name, toolKitList.get(i).getKitName());
//                values.put(CommonString.Kit_Id, toolKitList.get(i).getKitId());
//
//                id = db.insert(CommonString.TABLE_SUP_MERCHANDISER_TOOL_AUDIT, null, values);
//                if (id == -1) {
//                    throw new Exception();
//                }
//            }
//            return id;
//        } catch (Exception ex) {
//            Crashlytics.logException(ex);
//            ex.printStackTrace();
//            Log.d("Database Exception  ", ex.toString());
//            return id;
//        }
//    }
//
//    public ArrayList<SupMerchandiserKitMaster> getMerToolkitSavedData(String visit_date) {
//
//        ArrayList<SupMerchandiserKitMaster> list = new ArrayList<SupMerchandiserKitMaster>();
//        Cursor dbcursor = null;
//        try {
//
//            dbcursor = db.rawQuery("SELECT * from SUP_MERCHANDISER_TOOL_AUDIT where " + CommonString.KEY_VISIT_DATE + " = '" + visit_date + "' ", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    SupMerchandiserKitMaster sb = new SupMerchandiserKitMaster();
//
//                    sb.setKitId(Integer.valueOf(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Kit_Id))));
//                    sb.setKitName(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Kit_Name)));
//                    sb.setKitStatus(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_STATUS)));
//
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception fetching Mechandiser tool list !!!!!!!!!!!!!!!!!!!!!",
//                    e.toString());
//            return list;
//        }
//
//        Log.d("Fetching fetching Mechandiser tool list ---------------------->Stop<-----------",
//                "-------------------");
//        return list;
//    }
//
//    public ArrayList<SecondaryDisplayGetterSetter> getSecondryDisplayListData(String store_id) {
//        ArrayList<SecondaryDisplayGetterSetter> list = new ArrayList<SecondaryDisplayGetterSetter>();
//        Cursor dbcursor = null;
//        try {
//
//            dbcursor = db.rawQuery("select distinct SA.Store_Id,SA.Mer_MID,SA.Facing,SA.Total_Stock,CM.Category," +
//                    " BM.Brand,DM.Display,SA.Category_Id,SA.Brand_Id,SA.Display_Id,SA.Location_Id," +
//                    " WL.Window_Location from Sup_SelfService_SecondaryWindow_Audit SA " +
//                    " inner join Category_Master CM ON SA.Category_Id = CM.Category_Id " +
//                    " inner join Brand_Master BM ON SA.Brand_Id = BM.Brand_Id " +
//                    " inner join Sup_Display_Master DM ON SA.Display_Id = DM.Display_Id " +
//                    " inner join Sup_Window_Location WL ON SA.Location_Id = WL.Window_Location_Id " +
//                    " where Store_Id = '" + store_id + "' ", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    SecondaryDisplayGetterSetter sb = new SecondaryDisplayGetterSetter();
//
//                    sb.setStoreId(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_STORE_ID)));
//                    sb.setLocationCd(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Location_Id)));
//                    sb.setLocation(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_WINDOW_LOCATION)));
//                    sb.setBrandCd(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Brand_Id)));
//                    sb.setBrand(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_BRAND)));
//                    sb.setCategoryID(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Category_Id)));
//                    sb.setCategoryName(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_CATEGORY)));
//                    sb.setDisplayCd(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Display_Id)));
//                    sb.setDisplayType(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Display)));
//                    sb.setMer_MID(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Mer_MID)));
//                    sb.setTotalStock(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Total_Stock)));
//                    sb.setFacing(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Facing)));
//
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception fetching secondary window display list !!!!!!!!!!!!!!!!!!!!!",
//                    e.toString());
//            return list;
//        }
//
//        Log.d("Fetching fetching secondary window display list ---------------------->Stop<-----------",
//                "-------------------");
//        return list;
//
//    }
//
//    public long saveSecondaryDisplayData(ArrayList<SecondaryDisplayGetterSetter> data, String visit_date) {
//
//        db.delete(CommonString.TABLE_SECONDARY_DISPLAY, null, null);
//        ContentValues values = new ContentValues();
//        long id = 0, id2 = 0;
//
//        try {
//            for (int i = 0; i < data.size(); i++) {
//                values.put(CommonString.Store_Id, Integer.parseInt(data.get(i).getStoreId()));
//                values.put(CommonString.KEY_CATEGORY, data.get(i).getCategoryName());
//                values.put(CommonString.KEY_CATEGORY_ID, Integer.parseInt(data.get(i).getCategoryID()));
//                values.put(CommonString.KEY_BRAND_CD, Integer.parseInt(data.get(i).getBrandCd()));
//                values.put(CommonString.KEY_BRAND, (data.get(i).getBrand()));
//                values.put(CommonString.KEY_DISPLAY_CD, Integer.parseInt(data.get(i).getDisplayCd()));
//                values.put(CommonString.DISPLAY_TYPE, (data.get(i).getDisplayType()));
//                values.put(CommonString.KEY_LOCATION_CD, Integer.parseInt(data.get(i).getLocationCd()));
//                values.put(CommonString.KEY_LOCATION, (data.get(i).getLocation()));
//                values.put(CommonString.KEY_MER_FACING_QTY, Integer.parseInt(data.get(i).getFacing()));
//                values.put(CommonString.KEY_MER_TOTALSTOCK_QTY, Integer.parseInt(data.get(i).getTotalStock()));
//                values.put(CommonString.KEY_SUP_FACING_QTY, Integer.parseInt(data.get(i).getSupFacing()));
//                values.put(CommonString.KEY_SUP_TOTALSTOCK_QTY, Integer.parseInt(data.get(i).getSupStock()));
//                values.put(CommonString.Mer_MID, Integer.parseInt(data.get(i).getMer_MID()));
//                values.put(CommonString.KEY_VISIT_DATE, visit_date);
//                values.put(CommonString.KEY_IMAGE1, data.get(i).getImage1());
//                values.put(CommonString.KEY_IMAGE2, data.get(i).getImage2());
//                values.put(CommonString.KEY_IMAGE3, data.get(i).getImage3());
//
//                id = db.insert(CommonString.TABLE_SECONDARY_DISPLAY, null, values);
//                if (id == -1) {
//                    throw new Exception();
//                }
//
//            }
//            return id;
//        } catch (Exception ex) {
//            Crashlytics.logException(ex);
//            ex.printStackTrace();
//            Log.d("Database secondary display  ", ex.toString());
//            return 0;
//        }
//    }
//
//    public ArrayList<SecondaryDisplayGetterSetter> getSavedSecondaryDisplayList(String storeId, String visit_date) {
//        ArrayList<SecondaryDisplayGetterSetter> list = new ArrayList<SecondaryDisplayGetterSetter>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("SELECT  * from " + CommonString.TABLE_SECONDARY_DISPLAY + "  " +
//                    "where " + CommonString.KEY_STORE_ID + " = '" + storeId + "' and " + CommonString.KEY_VISIT_DATE + " = '" + visit_date + "' ", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    SecondaryDisplayGetterSetter sb = new SecondaryDisplayGetterSetter();
//
//                    sb.setStoreId(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_STORE_ID)));
//                    sb.setLocationCd(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_LOCATION_CD)));
//                    sb.setLocation(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_LOCATION)));
//                    sb.setBrandCd(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_BRAND_CD)));
//                    sb.setBrand(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_BRAND)));
//                    sb.setCategoryID(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_CATEGORY_ID)));
//
//                    sb.setCategoryName(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_CATEGORY)));
//
//                    sb.setDisplayCd(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_DISPLAY_CD)));
//                    sb.setDisplayType(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.DISPLAY_TYPE)));
//                    sb.setMer_MID(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Mer_MID)));
//                    sb.setTotalStock(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_MER_TOTALSTOCK_QTY)));
//                    sb.setFacing(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_MER_FACING_QTY)));
//
//                    sb.setSupStock(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_SUP_TOTALSTOCK_QTY)));
//                    sb.setSupFacing(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_SUP_FACING_QTY)));
//
//                    sb.setImage1(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_IMAGE1)));
//                    sb.setImage2(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_IMAGE2)));
//                    sb.setImage3(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_IMAGE3)));
//
//
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception get JCP!", e.toString());
//            return list;
//        }
//
//
//        return list;
//    }
//
//    public ArrayList<CategoryMaster> getCategoryListData() {
//
//        ArrayList<CategoryMaster> list = new ArrayList<>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("SELECT * from Category_Master", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    CategoryMaster sb = new CategoryMaster();
//
//                    sb.setCategoryId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Category_Id))));
//                    sb.setCategory(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CATEGORY)));
//
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception category",
//                    e.getMessage());
//            return list;
//        }
//
//        return list;
//    }
//
//    public boolean checkMerDisplayData(String storedLocationCD, String categoryCD, String storedDisplayCD, String storedBrandCD, String store_id) {
//        ArrayList<SecondaryDisplayGetterSetter> list = new ArrayList<SecondaryDisplayGetterSetter>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("SELECT  * from " + CommonString.TABLE_SECONDARY_DISPLAY + "  " +
//                    " where " + CommonString.KEY_STORE_ID + " = '" + store_id + "'  and " + CommonString.KEY_CATEGORY_ID + " = '" + categoryCD + "' " +
//                    " and " + CommonString.KEY_LOCATION_CD + " = '" + storedLocationCD + "' and " + CommonString.KEY_DISPLAY_CD + " = '" + storedDisplayCD + "' " +
//                    " and " + CommonString.KEY_BRAND_CD + " = '" + storedBrandCD + "' ", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    SecondaryDisplayGetterSetter sb = new SecondaryDisplayGetterSetter();
//
//                    sb.setStoreId(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_STORE_ID)));
//                    sb.setLocationCd(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_LOCATION_CD)));
//                    sb.setLocation(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_LOCATION)));
//                    sb.setBrandCd(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_BRAND_CD)));
//                    sb.setBrand(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_BRAND)));
//                    sb.setCategoryID(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_CATEGORY_ID)));
//
//                    sb.setCategoryName(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_CATEGORY)));
//
//                    sb.setDisplayCd(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_DISPLAY_CD)));
//                    sb.setDisplayType(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.DISPLAY_TYPE)));
//                    sb.setMer_MID(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Mer_MID)));
//                    sb.setTotalStock(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_MER_TOTALSTOCK_QTY)));
//                    sb.setFacing(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_MER_FACING_QTY)));
//
//                    sb.setSupStock(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_SUP_TOTALSTOCK_QTY)));
//                    sb.setSupFacing(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_SUP_FACING_QTY)));
//                    sb.setMer_MID(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Mer_MID)));
//
//                    sb.setImage1(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_IMAGE1)));
//                    sb.setImage2(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_IMAGE2)));
//                    sb.setImage3(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_IMAGE3)));
//
//
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//
//                dbcursor.close();
//                if (list.size() > 0) {
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception get data!", e.toString());
//            return false;
//        }
//        return false;
//    }
//
//    public boolean insertSupSelfServicePromotionCompetitionData(SupSelfservicePromotionCompetitionAuditGetterSetter data) {
//        List<SupSelfservicePromotionCompetitionAudit> list = data.getSupSelfservicePromotionCompetitionAudit();
//        ContentValues values = new ContentValues();
//        try {
//            if (list.size() == 0) {
//                return false;
//            }
//            for (int i = 0; i < list.size(); i++) {
//                values.put(CommonString.Category_Id, list.get(i).getCategoryId());
//                values.put(CommonString.Description, list.get(i).getDescription());
//                values.put(CommonString.Brand_Id, list.get(i).getBrandId());
//                values.put(CommonString.Store_Id, list.get(i).getStoreId());
//                values.put(CommonString.Promo_Type_Id, list.get(i).getPromoTypeId());
//                values.put(CommonString.Mer_MID, list.get(i).getMerMID());
//                values.put(CommonString.KEY_EMP_ID, list.get(i).getEmpId());
//                values.put("Visit_Date", list.get(i).getVisitDate());
//
//                long id = db.insert("Sup_Selfservice_Promotion_Competition_Audit", null, values);
//                if (id == -1) {
//                    throw new Exception();
//                }
//            }
//            return true;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            Log.d("Exception ", " in Sup_Selfservice_Promotion_Competition_Audit " + ex.toString());
//            return false;
//        }
//    }
//
//    public boolean insertSupSelfServiceTouchPointAuditData(SupSelfserviceTouchpointAuditGetterSetter data) {
//        List<SupSelfserviceTouchpointAudit> list = data.getSupSelfserviceTouchpointAudit();
//        ContentValues values = new ContentValues();
//        try {
//            if (list.size() == 0) {
//                return false;
//            }
//            for (int i = 0; i < list.size(); i++) {
//
//                values.put(CommonString.Brand_Id, list.get(i).getBrandId());
//                values.put(CommonString.Store_Id, list.get(i).getStoreId());
//                values.put(CommonString.KEY_MER_MID, list.get(i).getMerchandiserMID());
//                values.put(CommonString.Posm_Qty, list.get(i).getPosmQty());
//                values.put(CommonString.Posm_Id, list.get(i).getPosmId());
//                values.put(CommonString.Emp_Id, list.get(i).getEmpId());
//                values.put(CommonString.Type, list.get(i).getType());
//                values.put("Visit_Date", list.get(i).getVisitDate());
//
//                long id = db.insert("Sup_Selfservice_Touchpoint_Audit", null, values);
//                if (id == -1) {
//                    throw new Exception();
//                }
//            }
//            return true;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            Log.d("Exception ", " in Sup_Selfservice_Touchpoint_Audit " + ex.toString());
//            return false;
//        }
//    }
//
//    public long savePromotionData(ArrayList<PromotionData> data, String visit_date) {
//        db.delete(CommonString.TABLE_PROMOTION, null, null);
//        ContentValues values = new ContentValues();
//        long id = 0;
//
//        try {
//            for (int i = 0; i < data.size(); i++) {
//
//                values.put(CommonString.Store_Id, data.get(i).getStore_Id());
//                values.put(CommonString.KEY_CATEGORY, data.get(i).getCategory());
//                values.put(CommonString.Category_Id, data.get(i).getCategory_Id());
//                values.put(CommonString.Brand_Id, data.get(i).getBrand_Id());
//                values.put(CommonString.KEY_BRAND, data.get(i).getBrand());
//                values.put(CommonString.Mer_MID, data.get(i).getMer_MID());
//                values.put(CommonString.Description, data.get(i).getDesc());
//                values.put(CommonString.Posm_Present, data.get(i).getPosm_Present());
//                values.put(CommonString.Running, data.get(i).getRunning());
//                values.put(CommonString.Promo_Type_Id, data.get(i).getPromo_Type_Id());
//                values.put(CommonString.Promo_Type, data.get(i).getPromo_Type());
//
//                values.put(CommonString.Sup_Running, data.get(i).getSup_running());
//                values.put(CommonString.Sup_posm_present, data.get(i).getSup_posm_present());
//                values.put(CommonString.Sup_Description, data.get(i).getSup_Desc());
//
//                values.put(CommonString.KEY_MER_STOCK_QUY, data.get(i).getMer_Total_Stock());
//                values.put(CommonString.KEY_VISIT_DATE, visit_date);
//                values.put(CommonString.KEY_IMAGE1, data.get(i).getDesc_Img());
//
//                id = db.insert(CommonString.TABLE_PROMOTION, null, values);
//                if (id == -1) {
//                    throw new Exception();
//                }
//            }
//            return id;
//        } catch (Exception ex) {
//            Crashlytics.logException(ex);
//            ex.printStackTrace();
//            // Log.d("Database Promotion ", ex.toString());
//            return 0;
//        }
//    }
//
//    public ArrayList<PromotionData> getSavePromotionList(String storeId, String visit_date) {
//        ArrayList<PromotionData> list = new ArrayList<PromotionData>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("SELECT  * from " + CommonString.TABLE_PROMOTION + "  " +
//                    "where " + CommonString.KEY_STORE_ID + " = '" + storeId + "' and " + CommonString.KEY_VISIT_DATE + " = '" + visit_date + "' ", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    PromotionData sb = new PromotionData();
//
//                    sb.setStore_Id(Integer.parseInt(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Store_Id))));
//                    sb.setBrand(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_BRAND)));
//                    sb.setCategory((dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_CATEGORY))));
//                    sb.setDesc(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Description)));
//                    sb.setMer_MID(Integer.parseInt(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Mer_MID))));
//                    sb.setMer_Total_Stock(Integer.valueOf(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_MER_STOCK_QUY))));
//
//                    sb.setPosm_Present(Integer.parseInt(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Posm_Present))));
//                    sb.setPromo_Type(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Promo_Type)));
//                    sb.setRunning(Integer.parseInt(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Running))));
//
//                    sb.setDesc_Img(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_IMAGE1)));
//
//                    sb.setCategory_Id(Integer.valueOf(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Category_Id))));
//                    sb.setBrand_Id(Integer.parseInt(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Brand_Id))));
//
//                    sb.setPromo_Type_Id(Integer.parseInt(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Promo_Type_Id))));
//
//                    sb.setSup_posm_present(Integer.parseInt(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Sup_posm_present))));
//                    sb.setSup_running(Integer.parseInt(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Sup_Running))));
//
//                    sb.setSup_Desc(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Sup_Description)));
//
//                    list.add(sb);
//
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception get JCP!", e.toString());
//            return list;
//        }
//
//
//        return list;
//    }
//
//    public ArrayList<CompetitionData> getMerchandiserCompetitionData(String store_id) {
//        ArrayList<CompetitionData> list = new ArrayList<CompetitionData>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("select distinct PA.Mer_MID,PA.Description,PA.Store_Id," +
//                    "CM.Category,BM.Brand,PTM.Promo_Type,BM.Brand_Id,PTM.Promo_Type_Id,CM.Category_Id " +
//                    "from Sup_Selfservice_Promotion_Competition_Audit PA " +
//                    "INNER JOIN  Category_Master CM ON PA.Category_Id = CM.Category_Id " +
//                    "INNER JOIN  Brand_Master BM ON PA.Brand_Id = BM.Brand_Id " +
//                    "INNER JOIN Sup_Promo_Type_Master PTM ON PA.Promo_Type_Id = PTM.Promo_Type_Id " +
//                    "where PA.Store_Id = " + store_id + " ", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    CompetitionData sb = new CompetitionData();
//
//                    sb.setStore_Id(Integer.parseInt(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_STORE_ID))));
//                    sb.setBrand(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_BRAND)));
//                    sb.setCategory((dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_CATEGORY))));
//                    sb.setDesc(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Description)));
//                    sb.setMer_MID(Integer.parseInt(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Mer_MID))));
//
//                    sb.setPromo_Type(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Promo_Type)));
//
//                    sb.setCategory_Id(Integer.valueOf(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Category_Id))));
//                    sb.setBrand_Id(Integer.parseInt(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Brand_Id))));
//
//                    sb.setPromo_Type_Id(Integer.parseInt(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Promo_Type_Id))));
//
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception get JCP!", e.toString());
//            return list;
//        }
//        return list;
//    }
//
//    public ArrayList<CompetitionData> getSaveCompetitionList(String store_id, String visit_date) {
//        ArrayList<CompetitionData> list = new ArrayList<CompetitionData>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("SELECT  * from " + CommonString.TABLE_COMPETITION + "  " +
//                    "where " + CommonString.KEY_STORE_ID + " = '" + store_id + "' and " + CommonString.KEY_VISIT_DATE + " = '" + visit_date + "' ", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    CompetitionData sb = new CompetitionData();
//
//                    sb.setStore_Id(Integer.parseInt(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_STORE_ID))));
//                    sb.setBrand(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_BRAND)));
//                    sb.setCategory((dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_CATEGORY))));
//                    sb.setDesc(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Description)));
//                    sb.setMer_MID(Integer.parseInt(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Mer_MID))));
//
//                    sb.setPromo_Type(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Promo_Type)));
//
//                    sb.setDesc_Img(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_IMAGE1)));
//
//                    sb.setCategory_Id(Integer.valueOf(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_CATEGORY_ID))));
//                    sb.setBrand_Id(Integer.parseInt(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_BRAND_ID))));
//
//                    sb.setSup_Desc(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Sup_Description)));
//
//                    sb.setPromo_Type_Id(Integer.parseInt(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Promo_Type_Id))));
//
//                    list.add(sb);
//
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception get JCP!", e.toString());
//            return list;
//        }
//
//
//        return list;
//    }
//
//    public long saveCompetitionData(ArrayList<CompetitionData> data, String visit_date) {
//        db.delete(CommonString.TABLE_COMPETITION, null, null);
//        ContentValues values = new ContentValues();
//        long id = 0;
//
//        try {
//            for (int i = 0; i < data.size(); i++) {
//
//                values.put(CommonString.KEY_STORE_ID, data.get(i).getStore_Id());
//                values.put(CommonString.KEY_CATEGORY, data.get(i).getCategory());
//                values.put(CommonString.KEY_CATEGORY_ID, data.get(i).getCategory_Id());
//                values.put(CommonString.KEY_BRAND_ID, data.get(i).getBrand_Id());
//                values.put(CommonString.KEY_BRAND, data.get(i).getBrand());
//                values.put(CommonString.Mer_MID, data.get(i).getMer_MID());
//                values.put(CommonString.Description, data.get(i).getDesc());
//                values.put(CommonString.Promo_Type_Id, data.get(i).getPromo_Type_Id());
//                values.put(CommonString.Promo_Type, data.get(i).getPromo_Type());
//                values.put(CommonString.KEY_VISIT_DATE, visit_date);
//                values.put(CommonString.KEY_IMAGE1, data.get(i).getDesc_Img());
//
//                values.put(CommonString.Sup_Description, data.get(i).getSup_Desc());
//
//                id = db.insert(CommonString.TABLE_COMPETITION, null, values);
//                if (id == -1) {
//                    throw new Exception();
//                }
//            }
//            return id;
//        } catch (Exception ex) {
//            Crashlytics.logException(ex);
//            ex.printStackTrace();
//            // Log.d("Database Promotion ", ex.toString());
//            return 0;
//        }
//    }
//
//    public boolean insertSupSelfServicePrimaryAuditData(SupSelfServicePrimaryAuditGetterSetter data) {
//        List<SupSelfServicePrimaryAudit> list = data.getSupSelfServicePrimaryAudit();
//        ContentValues values = new ContentValues();
//        try {
//            if (list.size() == 0) {
//                return false;
//            }
//            for (int i = 0; i < list.size(); i++) {
//
//                values.put(CommonString.Mer_MID, list.get(i).getMerMID());
//                values.put(CommonString.Store_Id, list.get(i).getStoreId());
//                values.put(CommonString.TranId, list.get(i).getTranId());
//                values.put(CommonString.Category_Id, list.get(i).getCategoryId());
//                values.put(CommonString.Category_Total, list.get(i).getCategoryTotal());
//                values.put(CommonString.Brand_Group_Id, list.get(i).getBrandGroupId());
//                values.put(CommonString.Shelf_Count, list.get(i).getShelfCount());
//                values.put(CommonString.Shelf_Strip, list.get(i).getShelfStrip());
//                values.put(CommonString.Category_Header, list.get(i).getCategoryHeader());
//                values.put(CommonString.KEY_EMP_ID, list.get(i).getEmpId());
//                values.put("Visit_Date", list.get(i).getVisitDate());
//
//                long id = db.insert("Sup_SelfService_Primary_Audit", null, values);
//                if (id == -1) {
//                    throw new Exception();
//                }
//            }
//            return true;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            Log.d("Exception ", " in Sup_SelfService_Primary_Audit " + ex.toString());
//            return false;
//        }
//    }
//
//    public ArrayList<PrimaryBayGetterSetter> getPrimaryBayData(String store_id, String categoryCD) {
//        Log.d("Fetching primaryBay data--------------->Start<------------",
//                "------------------");
//        ArrayList<PrimaryBayGetterSetter> list = new ArrayList<PrimaryBayGetterSetter>();
//        Cursor dbcursor = null;
//        try {
//
//            dbcursor = db.rawQuery("select PA.Mer_MID,PA.Store_Id,PA.TranId,PA.Category_Id,CM.Category,PA.Brand_Group_Id, BGM.Brand_Group,PA.Category_Total," +
//                    "PA.Shelf_Count,PA.Shelf_Strip,PA.Category_Header from Sup_SelfService_Primary_Audit  PA " +
//                    "inner join Brand_Group_Master BGM ON  PA.Brand_Group_Id = BGM.Brand_Group_Id " +
//                    "inner join Category_Master CM ON PA.Category_Id = CM.Category_Id where PA.Store_Id = '" + store_id + "' and PA.Category_Id = '" + categoryCD + "'", null);
//
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//
//                    PrimaryBayGetterSetter data = new PrimaryBayGetterSetter();
//                    data.setBrandGroupId(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_BRAND_GROUP_ID)));
//                    data.setBrandGroup(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_BRAND_GROUP)));
//                    data.setCategoryId(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Category_Id)));
//                    data.setCategory(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CATEGORY)));
//
//                    data.setCategory_total(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Category_Total)));
//                    data.setCategory_header(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Category_Header)));
//                    data.setShelf_count(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Shelf_Count)));
//                    data.setShelf_strip(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Shelf_Strip)));
//
//                    data.setStoreId(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Store_Id)));
//                    data.setMer_MID(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Mer_MID)));
//                    data.setTransId(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.TranId)));
//
//                    list.add(data);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
//                    e.toString());
//            return list;
//        }
//
//        Log.d("Fetching Primary Bay Data---------------------->Stop<-----------",
//                "-------------------");
//        return list;
//    }
//
//    public boolean insertPrimaryBayMasterData(SupPrimaryBayMasterGetterSetter supPrimaryBayMasterObj) {
//        db.delete("Sup_Primary_Bay_Master", null, null);
//        ContentValues values = new ContentValues();
//
//        List<SupPrimaryBayMaster> data = supPrimaryBayMasterObj.getSupPrimaryBayMaster();
//        try {
//            if (data.size() == 0) {
//                return false;
//            }
//
//            for (int i = 0; i < data.size(); i++) {
//
//                values.put(CommonString.Bay, data.get(i).getBay());
//                values.put(CommonString.Bay_Id, data.get(i).getBayId());
//                values.put(CommonString.Category_Id, data.get(i).getCategoryId());
//                values.put(CommonString.Image_Mandatory, data.get(i).getImageMandatory());
//
//                long id = db.insert("Sup_Primary_Bay_Master", null, values);
//                if (id == -1) {
//                    throw new Exception();
//                }
//
//            }
//            return true;
//        } catch (Exception ex) {
//            Crashlytics.logException(ex);
//            ex.printStackTrace();
//            Log.d("Database Sup_Primary_Bay_Master  ", ex.toString());
//            return false;
//        }
//    }
//
//    public long insertPrimaryBayData(ArrayList<PrimaryBayGetterSetter> data, String visit_date, ArrayList<PrimaryBayMaster> primaryBayMasters, ArrayList<PrimaryBayMaster> categoryPictureList, String cat_count) {
//
//        db.delete(CommonString.TABLE_PRIMARY_BAY, CommonString.Store_Id + " = " + data.get(0).getStoreId() + " AND " + CommonString.Category_Id + " = " + data.get(0).getCategoryId(), null);
//        db.delete(CommonString.TABLE_PRIMARY_BAY_IMAGE, CommonString.KEY_STORE_ID + " = " + data.get(0).getStoreId() + " AND " + CommonString.KEY_CATEGORY_ID + " = " + data.get(0).getCategoryId(), null);
//        db.delete(CommonString.TABLE_PRIMARY_BAY_CATEGORY_IMAGE, CommonString.KEY_STORE_ID + " = " + data.get(0).getStoreId() + " AND " + CommonString.KEY_CATEGORY_ID + " = " + data.get(0).getCategoryId(), null);
//        ContentValues values = new ContentValues();
//        ContentValues values2 = new ContentValues();
//        ContentValues values3 = new ContentValues();
//        long commomID = 0, id2 = 0, id3 = 0;
//        try {
//
//            for (int j = 0; j < primaryBayMasters.size(); j++) {
//                values2.put(CommonString.KEY_VISIT_DATE, visit_date);
//                values2.put(CommonString.KEY_STORE_ID, Integer.parseInt(data.get(0).getStoreId()));
//                values2.put(CommonString.KEY_CATEGORY_ID, Integer.parseInt(data.get(0).getCategoryId()));
//                values2.put(CommonString.KEY_BAY_ID, primaryBayMasters.get(j).getBayId());
//                values2.put(CommonString.KEY_IMAGE, primaryBayMasters.get(j).getImage());
//
//                id2 = db.insert(CommonString.TABLE_PRIMARY_BAY_IMAGE, null, values2);
//            }
//
//            for (int j = 0; j < categoryPictureList.size(); j++) {
//                values3.put(CommonString.KEY_VISIT_DATE, visit_date);
//                values3.put(CommonString.KEY_STORE_ID, Integer.parseInt(data.get(0).getStoreId()));
//                values3.put(CommonString.KEY_CATEGORY_ID, Integer.parseInt(data.get(0).getCategoryId()));
//                values3.put(CommonString.KEY_CATEGORY, categoryPictureList.get(j).getCategoryName());
//                values3.put(CommonString.KEY_IMAGE_ID, categoryPictureList.get(j).getImageId());
//                values3.put(CommonString.KEY_IMAGE, categoryPictureList.get(j).getImage());
//
//                id3 = db.insert(CommonString.TABLE_PRIMARY_BAY_CATEGORY_IMAGE, null, values3);
//            }
//
//            for (int i = 0; i < data.size(); i++) {
//                values.put(CommonString.KEY_VISIT_DATE, visit_date);
//                values.put(CommonString.Store_Id, Integer.parseInt(data.get(i).getStoreId()));
//                values.put(CommonString.Category_Id, Integer.parseInt(data.get(i).getCategoryId()));
//                values.put(CommonString.Brand_Group_Id, Integer.parseInt(data.get(i).getBrandGroupId()));
//                values.put(CommonString.Brand_Group, data.get(i).getBrandGroup());
//                values.put(CommonString.Mer_MID, Integer.parseInt(data.get(i).getMer_MID()));
//                values.put(CommonString.TranId, Integer.parseInt(data.get(i).getTransId()));
//                values.put(CommonString.Mer_Shelf_Count, data.get(i).getShelf_count());
//                values.put(CommonString.Mer_Shelf_Strip, data.get(i).getShelf_strip());
//                values.put(CommonString.Mer_Category_Header, data.get(i).getCategory_header());
//                values.put(CommonString.Sup_Shelf_Count, data.get(i).getSup_shelf_count());
//                values.put(CommonString.Sup_Shelf_Strip, data.get(i).getSup_shelf_strip());
//                values.put(CommonString.Sup_Category_Header, data.get(i).getSup_category_header());
//                values.put(CommonString.Mer_Category_Total, data.get(i).getCategory_total());
//                values.put(CommonString.Sup_Category_Total, cat_count);
//
//                commomID = db.insert(CommonString.TABLE_PRIMARY_BAY, null, values);
//            }
//
//            if (commomID > 0) {
//                return commomID;
//            } else {
//                return 0;
//            }
//        } catch (Exception ex) {
//            Log.d("Excep primary Bay", ex.toString());
//            return 0;
//        }
//    }
//
//    public ArrayList<PrimaryBayGetterSetter> getSavedPrimaryBayData(String store_id, String categoryCD) {
//        Log.d("Fetching primaryBay data--------------->Start<------------",
//                "------------------");
//        ArrayList<PrimaryBayGetterSetter> list = new ArrayList<PrimaryBayGetterSetter>();
//        Cursor dbcursor = null;
//        try {
//            dbcursor = db.rawQuery("select * from " + CommonString.TABLE_PRIMARY_BAY + " where " + CommonString.Store_Id + " = '" + store_id + "' and " + CommonString.Category_Id + " = '" + categoryCD + "' ", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//
//                    PrimaryBayGetterSetter data = new PrimaryBayGetterSetter();
//                    data.setBrandGroupId(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Brand_Group_Id)));
//                    data.setBrandGroup(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Brand_Group)));
//                    data.setCategoryId(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Category_Id)));
//                    data.setCategory_total(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Mer_Category_Total)));
//                    data.setCategory_header(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Mer_Category_Header)));
//                    data.setShelf_count(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Mer_Shelf_Count)));
//                    data.setShelf_strip(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Mer_Shelf_Strip)));
//                    data.setStoreId(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Store_Id)));
//                    data.setMer_MID(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Mer_MID)));
//                    data.setTransId(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.TranId)));
//                    data.setSup_shelf_count(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Sup_Shelf_Count)));
//                    data.setSup_shelf_strip(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Sup_Shelf_Strip)));
//                    data.setSup_category_header(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Sup_Category_Header)));
//                    data.setSup_category_total(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Sup_Category_Total)));
//
//                    list.add(data);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
//                    e.toString());
//            return list;
//        }
//
//        Log.d("Fetching Primary Bay Data---------------------->Stop<-----------",
//                "-------------------");
//        return list;
//    }
//
//    public ArrayList<PrimaryBayGetterSetter> getSavedPrimaryBayDataForupload(String storeId) {
//        ArrayList<PrimaryBayGetterSetter> list = new ArrayList<PrimaryBayGetterSetter>();
//        Cursor dbcursor = null;
//        try {
//
//            dbcursor = db.rawQuery("select * from " + CommonString.TABLE_PRIMARY_BAY + " where " + CommonString.Store_Id + " = '" + storeId + "' ", null);
//
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//
//                    PrimaryBayGetterSetter data = new PrimaryBayGetterSetter();
//                    data.setBrandGroupId(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Brand_Group_Id)));
//                    data.setCategoryId(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Category_Id)));
//                    data.setCategory_total(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Mer_Category_Total)));
//                    data.setCategory_header(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Mer_Category_Header)));
//                    data.setShelf_count(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Mer_Shelf_Count)));
//                    data.setShelf_strip(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Mer_Shelf_Strip)));
//                    data.setStoreId(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Store_Id)));
//                    data.setMer_MID(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Mer_MID)));
//                    data.setTransId(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.TranId)));
//                    data.setSup_shelf_count(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Sup_Shelf_Count)));
//                    data.setSup_shelf_strip(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Sup_Shelf_Strip)));
//                    data.setSup_category_header(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Sup_Category_Header)));
//                    data.setSup_category_total(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Sup_Category_Total)));
//
//                    list.add(data);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
//                    e.toString());
//            return list;
//        }
//
//        Log.d("Fetching Primary Bay Data---------------------->Stop<-----------",
//                "-------------------");
//        return list;
//    }
//
//
//    public ArrayList<PrimaryBayMaster> getPrimaryBayImagesByStoreIDData(String store_id) {
//
//        ArrayList<PrimaryBayMaster> list = new ArrayList<>();
//        Cursor dbcursor = null;
//        try {
//
//            dbcursor = db.rawQuery("select * From " + CommonString.TABLE_PRIMARY_BAY_IMAGE + " where " + CommonString.KEY_STORE_ID + " = " + store_id + "", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//
//                    PrimaryBayMaster primaryBayGetset = new PrimaryBayMaster();
//
//                    primaryBayGetset.setBayId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_BAY_ID))));
//                    primaryBayGetset.setCategoryId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CATEGORY_ID))));
//                    // primaryBayGetset.setImageMandatory("1".equals(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Image_Mandatory"))));
//                    primaryBayGetset.setImage(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE)));
//
//                    list.add(primaryBayGetset);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
//                    e.toString());
//            return list;
//        }
//        return list;
//    }
//
//
//    public ArrayList<PrimaryBayMaster> getPrimaryBayCategoryImagesByStoreIDData(String store_id) {
//        ArrayList<PrimaryBayMaster> list = new ArrayList<>();
//        Cursor dbcursor = null;
//        try {
//            dbcursor = db.rawQuery("select * From " + CommonString.TABLE_PRIMARY_BAY_CATEGORY_IMAGE + " where " + CommonString.KEY_STORE_ID + " = " + store_id + "", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    PrimaryBayMaster primaryBayGetset = new PrimaryBayMaster();
//
//                    primaryBayGetset.setCategoryId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CATEGORY_ID))));
//                    primaryBayGetset.setCategoryName(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CATEGORY)));
//                    primaryBayGetset.setImage(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE)));
//
//                    list.add(primaryBayGetset);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
//                    e.toString());
//            return list;
//        }
//        return list;
//    }
//
//    public boolean insertDbPosmAllocationData(SupMappingPosmAllocationGetterSetter supMappingPosmAllocationObj) {
//        db.delete("Sup_Mapping_Posm_Allocation", null, null);
//        ContentValues values = new ContentValues();
//
//        List<SupMappingAllocation> data = supMappingPosmAllocationObj.getSupMappingPosmAllocation();
//        try {
//            if (data.size() == 0) {
//                return false;
//            }
//
//            for (int i = 0; i < data.size(); i++) {
//
//                values.put(CommonString.Brand_Id, data.get(i).getBrandId());
//                values.put(CommonString.Allocation, data.get(i).getAllocation());
//                values.put(CommonString.Allocation_Month, data.get(i).getAllocationMonth());
//                values.put(CommonString.LastDateOf_Month, data.get(i).getLastDateOfMonth());
//                values.put(CommonString.City_Id, data.get(i).getCityId());
//                values.put(CommonString.City, data.get(i).getCity());
//                values.put(CommonString.Distributor_Id, data.get(i).getDistributorId());
//                values.put(CommonString.Distributor, data.get(i).getDistributor());
//                values.put(CommonString.Posm_Id, data.get(i).getPosmId());
//                values.put(CommonString.Id, data.get(i).getId());
//                values.put(CommonString.MTD, data.get(i).getMTD());
//                values.put(CommonString.Upload_Status, data.get(i).getUploadStatus());
//
//                long id = db.insert("Sup_Mapping_Posm_Allocation", null, values);
//                if (id == -1) {
//                    throw new Exception();
//                }
//
//            }
//            return true;
//        } catch (Exception ex) {
//            Crashlytics.logException(ex);
//            ex.printStackTrace();
//            Log.d("Database Sup_Mapping_Posm_Allocation  ", ex.toString());
//            return false;
//        }
//    }
//
//    public ArrayList<SupMappingAllocation> getDbAllocationData(String DistributorId, String cityId, String month, String db_option) {
//        Log.d("posm allocation data", "------------------");
//        ArrayList<SupMappingAllocation> list = new ArrayList<>();
//        Cursor dbcursor = null;
//
//        try {
//            if (db_option.equalsIgnoreCase("0")) {
//                dbcursor = db.rawQuery(" select distinct MPA.Brand_Id,BM.Brand" +
//                        " from Sup_Mapping_Posm_Allocation MPA " +
//                        " INNER JOIN Brand_Master BM ON MPA.Brand_Id = BM.Brand_Id " +
//                        " where " + CommonString.Distributor_Id + " = '" + DistributorId + "'" +
//                        " and " + CommonString.City_Id + " = '" + cityId + "' and " + CommonString.LastDateOf_Month + " = '" + month + "'", null);
//            } else {
//                dbcursor = db.rawQuery(" select distinct MWA.Window_Id,WM.Window" +
//                        " from Sup_Mapping_Window_Allocation MWA " +
//                        " INNER JOIN Window_Master WM ON MWA.Window_Id = WM.Window_Id " +
//                        " where " + CommonString.Distributor_Id + " = '" + DistributorId + "'" +
//                        " and " + CommonString.City_Id + " = '" + cityId + "' and " + CommonString.LastDateOf_Month + " = '" + month + "'", null);
//            }
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    SupMappingAllocation spa = new SupMappingAllocation();
//
//                    if (db_option.equalsIgnoreCase("0")) {
//                        spa.setBrandId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Brand_Id))));
//                        spa.setBrand(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_BRAND)));
//                    } else {
//                        spa.setWindowId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Window_Id))));
//                        spa.setWindow(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Window)));
//                    }
//
//                    list.add(spa);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception ", e.getMessage());
//            return list;
//        }
//        return list;
//    }
//
//    public ArrayList<SupMappingAllocation> getDbPosmAllocationCityData(String db_option) {
//        Log.d("posm allocation data", "------------------");
//        ArrayList<SupMappingAllocation> list = new ArrayList<>();
//        SupMappingAllocation spa = new SupMappingAllocation();
//        Cursor dbcursor = null;
//
//        try {
//            // adding default value in city list
//            spa.setCity("Select City");
//            spa.setCityId(0);
//            list.add(spa);
//            // Here if db_option is "0" then dbposm data shows
//            // if db_option is "1" then window  kit data shows
//            if (db_option.equalsIgnoreCase("0")) {
//                dbcursor = db.rawQuery("SELECT  distinct City_Id,City from Sup_Mapping_Posm_Allocation order by City ", null);
//            } else {
//                dbcursor = db.rawQuery("SELECT  distinct City_Id,City from Sup_Mapping_Window_Allocation order by City ", null);
//            }
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    SupMappingAllocation spa1 = new SupMappingAllocation();
//                    spa1.setCityId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.City_Id))));
//                    spa1.setCity(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.City)));
//                    list.add(spa1);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception ", e.getMessage());
//            return list;
//        }
//        return list;
//    }
//
//
//    public ArrayList<SupMappingAllocation> getDbPosmAllocationDistributorData(String selectedCityId, String db_option) {
//        Log.d("posm allocation data", "------------------");
//        ArrayList<SupMappingAllocation> list = new ArrayList<>();
//        Cursor dbcursor = null;
//
//        try {
//            if (db_option.equalsIgnoreCase("0")) {
//                dbcursor = db.rawQuery("SELECT distinct Distributor,Distributor_Id,Upload_Status from Sup_Mapping_Posm_Allocation where " + CommonString.City_Id + " = '" + selectedCityId + "' ", null);
//            } else {
//                dbcursor = db.rawQuery("SELECT distinct Distributor,Distributor_Id,Upload_Status from Sup_Mapping_Window_Allocation where " + CommonString.City_Id + " = '" + selectedCityId + "' ", null);
//            }
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    SupMappingAllocation spa = new SupMappingAllocation();
//
//                    spa.setDistributorId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Distributor_Id))));
//                    spa.setDistributor(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Distributor)));
//                    spa.setUploadStatus(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Upload_Status))));
//
//                    list.add(spa);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception ", e.getMessage());
//            return list;
//        }
//        return list;
//    }
//
//    public ArrayList<SupMappingAllocation> getDbPosmAllocationMonthData(String distributorId, String cityId, String db_option) {
//        Log.d("posm allocation data", "------------------");
//        ArrayList<SupMappingAllocation> list = new ArrayList<>();
//        Cursor dbcursor = null;
//
//        try {
//            // Here if db_option is "0" then dbposm data shows
//            // if db_option is "1" then window  kit data shows
//            if (db_option.equalsIgnoreCase("0")) {
//                dbcursor = db.rawQuery("SELECT distinct  Allocation_Month," + CommonString.LastDateOf_Month + ",Distributor_Id,Distributor from Sup_Mapping_Posm_Allocation where " + CommonString.Distributor_Id + " = '" + distributorId + "' and " + CommonString.City_Id + " = '" + cityId + "'", null);
//            } else {
//                dbcursor = db.rawQuery("SELECT distinct  Allocation_Month," + CommonString.LastDateOf_Month + ",Distributor_Id,Distributor from Sup_Mapping_Window_Allocation where " + CommonString.Distributor_Id + " = '" + distributorId + "' and " + CommonString.City_Id + " = '" + cityId + "'", null);
//            }
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    SupMappingAllocation spa = new SupMappingAllocation();
//
//                    spa.setAllocationMonth(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Allocation_Month)));
//                    spa.setLastDateOfMonth(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.LastDateOf_Month)));
//                    spa.setDistributorId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Distributor_Id))));
//                    spa.setDistributor(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Distributor)));
//
//                    list.add(spa);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception ", e.getMessage());
//            return list;
//        }
//        return list;
//    }
//
//    public ArrayList<SupMappingAllocation> getDbPosmAllocationPosmData(String distributorId, String cityId, String month, Integer Id, String db_option) {
//        Log.d("posm allocation data", "------------------");
//        ArrayList<SupMappingAllocation> list = new ArrayList<>();
//        Cursor dbcursor = null;
//
//        try {
//            if (db_option.equalsIgnoreCase("0")) {
//                dbcursor = db.rawQuery(" select distinct MPA.Id,MPA.Distributor_Id,MPA.Distributor,MPA.Posm_Id,PM.Posm, " +
//                        " MPA.City_Id,MPA.City,MPA.Allocation,MPA.Allocation_Month,MPA.MTD,MPA.LastDateOf_Month,MPA.Upload_Status " +
//                        " from Sup_Mapping_Posm_Allocation MPA " +
//                        " INNER JOIN Sup_Posm_Master PM ON MPA.Posm_Id = PM.Posm_Id " +
//                        " where " + CommonString.Distributor_Id + " = '" + distributorId + "'" +
//                        " and " + CommonString.City_Id + " = '" + cityId + "' and " + CommonString.LastDateOf_Month + " = '" + month + "' and Brand_Id = '" + Id + "' ", null);
//            } else {
//                dbcursor = db.rawQuery(" select distinct MWA.Id,MWA.Distributor_Id,MWA.Distributor,MWA.Posm_Id,PM.Posm, " +
//                        " MWA.City_Id,MWA.City,MWA.Allocation,MWA.Allocation_Month,MWA.MTD,MWA.LastDateOf_Month,MWA.Upload_Status " +
//                        " from Sup_Mapping_Window_Allocation MWA " +
//                        " INNER JOIN Sup_Posm_Master PM ON MWA.Posm_Id = PM.Posm_Id " +
//                        " where " + CommonString.Distributor_Id + " = '" + distributorId + "'" +
//                        " and " + CommonString.City_Id + " = '" + cityId + "' and " + CommonString.LastDateOf_Month + " = '" + month + "' and Window_Id = '" + Id + "' ", null);
//            }
//
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    SupMappingAllocation spa = new SupMappingAllocation();
//
//                    spa.setId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Id))));
//                    spa.setAllocationMonth(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Allocation_Month)));
//                    spa.setLastDateOfMonth(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.LastDateOf_Month)));
//                    spa.setAllocation(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Allocation))));
//                    spa.setMTD(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.MTD))));
//                    spa.setCityId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.City_Id))));
//                    spa.setCity(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.City)));
//                    spa.setPosmId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Posm_Id))));
//                    spa.setPosm(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Posm)));
//                    spa.setDistributorId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Distributor_Id))));
//                    spa.setDistributor(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Distributor)));
//                    spa.setUploadStatus(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Upload_Status))));
//
//                    list.add(spa);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception ", e.getMessage());
//            return list;
//        }
//        return list;
//    }
//
//    public boolean insertDbWindowAllocationData(SupMappingWindowAllocationGetterSetter supMappingWindowAllocationObj) {
//        db.delete("Sup_Mapping_Window_Allocation", null, null);
//        ContentValues values = new ContentValues();
//
//        List<SupMappingAllocation> data = supMappingWindowAllocationObj.getSupMappingWindowAllocation();
//        try {
//            if (data.size() == 0) {
//                return false;
//            }
//
//            for (int i = 0; i < data.size(); i++) {
//
//                values.put(CommonString.Window_Id, data.get(i).getWindowId());
//                values.put(CommonString.Allocation, data.get(i).getAllocation());
//                values.put(CommonString.Allocation_Month, data.get(i).getAllocationMonth());
//                values.put(CommonString.LastDateOf_Month, data.get(i).getLastDateOfMonth());
//                values.put(CommonString.City_Id, data.get(i).getCityId());
//                values.put(CommonString.City, data.get(i).getCity());
//                values.put(CommonString.Distributor_Id, data.get(i).getDistributorId());
//                values.put(CommonString.Distributor, data.get(i).getDistributor());
//                values.put(CommonString.Posm_Id, data.get(i).getPosmId());
//                values.put(CommonString.Id, data.get(i).getId());
//                values.put(CommonString.MTD, data.get(i).getMTD());
//                values.put(CommonString.Upload_Status, data.get(i).getUploadStatus());
//
//                long id = db.insert("Sup_Mapping_Window_Allocation", null, values);
//                if (id == -1) {
//                    throw new Exception();
//                }
//
//            }
//            return true;
//        } catch (Exception ex) {
//            Crashlytics.logException(ex);
//            ex.printStackTrace();
//            Log.d("Database Sup_Mapping_Window_Allocation  ", ex.toString());
//            return false;
//        }
//    }
//
//    public long insertDistributorInputData(HashMap<SupMappingAllocation, List<SupMappingAllocation>> listDataChild, ArrayList<SupMappingAllocation> listDataHeader,
//                                           String db_option, String month, String visit_date, String cityId, String distributorId) {
//
//        if (db_option.equalsIgnoreCase("0")) {
//            db.delete(CommonString.TABLE_DISTRIBUTOR_POSM_DATA, CommonString.LastDateOf_Month + "='" + month + "' and " +
//                    CommonString.Distributor_Id + " = '" + distributorId + "' and " + CommonString.City_Id + " = '" + cityId + "' ", null);
//        } else {
//            db.delete(CommonString.TABLE_DISTRIBUTOR_WINDOW_KIT_DATA, CommonString.LastDateOf_Month + "='" + month + "' and " +
//                    CommonString.Distributor_Id + " = '" + distributorId + "' and " + CommonString.City_Id + " = '" + cityId + "' ", null);
//        }
//
//        ContentValues values = new ContentValues();
//        long id = 0;
//        String brand_Id = "", window_Id = "", brand_name = "", window_name = "";
//        try {
//            for (int i = 0; i < listDataHeader.size(); i++) {
//                if (db_option.equals("0")) {
//                    brand_Id = String.valueOf(listDataHeader.get(i).getBrandId());
//                    brand_name = listDataHeader.get(i).getBrand();
//                } else {
//                    window_Id = String.valueOf(listDataHeader.get(i).getWindowId());
//                    window_name = listDataHeader.get(i).getWindow();
//                }
//
//                for (int j = 0; j < listDataChild.get(listDataHeader.get(i)).size(); j++) {
//                    values.put(CommonString.Posm_Id, listDataChild.get(listDataHeader.get(i)).get(j).getPosmId());
//                    values.put(CommonString.Posm, listDataChild.get(listDataHeader.get(i)).get(j).getPosm());
//                    values.put(CommonString.Distributor_Id, listDataChild.get(listDataHeader.get(i)).get(j).getDistributorId());
//                    values.put(CommonString.Distributor, listDataChild.get(listDataHeader.get(i)).get(j).getDistributor());
//                    values.put(CommonString.City_Id, listDataChild.get(listDataHeader.get(i)).get(j).getCityId());
//                    values.put(CommonString.City, listDataChild.get(listDataHeader.get(i)).get(j).getCity());
//                    values.put(CommonString.Allocation_Month, listDataChild.get(listDataHeader.get(i)).get(j).getAllocationMonth());
//                    values.put(CommonString.LastDateOf_Month, listDataChild.get(listDataHeader.get(i)).get(j).getLastDateOfMonth());
//                    values.put(CommonString.KEY_VISIT_DATE, visit_date);
//                    values.put(CommonString.MTD, listDataChild.get(listDataHeader.get(i)).get(j).getMTD());
//                    values.put(CommonString.Today_Allocation, listDataChild.get(listDataHeader.get(i)).get(j).getTodayAllocation());
//                    values.put(CommonString.Allocation, listDataChild.get(listDataHeader.get(i)).get(j).getAllocation());
//                    values.put(CommonString.Id, listDataChild.get(listDataHeader.get(i)).get(j).getId());
//                    values.put(CommonString.Upload_Status, listDataChild.get(listDataHeader.get(i)).get(j).getUploadStatus());
//
//                    if (db_option.equalsIgnoreCase("0")) {
//                        values.put(CommonString.Brand_Id, brand_Id);
//                        values.put(CommonString.KEY_BRAND, brand_name);
//                        id = db.insert(CommonString.TABLE_DISTRIBUTOR_POSM_DATA, null, values);
//                    } else {
//                        values.put(CommonString.Window_Id, window_Id);
//                        values.put(CommonString.Window, window_name);
//                        id = db.insert(CommonString.TABLE_DISTRIBUTOR_WINDOW_KIT_DATA, null, values);
//                    }
//                }
//            }
//
//            if (id > 0) {
//                return id;
//            } else {
//                return 0;
//            }
//        } catch (Exception ex) {
//            Log.d("Database Exception ", ex.toString());
//            return 0;
//        }
//    }
//
//    public ArrayList<SupMappingAllocation> getDbPosmAllocationInsetedPosmData(String distributorId, String cityId, String month, Integer Id, String db_option, String visit_date) {
//        Log.d("posm allocation data", "------------------");
//        ArrayList<SupMappingAllocation> list = new ArrayList<>();
//        Cursor dbcursor = null;
//
//        try {
//
//            if (db_option.equalsIgnoreCase("0")) {
//                dbcursor = db.rawQuery(" select distinct MPA.Id,MPA.Distributor_Id,MPA.Distributor,MPA.Posm_Id,PM.Posm, " +
//                        " MPA.City_Id,MPA.City,MPA.Allocation,MPA.Allocation_Month,MPA.MTD,MPA.LastDateOf_Month,MPA.Upload_Status,MPA.Today_Allocation " +
//                        " from " + CommonString.TABLE_DISTRIBUTOR_POSM_DATA + " MPA " +
//                        " INNER JOIN Sup_Posm_Master PM ON MPA.Posm_Id = PM.Posm_Id " +
//                        " where " + CommonString.Distributor_Id + " = '" + distributorId + "'" +
//                        " and " + CommonString.City_Id + " = '" + cityId + "' and " + CommonString.LastDateOf_Month + " = '" + month + "' and Brand_Id = '"
//                        + Id + "' and " + CommonString.KEY_VISIT_DATE + " = '" + visit_date + "' ", null);
//            } else {
//                dbcursor = db.rawQuery(" select distinct MWA.Id,MWA.Distributor_Id,MWA.Distributor,MWA.Posm_Id,PM.Posm, " +
//                        " MWA.City_Id,MWA.City,MWA.Allocation,MWA.Allocation_Month,MWA.MTD,MWA.LastDateOf_Month,MWA.Upload_Status,MWA.Today_Allocation " +
//                        " from " + CommonString.TABLE_DISTRIBUTOR_WINDOW_KIT_DATA + " MWA " +
//                        " INNER JOIN Sup_Posm_Master PM ON MWA.Posm_Id = PM.Posm_Id " +
//                        " where " + CommonString.Distributor_Id + " = '" + distributorId + "'" +
//                        " and " + CommonString.City_Id + " = '" + cityId + "' and " + CommonString.LastDateOf_Month + " = '" + month + "' " +
//                        " and Window_Id = '" + Id + "' and " + CommonString.KEY_VISIT_DATE + " = '" + visit_date + "'", null);
//            }
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    SupMappingAllocation spa = new SupMappingAllocation();
//
//                    spa.setId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Id))));
//                    spa.setAllocationMonth(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Allocation_Month)));
//                    spa.setLastDateOfMonth(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.LastDateOf_Month)));
//                    spa.setAllocation(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Allocation))));
//                    spa.setMTD(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.MTD))));
//                    spa.setCityId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.City_Id))));
//                    spa.setCity(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.City)));
//                    spa.setPosmId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Posm_Id))));
//                    spa.setPosm(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Posm)));
//                    spa.setDistributorId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Distributor_Id))));
//                    spa.setDistributor(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Distributor)));
//                    spa.setUploadStatus(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Upload_Status))));
//                    spa.setTodayAllocation(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Today_Allocation)));
//
//                    list.add(spa);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception ", e.getMessage());
//            return list;
//        }
//        return list;
//    }
//
//
//    public ArrayList<SupMappingAllocation> getDbPosmAllocationInsetedPosmData(String distributorId, String cityId, String month, String db_option, String visit_date) {
//        Log.d("posm allocation data", "------------------");
//        ArrayList<SupMappingAllocation> list = new ArrayList<>();
//        Cursor dbcursor = null;
//
//        try {
//
//            if (db_option.equalsIgnoreCase("0")) {
//                dbcursor = db.rawQuery(" select distinct MPA.Id,MPA.Distributor_Id,MPA.Distributor,MPA.Posm_Id,PM.Posm, " +
//                        " MPA.City_Id,MPA.City,MPA.Allocation,MPA.Allocation_Month,MPA.MTD,MPA.LastDateOf_Month,MPA.Upload_Status,MPA.Today_Allocation " +
//                        " from " + CommonString.TABLE_DISTRIBUTOR_POSM_DATA + " MPA " +
//                        " INNER JOIN Sup_Posm_Master PM ON MPA.Posm_Id = PM.Posm_Id " +
//                        " where " + CommonString.Distributor_Id + " = '" + distributorId + "'" +
//                        " and " + CommonString.City_Id + " = '" + cityId + "' and " + CommonString.LastDateOf_Month + " = '" + month + "' and " + CommonString.KEY_VISIT_DATE + " = '" + visit_date + "' ", null);
//            } else {
//                dbcursor = db.rawQuery(" select distinct MWA.Id,MWA.Distributor_Id,MWA.Distributor,MWA.Posm_Id,PM.Posm, " +
//                        " MWA.City_Id,MWA.City,MWA.Allocation,MWA.Allocation_Month,MWA.MTD,MWA.LastDateOf_Month,MWA.Upload_Status,MWA.Today_Allocation " +
//                        " from " + CommonString.TABLE_DISTRIBUTOR_WINDOW_KIT_DATA + " MWA " +
//                        " INNER JOIN Sup_Posm_Master PM ON MWA.Posm_Id = PM.Posm_Id " +
//                        " where " + CommonString.Distributor_Id + " = '" + distributorId + "'" +
//                        " and " + CommonString.City_Id + " = '" + cityId + "' and " + CommonString.LastDateOf_Month + " = '" + month + "' " +
//                        " and " + CommonString.KEY_VISIT_DATE + " = '" + visit_date + "'", null);
//            }
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    SupMappingAllocation spa = new SupMappingAllocation();
//
//                    spa.setId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Id))));
//                    spa.setAllocationMonth(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Allocation_Month)));
//                    spa.setLastDateOfMonth(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.LastDateOf_Month)));
//                    spa.setAllocation(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Allocation))));
//                    spa.setMTD(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.MTD))));
//                    spa.setCityId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.City_Id))));
//                    spa.setCity(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.City)));
//                    spa.setPosmId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Posm_Id))));
//                    spa.setPosm(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Posm)));
//                    spa.setDistributorId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Distributor_Id))));
//                    spa.setDistributor(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Distributor)));
//                    spa.setUploadStatus(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Upload_Status))));
//                    spa.setTodayAllocation(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Today_Allocation)));
//
//                    list.add(spa);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception ", e.getMessage());
//            return list;
//        }
//        return list;
//    }
//
//    public ArrayList<SupMappingAllocation> getDbPosmAllocationInsetedPosmData(String distributorId, String cityId, String db_option, String visit_date) {
//        Log.d("posm allocation data", "------------------");
//        ArrayList<SupMappingAllocation> list = new ArrayList<>();
//        Cursor dbcursor = null;
//
//        try {
//
//            if (db_option.equalsIgnoreCase("0")) {
//                dbcursor = db.rawQuery(" select distinct MPA.Id,MPA.Distributor_Id,MPA.Distributor,MPA.Posm_Id,PM.Posm, " +
//                        " MPA.City_Id,MPA.City,MPA.Allocation,MPA.Allocation_Month,MPA.MTD,MPA.LastDateOf_Month,MPA.Upload_Status,MPA.Today_Allocation " +
//                        " from " + CommonString.TABLE_DISTRIBUTOR_POSM_DATA + " MPA " +
//                        " INNER JOIN Sup_Posm_Master PM ON MPA.Posm_Id = PM.Posm_Id " +
//                        " where " + CommonString.Distributor_Id + " = '" + distributorId + "'" +
//                        " and " + CommonString.City_Id + " = '" + cityId + "'  and " + CommonString.KEY_VISIT_DATE + " = '" + visit_date + "' ", null);
//            } else {
//                dbcursor = db.rawQuery(" select distinct MWA.Id,MWA.Distributor_Id,MWA.Distributor,MWA.Posm_Id,PM.Posm, " +
//                        " MWA.City_Id,MWA.City,MWA.Allocation,MWA.Allocation_Month,MWA.MTD,MWA.LastDateOf_Month,MWA.Upload_Status,MWA.Today_Allocation " +
//                        " from " + CommonString.TABLE_DISTRIBUTOR_WINDOW_KIT_DATA + " MWA " +
//                        " INNER JOIN Sup_Posm_Master PM ON MWA.Posm_Id = PM.Posm_Id " +
//                        " where " + CommonString.Distributor_Id + " = '" + distributorId + "'" +
//                        " and " + CommonString.City_Id + " = '" + cityId + "' and " + CommonString.KEY_VISIT_DATE + " = '" + visit_date + "'", null);
//            }
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    SupMappingAllocation spa = new SupMappingAllocation();
//
//                    spa.setId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Id))));
//                    spa.setAllocationMonth(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Allocation_Month)));
//                    spa.setLastDateOfMonth(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.LastDateOf_Month)));
//                    spa.setAllocation(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Allocation))));
//                    spa.setMTD(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.MTD))));
//                    spa.setCityId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.City_Id))));
//                    spa.setCity(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.City)));
//                    spa.setPosmId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Posm_Id))));
//                    spa.setPosm(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Posm)));
//                    spa.setDistributorId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Distributor_Id))));
//                    spa.setDistributor(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Distributor)));
//                    spa.setUploadStatus(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Upload_Status))));
//                    spa.setTodayAllocation(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Today_Allocation)));
//
//                    list.add(spa);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception ", e.getMessage());
//            return list;
//        }
//        return list;
//    }
//
//
//    public ArrayList<SupMappingAllocation> getDbPosmAllocationInsetedPosmData(String db_option, String visit_date, int Upload_Staus) {
//        Log.d("posm allocation data", "------------------");
//        ArrayList<SupMappingAllocation> list = new ArrayList<>();
//        Cursor dbcursor = null;
//
//        try {
//            if (db_option.equalsIgnoreCase("0")) {
//                dbcursor = db.rawQuery("select * from " + CommonString.TABLE_DISTRIBUTOR_POSM_DATA + " where " + CommonString.KEY_VISIT_DATE + " = '" + visit_date + "' and " + CommonString.Upload_Status + " <> '" + Upload_Staus + "'", null);
//            } else {
//                dbcursor = db.rawQuery("select * from " + CommonString.TABLE_DISTRIBUTOR_WINDOW_KIT_DATA + " where " + CommonString.KEY_VISIT_DATE + " = '" + visit_date + "' and " + CommonString.Upload_Status + " <> '" + Upload_Staus + "'", null);
//            }
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    SupMappingAllocation spa = new SupMappingAllocation();
//
//                    spa.setId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Id))));
//                    spa.setAllocationMonth(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Allocation_Month)));
//                    spa.setLastDateOfMonth(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.LastDateOf_Month)));
//                    spa.setAllocation(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Allocation))));
//                    spa.setMTD(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.MTD))));
//                    spa.setCityId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.City_Id))));
//                    spa.setCity(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.City)));
//
//                    if (db_option.equalsIgnoreCase("0")) {
//                        spa.setBrandId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Brand_Id))));
//                    } else {
//                        spa.setWindowId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Window_Id))));
//                    }
//
//                    spa.setPosmId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Posm_Id))));
//                    spa.setPosm(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Posm)));
//                    spa.setDistributorId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Distributor_Id))));
//                    spa.setDistributor(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Distributor)));
//                    spa.setUploadStatus(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Upload_Status))));
//                    spa.setTodayAllocation(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Today_Allocation)));
//
//                    list.add(spa);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception ", e.getMessage());
//            return list;
//        }
//        return list;
//    }
//
//    public long updateDistributorUpload_Status(ArrayList<SupMappingAllocation> dbList, String date, String table, int status) {
//        long id = 0;
//        ContentValues values = new ContentValues();
//
//        try {
//
//            for (int i = 0; i < dbList.size(); i++) {
//                values.put(CommonString.Upload_Status, status);
//                id = db.update(table, values, CommonString.KEY_VISIT_DATE + " = '" + date + "' and " + CommonString.Distributor_Id + " = " + dbList.get(i).getDistributorId() + "", null);
//            }
//
//            if (id > 0) {
//                return id;
//            } else {
//                return 0;
//            }
//        } catch (Exception ex) {
//            Log.d("Database Exception ", ex.toString());
//            return 0;
//        }
//    }
//
//    public ArrayList<SupMappingAllocation> getDbPosmAllocationPosmData(String db_option, int upload_status) {
//        Log.d("posm allocation data", "------------------");
//        ArrayList<SupMappingAllocation> list = new ArrayList<>();
//        Cursor dbcursor = null;
//
//        try {
//            if (db_option.equalsIgnoreCase("0")) {
//                dbcursor = db.rawQuery(" select Upload_Status from Sup_Mapping_Posm_Allocation where " + CommonString.Upload_Status + " <> '" + upload_status + "'", null);
//            } else {
//                dbcursor = db.rawQuery(" select Upload_Status from Sup_Mapping_Window_Allocation where " + CommonString.Upload_Status + " <> '" + upload_status + "'", null);
//            }
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    SupMappingAllocation spa = new SupMappingAllocation();
//
//                    spa.setUploadStatus(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Upload_Status))));
//
//                    list.add(spa);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception ", e.getMessage());
//            return list;
//        }
//        return list;
//    }
//
//    public ArrayList<SupMappingAllocation> getDbPosmAllocationPosmData(String db_option, String date) {
//        Log.d("posm allocation data", "------------------");
//        ArrayList<SupMappingAllocation> list = new ArrayList<>();
//        Cursor dbcursor = null;
//        String allocation = "";
//        try {
//            if (db_option.equalsIgnoreCase("0")) {
//                dbcursor = db.rawQuery(" select distinct City_Id,Distributor_Id,Distributor,City,Upload_Status from DISTRIBUTOR_POSM_DATA where " + CommonString.KEY_VISIT_DATE + " = '" + date + "'", null);
//            } else {
//                dbcursor = db.rawQuery(" select distinct City_Id,Distributor_Id,Distributor,City,Upload_Status from DISTRIBUTOR_WINDOW_KIT_DATA where " + CommonString.KEY_VISIT_DATE + " = '" + date + "'", null);
//            }
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    SupMappingAllocation spa = new SupMappingAllocation();
//
//                    spa.setCityId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.City_Id))));
//                    spa.setCity(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.City)));
//                    spa.setDistributorId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Distributor_Id))));
//                    spa.setDistributor(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Distributor)));
//                    spa.setUploadStatus(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.Upload_Status)));
//
//                    list.add(spa);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception ", e.getMessage());
//            return list;
//        }
//        return list;
//    }
//
//    public void deleteDistributorDat(String date) {
//        db.delete("DISTRIBUTOR_POSM_DATA", CommonString.KEY_VISIT_DATE + " <>'" + date + "'", null);
//        db.delete("DISTRIBUTOR_WINDOW_KIT_DATA", CommonString.KEY_VISIT_DATE + " <>'" + date + "'", null);
//        db.delete(CommonString.TABLE_SUP_MERCHANDISER_TOOL_AUDIT, CommonString.KEY_VISIT_DATE + " <>'" + date + "'", null);
//    }
//
//    public ArrayList<SupMappingAllocation> getDbPosmAllocationInsetedDBData(String distributorId, String cityId, String db_option, String visit_date) {
//        Log.d("posm allocation data", "------------------");
//        ArrayList<SupMappingAllocation> list = new ArrayList<>();
//        Cursor dbcursor = null;
//        String allocation = "";
//        try {
//
//            if (db_option.equalsIgnoreCase("0")) {
//                dbcursor = db.rawQuery(" select distinct MPA.Id,MPA.Distributor_Id,MPA.Distributor,MPA.Posm_Id,PM.Posm, " +
//                        " MPA.City_Id,MPA.City,MPA.Allocation,MPA.Allocation_Month,MPA.MTD,MPA.LastDateOf_Month,MPA.Upload_Status,MPA.Today_Allocation,MPA.Brand " +
//                        " from " + CommonString.TABLE_DISTRIBUTOR_POSM_DATA + " MPA " +
//                        " INNER JOIN Sup_Posm_Master PM ON MPA.Posm_Id = PM.Posm_Id " +
//                        " where " + CommonString.Distributor_Id + " = '" + distributorId + "'" +
//                        " and " + CommonString.City_Id + " = '" + cityId + "'  and " + CommonString.KEY_VISIT_DATE + " = '" + visit_date + "' ", null);
//            } else {
//                dbcursor = db.rawQuery(" select distinct MWA.Id,MWA.Distributor_Id,MWA.Distributor,MWA.Posm_Id,PM.Posm, " +
//                        " MWA.City_Id,MWA.City,MWA.Allocation,MWA.Allocation_Month,MWA.MTD,MWA.LastDateOf_Month,MWA.Upload_Status,MWA.Today_Allocation,MWA.Window " +
//                        " from " + CommonString.TABLE_DISTRIBUTOR_WINDOW_KIT_DATA + " MWA " +
//                        " INNER JOIN Sup_Posm_Master PM ON MWA.Posm_Id = PM.Posm_Id " +
//                        " where " + CommonString.Distributor_Id + " = '" + distributorId + "'" +
//                        " and " + CommonString.City_Id + " = '" + cityId + "' and " + CommonString.KEY_VISIT_DATE + " = '" + visit_date + "'", null);
//            }
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    SupMappingAllocation spa = new SupMappingAllocation();
//
//                    allocation = dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Today_Allocation));
//                    if(!allocation.equalsIgnoreCase("")) {
//                        spa.setId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Id))));
//                        spa.setAllocationMonth(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Allocation_Month)));
//                        spa.setLastDateOfMonth(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.LastDateOf_Month)));
//                        spa.setAllocation(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Allocation))));
//                        spa.setMTD(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.MTD))));
//                        spa.setCityId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.City_Id))));
//                        spa.setCity(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.City)));
//                        spa.setPosmId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Posm_Id))));
//                        spa.setPosm(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Posm)));
//                        spa.setDistributorId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Distributor_Id))));
//                        spa.setDistributor(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Distributor)));
//                        spa.setUploadStatus(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Upload_Status))));
//                        spa.setTodayAllocation(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Today_Allocation)));
//
//                        if (db_option.equalsIgnoreCase("0")) {
//                            spa.setBrand(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_BRAND)));
//                        } else {
//                            spa.setWindow(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Window)));
//                        }
//                        list.add(spa);
//                    }
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception ", e.getMessage());
//            return list;
//        }
//        return list;
//    }
//
//    public ArrayList<PrimarySelfData> getBrandSkuData(JourneyPlan jcpGetset, String brand_id) {
//        ArrayList<PrimarySelfData> list = new ArrayList<>();
//        Cursor dbcursor = null;
//
//        try {
//
//            dbcursor = db.rawQuery(" select distinct m.Emp_Id,m.Store_Id,m.Sku_id as Sku_Id, sk.Sku as Sku, m.Merchandiser_MID as Merchandiser_MID, m.Stock_Qty as Stock_Qty,sk.Brand_Id,bm.Brand from Sup_Primary_Shelf_Audit m " +
//                    " inner join Sku_Master sk on m.Sku_Id = sk.Sku_Id " +
//                    " inner join Brand_Master bm on sk.Brand_Id = bm.Brand_Id " +
//                    " where m.Store_Id = " + jcpGetset.getStoreId() + " and Emp_Id = " + jcpGetset.getEmpId() + " and bm.Brand_Id = " + brand_id + "", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    PrimarySelfData psd = new PrimarySelfData();
//
//                    psd.setSku_Id(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_SKU_ID)));
//                    psd.setSku(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_SKU)));
//                    psd.setStock_Quy(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_STOCK_QUY)));
//
//                    psd.setMer_MID(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_MER_MID)));
//
//                    psd.setBrand_Id(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Brand_Id)));
//
//                    psd.setBrand(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_BRAND)));
//
//                    psd.setEmp_Id(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.KEY_EMP_ID)));
//
//                    psd.setStoreId(dbcursor.getString(dbcursor
//                            .getColumnIndexOrThrow(CommonString.Store_Id)));
//
//
//                    list.add(psd);
//                    dbcursor.moveToNext();
//                }
//
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception get JCP!", e.toString());
//            return list;
//        }
//        return list;
//    }
//
//    public ArrayList<PrimarySelfData> getBrandSkuInsertedData(JourneyPlan jcpGetset, String brand_id) {
//        Log.d("FetchingStored", "------------------");
//        ArrayList<PrimarySelfData> list = new ArrayList<>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("SELECT  * from "
//                    + CommonString.TABLE_SUP_PRIMARY_SELF + " where "
//                    + CommonString.KEY_STORE_ID + " = '" + jcpGetset.getStoreId() + "' and "+CommonString.Brand_Id+" = '"+brand_id+"'", null);
//
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    PrimarySelfData psd = new PrimarySelfData();
//                    psd.setStoreId(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_STORE_ID)));
//                    psd.setMer_MID(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_MER_MID)));
//                    psd.setSku(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_SKU)));
//                    psd.setSku_Id(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_SKU_ID)));
//                    psd.setBrand(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_BRAND)));
//                    psd.setBrand_Id(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Brand_Id)));
//                    psd.setStock_Quy(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_MER_STOCK_QUY)));
//                    psd.setSup_data(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_SUP_STOCK_QUY)));
//                    psd.setTap_roll_check(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_TAPE_ROLL_CHECK)));
//                    psd.setFifo_chk(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_FIFO_CHCEK)));
//
//                    list.add(psd);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception ", e.getMessage());
//            return list;
//        }
//        return list;
//    }
//
//    public List<JourneyPlan> getJournyPlanTodayData(String outlet_today, String empId) {
//
//        ArrayList<JourneyPlan> list = new ArrayList<JourneyPlan>();
//        Cursor dbcursor = null;
//
//        try {
//
//            dbcursor = db.rawQuery("SELECT distinct * from  " + outlet_today + " where Emp_Id = '" + empId + "' ", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//
//                    JourneyPlan sb = new JourneyPlan();
//                    sb.setEmpId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Emp_Id"))));
//                    sb.setAddress1(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Address1")));
//                    sb.setAddress2(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Address2")));
//                    sb.setCity(dbcursor.getString(dbcursor.getColumnIndexOrThrow("City")));
//                    sb.setCityId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("City_Id"))));
//                    sb.setClassification(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Classification")));
//                    sb.setClassificationId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Classification_Id"))));
//                    sb.setDistributorId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Distributor_Id"))));
//                    sb.setEmpId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Emp_Id"))));
//                    sb.setGeoTag(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Geo_Tag")));
//                    sb.setLandmark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Landmark")));
//                    sb.setPincode(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Pincode")));
//                    sb.setReasonId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Reason_Id"))));
//                    sb.setRemark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Remark")));
//                    sb.setStoreCategory(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Category")));
//                    sb.setStoreCategoryId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Category_Id"))));
//                    sb.setStoreId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Id"))));
//                    sb.setStoreName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Name")));
//                    sb.setStoreType(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Type")));
//                    sb.setStoreTypeId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Type_Id"))));
//                    sb.setSubReasonId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Sub_Reason_Id"))));
//                    sb.setTierId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Tier_Id"))));
//                    sb.setTradeAreaId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Trade_Area_Id"))));
//                    sb.setUploadStatus(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Upload_Status")));
//                    sb.setVisitDate(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Visit_Date")));
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Crashlytics.logException(e);
//            Log.d("Exception when fetc", e.toString());
//            return list;
//        }
//
//        Log.d("FetchingStore", "-------------------");
//        return list;
//    }
//
//    public List<SupPrimaryShelfAudit> getPrimaryShelfInsertedData(String empId) {
//        Log.d("FetchingStored", "------------------");
//        ArrayList<SupPrimaryShelfAudit> list = new ArrayList<>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("SELECT  * from  Sup_Primary_Shelf_Audit where " + CommonString.KEY_EMP_ID + " = '" + empId + "'", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    SupPrimaryShelfAudit psd = new SupPrimaryShelfAudit();
//                    psd.setStoreId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_STORE_ID)));
//                    psd.setMerchandiserMID(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_MER_MID)));
//                    psd.setEmpId(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_EMP_ID)));
//
//                    list.add(psd);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception ", e.getMessage());
//            return list;
//        }
//        return list;
//    }
//
//    public List<SupSelfServicePrimaryAudit> getShelfServicePrimaryInsertedData(String empId) {
//        Log.d("FetchingStoredata-----", "------------------");
//        ArrayList<SupSelfServicePrimaryAudit> list = new ArrayList<SupSelfServicePrimaryAudit>();
//        Cursor dbcursor = null;
//
//        try {
//
//            dbcursor = db.rawQuery("SELECT  * from  Sup_SelfService_Primary_Audit where " + CommonString.KEY_EMP_ID + " = '" + empId + "'", null);
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    SupSelfServicePrimaryAudit sb = new SupSelfServicePrimaryAudit();
//                    sb.setMerMID(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Mer_MID"))));
//                    sb.setBrandGroupId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("Brand_Group_Id")));
//                    sb.setTranId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("TranId")));
//                    sb.setStoreId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("Store_Id")));
//                    sb.setEmpId(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Emp_Id")));
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Crashlytics.logException(e);
//            Log.d("Exception when fetching", e.toString());
//            return list;
//        }
//        return list;
//    }
//
//    public List<SupSelfserviceTouchpointAudit> getShelfServiceTouchPointInsertedData(String empId) {
//        Log.d("FetchingStoredata-----", "------------------");
//        ArrayList<SupSelfserviceTouchpointAudit> list = new ArrayList<SupSelfserviceTouchpointAudit>();
//        Cursor dbcursor = null;
//
//        try {
//
//            dbcursor = db.rawQuery("SELECT  * from  Sup_Selfservice_Touchpoint_Audit where " + CommonString.KEY_EMP_ID + " = '" + empId + "'", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    SupSelfserviceTouchpointAudit sb = new SupSelfserviceTouchpointAudit();
//                    sb.setEmpId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Emp_Id"))));
//                    sb.setMerchandiserMID(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("Merchandiser_MID")));
//                    sb.setBrandId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("Brand_Id")));
//                    sb.setPosmId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("Posm_Id")));
//
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Crashlytics.logException(e);
//            Log.d("Exception when fetching", e.toString());
//            return list;
//        }
//        return list;
//    }
//
//    public List<SupSelfservicePromotionCompetitionAudit> getShelfServicePromotionCompetitionInsertedData(String empId) {
//        Log.d("FetchingStoredata-----", "------------------");
//        ArrayList<SupSelfservicePromotionCompetitionAudit> list = new ArrayList<SupSelfservicePromotionCompetitionAudit>();
//        Cursor dbcursor = null;
//
//        try {
//
//            dbcursor = db.rawQuery("SELECT  * from  Sup_Selfservice_Promotion_Competition_Audit where " + CommonString.KEY_EMP_ID + " = '" + empId + "'", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    SupSelfservicePromotionCompetitionAudit sb = new SupSelfservicePromotionCompetitionAudit();
//                    sb.setEmpId(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Emp_Id")));
//                    sb.setBrandId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("Brand_Id")));
//                    sb.setCategoryId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("Category_Id")));
//                    sb.setStoreId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("Store_Id")));
//                    sb.setMerMID(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("Mer_MID")));
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Crashlytics.logException(e);
//            Log.d("Exception when fetching", e.toString());
//            return list;
//        }
//        return list;
//    }
//
//    public List<SupSelfServicePromotionAudit> getShelfServicePromotionInsertedData(String empId) {
//        Log.d("FetchingStoredata-----", "------------------");
//        ArrayList<SupSelfServicePromotionAudit> list = new ArrayList<SupSelfServicePromotionAudit>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("SELECT  * from  Sup_SelfService_Promotion_Audit where " + CommonString.KEY_EMP_ID + " = '" + empId + "'", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    SupSelfServicePromotionAudit sb = new SupSelfServicePromotionAudit();
//                    sb.setEmpId(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Emp_Id")));
//                    sb.setBrandId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("Brand_Id")));
//                    sb.setCategoryId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("Category_Id")));
//                    sb.setStoreId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("Store_Id")));
//                    sb.setMerMID(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("Mer_MID")));
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Crashlytics.logException(e);
//            Log.d("Exception when fetching", e.toString());
//            return list;
//        }
//        return list;
//    }
//
//
//    public List<SupSelfServiceSecondaryWindowAudit> getShelfServiceSecondaryWindowInsertedData(String empId) {
//        Log.d("FetchingStoredata-----", "------------------");
//        ArrayList<SupSelfServiceSecondaryWindowAudit> list = new ArrayList<SupSelfServiceSecondaryWindowAudit>();
//        Cursor dbcursor = null;
//
//        try {
//
//            dbcursor = db.rawQuery("SELECT  * from  Sup_SelfService_SecondaryWindow_Audit where " + CommonString.KEY_EMP_ID + " = '" + empId + "'", null);
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    SupSelfServiceSecondaryWindowAudit sb = new SupSelfServiceSecondaryWindowAudit();
//                    sb.setEmpId(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Emp_Id")));
//                    sb.setBrandId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("Brand_Id")));
//                    sb.setCategoryId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("Category_Id")));
//                    sb.setStoreId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("Store_Id")));
//                    sb.setMerMID(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("Mer_MID")));
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Crashlytics.logException(e);
//            Log.d("Exception when fetching", e.toString());
//            return list;
//        }
//        return list;
//    }
//
//    public List<SupWindowAudit> getWinodwInsertedData(String empId) {
//        Log.d("FetchingStoredata-----", "------------------");
//        ArrayList<SupWindowAudit> list = new ArrayList<SupWindowAudit>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("SELECT  * from  Sup_Window_Audit where " + CommonString.KEY_EMP_ID + " = '" + empId + "'", null);
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    SupWindowAudit sb = new SupWindowAudit();
//                    sb.setEmpId(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Emp_Id")));
//                    sb.setMerchandiserMID(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("Merchandiser_MID")));
//                    sb.setWindowId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("Window_Id")));
//                    sb.setStoreId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("Store_Id")));
//
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Crashlytics.logException(e);
//            Log.d("Exception when fetching", e.toString());
//            return list;
//        }
//        return list;
//    }
//
//    public List<SupTouchpointAudit> getTouchPointInsertedData(String empId) {
//        Log.d("FetchingStoredata-----", "------------------");
//        ArrayList<SupTouchpointAudit> list = new ArrayList<SupTouchpointAudit>();
//        Cursor dbcursor = null;
//
//        try {
//
//            dbcursor = db.rawQuery("SELECT  * from  Sup_Touchpoint_Audit where " + CommonString.KEY_EMP_ID + " = '" + empId + "'", null);
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    SupTouchpointAudit sb = new SupTouchpointAudit();
//                    sb.setEmpId(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Emp_Id")));
//                    sb.setMerchandiserMID(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("Merchandiser_MID")));
//                    sb.setPosmId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("Posm_Id")));
//                    sb.setStoreId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("Store_Id")));
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Crashlytics.logException(e);
//            Log.d("Exception when fetching", e.toString());
//            return list;
//        }
//        return list;
//    }
//
//    public List<SupMappingStock> getMappingStockInsertedData(String empId) {
//        Log.d("FetchingStoredata-----", "------------------");
//        ArrayList<SupMappingStock> list = new ArrayList<SupMappingStock>();
//        Cursor dbcursor = null;
//
//        try {
//
//            dbcursor = db.rawQuery("SELECT  * from  Sup_Mapping_Stock where " + CommonString.KEY_EMP_ID + " = '" + empId + "'", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    SupMappingStock sb = new SupMappingStock();
//                    sb.setEmpId(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Emp_Id")));
//                    sb.setClassificationId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("Classification_Id")));
//                    sb.setTierId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("Tier_Id")));
//                    sb.setSkuId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("Sku_Id")));
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Crashlytics.logException(e);
//            Log.d("Exception when fetching", e.toString());
//            return list;
//        }
//        return list;
//    }
//
//    public String getFinalStoreData(String storeId, String classificationID) {
//        String date = "";
//        Cursor dbcursor = null;
//
//        try {
//
//            if(!classificationID.equalsIgnoreCase("2")) {
//                dbcursor = db.rawQuery("select distinct Visit_Date from Sup_Touchpoint_Audit where Store_Id = '" + storeId + "' union " +
//                                            "select distinct Visit_Date from Sup_Window_Audit Where Store_Id = '" + storeId + "' union " +
//                                            "select distinct Visit_Date from Sup_Primary_Shelf_Audit where Store_Id = '" + storeId + "' ", null);
//            }else{
//                dbcursor = db.rawQuery("select distinct Visit_Date from Sup_SelfService_Primary_Audit where Store_Id = '" + storeId + "' union " +
//                                            "select distinct Visit_Date from Sup_SelfService_Promotion_Audit Where Store_Id = '" + storeId + "' union " +
//                                            "select distinct Visit_Date from Sup_SelfService_SecondaryWindow_Audit where Store_Id = '" + storeId + "' union " +
//                                            "select distinct Visit_Date from Sup_Selfservice_Promotion_Competition_Audit Where Store_Id = '" + storeId + "' union " +
//                                            "select distinct Visit_Date from Sup_Selfservice_Touchpoint_Audit where Store_Id = '" + storeId + "' ", null);
//            }
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    date = dbcursor.getString(dbcursor.getColumnIndexOrThrow("Visit_Date"));
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return date;
//            }
//
//        } catch (Exception e) {
//            Crashlytics.logException(e);
//            Log.d("Exception when fetc", e.toString());
//            return date;
//        }
//
//        Log.d("FetchingStore", "-------------------");
//        return date;
//
//    }
//
//
//    public ArrayList<PrimaryBayGetterSetter> getPrimaryBayDataForAudit(String store_id) {
//        Log.d("Fetching primaryBay data--------------->Start<------------",
//                "------------------");
//        ArrayList<PrimaryBayGetterSetter> list = new ArrayList<PrimaryBayGetterSetter>();
//        Cursor dbcursor = null;
//        try {
//
//            dbcursor = db.rawQuery("select * from Sup_SelfService_Primary_Audit where Store_Id = '"+store_id+"'" , null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//
//                    PrimaryBayGetterSetter data = new PrimaryBayGetterSetter();
//                    data.setBrandGroupId(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_BRAND_GROUP_ID)));
//                    data.setCategoryId(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Category_Id)));
//
//                    data.setCategory_total(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Category_Total)));
//                    data.setCategory_header(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Category_Header)));
//                    data.setShelf_count(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Shelf_Count)));
//                    data.setShelf_strip(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Shelf_Strip)));
//
//                    data.setStoreId(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Store_Id)));
//                    data.setMer_MID(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Mer_MID)));
//                    data.setTransId(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.TranId)));
//
//                    list.add(data);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
//                    e.toString());
//            return list;
//        }
//
//        Log.d("Fetching Primary Bay Data---------------------->Stop<-----------",
//                "-------------------");
//        return list;
//    }
//
//    public String getLoginVisitedDate(String date) {
//        String loginDate = "";
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("Select * from "+CommonString.TABLE_LOGIN_VISITED_DATE+" where "+CommonString.KEY_VISIT_DATE+" = '"+date+"'", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    loginDate = dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_VISIT_DATE));
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return loginDate;
//            }
//
//        } catch (Exception e) {
//            return loginDate;
//        }
//        return loginDate;
//    }
//
//
//
//
//    public ArrayList<JourneyPlan> getTodayStoreData(String empId, String visit_date, String outlet_today) {
//
//        ArrayList<JourneyPlan> list = new ArrayList<JourneyPlan>();
//        Cursor dbcursor = null;
//
//        try {
//
//            dbcursor = db.rawQuery("SELECT distinct * from  " + outlet_today + " where Visit_Date ='" + visit_date + "' and Emp_Id = "+empId+" ", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//
//                    JourneyPlan sb = new JourneyPlan();
//                    sb.setEmpId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Emp_Id"))));
//                    sb.setAddress1(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Address1")));
//                    sb.setAddress2(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Address2")));
//                    sb.setCity(dbcursor.getString(dbcursor.getColumnIndexOrThrow("City")));
//                    sb.setCityId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("City_Id"))));
//                    sb.setClassification(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Classification")));
//                    sb.setClassificationId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Classification_Id"))));
//                    sb.setDistributorId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Distributor_Id"))));
//                    sb.setEmpId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Emp_Id"))));
//                    sb.setGeoTag(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Geo_Tag")));
//                    sb.setLandmark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Landmark")));
//                    sb.setPincode(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Pincode")));
//                    sb.setReasonId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Reason_Id"))));
//                    sb.setRemark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Remark")));
//                    sb.setStoreCategory(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Category")));
//                    sb.setStoreCategoryId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Category_Id"))));
//                    sb.setStoreId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Id"))));
//                    sb.setStoreName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Name")));
//                    sb.setStoreType(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Type")));
//                    sb.setStoreTypeId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Type_Id"))));
//                    sb.setSubReasonId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Sub_Reason_Id"))));
//                    sb.setTierId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Tier_Id"))));
//                    sb.setTradeAreaId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Trade_Area_Id"))));
//                    sb.setUploadStatus(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Upload_Status")));
//                    sb.setVisitDate(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Visit_Date")));
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Crashlytics.logException(e);
//            Log.d("Exception when fetc", e.toString());
//            return list;
//        }
//
//        Log.d("FetchingStore", "-------------------");
//        return list;
//    }
//
//    public ArrayList<JourneyPlan> getPreviousDayStoreData(String empId, String visit_date, String outlet_today) {
//
//        ArrayList<JourneyPlan> list = new ArrayList<JourneyPlan>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("SELECT distinct * from  " + outlet_today + " where Visit_Date ='" + visit_date + "' and Emp_Id = "+empId+" ", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//
//                    JourneyPlan sb = new JourneyPlan();
//                    sb.setEmpId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Emp_Id"))));
//                    sb.setAddress1(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Address1")));
//                    sb.setAddress2(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Address2")));
//                    sb.setCity(dbcursor.getString(dbcursor.getColumnIndexOrThrow("City")));
//                    sb.setCityId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("City_Id"))));
//                    sb.setClassification(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Classification")));
//                    sb.setClassificationId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Classification_Id"))));
//                    sb.setDistributorId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Distributor_Id"))));
//                    sb.setEmpId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Emp_Id"))));
//                    sb.setGeoTag(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Geo_Tag")));
//                    sb.setLandmark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Landmark")));
//                    sb.setPincode(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Pincode")));
//                    sb.setReasonId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Reason_Id"))));
//                    sb.setRemark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Remark")));
//                    sb.setStoreCategory(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Category")));
//                    sb.setStoreCategoryId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Category_Id"))));
//                    sb.setStoreId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Id"))));
//                    sb.setStoreName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Name")));
//                    sb.setStoreType(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Type")));
//                    sb.setStoreTypeId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Store_Type_Id"))));
//                    sb.setSubReasonId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Sub_Reason_Id"))));
//                    sb.setTierId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Tier_Id"))));
//                    sb.setTradeAreaId(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Trade_Area_Id"))));
//                    sb.setUploadStatus(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Upload_Status")));
//                    sb.setVisitDate(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Visit_Date")));
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Crashlytics.logException(e);
//            Log.d("Exception when fetc", e.toString());
//            return list;
//        }
//
//        Log.d("FetchingStore", "-------------------");
//        return list;
//    }
//
//    public ArrayList<SupMerchandiserKitMaster> getMerchandiserAuditData(Integer merId) {
//        ArrayList<SupMerchandiserKitMaster> list = new ArrayList<SupMerchandiserKitMaster>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("SELECT * from " + CommonString.TABLE_SUP_MERCHANDISER_TOOL_AUDIT + " where Mer_Id = " + merId + " ", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//
//                    SupMerchandiserKitMaster sb = new SupMerchandiserKitMaster();
//                    sb.setMer_Id(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Mer_Id")));
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Crashlytics.logException(e);
//            Log.d("Exception when fetc", e.toString());
//            return list;
//        }
//
//        Log.d("FetchingStore", "-------------------");
//        return list;
//    }
//
//    public boolean getDeviationEntryAllowStatus(String id) {
//        Log.d("Fetching SupDeviat", "------------------");
//        boolean entryAllow = false;
//        Cursor dbcursor = null;
//        try {
//
//            dbcursor = db.rawQuery("SELECT  * from Sup_Deviation_Reason where Deviation_Reason_Id = '"+id+"'", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    String value = dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.DEVIATION_ALLOW));
//                    if (value.equalsIgnoreCase("1")) {
//                        entryAllow = true;
//                    } else {
//                        entryAllow = false;
//                    }
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return entryAllow;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception ", e.getMessage());
//            return entryAllow;
//        }
//
//        Log.d("FetchingS", "-------------------");
//        return entryAllow;
//    }
//
//    // delete data id user upload some data on server and from server
//    // if that data deleted then response comes "" so it no able to
//    // delete existing data so we are deleteing data here for that key if come ""
//    public void deleteAlreadyExistData(String finalKeyName) {
//        db.delete(finalKeyName, null, null);
//    }
//}
