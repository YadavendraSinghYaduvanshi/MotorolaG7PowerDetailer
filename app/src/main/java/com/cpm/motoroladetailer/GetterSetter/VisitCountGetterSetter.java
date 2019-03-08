package com.cpm.motoroladetailer.GetterSetter;

public class VisitCountGetterSetter {
    private String emp_cd;
    private String visited_date;

    private int smart_camera_count = 0;
    private int super_fast_processor_count=0;
    private int u_notch_display_count =0;
    private int turbo_power_charging_count=0;

    public String getEmp_cd() {
        return emp_cd;
    }

    public void setEmp_cd(String emp_cd) {
        this.emp_cd = emp_cd;
    }

    public String getVisited_date() {
        return visited_date;
    }

    public void setVisited_date(String visited_date) {
        this.visited_date = visited_date;
    }

    public int getSmart_camera_count() {
        return smart_camera_count;
    }

    public void setSmart_camera_count(int smart_camera_count) {
        this.smart_camera_count = smart_camera_count;
    }

    public int getSuper_fast_processor_count() {
        return super_fast_processor_count;
    }

    public void setSuper_fast_processor_count(int super_fast_processor_count) {
        this.super_fast_processor_count = super_fast_processor_count;
    }

    public int getU_notch_display_count() {
        return u_notch_display_count;
    }

    public void setU_notch_display_count(int u_notch_display_count) {
        this.u_notch_display_count = u_notch_display_count;
    }

    public int getTurbo_power_charging_count() {
        return turbo_power_charging_count;
    }

    public void setTurbo_power_charging_count(int turbo_power_charging_count) {
        this.turbo_power_charging_count = turbo_power_charging_count;
    }
}
