package com.timesheet.bean;

public class Project {
    private int proj_id;
    private String proj_name;
    private String start_date;
    private String end_date;
    private String target_date;
    private String proj_desc;
    private String status;

    public Project() {
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getProj_desc() {
        return proj_desc;
    }

    public void setProj_desc(String proj_desc) {
        this.proj_desc = proj_desc;
    }

    public int getProj_id() {
        return proj_id;
    }

    public void setProj_id(int proj_id) {
        this.proj_id = proj_id;
    }

    public String getProj_name() {
        return proj_name;
    }

    public void setProj_name(String proj_name) {
        this.proj_name = proj_name;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getTarget_date() {
        return target_date;
    }

    public void setTarget_date(String target_date) {
        this.target_date = target_date;
    }
}
