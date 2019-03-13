package com.timesheet.bean;

public class EmpTransaction {

    private long trans_id;
    private String emp_id;
    private String trans_date;
    private String start_time;
    private String end_time;
    private int assign_by_id;
    private String assign_by_name;
    private String proxy_empid;
    private String emp_fname;
    private String emp_lname;
    private int proj_id;
    private String proj_name;
    private String work_desc;
    private String status;
    private String hour;
    private String total;
    private String isLate;
//    List<Map<String, String>> total;
    private String dept_name;
    private String month;
    private String year;
    private String day;

    public String getEmp_lname() {
        return emp_lname;
    }

    public void setEmp_lname(String emp_lname) {
        this.emp_lname = emp_lname;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public EmpTransaction() {
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

//    public List<Map<String,String>> getTotal() {
//    return total;
//  }
//  public void setTotal(List<Map<String,String>> total) {
//    this.total = total;
//  }
    public String getIsLate() {
        return isLate;
    }

    public void setIsLate(String isLate) {
        this.isLate = isLate;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getAssign_by_id() {
        return assign_by_id;
    }

    public void setAssign_by_id(int assign_by_id) {
        this.assign_by_id = assign_by_id;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public int getProj_id() {
        return proj_id;
    }

    public void setProj_id(int proj_id) {
        this.proj_id = proj_id;
    }

    public String getProxy_empid() {
        return proxy_empid;
    }

    public void setProxy_empid(String proxy_empid) {
        this.proxy_empid = proxy_empid;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTrans_date() {
        return trans_date;
    }

    public void setTrans_date(String trans_date) {
        this.trans_date = trans_date;
    }

    public long getTrans_id() {
        return trans_id;
    }

    public void setTrans_id(long trans_id) {
        this.trans_id = trans_id;
    }

    public String getWork_desc() {
        return work_desc;
    }

    public void setWork_desc(String work_desc) {
        this.work_desc = work_desc;
    }

    public String getAssign_by_name() {
        return assign_by_name;
    }

    public void setAssign_by_name(String assign_by_name) {
        this.assign_by_name = assign_by_name;
    }

    public String getEmp_fname() {
        return emp_fname;
    }

    public void setEmp_fname(String emp_fname) {
        this.emp_fname = emp_fname;
    }

    public String getProj_name() {
        return proj_name;
    }

    public void setProj_name(String proj_name) {
        this.proj_name = proj_name;
    }

    public String getDept_name() {
        return dept_name;
    }

    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }
}
