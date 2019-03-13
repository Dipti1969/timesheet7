/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesheet.bean;

/**
 *
 * @author prodigy
 */
public class Designation {

    private int desi_id;
    private String desi_name;
    private String status;

    public Designation() {
    }

    
    public int getDesi_id() {
        return desi_id;
    }

    public void setDesi_id(int desi_id) {
        this.desi_id = desi_id;
    }

    public String getDesi_name() {
        return desi_name;
    }

    public void setDesi_name(String desi_name) {
        this.desi_name = desi_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
