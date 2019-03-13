/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.timesheet.registration;

import com.timesheet.bean.Department;
import com.timesheet.bean.Designation;
import com.timesheet.bean.Employee;
import com.timesheet.bean.Role;
import java.util.List;

/**
 *
 * @author prodigy
 */
public interface RegistrationService {

    public void add(Employee employee);
    public List<Employee> select();
    public List<Role> getByRole();
    public List<Department> getByDepartment();
    public List<Designation> getByDesignation();
    public Employee getByPk(String emp_id);
    public boolean update(String emp_id,String emp_fname,String fname,String emp_lname,String gender,int dept_id,int roleid,int desi_id,String time_right,String emp_email,String emp_address,String emp_phone,String emp_mobile,String emp_birthdate);
    public String Insert_fmt_date(String dat);
    public boolean blockuser(String emp_id);
    public boolean unblockuser(String emp_id);
    public String getEmpName(String emp_id);
    public boolean deleteUser(String empid);
    public boolean check(String empid);
}
