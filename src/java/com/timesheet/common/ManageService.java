/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.timesheet.common;

import com.timesheet.bean.AssignBy;
import com.timesheet.bean.EmpTransaction;
import java.util.List;

/**
 *
 * @author prodigy
 */
public interface  ManageService {
    public void add(AssignBy assignBy);
    public List<AssignBy> select();
    public AssignBy getById(int assignbyid);
    public boolean update(int assignbyid,String assignbyname);
    public boolean delete(int id);
    public boolean checkTrans(int assignid);
     public boolean blockAssign(int assignid);
    public boolean unblockAssign(int assignid);

}
