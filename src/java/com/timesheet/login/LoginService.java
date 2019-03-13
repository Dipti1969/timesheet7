package com.timesheet.login;

import com.timesheet.bean.Employee;

public interface LoginService {
    public Employee checkUser(String username, String password);
    public int getEmpRole(String username);
    public boolean  changepassword(String emp_id,String newpassword);
    public String getStatus(String emp_id);
    public String getpassword(String emp_id);
    public String checkEmailID(String emailID);
    public Employee getUserPassword(String emp_email);
    public String checkRight(String username);
}
