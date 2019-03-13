/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesheet.bean;

/**
 *
 * @author prodigy
 */
public class Employee {

    private String emp_id;
    private String userid;
    private String emp_password;
    private String emp_fname;
    private String fname;
    private String emp_lname;
    private String gender;
    private int dept_id;
    private String dept_name;
    private int role_id;
    private String time_right;
    private String role_name;
    private int desi_id;
    private String desi_name;
    private String emp_email;
    private String emp_address;
    private String emp_phone;
    private String emp_mobile;
    private String emp_birthdate;
    private String emp_status;

    public Employee() {
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    
   
    public String getDept_name() {
        return dept_name;
    }

    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }

    public String getDesi_name() {
        return desi_name;
    }

    public void setDesi_name(String desi_name) {
        this.desi_name = desi_name;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }


   
    public int getDept_id() {
        return dept_id;
    }

    public void setDept_id(int dept_id) {
        this.dept_id = dept_id;
    }

    public int getDesi_id() {
        return desi_id;
    }

    public void setDesi_id(int desi_id) {
        this.desi_id = desi_id;
    }

    public String getEmp_address() {
        return emp_address;
    }

    public void setEmp_address(String emp_address) {
        this.emp_address = emp_address;
    }

    public String getEmp_birthdate() {
        return emp_birthdate;
    }

    public void setEmp_birthdate(String emp_birthdate) {
        this.emp_birthdate = emp_birthdate;
    }

    public String getEmp_email() {
        return emp_email;
    }

    public void setEmp_email(String emp_email) {
        this.emp_email = emp_email;
    }

    public String getEmp_fname() {
        return emp_fname;
    }

    public void setEmp_fname(String emp_fname) {
        this.emp_fname = emp_fname;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getEmp_lname() {
        return emp_lname;
    }

    public void setEmp_lname(String emp_lname) {
        this.emp_lname = emp_lname;
    }

    public String getEmp_mobile() {
        return emp_mobile;
    }

    public void setEmp_mobile(String emp_mobile) {
        this.emp_mobile = emp_mobile;
    }

    public String getEmp_password() {
        return emp_password;
    }

    public void setEmp_password(String emp_password) {
        this.emp_password = emp_password;
    }

    public String getEmp_phone() {
        return emp_phone;
    }

    public void setEmp_phone(String emp_phone) {
        this.emp_phone = emp_phone;
    }

    public String getEmp_status() {
        return emp_status;
    }

    public void setEmp_status(String emp_status) {
        this.emp_status = emp_status;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTime_right() {
        return time_right;
    }

    public void setTime_right(String time_right) {
        this.time_right = time_right;
    }


    
    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }
}
