package com.timesheet.adminmanagetimesheet;

import com.timesheet.bean.EmpTransaction;
import com.timesheet.bean.Employee;
import java.util.List;

public interface ManageTimeSheetService {
    public List<Employee> getEmployees();
    public List<EmpTransaction> getMonthTransaction(String emp_id);
    public String getTotalDuration(String empid);
    public String getEmpName(String emp_id);
    public List<EmpTransaction> getStatus();
    public boolean  addTimeSheet(long trans_id,String end_time,int assign_by, String proxy_empid,int proj_id, String work_desc);
    public String getMonthYearHours(String emp_id,String month,String year);
    public void insertTimeSheet(EmpTransaction et);
    public EmpTransaction getEmpTransaction(long trans_id,String emp_id);
    public boolean updateEmpTransaction(long trans_id,String trans_date, String start_time,String end_time,int assign_by, String proxy_empid,int proj_id, String work_desc);
    public String getAllTotalDuration(String empid);
    public boolean deleteTransaction(long trans_id);
    public List<EmpTransaction> getDateTransaction(String emp_id, String trans_date);
    public String Insert_fmt_date(String dat);
    public String getTotalDurationWithDate(String empid, String trans_date);
}
