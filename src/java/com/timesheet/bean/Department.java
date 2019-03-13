/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesheet.bean;

/**
 *
 * @author prodigy
 */
public class Department {

    private int dept_id;
    private String dept_name;
    private String status;


    public Department() {
    }
    
    public int getDept_id() {
        return dept_id;
    }

    public void setDept_id(int dept_id) {
        this.dept_id = dept_id;
    }

    public String getDept_name() {
        return dept_name;
    }

    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
