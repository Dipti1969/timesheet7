/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.timesheet.bean;

/**
 *
 * @author prodigy
 */
public class AssignBy {
    private int assign_by_id;
    private String assign_by_name;
    private String status;

    
    public AssignBy(){}
    

    public int getAssign_by_id() {
        return assign_by_id;
    }

    public void setAssign_by_id(int assign_by_id) {
        this.assign_by_id = assign_by_id;
    }

    public String getAssign_by_name() {
        return assign_by_name;
    }

    public void setAssign_by_name(String assign_by_name) {
        this.assign_by_name = assign_by_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    


}
