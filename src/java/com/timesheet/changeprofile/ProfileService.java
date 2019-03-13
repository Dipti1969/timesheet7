/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.timesheet.changeprofile;

import com.timesheet.bean.Employee;
import java.util.List;

/**
 *
 * @author prodigy
 */
public interface ProfileService {

    public Employee getByPk(String emp_id);
    public boolean changeprofile(String emp_id,String userid,String emp_fname,String fname,String emp_lname,String gender,String emp_email,String emp_address,String emp_phone,String emp_mobile,String emp_birthdate);
    public String Insert_fmt_date(String dt);
    public List<Employee> checkUserId(String userid);
    public List<Employee> checkFName(String empfname);
}
